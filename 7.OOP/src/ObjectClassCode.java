import java.util.Objects;

public class ObjectClassCode {

    public static void main(String[] args) throws CloneNotSupportedException {

        // ============================================================
        // 1. toString()
        // ============================================================

        Student s1 = new Student();
        s1.name = "Aditya";
        s1.age = 28;

        // System.out.println(s1);
        // Automatically calls s1.toString()
        // Output: Aditya , 28


        // ============================================================
        // 2. equals()
        // ============================================================

        Student s2 = new Student();
        s2.name = "Aditya";
        s2.age = 28;

        // == checks whether both references
        // point to the SAME object.
        //
        // System.out.println(s1 == s2);
        // false
        //
        // equals() checks logical/value equality
        // because we have overridden it.
        //
        // System.out.println(s1.equals(s2));
        // true


        // ============================================================
        // 3. hashCode()
        // ============================================================

        // If equals() is true, hashCode() MUST be same.
        //
        // System.out.println(s1.hashCode() == s2.hashCode());
        // true


        // ============================================================
        // 4. getClass()
        // ============================================================

        // Returns the EXACT runtime class.
        //
        // System.out.println(s1.getClass().getName());
        // ObjectClassCode$Student
        //
        // System.out.println(s2.getClass().getName());
        // ObjectClassCode$Student


        // ============================================================
        // 5. instanceof
        // ============================================================

        // Checks whether object is an instance of a class
        // OR any of its subclasses.
        //
        // System.out.println(s1 instanceof Object);
        // true


        // ============================================================
        // 6. clone()
        // ============================================================

        // Student implements Cloneable,
        // so clone() is allowed.
        //
        // clone() creates a SHALLOW COPY.
        //
        Student s3 = (Student) s1.clone();

        System.out.println(s3.name);
        System.out.println(s3.age);


        // ============================================================
        // 7. getClass() vs instanceof
        // ============================================================

        Animal a = new Animal();
        Animal d = new Dog();

        // getClass() -> EXACT runtime class
        //
        System.out.println(a.getClass().getName());
        // ObjectClassCode$Animal
        //
        System.out.println(d.getClass().getName());
        // ObjectClassCode$Dog


        // instanceof -> checks type + parent types
        //
        System.out.println(a instanceof Dog);
        // false
        //
        System.out.println(d instanceof Animal);
        // true


        // ============================================================
        // IMPORTANT DIFFERENCE
        // ============================================================

        // Animal d = new Dog();
        //
        // d.getClass() == Dog.class
        // -> true
        //
        // d instanceof Dog
        // -> true
        //
        // d instanceof Animal
        // -> true
        //
        // getClass() asks:
        // "What EXACT object was created?"
        //
        // instanceof asks:
        // "Can this object be treated as this type?"


        // ============================================================
        // OBJECT CLASS QUICK FLOW
        // ============================================================

        // Object
        //   |
        //   +-- toString()  -> String representation
        //   |
        //   +-- equals()    -> Logical equality
        //   |
        //   +-- hashCode()  -> Hash value
        //   |
        //   +-- getClass()  -> Exact runtime class
        //   |
        //   +-- clone()     -> Shallow copy
        //   |
        //   +-- wait()      -> Thread waits
        //   |
        //   +-- notify()    -> Wake one waiting thread
        //   |
        //   +-- notifyAll() -> Wake all waiting threads
    }


    // ================================================================
    // STUDENT CLASS
    // ================================================================

    static class Student extends Object implements Cloneable {

        String name;
        int age;


        // ------------------------------------------------------------
        // toString()
        // ------------------------------------------------------------

        @Override
        public String toString() {
            return name + " , " + age;
        }


        // ------------------------------------------------------------
        // equals()
        // ------------------------------------------------------------

        @Override
        public boolean equals(Object obj) {

            // Same reference?
            if (this == obj) {
                return true;
            }

            // Comparing with null?
            if (obj == null) {
                return false;
            }

            // Check exact class.
            // Prevents invalid type comparison/casting.
            if (obj.getClass() != this.getClass()) {
                return false;
            }

            Student s = (Student) obj;

            // IMPORTANT:
            // Use Objects.equals() for String comparison.
            //
            // DO NOT use:
            // this.name == s.name
            //
            // == compares String references.
            // Objects.equals() compares String values.
            return Objects.equals(this.name, s.name)
                    && this.age == s.age;
        }


        // ------------------------------------------------------------
        // hashCode()
        // ------------------------------------------------------------

        @Override
        public int hashCode() {

            // Must use the SAME fields used in equals().
            //
            // If:
            // s1.equals(s2) == true
            //
            // Then:
            // s1.hashCode() == s2.hashCode()
            //
            return Objects.hash(name, age);
        }


        // ------------------------------------------------------------
        // clone()
        // ------------------------------------------------------------

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }


    // ================================================================
    // INHERITANCE EXAMPLE
    // ================================================================

    static class Animal {
    }


    static class Dog extends Animal {
    }
}