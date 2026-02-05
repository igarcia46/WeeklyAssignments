/**
 * To test this class, run the CircleAssertion main method
 * The UML for this class is located in Week3/Circle_UML.png
 * **/

public class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public int compareTo(Circle other) {
        return Double.compare(this.radius, other.radius);
    }

    @Override
    public boolean equals(Object obj) {
        // first check if even a circle
        if (!(obj instanceof Circle)) {
            return false;
        }
        Circle other = (Circle) obj;
        return this.radius == other.radius;
    }

    public void printCircle() {
        System.out.println("The circle is created " + getDateCreated() + " and the radius is: " + radius);
    }
}