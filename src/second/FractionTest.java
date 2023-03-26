package second;

/**
 * @version 1.1, 23.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.math.BigInteger;
import first.Fraction;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

class FractionTest {
    Fraction testA = new Fraction(Long.MIN_VALUE, Long.MAX_VALUE);
    Fraction testB = new Fraction(Long.MAX_VALUE, Long.MIN_VALUE);
    Fraction testC = new Fraction("4", "2");
    Fraction testD = new Fraction("2", "4");
    Fraction testE = new Fraction(4, 0);
    Fraction testF = new Fraction(0, 4);
    Fraction testG = new Fraction(8734, 872364);
    Fraction testH = new Fraction(872364, 8734);

    //Alle Tests für die getNum/getDen-Methode

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestA() {
        BigInteger expected = new BigInteger(String.valueOf(Long.MIN_VALUE));
        BigInteger actual = testA.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestB() {
        BigInteger expected = new BigInteger(String.valueOf(Long.MIN_VALUE + 1));
        BigInteger actual = testB.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestC() {
        BigInteger expected = new BigInteger("2");
        BigInteger actual = testC.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestD() {
        BigInteger expected = new BigInteger("1");
        BigInteger actual = testD.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestE() {
        BigInteger expected = new BigInteger("0");
        BigInteger actual = testE.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestF() {
        BigInteger expected = new BigInteger("0");
        BigInteger actual = testF.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestG() {
        BigInteger expected = new BigInteger("4367");
        BigInteger actual = testG.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetNumerator_TestH() {
        BigInteger expected = new BigInteger("436182");
        BigInteger actual = testH.getNumerator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestA() {
        BigInteger expected = new BigInteger(String.valueOf(Long.MAX_VALUE));
        BigInteger actual = testA.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestB() {
        BigInteger expected = new BigInteger("9223372036854775808");
        BigInteger actual = testB.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestC() {
        BigInteger expected = new BigInteger("1");
        BigInteger actual = testC.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestD() {
        BigInteger expected = new BigInteger("2");
        BigInteger actual = testD.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestE() {
        BigInteger expected = new BigInteger("0");
        BigInteger actual = testE.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestF() {
        BigInteger expected = new BigInteger("1");
        BigInteger actual = testF.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestG() {
        BigInteger expected = new BigInteger("436182");
        BigInteger actual = testG.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testgetDenominator_TestH() {
        BigInteger expected = new BigInteger("4367");
        BigInteger actual = testH.getDenominator();
        assertEquals(expected.toString(), actual.toString());
    }

    //Alle Tests für die Add-Methode

    @org.junit.jupiter.api.Test
    void testadd_TestA() {
        Fraction expected = new Fraction(new BigInteger("-18446744073709551616"), new BigInteger("9223372036854775807"));
        Fraction actual = testA.add(testA);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestB() {
        Fraction expected = new Fraction(new BigInteger("-9223372036854775807"), new BigInteger("4611686018427387904"));
        Fraction actual = testB.add(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestC() {
        Fraction expected = new Fraction(4, 1);
        Fraction actual = testC.add(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestD() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testD.add(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestE() {
        Fraction expected = new Fraction(1, 0);
        Fraction actual = testE.add(testE);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestF() {
        Fraction expected = new Fraction(0, 4);
        Fraction actual = testF.add(testF);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestG() {
        Fraction expected = new Fraction(17468, 872364);
        Fraction actual = testG.add(testG);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestH() {
        Fraction expected = new Fraction(1744728, 8734);
        Fraction actual = testH.add(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestI() {
        Fraction expected = new Fraction(new BigInteger("-170141183460469231713240559642174554113"), new BigInteger("85070591730234615856620279821087277056"));
        Fraction actual = testA.add(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestJ() {
        Fraction expected = new Fraction(new BigInteger("9223372036854775809"), new BigInteger("9223372036854775808"));
        Fraction actual = testB.add(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestK() {
        Fraction expected = new Fraction(5, 2);
        Fraction actual = testC.add(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testadd_TestL() {
        Fraction expected = new Fraction(3506924, 34936);
        Fraction actual = testD.add(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    //Alle Substract-Methoden Tests

    @org.junit.jupiter.api.Test
    void testsubtract_TestA() {
        Fraction expected = new Fraction(new BigInteger("0"), new BigInteger("1"));
        Fraction actual = testA.subtract(testA);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestB() {
        Fraction expected = new Fraction(new BigInteger("0"), new BigInteger("1"));
        Fraction actual = testB.subtract(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestC() {
        Fraction expected = new Fraction(0, 2);
        Fraction actual = testC.subtract(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestD() {
        Fraction expected = new Fraction(0, 4);
        Fraction actual = testD.subtract(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestE() {
        Fraction expected = new Fraction(0, 0);
        Fraction actual = testE.subtract(testE);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestF() {
        Fraction expected = new Fraction(0, 4);
        Fraction actual = testF.subtract(testF);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestG() {
        Fraction expected = new Fraction(0, 872364);
        Fraction actual = testG.subtract(testG);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestH() {
        Fraction expected = new Fraction(0, 8734);
        Fraction actual = testH.subtract(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestI() {
        Fraction expected = new Fraction(new BigInteger("-18446744073709551615"), new BigInteger("85070591730234615856620279821087277056"));
        Fraction actual = testA.subtract(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestJ() {
        Fraction expected = new Fraction(new BigInteger("-27670116110564327423"), new BigInteger("9223372036854775808"));
        Fraction actual = testB.subtract(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestK() {
        Fraction expected = new Fraction(3, 2);
        Fraction actual = testC.subtract(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testsubtract_TestL() {
        Fraction expected = new Fraction(-3471988, 34936);
        Fraction actual = testD.subtract(testH);
        assertEquals(expected.toString(), actual.toString());
    }


    //Alle Multiply-Methoden Tests

    @org.junit.jupiter.api.Test
    void testmultiply_TestA() {
        Fraction expected = new Fraction(new BigInteger("85070591730234615865843651857942052864"), new BigInteger("85070591730234615847396907784232501249"));
        Fraction actual = testA.multiply(testA);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestB() {
        Fraction expected = new Fraction(new BigInteger("85070591730234615847396907784232501249"), new BigInteger("85070591730234615865843651857942052864"));
        Fraction actual = testB.multiply(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestC() {
        Fraction expected = new Fraction(16, 4);
        Fraction actual = testC.multiply(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestD() {
        Fraction expected = new Fraction(4, 16);
        Fraction actual = testD.multiply(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestE() {
        Fraction expected = new Fraction(16, 0);
        Fraction actual = testE.multiply(testE);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestF() {
        Fraction expected = new Fraction(0, 16);
        Fraction actual = testF.multiply(testF);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestG() {
        Fraction expected = new Fraction(new BigInteger("76282756"), new BigInteger("761018948496"));
        Fraction actual = testG.multiply(testG);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestH() {
        Fraction expected = new Fraction(new BigInteger("761018948496"), new BigInteger("76282756"));
        Fraction actual = testH.multiply(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestI() {
        Fraction expected = new Fraction(new BigInteger("1"), new BigInteger("1"));
        Fraction actual = testA.multiply(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestJ() {
        Fraction expected = new Fraction(new BigInteger("-9223372036854775807"), new BigInteger("4611686018427387904"));
        Fraction actual = testB.multiply(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestK() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testC.multiply(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testmultiply_TestL() {
        Fraction expected = new Fraction(1744728, 34936);
        Fraction actual = testD.multiply(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    //Alle Divide-Methoden Tests

    @org.junit.jupiter.api.Test
    void testdivide_TestA() {
        Fraction expected = new Fraction(new BigInteger("1"), new BigInteger("1"));
        Fraction actual = testA.divide(testA);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestB() {
        Fraction expected = new Fraction(new BigInteger("1"), new BigInteger("1"));
        Fraction actual = testB.divide(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestC() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testC.divide(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestD() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testD.divide(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestE() {
        Fraction expected = new Fraction(0, 0);
        Fraction actual = testE.divide(testE);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestF() {
        Fraction expected = new Fraction(0, 0);
        Fraction actual = testF.divide(testF);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestG() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testG.divide(testG);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestH() {
        Fraction expected = new Fraction(1, 1);
        Fraction actual = testH.divide(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestI() {
        Fraction expected = new Fraction(new BigInteger("85070591730234615865843651857942052864"), new BigInteger("85070591730234615847396907784232501249"));
        Fraction actual = testA.divide(testB);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestJ() {
        Fraction expected = new Fraction(new BigInteger("-9223372036854775807"), new BigInteger("18446744073709551616"));
        Fraction actual = testB.divide(testC);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestK() {
        Fraction expected = new Fraction(4, 1);
        Fraction actual = testC.divide(testD);
        assertEquals(expected.toString(), actual.toString());
    }

    @org.junit.jupiter.api.Test
    void testdivide_TestL() {
        Fraction expected = new Fraction(17468, 3489456);
        Fraction actual = testD.divide(testH);
        assertEquals(expected.toString(), actual.toString());
    }

    //Alle toString-Methoden

    @org.junit.jupiter.api.Test
    void testToString_TestA() {
        String expected = "-9223372036854775808/9223372036854775807";
        String actual = testA.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestB() {
        String expected = "-9223372036854775807/9223372036854775808";
        String actual = testB.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestC() {
        String expected = "2/1";
        String actual = testC.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestD() {
        String expected = "1/2";
        String actual = testD.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestE() {
        String expected = "0/0";
        String actual = testE.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestF() {
        String expected = "0/1";
        String actual = testF.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestG() {
        String expected = "4367/436182";
        String actual = testG.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testToString_TestH() {
        String expected = "436182/4367";
        String actual = testH.toString();
        assertEquals(expected, actual);
    }

    // Alle isInteger-Methoden

    @org.junit.jupiter.api.Test
    void testisInteger_TestA() {
        boolean actual = testA.isInteger();
        assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestB() {
        boolean actual = testB.isInteger();
        assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestC() {
        boolean actual = testC.isInteger();
        assertTrue(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestD() {
        boolean actual = testD.isInteger();
        assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestE() {
        assertThrows(ArithmeticException.class, () -> testE.isInteger());
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestF() {
        boolean actual = testF.isInteger();
        assertTrue(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestG() {
        boolean actual = testG.isInteger();
        assertFalse(actual);
    }

    @org.junit.jupiter.api.Test
    void testisInteger_TestH() {
        boolean actual = testH.isInteger();
        assertFalse(actual);
    }

    // Alle compareTo-Methoden

    @org.junit.jupiter.api.Test
    void testcompareTo_TestA() {
        int expected = 0;
        int actual = testA.compareTo(testA);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestB() {
        int expected = 0;
        int actual = testA.compareTo(testB);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestC() {
        int expected = 1;
        int actual = testC.compareTo(testD);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestD() {
        int expected = 1;
        int actual = testD.compareTo(testE);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestE() {
        int expected = 1;
        int actual = testE.compareTo(testF);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestF() {
        int expected = -1;
        int actual = testF.compareTo(testG);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestG() {
        int expected = 0;
        int actual = testG.compareTo(testG);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testcompareTo_TestH() {
        int expected = 0;
        int actual = testH.compareTo(testH);
        assertEquals(expected, actual);
    }

    // Alle Int-Value tests

    @org.junit.jupiter.api.Test
    void testintValue_TestA() {
        int expected = -1;
        int actual = testA.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestB() {
        int expected = 0;
        int actual = testB.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestC() {
        int expected = 2;
        int actual = testC.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestD() {
        int expected = 0;
        int actual = testD.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestE() {
        assertThrows(ArithmeticException.class, () -> testE.intValue());
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestF() {
        int expected = 0;
        int actual = testF.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestG() {
        int expected = 0;
        int actual = testG.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testintValue_TestH() {
        int expected = 99;
        int actual = testH.intValue();
        assertEquals(expected, actual);
    }

    // Alle Long-Value tests

    @org.junit.jupiter.api.Test
    void testlongValue_TestA() {
        long expected = -1L;
        long actual = testA.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestB() {
        long expected = 0L;
        long actual = testB.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestC() {
        long expected = 2L;
        long actual = testC.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestD() {
        long expected = 0L;
        long actual = testD.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestE() {
        assertThrows(ArithmeticException.class, () -> testE.longValue());
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestF() {
        long expected = 0L;
        long actual = testF.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestG() {
        long expected = 0L;
        long actual = testG.intValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testlongValue_TestH() {
        long expected = 99L;
        long actual = testH.longValue();
        assertEquals(expected, actual);
    }

    // Alle Float-Value tests

    @org.junit.jupiter.api.Test
    void testfloatValue_TestA() {
        float expected = -1.0f;
        float actual = testA.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestB() {
        float expected = -1f;
        float actual = testB.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestC() {
        float expected = 2.0f;
        float actual = testC.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestD() {
        float expected = 0.5f;
        float actual = testD.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestE() {
        float expected = (float) NaN;
        float actual = testE.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestF() {
        float expected = 0f;
        float actual = testF.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestG() {
        float expected = 0.010011876f;
        float actual = testG.floatValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testfloatValue_TestH() {
        float expected = 99.881386f;
        float actual = testH.floatValue();
        assertEquals(expected, actual);
    }

    // Alle Double-Value tests

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestA() {
        double expected = -1d;
        double actual = testA.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestB() {
        double expected = -1d;
        double actual = testB.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestC() {
        double expected = 2.0d;
        double actual = testC.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestD() {
        double expected = 0.5d;
        double actual = testD.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestE() {
        double actual = testE.doubleValue();
        assertEquals(NaN, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestF() {
        double expected = 0d;
        double actual = testF.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestG() {
        double expected = 0.010011875776625354d;
        double actual = testG.doubleValue();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testdoubleValue_TestH() {
        double expected = 99.88138310052668d;
        double actual = testH.doubleValue();
        assertEquals(expected, actual);
    }
}