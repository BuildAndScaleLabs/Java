/*
 * ============================================================================
 * Direction.java
 * ============================================================================
 *
 * COMPLETE ENUM PRACTICAL NOTES
 *
 * We are going to understand:
 *
 * 1. Basic enum
 * 2. Enum constants
 * 3. Enum objects
 * 4. Reference variables
 * 5. Multiple references to same enum object
 * 6. Enum constructor
 * 7. Enum instance fields
 * 8. Enum methods
 * 9. values()
 * 10. valueOf()
 * 11. name()
 * 12. == comparison
 * 13. switch
 *
 *
 * ============================================================================
 * 1. BASIC ENUM
 * ============================================================================
 *
 * An enum represents a FIXED SET of values.
 *
 * Here we have only four possible directions:
 *
 *     NORTH
 *     SOUTH
 *     EAST
 *     WEST
 *
 * Each enum constant represents one fixed enum object.
 *
 *
 * Conceptually:
 *
 *     Direction.NORTH  -> Direction object
 *     Direction.SOUTH  -> Direction object
 *     Direction.EAST   -> Direction object
 *     Direction.WEST   -> Direction object
 *
 *
 * We cannot create another Direction object using new.
 *
 */


public enum Direction {

    NORTH,
    SOUTH,
    EAST,
    WEST
}


/*
 * ============================================================================
 * 2. REFERENCE VARIABLE + ENUM OBJECT
 * ============================================================================
 *
 * Example:
 *
 *     Direction direction = Direction.NORTH;
 *
 *
 * Direction
 *     ↓
 *     enum type
 *
 *
 * direction
 *     ↓
 *     reference variable
 *
 *
 * Direction.NORTH
 *     ↓
 *     predefined NORTH enum object
 *
 *
 * Diagram:
 *
 *     direction
 *         |
 *         v
 *     Direction.NORTH
 *
 *
 * IMPORTANT:
 *
 * We are NOT doing:
 *
 *     new Direction()
 *
 *
 * We are getting the already-created enum object:
 *
 *     Direction.NORTH
 *
 *
 * Example:
 *
 *     Direction direction = Direction.NORTH;
 *
 *     System.out.println(direction);
 *
 *
 * Output:
 *
 *     NORTH
 *
 *
 *
 * ============================================================================
 * 3. MULTIPLE REFERENCE VARIABLES CAN POINT TO SAME ENUM OBJECT
 * ============================================================================
 *
 * We can create multiple reference variables:
 *
 *     Direction d1 = Direction.NORTH;
 *     Direction d2 = Direction.NORTH;
 *     Direction d3 = Direction.NORTH;
 *
 *
 * But we are NOT creating three NORTH objects.
 *
 * All three references point to the SAME NORTH enum object.
 *
 *
 * Diagram:
 *
 *
 *     d1 ─────────┐
 *                 |
 *     d2 ─────────┼──────> Direction.NORTH
 *                 |
 *     d3 ─────────┘
 *
 *
 * Therefore:
 *
 *     d1 == d2
 *     d2 == d3
 *     d1 == d3
 *
 * are all true.
 *
 */


class EnumReferenceExample {

    public static void main(String[] args) {

        Direction d1 = Direction.NORTH;
        Direction d2 = Direction.NORTH;
        Direction d3 = Direction.NORTH;

        System.out.println(d1 == d2);
        System.out.println(d2 == d3);
        System.out.println(d1 == d3);
    }
}


/*
 * Output:
 *
 *     true
 *     true
 *     true
 *
 *
 * ============================================================================
 * 4. DIFFERENT ENUM CONSTANTS ARE DIFFERENT ENUM OBJECTS
 * ============================================================================
 *
 * NORTH and SOUTH are different enum constants.
 *
 * Therefore they represent different enum objects.
 *
 *
 *     Direction north = Direction.NORTH;
 *     Direction south = Direction.SOUTH;
 *
 *
 * Diagram:
 *
 *     north ─────> NORTH object
 *
 *     south ─────> SOUTH object
 *
 *
 * Therefore:
 *
 *     north == south
 *
 * is false.
 *
 */


