# On Garbage Collection

Two families of strategies: tracing, and counting. 

## [Tracing](https://en.wikipedia.org/wiki/Tracing_garbage_collection)

Take Java and JVM for example. HotSpot's garbage collection has two phases: mark, and sweep. **Marking** is where the garbage collector identifies which pieces of memory are in use and which are not. 能从root出发抵达的object都是live object. **Sweeping** 有两种flavor。_Normal deletion_ removes unreferenced objects by removing all but referenced objects, leaving referenced objects and pointers to free space. _Deletion with compacting_ 是在normal deletion的基础上，compact the remaining referenced objects. By moving referenced object together, this makes new memory allocation much easier and faster. 这个Mark and Sweep是最基础的。

很明显，从root出发一个个找live object费时费力（root 是指一些特别的，能走到其他object的object。这样的root包括class, live thread, etc）。因此我们给heap一个three generation model: Young, Old, Permanent. Oracle的[这篇文章](https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html)讲得很好啦。像Java 5,6 的默认GC是serial, 7,8的默认是Parallel, 9,10是G1, 11可能是ZGC. 不过我们可以根据场景使用不同的option来选择gc。

## [Reference Counting](https://en.wikipedia.org/wiki/Reference_counting)

Swift\(以及Objective-C\) 用的Automatic Reference Counting \(ARC\) 就是 RC的一种形式。



### Reference:

{% embed url="https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html" %}

{% embed url="https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html\#RequiredSoftware" %}

{% embed url="https://www.oracle.com/technetwork/tutorials/tutorials-1876574.html" %}

{% embed url="https://www.cubrid.org/blog/understanding-java-garbage-collection" %}

{% embed url="https://www.geeksforgeeks.org/garbage-collection-in-c-sharp-dot-net-framework/" %}





