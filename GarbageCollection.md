# Java Garbage Collection — Complete Interview Notes

## 1. What is Garbage Collection?

Garbage Collection (GC) is the JVM's automatic process for reclaiming **heap memory** occupied by objects that are no longer reachable by the running application.

Example:

```java
public void test() {
    Student s = new Student();
}
```

While the method runs:

```text
Stack
┌─────────────────┐
│ test() frame    │
│ s ──────────────┼──────► Student object
└─────────────────┘       Heap
```

After `test()` returns, the stack frame disappears. If nothing else references the `Student`, the object becomes unreachable and **eligible for GC**.

Important:

> Eligible for GC does NOT mean immediately deleted. The JVM decides when and how to reclaim it.

---

## 2. Why Do We Need GC?

Java normally does not require programmers to manually free objects.

GC:
- Automatically reclaims unused heap memory.
- Reduces many manual-memory-management bugs.
- Lets the JVM optimize memory reclamation.

GC is not free: it consumes CPU and some GC operations can pause application threads.

---

## 3. What Memory Does GC Manage?

For normal Java application understanding:

> **GC primarily manages Java Heap memory.**

The heap contains objects and arrays.

```java
Student s = new Student();
int[] numbers = new int[100];
```

Conceptually:

```text
Stack                         Heap

s ───────────────────────► Student object
numbers ─────────────────► int[100]
```

The references are local variables in a stack frame; the objects are in the heap.

A stack frame disappears when its method returns. GC is not what destroys normal stack frames.

---

# 4. Object Lifecycle

```text
new Object()
     ↓
Heap allocation
     ↓
Object is reachable
     ↓
Application stops referencing it
     ↓
Object becomes unreachable
     ↓
Eligible for GC
     ↓
GC eventually reclaims its memory
```

The exact timing depends on the JVM and garbage collector.

---

# 5. Reachable vs Unreachable

The core GC question is:

> **Can this object still be reached from a GC Root?**

If yes → keep it.

If no → it is eligible for reclamation.

GC does not simply ask whether a particular variable is `null`.

---

# 6. GC Roots

GC Roots are starting points used to determine reachability.

Common examples:
- References in active stack frames
- Static references
- Active Java threads
- Certain JNI/native references

Example:

```java
Student s = new Student();
```

```text
GC Root
   ↓
   s
   ↓
Student Object
```

The object is reachable.

After:

```java
s = null;
```

if no other path exists:

```text
GC Root

s = null

Student Object
(no path from a GC Root)
```

The object becomes eligible for GC.

---

# 7. Reachability Through Other Objects

Objects can be reachable indirectly.

```java
class Department {
    Employee employee;
}

Department d = new Department();
d.employee = new Employee();
```

Conceptually:

```text
GC Root
   ↓
Department
   ↓
Employee
```

If the root reference to `Department` disappears and no other root can reach either object, both can become unreachable.

---

# 8. Why Most Objects Die Young

A major observation behind generational GC is:

> **Most objects are short-lived.**

For example, request-processing code often creates temporary objects that are no longer needed after the operation.

Other objects can live much longer:
- Spring singleton beans
- ApplicationContext
- Connection pools
- Caches
- Configuration objects

Generational GC takes advantage of this difference in object lifetime.

---

# 9. Young Generation

In a traditional generational model, newly created objects start in the Young Generation.

```text
Young Generation
┌─────────┬────────────┬────────────┐
│  Eden   │ Survivor 0 │ Survivor 1 │
└─────────┴────────────┴────────────┘
```

The exact implementation depends on the selected garbage collector.

---

# 10. Eden

New objects are generally allocated in Eden in generational collectors.

```java
Student s = new Student();
```

Conceptually:

```text
Eden
┌────────────────────┐
│ Student object     │
└────────────────────┘
```

Many temporary objects become garbage here.

---

# 11. Survivor Spaces S0 and S1

Traditionally, young generation has two Survivor spaces.

Objects that survive young collections can move between them.

Conceptually:

```text
Eden
  ↓ survives
S0
  ↓ survives
S1
  ↓ survives
S0
```

If an object survives enough collections, it can be promoted to Old Generation.

You do not need to memorize the exact copying algorithm for a normal Spring Boot interview.

Remember:

> New objects → Young area → surviving objects may move through Survivor spaces → long-lived objects can be promoted to Old Generation.

---

# 12. Object Age and Promotion

