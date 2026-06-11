# Day 1 - Learning Journey: Understanding Threads Like an Engineer

**Student:** Sahil
**Goal:** Understand threads from first principles instead of memorizing definitions.

---

# Question 1

## Mentor Question

What will be the output and how much time will this program take?

```java
public class Main {

    public static void task() {
        try {
            Thread.sleep(5000);
            System.out.println("Task Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        task();
        task();
        task();

        System.out.println("Completed");
    }
}
```

---

## Sahil's Answer

> it will cost use 15 second the the reason is java execute code line by line from top to bottom and it is synchronous as well so each will execute one after one so it will take nearly 15 seconds.

---

## What Was Correct

✅ Java executes statements sequentially.

✅ Each `task()` call blocks the current thread.

✅ `Thread.sleep()` pauses the current thread.

✅ Total execution time is approximately 15 seconds.

---

## What Needed Clarification

Java **can** be asynchronous.

However, this particular program is synchronous because:

```java
task();
task();
task();
```

Each method call waits for the previous one to finish.

---

## Final Understanding

Execution flow:

```text
main thread
    ↓
task() → waits 5 sec
    ↓
task() → waits 5 sec
    ↓
task() → waits 5 sec
    ↓
Completed
```

Total time:

```text
5 + 5 + 5 = 15 seconds
```

---

## Why This Question Was Asked

To understand:

> Java executes sequentially unless we explicitly introduce concurrency.

---

# Question 2

## Sahil's Observation

> Java can be synchronous and asynchronous depending upon our implementation.

---

## Mentor Response

Correct.

Java itself is neither purely synchronous nor asynchronous.

The programmer decides.

Examples:

Synchronous:

```java
task1();
task2();
task3();
```

Asynchronous:

```java
new Thread(task1).start();
new Thread(task2).start();
new Thread(task3).start();
```

---

## Final Understanding

```text
Java gives tools.

Programmer decides behaviour.
```

---

# Question 3

## Mentor Question

Predict the output.

```java
public class Main {

    public static void main(String[] args) {

        Thread t = new Thread(() -> {

            try {
                Thread.sleep(5000);
                System.out.println("Worker Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        t.start();

        System.out.println("Task Completed");
    }
}
```

---

## Sahil's Answer

> option b the reason is when you start the thread then it can also take time to start and also we are doing thread.sleep so current thread which is executing that which we created by using start will block the System.out.println for the 5 seconds more so by till main thread will print task completed.

---

## What Was Correct

✅ `Thread.sleep()` blocks the worker thread.

✅ `start()` creates another thread.

✅ Main thread continues independently.

---

## What Needed Correction

This statement was incorrect:

> sleep blocks System.out.println.

Actually:

```text
Worker Thread sleeps.

Main Thread continues.
```

The main thread is NOT blocked.

---

## Final Understanding

Execution:

```text
Main Thread
    ↓
t.start()
    ↓
Print Task Completed

Worker Thread
    ↓
sleep 5 sec
    ↓
Print Worker Done
```

Expected output:

```text
Task Completed
Worker Done
```

---

## Why This Question Was Asked

To understand:

> Threads execute independently.

---

# Question 4

## Mentor Question

What is `join()`?

Predict output.

```java
Thread t = new Thread(() -> {

    try {
        Thread.sleep(5000);
        System.out.println("Worker Done");
    } catch (Exception e) {
    }

});

t.start();

t.join();

System.out.println("Main Done");
```

---

## Sahil's Answer

> bro sorry here i am not aware of what join and what will be the output but i will tell you what did i get so you create one object of Thread t1 and inside that thread you write Thread.sleep so current thread will sleep for 5 second i dont know bro what happen you tell me all.

---

## What Was Correct

✅ Correctly identified:

```text
Thread.sleep()
```

affects the worker thread.

---

## What Was Missing

Understanding of:

```java
join()
```

---

## Mentor Explanation

`join()` means:

```text
Main Thread waits until the worker thread finishes.
```

---

## Final Understanding

Flow:

```text
Main Thread
    ↓
start worker
    ↓
join()
    ↓ waits here

Worker Thread
    ↓
sleep 5 sec
    ↓
Worker Done

Main Thread resumes
    ↓
Main Done
```

Output:

```text
Worker Done
Main Done
```

---

## Why This Question Was Asked

To introduce:

> Coordination between threads.

---

# Question 5

## Mentor Question

Without using join.

Can we guarantee output order?

---

## Sahil's Answer

> we cannot guarantee output it is on JVM which thread get finish first that method will run first but i think everytime Main finish will be first.

---

## What Was Correct

✅ Output order cannot be guaranteed.

---

## What Needed Refinement

Not because of JVM randomness.

But because:

```text
Thread Scheduling
```

is controlled by the operating system.

---

## Final Understanding

Without synchronization:

```text
Execution order is unpredictable.
```

---

## Why This Question Was Asked

To understand:

> Concurrency introduces nondeterminism.

---

# Question 6

## Mentor Question

Why does Main usually finish first?

---

## Sahil's Answer

> yes bro main will finish why not because start creates another thread right but main is different thread also then main is the first thread which runs after started java application i guess.

---

## What Was Correct

✅ Main thread already exists.

✅ start() creates another thread.

✅ Main thread continues execution.

---

## What Needed Clarification

Main finishes first because:

```text
It has less work.
```

Worker thread:

```text
sleep 5 seconds
```

Main thread:

```text
just print.
```

---

## Final Understanding

```text
JVM
 ↓
creates Main Thread
 ↓
Main Thread starts Worker Thread
 ↓
Main continues
 ↓
Worker works independently
```

---

## Why This Question Was Asked

To understand:

> Thread creation does NOT stop the main thread.

---

# Biggest Learning of Today

Before today I thought:

```text
Thread = some magical Java feature.
```

Now I understand:

```text
A thread is simply an independent path of execution.
```

---

# Mental Model Built Today

```text
JVM Starts
    ↓
Creates Main Thread
    ↓
Main executes code line by line
    ↓
start()
    ↓
JVM creates another thread
    ↓
Both threads run independently
    ↓
sleep() blocks current thread only
    ↓
join() makes one thread wait for another
```

---

# Things Sahil Understood Well Today

✅ Sequential execution.

✅ Main thread exists first.

✅ start() creates another thread.

✅ sleep() blocks the thread executing it.

✅ Threads execute independently.

---

# Things To Revise Tomorrow

🔄 Difference between `run()` and `start()`.

🔄 Thread lifecycle.

🔄 `wait()` vs `sleep()`.

🔄 `notify()`.

🔄 Producer Consumer problem.

🔄 How backend listener threads work.

---

# Final Reflection

Today I stopped memorizing thread definitions.

I started reasoning:

* Which thread executes this?
* Who created this thread?
* Is this thread blocked?
* What happens to the main thread?
* Who waits for whom?

This shift from syntax to reasoning is what engineers use while debugging real production systems.
