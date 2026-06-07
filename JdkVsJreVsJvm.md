# JDK, JRE, and JVM Explained

## Java Ecosystem

```text
JDK
 ├── JRE
 │    ├── JVM
 │    └── Java Libraries
 │
 └── Development Tools
      ├── javac
      ├── jar
      ├── javadoc
      └── jdb
```

---

## JVM (Java Virtual Machine)

The JVM is responsible for:

* Loading classes
* Executing bytecode
* Memory management
* Garbage Collection
* Thread management

When you run:

```bash
java MyClass
```

the JVM executes the `.class` file.

Think of JVM as the **engine**.

---

## JRE (Java Runtime Environment)

JRE = JVM + Runtime Libraries

```text
JRE
 ├── JVM
 └── Java Libraries
```

The libraries include:

* String
* ArrayList
* HashMap
* LocalDate
* Files
* Stream
* Collections
* Thread

Example:

```java
String name = "Sahil";
List<Integer> nums = new ArrayList<>();
```

These classes come from Java runtime libraries.

---

## JDK (Java Development Kit)

JDK = JRE + Development Tools

```text
JDK
 ├── JRE
 │    ├── JVM
 │    └── Libraries
 │
 └── Tools
      ├── javac
      ├── jar
      ├── javadoc
      └── jdb
```

Used to develop Java applications.

---

## Example

### Compile

```bash
javac Test.java
```

Provided by:

```text
JDK
```

Output:

```text
Test.class
```

---

### Run

```bash
java Test
```

Execution:

```text
JVM
```

---

## Real Flow

```text
Test.java
    │
    ▼
javac (JDK)
    │
    ▼
Test.class
    │
    ▼
JVM (inside JRE)
    │
    ▼
Machine Code
    │
    ▼
Program Runs
```

---

## Why Java Is Platform Independent

```text
Windows JVM → Windows Machine Code

Linux JVM → Linux Machine Code

Mac JVM → Mac Machine Code
```

Same bytecode can run on different operating systems.

This is:

```text
Write Once, Run Anywhere (WORA)
```

---

## Easy Memory Trick

```text
JVM = Executes Java

JRE = JVM + Libraries
      (Run Java)

JDK = JRE + Tools
      (Develop Java)
```

---

## Final Summary

```text
JDK
│
├── JRE
│    │
│    ├── JVM
│    │
│    └── Java Libraries
│
└── Development Tools
     ├── javac
     ├── jar
     ├── javadoc
     └── jdb
```