An object that survives collections becomes older conceptually.

```text
Object
  ↓
Age increases
  ↓
Survives more young collections
  ↓
Promotion
  ↓
Old Generation
```

The exact promotion threshold is JVM/collector dependent. Do not treat a number such as 15 as a universal rule.

---

# 13. Old Generation (Tenured)

Old Generation contains long-lived objects.

Examples in Spring Boot may include:
- Singleton beans
- ApplicationContext
- Caches
- Connection pools
- Long-lived configuration objects

Conceptually:

```text
Young Generation
      ↓
long-lived objects
      ↓
Old Generation
```

Promotion is based on JVM/collector behavior, not simply on whether an object is "important."

---

# 14. Metaspace

Metaspace is separate from the ordinary Java heap.

It stores class-related metadata.

It replaced PermGen starting with Java 8.

For interviews:

```text
Heap       → Objects / Arrays
Metaspace  → Class metadata
```

Metaspace uses native memory rather than being part of the normal Java heap.

---

# 15. Minor GC

**Minor GC** traditionally means a collection focused on the Young Generation.

It deals mainly with:

```text
Eden
+
Survivor spaces
```

Example:

```text
Young Generation

Eden
Student A ← garbage
Student B ← alive
Student C ← garbage
```

A young collection can reclaim unreachable objects and handle surviving objects.

### Why is Minor GC usually fast?

- Young Generation is relatively smaller.
- Many young objects are short-lived.
- A large proportion may already be unreachable.

### Interview definition

> **Minor GC is a collection focused on the Young Generation. It happens relatively frequently and is generally faster than collections involving the Old Generation.**

---

# 16. Major GC

"Major GC" is commonly used to describe collection involving the Old Generation.

Conceptually:

```text
Old Generation

long-lived object
long-lived object
garbage
long-lived object
```

It can be more expensive because the Old Generation contains more long-lived objects.

### Interview definition

> **Major GC generally refers to collection of the Old Generation. It is usually less frequent and can be more expensive than a young-generation collection.**

Terminology can vary between JVM collectors. Focus on the Young-vs-Old concept rather than arguing over labels.

---

# 17. Full GC

A Full GC generally means a broad collection involving the heap, often including both Young and Old generations.

```text
Entire Heap

Young Generation
       +
Old Generation
```

It is generally expensive and can produce longer pauses.

### Interview definition

> **Full GC is a broad heap collection that can involve both Young and Old generations. It is generally expensive and is something production systems try to avoid unnecessarily.**

The exact meaning and behavior depend on the collector.

---

# 18. Stop-The-World (STW)

Some GC operations require application threads to pause.

```text
Application threads
        ↓
      PAUSE
        ↓
     GC work
        ↓
Application continues
```

This is called **Stop-The-World**.

Modern collectors try to keep important pauses short.

Not every part of every modern GC cycle has to stop the application.

---

# 19. Concurrent GC

Concurrent GC performs some work while application threads continue running.

```text
Application ─────────────────►
GC           ─────────────────►
             concurrent work
```

This can reduce pauses, but GC still consumes CPU resources.

---

# 20. How Does GC Find Garbage?

The core idea is **reachability**.

Conceptually:

```text
GC Roots
  │
  ├──► Object A ───► Object B
  │
  └──► Object C

Object D
Object E
```

A, B and C are reachable.

D and E have no path from a GC Root.

Therefore:

```text
A → Keep
B → Keep
C → Keep
D → Eligible for reclamation
E → Eligible for reclamation
```

The collector then uses its own techniques to reclaim/reorganize memory.

---

# 21. What Happens to Reclaimed Memory?

The reclaimed space becomes available for future allocations.

Some collectors may also move surviving objects to reduce fragmentation.

Conceptually:

```text
Before:
[Live][Garbage][Live][Garbage][Live]

After:
[Live][Live][Live][Free][Free]
```

Exact behavior depends on the collector.

---

# 22. Garbage Collectors

A garbage collector is the JVM's strategy/component for performing collection.

Collectors make different trade-offs between:
- Throughput
- Latency
- Pause time
- CPU usage
- Heap size

---

# 23. Serial GC

Serial GC uses a single GC thread for collection work.

Conceptually:

```text
Application
     ↓
   PAUSE
     ↓
One GC thread
     ↓
Collection
     ↓
Application resumes
```

Good for:
- Small applications
- Small heaps
- Simple environments

