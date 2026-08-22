# Java Strings – Deep Dive

## 1. What is a String?

* `String` is **not a primitive data type**.
* It is an **object** from the `java.lang.String` class.
* Strings represent a sequence of characters.
* `String` is also a **final class**, so it cannot be extended.

```java
String name = "Aditya";
```

Conceptually:

```text
name
  |
  v
"Aditya"
```

---

# 2. Internal Representation of String

## Pre-Java 9 → `char[]`

Before Java 9, Strings were internally backed by a character array:

```text
char[]
```

Java's `char` uses **2 bytes**.

Example:

```java
String s = "JAVA";
```

Conceptually:

```text
J | A | V | A
2   2   2   2 bytes
```

So ASCII/English text could consume more memory than necessary.

---

## Java 9+ → Compact Strings

Java 9 introduced **Compact Strings** to reduce memory usage.

Modern Java internally uses:

```text
byte[] + coder flag
```

The `coder` determines how characters are stored.

### Latin-1 / ASCII-Compatible Characters

For characters that fit in Latin-1:

```text
coder = LATIN1
```

They can use **1 byte per character**.

Example:

```java
String s = "JAVA";
```

Conceptually:

```text
J | A | V | A
1   1   1   1 byte
```

### Characters Requiring UTF-16

If the String contains characters outside Latin-1, Java uses UTF-16 storage internally.

Conceptually:

```text
byte[] + UTF-16 coder
```

This allows Java to store a wider range of Unicode characters.

> **Important Note:** Think of this as an internal implementation detail. Java's public `String` API and Unicode behavior remain the same.

---

# 3. String Immutability

Strings in Java are **immutable**.

Once a `String` object is created, its value cannot be changed.

```java
String s = "Hello";
s = s + " World";
```

The original `"Hello"` object is **not modified**.

Conceptually:

```text
Step 1:

s
|
v
"Hello"
```

After:

```java
s = s + " World";
```

A new String is created:

```text
Old Object              New Object

"Hello"                 "Hello World"
                           ^
                           |
                           s
```

The old object may later become eligible for garbage collection if nothing references it.

---

## Important: Reassigning Is Not Modifying

```java
String s = "Hello";

s = "Java";
```

This does **not** change `"Hello"` into `"Java"`.

It only changes what `s` refers to:

```text
Before:

s ---> "Hello"

After:

s ---> "Java"
```

---

# 4. Why Are Strings Immutable?

This is a very common interview question.

## A. Security

Strings are commonly used for:

* Database URLs
* Usernames
* Passwords
* File paths
* Class names
* Network connections

Example:

```java
String url = "jdbc:mysql://localhost:3306/db";
```

If Strings were mutable, code could potentially change important values after validation.

Immutability makes a String value stable once created.

---

## B. Thread Safety

Because a String cannot change, multiple threads can safely share the same String object.

```text
Thread 1 ----\
              ---> "Java"
Thread 2 ----/
```

Neither thread can modify `"Java"`.

Therefore, Strings are naturally safe to share.

---

## C. Hash Code Stability and Caching

Strings are heavily used as keys in:

```text
HashMap
HashSet
Hashtable
```

Example:

```java
Map<String, Integer> map = new HashMap<>();

map.put("Java", 100);
```

If a key changed after being inserted into a `HashMap`, lookup behavior could break.

Because Strings are immutable:

```text
String value never changes
        ↓
hashCode remains stable
        ↓
Safe and efficient HashMap key
```

The `String` implementation can also cache its hash code after calculation, avoiding repeated computation for the same immutable value.

---

# 5. String Pool vs Heap

This is one of the most important String concepts.

The **String Pool** is used to reuse String values and avoid unnecessary duplicate objects.

The pool is part of the JVM's managed heap memory.

---

# 6. Rule 1: String Literals Are Reused Through the Pool

```java
String s1 = "Java";
String s2 = "Java";
```

Conceptually:

```text
String Pool:

       "Java"
       /    \
      /      \
    s1        s2
```

Both references point to the same String object.

Therefore:

```java
System.out.println(s1 == s2);
```

Output:

```text
true
```

Because `==` compares references.

And:

```java
System.out.println(s1.equals(s2));
```

Output:

```text
true
```

Because both values are also equal.

---

# 7. Rule 2: Using `new String()`

```java
String s1 = "Java";
String s3 = new String("Java");
```

The literal `"Java"` can exist in the String Pool, while `new String("Java")` explicitly creates a **new String object**.

Conceptually:

```text
String Pool:              Heap:

"Java" <--- s1            "Java" <--- s3
```

Therefore:

```java
System.out.println(s1 == s3);
```

Output:

```text
false
```

Because the references point to different objects.

But:

```java
System.out.println(s1.equals(s3));
```

Output:

```text
true
```

Because the values are the same.

### Golden Rule

```text
==       -> Reference comparison
equals() -> Value/content comparison
```

---

# 8. Rule 3: Runtime String Concatenation

Consider:

```java
String a = "Ja";
String s1 = "Java";

String s4 = a + "va";
```

Since `a` is a variable, the concatenation is generally resolved at runtime.

```text
"Ja" + "va"
   ↓
Runtime concatenation
   ↓
New String result
```

Therefore:

```java
System.out.println(s1 == s4);
```

Typically:

```text
false
```

Because `s1` refers to the pooled `"Java"` literal, while the runtime concatenation result is a different String object.

But:

