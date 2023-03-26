package first;

/**
 * @version 1.1, 31.10.2022
 * @author Denis Schaffer
 */

import java.util.Scanner;
import java.math.BigInteger;
import java.math.BigDecimal;

public final class MyIO {
    static Scanner sc = new Scanner(System.in);

    public static String promptAndRead(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static Byte readByte(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Byte.parseByte(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static short readShort(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Short.parseShort(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static long readLong(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Long.parseLong(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static BigInteger readBigInteger(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return new BigInteger(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static float readFloat(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Float.parseFloat(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return new BigDecimal(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static Fraction readFraction(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                Fraction f = null;
                String s = sc.nextLine().trim();
                for (int i = 0; i < s.length(); i++) {
                    if (s.substring(i, i + 1).contains("/")) {
                        f = new Fraction((s.substring(0, i)), (s.substring(i + 1)));
                    }
                }
                return f;
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }

    public static boolean promptAndReadBoolean(String prompt) {
        System.out.println(prompt);
        String s;
        while (true) {
            s = sc.nextLine().toLowerCase();
            try {
                switch (s) {
                    case "f", "false", "n", "no", "0" -> {
                        return false;
                    }
                    case "t", "true", "y", "yes", "1" -> {
                        return true;
                    }
                    default -> throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Error! Try again.");
            }
        }
    }
}