---

# 24. Parallel GC

Parallel GC uses multiple GC threads.

```text
GC Thread 1 ─┐
GC Thread 2 ─┼─► GC work
GC Thread 3 ─┤
GC Thread 4 ─┘
```

Its main goal is high throughput.

It can still have Stop-The-World pauses.

Good for:
- Batch processing
- Throughput-oriented applications

---

# 25. G1 GC

G1 means **Garbage-First Garbage Collector**.

G1 is the default garbage collector in modern JDKs. Oracle documents G1 as the default starting with JDK 9 and in current JDK releases.

Instead of treating the heap as only two large blocks, G1 divides it into many regions.

```text
Heap

┌───┬───┬───┬───┬───┐
│ R │ R │ R │ R │ R │
├───┼───┼───┼───┼───┤
│ R │ R │ R │ R │ R │
├───┼───┼───┼───┼───┤
│ R │ R │ R │ R │ R │
└───┴───┴───┴───┴───┘
```

G1 can perform parts of its work concurrently and is designed to balance throughput with pause-time goals.

### Interview definition

> **G1 is a mostly concurrent, region-based garbage collector designed to balance throughput and predictable pause times. It is the default collector in modern JDKs.**

For a normal Spring Boot interview, this is enough.

---

# 26. ZGC

ZGC is designed for very low pause times, including large heaps.

Much of its work is performed concurrently with application execution.

Good for:
- Large heaps
- Latency-sensitive systems
- Applications where long pauses are unacceptable

Interview definition:

> **ZGC is a low-latency garbage collector designed to keep pause times extremely small, even for large heaps.**

---

# 27. Shenandoah GC

Shenandoah is another low-pause garbage collector.

Its goal is to perform much of the collection work concurrently with the application.

Good for:
- Low-latency applications
- Large server workloads
- Systems where pause time matters heavily

Interview definition:

> **Shenandoah is a concurrent, low-pause garbage collector designed to keep application pauses very small.**

---

# 28. Collector Comparison

| Collector | Main Focus | Key Point |
|---|---|---|
| Serial | Simplicity | Single GC thread |
| Parallel | Throughput | Multiple GC threads |
| G1 | Balance | Region-based, mostly concurrent |
| ZGC | Very low latency | Highly concurrent |
| Shenandoah | Very low latency | Concurrent, low-pause |

There is no universally "best" collector. The right choice depends on the workload.

---

# 29. `System.gc()`

You may see:

```java
System.gc();
```

It is only a request/suggestion to the JVM to make an effort to perform GC.

It does NOT mean:

> "Run GC immediately and delete this object."

The JVM is not required to reclaim a particular object or amount of memory because of this call.

Do not use `System.gc()` as normal application memory-management logic.

---

# 30. `finalize()` — What Was It?

`finalize()` is a method inherited from `java.lang.Object`.

Historically, developers could override it:

```java
class Student {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Cleanup");
    }
}
```

The original idea was:

> When an object became unreachable and was going to have its memory reclaimed, give it a last opportunity to perform cleanup.

This approach has serious problems.

---

# 31. Where Did `finalize()` Fit Into GC?

The old conceptual model was:

```text
Object created
     ↓
Object reachable
     ↓
No longer reachable
     ↓
Eligible for GC
     ↓
finalize() could be invoked
     ↓
Memory eventually reclaimed
```

The timing of finalization was not something application code could safely rely on.

---

# 32. Why Was `finalize()` Bad?

## 1. Unpredictable timing

You cannot reliably know when it will execute.

It might happen much later, and modern runtimes can disable finalization.

Therefore it is unsuitable for important resource cleanup.

## 2. Performance and reliability problems

Finalization complicates memory management and can delay reclamation.

## 3. Object resurrection

An object can potentially make itself reachable again from `finalize()`.

```java
class Student {

    static Student saved;

    @Override
    protected void finalize() {
        saved = this;
    }
}
```

Conceptually:

```text
Student becomes unreachable
        ↓
finalize()
        ↓
saved = this
        ↓
Object becomes reachable again
```

This is called **object resurrection**.

---

# 33. Is `finalize()` Deprecated?

Yes.

`Object.finalize()` was deprecated in **Java 9** and is marked **deprecated for removal** in current Java documentation.

Modern Java documentation recommends alternatives such as:
- Explicit `close()`
- `AutoCloseable`
- try-with-resources
- `Cleaner`
- `PhantomReference`

