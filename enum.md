# Java Enum — Production-Level Notes

## 1. What is an enum in Java?

`enum` means **enumeration**.

In simple words, an enum is used when we have a **fixed set of known values**.

Example:

```java
public enum WeekDays {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
}
```

Here, weekdays are fixed. We do not want random values like:

```java
"Someday"
"HolidayDay"
"WrongDay"
```

So instead of using plain strings, we use enum.

---

## 2. Why do we create enum?

We create enum when values are limited and known in advance.

Examples:

```java
public enum OrderStatus {
    CREATED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
```

```java
public enum UserRole {
    ADMIN,
    USER,
    MANAGER
}
```

```java
public enum EC2Event {
    ADD_NEW_SCHEDULED_TASK,
    EDIT_SCHEDULED_TASK,
    ENABLE_SCHEDULED_TASK,
    DISABLE_SCHEDULED_TASK
}
```

Instead of passing random strings everywhere, enum gives us type safety.

Bad approach:

```java
public void updateOrderStatus(String status) {
    // Someone can pass "PAAID", "paid", "done", "anything"
}
```

Better approach:

```java
public void updateOrderStatus(OrderStatus status) {
    // Only valid OrderStatus enum values are allowed
}
```

---

## 3. Main benefits of enum

### 3.1 Type safety

With string:

```java
String status = "PAAID"; // typo, but Java allows it
```

With enum:

```java
OrderStatus status = OrderStatus.PAID;
```

If you write wrong enum value:

```java
OrderStatus status = OrderStatus.PAAID;
```

Java gives compile-time error.

That is safer.

---

### 3.2 Cleaner code

Without enum:

```java
if (status.equals("PAID")) {
    // logic
}
```

With enum:

```java
if (status == OrderStatus.PAID) {
    // logic
}
```

Enums are easier to read and safer to compare.

---

### 3.3 Avoid magic strings

Magic strings are hardcoded strings used everywhere.

Bad:

```java
if (event.equals("ADD NEW SCHEDULED TASK")) {
    // logic
}
```

Better:

```java
if (event == EC2Event.ADD_NEW_SCHEDULED_TASK) {
    // logic
}
```

Now the code is safer and easier to refactor.

---

### 3.4 Enums can have fields, constructors, and methods

Enums are not just constants. In Java, enum constants are objects.

So enum can have:

- fields
- constructor
- getter methods
- static methods
- lookup maps
- custom logic

Example:

```java
public enum EC2Event {

    ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK"),
    ENABLE_SCHEDULED_TASK("ENABLE SCHEDULED TASK"),
    DISABLE_SCHEDULED_TASK("DISABLE SCHEDULED TASK");

    private final String eventName;

    private EC2Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
```

---

## 4. Very important concept: each enum value is an object

When we write:

```java
public enum WeekDays {
    SUNDAY,
    MONDAY,
    TUESDAY
}
```

Think like this:

```text
WeekDays.SUNDAY  -> one object
WeekDays.MONDAY  -> one object
WeekDays.TUESDAY -> one object
```

Each enum constant is a fixed object created by Java.

So this:

```java
WeekDays day = WeekDays.MONDAY;
```

means `day` is referring to the enum object `MONDAY`.

---

## 5. Enum with constructor

Example:

```java
public enum EC2Event {

    ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK");

    private final String eventName;

    private EC2Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
```

When Java sees this:

```java
ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK")
```

It calls the enum constructor:

```java
private EC2Event(String eventName) {
    this.eventName = eventName;
}
```

So internally think like this:

```text
EC2Event.ADD_NEW_SCHEDULED_TASK.eventName = "ADD NEW SCHEDULED TASK"
EC2Event.EDIT_SCHEDULED_TASK.eventName    = "EDIT SCHEDULED TASK"
```

---

## 6. Why enum constructor is private?

Enum constructor is always private by design.

You cannot create enum object manually like this:

```java
EC2Event event = new EC2Event("TEST"); // not allowed
```

Why?

Because enum values must be fixed.

Only these values should exist:

```java
ADD_NEW_SCHEDULED_TASK
EDIT_SCHEDULED_TASK
ENABLE_SCHEDULED_TASK
DISABLE_SCHEDULED_TASK
```

