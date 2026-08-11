// rules of immutable object

// 1> Mark class as a final so it cannot be overridden
// 2> Mark instance variables as private and final as well
// 3> No setters
// 4> if we have any  mutable class or object make it immutable as well by making there instance variable private and final if we did not want to do that
// 5> we can make defensive copy in constructor and getters

import java.util.*;

final class ImmutableClass {

    private final int age;
    private final String name;
    private final College college;


    ImmutableClass(int age, String name,College college) {
        this.age = age;
        this.name = name;
        this.college =new College(college.name,college.address);
    }


    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public College getCollege() {
        return new College(this.college.name, this.college.address);
    }

}


class College{
    String name;
    String address;

    College(String name, String address){
        this.name = name;
        this.address = address;
    }


}