Finalization can also be disabled in modern JVMs as Java moves toward eventual removal.

Therefore:

> **Do not use `finalize()` in new code.**

---

# 34. What Should Replace `finalize()`?

For files, streams, sockets, database resources, etc., use deterministic cleanup.

## try-with-resources

```java
try (FileInputStream input =
         new FileInputStream("data.txt")) {

    // use resource

}
```

The resource is automatically closed when execution leaves the try block.

---

# 35. `AutoCloseable`

A resource can implement:

```java
AutoCloseable
```

Example:

```java
class MyResource implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("Resource closed");
    }
}
```

Then:

```java
try (MyResource resource = new MyResource()) {

    // use resource

}
```

Java automatically calls `close()`.

This is deterministic cleanup.

---

# 36. `Cleaner`

Modern Java also provides `Cleaner` for specialized cleanup scenarios.

It is safer than relying on `finalize()`, but it should not replace deterministic resource management when explicit closing is possible.

For ordinary resource handling:

```text
AutoCloseable
      +
try-with-resources
```

is usually the preferred approach.

---

# 37. GC vs Resource Cleanup

This distinction is extremely important.

### Garbage Collection

Responsible for:

> Reclaiming heap memory occupied by unreachable objects.

### Resource Cleanup

Responsible for:

> Releasing external resources such as files, sockets, database connections and streams.

Do NOT think:

```text
GC = close database connection
```

Use explicit resource-management mechanisms.

---

# 38. Spring Boot and GC

Example:

```java
@Service
public class UserService {
}
```

Spring normally creates a singleton bean and keeps it referenced through the ApplicationContext.

Conceptually:

```text
Live JVM references
       ↓
ApplicationContext
       ↓
UserService
```

As long as the object remains reachable, GC does not reclaim it.

Temporary request objects can become eligible after the request and after all relevant references disappear.

---

# 39. Does GC Run When a Method Ends?

No.

```java
void test() {
    Student s = new Student();
}
```

When `test()` returns:
- The `test()` stack frame disappears.
- `s` disappears.
- The Student object may become unreachable.

But GC does NOT automatically run just because the method ended.

The object becomes **eligible** for GC.

The JVM decides when to collect it.

---

# 40. Does `null` Immediately Delete an Object?

No.

```java
Student s = new Student();
s = null;
```

This may make the object unreachable.

Correct:

```text
No reference
   ↓
Eligible for GC
```

Incorrect:

```text
No reference
   ↓
Immediately deleted
```

---

# 41. `null` Does Not Always Make an Object Garbage

```java
Student s1 = new Student();
Student s2 = s1;

s1 = null;
```

There is still:

```text
s2 ─────► Student Object
```

So the object is still reachable.

It is not garbage.

---

# 42. Circular References

```java
class A {
    B b;
}

class B {
    A a;
}
```

Suppose:

```java
A a = new A();
B b = new B();

a.b = b;
b.a = a;
```

Conceptually:

```text
GC Root
   ↓
   A ─────► B
   ▲        │
   └────────┘
```

If the root reference disappears:

```text
GC Root
   X

A ◄────► B
```

A and B can still become garbage because there is no path from a GC Root.

Therefore:

> Modern GC is based on reachability, not simply reference counting.

---

# 43. Java Can Still Have Memory Leaks

Automatic GC does not mean memory leaks are impossible.

Example:

```java
static List<Student> cache = new ArrayList<>();

public void add(Student s) {
    cache.add(s);
}
```

If this cache grows forever:

```text
GC Root
   ↓
static cache
   ↓
Student
Student
Student
Student
...
```

Those objects are still reachable.

GC cannot reclaim them.

Possible result:

```text
Heap fills
   ↓
GC works harder
   ↓
Memory pressure
   ↓
Possible OutOfMemoryError
```

A Java memory leak often means:

> Objects are still reachable even though the application no longer logically needs them.

---

# 44. OutOfMemoryError and GC

If the application keeps allocating objects and the JVM cannot reclaim enough memory:

```text
Allocate objects
      ↓
Heap gets full
      ↓
GC attempts reclamation
      ↓
Not enough memory recovered
      ↓
Allocation still fails
      ↓
OutOfMemoryError
```

Possible causes include:
- Memory leaks
- Excessive caching
- Large object allocations
- Incorrect heap sizing
- Genuine high memory requirements

