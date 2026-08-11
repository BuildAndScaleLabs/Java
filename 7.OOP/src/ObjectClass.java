public class ObjectClass {

    // ============================================================
    // 1. ROOT OF ALL CLASSES
    // ============================================================

    // Object is the root/cosmic parent of every Java class.
    // It belongs to java.lang -> no import required.
    //
    // class Student {}
    // is treated as:
    // class Student extends Object {}
    //
    // class EngStudent extends Student {}
    // EngStudent -> Student -> Object
    //
    // Therefore every object can be referenced by Object:
    //
    // Object obj = new Student();


    // ============================================================
    // 2. IMPORTANT METHODS OF OBJECT
    // ============================================================

    // A. toString()
    //
    // Purpose:
    // Returns String representation of an object.
    //
    // Default:
    // ClassName@HexadecimalHashCode
    //
    // Example:
    // Student s = new Student("Sahil", 28);
    // System.out.println(s);
    //
    // Without override:
    // Student@1a2b3c
    //
    // With override:
    // Student[name=Sahil, age=28]
    //
    // IMPORTANT:
    // System.out.println(obj)
    // internally calls obj.toString().


    // B. getClass()
    //
    // Purpose:
    // Returns the EXACT runtime class of an object.
    //
    // Example:
    //
    // Object obj = new Student();
    //
    // obj.getClass().getName()
    // -> Student
    //
    // obj.getClass() == Student.class
    // -> true
    //
    // IMPORTANT:
    // getClass() is final -> cannot be overridden.
    //
    // getClass() vs instanceof:
    //
    // getClass()  -> exact runtime type
    // instanceof  -> checks type + child types


    // Example:
    //
    // Object obj = new Student();
    //
    // obj.getClass() == Student.class
    // -> true
    //
    // obj instanceof Student
    // -> true
    //
    // obj instanceof Object
    // -> true


    // C. equals(Object obj)
    //
    // Purpose:
    // Compares objects for equality.
    //
    // DEFAULT:
    // Same logical behavior as ==
    //
    // == checks whether two references point
    // to the SAME object.
    //
    // Example:
    //
    // Student s1 = new Student("Sahil");
    // Student s2 = new Student("Sahil");
    //
    // s1 == s2
    // -> false
    //
    // s1.equals(s2)
    // -> false by default
    //
    // If we override equals() to compare name:
    // s1.equals(s2)
    // -> true


    // ============================================================
    // 3. == vs equals()
    // ============================================================

    // == with objects:
    // Checks reference identity.
    //
    // equals():
    // Checks logical/value equality IF overridden.
    //
    // Example:
    //
    // String s1 = new String("Java");
    // String s2 = new String("Java");
    //
    // s1 == s2
    // -> false
    //
    // s1.equals(s2)
    // -> true
    //
    // String overrides equals().


    // D. hashCode()
    //
    // Purpose:
    // Returns an integer hash value for an object.
    //
    // Heavily used by:
    // HashMap, HashSet, Hashtable, etc.
    //
    // Collections use hashCode() first to find
    // the possible bucket quickly.


    // ============================================================
    // 4. equals() + hashCode() CONTRACT
    // ============================================================

    // GOLDEN RULE:
    //
    // If:
    // obj1.equals(obj2) == true
    //
    // Then MUST:
    // obj1.hashCode() == obj2.hashCode()
    //
    // BUT:
    // Same hashCode does NOT mean objects are equal.
    //
    // Different objects CAN have the same hashCode.
    // This is called a HASH COLLISION.
    //
    // Therefore:
    //
    // equals() true
    //      ↓
    // hashCode() MUST be same
    //
    // hashCode() same
    //      ↓
    // equals() MAY be true OR false


    // ============================================================
    // 5. WHY HASHCODE MATTERS
    // ============================================================

    // Example:
    //
    // Student s1 = new Student("Sahil");
    // Student s2 = new Student("Sahil");
    //
    // If equals() compares name:
    // s1.equals(s2) -> true
    //
    // Then hashCode() must also be based on name.
    //
    // If equals() is overridden but hashCode() is NOT:
    // equal objects may have different hash codes.
    //
    // HashSet can then store both objects.
    //
    // IMPORTANT:
    // Always override equals() and hashCode() TOGETHER.


    // ============================================================
    // 6. HashSet LOOKUP IDEA
    // ============================================================

    // When HashSet checks an object:
    //
    // 1. hashCode()
    //       ↓
    // 2. Find possible bucket
    //       ↓
    // 3. equals()
    //       ↓
    // 4. Decide whether object already exists
    //
    // So:
    // hashCode() -> narrows the search
    // equals()   -> confirms equality
    //
    // This is why breaking the contract causes problems.


    // E. clone()
    //
    // Purpose:
    // Creates a copy of an object.
    //
    // Default behavior:
    // SHALLOW COPY.
    //
    // IMPORTANT:
    // Class must implement Cloneable.
    //
    // Cloneable is a MARKER INTERFACE:
    // - No methods
    // - Acts as a permission/flag for cloning
    //
    // Without Cloneable:
    // CloneNotSupportedException
    //
    // Example:
    //
    // class Student implements Cloneable {
    //
    //     @Override
    //     public Student clone() throws CloneNotSupportedException {
    //         return (Student) super.clone();
    //     }
    // }
    //
    // Student s2 = s1.clone();


    // ============================================================
    // 7. SHALLOW COPY vs DEEP COPY
    // ============================================================

    // Shallow copy:
    // Primitive values are copied.
    // Reference fields still point to the SAME objects.
    //
    // Example:
    //
    // Student
    //   |
    //   +---- Address ----> Address Object
    //
    // After shallow clone:
    //
    // Student 1 ----+
    //               |
    //               +----> SAME Address Object
    //               |
    // Student 2 ----+
    //
    // So changing shared mutable objects can affect both.
    //
    // Deep copy:
    // Creates separate copies of referenced objects too.


    // F. wait()
    // G. notify()
    // H. notifyAll()
    //
    // These methods are related to THREAD COMMUNICATION.
    //
    // wait():
    // Current thread releases the object's monitor
    // and waits.
    //
    // notify():
    // Wakes ONE waiting thread.
    //
    // notifyAll():
    // Wakes ALL waiting threads.
    //
    // IMPORTANT:
    // These methods must be called while owning
    // the object's monitor, normally inside synchronized code.
    //
    // Otherwise:
    // IllegalMonitorStateException


    // ============================================================
    // 8. finalize() -> HISTORICAL / AVOID
    // ============================================================

    // finalize() was historically associated with
    // cleanup before garbage collection.
    //
    // IMPORTANT:
    // DO NOT use finalize() for resource cleanup.
    //
    // It is deprecated and should be avoided.
    //
    // Prefer:
    // try-with-resources
    // AutoCloseable
    //
    // Example:
    //
    // try (FileInputStream file = ...) {
    //     // use resource
    // }
    //
    // Resource is automatically closed.


    // ============================================================
    // 9. OBJECT CLASS QUICK MEMORY MAP
    // ============================================================

    // Object
    //  |
    //  +-- toString()   -> String representation
    //  |
    //  +-- equals()     -> logical equality
    //  |
    //  +-- hashCode()   -> hash value
    //  |
    //  +-- getClass()   -> exact runtime class
    //  |
    //  +-- clone()      -> shallow copy
    //  |
    //  +-- wait()       -> thread waits
    //  |
    //  +-- notify()     -> wake one thread
    //  |
    //  +-- notifyAll()  -> wake all waiting threads
    //  |
    //  +-- finalize()   -> deprecated; DO NOT rely on it


    // ============================================================
    // 10. MOST IMPORTANT INTERVIEW RULES
    // ============================================================

    // 1. Every class ultimately extends Object.
    //
    // 2. Object is the parent of all reference types,
    //    but primitives are NOT objects.
    //
    // 3. == checks reference identity.
    //
    // 4. equals() checks logical equality when overridden.
    //
    // 5. If equals() is overridden,
    //    hashCode() should ALSO be overridden.
    //
    // 6. Equal objects MUST have equal hash codes.
    //
    // 7. Same hash code does NOT guarantee equality.
    //
    // 8. getClass() gives the exact runtime class.
    //
    // 9. clone() performs a shallow copy by default.
    //
    // 10. Cloneable is a marker interface.
    //
    // 11. wait(), notify(), notifyAll() are used
    //     for thread communication.
    //
    // 12. finalize() is deprecated -> don't use it
    //     for resource cleanup.
}