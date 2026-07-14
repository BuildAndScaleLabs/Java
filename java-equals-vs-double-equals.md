# `==` vs `.equals()` in Java (Production-Level Notes)

> One of the most misunderstood topics in Java is the difference between `==` and `.equals()`.
>
> The short version:
>
> - `==` compares **references** (or primitive values).
> - `.equals()` compares **logical equality**, **only if the class overrides it**.

# Interview Takeaways

✅ `==` compares primitive values and object references.

✅ `.equals()` is a method inherited from `Object`.

✅ If a class does **not** override `equals()`, it behaves like `==` for objects.

✅ Classes such as `String`, `Integer`, `ArrayList`, `HashSet`, etc., override `equals()` to provide meaningful logical comparisons.

✅ For your own classes, if you want two objects with the same data to be considered equal, you must override `equals()` (and also `hashCode()`).

✅ `Object.equals()` does **not** compare object contents—it effectively checks whether both references point to the same object.

---

# 1. What does `==` do?

The meaning of `==` depends on what you're comparing.

## For Primitive Types

For primitives (`int`, `char`, `double`, `boolean`, etc.), `==` compares the **actual values**.

```java
int a = 10;
int b = 10;

System.out.println(a == b);
```

Output

```
true
```

Because

```
10 == 10
```

---

## For Objects

For objects, `==` **does NOT compare data**.

It compares whether both variables point to the **same object in memory**.

Example

```java
String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2);
```

Output

```
false
```

Memory

```
s1 ---------> "Java"

s2 ---------> "Java"
```

Two different objects exist.

Even though they contain the same text, they are different objects.

Therefore

```
s1 == s2
```

returns

```
false
```

---

# 2. What does `.equals()` do?

Many beginners think `.equals()` magically compares object data.

**This is NOT true.**

`.equals()` only behaves that way if the class **overrides** it.

This is one of the most important things to remember in Java.

---

# 3. Where does `.equals()` come from?

Every Java class ultimately extends the `Object` class.

```
Object
   ↑
 String
 Student
 Employee
 ArrayList
 HashMap
 ...
```

The `Object` class already contains an `equals()` method.

```java
public boolean equals(Object obj)
```

If your class does **not** override it, Java will automatically use `Object.equals()`.

---

# 4. What does `Object.equals()` actually do?

The default implementation is effectively equivalent to:

```java
public boolean equals(Object obj) {
    return this == obj;
}
```

That means the default implementation checks whether both references point to the **same object**.

So without overriding,

```
.equals()
```

and

```
==
```

behave the same for objects.

---

Example

```java
class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

public class Main {

    public static void main(String[] args) {

        Student s1 = new Student("Ram");
        Student s2 = new Student("Ram");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));

    }
}
```

Output

```
false
false
```

Because

```
Object.equals()
```

is being used.

Internally it does

```
return this == obj;
```

Both objects have different memory addresses.

Memory

```
s1 ------------+
               |
               v
          Student(name=Ram)

s2 ------------+
               |
               v
          Student(name=Ram)
```

Different objects.

Therefore

```
equals()
```

returns

```
false
```

---

# 5. Why does `.equals()` work for String?

Because **String overrides `equals()`**.

The String class provides its own implementation.

Simplified version

```java
public boolean equals(Object obj) {

    // compare characters

}
```

Instead of checking memory addresses, it compares the actual characters.

Example

