public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private double gpa;

    public Student(int id, String firstName, String lastName, double gpa) {
        if (id < 1 || id > 99 || firstName == null || firstName.length() < 2 ||
            lastName == null || lastName.length() < 2 || gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("Invalid!");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public Student setId(int id) {
        if (id < 1 || id > 99) {
            throw new IllegalArgumentException("Invalid id for Student");
        }
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Student setFirstName(String firstName) {
        if (firstName == null || firstName.length() < 2) {
            throw new IllegalArgumentException("Invalid firstName for Student");
        }
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Student setLastName(String lastName) {
        if (lastName == null || lastName.length() < 2) {
            throw new IllegalArgumentException("Invalid lastName");
        }
        this.lastName = lastName;
        return this;
    }

    public double getGpa() {
        return gpa;
    }

    public Student setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("Invalid GPA");
        }
        this.gpa = gpa;
        return this;
    }
}