If Java allowed `new EC2Event(...)`, then anyone could create unlimited values, and enum would lose its purpose.

So enum constructor is private.

This is valid:

```java
private EC2Event(String eventName) {
    this.eventName = eventName;
}
```

Even if you do not write `private`, enum constructor is private by default.

---

## 7. Why `eventName` is not static?

Example:

```java
private final String eventName;
```

This is non-static because each enum constant needs its own event name.

Example:

```java
ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK"),
DISABLE_SCHEDULED_TASK("DISABLE SCHEDULED TASK");
```

Think like this:

```text
ADD_NEW_SCHEDULED_TASK has eventName = "ADD NEW SCHEDULED TASK"
EDIT_SCHEDULED_TASK has eventName    = "EDIT SCHEDULED TASK"
DISABLE_SCHEDULED_TASK has eventName = "DISABLE SCHEDULED TASK"
```

So `eventName` belongs to each enum object.

That is why it should be:

```java
private final String eventName;
```

Not:

```java
private static final String eventName; // wrong for this case
```

If `eventName` was static, there would be only one common `eventName` for the whole enum class. That would be wrong because each event needs a different name.

---

## 8. Why `eventName` is final?

```java
private final String eventName;
```

`final` means once the value is assigned, it cannot be changed.

Example:

```java
private EC2Event(String eventName) {
    this.eventName = eventName;
}
```

After this assignment, `eventName` cannot be changed.

That is good because enum values should be fixed and stable.

We do not want this:

```java
EC2Event.ADD_NEW_SCHEDULED_TASK.eventName = "WRONG"; // should not be allowed
```

So `final` is correct for enum fields.

---

## 9. `static` vs non-static in enum

This is one of the most important topics.

### Non-static field

A non-static field belongs to each object.

Example:

```java
public enum WeekDays {
    SUNDAY,
    MONDAY,
    TUESDAY;

    private String displayName;
}
```

Think like this:

```text
SUNDAY.displayName
MONDAY.displayName
TUESDAY.displayName
```

Each enum object has its own `displayName`.

---

### Static field

A static field belongs to the class itself.

Example:

```java
public enum WeekDays {
    SUNDAY,
    MONDAY,
    TUESDAY;

    private static String commonValue;
}
```

Think like this:

```text
WeekDays.commonValue
```

Only one shared value exists for the whole enum class.

---

## 10. Why lookup map should be static?

Example:

```java
private static final Map<String, EC2Event> LOOKUP = new ConcurrentHashMap<>();
```

The lookup map is not specific to one event.

It is common for the whole enum.

It stores all mappings:

```text
"ADD NEW SCHEDULED TASK" -> ADD_NEW_SCHEDULED_TASK
"EDIT SCHEDULED TASK"    -> EDIT_SCHEDULED_TASK
"ENABLE SCHEDULED TASK"  -> ENABLE_SCHEDULED_TASK
"DISABLE SCHEDULED TASK" -> DISABLE_SCHEDULED_TASK
```

So there should be only one common map.

That is why it is `static`.

---

## 11. What happens if lookup map is not static?

Suppose we write:

```java
private Map<String, WeekDays> LOOKUP = new ConcurrentHashMap<>();
```

Now `LOOKUP` is non-static.

That means each enum constant gets its own map:

```text
SUNDAY has its own LOOKUP map
MONDAY has its own LOOKUP map
TUESDAY has its own LOOKUP map
WEDNESDAY has its own LOOKUP map
THURSDAY has its own LOOKUP map
FRIDAY has its own LOOKUP map
SATURDAY has its own LOOKUP map
```

This is wasteful and wrong.

We do not need 7 maps.

We need only one map:

```text
WeekDays.LOOKUP
```

So lookup map should be static.

---

## 12. Why static block can access static map directly?

Example:

```java
private static final Map<String, WeekDays> LOOKUP = new ConcurrentHashMap<>();

static {
    for (WeekDays weekDay : WeekDays.values()) {
        LOOKUP.put(weekDay.name(), weekDay);
    }
}
```

