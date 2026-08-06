# Java References, `this`, Constructor Injection & Spring `@Autowired`

> **Goal:** Understand *why* we write `this.engine = engine` and what Spring actually does behind the scenes.

---

# 1. First Rule: Variables Don't Store Objects

When you write

```java
Engine engine = new Engine();
```

Java creates an object in the Heap.

```
Stack                           Heap

engine -----------------------> Engine Object
```

The variable `engine` **does not contain the object**.

It only contains a **reference** (think of it like a house address).

---

# 2. Passing Object to Constructor

Suppose we have

```java
class Car {

    Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}
```

Now create objects.

```java
Engine e = new Engine();

Car car = new Car(e);
```

Let's see what Java does.

---

## Step 1

```java
Engine e = new Engine();
```

Memory

```
Stack                           Heap

e ----------------------------> Engine Object
```

---

## Step 2

Java calls

```java
new Car(e);
```

Remember:

Java is **Pass By Value**.

It copies the **reference**, not the object.

Now

```
Stack

e -----------------------------> Engine Object

constructor parameter ----------> Engine Object
```

Notice

There is still only ONE Engine object.

There are TWO references.

---

# 3. Why do we write

```java
this.engine = engine;
```

This was my biggest confusion.

Inside constructor there are TWO variables named `engine`.

```java
class Car {

    Engine engine;              // Instance Variable

    Car(Engine engine) {        // Constructor Parameter

        this.engine = engine;

    }
}
```

`this.engine`

means

> Engine field inside the current Car object.

The right side

```java
engine
```

means

> Constructor parameter.

So

```java
this.engine = engine;
```

means

> Copy the constructor parameter's reference into the current object's field.

Memory becomes

```
Stack

e -----------------------------> Engine Object

constructor parameter ----------> Engine Object


Heap

Car Object

engine -------------------------> Engine Object
```

---

# 4. Why Do We Copy the Reference?

Very important.

Constructor parameter exists ONLY while constructor runs.

```
Constructor Starts

↓

Parameter Exists

↓

Constructor Ends

↓

Parameter Dies ❌
```

Suppose we didn't store it.

```java
class Car {

    Car(Engine engine){

    }

    void start(){

        engine.start(); // ERROR

    }

}
```

Why?

Because

`engine`

no longer exists.

It died with constructor.

That's why we save it.

```java
this.engine = engine;
```

Now Car remembers the Engine forever.

---

# 5. Real Life Example

Suppose someone tells you a phone number.

```
9876543210
```

If you don't save it,

after conversation,

you forget it.

So you save it.

```
Contacts

Rahul -> 9876543210
```

Now you can call Rahul anytime.

Constructor Parameter = Someone telling you the number.

Instance Variable = Saving it in contacts.

Exactly same thing.

---

# 6. Who Calls start()?

Suppose

```java
Engine e = new Engine();

Car car = new Car(e);

car.start();
```

Execution Flow

```
new Engine()

↓

new Car(e)

↓

Constructor Executes

↓

this.engine = engine

↓

Constructor Ends

↓

car.start()
```

Now Java executes

```java
engine.start();
```

Which engine?

The one stored inside

```
Car Object

engine ---------------------> Engine Object
```

That's why

```java
car.start();
```

works even though constructor already finished.

---

# 7. Multiple References

```
Engine e = new Engine();

Car car = new Car(e);
```

Memory

```
e ----------------------+

                         |

                         V

                 Engine Object

                         ^

                         |

car.engine --------------+
```

Two references.

One object.

---

# 8. Modify Through One Reference

Suppose

```java
class Engine {

    int speed = 100;

}
```

Now

```java
car.engine.speed = 200;
```

Then

```java
System.out.println(e.speed);
```

Output

```
200
```

Why?

Because

Both references point to SAME object.

You modified the object.

Not the reference.

---

# 9. Change the Reference

Suppose

```java
car.engine = new DieselEngine();
```

Now

```
e ------------------------> PetrolEngine

car.engine ----------------> DieselEngine
```

Only

```
car.engine
```

changed.

The original reference

```
e
```

still points to PetrolEngine.

---

# 10. Manual Dependency Injection

Without Spring

WE create objects.

```java
class Main {

    public static void main(String[] args){

        Engine engine = new Engine();

        Car car = new Car(engine);

        car.start();

    }

}
```

We are responsible for

- Creating Engine
- Creating Car
- Passing Engine

Everything is manual.

---

# 11. Constructor Injection

```java
class Car {

    private Engine engine;

    Car(Engine engine){

        this.engine = engine;

    }

}
```

This constructor is NOT creating Engine.

It is saying

> "If someone wants to create me,
> they must provide an Engine."

Think of it as a CONTRACT.

---

# 12. Spring Boot

Suppose

```java
@Component
class Engine {

}
```

```java
@Service
class Car {

    private Engine engine;

    Car(Engine engine){

        this.engine = engine;

    }

}
```

Do we write

```java
Engine e = new Engine();

Car car = new Car(e);
```

NO.

Spring does it.

Internally think of Spring like

```java
Engine e = new Engine();

Car car = new Car(e);
```

Spring writes this code for us.

---

# 13. What Does @Autowired Actually Do?

Suppose

```java
@Service
class BookingService {

    @Autowired
    private UserRepository repository;

}
```

Conceptually Spring does

```java
BookingService service = new BookingService();

service.repository = repositoryBean;
```

Spring injects the reference.

---

Suppose Constructor Injection

```java
@Service
class BookingService {

    private final UserRepository repository;

    BookingService(UserRepository repository){

        this.repository = repository;

    }

}
```

Spring internally does

```java
UserRepository repo = new UserRepositoryImpl();

BookingService service = new BookingService(repo);
```

Notice

The constructor is still plain Java.

Spring only supplies the object.

---

# 14. Complete Spring Flow

```
Browser

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

Repository

↓

Service

↓

Controller

↓

JSON Response
```

Controller stores reference of Service.

Service stores reference of Repository.

Repository talks to Database.

Every object only knows the next object.

---

# 15. Biggest Realization

Originally I thought

```java
this.engine = engine;
```

was just syntax.

Now I understand

It is saving the dependency's reference inside the object.

Why?

Because constructor parameters die.

Instance variables live as long as the object lives.

Without saving the reference,

the object would forget its dependency.

---

# Golden Rules

✅ Objects live in Heap.

✅ References point to objects.

✅ Java copies references, not objects.

✅ Constructor parameters are temporary.

✅ Instance variables live with the object.

✅ `this.engine = engine` stores the reference for future use.

✅ Spring performs the object creation and dependency wiring.

✅ Constructor Injection tells Spring what dependencies are required.

✅ `@Autowired` tells Spring to inject the required dependency.

---

# One Sentence I'll Never Forget

> **Spring is not magic. It simply creates objects, keeps them inside the IoC Container, and passes their references to constructors or fields. Everything after that is just plain Java.**