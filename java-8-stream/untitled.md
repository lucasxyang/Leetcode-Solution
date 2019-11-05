# Stream&lt;T&gt;, IntStream, Collector&lt;T,A,R&gt;, & Collectors

在1243中，我们用到了这样一个call，

```text
List<Integer> inputList = Arrays.stream(arr).boxed().collect(Collectors.toList());
```

## Background

首先，\(带s的俩\) Arrays, Collectors 是class，正如同 Collections是有一系列共同behavior的函数definition的类, 而Collection&lt;E&gt; 是仅有函数声明declaration的泛型接口。 

【至于名字带不带s 和 是不是class，没有必然联系。这只是我的一个起助记作用的观察，purely empirical】

举个例子，有behavior的函数definition 可以是Collections.min\(\), Collections.binarySearch\(\)等。这些行为不是接口定义的一部分。部分操作可能只对部分Collection类管用，比如binarySearch只对，也只应当对List操作，对Map和Set不应该有效果。

而函数declaration，则是譬如 someCollectionDeque.isEmpty\(\), anotherCollectionMap.removeAll\(\)。调用者是一个java集合的实例。这些函数是任何一个Collection类（符合Collection接口的类）都应有的方法，所以他们是接口定义的一部分。

剩下的 IntStream, Stream&lt;T&gt;, Collector&lt;T,A,R&gt; 都是Interface。

## Stream, IntStream

`Arrays.stream(arr)`是一个static generic \(including primitives\) method, 也是一个overload method。arr 可以是int\[\], 带起始index的double\[\], 或T\[\], 返回则是IntStream, DoubleStream, Stream&lt;T&gt;. 

还有一个等价的overload函数，`IntStream.of(int ... values)`

可以这样用: IntStream\(15\), 也可以IntStream\(arr\), 还可以IntStream\(-7, -9, 11\). 这个IntStream.of\(\)函数在背地里调用的其实就是Arrays.stream\(arr\)。



由于我们从int\[\]而非Integer\[\]出发，在得到一个IntStream instance 后，我们用一个属于IntStream的中间过程 boxed\(\)得到 Stream&lt;Integer&gt;. 下一步我们要做的，是将这个Stream&lt;Integer&gt; 转换成 List&lt;Integer&gt;. \*

## Collector, Collectors

functional参数，`Collectors.toList()`是一个静态方法，返回一个Collector&lt;T, ?, List&lt;T&gt;&gt; \(generic Collector instance\). 这三个泛型变量分别对应Collector&lt;T, A, R&gt;中的 T, A, 和 R。很明显T已由Stream&lt;Integer&gt; 决定了是Integer。A是accumulation type, 属于implementation detail, 可以认为是 possibly Integer 或类似于StringBuilder 的 mutable IntegerAdder。有意思的是R。按照Collector.toList\(\)的返回，R肯定是一个List&lt;T&gt;或本例的List&lt;Integer&gt;，然而实际的返回类型type却都是ArrayList&lt;T&gt;。一方面，`Collectors.toList()` 说不保证 type & mutability of the List, 另一方面我们看到不管是[OpenJDK 8](http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/util/stream/Collectors.java#l228) 还是 某不明版本的[Oracle JDK](https://stackoverflow.com/questions/58688172/how-to-understand-this-java-8-stream-collect-method/58688410?noredirect=1#comment103691420_58688410), 实际的implementation 都是用ArrayList. 

我觉得是不是可以这么理解：Java Specification 只说用List，但肯定不能光返回一个没有concrete class的interface type。于是Oracle 和 OpenJDK选择了最直接运用最广的ArrayList. Maybe in the future when Oracle releases Java8u1024 或 Java 15, 或者[其他第三方JDK](https://alternativeto.net/software/zulu-certified-openjdk/)觉得LinkedList更堪实用, 突然改用LinkedList, 也是符合这样一个spec的. \*\*

## 转换

有了Stream&lt;Integer&gt; 和 Collector&lt;T, A, R&gt;, 我们就可以调用属于 Stream的collect\(\)函数，并得到一个R type的结果, List&lt;Integer&gt; inputList. 

\*\*: 如果我们想今天在default implementation没有改变的情况下得到一个LinkedList，可以用 `Arrays.stream(arr).boxed().collect(toCollection(LinkedList::new));`



\*: 如果我们仔细看IntStream, 发现它也有一个方法叫`collect(Supplier supplier, ObjIntConsumer accumulator, BiConsumer combiner)`

所以如果我们愿意的话，可以跳过从IntStream到Stream&lt;Integer&gt;的转换，直接生成List&lt;Integer&gt;. 

```
List<Integer> list2 = Arrays.stream(arr).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
```

或者，

```text
ObjIntConsumer<ArrayList> accumulator =
    ( a, val) -> {
    // val is the *hidden* argument not in generics type list
    // it is of type int because this is ObjIntConsumer in IntStream.
        a.add(val);
        // a.add(0);
    };
    
    // 其实如果没有parallel，这个combiner是用不上的
  BiConsumer<ArrayList, ArrayList> combiner =
    (a1, a2) -> {
        // a1.clear();
        a1.addAll(a2);
    };
  
  List<Integer> list2 = Arrays.stream(arr).collect(ArrayList::new, accumulator, combiner);
```

collect\(\) 有三个argument，\(Supplier&lt;R&gt; supplier, ObjIntConsumer&lt;R&gt; accumulator, BiConsumer&lt;R,R&gt; combiner\)。名字很intimidating，实际上doc讲得很平易，目的分别是创建一个装结果的container；一个reducer做mutable reduction；一个能将并行计算多个reduction结果合并起来的combiner。



More reading: [https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)

Ref: [https://www.logicbig.com/how-to/code-snippets/jcode-java-8-streams-stream-collect.html](https://www.logicbig.com/how-to/code-snippets/jcode-java-8-streams-stream-collect.html)

