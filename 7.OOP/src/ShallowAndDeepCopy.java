public class ShallowAndDeepCopy {
    // Shallow copy
//    primitive → copy value
//    reference → copy reference

    // deep copy
//    primitive → copy value
//    reference → create NEW referenced object
    // Reference class
    static class Data {
        int value;

        Data(int value) {
            this.value = value;
        }
    }

    static class Random {
        int x;
        int y;
        Data d;   // Reference variable

        // Normal Constructor
        Random(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.d = new Data(value);
        }

        // Shallow Copy Constructor
        Random(Random r) {
            this.x = r.x;
            this.y = r.y;
            this.d = r.d;          // Same Data object is shared
        }

        // Deep Copy Method
        Random deepCopy() {
            Random copy = new Random(this.x, this.y, this.d.value);
            return copy;
        }
    }

    public static void main(String[] args) {

        Random r1 = new Random(4, 5, 100);

        // Shallow Copy
        Random shallow = new Random(r1);

        // Deep Copy
        Random deep = r1.deepCopy();

        System.out.println("Before Modification");
        System.out.println("r1       : " + r1.x + ", " + r1.y + ", " + r1.d.value);
        System.out.println("shallow  : " + shallow.x + ", " + shallow.y + ", " + shallow.d.value);
        System.out.println("deep     : " + deep.x + ", " + deep.y + ", " + deep.d.value);

        // Modify copied objects
        shallow.d.value = 500;   // Shared object
        deep.d.value = 900;      // Separate object

        System.out.println("\nAfter Modification");
        System.out.println("r1       : " + r1.x + ", " + r1.y + ", " + r1.d.value);
        System.out.println("shallow  : " + shallow.x + ", " + shallow.y + ", " + shallow.d.value);
        System.out.println("deep     : " + deep.x + ", " + deep.y + ", " + deep.d.value);
    }
}