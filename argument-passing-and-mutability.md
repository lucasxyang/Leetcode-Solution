# Argument passing and mutability

```text
1. by Value (Mutable object)
void foo (Object k, List l) {
   k.setName("new name"); 
   l.add(o2);
}

2. by Value (re-assignment, mutability unknown)
void foo (Object k, List l) {
   k = new Object();
   l = new ArrayList();
}

3. by Ref (C#. Unavailable in Java. Mutable object.)
void foo (ref Object k, ref List l) {
   k.setName("new name");
   l.add(o2);
}

4. by Ref (C#. Unavailable in Java. Re-assignment, mutability unknown)
void foo (ref Object k, ref List l) {
   k = new Object();
   l = new ArrayList();
}
```

解析：

1. 很容易理解的过程。setName\(\)更改的是对象的一个属性，不改变对象本身。只需k是mutable type即可。
2. Reference Type 变量k 的**值** 被传过来了。这个值是什么？是reference\(Eng.\) to the object，essentially它可以理解为object address. 我们接下来的reassignment丝毫不涉及到更改这个object，而是新开辟一个对象，并在当前的context中，把这个k的值（存有地址）改到新对象那边去。外部caller的reference也不变。
3. 这个方法是C\#的，在Java中不可用。我们通过传引用reference，直接对heap中的这个被指向的对象进行mutation操作。好理解。
4. 这个方法也是C\#的，在Java中不可用。我们通过传引用reference，将reference本身改了。引用/reference相当于一个箭头，我们将整个箭头都替换掉。

