/*
 * ============================================================================
 * JAVA ENUM + OBJECT + REFERENCE + STATIC + FINAL
 * ============================================================================
 *
 * 1. FIRST UNDERSTAND: CLASS, OBJECT AND REFERENCE
 * ============================================================================
 *
 * A CLASS is a blueprint.
 *
 * An OBJECT is an actual instance created from that class.
 *
 * A REFERENCE VARIABLE is a variable that points to an object.
 *
 *
 * Example:
 *
 *     Direction d1 = new Direction();
 *
 *
 * Think:
 *
 *     d1
 *      |
 *      | reference
 *      v
 *   Direction Object
 *
 *
 * Here:
 *
 *     Direction = class/type
 *     d1        = reference variable
 *     new Direction() = object
 *
 *
 *
 * IMPORTANT:
 *
 *     Reference variable != Object
 *
 * The reference points to the object.
 *
 *
 * ============================================================================
 * 2. MULTIPLE REFERENCES CAN POINT TO THE SAME OBJECT
 * ============================================================================
 *
 * Example:
 *
 *     Direction d1 = new Direction();
 *     Direction d2 = d1;
 *     Direction d3 = d1;
 *
 *
 * Diagram:
 *
 *     d1 ─────────┐
 *                 |
 *     d2 ─────────┼────> SAME Direction Object
 *                 |
 *     d3 ─────────┘
 *
 *
 * Here:
 *
 *     3 reference variables
 *     1 object
 *
 *
 * Therefore:
 *
 *     Number of references != Number of objects
 *
 *
 *
 * ============================================================================
 * 3. REASSIGNING A REFERENCE
 * ============================================================================
 *
 * Example:
 *
 *     Direction d1 = new Direction();
 *
 *     d1 = new Direction();
 *
 *
 * First:
 *
 *     d1 ─────> Object #1
 *
 *
 * After:
 *
 *     d1 ─────> Object #2
 *
 *
 * We did NOT change Object #1.
 *
 * We only changed where the reference d1 points.
 *
 *
 * IMPORTANT:
 *
 *     Changing a reference
 *         !=
 *     changing an object
 *
 *
 * If Object #1 has no other references, it may later become
 * eligible for Garbage Collection.
 *
 *
 *
 * ============================================================================
 * 4. NON-STATIC MEMBER
 * ============================================================================
 *
 * Example:
 *
 *     class Test {
 *
 *         int value;
 *         Person person = new Person();
 *     }
 *
 *
 * Now:
 *
 *     Test t1 = new Test();
 *     Test t2 = new Test();
 *     Test t3 = new Test();
 *
 *
 * Each Test object gets its OWN copy of the non-static fields.
 *
 *
 * Diagram:
 *
 *     t1
 *      |
 *      ├── value
 *      └── person ─────> Person Object #1
 *
 *
 *     t2
 *      |
 *      ├── value
 *      └── person ─────> Person Object #2
 *
 *
 *     t3
 *      |
 *      ├── value
 *      └── person ─────> Person Object #3
 *
 *
 * So:
 *
 *     NON-STATIC
 *          ↓
 *     belongs to each OBJECT
 *          ↓
 *     every object gets its own member/reference
 *
 *
 *
 * ============================================================================
 * 5. STATIC MEMBER
 * ============================================================================
 *
 * static means:
 *
 *     The member belongs to the CLASS itself,
 *     rather than separately to every OBJECT.
 *
 *
 * VERY IMPORTANT:
 *
 *     static does NOT mean "only one object exists".
 *
 *     static means "one shared class-level member/reference".
 *
 *
 * Example:
 *
 *     class Test {
 *
 *         static Person person = new Person();
 *     }
 *
 *
 * Here:
 *
 *     person = one static reference
 *
 *     and that reference points to:
 *
 *     one Person object
 *
 *
 * Diagram:
 *
 *                    Test CLASS
 *                        |
 *                      person
 *                        |
 *                        v
 *                  Person Object #1
 *
 *
 * If:
 *
 *     Test t1 = new Test();
 *     Test t2 = new Test();
 *     Test t3 = new Test();
 *
 *
 * All objects access the SAME static reference:
 *
 *
 *     t1.person ─────┐
 *                   |
 *     t2.person ─────┼────> SAME Person Object
 *                   |
 *     t3.person ─────┘
 *
 *
 * Therefore:
 *
 *     static reference
 *          ↓
 *     ONE reference belongs to the CLASS
 *          ↓
 *     all instances share that reference
 *          ↓
 *     all instances see the SAME referenced object
 *
 *
 *
 * ============================================================================
 * 6. STATIC DOES NOT MEAN ONE OBJECT
 * ============================================================================
 *
 * Example:
 *
 *     class Direction {
 *
 *         static Direction north = new Direction();
 *     }
 *
 *
 * There are two separate concepts:
 *
 *
 *     static Direction north
 *             ↓
 *        REFERENCE VARIABLE
 *
 *
 *     new Direction()
 *             ↓
 *           OBJECT
 *
 *
 * static applies to the REFERENCE/MEMBER.
 *
 * It does NOT mean that Java can only create one object in general.
 *
 *
 *
 * ============================================================================
 * 7. NON-STATIC REFERENCE VS STATIC REFERENCE
 * ============================================================================
 *
 *
 * NON-STATIC:
 *
 *     class Test {
 *         Person person = new Person();
 *     }
 *
 *
 *     Test t1 = new Test();
 *     Test t2 = new Test();
 *     Test t3 = new Test();
 *
 *
 * Diagram:
 *
 *     t1
 *      |
 *      └── person ─────> Person #1
 *
 *     t2
 *      |
 *      └── person ─────> Person #2
 *
 *     t3
 *      |
 *      └── person ─────> Person #3
 *
 *
 * Every Test object has its own person reference.
 *
 *
 *
 * STATIC:
 *
 *     class Test {
 *         static Person person = new Person();
 *     }
 *
 *
 * Diagram:
 *
 *                    Test CLASS
 *                        |
 *                      person
 *                        |
 *                        v
 *                    Person #1
 *                    ↑    ↑    ↑
 *                    |    |    |
 *                   t1   t2   t3
 *
 *
 * Every Test object accesses the SAME person reference.
 *
 *
 *
 * ============================================================================
 * 8. FINAL REFERENCE
 * ============================================================================
 *
 * final means:
 *
 *     The reference cannot be reassigned.
 *
 *
 * Example:
 *
 *     final Person person = new Person();
 *
 *
 * This is NOT allowed:
 *
 *     person = new Person();
 *
 *
 * Because we are trying to make the reference point to another object.
 *
 *
 * Diagram:
 *
 *     person ─────> Person Object #1
 *       |
 *       X
 *       |
 *     cannot point to Object #2
 *
 *
 *
 * ============================================================================
 * 9. FINAL DOES NOT NECESSARILY MEAN IMMUTABLE OBJECT
 * ============================================================================
 *
 * Example:
 *
 *     class Person {
 *         String name;
 *     }
 *
 *
 *     final Person person = new Person();
 *
 *     person.name = "John";
 *
 *
 * This is allowed.
 *
 *
 * Why?
 *
 * Because we did not change the reference.
 *
 * The reference still points to the SAME Person object.
 *
 *
 *     person ─────> Person Object
 *                       |
 *                       └── name = "John"
 *
 *
 * But this is NOT allowed:
 *
 *     person = new Person();
 *
 *
 * Because that changes the reference.
 *
 *
 * Therefore:
 *
 *     final reference
 *          ↓
 *     cannot point somewhere else
 *
 * But:
 *
 *     final reference
 *          ↓
 *     object may still be mutable
 *
 *
 *
 * ============================================================================
 * 10. STATIC + FINAL
 * ============================================================================
 *
 * Example:
 *
 *     class Direction {
 *
 *         static final Direction NORTH = new Direction();
 *     }
 *
 *
 * Break it down:
 *
 *
 *     static
 *         ↓
 *     one class-level reference
 *
 *
 *     final
 *         ↓
 *     reference cannot be reassigned
 *
 *
 * Therefore:
 *
 *     static final
 *         ↓
 *     one shared class-level reference
 *         +
 *     that reference cannot be redirected
 *
 *
 * Diagram:
 *
 *                    Direction CLASS
 *                          |
 *                        NORTH
 *                          |
 *                          v
 *                     NORTH OBJECT
 *
 *
 * You cannot do:
 *
 *     Direction.NORTH = new Direction();
 *
 *
 * because NORTH is final.
 *
 *
 *
 * ============================================================================
 * 11. MULTIPLE STATIC FINAL CONSTANTS
 * ============================================================================
 *
 * Example:
 *
 *     class Direction {
 *
 *         static final Direction NORTH = new Direction();
 *         static final Direction SOUTH = new Direction();
 *         static final Direction EAST  = new Direction();
 *         static final Direction WEST  = new Direction();
 *     }
 *
 *
 * This creates:
 *
 *     4 static references
 *     4 different objects
 *
 *
 * Diagram:
 *
 *                    Direction CLASS
 *                         |
 *          ┌──────────────┼──────────────┐
 *          |              |              |
 *          ↓              ↓              ↓
 *        NORTH          SOUTH           EAST        WEST
 *          |              |              |           |
 *          ↓              ↓              ↓           ↓
 *       Object #1       Object #2      Object #3  Object #4
 *
 *
 * IMPORTANT:
 *
 *     static does NOT mean one object total.
 *
 * It means each static member belongs to the class.
 *
 *
 *
 * ============================================================================
 * 12. WHY WOULD NORTH/SOUTH/EAST/WEST BE STATIC?
 * ============================================================================
 *
 * Suppose we wrote:
 *
 *     class Direction {
 *
 *         final Direction NORTH = new Direction();
 *     }
 *
 *
 * Now NORTH is NON-STATIC.
 *
 * That means every Direction object gets its own NORTH reference.
 *
 *
 * If:
 *
 *     Direction d1 = new Direction();
 *     Direction d2 = new Direction();
 *
 *
 * Then conceptually:
 *
 *     d1
 *      |
 *      └── NORTH ─────> Object #3
 *
 *
 *     d2
 *      |
 *      └── NORTH ─────> Object #4
 *
 *
 * We now have multiple NORTH references.
 *
 *
 * But logically we want:
 *
 *     Direction class
 *          |
 *        NORTH
 *          |
 *          v
 *       ONE NORTH OBJECT
 *
 *
 * Therefore NORTH should be static.
 *
 *
 *
 * ============================================================================
 * 13. NOW ENTER ENUM
 * ============================================================================
 *
 * Java enum is a special kind of class designed for a FIXED SET of objects.
 *
 *
 * Example:
 *
 *     enum Direction {
 *
 *         NORTH,
 *         SOUTH,
 *         EAST,
 *         WEST
 *     }
 *
 *
 * Conceptually think:
 *
 *     Direction CLASS
 *          |
 *          ├── NORTH ─────> Direction Object #1
 *          |
 *          ├── SOUTH ─────> Direction Object #2
 *          |
 *          ├── EAST ──────> Direction Object #3
 *          |
 *          └── WEST ──────> Direction Object #4
 *
 *
 * Therefore:
 *
 *     4 enum constants
 *         ↓
 *     4 fixed enum objects
 *
 *
 *
 * ============================================================================
 * 14. ENUM CONSTANTS ARE FIXED REFERENCES TO FIXED OBJECTS
 * ============================================================================
 *
 * When you write:
 *
 *     Direction.NORTH
 *
 * think:
 *
 *     Direction.NORTH
 *            |
 *            v
 *     one specific Direction enum object
 *
 *
 * Similarly:
 *
 *     Direction.SOUTH
 *            |
 *            v
 *     another specific Direction enum object
 *
 *
 * So:
 *
 *     NORTH → Object #1
 *     SOUTH → Object #2
 *     EAST  → Object #3
 *     WEST  → Object #4
 *
 *
 * These objects are created and controlled by Java's enum mechanism.
 *
 *
 *
 * ============================================================================
 * 15. ENUM CONSTANTS ARE CONCEPTUALLY STATIC FINAL
 * ============================================================================
 *
 * A useful mental model is:
 *
 *
 *     enum Direction {
 *         NORTH,
 *         SOUTH,
 *         EAST,
 *         WEST
 *     }
 *
 *
 * Think approximately like:
 *
 *
 *     class Direction {
 *
 *         public static final Direction NORTH = ...;
 *         public static final Direction SOUTH = ...;
 *         public static final Direction EAST  = ...;
 *         public static final Direction WEST  = ...;
 *
 *         private Direction(...) {
 *         }
 *     }
 *
 *
 * This is a MENTAL MODEL.
 *
 * Enum has special compiler/JVM behavior, so do not think Java
 * literally generates ordinary source code exactly like this.
 *
 *
 *
 * ============================================================================
 * 16. WHY STATIC FOR ENUM CONSTANTS?
 * ============================================================================
 *
 * Because enum constants belong to the ENUM CLASS itself.
 *
 *
 * We want:
 *
 *     Direction.NORTH
 *     Direction.SOUTH
 *     Direction.EAST
 *     Direction.WEST
 *
 *
 * We do NOT want to create a Direction object first:
 *
 *     Direction d = new Direction();
 *
 *     d.NORTH
 *
 *
 * That would make NORTH an instance member.
 *
 * Then every Direction object could have its own NORTH.
 *
 *
 * We want ONE class-level NORTH reference.
 *
 *
 * Therefore:
 *
 *     enum constant
 *          ↓
 *     class-level/static concept
 *
 *
 *
 * ============================================================================
 * 17. WHY FINAL FOR ENUM CONSTANTS?
 * ============================================================================
 *
 * We don't want:
 *
 *     Direction.NORTH = new Direction(...);
 *
 *
 * We want NORTH to permanently represent the NORTH enum object.
 *
 *
 * Therefore:
 *
 *     final
 *         ↓
 *     reference cannot be reassigned
 *
 *
 * So the mental model is:
 *
 *     static
 *         ↓
 *     class-level constant
 *
 *     final
 *         ↓
 *     cannot be reassigned
 *
 *
 *
 * ============================================================================
 * 18. ENUM WITH INSTANCE FIELD
 * ============================================================================
 *
 * Example:
 *
 *     enum EC2Event {
 *
 *         ADD("ADD"),
 *         EDIT("EDIT"),
 *         DELETE("DELETE");
 *
 *         private final String eventName;
 *
 *         EC2Event(String eventName) {
 *             this.eventName = eventName;
 *         }
 *     }
 *
 *
 * Think:
 *
 *
 *     EC2Event CLASS
 *          |
 *          ├── ADD OBJECT
 *          |     └── eventName = "ADD"
 *          |
 *          ├── EDIT OBJECT
 *          |     └── eventName = "EDIT"
 *          |
 *          └── DELETE OBJECT
 *                └── eventName = "DELETE"
 *
 *
 * eventName is NON-STATIC.
 *
 *
 * Why?
 *
 * Because eventName belongs to EACH enum object.
 *
 *
 * ADD needs:
 *
 *     "ADD"
 *
 * EDIT needs:
 *
 *     "EDIT"
 *
 * DELETE needs:
 *
 *     "DELETE"
 *
 *
 * If eventName were static, there would be only ONE eventName
 * shared by the entire EC2Event class.
 *
 *
 * That would be wrong.
 *
 *
 *
 * ============================================================================
 * 19. WHY eventName IS NOT STATIC
 * ============================================================================
 *
 * We need:
 *
 *     ADD object
 *         ↓
 *     eventName = "ADD"
 *
 *     EDIT object
 *         ↓
 *     eventName = "EDIT"
 *
 *     DELETE object
 *         ↓
 *     eventName = "DELETE"
 *
 *
 * Therefore:
 *
 *     private final String eventName;
 *
 *
 * NOT:
 *
 *     private static final String eventName;
 *
 *
 * Because static would create ONE shared eventName for the entire enum.
 *
 *
 *
 * ============================================================================
 * 20. ENUM CONSTRUCTOR
 * ============================================================================
 *
 * Example:
 *
 *     enum EC2Event {
 *
 *         ADD("ADD"),
 *         EDIT("EDIT");
 *
 *         private final String eventName;
 *
 *         private EC2Event(String eventName) {
 *             this.eventName = eventName;
 *         }
 *     }
 *
 *
 * When Java sees:
 *
 *     ADD("ADD")
 *
 * think:
 *
 *     create the ADD enum object
 *     and pass "ADD" to its constructor.
 *
 *
 * Similarly:
 *
 *     EDIT("EDIT")
 *
 * creates another enum object and passes "EDIT".
 *
 *
 * Conceptually:
 *
 *     ADD object
 *         eventName = "ADD"
 *
 *     EDIT object
 *         eventName = "EDIT"
 *
 *
 * Enum constructors cannot be called manually using new.
 *
 *
 *
 * ============================================================================
 * 21. WHY ENUM CONSTRUCTOR IS PRIVATE?
 * ============================================================================
 *
 * Enum values must be fixed.
 *
 * We do NOT want:
 *
 *     new EC2Event("TEST");
 *
 *
 * Otherwise someone could keep creating new values.
 *
 *
 * Enum says:
 *
 *     These are the ONLY allowed values.
 *
 *
 * Therefore enum controls object creation.
 *
 *
 *
 * ============================================================================
 * 22. NOW LOOKUP MAP
 * ============================================================================
 *
 * Suppose:
 *
 *     enum EC2Event {
 *
 *         ADD("ADD"),
 *         EDIT("EDIT"),
 *         DELETE("DELETE");
 *     }
 *
 *
 * We want:
 *
 *     "ADD"    → ADD object
 *     "EDIT"   → EDIT object
 *     "DELETE" → DELETE object
 *
 *
 * This is what a lookup map does.
 *
 *
 *     Map<String, EC2Event>
 *
 *
 * Diagram:
 *
 *     LOOKUP
 *       |
 *       ├── "ADD"    → ADD object
 *       ├── "EDIT"   → EDIT object
 *       └── "DELETE" → DELETE object
 *
 *
 *
 * ============================================================================
 * 23. WHY LOOKUP MAP IS STATIC
 * ============================================================================
 *
 * The lookup map is not information about ONE enum object.
 *
 * It is a lookup table for ALL enum objects.
 *
 *
 * Therefore we want:
 *
 *     ONE map for the entire enum class.
 *
 *
 * So:
 *
 *     private static final Map<String, EC2Event> LOOKUP;
 *
 *
 * Diagram:
 *
 *                   EC2Event CLASS
 *                         |
 *                         |
 *                       LOOKUP
 *                         |
 *          ┌──────────────┼──────────────┐
 *          ↓              ↓              ↓
 *        "ADD"          "EDIT"        "DELETE"
 *          |              |              |
 *          ↓              ↓              ↓
 *        ADD            EDIT          DELETE
 *
 *
 * One map.
 *
 * Many enum objects.
 *
 *
 *
 * ============================================================================
 * 24. WHAT IF LOOKUP IS NOT STATIC?
 * ============================================================================
 *
 * If we write:
 *
 *     private final Map<String, EC2Event> LOOKUP;
 *
 * then LOOKUP belongs to every enum object.
 *
 *
 * Conceptually:
 *
 *     ADD object
 *         |
 *         └── LOOKUP map #1
 *
 *
 *     EDIT object
 *         |
 *         └── LOOKUP map #2
 *
 *
 *     DELETE object
 *         |
 *         └── LOOKUP map #3
 *
 *
 * We don't want this.
 *
 *
 * We would have multiple maps containing essentially the same
 * lookup information.
 *
 *
 * We want:
 *
 *     EC2Event class
 *          |
 *       ONE LOOKUP
 *
 *
 * Therefore LOOKUP is static.
 *
 *
 *
 * ============================================================================
 * 25. STATIC BLOCK FOR LOOKUP INITIALIZATION
 * ============================================================================
 *
 * Example:
 *
 *     private static final Map<String, EC2Event> LOOKUP;
 *
 *     static {
 *
 *         Map<String, EC2Event> map = new HashMap<>();
 *
 *         for (EC2Event event : EC2Event.values()) {
 *             map.put(event.eventName, event);
 *         }
 *
 *         LOOKUP = Collections.unmodifiableMap(map);
 *     }
 *
 *
 * The static block runs when the enum class is initialized.
 *
 *
 * It goes through:
 *
 *     ADD
 *     EDIT
 *     DELETE
 *
 *
 * and creates:
 *
 *     "ADD"    → ADD
 *     "EDIT"   → EDIT
 *     "DELETE" → DELETE
 *
 *
 *
 * ============================================================================
 * 26. WHY STATIC BLOCK CAN ACCESS event.eventName
 * ============================================================================
 *
 * This is allowed:
 *
 *     for (EC2Event event : EC2Event.values()) {
 *
 *         event.eventName
 *     }
 *
 *
 * Why?
 *
 * Because 'event' is a specific enum OBJECT.
 *
 *
 * Static code cannot directly say:
 *
 *     eventName
 *
 * because eventName belongs to an object.
 *
 *
 * But it can say:
 *
 *     event.eventName
 *
 * because now Java knows WHICH object we are talking about.
 *
 *
 * Think:
 *
 *     event
 *       ↓
 *     specific EC2Event object
 *       ↓
 *     its eventName
 *
 *
 *
 * ============================================================================
 * 27. STATIC vs INSTANCE IN ONE DIAGRAM
 * ============================================================================
 *
 *
 *                    EC2Event CLASS
 *                          |
 *          ┌───────────────┴────────────────┐
 *          |                                |
 *       STATIC                         NON-STATIC
 *          |                                |
 *       LOOKUP                         eventName
 *          |                                |
 *     one shared map              each object gets
 *          |                      its own eventName
 *          |                                |
 *          ↓                                ↓
 *
 *     "ADD" → ADD object            ADD → "ADD"
 *     "EDIT" → EDIT object          EDIT → "EDIT"
 *     "DELETE" → DELETE object      DELETE → "DELETE"
 *
 *
 *
 * ============================================================================
 * 28. FINAL LOOKUP
 * ============================================================================
 *
 *     private static final Map<String, EC2Event> LOOKUP;
 *
 *
 * Break it down:
 *
 *
 * private
 *     ↓
 * outside code cannot directly access the map
 *
 *
 * static
 *     ↓
 * one shared map for the whole enum class
 *
 *
 * final
 *     ↓
 * LOOKUP reference cannot be reassigned
 *
 *
 * Map<String, EC2Event>
 *     ↓
 * String key
 *     EC2Event value
 *
 *
 *
 * ============================================================================
 * 29. FINAL MAP DOES NOT MEAN MAP CONTENT CANNOT CHANGE
 * ============================================================================
 *
 * Example:
 *
 *     final Map<String, String> map = new HashMap<>();
 *
 *
 * This is allowed:
 *
 *     map.put("A", "B");
 *
 *
 * This is NOT allowed:
 *
 *     map = new HashMap<>();
 *
 *
 * Why?
 *
 *     final protects the REFERENCE.
 *
 * It does not automatically make the object immutable.
 *
 *
 * If we want a read-only map after initialization:
 *
 *     Collections.unmodifiableMap(map)
 *
 *
 *
 * ============================================================================
 * 30. COMPLETE ENUM EXAMPLE
 * ============================================================================
 *
 *
 * import java.util.Collections;
 * import java.util.HashMap;
 * import java.util.Locale;
 * import java.util.Map;
 *
 *
 * public enum EC2Event {
 *
 *     ADD_NEW_SCHEDULED_TASK("ADD NEW SCHEDULED TASK"),
 *     EDIT_SCHEDULED_TASK("EDIT SCHEDULED TASK"),
 *     ENABLE_SCHEDULED_TASK("ENABLE SCHEDULED TASK"),
 *     DISABLE_SCHEDULED_TASK("DISABLE SCHEDULED TASK");
 *
 *
 *     // INSTANCE FIELD
 *     //
 *     // Every enum object gets its OWN eventName.
 *     //
 *     private final String eventName;
 *
 *
 *     // CLASS-LEVEL FIELD
 *     //
 *     // Only ONE lookup map exists for the entire enum class.
 *     //
 *     private static final Map<String, EC2Event> LOOKUP;
 *
 *
 *     // STATIC BLOCK
 *     //
 *     // Runs when the enum class is initialized.
 *     //
 *     // Builds ONE lookup map containing ALL enum objects.
 *
 *     static {
 *
 *         Map<String, EC2Event> map = new HashMap<>();
 *
 *         for (EC2Event event : EC2Event.values()) {
 *
 *             // event is a specific enum object.
 *             //
 *             // Therefore event.eventName accesses
 *             // that object's own eventName.
 *
 *             map.put(
 *                 event.eventName.toUpperCase(Locale.ROOT),
 *                 event
 *             );
 *         }
 *
 *         LOOKUP = Collections.unmodifiableMap(map);
 *     }
 *
 *
 *     // ENUM CONSTRUCTOR
 *     //
 *     // Each enum constant passes its own eventName.
 *
 *     private EC2Event(String eventName) {
 *         this.eventName = eventName;
 *     }
 *
 *
 *     public String getEventName() {
 *         return eventName;
 *     }
 *
 *
 *     // SEARCH USING THE COMMON STATIC LOOKUP MAP.
 *
 *     public static EC2Event search(String eventName) {
 *
 *         if (eventName == null || eventName.trim().isEmpty()) {
 *             return null;
 *         }
 *
 *         return LOOKUP.get(
 *             eventName.trim().toUpperCase(Locale.ROOT)
 *         );
 *     }
 * }
 *
 *
 *
 * ============================================================================
 * 31. COMPLETE MEMORY DIAGRAM
 * ============================================================================
 *
 *
 *                         EC2Event CLASS
 *                              |
 *             ┌────────────────┴─────────────────┐
 *             |                                  |
 *        STATIC MEMBERS                     ENUM OBJECTS
 *             |                                  |
 *          LOOKUP                         ┌──────┼──────┐
 *             |                           |      |      |
 *             |                           ↓      ↓      ↓
 *             |                          ADD   EDIT  ENABLE
 *             |                           |      |      |
 *             |                           |      |      |
 *             |                       eventName eventName
 *             |                           |      |
 *             |                          "ADD"  "EDIT"
 *             |
 *             ├── "ADD"    ───────────────> ADD
 *             |
 *             ├── "EDIT"   ───────────────> EDIT
 *             |
 *             └── "ENABLE" ───────────────> ENABLE
 *
 *
 *
 * ============================================================================
 * 32. THE MOST IMPORTANT MEMORY TRICK
 * ============================================================================
 *
 *
 * CLASS
 *     ↓
 * blueprint / type
 *
 *
 * OBJECT
 *     ↓
 * actual instance created from the class
 *
 *
 * REFERENCE
 *     ↓
 * variable that points to an object
 *
 *
 * NON-STATIC
 *     ↓
 * belongs to each object
 *
 *
 * STATIC
 *     ↓
 * belongs to the class
 *     ↓
 * one shared class-level member/reference
 *
 *
 * FINAL
 *     ↓
 * reference cannot be reassigned
 *
 *
 * STATIC FINAL
 *     ↓
 * one class-level reference
 *     +
 * reference cannot be reassigned
 *
 *
 * ENUM
 *     ↓
 * special Java type for a fixed set of constants/objects
 *
 *
 * ENUM CONSTANT
 *     ↓
 * fixed reference to a specific enum object
 *
 *
 * ENUM INSTANCE FIELD
 *     ↓
 * each enum object gets its own copy
 *
 *
 * ENUM STATIC FIELD
 *     ↓
 * one shared field for the whole enum class
 *
 *
 * LOOKUP MAP
 *     ↓
 * usually static because one map is needed
 * for looking up ALL enum objects
 *
 *
 *
 * ============================================================================
 * 33. FINAL ONE-LINE RULES
 * ============================================================================
 *
 *
 * static:
 *     "One shared member for the CLASS."
 *
 *
 * non-static:
 *     "Each OBJECT gets its own member."
 *
 *
 * final:
 *     "This reference cannot be redirected."
 *
 *
 * static final:
 *     "One class-level reference that cannot be redirected."
 *
 *
 * reference variable:
 *     "A variable that points to an object."
 *
 *
 * object:
 *     "The actual instance in memory."
 *
 *
 * enum:
 *     "A special class-like type with a fixed set of enum objects."
 *
 *
 * enum constant:
 *     "A fixed class-level reference to one enum object."
 *
 *
 * enum instance field:
 *     "Data belonging separately to each enum object."
 *
 *
 * enum static field:
 *     "Data shared by the entire enum class."
 *
 *
 * LOOKUP:
 *     "One shared map that maps external values to enum objects."
 *
 *
 * ============================================================================
 * 34. THE FINAL PICTURE
 * ============================================================================
 *
 *
 *                         EC2Event CLASS
 *                              |
 *               ┌──────────────┴──────────────┐
 *               |                             |
 *          STATIC MEMBERS               ENUM OBJECTS
 *               |                             |
 *            LOOKUP                     ADD object
 *               |                       EDIT object
 *               |                       ENABLE object
 *               |                       DISABLE object
 *               |
 *               ├── "ADD" ────────> ADD object
 *               ├── "EDIT" ───────> EDIT object
 *               ├── "ENABLE" ─────> ENABLE object
 *               └── "DISABLE" ────> DISABLE object
 *
 *
 * Each enum object:
 *
 *     ADD object
 *         └── its own eventName
 *
 *     EDIT object
 *         └── its own eventName
 *
 *     ENABLE object
 *         └── its own eventName
 *
 *
 * While LOOKUP:
 *
 *     belongs to the class
 *     ↓
 *     static
 *     ↓
 *     one shared map
 *
 *
 * ============================================================================
 * THE GOLDEN RULE
 * ============================================================================
 *
 *
 *     "Ask WHO owns this data?"
 *
 *
 * If ONE PARTICULAR OBJECT owns it:
 *
 *     → NON-STATIC
 *
 *
 * If THE WHOLE CLASS owns it:
 *
 *     → STATIC
 *
 *
 * If the REFERENCE should never point somewhere else:
 *
 *     → FINAL
 *
 *
 * If it is a FIXED SET of predefined objects:
 *
 *     → ENUM
 *
 *
 * ============================================================================
 */