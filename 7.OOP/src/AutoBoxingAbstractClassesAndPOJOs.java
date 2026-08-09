public class AutoBoxingAbstractClassesAndPOJOs {

    // ============================================================
    // 1. ONE PUBLIC CLASS PER FILE
    // ============================================================

    // A .java file can contain multiple classes,
    // but it can have only ONE public top-level class.
    //
    // If a class is public, the filename must exactly match
    // the public class name.
    //
    // Example:
    // Student.java
    //
    // public class Student {
    // }
    //
    // You can have non-public classes in the same file:
    //
    // public class Student {
    // }
    //
    // class Address {
    // }
    //
    // class Course {
    // }
    //
    // But you cannot have:
    //
    // public class Student { }
    // public class Teacher { }
    //
    // in the same file.
    //
    // IMPORTANT:
    // This rule is NOT because the JVM needs to find the main() method.
    // The rule provides a clear mapping between a public class
    // and its .java source file.
    //
    // main() is simply the standard entry point used to start
    // a Java application:
    //
    // public static void main(String[] args) {
    // }


    // ============================================================
    // 2. PRIMITIVES VS WRAPPER CLASSES
    // ============================================================

    // Primitive Types:
    // int, float, double, boolean, char, long, short, byte
    //
    // They represent simple values and are generally more
    // memory-efficient than wrapper objects.
    //
    // Example:
    // int age = 20;
    //
    // Wrapper Classes:
    // Integer, Float, Double, Boolean, Character, Long, etc.
    //
    // They are objects that represent primitive values.
    //
    // Example:
    // Integer age = 20;
    //
    // IMPORTANT:
    // Saying "primitives are always stored in Stack and wrappers
    // are always stored in Heap" is an oversimplification.
    //
    // A local primitive may be stored as part of a stack frame,
    // while a primitive field can be part of an object on the Heap.
    //
    // Wrapper objects are objects and generally have object/reference
    // overhead.


    // ============================================================
    // 3. WHY DO WE NEED WRAPPER CLASSES?
    // ============================================================

    // Java Collections work with Objects, not primitive types.
    //
    // This is NOT valid:
    //
    // ArrayList<int> list = new ArrayList<>();
    //
    // This is valid:
    //
    // ArrayList<Integer> list = new ArrayList<>();
    //
    // Therefore, when we need to use a primitive value where
    // an Object is required, we use its Wrapper Class.
    //
    // int     -> Integer
    // long    -> Long
    // double  -> Double
    // float   -> Float
    // boolean -> Boolean
    // char    -> Character


    // ============================================================
    // 4. AUTOBOXING
    // ============================================================

    // Autoboxing = Automatic conversion of Primitive -> Wrapper.
    //
    // Example:
    //
    // Integer x = 10;
    //
    // Conceptually, the compiler converts it to:
    //
    // Integer x = Integer.valueOf(10);
    //
    // Another example:
    //
    // ArrayList<Integer> numbers = new ArrayList<>();
    // numbers.add(10);
    //
    // 10 is an int, but ArrayList<Integer> requires Integer.
    // Java automatically boxes int into Integer.


    // ============================================================
    // 5. UNBOXING
    // ============================================================

    // Unboxing = Automatic conversion of Wrapper -> Primitive.
    //
    // Example:
    //
    // Integer x = 10;
    // int y = x;
    //
    // Conceptually:
    //
    // int y = x.intValue();
    //
    // Examples:
    //
    // Long x = 100L;
    // long y = x;
    //
    // Double x = 10.5;
    // double y = x;


    // ============================================================
    // 6. NULL POINTER EXCEPTION WITH UNBOXING
    // ============================================================

    // Wrapper classes are Objects, so they can contain null.
    //
    // Primitive types CANNOT contain null.
    //
    // Example:
    //
    // Integer age = null;
    //
    // This is valid because Integer is an Object.
    //
    // But:
    //
    // int x = age;
    //
    // causes NullPointerException.
    //
    // WHY?
    //
    // Java needs to unbox Integer into int.
    //
    // Conceptually:
    //
    // int x = age.intValue();
    //
    // But age is null:
    //
    // age == null
    //
    // So Java effectively tries:
    //
    // null.intValue();
    //
    // You cannot call an instance method on null.
    // Therefore -> NullPointerException.
    //
    //
    // IMPORTANT:
    // NPE can happen anywhere Java needs to unbox a null Wrapper.
    //
    // Example 1:
    //
    // Integer x = null;
    // int y = x;
    //
    // Example 2:
    //
    // Integer x = null;
    // int y = x + 10;
    //
    // Java needs x.intValue() for the calculation.
    //
    // Example 3:
    //
    // Integer x = null;
    // if (x > 10) {
    // }
    //
    // Java needs to unbox x before comparison.
    //
    // Example 4:
    //
    // void printAge(int age) {
    // }
    //
    // Integer age = null;
    // printAge(age);
    //
    // Java tries to unbox age before passing it to the method.
    //
    // General rule:
    //
    // Wrapper == null
    //        +
    // Java needs primitive value
    //        =
    // NullPointerException


    // ============================================================
    // 7. WRAPPER CACHING
    // ============================================================

    // Java caches certain Wrapper objects.
    //
    // Integer values from -128 to 127 are guaranteed to be cached.
    //
    // Example:
    //
    // Integer a = 100;
    // Integer b = 100;
    //
    // System.out.println(a == b);
    //
    // Usually -> true
    //
    // because both references can point to the same cached Integer.
    //
    // Example:
    //
    // Integer a = 200;
    // Integer b = 200;
    //
    // System.out.println(a == b);
    //
    // Can be -> false
    //
    // because they may refer to different Integer objects.
    //
    // IMPORTANT:
    // Never depend on == for comparing Wrapper values.


    // ============================================================
    // 8. == VS equals() WITH WRAPPERS
    // ============================================================

    // == compares object references when used with Wrapper objects.
    //
    // equals() compares the actual value for Wrapper classes.
    //
    // WRONG:
    //
    // Integer a = 200;
    // Integer b = 200;
    //
    // a == b
    //
    // RIGHT:
    //
    // a.equals(b)
    //
    // If null is possible, use:
    //
    // Objects.equals(a, b);
    //
    // Objects.equals() safely handles null values.


    // ============================================================
    // 9. POJO - PLAIN OLD JAVA OBJECT
    // ============================================================

    // POJO = Plain Old Java Object.
    //
    // A POJO is a simple Java class that is not tightly coupled
    // to a particular framework.
    //
    // A typical POJO contains:
    //
    // - Private fields
    // - Constructor
    // - Getters
    // - Setters
    //
    // Example:
    //
    // public class Student {
    //
    //     private Integer id;
    //     private String name;
    //     private Integer age;
    //
    //     public Student() {
    //     }
    //
    //     public Student(Integer id, String name, Integer age) {
    //         this.id = id;
    //         this.name = name;
    //         this.age = age;
    //     }
    //
    //     public Integer getAge() {
    //         return age;
    //     }
    //
    //     public void setAge(Integer age) {
    //         this.age = age;
    //     }
    // }
    //
    // IMPORTANT:
    // Getters, setters and constructors are common in POJOs,
    // but they are not a strict requirement for something to
    // conceptually be called a POJO.


    // ============================================================
    // 10. ANEMIC VS RICH DOMAIN MODEL
    // ============================================================

    // ANEMIC DOMAIN MODEL:
    //
    // A class that mainly contains data with getters and setters
    // but has little or no business logic.
    //
    // Example:
    //
    // Student
    //   -> name
    //   -> age
    //   -> marks
    //   -> getters/setters
    //
    // DTOs are commonly designed this way.
    //
    //
    // RICH DOMAIN MODEL:
    //
    // A class contains both:
    //
    // State + Business Logic
    //
    // Example:
    //
    // public boolean isEligibleForGraduation() {
    //     return marks != null && marks >= 40;
    // }
    //
    // Here the Student class contains business logic related
    // to the Student's own data.


    // ============================================================
    // 11. DTOs - WHY USE INTEGER INSTEAD OF INT?
    // ============================================================

    // Suppose the database has:
    //
    // age = NULL
    //
    // If the DTO contains:
    //
    // private int age;
    //
    // int cannot represent null.
    //
    // Therefore, 0 may be used as the default value when no value
    // is assigned.
    //
    // This can create a problem:
    //
    // Database:
    // age = NULL
    //
    // Java:
    // age = 0
    //
    // Now the application cannot distinguish:
    //
    // 0 = actual age
    //
    // from:
    //
    // 0 = no age value was provided
    //
    //
    // Using:
    //
    // private Integer age;
    //
    // allows:
    //
    // age = 25
    //
    // OR:
    //
    // age = null
    //
    // Therefore, Integer preserves the difference between
    // "actual value" and "missing value".
    //
    // This is especially useful for:
    // - Database fields
    // - DTOs
    // - API request/response objects
    // - Optional fields


    // ============================================================
    // 12. QUICK REVISION
    // ============================================================

    // Primitive:
    // int
    //   -> simple value
    //   -> cannot be null
    //
    // Wrapper:
    // Integer
    //   -> Object
    //   -> can be null
    //
    // Autoboxing:
    // int -> Integer
    //
    // Unboxing:
    // Integer -> int
    //
    // Null + Unboxing:
    // Integer x = null;
    // int y = x;
    // -> NullPointerException
    //
    // Wrapper comparison:
    // ==       -> reference comparison
    // equals() -> value comparison
    //
    // Integer caching:
    // -128 to 127 are guaranteed cached.
    //
    // POJO:
    // Simple Java object used to represent data/domain concepts.
    //
    // Anemic:
    // Data + getters/setters
    //
    // Rich:
    // Data + business logic
    //
    // DTO:
    // Prefer Integer over int when null has meaning.

}