class DifferentEnumObjectsExample {

    public static void main(String[] args) {

        Direction north = Direction.NORTH;
        Direction south = Direction.SOUTH;

        System.out.println(north == south);
    }
}


/*
 * Output:
 *
 *     false
 *
 *
 * ============================================================================
 * 5. ENUM CANNOT BE CREATED USING new
 * ============================================================================
 *
 * With a normal class we can do:
 *
 *     Direction direction = new Direction();
 *
 *
 * But enum does NOT allow this.
 *
 *
 *     Direction direction = new Direction(); // ERROR
 *
 *
 * Why?
 *
 * Because enum controls its own fixed set of objects.
 *
 *
 * We can only use:
 *
 *     Direction.NORTH
 *     Direction.SOUTH
 *     Direction.EAST
 *     Direction.WEST
 *
 *
 * We cannot create:
 *
 *     Direction.NORTH2
 *     Direction.SOMETHING
 *     Direction.TEST
 *
 * dynamically using new.
 *
 *
 * ============================================================================
 * 6. ENUM WITH CONSTRUCTOR
 * ============================================================================
 *
 * An enum can have a constructor.
 *
 * Each enum constant can pass a value to the constructor.
 *
 *
 * Example:
 *
 *     NORTH("North direction")
 *
 *
 * means the NORTH enum object gets:
 *
 *     "North direction"
 *
 *
 * Similarly:
 *
 *     SOUTH("South direction")
 *     EAST("East direction")
 *     WEST("West direction")
 *
 *
 * each passes its own value to the constructor.
 *
 *
 * ============================================================================
 */


enum DirectionWithDescription {

    NORTH("North direction"),
    SOUTH("South direction"),
    EAST("East direction"),
    WEST("West direction");


    /*
     * This is an INSTANCE field.
     *
     * Every enum object gets its OWN description.
     *
     *
     * NORTH object:
     *
     *     description = "North direction"
     *
     *
     * SOUTH object:
     *
     *     description = "South direction"
     *
     *
     * EAST object:
     *
     *     description = "East direction"
     *
     *
     * WEST object:
     *
     *     description = "West direction"
     *
     *
     * Therefore this field is NON-STATIC.
     *
     */

    private final String description;


    /*
     * Enum constructor.
     *
     * When Java initializes:
     *
     *     NORTH("North direction")
     *
     * it passes:
     *
     *     "North direction"
     *
     * to this constructor.
     *
     *
     * When Java initializes:
     *
     *     SOUTH("South direction")
     *
     * it passes:
     *
     *     "South direction"
     *
     * to this constructor.
     *
     */

    private DirectionWithDescription(String description) {

        this.description = description;
    }


    /*
     * Getter method.
     *
     * It returns the description belonging to
     * the particular enum object.
     *
     */

    public String getDescription() {

        return description;
    }
}


/*
 * ============================================================================
 * 7. USING ENUM OBJECT'S INSTANCE FIELD
 * ============================================================================
 *
 * Each enum object has its own description.
 *
 */


class EnumFieldExample {

    public static void main(String[] args) {

        DirectionWithDescription north =
                DirectionWithDescription.NORTH;

        DirectionWithDescription south =
                DirectionWithDescription.SOUTH;


        System.out.println(north.getDescription());

        System.out.println(south.getDescription());
    }
}


/*
 * Output:
 *
 *     North direction
 *     South direction
 *
 *
 * ============================================================================
 * 8. WHY description IS NOT STATIC
 * ============================================================================
 *
 * We have:
 *
 *     NORTH -> "North direction"
 *     SOUTH -> "South direction"
 *     EAST  -> "East direction"
 *     WEST  -> "West direction"
 *
 *
 * Therefore each enum object needs its own description.
 *
 *
 * So:
 *
 *     private final String description;
 *
 * is correct.
 *
 *
 * If we wrote:
 *
 *     private static final String description;
 *
 *
 * then there would be only ONE shared description
 * for the entire DirectionWithDescription enum.
 *
 *
 * That would be wrong because:
 *
 *     NORTH needs one value
 *     SOUTH needs another value
 *     EAST needs another value
 *     WEST needs another value
 *
 *
 * ============================================================================
 * 9. values()
 * ============================================================================
 *
 * Every enum automatically provides values().
 *
 *
 * values() returns all enum constants.
 *
 *
 * For Direction:
 *
 *     Direction.values()
 *
 * gives:
 *
 *     NORTH
 *     SOUTH
 *     EAST
 *     WEST
 *
 */