GC cannot create unlimited memory.

---

# 45. Complete GC Flow

```text
                 new Object()
                      │
                      ▼
                    Heap
                      │
                      ▼
              Object is reachable
                      │
             ┌────────┴────────┐
             │                 │
          Still used       No longer used
             │                 │
             ▼                 ▼
           KEEP          Unreachable
                               │
                               ▼
                         Eligible for GC
                               │
                               ▼
                         GC identifies it
                               │
                               ▼
                     Memory is reclaimed
                               │
                               ▼
                    Space available again
```

Generational flow:

```text
New Object
    ↓
  Eden
    ↓
Young collection
    ↓
 ┌──┴──────────────┐
 │                 │
Dead             Survives
 │                 │
Reclaimed       Survivor
                   ↓
             More young GCs
                   ↓
             Old Generation
                   ↓
          Eventually unreachable
                   ↓
        Old/broad collection
                   ↓
          Memory reclaimed
```

---

# 46. Minor vs Major vs Full GC

| Type | Main Area | Frequency | Typical Cost |
|---|---|---|---|
| Minor GC | Young Generation | More frequent | Usually lower |
| Major GC | Old Generation | Less frequent | Usually higher |
| Full GC | Broad/whole heap | Ideally less frequent | Usually highest |

Terminology can vary between collectors, so focus on the conceptual Young-vs-Old distinction.

---

# 47. Interview Questions and Answers

### Q1. What is Garbage Collection?

> Garbage Collection is the JVM's automatic process of reclaiming heap memory occupied by objects that are no longer reachable.

### Q2. Does GC clean stack memory?

> No. Stack frames are associated with method calls and disappear when methods return. GC primarily manages heap objects.

### Q3. When does an object become eligible for GC?

> When it is no longer reachable from any GC Root.

### Q4. Does `null` immediately delete an object?

> No. It may make the object unreachable, making it eligible for GC. The JVM decides when to reclaim it.

### Q5. What are GC Roots?

> Starting points for reachability analysis, such as active stack references, static references, active threads, and certain native/JNI references.

### Q6. Why do we have Young and Old generations?

> Because most objects are short-lived while fewer objects survive for a long time. Generational collection uses this behavior to make reclamation more efficient.

### Q7. What is Minor GC?

> A collection focused on the Young Generation.

### Q8. What is Major GC?

> Commonly, a collection involving the Old Generation.

### Q9. What is Full GC?

> A broad collection involving the heap, commonly including Young and Old areas depending on the collector.

### Q10. What is Stop-The-World?

> A period during which application threads are paused for particular JVM/GC operations.

### Q11. What is G1 GC?

> A mostly concurrent, region-based collector designed to balance throughput and predictable pause times. It is the default collector in modern JDKs.

### Q12. What is `finalize()`?

> An old Object cleanup mechanism associated with finalization of unreachable objects. It is deprecated and subject to removal and should not be used for new code.

### Q13. What should replace `finalize()`?

> Prefer `AutoCloseable` with try-with-resources for deterministic resource cleanup. `Cleaner` or `PhantomReference` are options for specialized cases.

---

# 48. Final Interview Cheat Sheet

## Memory

- Stack → one per thread
- Stack Frame → one per method call
- Heap → objects and arrays
- Metaspace → class metadata

## GC

- GC → automatic heap memory reclamation
- GC Roots → starting points for reachability
- Reachable → keep
- Unreachable → eligible for reclamation
- Eligible ≠ immediately deleted

## Generations

- Eden → new objects
- S0/S1 → surviving young objects in the traditional generational model
- Old → long-lived objects
- Minor GC → Young Generation
- Major GC → commonly Old Generation
- Full GC → broad/whole-heap collection

## Collectors

- Serial → simple/single-threaded
- Parallel → throughput
- G1 → balanced latency + throughput; modern default
- ZGC → very low latency
- Shenandoah → very low latency

## Finalization

- `finalize()` → old cleanup mechanism
- Deprecated since Java 9
- Deprecated for removal
- Do not use for new code
- Prefer `AutoCloseable` + try-with-resources
- `Cleaner`/`PhantomReference` → specialized alternatives

## Golden Rule

> **GC manages memory. It does not manage the logical lifecycle of your application's external resources.**

Use GC for heap-memory reclamation.

Use explicit resource management for files, sockets, database connections, streams, and similar resources.
