# Pass by reference? Pass by value?

{% tabs %}
{% tab title="C" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">C</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">ONLY pass by value</td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">
        <p>for primitives: this is for sure, easy</p>
        <p>for Objects: pass the pointer, which is a numeric value</p>
      </td>
    </tr>
  </tbody>
</table>
{% endtab %}

{% tab title="C++" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">C++</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">
        <p>pass by value (recommended)</p>
        <p>pass by reference (with something special).</p>
      </td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">
        <p>&quot;pass by value&quot; is also available in Java and C.
          <br />&quot;pass by value&quot; can be subsequently divided into two groups:
          ordinary pass by value, pass pointer by value.</p>
        <p>Some people call &quot;pass pointer by value&quot; as &quot;pass by address&quot;.</p>
      </td>
    </tr>
  </tbody>
</table>There are three ways to pass argument in C++. Using IBM's [categorization](https://www.ibm.com/support/knowledgecenter/en/SSLTBW_2.3.0/com.ibm.zos.v2r3.cbclx01/cfpv.htm), 

C++ Pass by Value, is the ordinary \*pass by value\* we know. 

C++ Pass by Pointer, is what people often colloquially referred as \*pass by reference" when they use the general CS term "Reference" or common English word "reference". A Pointer is a Reference data type, and is a reference in English sense. [Some C++ folks](https://www.learncpp.com/cpp-tutorial/74-passing-arguments-by-address/) prefer to call this \*pass by address\*. 

C++ Pass by Reference, is something different and I've never learned from school. Note C++ Reference is a proper noun with specialized meaning. C++ Reference is not CS Reference, nor is the common word reference in English. C++ Reference is like an alias to an existing variable. Sub it with "alias name" or "alternative id" could help understanding this concept. Its declaration is as follows, `<Type>& <Name>` , and its definition is just like ordinary definition of other types, `int& r_a = a;`. Remember I could only declare and define Pointers in C and early C++, like `int *pt = &someVar`and `void func(int* ptr)`? These two syntaxes look similar but they have noticeable differences. 

```text
// pass by value

#include <stdio.h>

void func (int a, int b)
{
   a += b;
   printf("In func, a = %d    b = %d\n", a, b);
}

int main(void)
{
   int x = 5, y = 7;
   func(x, y);
   printf("In main, x = %d    y = %d\n", x, y);
   return 0;
}

// Output:
In func, a = 12 b = 7
In main, x = 5  y = 7
```

```text
// pass by Pointer

#include <stdio.h>

void swapnum(int *i, int *j) {
  int temp = *i;
  *i = *j;
  *j = temp;
}

int main(void) {
  int a = 10;
  int b = 20;

  swapnum(&a, &b);
  printf("A is %d and B is %d\n", a, b);
  return 0;
}

// Output:
A is 20 and B is 10
```

```text
// pass by Reference

#include <stdio.h>

void swapnum(int &i, int &j) {
  int temp = i;
  i = j;
  j = temp;
}

int main(void) {
  int a = 10;
  int b = 20;

  swapnum(a, b);
  printf("A is %d and B is %d\n", a, b);
  return 0;
}

// Output: 
A is 20 and B is 10
```
{% endtab %}

{% tab title="Java" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">Java</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">ONLY pass by value</td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">
        <p>for primitives: this is for sure, easy</p>
        <p>for Objects: pass the pointer, which is a numeric value (like an Opaque
          type)</p>
      </td>
    </tr>
  </tbody>
</table>
{% endtab %}

{% tab title="C\#/VB.NET" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">.NET CLI</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">
        <p>pass by value (default and recommended);</p>
        <p>pass by reference (with keyword <code>ref</code> or<code>in</code> or<code>out</code>)</p>
      </td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">Almost same as Java.</td>
    </tr>
  </tbody>
</table>
{% endtab %}

{% tab title="Swift" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">Apple Swift</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">ONLY pass by value</td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">
        <p>for primitives: this is for sure, easy</p>
        <p>for Objects: pass the pointer, which is a numeric value</p>
      </td>
    </tr>
  </tbody>
</table>
{% endtab %}

{% tab title="JavaScript" %}
<table>
  <thead>
    <tr>
      <th style="text-align:left">JS</th>
      <th style="text-align:left">What they say...</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left">Official Doc</td>
      <td style="text-align:left">ONLY pass by value</td>
    </tr>
    <tr>
      <td style="text-align:left">My Understanding</td>
      <td style="text-align:left">
        <p>for primitives: this is for sure, easy</p>
        <p>for Objects: pass the pointer, which is a numeric value</p>
      </td>
    </tr>
  </tbody>
</table>
{% endtab %}
{% endtabs %}





