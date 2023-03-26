package first;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15.11.2022
 */

public class Matrix {
    public static void main(String[] args) {
        Matrix l = new Matrix(2, 2);
        Matrix o = new Matrix(2, 2);
        Matrix m = new Matrix(6, 6);
        m.setValue(0, 1, 1);
        m.setValue(1, 2, 1);
        m.setValue(2, 3, 1);
        m.setValue(3, 4, 1);
        m.setValue(4, 5, 1);
        m.setValue(5, 0, 1);
        m.setValue(5, 2, -1);
        m.setValue(5, 5, 2);
        Matrix s = m;
        //l.setValue(0, 0, 1d);
        //l.setValue(0, 1, 2d);
        //l.setValue(1, 0, 3d);
        //l.setValue(1, 1, 4d);
        //System.out.println(l);
        //o.setValue(0, 0, 6d);
        //o.setValue(0, 1, 8d);
        //o.setValue(1, 0, 7d);
        //o.setValue(1, 1, 9d);
        System.out.println(l.add(o));
        //System.out.println(o);
        System.out.println("M^1: ");
        System.out.println(s);
        for (int i = 1; i < 20; i++) {
            s = s.multiply(m);
            System.out.println("M^" + (i + 1) + ": ");
            System.out.println(s);
        }


    }

    double[][] matrix;
    int m, n;

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        matrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0.0d;
            }
        }
    }

    public String toString() {
        StringBuilder a = new StringBuilder();
        for (double[] doubles : this.matrix) {
            a.append("[");
            for (double aDouble : doubles) {
                a.append(" ").append(aDouble);
            }
            a.append(" ]\n");
        }
        return a.toString();
    }

    public void setValue(int i, int j, double x) {
        this.matrix[i][j] = x;
    }

    public double getValue(int i, int j) {
        return this.matrix[i][j];
    }

    public Matrix add(Matrix other) {
        if (other.matrix.length == this.matrix.length && this.matrix[0].length == other.matrix[0].length) {
            Matrix m = new Matrix(this.m, this.n);
            for (int i = 0; i < this.matrix.length; i++) {
                for (int j = 0; j < this.matrix[i].length; j++) {
                    m.setValue(i, j, (other.getValue(i, j) + this.matrix[i][j]));
                }
            }
            return m;
        } else {
            throw new IndexOutOfBoundsException("Matrizen müssen gleich groß sein!");
        }
    }

    public Matrix multiply(Matrix other) {
        if (this.matrix[0].length == other.matrix.length) {
            double r;
            Matrix m = new Matrix(other.matrix[0].length, this.matrix.length);
            for (int i = 0; i < this.matrix.length; i++) { // Zeile
                for (int j = 0; j < other.matrix[0].length; j++) { // Spalte
                    r = 0d;
                    for (int k = 0; k < this.matrix[0].length; k++) { // Zeile
                        r += (this.getValue(i, k) * other.getValue(k, j));
                    }
                    m.setValue(i, j, r);
                }
            }
            return m;
        } else {
            throw new IndexOutOfBoundsException("Nicht möglich!");
        }
    }
}