```java
System.out.println(s1.equals(s4));
```

Output:

```text
true
```

---

# 9. Compile-Time Concatenation

Now consider:

```java
String s1 = "Java";

String s5 = "Ja" + "va";
```

Both values are literals.

The compiler can resolve:

```text
"Ja" + "va"
```

At compile time as:

```text
"Java"
```

So it can reuse the same pooled String.

Conceptually:

```text
String Pool:

       "Java"
       /    \
      /      \
    s1        s5
```

Therefore:

```java
System.out.println(s1 == s5);
```

Output:

```text
true
```

---

# 10. Compile-Time vs Runtime Concatenation

## Literal + Literal

```java
String s1 = "Java";
String s2 = "Ja" + "va";

System.out.println(s1 == s2);
```

Output:

```text
true
```

Reason:

```text
Compiler resolves "Ja" + "va"
            ↓
          "Java"
            ↓
      Reuses pooled String
```

---

## Variable + Literal

```java
String a = "Ja";

String s1 = "Java";
String s2 = a + "va";

System.out.println(s1 == s2);
```

Output:

```text
false
```

Reason:

```text
Variable value involved
        ↓
Runtime concatenation
        ↓
Different String object result
```

---

# 11. `final` Variable and Compile-Time Constants

A useful interview point:

```java
final String a = "Ja";

String s1 = "Java";
String s2 = a + "va";

System.out.println(s1 == s2);
```

Because `a` is a compile-time constant, the compiler can optimize:

```text
a + "va"
```

To:

```text
"Java"
```

So:

```java
s1 == s2
```

Can be:

```text
true
```

But this is different:

```java
String a = "Ja"; // Not final

String s1 = "Java";
String s2 = a + "va";
```

Here, concatenation occurs at runtime.

```java
s1 == s2
```

Result:

```text
false
```

---

# 12. `intern()` Method

`intern()` returns the canonical representation of a String from the String Pool.

Example:

```java
String s1 = "Java";

String s2 = new String("Java");

System.out.println(s1 == s2);
```

Output:

```text
false
```

Now:

```java
String s3 = s2.intern();

System.out.println(s1 == s3);
```

Output:

```text
true
```

Conceptually:

```text
new String("Java")
        |
        | intern()
        v
Pooled "Java"
```

---

# 13. `String` Is Final

The `String` class is declared as `final`.

Conceptually:

```java
public final class String {
}
```

Therefore:

```java
class MyString extends String {
}
```

Is not allowed.

This helps preserve the behavior, security, and immutability guarantees of `String`.

---

# 14. String Comparison: `==` vs `equals()`

Consider:

```java
String s1 = new String("Java");
String s2 = new String("Java");
```

```java
System.out.println(s1 == s2);
```

Output:

```text
false
```

Because:

```text
s1 ---> String Object 1
s2 ---> String Object 2
```

But:

```java
System.out.println(s1.equals(s2));
```

Output:

```text
true
```

Because both contain:

```text
Java
```

### Always Remember

```text
==       -> Are references the same?
equals() -> Is the content the same?
```

---

# 15. Common String Creation Summary

| Code                            | Result                                           |
| ------------------------------- | ------------------------------------------------ |
| `String s = "Java"`             | Uses/reuses pooled literal                       |
| `String s = new String("Java")` | Explicitly creates a new String object           |
| `"Ja" + "va"`                   | Compile-time constant, can be folded to `"Java"` |
| `variable + "va"`               | Runtime concatenation                            |
| `s.intern()`                    | Returns canonical pooled representation          |

---

# 16. Quick Memory Visualization

```text
String s1 = "Java";
String s2 = "Java";
```

```text
String Pool:

       "Java"
       /    \
      /      \
    s1        s2
```

---

```java
String s1 = "Java";
String s3 = new String("Java");
```

```text
Pool:                   Regular String Object:

"Java" <--- s1          "Java" <--- s3
```

---

```java
String s1 = "Java";
String s2 = "Ja" + "va";
```

```text
Compile Time:

"Ja" + "va"
     ↓
   "Java"
     ↓
Reuse pooled String
```

---

```java
String a = "Ja";
String s2 = a + "va";
```

```text
Runtime:

a + "va"
   ↓
String concatenation
   ↓
Resulting String object
```

---

# 17. Most Important Interview Rules

1. `String` is an **object**, not a primitive.
2. `String` is **immutable**.
3. `String` is a **final class**.
4. Reassigning a String does not modify the original object.
5. A new String value is created when an operation produces a changed result.
6. String literals are reused through the **String Pool**.
7. `new String()` explicitly creates a new String object.
8. `==` compares references.
9. `equals()` compares String content.
10. Literal + Literal can be resolved at compile time.
11. Variable + Literal is generally resolved at runtime.
12. `final` compile-time constants can participate in compile-time concatenation.
13. `intern()` returns the canonical pooled representation.
14. Immutability improves security.
15. Immutability makes Strings safe to share between threads.
16. Stable String values make them suitable as hash-based collection keys.
17. String implementations can cache hash code results because the contents never change.
18. Java 9 introduced **Compact Strings** using `byte[]` plus an internal coder representation.

## Golden Rule

```text
String Literal
      ↓
JVM can reuse pooled representation

new String()
      ↓
Explicitly creates a new String object

== 
      ↓
Reference comparison

equals()
      ↓
Content comparison

String
      ↓
IMMUTABLE
```