```java
String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

Output

```
false
true
```

Explanation

```
==
```

↓

Different objects

```
false
```

```
.equals()
```

↓

Character comparison

```
Java == Java
```

↓

```
true
```

---

# 6. A Common Misconception

Many beginners think

> Every object has a useful `.equals()` implementation.

This is **false**.

Every object has an `equals()` method because it inherits one from `Object`.

But unless the class overrides it, the default implementation simply checks reference equality.

---

# 7. Custom Class Example

Suppose we create our own class.

```java
class Student {

    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

Now

```java
Student s1 = new Student("Ram",20);
Student s2 = new Student("Ram",20);

System.out.println(s1.equals(s2));
```

Output

```
false
```

Reason

No override exists.

Java calls

```
Object.equals()
```

which internally behaves like

```java
return this == obj;
```

Different objects

↓

```
false
```

---

# 8. How to Override `equals()`

If we want two students to be considered equal when they have the same name and age, we override `equals()`.

```java
import java.util.Objects;

class Student {

    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Student other = (Student) obj;

        return age == other.age &&
               Objects.equals(name, other.name);
    }
}
```

Now

```java
Student s1 = new Student("Ram",20);
Student s2 = new Student("Ram",20);

System.out.println(s1.equals(s2));
```

Output

```
true
```

Because now Java compares

```
name
```

and

```
age
```

instead of memory addresses.

---

# 9. Understanding the Override

This line

```java
if (this == obj)
    return true;
```

means

If both references point to the same object, no further comparison is needed.

---

This line

```java
if (obj == null)
    return false;
```

means

A real object can never be equal to `null`.

---

This line

```java
if (getClass() != obj.getClass())
    return false;
```

means

Objects of different classes cannot be equal.

---

Then

```java
Student other = (Student) obj;
```

casts the generic `Object` into a `Student`.

---

Finally

```java
return age == other.age &&
       Objects.equals(name, other.name);
```

compares the actual fields.

---

# 10. Why use `Objects.equals()`?

Instead of writing

```java
name.equals(other.name)
```

we use

```java
Objects.equals(name, other.name)
```

because it safely handles `null`.

Example

```java
Objects.equals(null, null)
```

returns

```
true
```

while

```java
null.equals(...)
```

throws

```
NullPointerException
```

---

# 11. `equals()` and `hashCode()`

Whenever you override `equals()`, you should also override `hashCode()`.

Reason

Collections like

- HashMap
- HashSet
- Hashtable

depend on both methods.

Rule

```
Equal objects must have equal hash codes.
```

Example

```java
@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```
# Can `==` Compare Any Two Objects?

`==` can compare object references **only if the two reference types are compatible at compile time**.

This means:

- They are the same type, **or**
- One class is a subclass (child) of the other, **or**
- They share a common interface through assignment compatibility.

Otherwise, the code won't compile.

Example (Valid)

```java
class Animal {}

class Dog extends Animal {}

Animal a = new Dog();
Dog d = new Dog();

System.out.println(a == d);   // Compiles
```

Both references are type-compatible because `Dog` is an `Animal`.

---

Example (Invalid)

```java
class Student {}
class Employee {}

Student s = new Student();
Employee e = new Employee();

System.out.println(s == e);   // Compilation Error
```

Compilation Error

```
Incomparable types: Student and Employee
```

The compiler knows a `Student` reference can never refer to an `Employee` object.

---

Example (Valid because of `Object`)

```java
Object o1 = new Student();
Object o2 = new Employee();

System.out.println(o1 == o2);   // Compiles
```

Here both variables are of type `Object`, so Java allows the comparison.

The result will be `false` unless both references point to the exact same object.

---

## Interview Tip

`==` compares **references**, but Java only allows the comparison when the reference types are assignment-compatible. Otherwise, you'll get a compile-time error saying the types are incomparable.
---

# 12. Quick Comparison Table

| Feature | `==` | `.equals()` |
|----------|------|-------------|
| Available for primitives | ✅ Yes | ❌ No |
| Available for objects | ✅ Yes | ✅ Yes |
| Primitive comparison | Value | Not applicable |
| Object comparison | Reference equality | Logical equality (if overridden) |
| Default object behavior | Memory/reference comparison | Same as `==` (via `Object.equals()`) |
| Can be customized | ❌ No | ✅ Yes (override) |

---

# 13. Summary

### Primitive

```java
int a = 5;
int b = 5;

a == b
```

Compares values.

---

### Object (No Override)

```java
Student s1 = new Student("Ram",20);
Student s2 = new Student("Ram",20);

s1 == s2
```

↓

```
false
```

```
s1.equals(s2)
```

↓

```
false
```

because Java uses `Object.equals()`.

---

### Object (Override Present)

```java
Student s1 = new Student("Ram",20);
Student s2 = new Student("Ram",20);

s1.equals(s2)
```

↓

```
true
```

because your overridden method compares the fields.

---
