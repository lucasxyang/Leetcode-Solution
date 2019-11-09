# Comparable vs Comparator

Comparable, 顾名思义是一个interface, 就像Serializable, Navigable, Cloneable。Comparable由某一class实现，就是implemented by classA. 当我们自己写Comparable的时候，我们往往是新定义一个custom class, such as Student, 然后让Student implements Comparable&lt;Student&gt;，并在类中重定义/override int compareTo\(T o\)这一函数来对他们按学号排序，在此例即为int compareTo\(Student o\)。（当然别忘了boolean equals\(Object o\) 和 int hashCode\(\)这两个）

相比之下Comparator就轻量多了。我们可以定义一个custom Comparator on the fly, 哪怕对已有默认排序的类我们都可以给它扔一个新的Comparator, 比如学生的默认排序是学号，我们可以在需要的时候给它按成绩高低排序，或者按出生日期排序。Comparator有效地将排序逻辑从类中剥离出。



## Syntax

#### Comparable&lt;T&gt;

Comparable&lt;T&gt; 很好写, 可能一个原因是它的写法比较单一。

```text
import java.lang.Comparable;

public class Student implements Comparable<Student> {
 
    private Long id;
    private String name;
    private int score;
    private char grade;
    private Date dob;
    
    // getter
    private Long getId() {
        return this.id;
    }
    
    int getScore() {
        return this.score;
    }
          
    @Override
    public int compareTo(Student st) 
    {
        return this.getId().compareTo( st.getId() );
        // equivalent to this.getId() - st.getId(; both yield to ascending ordered list
    }
}
```

#### Comparator&lt;T&gt;

写Comparator&lt;T&gt;最核心的事情是要重定义函数compare\(T o1, T o2\)。在Java 7及以前，这几乎是custom Comparator&lt;T&gt;的唯一方式。With the advent of functional programming in Java 8，我们能在Comparator&lt;T&gt; interface中看到很多封装好的函数。我们有更多选择来生成选用Comparator&lt;T&gt;. 

重写 compare\(T o1, T o2\) 的方式很多，我们一个个说。

1. 我们可以创建一个Comparator&lt;T&gt;类型的field或者variable，并在其中实现要重写的compare\(T o1, T o2\)。

```text
import java.util.Comparator;

// definition (anywhere, eg. next to main method, or within Student class)
public static Comparator<Student> StudentScoreComparator 
                          = new Comparator<Student>() {

    public int compare(Student s1, Student s2) {
    	
      int score1 = s1.score;
      int score2 = s2.score;
      
      //ascending order
      return score1 - score2;
      
      //descending order
      //return score2 - score1;
    }

};

// usage
Arrays.sort(students, StudentScoreComparator);
```

2. 我们可以将上面的过程浓缩写在一行里，这样可以更方便更随时地给出一个Comparator.

```text
// usage
Arrays.sort(students, new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
      //ascending order
      return s1.score - s2.score;
    }
});

```

需要指出的是，这里用到了 anonymous inner class. 这样的类只需实现接口，就满足了目的。我们不在乎它的名字。

3. 【Java 8】可以用 :: 来取用T类里的函数。

```text
Arrays.sort(gift, Comparator.comparing(Student::getScore));
```

4. 【Java 8】对于已实现Comparable&lt;T&gt;的类来说，我们可以用Java 8的箭头函数来调用它的默认comparator。

```text
Arrays.sort(students, (m1, m2) -> m1 - m2);
```

5. 【Java 8】对于已实现Comparable&lt;T&gt;的类来说，我们也可以得到它的default comparator, 在排序前做一些改动。

```text
Arrays.sort(students, Comparator.naturalOrder());
// OR
Arrays.sort(students, Comparator.naturalOrder().reversed());

```

6. 【Java 8】如果担心有平局tie, 我们可以用`thenComparing(Comparator<? super T> other)`，以及`thenComparingInt(ToIntFunction<? super T> keyExtractor)`。例见[此](https://www.javaworld.com/article/3323403/java-challengers-5-sorting-with-comparable-and-comparator-in-java.html)。

#### 

#### 其他数据结构

对于Array和List的排序，我们有很多方法做。如果想用custom Comparator，调用`Collections.sort(List<T> list, Comparator<? super T> c)`或者`Arrays.sort(T[] a, Comparator<? super T> c)`便好。

对于Map或Set，我们要用 `TreeMap` or `TreeSet` 。Use `TreeMap` or `TreeSet` when sorting a `Map` or a `Set`. 他们自带Comparator排序。



#### 总结

我们概括一下使用Comparable 和 Comparator的场景。

Comparable: 

* Use Comparable when sorting is standard for the given class.
* Use Comparable when sorting needs no change/customization
* Many core Java classes implement `Comparable`.
* Located in `java.lang` .
* Client is unaware of the inside mechanism. 
* T Class needs to implement the interface

Comparator: 

* Use Comparator when more flexibility is needed.
* It's possible to use lambdas with `Comparator`. 
* Located in `java.util`.
* T Class does not need to implement the interface.
* Client is responsible for creating a custom comparator that implements the interface.
* Provides multiple sorting strategies to choose from. 



关于compareTo\(\)的返回值\*，

|   **If the comparison returns** |   **Then ...** | Position of \*\*this\*\* |
| :--- | :--- | :--- |
|   `>= 1` |   `this.name > simpson.name` | After |
|   `0` |   `this.name == simpson.name` | Equal |
|   `<= -1` |   `this.name < simpson.name` | Before |

\*: 假设我们使用标准的this.name - otherObject.name语句。如果使用逆序的话，根据同样的返回值，第二栏的大小顺序要改变，第三栏的位置顺序也要改变，最终得到的custom comparator等价于一个descending order comparator。

