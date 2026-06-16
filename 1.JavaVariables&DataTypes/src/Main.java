import java.util.ArrayList;
import java.util.List;

// we write classname in capital letter it's not compulsory but its good practice that we should write classnames first character in capital letter
// if the file name is Main.java then that class should be public, and it should be outside as well I mean the outer class
// public means this class can be accessible from anywhere in same package, class is name group of properties and functions
public class Main{

    //inside the class we have functions
    // function is block of code
    // from where you are program starts that function called main function
    // the main is the first function which executes firstly
    // we have to give the function names as main its by convention
    public static void main(String[] args){
        //static means here without creating object of Main class or whatever class name we have fven above  we need to run main method
        // if we did not write static then we need to create object of that class to run but the main methods itself is the first thing
        // that run after running application how can we write soemthiog before that, how can we create object that's why we make it static
        System.out.println("hello world!");

        System.out.println(calculator(1.0,4.0,"+"));


        List<String> names = new ArrayList<>();
        names.add("sahil");
        names.add("asdf");
        names.add("fsdf");names.add("sdfafde");
        names.add("dasfsdf");
        names.add("  ");
        names.add("");


        List<String> filterNames = names.stream()
                .filter(s-> !s.isBlank())
                .toList();


        System.out.println(filterNames);
    }

    public static Double calculator(Double a,Double b,String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> 0.0;
        };
    }
}

