# C/C++ Pointer cheatsheet

### Note this doc is for people who have learned C/C++ in the past and just need a quick refresher. 

## All 'bout that \*, &, sizeof\(\) in C and C++

Okay I am not a huge fan of C/C++ style memory management. Not pointer this, not memory-location that. I hate when people use everyday words and give them new meanings with nuance. 

However many great programmers, including those in LC, use C/C++ nonetheless. To understand their code, I have to get my hands wet. 

A pointer references\(v.\) a location in memory, and obtaining the value stored at that location is known as dereferencing the pointer. 

{% tabs %}
{% tab title=" \* and &" %}
First of all, `*` is the Indirection \(Dereference\) operator in C/CPP, while `&` is the Address-of operator. 

Note: `*` should only be applied to a Pointer type, not on others like `int`. 

通俗地讲，`&`是取地址，并制成指针。`*` 是找到指针parameter的指向地址\(destination\)，取内容。
{% endtab %}

{% tab title="Sample C Code" %}
```text
#include<stdio.h>

int main() {
    
    int x=10;
    int y=25;
    int z=x+y;
    
    int *a = &x;
    
    printf("%d\n", x);
    printf("%d\n", a);
    
    printf("%d\n", &x);
    printf("%d\n", &a);
    
    // printf("%d\n", *x); // does not compile
    printf("%d\n", *a);
}

/*

10
-1033637708
-1033637708
-1033637696
10

*/
```
{% endtab %}

{% tab title="Sample C++ Code" %}
```text
#include <iostream>

using namespace std;

int main() {
    int x=10;
    int y=25;
    int z=x+y;
    
    int *a = &x;
    
    cout << x << endl;
    cout << a << endl;

    cout << &x << endl;
    cout << &a << endl;
    
    // cout << *x << endl; // does not compile
    cout << *a << endl;
}

/*

10
0x7ffd7086b884
0x7ffd7086b884
0x7ffd7086b890
10

*/
```
{% endtab %}
{% endtabs %}



还有sizeof\(\). C的lang spec只说到这样的一个size relation: char &lt; short &lt;= int &lt;= long &lt;= long long，并且char的size为1。

```text
int i = 20;
int p* = &i;

// &i === p: -1225406604 (使用整型非长整型的溢出，第三行同）
// *p === i: 20
// &p : -1225406604
// *i : compiler error

sizeof(i) can be 2, 4, etc. It is compiler dependent. Language spec only asks it to be at least 16 bits (2 bytes). 
sizeof(int) is the same of sizeof(i).
sizeof(p) 即为 sizeof(&i),是指针的size，取决于OS.
sizeof(&p) 为内存地址的尺寸。比如8表明总共有2^(8*8)个内存地址。
```

Sample C code with output: 

```text
int main() {
    int i = 5;
    int* p = &i;
    
    printf("%ld\n", p);
    printf("%d\n", *p);
    printf("%ld\n", &p);
    printf("%ld\n", &(*p));
    
    printf("%d\n", sizeof(i));
    // printf("%d\n", sizeof(*i)); // does not compile
    printf("%d\n", sizeof(&i));
    printf("%d\n", sizeof(p));
    printf("%d\n", sizeof(*p));
    printf("%d\n", sizeof(&p));
}

/*

140730514249500
5
140730514249504
140730514249500
4
8
8
4
8

*/
```



References:

{% embed url="https://stackoverflow.com/questions/2704167/type-declaration-pointer-asterisk-position" %}

{% embed url="https://en.wikipedia.org/wiki/C\_data\_types" %}

{% embed url="https://en.wikipedia.org/wiki/Operators\_in\_C\_and\_C%2B%2B" %}

{% embed url="https://en.wikipedia.org/wiki/Dereference\_operator" %}

{% embed url="https://stackoverflow.com/questions/399003/is-the-sizeofsome-pointer-always-equal-to-four" %}

{% embed url="https://stackoverflow.com/questions/11438794/is-the-size-of-c-int-2-bytes-or-4-bytes" %}



## More about memory management



