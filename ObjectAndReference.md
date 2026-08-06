
# 1. Objects vs References

One of the biggest misconceptions is thinking a variable stores an object.

It **doesn't**.

Example:

```java
Engine engine = new Engine();
```

Memory

```
Stack                     Heap

engine  ----------------> Engine Object
```

The variable `engine` **does not contain the object**.

It contains a **reference** (pointer/address) to the object.

---

# 2. Java is Pass-by-Value

Java is ALWAYS pass-by-value.

This statement is 100% true.

The confusing part is:

When we pass an object,

Java passes **a copy of the reference**.

Example

```java
Engine e = new Engine();

Car car = new Car(e);
```

Suppose

```
e = 0x100
```

(Java doesn't expose real addresses, but imagine.)

Java copies

```
0x100
```

to the constructor parameter.

Now

```
e ---------------------> Engine Object

engine ----------------> Engine Object
```

There is still

- ONE Engine object
- TWO references

---

# 3. Constructor Parameter is Temporary

Constructor

```java
Car(Engine engine) {

}
```

This parameter

```java
engine
```

exists ONLY while constructor is executing.

```
Constructor Starts

↓

engine parameter exists

↓

Constructor Ends

↓

engine parameter disappears
```

So later,

```java
start();
```

has NO idea which Engine object it should use.

---

# 4. Why do we write

```java
this.engine = engine;
```

This was my biggest confusion.

Let's understand.

Inside Car

```java
class Car {

    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }
}
```

Notice there are TWO variables.

Instance variable

```java
Engine engine;
```

Constructor parameter

```java
Engine engine
```

Now

```java
this.engine = engine;
```

means

> Copy the constructor parameter's reference into the current object's field.

After assignment

```
Car Object

engine --------------------> Engine Object
```

Now even after constructor finishes,

Car remembers which Engine it owns.

---

# Think like a Human

Suppose someone gives you a phone number.

```
9876543210
```

If you don't save it,

after conversation,

you forget it.

So you save it.

```
Contacts

Rahul → 9876543210
```

Now you can call Rahul anytime.

Exactly same thing.

Constructor parameter = someone telling you the number.

Instance variable = saving the number.

---

# 5. Why not use constructor parameter directly?

Imagine

```java
class OrderService {

    OrderService(PaymentService paymentService){

    }

    void placeOrder(){

        paymentService.pay();

    }

}
```

This won't compile.

Why?

Because

```
paymentService
```

doesn't exist anymore.

It died with constructor.

That's why

```
this.paymentService = paymentService;
```

is necessary.

---

# 6. Why do we store the reference?

This is NOT because Java requires it.

We store it because

the object will need it MANY times.

Example

```java
class BookingService {

    private UserRepository repository;

    BookingService(UserRepository repository){

        this.repository = repository;

    }

    save(){}

    update(){}

    delete(){}

    find(){}

}
```

Every method needs Repository.

Instead of passing Repository every time,

we store it once.

---

# 7. Multiple References

Example

```java
Engine e = new Engine();

Car car = new Car(e);
```

Memory

```
e --------------------+

                       |

                       V

                Engine Object

                       ^

                       |

car.engine ------------+
```

Notice

Both references point to SAME object.

There are NOT two objects.

---

# 8. If we modify using one reference

Suppose

```java
engine.speed = 100;
```

Then

```java
car.engine.speed = 500;
```

Now

```java
System.out.println(engine.speed);
```

prints

```
500
```

Why?

Because

Both references point to SAME object.

---

# 9. If reference changes

Suppose

```java
car.engine = new DieselEngine();
```

Now

```
engine --------------------> PetrolEngine

car.engine ----------------> DieselEngine
```

Only

```
car.engine
```

changed.

The original

```
engine
```

still points to PetrolEngine.

---

# 10. Dependency Injection

Without Spring

WE create everything.

```java
UserRepository repo = new UserRepositoryImpl();

BookingService service = new BookingService(repo);

BookingController controller = new BookingController(service);
```

Everything is manual.

---

# 11. Spring Dependency Injection

With Spring

We NEVER write

```java
new UserRepository()

new BookingService(...)

new BookingController(...)
```

Spring does it.

Internally think of Spring like

```java
UserRepository repo = new UserRepositoryImpl();

BookingService service = new BookingService(repo);

BookingController controller = new BookingController(service);
```

Spring is simply writing this code for us.

---

# 12. Then why do we still write constructor?

Because

Constructor tells Spring

what dependencies are required.

Example

```java
BookingService(UserRepository repository)
```

Spring reads

```
BookingService requires UserRepository
```

Spring then finds Repository

and passes it.

The constructor is a CONTRACT.

It says

> "I cannot exist unless you give me UserRepository."

---

# 13. What does @Autowired do?

Field Injection

```java
@Autowired
private UserRepository repository;
```

Conceptually Spring does

```java
BookingService service = new BookingService();

service.repository = repositoryBean;
```

Spring injects the reference.

---

Constructor Injection

```java
BookingService(UserRepository repository){

    this.repository = repository;

}
```

Conceptually Spring does

```java
UserRepository repo = new UserRepositoryImpl();

BookingService service = new BookingService(repo);
```

The assignment

```java
this.repository = repository;
```

is still Java.

Spring only supplies the object.

---

# 14. What is IoC Container?

Think of it as a Map.

```
Spring Container

UserRepository

↓

Repository Object

BookingService

↓

BookingService Object

BookingController

↓

BookingController Object
```

Whenever some class asks for

```
UserRepository
```

Spring says

```
I already have one.

Take it.
```

---

# 15. Why Interfaces?

Instead of

```java
class Car{

    PetrolEngine engine;

}
```

We do

```java
class Car{

    Engine engine;

}
```

where

```java
interface Engine{}
```

Implementations

```
PetrolEngine

DieselEngine

ElectricEngine
```

Now Car doesn't care.

It simply says

> "Give me anything that behaves like an Engine."

This follows

Dependency Inversion Principle (DIP).

---

# 16. Biggest Realization

I originally thought

```
this.engine = engine;
```

was just syntax.

It isn't.

It is

> Saving the dependency's reference inside the object so every method of the object can use it throughout its lifetime.

Constructor parameter is temporary.

Instance variable lives as long as the object lives.

---

# 17. Mental Model

Without Spring

```
Me

↓

Create Repository

↓

Create Service

↓

Pass Repository

↓

Create Controller

↓

Pass Service
```

With Spring

```
Spring

↓

Creates Repository

↓

Creates Service

↓

Injects Repository

↓

Creates Controller

↓

Injects Service
```

The constructor simply tells Spring

what is required.

Spring provides it.

---

# Golden Rules

✅ Objects live in Heap.

✅ Reference variables live in Stack (local variables) or inside objects (instance fields).

✅ References point to objects.

✅ Java copies references, NOT objects.

✅ Constructor parameters are temporary.

✅ Instance variables survive as long as the object survives.

✅ `this.engine = engine` stores the reference for future use.

✅ Spring performs Dependency Injection.

✅ Constructor Injection is preferred over Field Injection.

✅ `@Autowired` tells Spring:
> "Find this dependency and inject it."

---

# One Sentence I'll Never Forget

> **Spring doesn't perform magic. It simply creates objects, stores them in the IoC container, and passes references to constructors or fields. The rest is plain Java.**