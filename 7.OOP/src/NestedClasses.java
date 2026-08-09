public class NestedClasses {

    /*
    ============================================================
    JAVA NESTED CLASSES
    ============================================================

    A Nested Class is a class declared inside another class.

    Java has 4 types of nested classes:

    1. Static Nested Class
    2. Inner Class
    3. Local Class
    4. Anonymous Inner Class
    */


    /*
    ============================================================
    1. STATIC NESTED CLASS
    ============================================================

    DECLARATION:

        class Outer {
            static class Inner {
            }
        }

    THEORY:

    A Static Nested Class is a class declared inside another
    class using the 'static' keyword.

    It belongs to the OUTER CLASS, not to a particular object
    of the Outer class.

    Therefore, we do NOT need to create an Outer object.

    Syntax:

        Outer.Inner obj = new Outer.Inner();

    A Static Nested Class can directly access:
        - static variables
        - static methods
        - private static members

    It cannot directly access non-static members of Outer
    because it is not connected to a particular Outer object.

    REAL-WORLD USE:
        Builder Pattern commonly uses a Static Nested Class.
    */

    static class StaticNested {

        static void display() {

            System.out.println("This is Static Nested Class");

        }
    }


    /*
    ============================================================
    2. INNER CLASS / NON-STATIC NESTED CLASS
    ============================================================

    DECLARATION:

        class Outer {
            class Inner {
            }
        }

    THEORY:

    An Inner Class is a nested class without the static keyword.

    It is associated with a SPECIFIC OBJECT of the Outer class.

    Therefore, we must first create an Outer object.

    Syntax:

        Outer outer = new Outer();

        Outer.Inner inner = outer.new Inner();

    An Inner Class can access:
        - static members of Outer
        - non-static members of Outer
        - private members of Outer
        - public/protected members of Outer

    IMPORTANT:

    An Inner Class has a relationship with the Outer OBJECT.

        Outer Object
             |
             ---> Inner Object

    This is the main difference between:

        Static Nested Class
        vs
        Inner Class
    */

    class Inner {

        void display() {

            System.out.println("This is Inner Class");

        }
    }


    /*
    ============================================================
    3. LOCAL CLASS
    ============================================================

    DECLARATION:

        void method() {

            class Local {
            }

        }

    THEORY:

    A Local Class is a class declared inside a method,
    constructor, loop, if block, or another block.

    Its scope is limited to that block.

    Example:

        void test() {

            class Local {

                void display() {
                    System.out.println("Hello");
                }
            }

            Local obj = new Local();
            obj.display();
        }

    We cannot directly use Local outside the test() method.

    IMPORTANT INTERVIEW TOPIC:

    A Local Class can access local variables of the method
    only when they are:

        1. final
        OR
        2. effectively final

    EFFECTIVELY FINAL:

    A variable is effectively final when it is not declared
    with final, but its value is never changed.

    Example:

        int x = 10;

    If we never change x, it is effectively final.

    But:

        int x = 10;
        x = 20;

    Now x is NOT effectively final.

    Therefore a Local Class cannot capture x after reassignment.

    WHY?

    The local variable belongs to the method's execution
    context, while the Local Class object can continue to exist
    after the method finishes.

    Java therefore captures the VALUE and requires that value
    to remain stable.

    INTERVIEW ANSWER:

    "Local variables captured by a Local Class must be final or
    effectively final because the captured value needs to remain
    stable even if the nested object outlives the method."
    */

    void localClassExample() {

        int number = 100; // effectively final

        class Local {

            void display() {

                System.out.println(
                        "Local Class value: " + number
                );
            }
        }

        Local obj = new Local();

        obj.display();
    }


    /*
    ============================================================
    4. ANONYMOUS INNER CLASS
    ============================================================

    DECLARATION:

        InterfaceName obj = new InterfaceName() {

            @Override
            public void method() {

            }
        };

    THEORY:

    An Anonymous Inner Class is a class without an explicit name.

    It is declared and instantiated at the same time.

    We use it when we need a particular implementation only once.

    Example:

        Person p = new Person() {

            @Override
            public void introduce() {
                System.out.println("Hello");
            }
        };

    There is no class name such as:

        class Student

    That's why it is called ANONYMOUS.

    COMMON USE:

        - One-time implementation
        - Callbacks
        - Event handling
        - Older Java APIs

    MODERN JAVA:

    If the interface is a Functional Interface
    (only one abstract method), a Lambda Expression can often
    be used instead of an Anonymous Class.

    Anonymous Class:

        Person p = new Person() {

            public void introduce() {
                System.out.println("Hello");
            }
        };

    Lambda:

        Person p = () -> {
            System.out.println("Hello");
        };

    But Lambda cannot replace every Anonymous Class.
    Lambda works only with Functional Interfaces.
    */

    interface Person {

        void introduce();
    }


    void anonymousClassExample() {

        Person person = new Person() {

            @Override
            public void introduce() {

                System.out.println(
                        "Hello from Anonymous Class"
                );
            }
        };

        person.introduce();
    }


    /*
    ============================================================
    MAIN METHOD
    ============================================================
    */

    public static void main(String[] args) {

        /*
        --------------------------------------------------------
        1. STATIC NESTED CLASS
        --------------------------------------------------------
        */

        StaticNested staticObj =
                new StaticNested();

        staticObj.display();


        /*
        --------------------------------------------------------
        2. INNER CLASS
        --------------------------------------------------------
        */

        NestedClasses outer =
                new NestedClasses();

        NestedClasses.Inner inner =
                outer.new Inner();

        inner.display();


        /*
        --------------------------------------------------------
        3. LOCAL CLASS
        --------------------------------------------------------
        */

        outer.localClassExample();


        /*
        --------------------------------------------------------
        4. ANONYMOUS INNER CLASS
        --------------------------------------------------------
        */

        outer.anonymousClassExample();
    }
}


/*
============================================================
QUICK REVISION
============================================================

1. STATIC NESTED CLASS

   static class Inner

   -> Does NOT need Outer object
   -> Associated with Outer class
   -> Can directly access static members


2. INNER CLASS

   class Inner

   -> NEEDS Outer object
   -> Associated with Outer object
   -> Can access instance + static members


3. LOCAL CLASS

   class Local

   -> Declared inside method/block
   -> Scope limited to that block
   -> Can capture final/effectively final variables


4. ANONYMOUS CLASS

   new Interface() { ... }

   -> No explicit class name
   -> Used for one-time implementation
   -> Lambda can often replace it for Functional Interfaces


IMPORTANT INTERVIEW POINT:

    Outer.this.x

means:

    "Access x belonging to the enclosing Outer object."


IMPORTANT INTERVIEW POINT:

    effectively final

means:

    "Not declared final, but its value is never reassigned."


MEMORY TRICK:

    STATIC    -> CLASS
    INNER     -> OBJECT
    LOCAL     -> BLOCK
    ANONYMOUS -> NAMELESS


============================================================
*/