This is allowed because both are class-level:

```text
static block -> class level
LOOKUP       -> class level
```

So static block can directly access `LOOKUP`.

---

## 13. Static block cannot directly access non-static field

This is wrong:

```java
public enum EC2Event {

    ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK");

    private final String eventName;

    private EC2Event(String eventName) {
        this.eventName = eventName;
    }

    static {
        System.out.println(eventName); // error
    }
}
```

Why error?

Because `eventName` belongs to each enum object.

Java asks:

```text
Which eventName?
ADD_NEW_SCHEDULED_TASK's eventName?
EDIT_SCHEDULED_TASK's eventName?
DISABLE_SCHEDULED_TASK's eventName?
```

So direct access is not allowed.

---

## 14. Static block can access non-static field using object reference

This is correct:

```java
static {
    for (EC2Event enumEntry : EC2Event.values()) {
        System.out.println(enumEntry.eventName);
    }
}
```

Why?

Because now we are accessing `eventName` through a specific enum object:

```java
enumEntry.eventName
```

During loop:

```text
enumEntry = ADD_NEW_SCHEDULED_TASK
enumEntry.eventName = "ADD NEW SCHEDULED TASK"

enumEntry = EDIT_SCHEDULED_TASK
enumEntry.eventName = "EDIT SCHEDULED TASK"
```

So the rule is:

```text
Static block cannot directly access non-static fields.

But static block can access non-static fields through an object reference.
```

---

## 15. What is static block?

A static block runs once when the class is loaded.

Example:

```java
static {
    System.out.println("WeekDays enum loaded");
}
```

In enum lookup map, static block is used to fill the map one time.

Example:

```java
static {
    for (WeekDays weekDay : WeekDays.values()) {
        LOOKUP.put(weekDay.name(), weekDay);
    }
}
```

This runs once.

After that, lookup is fast:

```java
WeekDays.search("MONDAY");
```

The map is already prepared.

---

## 16. Why use lookup map?

Without lookup map, every search has to loop:

```java
public static EC2Event search(String eventName) {
    for (EC2Event event : EC2Event.values()) {
        if (event.getEventName().equalsIgnoreCase(eventName)) {
            return event;
        }
    }
    return null;
}
```

This works, but every call loops over all enum values.

With lookup map:

```java
public static EC2Event search(String eventName) {
    return LOOKUP.get(eventName.toUpperCase());
}
```

Now lookup is direct and fast.

For small enums, loop is also okay.

For production code, lookup map is useful when:

- enum has many values
- lookup is called frequently
- lookup is used in API request mapping
- lookup is used in validation
- lookup is used in scheduler/event systems
- lookup is used in database/string conversion

---

## 17. Why `private static final Map`?

Example:

```java
private static final Map<String, EC2Event> LOOKUP = new ConcurrentHashMap<>();
```

Breakdown:

### `private`

```java
private static final Map<String, EC2Event> LOOKUP
```

`private` means outside classes cannot access this map directly.

This is good because external code should not do this:

```java
EC2Event.LOOKUP.clear();
EC2Event.LOOKUP.put("WRONG", EC2Event.DISABLE_SCHEDULED_TASK);
```

The map is internal implementation detail.

External code should use method:

```java
EC2Event.search("ADD NEW SCHEDULED TASK");
```

---

### `static`

`static` means one shared map for the whole enum class.

We do not want one map per enum constant.

Correct:

```text
EC2Event.LOOKUP
```

Wrong mental model:

```text
ADD_NEW_SCHEDULED_TASK.LOOKUP
EDIT_SCHEDULED_TASK.LOOKUP
DISABLE_SCHEDULED_TASK.LOOKUP
```

So `static` is correct.

---

### `final`

`final` means the `LOOKUP` variable cannot be reassigned to another map.

Allowed:

```java
LOOKUP.put("ADD NEW SCHEDULED TASK", EC2Event.ADD_NEW_SCHEDULED_TASK);
```

Not allowed if final:

```java
LOOKUP = new ConcurrentHashMap<>();
```

Important:

```text
final Map does not mean the map content cannot change.
final means the variable cannot point to another map.
```

