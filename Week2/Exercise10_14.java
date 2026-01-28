import java.util.GregorianCalendar;

/** UML for this assignment is located in Week2/Exercise10_14_UML.png */
public class Exercise10_14 {
    public static void main(String[] args) {
        MyDate date1 = new MyDate();
        MyDate date2 = new MyDate(34355555133101L);

        System.out.println("MyDate(): " + "year= " + date1.getYear() + ", month= " + date1.getMonth() + ", day= " + date1.getDay());
        System.out.println("MyDate(34355555133101L): " + "year= " + date2.getYear() + ", month= " + date2.getMonth() + ", day= " + date2.getDay());
    }
}

class MyDate {
    // month is 0-based, so 0= January, 1= February, etc...
    private int year;
    private int month;
    private int day;

    /** No-arg constructor for the current date */
    public MyDate() {
        GregorianCalendar cal = new GregorianCalendar(); // now
        this.year = cal.get(GregorianCalendar.YEAR);
        this.month = cal.get(GregorianCalendar.MONTH);
        this.day = cal.get(GregorianCalendar.DAY_OF_MONTH);
    }

    /** Construct from elapsed milliseconds since Jan 1, 1970 */
    public MyDate(long elapsedTime) {
        setDate(elapsedTime);
    }

    /** Construct from year, month, and day */
    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /** Set date using elapsed milliseconds since Jan 1, 1970 */
    public void setDate(long elapsedTime) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(elapsedTime);

        this.year = cal.get(GregorianCalendar.YEAR);
        this.month = cal.get(GregorianCalendar.MONTH);
        this.day = cal.get(GregorianCalendar.DAY_OF_MONTH);
    }
}
