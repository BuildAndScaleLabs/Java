# LRU (Least Recently Used) Cache in Java

## What is an LRU Cache?

An **LRU (Least Recently Used) Cache** is a data structure that stores a **fixed number of key-value pairs**. When the cache reaches its maximum capacity and a new item needs to be inserted, it automatically removes the **least recently used** item.

The basic idea is simple:

> **Recently used data is more likely to be used again, so keep it in memory.**

This strategy improves performance by reducing expensive operations such as database queries, API calls, or disk reads.

---

# Real-World Example

Imagine you have a browser.

Suppose your browser cache can only store **3 webpages**.

```
Capacity = 3

Visit:
Google
YouTube
GitHub

Cache:
[Google, YouTube, GitHub]
```

Now you open **Google** again.

Google becomes the **Most Recently Used (MRU)** page.

```
Cache Order:

GitHub -> YouTube -> Google
^                     ^
LRU                  MRU
```

Now you visit **ChatGPT**.

The cache is already full.

Which page should be removed?

The least recently used one:

```
GitHub
```

New cache:

```
YouTube -> Google -> ChatGPT
```

---

# Why Do We Need an LRU Cache?

Without caching:

```
Application
      |
      v
Database
```

Every request hits the database.

With caching:

```
Application
      |
      v
  LRU Cache
      |
      v
Database (only if data is absent)
```

Benefits:

- Faster response time
- Reduced database load
- Lower network latency
- Better scalability
- Lower infrastructure cost

---

# Common Use Cases

- Browser cache
- Redis cache
- API response caching
- Database query caching
- DNS caching
- Image caching
- CDN edge caching
- JVM object caching

---

# Time Complexity

| Operation | Time Complexity |
|-----------|-----------------|
| get() | O(1) |
| put() | O(1) |
| remove() | O(1) |

The O(1) complexity is achieved using:

- **HashMap** → Fast lookup
- **Doubly Linked List** → Fast insertion/removal

---

# LRU Cache Operations

An LRU Cache supports two primary operations.

## 1. get(key)

Retrieves the value associated with the given key.

If the key exists:

- Return the value
- Move the item to the **Most Recently Used (MRU)** position

If the key does not exist:

Return `null` (or `-1` in some implementations).

Example:

```
Cache:

1 -> One
2 -> Two

get(1)

Returns:
One

New Order:

2 -> 1
```

---

## 2. put(key, value)

Adds a new key-value pair.

If the key already exists:

- Update its value
- Move it to the MRU position

If the cache is full:

- Remove the LRU item
- Insert the new item

Example:

```
Capacity = 2

put(1, One)

Cache:

1

put(2, Two)

Cache:

1 2

put(3, Three)

Cache becomes full.

Remove LRU:

1

New Cache:

2 3
```

---

# LRU Cache Implementation Using LinkedHashMap

Java provides an excellent implementation through **LinkedHashMap**.

A normal `HashMap` does **not** maintain order.

A `LinkedHashMap` maintains insertion order by default.

When created with:

```java
accessOrder = true
```

it maintains **access order**, which is exactly what an LRU cache needs.

---

# Java Implementation

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    // Maximum number of entries the cache can hold
    private final int capacity;

    public LRUCache(int capacity) {

        /*
            LinkedHashMap Constructor

            initialCapacity = capacity
            loadFactor      = 0.75
            accessOrder     = true

            accessOrder = true means every successful get() or put()
            moves that entry to the end of the linked list,
            making it the Most Recently Used (MRU) item.
        */

        super(capacity, 0.75f, true);

        this.capacity = capacity;
    }

    /*
        This method is automatically called after every put().

        If it returns true,
        LinkedHashMap removes the eldest entry.

        Here we remove the least recently used entry whenever
        cache size exceeds its capacity.
    */

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {

        LRUCache<Integer, String> cache = new LRUCache<>(2);

        cache.put(1, "One");
        cache.put(2, "Two");

        System.out.println(cache);

        System.out.println(cache.get(1));

        // Accessing key 1 moves it to the MRU position

        System.out.println(cache);

        cache.put(3, "Three");

        // Capacity exceeded.
        // Key 2 is removed because it is the LRU entry.

        System.out.println(cache);

        System.out.println(cache.get(2));
    }
}
```

---

# Execution Walkthrough

## Step 1

```java
cache.put(1, "One");
```

```
Cache:

1
```

---

## Step 2

```java
cache.put(2, "Two");
```

```
Cache:

1 -> 2

LRU        MRU
```

---

## Step 3

```java
cache.get(1);
```

Accessing key **1** makes it the most recently used.

```
Cache:

2 -> 1

LRU        MRU
```

---

## Step 4

```java
cache.put(3, "Three");
```

Capacity exceeded.

Remove the least recently used item.

```
Removed:

2
```

New cache:

```
1 -> 3

LRU      MRU
```

---

## Step 5

```java
cache.get(2);
```

Output:

```
null
```

because key **2** was evicted.

---

# Understanding `accessOrder = true`

The third constructor parameter of `LinkedHashMap` determines how entries are ordered.

```java
new LinkedHashMap<>(capacity, 0.75f, true);
```

If `false`:

```
Insertion Order

1
2
3

Always remains:

1
2
3
```

If `true`:

```
Insert:

1
2
3

Access:

get(1)

Order becomes:

2
3
1
```

This behavior makes `LinkedHashMap` suitable for implementing an LRU cache.

---

# Understanding `removeEldestEntry()`

This method is automatically invoked after every `put()` operation.

```java
@Override
protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > capacity;
}
```

If it returns:

```java
true
```

the oldest entry (which is also the least recently used when `accessOrder = true`) is removed automatically.

---

# Advantages

- Very simple implementation
- O(1) average time complexity
- Uses Java's built-in data structure
- Automatically handles eviction
- Generic implementation (`K`, `V`)

---

# Limitations

- Not thread-safe
- Suitable only for single-threaded use unless externally synchronized
- Stores everything in memory
- Eviction policy is fixed to LRU

---

# How Is LRU Implemented Internally?

A production-quality LRU cache is typically built using:

```
                HashMap
                   |
                   |
         key -----> Node
                     |
                     v
         Doubly Linked List
```

The **HashMap** provides **O(1)** lookup by key.

The **Doubly Linked List** maintains the usage order.

```
HEAD
 |
 v

A <-> B <-> C <-> D

^

Least Recently Used


Most Recently Used

                     ^
                     |
                    TAIL
```

When an item is accessed:

1. Find it using the HashMap.
2. Remove it from its current position in the linked list.
3. Move it to the tail (MRU).

When inserting into a full cache:

1. Remove the node next to the head (LRU).
2. Delete it from the HashMap.
3. Insert the new node at the tail.

This is how libraries like **Caffeine**, **Guava Cache**, and many operating systems implement efficient LRU behavior (often with additional optimizations).

---

# Summary

- LRU stands for **Least Recently Used**.
- It stores only a fixed number of entries.
- Recently accessed entries are kept in the cache.
- The least recently used entry is removed when the cache is full.
- Java's `LinkedHashMap` can implement an LRU cache easily using:
  - `accessOrder = true`
  - `removeEldestEntry()`
- `get()` and `put()` operations have **O(1)** average time complexity.
- LRU caches are widely used in browsers, databases, Redis, APIs, operating systems, and distributed systems to improve performance.