Example:

```java
final Map<String, String> map = new ConcurrentHashMap<>();

map.put("A", "B");      // allowed
map.put("C", "D");      // allowed

map = new ConcurrentHashMap<>(); // not allowed
```

---

## 18. Why `ConcurrentHashMap`?

```java
new ConcurrentHashMap<>()
```

`ConcurrentHashMap` is thread-safe.

In web applications, many threads may call this method at the same time:

```java
EC2Event.search("ADD NEW SCHEDULED TASK");
```

So `ConcurrentHashMap` is safe for concurrent access.

But in this exact enum lookup case, we usually write data only once in the static block and then only read it.

So `ConcurrentHashMap` is not always necessary.

This is also okay:

```java
private static final Map<String, EC2Event> LOOKUP = new HashMap<>();
```

But better production style is often:

```java
private static final Map<String, EC2Event> LOOKUP;

static {
    Map<String, EC2Event> map = new HashMap<>();

    for (EC2Event event : EC2Event.values()) {
        map.put(event.getEventName().toUpperCase(Locale.ROOT), event);
    }

    LOOKUP = Collections.unmodifiableMap(map);
}
```

Now the map cannot be accidentally changed after initialization.

---

## 19. Production-safe enum lookup example

```java
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum EC2Event {

    MANAGE_NEW_EC2_RESOURCE("MANAGE NEW EC2 RESOURCE"),
    REMOVE_MANAGED_EC2_INSTANCE("REMOVE MANAGED EC2 INSTANCE"),
    ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK"),
    PAUSE_SCHEDULED_TASK_ACTION("PAUSE SCHEDULED TASK"),
    RESUMED_SCHEDULED_TASK("RESUME SCHEDULED TASK"),
    ENABLE_SCHEDULED_TASK("ENABLE SCHEDULED TASK"),
    DISABLE_SCHEDULED_TASK("DISABLE SCHEDULED TASK"),
    EXECUTE_SCHEDULED_TASK_ACTION("EXECUTE SCHEDULED TASK");

    private final String eventName;

    private static final Map<String, EC2Event> LOOKUP;

    static {
        Map<String, EC2Event> map = new HashMap<>();

        for (EC2Event event : EC2Event.values()) {
            map.put(normalize(event.eventName), event);
        }

        LOOKUP = Collections.unmodifiableMap(map);
    }

    private EC2Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public static EC2Event search(String eventName) {
        if (eventName == null || eventName.trim().isEmpty()) {
            return null;
        }

        return LOOKUP.get(normalize(eventName));
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
```

---

## 20. Why use `Locale.ROOT` with `toUpperCase()`?

Bad:

```java
eventName.toUpperCase()
```

Better:

```java
eventName.toUpperCase(Locale.ROOT)
```

Reason:

`toUpperCase()` without locale can behave differently in some languages.

For production code, use:

```java
toUpperCase(Locale.ROOT)
```

when normalizing technical keys, enum names, event names, status codes, etc.

---

## 21. Why use `trim()`?

Current code:

```java
LOOKUP.get(eventName.toUpperCase())
```

If input is:

```text
" ADD NEW SCHEDULED TASK "
```

it will not match because of extra spaces.

Better:

```java
eventName.trim().toUpperCase(Locale.ROOT)
```

This handles extra spaces.

---

## 22. `name()` vs `toString()` in enum

Every enum has a built-in method:

```java
name()
```

Example:

```java
WeekDays.MONDAY.name()
```

Output:

```text
MONDAY
```

`toString()` by default also returns the enum name.

Example:

```java
WeekDays.MONDAY.toString()
```

Output:

```text
MONDAY
```

But `toString()` can be overridden.

Example:

```java
public enum WeekDays {
    MONDAY;

    @Override
    public String toString() {
        return "Monday";
    }
}
```

Now:

```java
WeekDays.MONDAY.name();     // MONDAY
WeekDays.MONDAY.toString(); // Monday
```

Production rule:

```text
Use name() when you need the exact enum constant name.
Use custom field/getter when you need business display value.
Use toString() carefully because it can be overridden.
```

---

## 23. Enum comparison: use `==`

