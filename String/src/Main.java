public class Main {
    public static void main(String[] args){
//        String Methods
        // empty string
        String s1 = new String();

        // this is creating hello in string pool as well as in heap
        String s2 = new String("Hello");

        // string literal
        String s3 = "Aditya";
        // passing literal in s4
        String s4= new String(s3);
        System.out.println(s1);

//        char array
        char[] arr = {'s','a','h','i','l'};
        String str = new String(arr);
//        arr[0] = 'a';
//        System.out.println(str); //str will not change because String is immutable

        String s6 = new String(arr,0,3);

        byte[] arr2 = {97,98,99};
        String s7 = new String(arr2);
        System.out.println(s7);


        //StringBuilder and StringBuffer

    }
}
