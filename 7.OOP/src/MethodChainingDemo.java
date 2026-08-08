import java.util.Arrays;
import java.util.List;

public class MethodChainingDemo {

    // 1. BASIC METHOD CHAINING
    static class Student {

        private String name;
        private int age;
        private Address address;

        public Student(String name, int age, Address address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        /*
         * Returns Student.
         *
         * Because it returns Student, another Student method
         * can be called immediately.
         */
        public Student setName(String name) {
            this.name = name;
            return this;       // return current Student object
        }

        public Student setAge(int age) {
            this.age = age;
            return this;
        }

        public Student setAddress(Address address) {
            this.address = address;
            return this;
        }

        public void save() {
            System.out.println(
                    "Saving Student: " + name + ", age: " + age
            );
        }

        // ========================================================
        // 2. METHOD CHAINING WITH DIFFERENT RETURN TYPES
        // ========================================================

        /*
         * This method does NOT return Student.
         * It returns Address.
         *
         * Therefore, after getAddress(), we can call
         * methods belonging to Address.
         */
        public Address getAddress() {
            return address;
        }
    }


    // ============================================================
    // ADDRESS CLASS
    // ============================================================

    static class Address {

        private String city;

        public Address(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }


    // ============================================================
    // 3. BUILDER PATTERN
    // ============================================================

    static class User {

        private String name;
        private int age;
        private String email;

        private User() {
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }


        static class Builder {

            private String name;
            private int age;
            private String email;

            /*
             * Each method returns Builder.
             *
             * Therefore another Builder method can be called.
             */

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder age(int age) {
                this.age = age;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            /*
             * build() is normally the final operation.
             *
             * It returns User instead of Builder.
             */
            public User build() {

                User user = new User();

                user.name = this.name;
                user.age = this.age;
                user.email = this.email;

                return user;
            }
        }
    }


    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(String[] args) {


        // ========================================================
        // EXAMPLE 1: BASIC CHAINING
        // ========================================================

        Student student =
                new Student(
                        "Unknown",
                        0,
                        new Address("Pune")
                );

        student
                .setName("Rahul")
                .setAge(25)
                .save();


        /*
         * What Java is conceptually doing:
         *
         * Student temp1 = student.setName("Rahul");
         * Student temp2 = temp1.setAge(25);
         * temp2.save();
         *
         * Because setName() returns Student,
         * setAge() can be called.
         *
         * Because setAge() returns Student,
         * save() can be called.
         */


        // ========================================================
        // EXAMPLE 2: CHAINING WITH DIFFERENT RETURN TYPES
        // ========================================================

        String city =
                student
                        .getAddress()
                        .getCity()
                        .toUpperCase();

        System.out.println("City: " + city);


        /*
         * The chain is:
         *
         * student
         *    ↓
         * getAddress()
         *    ↓
         * Address
         *    ↓
         * getCity()
         *    ↓
         * String
         *    ↓
         * toUpperCase()
         *    ↓
         * String
         *
         *
         * Notice:
         *
         * getAddress() returns Address
         * getCity() returns String
         * toUpperCase() returns String
         *
         * return this is NOT required here.
         */


        // ========================================================
        // EXAMPLE 3: BUILDER PATTERN
        // ========================================================

        User user =
                new User.Builder()
                        .name("Rahul")
                        .age(25)
                        .email("rahul@example.com")
                        .build();

        System.out.println(user);


        /*
         * Chain:
         *
         * new User.Builder()
         *        ↓
         *      Builder
         *        ↓
         * name()
         *        ↓
         *      Builder
         *        ↓
         * age()
         *        ↓
         *      Builder
         *        ↓
         * email()
         *        ↓
         *      Builder
         *        ↓
         * build()
         *        ↓
         *       User
         */


        // ========================================================
        // EXAMPLE 4: STREAM METHOD CHAINING
        // ========================================================

        List<String> names =
                Arrays.asList(
                        "Rahul",
                        "Amit",
                        "Rohit",
                        "Ankit"
                );

        List<String> result =
                names.stream()
                        .filter(name -> name.startsWith("R"))
                        .map(String::toUpperCase)
                        .sorted()
                        .toList();

        System.out.println("Stream result: " + result);


        /*
         * Stream chain:
         *
         * names
         *   ↓
         * stream()
         *   ↓
         * Stream
         *   ↓
         * filter()
         *   ↓
         * Stream
         *   ↓
         * map()
         *   ↓
         * Stream
         *   ↓
         * sorted()
         *   ↓
         * Stream
         *   ↓
         * toList()
         *   ↓
         * List
         *
         * toList() is the final operation here.
         */


        // ========================================================
        // EXAMPLE 5: WHY void BREAKS CHAINING
        // ========================================================

        /*
         * Imagine we had:
         *
         * public void setName(String name) {
         *     this.name = name;
         * }
         *
         * Then this would NOT work:
         *
         * student
         *     .setName("Rahul")
         *     .setAge(25);       // ERROR
         *
         * Why?
         *
         * setName() returns void.
         *
         * There is no object returned on which Java can call
         * setAge().
         *
         */


        // ========================================================
        // FINAL RULE
        // ========================================================

        /*
         * METHOD CHAINING RULE:
         *
         * The result returned by one method must be suitable
         * for the next method call.
         *
         * return this is common:
         *
         * Student setName(...) {
         *     ...
         *     return this;
         * }
         *
         * But return this is NOT mandatory.
         *
         * Example:
         *
         * student.getAddress().getCity().toUpperCase();
         *
         * Here different objects/types are returned at each step.
         */
    }
}