For enum comparison, use:

```java
if (status == OrderStatus.PAID) {
    // logic
}
```

This is safe because enum constants are singleton objects.

You can also use `.equals()`, but `==` is common and safe for enums.

Example:

```java
OrderStatus status = OrderStatus.PAID;

System.out.println(status == OrderStatus.PAID); // true
```

---

## 24. Enum in switch

Enums work well with switch.

```java
public void handleStatus(OrderStatus status) {
    switch (status) {
        case CREATED:
            System.out.println("Order created");
            break;

        case PAID:
            System.out.println("Payment received");
            break;

        case SHIPPED:
            System.out.println("Order shipped");
            break;

        case DELIVERED:
            System.out.println("Order delivered");
            break;

        case CANCELLED:
            System.out.println("Order cancelled");
            break;

        default:
            throw new IllegalArgumentException("Unsupported status: " + status);
    }
}
```

With modern Java switch expression:

```java
public String getMessage(OrderStatus status) {
    return switch (status) {
        case CREATED -> "Order created";
        case PAID -> "Payment received";
        case SHIPPED -> "Order shipped";
        case DELIVERED -> "Order delivered";
        case CANCELLED -> "Order cancelled";
    };
}
```

---

## 25. Enum with behavior

Enums can contain behavior.

Example:

```java
public enum PaymentStatus {

    SUCCESS {
        @Override
        public boolean isFinal() {
            return true;
        }
    },

    FAILED {
        @Override
        public boolean isFinal() {
            return true;
        }
    },

    PENDING {
        @Override
        public boolean isFinal() {
            return false;
        }
    };

    public abstract boolean isFinal();
}
```

Usage:

```java
PaymentStatus status = PaymentStatus.SUCCESS;

if (status.isFinal()) {
    System.out.println("Payment completed");
}
```

This is useful when each enum value has different behavior.

---

## 26. Enum implementing interface

Example:

```java
public interface ActionHandler {
    void execute();
}
```

```java
public enum SchedulerAction implements ActionHandler {

    ENABLE {
        @Override
        public void execute() {
            System.out.println("Enable task");
        }
    },

    DISABLE {
        @Override
        public void execute() {
            System.out.println("Disable task");
        }
    };

    public abstract void execute();
}
```

This is possible because enum is a special kind of class.

---

## 27. Where should we create enums in production?

Common package structures:

```text
com.company.project.enums
```

or domain-specific:

```text
com.exascale.common.enums.ec2
com.exascale.common.enums.scheduler
com.exascale.common.enums.rds
com.exascale.common.enums.s3
```

For your case, this is good:

```text
com.exascale.common.enums.ec2.EC2Event
```

Why?

Because `EC2Event` belongs to EC2 domain.

Production rule:

```text
Place enum near the domain where it is used.
If enum is shared across modules, place it in common/shared package.
If enum is only used inside one feature, keep it inside that feature package.
```

---

## 28. When should we use enum?

Use enum for:

- statuses
- roles
- event types
- action types
- fixed categories
- cloud provider names
- scheduler states
- payment states
- resource states
- request types
- notification types

Examples:

```java
public enum CloudProvider {
    AWS,
    AZURE,
    GCP
}
```

```java
public enum TaskStatus {
    ENABLED,
    DISABLED,
    PAUSED,
    RUNNING,
    FAILED
}
```

```java
public enum EC2Action {
    START_INSTANCE,
    STOP_INSTANCE,
    REBOOT_INSTANCE,
    CREATE_AMI,
    CREATE_EBS_SNAPSHOT
}
```

---

## 29. When should we not use enum?

Do not use enum when values change frequently from database or admin panel.

Example:

```text
Product categories managed by admin
Country list updated from external system
Dynamic pricing plans
User-created tags
Tenant-specific custom values
```

For dynamic values, use database tables.

Enums are best for fixed values known at compile time.

---

## 30. Enum and database

Enums are commonly stored in DB.

Two common approaches:

### Store enum name

```text
PAID
FAILED
PENDING
```

Good because it is readable.

In JPA:

```java
@Enumerated(EnumType.STRING)
private OrderStatus status;
```