class ValuesExample {

    public static void main(String[] args) {

        for (Direction direction : Direction.values()) {

            System.out.println(direction);
        }
    }
}


/*
 * Output:
 *
 *     NORTH
 *     SOUTH
 *     EAST
 *     WEST
 *
 *
 * ============================================================================
 * 10. valueOf()
 * ============================================================================
 *
 * Every enum automatically provides valueOf().
 *
 *
 * valueOf() takes the exact enum constant name
 * and returns that enum object.
 *
 *
 *     Direction.valueOf("NORTH")
 *
 * gives:
 *
 *     Direction.NORTH
 *
 */


class ValueOfExample {

    public static void main(String[] args) {

        Direction direction =
                Direction.valueOf("NORTH");

        System.out.println(direction);
    }
}


/*
 * Output:
 *
 *     NORTH
 *
 *
 * IMPORTANT:
 *
 * valueOf() is case-sensitive.
 *
 *
 * This works:
 *
 *     Direction.valueOf("NORTH");
 *
 *
 * This does NOT work:
 *
 *     Direction.valueOf("north");
 *
 *
 * Because the enum constant is exactly:
 *
 *     NORTH
 *
 *
 * ============================================================================
 * 11. name()
 * ============================================================================
 *
 * name() returns the exact name of the enum constant.
 *
 */


class NameExample {

    public static void main(String[] args) {

        System.out.println(Direction.NORTH.name());

        System.out.println(Direction.SOUTH.name());

        System.out.println(Direction.EAST.name());

        System.out.println(Direction.WEST.name());
    }
}


/*
 * Output:
 *
 *     NORTH
 *     SOUTH
 *     EAST
 *     WEST
 *
 *
 * ============================================================================
 * 12. COMPARING ENUMS USING ==
 * ============================================================================
 *
 * Enum constants are fixed objects.
 *
 * Therefore we normally use == to compare enums.
 *
 */


class EnumComparisonExample {

    public static void main(String[] args) {

        Direction direction = Direction.NORTH;


        if (direction == Direction.NORTH) {

            System.out.println("Direction is NORTH");
        }


        if (direction != Direction.SOUTH) {

            System.out.println("Direction is not SOUTH");
        }
    }
}


/*
 * Output:
 *
 *     Direction is NORTH
 *     Direction is not SOUTH
 *
 *
 * ============================================================================
 * 13. ENUM WITH switch
 * ============================================================================
 *
 * Enums work very well with switch.
 *
 */


class EnumSwitchExample {

    public static void main(String[] args) {

        Direction direction = Direction.EAST;


        switch (direction) {

            case NORTH:

                System.out.println("Moving North");

                break;


            case SOUTH:

                System.out.println("Moving South");

                break;


            case EAST:

                System.out.println("Moving East");

                break;


            case WEST:

                System.out.println("Moving West");

                break;
        }
    }
}


/*
 * Output:
 *
 *     Moving East
 *
 *
 * ============================================================================
 * 14. COMPLETE PRACTICAL ENUM
 * ============================================================================
 *
 * Now combine everything into one proper enum.
 *
 *
 * We have:
 *
 *     4 fixed enum objects
 *
 *     NORTH
 *         └── description = "North direction"
 *
 *     SOUTH
 *         └── description = "South direction"
 *
 *     EAST
 *         └── description = "East direction"
 *
 *     WEST
 *         └── description = "West direction"
 *
 *
 * `description` is NON-STATIC because every enum object
 * has its own description.
 *
 */


enum DirectionComplete {

    NORTH("North direction"),
    SOUTH("South direction"),
    EAST("East direction"),
    WEST("West direction");


