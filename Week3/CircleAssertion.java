public class CircleAssertion {
    // this class is to test the Circle class, which extends GeometricObject and implements Comparable
    public static void main(String[] args) {
        Circle c1 = new Circle(9);
        Circle c2 = new Circle(7);
        Circle c3 = new Circle(9);

        System.out.println("c1 compareTo c2: " + c1.compareTo(c2)); // should print 1 for true
        System.out.println("c1 equals c3: " + c1.equals(c3)); // should print true
        c1.printCircle(); // should print the date and the radius of c1
    }
}