Use `EnumType.STRING`, not `EnumType.ORDINAL`.

---

### Avoid ordinal in production

Bad:

```java
@Enumerated(EnumType.ORDINAL)
private OrderStatus status;
```

Why bad?

If enum is:

```java
CREATED, PAID, SHIPPED
```

DB stores:

```text
0, 1, 2
```

If later you reorder enum:

```java
PAID, CREATED, SHIPPED
```

Old DB values can map to wrong statuses.

Production rule:

```text
Always prefer EnumType.STRING for database storage.
Avoid EnumType.ORDINAL.
```

---

## 31. Enum and API request/response

For APIs, you can accept enum values directly:

```java
public class CreateTaskRequest {
    private EC2Event event;
}
```

JSON:

```json
{
  "event": "ADD_NEW_SCHEDULED_TASK"
}
```

But if API sends business name:

```json
{
  "event": "ADD NEW SCHEDULED TASK"
}
```

Then you need custom lookup:

```java
EC2Event.search("ADD NEW SCHEDULED TASK");
```

Production advice:

```text
For APIs, prefer stable enum codes like ADD_NEW_SCHEDULED_TASK.
For user-facing display, use eventName like "ADD NEW SCHEDULED TASK".
```

---

## 32. Code value vs display value

Better enum design:

```java
public enum EC2Event {

    ADD_NEW_SCHEDULED_TASK("ADD_NEW_SCHEDULED_TASK", "ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT_SCHEDULED_TASK", "EDIT SCHEDULED TASK");

    private final String code;
    private final String displayName;

    private EC2Event(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
```

Why?

`code` is stable for API/DB/system usage.

`displayName` is for UI/logs.

Example:

```text
code        = ADD_NEW_SCHEDULED_TASK
displayName = ADD NEW SCHEDULED TASK
```

Production recommendation:

```text
Use stable code for system communication.
Use display name for humans.
```

---

## 33. Complete production-style enum with code and display name

```java
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum EC2Event {

    MANAGE_NEW_EC2_RESOURCE("MANAGE_NEW_EC2_RESOURCE", "MANAGE NEW EC2 RESOURCE"),
    REMOVE_MANAGED_EC2_INSTANCE("REMOVE_MANAGED_EC2_INSTANCE", "REMOVE MANAGED EC2 INSTANCE"),
    ADD_NEW_SCHEDULED_TASK("ADD_NEW_SCHEDULED_TASK", "ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT_SCHEDULED_TASK", "EDIT SCHEDULED TASK"),
    PAUSE_SCHEDULED_TASK_ACTION("PAUSE_SCHEDULED_TASK_ACTION", "PAUSE SCHEDULED TASK"),
    RESUMED_SCHEDULED_TASK("RESUMED_SCHEDULED_TASK", "RESUME SCHEDULED TASK"),
    ENABLE_SCHEDULED_TASK("ENABLE_SCHEDULED_TASK", "ENABLE SCHEDULED TASK"),
    DISABLE_SCHEDULED_TASK("DISABLE_SCHEDULED_TASK", "DISABLE SCHEDULED TASK"),
    EXECUTE_SCHEDULED_TASK_ACTION("EXECUTE_SCHEDULED_TASK_ACTION", "EXECUTE SCHEDULED TASK");

    private final String code;
    private final String displayName;

    private static final Map<String, EC2Event> BY_CODE;
    private static final Map<String, EC2Event> BY_DISPLAY_NAME;

    static {
        Map<String, EC2Event> byCode = new HashMap<>();
        Map<String, EC2Event> byDisplayName = new HashMap<>();

        for (EC2Event event : EC2Event.values()) {
            byCode.put(normalize(event.code), event);
            byDisplayName.put(normalize(event.displayName), event);
        }

        BY_CODE = Collections.unmodifiableMap(byCode);
        BY_DISPLAY_NAME = Collections.unmodifiableMap(byDisplayName);
    }

    private EC2Event(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EC2Event fromCode(String code) {
        if (isBlank(code)) {
            return null;
        }

        return BY_CODE.get(normalize(code));
    }

    public static EC2Event fromDisplayName(String displayName) {
        if (isBlank(displayName)) {
            return null;
        }

        return BY_DISPLAY_NAME.get(normalize(displayName));
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
```

