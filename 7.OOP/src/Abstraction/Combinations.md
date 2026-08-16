# Java Abstract Class + Interface Modifier Combination Cheat Sheet

> A practical reference for understanding valid and invalid modifier combinations in **abstract classes**, **interfaces**, their methods, constructors, and fields.

---

## Table of Contents

1. [Abstract Class Declaration](#1-abstract-class-declaration)
2. [Abstract Class Methods](#2-abstract-class-methods)
3. [Abstract Class Constructors](#3-abstract-class-constructors)
4. [Interface Methods](#4-interface-methods)
5. [Interface Default Methods](#5-interface-default-methods)
6. [Interface Static Methods](#6-interface-static-methods)
7. [Interface Private Methods](#7-interface-private-methods)
8. [Interface Protected Methods](#8-interface-protected-methods)
9. [Interface Constructors](#9-interface-constructors)
10. [Interface Fields](#10-interface-fields)
11. [Quick Abstract Method Combinations](#11-quick-abstract-method-combinations)
12. [Quick Interface Method Combinations](#12-quick-interface-method-combinations)
13. [Access Modifier Rules](#13-access-modifier-rules)
14. [Big Memory Table](#14-big-memory-table)
15. [Most Important Rules](#15-most-important-rules)

---

# 1. Abstract Class Declaration

## Top-Level Abstract Class

```java
public abstract class Animal {
}
```

**Valid** — `public` abstract top-level class.

```java
abstract class Animal {
}
```

**Valid** — package-private abstract class.

```java
protected abstract class Animal {
}
```

**Invalid** — a top-level class cannot be `protected`.

```java
private abstract class Animal {
}
```

**Invalid** — a top-level class cannot be `private`.

```java
final abstract class Animal {
}
```

**Invalid** — `abstract` and `final` are contradictory:

* `abstract` → must be extended.
* `final` → cannot be extended.

```java
static abstract class Animal {
}
```

**Invalid** — a top-level class cannot be `static`.

### Top-Level Class Access Modifiers

A top-level class can be:

* `public`
* package-private

A top-level class cannot be:

* `private`
* `protected`
* `static`

---

## Nested Abstract Classes

Nested classes have different rules.

```java
class Outer {

    private abstract class Inner {
    }

    protected abstract class Inner2 {
    }

    public abstract class Inner3 {
    }

    static abstract class Inner4 {
    }
}
```

All four declarations are **valid**.

A nested class can have:

* `public`
* `protected`
* `private`
* package-private
* `static`

---

# 2. Abstract Class Methods

## Abstract Methods

```java
abstract void method();
```

**Valid**

This declares an abstract method.

```java
public abstract void method();
```

**Valid**

```java
protected abstract void method();
```

**Valid**

```java
private abstract void method();
```

**Invalid**

A `private` method cannot be overridden by a subclass, so it cannot be abstract.

```java
static abstract void method();
```

**Invalid**

A `static` method is associated with the class rather than being overridden polymorphically.

```java
final abstract void method();
```

**Invalid**

A `final` method cannot be overridden, which conflicts with `abstract`.

```java
abstract void method() {
}
```

**Invalid**

An abstract method cannot have a method body.

### Valid Abstract Method Modifiers

```text
abstract
public abstract
protected abstract
```

### Invalid Abstract Method Modifiers

```text
private abstract
static abstract
final abstract
```

---

## Normal Methods Inside an Abstract Class

An abstract class can contain fully implemented methods.

```java
void method() {
}
```

**Valid**

```java
public void method() {
}
```

**Valid**

```java
protected void method() {
}
```

**Valid**

```java
private void method() {
}
```

**Valid**

```java
static void method() {
}
```

**Valid**

```java
final void method() {
}
```

**Valid**

An abstract class can therefore contain both:

* abstract methods
* concrete/implemented methods

---

# 3. Abstract Class Constructors

An abstract class **can have constructors**.

```java
abstract class Animal {

    Animal() {
    }
}
```

**Valid**

```java
abstract class Animal {

    protected Animal() {
    }
}
```

**Valid**

```java
abstract class Animal {

    public Animal() {
    }
}
```

**Valid**

```java
abstract class Animal {

    private Animal() {
    }
}
```

**Valid**

However, a subclass cannot normally invoke a private superclass constructor.

---

## Can We Create an Object of an Abstract Class?

No.

```java
abstract class Animal {
}
```

```java
new Animal();
```

**Invalid**

An abstract class cannot be directly instantiated.

However, its constructor **can execute** when a subclass object is created.

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal constructor");
    }
}

class Dog extends Animal {
}
```

```java
Dog d = new Dog();
```

The `Animal` constructor executes as part of creating the `Dog` object.

---

# 4. Interface Methods

A normal interface method without a body is implicitly:

```java
public abstract
```

Therefore:

```java
interface Animal {

    void sound();
}
```

is equivalent to:

```java
interface Animal {

    public abstract void sound();
}
```

Both are **valid**.

---

## Explicit `public`

```java
interface Animal {

    public void sound();
}
```

**Valid**

The method is implicitly abstract.

---

## Explicit `public abstract`

```java
interface Animal {

    public abstract void sound();
}
```

**Valid**

---

## Protected Interface Method

```java
interface Animal {

    protected void sound();
}
```

**Invalid**

Interface methods cannot have `protected` access.

---

## Private Interface Method Without Body

```java
interface Animal {

    private void sound();
}
```

**Invalid**

A private interface method cannot be abstract.

A private method must provide an implementation.

---

## Plain Interface Method With a Body

```java
interface Animal {

    void sound() {
    }
}
```

**Invalid**

A normal interface method without `default`, `static`, or `private` cannot have a body.

---

# 5. Interface Default Methods

A `default` interface method provides an implementation.

```java
interface Animal {

    default void sound() {
        System.out.println("Sound");
    }
}
```

**Valid**

---

## Default Method Without a Body

```java
default void sound();
```

**Invalid**

A `default` method must have a body.

---

## Public Default Method

```java
public default void sound() {
}
```

**Valid**

---

## Protected Default Method

```java
protected default void sound() {
}
```

**Invalid**

Interface methods cannot be `protected`.

---

## Private Default Method

```java
private default void sound() {
}
```

**Invalid**

A `private` interface method can have an implementation, but it cannot use the `default` modifier.

---

## Static Default Method

```java
static default void sound() {
}
```

**Invalid**

`static` and `default` cannot be combined for an interface method.

---

## Abstract Default Method

```java
abstract default void sound() {
}
```

**Invalid**

`abstract` and `default` are contradictory.

---

# 6. Interface Static Methods

Interface static methods must have a body.

```java
interface Animal {

    static void sound() {
        System.out.println("Sound");
    }
}
```

**Valid**

---

## Static Method Without a Body

```java
static void sound();
```

**Invalid**

A static method must have an implementation.

---

## Public Static Method

```java
public static void sound() {
}
```

**Valid**

---

## Protected Static Method

```java
protected static void sound() {
}
```

**Invalid**

Interface methods cannot be `protected`.

---

## Private Static Method

Since Java 9:

```java
private static void sound() {
}
```

**Valid**

---

## Abstract Static Method

```java
abstract static void sound();
```

**Invalid**

`abstract` and `static` cannot be combined.

---

## Final Static Interface Method

```java
static final void sound() {
}
```

**Invalid**

Interface methods cannot be `final`.

---

# 7. Interface Private Methods

Private interface methods were introduced in **Java 9**.

They must have an implementation.

```java
interface Animal {

    private void helper() {
        System.out.println("Helper");
    }
}
```

**Valid — Java 9+**

---

## Private Method Without a Body

```java
interface Animal {

    private void helper();
}
```

**Invalid**

A private interface method cannot be abstract.

---

## Private Static Method

```java
interface Animal {

    private static void helper() {
        System.out.println("Helper");
    }
}
```

**Valid — Java 9+**

---

## Private Default Method

```java
interface Animal {

    private default void helper() {
    }
}
```

**Invalid**

`private` and `default` cannot be combined.

---

# 8. Interface Protected Methods

`protected` is **not allowed** for interface methods.

All of the following are invalid:

```java
protected void sound();
```

```java
protected void sound() {
}
```

```java
protected default void sound() {
}
```

```java
protected static void sound() {
}
```

```java
protected private void sound() {
}
```

### Important Rule

> **Interface methods can never be `protected`.**

This applies regardless of whether the method is:

* abstract
* default
* static
* implemented

---

# 9. Interface Constructors

An interface does **not** have constructors.

The following are invalid:

```java
interface Animal {

    Animal() {
    }
}
```

```java
interface Animal {

    private Animal() {
    }
}
```

```java
interface Animal {

    protected Animal() {
    }
}
```

### Important Rule

> **Interfaces do not have constructors.**

Only classes have constructors.

---

# 10. Interface Fields

Interface fields are automatically:

```text
public static final
```

Therefore:

```java
interface Animal {

    int MAX = 10;
}
```

is equivalent to:

```java
interface Animal {

    public static final int MAX = 10;
}
```

Both are valid.

---

## Explicit Interface Field Modifiers

```java
interface Animal {

    public static final int MAX = 10;
}
```

**Valid**

---

## Private Interface Field

```java
interface Animal {

    private int MAX = 10;
}
```

**Invalid**

Interface fields are always `public`.

---

## Protected Interface Field

```java
interface Animal {

    protected int MAX = 10;
}
```

**Invalid**

Interface fields cannot be `protected`.

---

## Interface Field Rules

Every interface field is automatically:

```text
public
static
final
```

Therefore:

```java
interface Constants {

    int MAX = 10;
}
```

means:

```java
interface Constants {

    public static final int MAX = 10;
}
```

---

# 11. Quick Abstract Method Combinations

| Combination          | Valid? |
| -------------------- | :----: |
| `abstract`           |    ✅   |
| `public abstract`    |    ✅   |
| `protected abstract` |    ✅   |
| `private abstract`   |    ❌   |
| `static abstract`    |    ❌   |
| `final abstract`     |    ❌   |

## Why?

### `abstract`

Means:

> A subclass must provide an implementation.

### `private`

Means:

> The method is accessible only within the declaring class.

A subclass cannot override a private method.

Therefore:

```text
abstract + private
```

is invalid.

### `static`

A static method belongs to the class and is not overridden polymorphically.

Therefore:

```text
abstract + static
```

is invalid.

### `final`

A final method cannot be overridden.

Therefore:

```text
abstract + final
```

is invalid.

---

# 12. Quick Interface Method Combinations

## Plain Interface Method

```java
void method();
```

**Valid**

Equivalent to:

```java
public abstract void method();
```

---

## Public Interface Method

```java
public void method();
```

**Valid**

Equivalent to:

```java
public abstract void method();
```

---

## Explicit Public Abstract

```java
public abstract void method();
```

**Valid**

---

## Protected

```java
protected void method();
```

**Invalid**

---

## Private Without Body

```java
private void method();
```

**Invalid**

---

## Private With Body

```java
private void method() {
}
```

**Valid — Java 9+**

---

## Default With Body

```java
default void method() {
}
```

**Valid**

---

## Default Without Body

```java
default void method();
```

**Invalid**

---

## Static With Body

```java
static void method() {
}
```

**Valid**

---

## Static Without Body

```java
static void method();
```

**Invalid**

---

## Protected Default

```java
protected default void method() {
}
```

**Invalid**

---

## Private Default

```java
private default void method() {
}
```

**Invalid**

---

## Private Static

```java
private static void method() {
}
```

**Valid — Java 9+**

---

## Private Static Without Body

```java
private static void method();
```

**Invalid**

---

## Abstract Static

```java
abstract static void method();
```

**Invalid**

---

## Abstract Final

```java
abstract final void method();
```

**Invalid**

---

## Private Abstract

```java
private abstract void method();
```

**Invalid**

---

# 13. Access Modifier Rules

## Top-Level Class

| Modifier        | Allowed? |
| --------------- | :------: |
| `public`        |     ✅    |
| package-private |     ✅    |
| `protected`     |     ❌    |
| `private`       |     ❌    |
| `static`        |     ❌    |

Example:

```java
public class Animal {
}
```

or:

```java
class Animal {
}
```

---

## Nested Class

A nested class can use:

| Modifier        | Allowed? |
| --------------- | :------: |
| `public`        |     ✅    |
| `protected`     |     ✅    |
| `private`       |     ✅    |
| package-private |     ✅    |
| `static`        |     ✅    |

Example:

```java
class Outer {

    public class A {
    }

    protected class B {
    }

    private class C {
    }

    static class D {
    }
}
```

---

## Interface Methods

| Modifier        |  Allowed? |
| --------------- | :-------: |
| `public`        |     ✅     |
| `private`       | ✅ Java 9+ |
| `protected`     |     ❌     |
| package-private |     ❌     |

---

# 14. Big Memory Table

| Feature                   | Abstract Class |       Interface       |
| ------------------------- | :------------: | :-------------------: |
| Constructor               |        ✅       |           ❌           |
| Private constructor       |        ✅       |           ❌           |
| Abstract method           |        ✅       |           ✅           |
| Normal/concrete method    |        ✅       |           ❌*          |
| Default method            |       ❌**      |           ✅           |
| Static method             |        ✅       |           ✅           |
| Private method            |        ✅       |       ✅ Java 9+       |
| Protected method          |        ✅       |           ❌           |
| Protected abstract method |        ✅       |           ❌           |
| Private abstract method   |        ❌       |           ❌           |
| Static abstract method    |        ❌       |           ❌           |
| Final abstract method     |        ❌       |           ❌           |
| Interface fields          |       N/A      | `public static final` |

### Notes

* A traditional interface method cannot simply be a normal instance method with a body. Interface methods with implementations must be `default`, `static`, or `private`.

** `default` is an interface method modifier. Classes do not use `default` in this sense.

---

# 15. Most Important Rules

## Rule 1 — `abstract`

`abstract` means:

> Someone else must provide the implementation.

For a class:

```java
abstract class Animal {
}
```

For a method:

```java
abstract void sound();
```

---

## Rule 2 — `final`

`final` means:

> Cannot be overridden or extended.

Therefore:

```text
abstract + final
```

is invalid.

---

## Rule 3 — `static`

`static` means:

> Belongs to the class/interface itself rather than an individual object.

Therefore:

```text
abstract + static
```

is invalid.

---

## Rule 4 — `private`

`private` means:

> Accessible only within the declaring class/interface.

A private method cannot be overridden.

Therefore:

```text
abstract + private
```

is invalid.

---

## Rule 5 — `protected`

For classes, `protected` methods are allowed.

```java
abstract class Animal {

    protected abstract void sound();
}
```

**Valid**

For interfaces, `protected` methods are never allowed.

```java
interface Animal {

    protected void sound();
}
```

**Invalid**

---

## Rule 6 — `default` in an Interface

A `default` method provides an implementation.

```java
interface Animal {

    default void sound() {
        System.out.println("Sound");
    }
}
```

A default method **must have a body**.

---

## Rule 7 — Plain Interface Methods

```java
interface Animal {

    void sound();
}
```

Automatically means:

```java
public abstract void sound();
```

---

## Rule 8 — Interface Methods With a Body

An interface method with a body must be one of:

```text
default
static
private
```

Examples:

```java
default void sound() {
}
```

```java
static void sound() {
}
```

```java
private void helper() {
}
```

---

## Rule 9 — Protected Interface Methods

> **Never allowed.**

These are all invalid:

```java
protected void sound();
```

```java
protected default void sound() {
}
```

```java
protected static void sound() {
}
```

---

## Rule 10 — Interfaces Have No Constructors

```java
interface Animal {
}
```

There is no constructor declaration for `Animal`.

---

## Rule 11 — Abstract Classes Can Have Constructors

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal constructor");
    }
}
```

This is completely valid.

The constructor executes when a subclass object is created.

---

## Rule 12 — Abstract Classes Can Have Private Constructors

```java
abstract class Animal {

    private Animal() {
    }
}
```

This is valid.

However, subclasses cannot normally invoke a private superclass constructor.

---

## Rule 13 — Top-Level Classes

A top-level class can only have:

```text
public
```

or:

```text
package-private
```

It cannot be:

```text
private
protected
static
```

---

## Rule 14 — `abstract + final`

```text
abstract + final
```

❌ **Invalid**

Reason:

```text
abstract → must be overridden/extended
final    → cannot be overridden/extended
```

---

## Rule 15 — `abstract + static`

```text
abstract + static
```

❌ **Invalid**

Reason:

```text
abstract → requires overriding
static   → is not overridden polymorphically
```

---

## Rule 16 — `abstract + private`

```text
abstract + private
```

❌ **Invalid**

Reason:

```text
abstract → subclass must override
private  → subclass cannot override
```

---

## Rule 17 — `abstract + protected`

```text
abstract + protected
```

✅ **Valid in a class**

Example:

```java
abstract class Animal {

    protected abstract void sound();
}
```

---

# Final Quick-Revision Chart

```text
ABSTRACT CLASS
────────────────────────────────────────

abstract class Animal {}             ✅
public abstract class Animal {}      ✅
protected abstract class Animal {}   ❌ top-level
private abstract class Animal {}     ❌ top-level
static abstract class Animal {}      ❌ top-level
final abstract class Animal {}       ❌


ABSTRACT CLASS METHODS
────────────────────────────────────────

abstract void m();                   ✅
public abstract void m();            ✅
protected abstract void m();         ✅
private abstract void m();           ❌
static abstract void m();            ❌
final abstract void m();             ❌

void m() {}                          ✅
private void m() {}                  ✅
static void m() {}                   ✅
final void m() {}                    ✅


INTERFACE METHODS
────────────────────────────────────────

void m();                            ✅ public abstract
public void m();                     ✅
public abstract void m();            ✅
protected void m();                  ❌

default void m() {}                  ✅
default void m();                    ❌

static void m() {}                   ✅
static void m();                     ❌

private void m() {}                  ✅ Java 9+
private void m();                    ❌

private static void m() {}           ✅ Java 9+
private static void m();             ❌

protected default void m() {}        ❌
protected static void m() {}         ❌

abstract static void m();            ❌
abstract final void m();             ❌
private abstract void m();           ❌


CONSTRUCTORS
────────────────────────────────────────

Abstract class constructor             ✅
Private abstract-class constructor     ✅
Interface constructor                  ❌


INTERFACE FIELDS
────────────────────────────────────────

int MAX = 10;

means:

public static final int MAX = 10;


TOP-LEVEL CLASS
────────────────────────────────────────

public                         ✅
package-private                ✅
protected                      ❌
private                        ❌
static                         ❌
```

## One-Line Memory Trick

> **Class:** abstract methods can be `public` or `protected`, but not `private`, `static`, or `final`.

> **Interface:** methods are `public abstract` by default; implemented methods must be `default`, `static`, or `private`; **`protected` is never allowed**.
