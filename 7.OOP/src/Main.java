public class Main {
    public static void main(String[] args) {

//        Class (The Blueprint): A Class is an abstract idea. It defines what an object should look like (its characteristics and behaviors) without actually taking up physical data space.
//                Example: The concept of a "Student" or a "Bank Account."
//        Object (The Instance): An Object is the physical manifestation of a Class. It takes up actual memory and holds real data.
//                Example: "Sahil Deshmukh", the specific student.

//        Characteristics (State): Represented by Variables inside the class (e.g., String name).
//        Behaviors (Actions): Represented by Methods (Functions) inside the class (e.g., void markAttendance()).
//        Because the method lives inside the class, it has direct access to the object's variables without needing them passed as parameters.

//        Student s1 (The Declaration): Creates a Reference Variable named s1 in the Stack Memory. At this point, it points to nothing (null). It is not an object.
//        new Student() (The Instantiation): The new keyword triggers Dynamic Memory Allocation. It goes to the Heap Memory at runtime,
//        reserves a block of space large enough to hold all the variables in the Student class, and creates the actual, unnamed object.
//        (The Assignment): The starting memory address of the new Heap object (e.g., Address 1001) is returned and stored inside the s1 reference variable in the Stack.

//       The Dot Operator (.) acts as the bridge. When you write s1.name, the JVM goes to the Stack, reads the address stored in s1, jumps to that address in the Heap, and accesses the name field.

//        Naming Conventions (Industry Standards)
//        Classes: PascalCase (e.g., Student, BankAccount). Must start with a capital letter.
//                Variables & Methods: camelCase (e.g., firstName, markAttendance()).
//                These are not compiler rules, but ignoring them is an immediate red flag in professional environments.
     }
}