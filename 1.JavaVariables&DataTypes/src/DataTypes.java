public class DataTypes {

    public static void main(String[] args){

        //In Java, all numeric integer types (byte, short, int, long) are signed, while the char type is entirely unsigned
        //signed means number can be positive or negative , char can only be postive we represen in ascaii java stores the cahr in unicode 

        //datatypes is of mainly two types primitive and non-primitive
        // primitive mainly four types
        // Integer -> byte, short , int,long
        // real no -> float, double
        // character -> char
        // boolean -> boolean

        // in byte we can store till 255 the formula  is (2 raise to n)-1 n is no of bits
        // remember we can store 255 but it also includes the negative no taht is why the range is -128 to 127

//| Data Type |                  Size |                Minimum Value |                            Maximum Value | Example                |
//| --------- | --------------------: | ---------------------------: | ---------------------------------------: | ---------------------- |
//| `byte`    |     1 byte / 8 bits   | `-128`                       |  `127`                                   | `byte b = 10;`         |
//| `short`   |     2 bytes / 16 bits |                    `-32,768` |                                 `32,767` | `short s = 1000;`      |
//| `int`     |     4 bytes / 32 bits |             `-2,147,483,648` |                          `2,147,483,647` | `int i = 100000;`      |
//| `long`    |     8 bytes / 64 bits | `-9,223,372,036,854,775,808` |              `9,223,372,036,854,775,807` | `long l = 100000L;`    |
//| `float`   |     4 bytes / 32 bits |     Approximately `±1.4E-45` |           Approximately `±3.4028235E+38` | `float f = 10.5f;`     |
//| `double`  |     8 bytes / 64 bits |    Approximately `±4.9E-324` | Approximately `±1.7976931348623157E+308` | `double d = 10.5;`     |
//| `char`    |     2 bytes / 16 bits |             `0`              |                    `65,535`              | `char c = 'A';`        |
//| `boolean` | Not precisely defined |                            — |                   `true` or `false` only | `boolean flag = true;` |

    }
}
