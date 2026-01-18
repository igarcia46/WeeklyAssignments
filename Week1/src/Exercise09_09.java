/** The UML for this assignment is UML.png located in Week1\src\UML.png */
public class Exercise09_09 {
    public static void main(String[] args) {
        // create 3 "RegularPolygon" objects
        RegularPolygon p1 = new RegularPolygon();
        RegularPolygon p2 = new RegularPolygon(6, 4);
        RegularPolygon p3 = new RegularPolygon(10, 4, 5.6, 7.8);

        // display results
        printPolygonInfo("Polygon 1", p1);
        printPolygonInfo("Polygon 2", p2);
        printPolygonInfo("Polygon 3", p3);
    }

    // method to print out data on separate lines
    private static void printPolygonInfo(String label, RegularPolygon p) {
        System.out.println(label);
        System.out.printf("Perimeter: %.2f%n", p.getPerimeter());
        System.out.printf("Area: %.2f%n%n", p.getArea());
    }
}

class RegularPolygon {
    // data fields
    private int n = 3;
    private double side = 1;
    private double x = 0;
    private double y = 0;

    // no arg constructor
    public RegularPolygon() {
    }

    // constructor centered at (0, 0)
    public RegularPolygon(int n, double side) {
        this.n = n;
        this.side = side;
        this.x = 0;
        this.y = 0;
    }

    // constructor, specified n, side, x, y
    public RegularPolygon(int n, double side, double x, double y) {
        this.n = n;
        this.side = side;
        this.x = x;
        this.y = y;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // perimeter
    public double getPerimeter() {
        return n * side;
    }

    // area = (n * s^2) / (4 * tan(pi / n))
    public double getArea() {
        return (n * side * side) / (4.0 * Math.tan(Math.PI / n));
    }
}
