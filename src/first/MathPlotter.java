package first;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06.12.2022
 */

interface D2Method {
    public double compute(double value);
}

public class MathPlotter {
    public static void main(String[] args) {
        String s;
        do {
            s = MyIO.promptAndRead("Bitte Mathematische Funktion angeben: ");
            switch (s) {
                case "stop":
                    break;
                case "sin":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.sin(value);
                        }
                    });
                    break;
                case "cos":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.cos(value);
                        }
                    });
                    break;
                case "exp":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.exp(value);
                        }
                    });
                    break;
                case "log":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.log(value);
                        }
                    });
                    break;
                case "sqrt":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.sqrt(value);
                        }
                    });
                    break;
                case "tan":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.tan(value);
                        }
                    });
                    break;
                case "square":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return value * value;
                        }
                    });
                    break;
                case "cube":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return value * value * value;
                        }
                    });
                    break;
                case "quad":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return value * value * value * value;
                        }
                    });
                    break;
                case "tower":
                    plot(new D2Method() {
                        @Override
                        public double compute(double value) {
                            return Math.pow(value, Math.pow(value, value));
                        }
                    });
                    break;
                default:
                    System.out.println("Falsche Eingabe");
            }
        } while (!s.equals("stop"));
        System.out.println("Programm beendet");
    }

    public static void plot(D2Method meth) {
        double start = MyIO.readDouble("Startewert eingeben: ");
        double ende = MyIO.readDouble("Endwert eingeben: ");
        double schritte = MyIO.readDouble("Schrittweite eingeben: ");
        System.out.println("x:                   | f(x):");
        for (int i = 0; start + i * schritte <= ende + 0.1; i++) {
            double x = start + i * schritte;
            String xs = "" + x;
            for (int j = xs.length(); j < 20; j++) {
                xs += " ";
            }
            System.out.println(xs + " | " + meth.compute(x));
        }

    }
}