---

## 34. Should search return null or throw exception?

Both approaches are valid, depending on use case.

### Return null

```java
public static EC2Event search(String eventName) {
    if (eventName == null || eventName.trim().isEmpty()) {
        return null;
    }

    return LOOKUP.get(normalize(eventName));
}
```

Good when invalid input is normal and caller will handle it.

Example:

```java
EC2Event event = EC2Event.search(input);

if (event == null) {
    // show validation error
}
```

---

### Throw exception

```java
public static EC2Event fromEventName(String eventName) {
    EC2Event event = search(eventName);

    if (event == null) {
        throw new IllegalArgumentException("Invalid EC2 event: " + eventName);
    }

    return event;
}
```

Good when invalid value means programming mistake or invalid API request.

Production approach:

```text
Use search()/find() when null is acceptable.
Use from()/valueOf-like method when invalid input should fail fast.
```

---

## 35. `valueOf()` method

Every enum automatically has `valueOf()`.

Example:

```java
EC2Event event = EC2Event.valueOf("ADD_NEW_SCHEDULED_TASK");
```

This works only with exact enum constant name.

This will not work:

```java
EC2Event.valueOf("ADD NEW SCHEDULED TASK"); // error
```

Because enum constant is:

```java
ADD_NEW_SCHEDULED_TASK
```

Not:

```text
ADD NEW SCHEDULED TASK
```

So for custom display names, use your own lookup method.

---

## 36. `values()` method

Every enum automatically has `values()`.

Example:

```java
for (EC2Event event : EC2Event.values()) {
    System.out.println(event);
}
```

It returns all enum constants.

Example output:

```text
MANAGE_NEW_EC2_RESOURCE
REMOVE_MANAGED_EC2_INSTANCE
ADD_NEW_SCHEDULED_TASK
...
```

In lookup map static block, we use `values()` to fill the map.

---

## 37. Simple WeekDays lookup example

```java
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum WeekDays {

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    private static final Map<String, WeekDays> LOOKUP;

    static {
        Map<String, WeekDays> map = new HashMap<>();

        for (WeekDays weekDay : WeekDays.values()) {
            map.put(weekDay.name(), weekDay);
        }

        LOOKUP = Collections.unmodifiableMap(map);
    }

    public static WeekDays search(String dayName) {
        if (dayName == null || dayName.trim().isEmpty()) {
            return null;
        }

        return LOOKUP.get(dayName.trim().toUpperCase(Locale.ROOT));
    }

    public static void main(String[] args) {
        System.out.println(WeekDays.search("monday"));
        System.out.println(WeekDays.search(" MONDAY "));
    }
}
```

Output:

```text
MONDAY
MONDAY
```

---

## 38. Enum memory model in simple diagram

```text
EC2Event class
│
├── MANAGE_NEW_EC2_RESOURCE object
│   └── eventName = "MANAGE NEW EC2 RESOURCE"
│
├── ADD_NEW_SCHEDULED_TASK object
│   └── eventName = "ADD NEW SCHEDULED TASK"
│
├── EDIT_SCHEDULED_TASK object
│   └── eventName = "EDIT SCHEDULED TASK"
│
└── static LOOKUP map
    ├── "MANAGE NEW EC2 RESOURCE" -> MANAGE_NEW_EC2_RESOURCE
    ├── "ADD NEW SCHEDULED TASK" -> ADD_NEW_SCHEDULED_TASK
    └── "EDIT SCHEDULED TASK" -> EDIT_SCHEDULED_TASK
```

Important:

```text
eventName is non-static because each enum object has its own eventName.

LOOKUP is static because there should be one common map for all enum objects.
```

---

## 39. Common beginner questions

### Q1. Is each enum value an object?

Yes.

```java
SUNDAY
MONDAY
TUESDAY
```

Each one is an object of the enum type.

---

### Q2. Why is `eventName` non-static?

Because each enum object has a different `eventName`.

---

### Q3. Why is `LOOKUP` static?

