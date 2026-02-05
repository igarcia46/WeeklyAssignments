// Look for WRITE YOUR CODE to write your code
import java.math.*;
import java.util.Scanner;

public class Exercise13_15 {
    public static void main(String[] args) {
        // Prompt the user to enter two Rational numbers
        Scanner input = new Scanner(System.in);
        System.out.print("Enter rational r1 with numerator and denominator seperated by a space: ");
        String n1 = input.next();
        String d1 = input.next();

        System.out.print("Enter rational r2 with numerator and denominator seperated by a space: ");
        String n2 = input.next();
        String d2 = input.next();

        RationalUsingBigInteger r1 = new RationalUsingBigInteger(
                new BigInteger(n1), new BigInteger(d1));
        RationalUsingBigInteger r2 = new RationalUsingBigInteger(
                new BigInteger(n2), new BigInteger(d2));

        // Display results
        System.out.println(r1 + " + " + r2 + " = " + r1.add(r2));
        System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2));
        System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2));
        System.out.println(r1 + " / " + r2 + " = " + r1.divide(r2));
        System.out.println(r2 + " is " + r2.doubleValue());
    }
}

// Name the revised Rational class RationalUsingBigInteger
class RationalUsingBigInteger extends Number
        implements Comparable<RationalUsingBigInteger> {
    // Data fields for numerator and denominator
    private BigInteger numerator = BigInteger.ZERO;
    private BigInteger denominator = BigInteger.ONE;

    /** default constructor */
    public RationalUsingBigInteger() {
        this(BigInteger.ZERO, BigInteger.ONE);
    }

    /** rational with a specified numerator and denominator */
    public RationalUsingBigInteger(BigInteger numerator, BigInteger denominator) {
        // throw for undefined
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator cannot be zero");
        }

        // reduce
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);

        // keep denominator positive
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public RationalUsingBigInteger add(RationalUsingBigInteger secondRational) {
        BigInteger n = numerator.multiply(secondRational.getDenominator())
                .add(denominator.multiply(secondRational.getNumerator()));
        BigInteger d = denominator.multiply(secondRational.getDenominator());
        return new RationalUsingBigInteger(n, d);
    }

    public RationalUsingBigInteger subtract(RationalUsingBigInteger secondRational) {
        BigInteger n = numerator.multiply(secondRational.getDenominator())
                .subtract(denominator.multiply(secondRational.getNumerator()));
        BigInteger d = denominator.multiply(secondRational.getDenominator());
        return new RationalUsingBigInteger(n, d);
    }

    public RationalUsingBigInteger multiply(RationalUsingBigInteger secondRational) {
        BigInteger n = numerator.multiply(secondRational.getNumerator());
        BigInteger d = denominator.multiply(secondRational.getDenominator());
        return new RationalUsingBigInteger(n, d);
    }

    public RationalUsingBigInteger divide(RationalUsingBigInteger secondRational) {
        BigInteger n = numerator.multiply(secondRational.getDenominator());
        BigInteger d = denominator.multiply(secondRational.getNumerator());
        return new RationalUsingBigInteger(n, d);
    }

    @Override
    public String toString() {
        if (denominator.equals(BigInteger.ONE)) {
            return numerator.toString();
        }
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RationalUsingBigInteger)) {
            return false;
        }
        RationalUsingBigInteger other = (RationalUsingBigInteger) obj;
        return numerator.equals(other.numerator) && denominator.equals(other.denominator);
    }

    @Override
    public int compareTo(RationalUsingBigInteger o) {
        BigInteger left = numerator.multiply(o.denominator);
        BigInteger right = denominator.multiply(o.numerator);
        return left.compareTo(right);
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }
}