    private final String description;


    private DirectionComplete(String description) {

        this.description = description;
    }


    public String getDescription() {

        return description;
    }
}


/*
 * ============================================================================
 * 15. USING THE COMPLETE ENUM
 * ============================================================================
 */

class CompleteEnumExample {

    public static void main(String[] args) {

        DirectionComplete d1 =
                DirectionComplete.NORTH;


        DirectionComplete d2 =
                DirectionComplete.NORTH;


        DirectionComplete d3 =
                DirectionComplete.SOUTH;


        /*
         * d1 and d2 point to the SAME NORTH enum object.
         */

        System.out.println(d1 == d2);


        /*
         * d1 points to NORTH.
         *
         * d3 points to SOUTH.
         *
         * Therefore they are different objects.
         */

        System.out.println(d1 == d3);


        /*
         * Access the field belonging to the NORTH object.
         */

        System.out.println(d1.getDescription());


        /*
         * Access the field belonging to the SOUTH object.
         */

        System.out.println(d3.getDescription());


        /*
         * Get every enum constant.
         */

        for (DirectionComplete direction
                : DirectionComplete.values()) {

            System.out.println(
                    direction +
                            " -> " +
                            direction.getDescription()
            );
        }
    }
}


/*
 * Output:
 *
 *     true
 *     false
 *     North direction
 *     South direction
 *
 *     NORTH -> North direction
 *     SOUTH -> South direction
 *     EAST -> East direction
 *     WEST -> West direction
 *
 *
 * ============================================================================
 * FINAL ENUM MENTAL MODEL
 * ============================================================================
 *
 *
 *     enum Direction {
 *
 *         NORTH,
 *         SOUTH,
 *         EAST,
 *         WEST
 *     }
 *
 *
 * Think of it as:
 *
 *
 *                    Direction
 *                    ENUM CLASS
 *                        |
 *          ┌─────────────┼─────────────┐
 *          |             |             |
 *          ↓             ↓             ↓
 *       NORTH          SOUTH          EAST       WEST
 *          |             |             |          |
 *          ↓             ↓             ↓          ↓
 *       Object #1      Object #2     Object #3  Object #4
 *
 *
 * Each constant represents one fixed enum object.
 *
 *
 * ============================================================================
 * REFERENCE VARIABLES
 * ============================================================================
 *
 *
 *     Direction d1 = Direction.NORTH;
 *     Direction d2 = Direction.NORTH;
 *
 *
 *
 *     d1 ─────────┐
 *                 |
 *                 ├────> SAME NORTH enum object
 *                 |
 *     d2 ─────────┘
 *
 *
 * Therefore:
 *
 *     d1 == d2
 *
 * is true.
 *
 *
 * ============================================================================
 * ENUM WITH INSTANCE FIELD
 * ============================================================================
 *
 *
 *     NORTH("North direction")
 *     SOUTH("South direction")
 *
 *
 * means:
 *
 *
 *     NORTH object
 *         |
 *         └── description = "North direction"
 *
 *
 *     SOUTH object
 *         |
 *         └── description = "South direction"
 *
 *
 * Therefore:
 *
 *     private final String description;
 *
 *
 * is NON-STATIC.
 *
 *
 * ============================================================================
 * MOST IMPORTANT RULE
 * ============================================================================
 *
 *
 * ENUM
 *     =
 * fixed set of predefined objects
 *
 *
 * NORTH
 *     =
 * one predefined enum object
 *
 *
 * SOUTH
 *     =
 * another predefined enum object
 *
 *
 * Direction d1 = Direction.NORTH;
 *
 *     =
 * d1 is a reference pointing to the predefined NORTH object.
 *
 *
 * Direction d2 = Direction.NORTH;
 *
 *     =
 * d2 is another reference pointing to the SAME NORTH object.
 *
 *
 * We can create many references:
 *
 *     d1
 *     d2
 *     d3
 *     d4
 *
 * pointing to NORTH.
 *
 * But there is still only the predefined NORTH enum object.
 *
 *
 * ============================================================================
 */