Because the lookup map is shared by all enum values.

We need one map, not one map per enum constant.

---

### Q4. What happens if `LOOKUP` is not static?

Every enum constant will have its own `LOOKUP` map.

For `WeekDays`, that means:

```text
SUNDAY.LOOKUP
MONDAY.LOOKUP
TUESDAY.LOOKUP
...
```

That is not needed.

Also, a static block or static method cannot directly access a non-static `LOOKUP`.

---

### Q5. Why use `final` with `LOOKUP`?

So the `LOOKUP` variable cannot be reassigned to another map.

---

### Q6. Does `final Map` mean map content cannot change?

No.

This is allowed:

```java
LOOKUP.put("KEY", VALUE);
```

This is not allowed if `LOOKUP` is final:

```java
LOOKUP = new HashMap<>();
```

If you want map content also not to change, use:

```java
Collections.unmodifiableMap(map)
```

or Java 9+ immutable map factories where suitable.

---

### Q7. Why can static block use `enumEntry.eventName`?

Because `enumEntry` is a specific enum object.

Static block cannot do this:

```java
eventName
```

But it can do this:

```java
enumEntry.eventName
```

Because now it knows which object's field is needed.

---

### Q8. Should enum fields be final?

Usually yes.

Enum values should be stable and immutable.

Use:

```java
private final String eventName;
```

---

### Q9. Should enum lookup return null?

It depends.

For flexible validation, return null.

For strict parsing, throw exception.

Many production teams provide both:

```java
search(String value)       // returns null
fromValue(String value)    // throws exception if invalid
```

---

### Q10. Should we use enum or database table?

Use enum when values are fixed in code.

Use database table when values are dynamic and can change without deployment.

---

## 40. Production checklist for enums

Use this checklist:

```text
1. Use enum for fixed known values.
2. Do not use enum for dynamic database-managed values.
3. Keep enum names clear and stable.
4. Use fields for business/display names.
5. Make enum fields private final.
6. Use getters, do not expose fields directly.
7. Use static lookup map for reverse lookup.
8. Keep lookup map private.
9. Make lookup map static final.
10. Normalize input using trim() and Locale.ROOT.
11. Avoid EnumType.ORDINAL in database.
12. Prefer EnumType.STRING in database.
13. Use name() for exact enum constant name.
14. Use displayName/code fields for business values.
15. Use == for enum comparison.
16. Use switch for clear enum-based logic.
17. Avoid putting too much business logic in enum if it makes enum too large.
18. Keep enum in domain-specific package.
19. Do not expose mutable lookup maps.
20. Consider unmodifiableMap for read-only lookup.
```

---

## 41. Final memory trick

```text
enum value = object

non-static field = belongs to each enum object

static field = belongs to enum class

eventName should be non-static
because each event has its own eventName

LOOKUP should be static
because one common lookup table is enough for all events

final field = value/reference cannot be reassigned

private field = outside class cannot directly change it
```

---

## 42. Final example from our discussion

```java
public enum EC2Event {

    ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
    EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK"),
    ENABLE_SCHEDULED_TASK("ENABLE SCHEDULED TASK"),
    DISABLE_SCHEDULED_TASK("DISABLE SCHEDULED TASK");

    private final String eventName;

    private static final Map<String, EC2Event> LOOKUP = new ConcurrentHashMap<>();

    static {
        for (EC2Event enumEntry : EC2Event.values()) {
            LOOKUP.put(enumEntry.eventName.toUpperCase(), enumEntry);
        }
    }

    private EC2Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public static EC2Event search(String eventName) {
        return eventName != null && !eventName.trim().isEmpty()
                ? LOOKUP.get(eventName.toUpperCase())
                : null;
    }
}
```

Understanding:

```text
eventName:
    non-static
    because each enum constant has its own event name

LOOKUP:
    static
    because it is one common map for searching all enum constants

static block:
    fills LOOKUP one time when enum class loads

enumEntry.eventName:
    allowed because enumEntry is a specific enum object

final eventName:
    event name cannot change after enum object is created

final LOOKUP:
    LOOKUP variable cannot point to another map
```
