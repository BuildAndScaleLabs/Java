# Java Pass By Value Explained (Production-Level Notes)

## Introduction

One of the most common Java interview questions is:

> Is Java Pass By Value or Pass By Reference?

Many developers answer:

```text
Objects are Pass By Reference
Primitives are Pass By Value
```

This is incorrect.

Java is always:

```text
PASS BY VALUE
```

For both primitives and objects.

The confusion comes from how object references work.

---

# What Does Pass By Value Mean?

Pass By Value means:

```text
A copy of the variable's value is passed to the method.
```

The original variable is never passed.

---

# Primitive Example

```java
public class Main {

    static void change(int number) {
        number = 100;
    }

    public static void main(String[] args) {

        int x = 10;

        change(x);

        System.out.println(x);
    }
}
```

Output:

```text
10
```

---

## Memory

Before Method Call

```text
main()

x = 10
```

Method Call

```java
change(x);
```

JVM copies:

```text
10
```

New Stack Frame

```text
main()

x = 10

-------------------

change()

number = 10
```

Now:

```java
number = 100;
```

Only the copy changes.

```text
main()

x = 10

-------------------

change()

number = 100
```

Method Ends

```text
change() frame destroyed
```

Remaining:

```text
main()

x = 10
```

Original variable never changed.

---

# Object Example

Consider:

```java
class Person {
    String name;
}
```

---

## Example

```java
public class Main {

    static void update(Person person) {
        person.name = "Rahul";
    }

    public static void main(String[] args) {

        Person p = new Person();
        p.name = "Sahil";

        update(p);

        System.out.println(p.name);
    }
}
```

Output:

```text
Rahul
```

Many people think:

```text
Java passed object by reference
```

Wrong.

Java still passed by value.

---

# What Is Actually Passed?

Suppose JVM creates:

```text
Person Object Address = 1000
```

Memory:

```text
STACK

p = 1000

HEAP

Person Object
name = "Sahil"
```

Method Call:

```java
update(p);
```

JVM copies:

```text
1000
```

New Stack Frame:

```text
main()

p = 1000

--------------------

update()

person = 1000
```

Notice:

```text
Both variables contain the same address value.
```

The reference value was copied.

The object was NOT copied.

---

# Why Object Changes Are Visible

Both variables point to:

```text
Object 1000
```

Memory:

```text
p -----------\
              \
               ---> Person Object
              /
person ------/
```

Now:

```java
person.name = "Rahul";
```

modifies:

```text
Object 1000
```

Since both references point to the same object:

```java
System.out.println(p.name);
```

prints:

```text
Rahul
```

---

# Multiple References To Same Object

Example:

```java
Person p1 = new Person();

Person p2 = p1;
```

Memory:

```text
STACK

p1 = 1000

p2 = 1000

HEAP

Person Object
```

Both references point to the same object.

---

## Proof

```java
p2.name = "Rahul";
```

Now:

```java
System.out.println(p1.name);
```

Output:

```text
Rahul
```

Because there is only one object.

---

# Important Distinction

Copying a Reference

```java
Person p2 = p1;
```

does NOT mean:

```text
Copy Object
```

It means:

```text
Copy Reference Value
```

---

# Reference Copy vs Object Copy

Suppose:

```java
Person p1 = new Person();
```

Memory:

```text
p1 = 1000
```

Now:

```java
Person p2 = p1;
```

Memory:

```text
p1 = 1000

p2 = 1000
```

Same object.

No new object created.

---

# Actual Object Copy

To create a separate object:

```java
Person p2 = new Person();

p2.name = p1.name;
```

Memory:

```text
p1 = 1000

p2 = 2000
```

Now there are two different objects.

Changes to one do not affect the other.

---

# Most Important Example

This example proves Java is NOT Pass By Reference.

```java
class Person {
    String name;
}

public class Main {

    static void update(Person person) {

        person = new Person();

        person.name = "Rahul";
    }

    public static void main(String[] args) {

        Person p = new Person();

        p.name = "Sahil";

        update(p);

        System.out.println(p.name);
    }
}
```

Output:

```text
Sahil
```

---

# Why?

Before Call

```text
p = 1000
```

Method Call

```text
person = 1000
```

Reference Value Copied.

Now:

```java
person = new Person();
```

Creates:

```text
2000
```

Memory:

```text
main()

p = 1000

-------------------

update()

person = 2000
```

Only local variable changed.

Original variable:

```text
p
```

still points to:

```text
1000
```

Method Ends

```text
update() frame destroyed
```

Object:

```text
2000
```

becomes eligible for GC.

Output:

```text
Sahil
```

---

# Interview Answer

## Is Java Pass By Value?

Yes.

Java is always Pass By Value.

---

## What Is Passed For Primitive Types?

```text
Copy of Primitive Value
```

Example:

```java
int x = 10;
```

Method receives:

```text
Copy of 10
```

---

## What Is Passed For Objects?

```text
Copy of Reference Value
```

Example:

```java
Person p = new Person();
```

Method receives:

```text
Copy of object's address/reference
```

---

## Does Java Ever Pass Objects By Reference?

No.

Java never passes variables by reference.

---

# Easy Memory Trick

```text
Primitive

Copy Value

10 ---> 10

-----------------------

Object

Copy Reference

1000 ---> 1000

-----------------------

Object Itself

Never Copied Automatically
```

---

# Final Summary

```text
Java = Always Pass By Value

Primitive
    ↓
Copy of Value

Object
    ↓
Copy of Reference Value

Reference Copy
    ↓
Same Object

Object Copy
    ↓
New Object

Method Can
    ↓
Modify Object State

Method Cannot
    ↓
Change Caller's Reference Variable
```

This is the correct production-level explanation of Java's parameter passing mechanism and one of the most frequently asked Java interview topics.