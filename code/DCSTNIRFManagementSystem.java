
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

// REQUIREMENT 2: Enum
enum CourseType {
    UNDERGRADUATE, POSTGRADUATE
}

// REQUIREMENT 1: Class, constructors and shared references
class Address implements Serializable {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Student implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    int studentId;
    String name;
    Address address;

    // REQUIREMENT 2: Static counter
    static int memberCount = 0;

    Student(int studentId, String name, Address address) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        memberCount++;
    }

    static void displayCount() {
        System.out.println("Members created: " + memberCount);
    }

    void display() {
        System.out.println(studentId + " - " + name
                + " - " + address.city);
    }

    // REQUIREMENT 7: Overriding
    public String toString() {
        return studentId + " - " + name;
    }

    // REQUIREMENT 10: Shallow cloning
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}

// REQUIREMENT 3: Multiple interface inheritance
interface AcademicWork {
    void performWork();
}

interface ReportWork {
    void prepareReport();
}

class StudentDevelopmentOfficer implements AcademicWork, ReportWork {
    public void performWork() {
        System.out.println("Coordinates student development activities.");
    }

    public void prepareReport() {
        System.out.println("Prepares the NIRF report.");
    }
}

// REQUIREMENT 4: Access modifiers
class Staff {
    public String staffName;
    private double salary;
    protected String department;

    Staff(String staffName, double salary, String department) {
        this.staffName = staffName;
        this.salary = salary;
        this.department = department;
    }

    public void displayStaff() {
        System.out.println("Staff: " + staffName);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
    }
}

class TeachingStaff extends Staff {
    TeachingStaff(String name, double salary, String department) {
        super(name, salary, department);
    }

    void displayDepartment() {
        System.out.println("Protected department: " + department);
    }
}

// REQUIREMENT 5: Abstract class and interface
abstract class Activity {
    abstract void conduct();
}

interface Reportable {
    void generateReport();
}

class AcademicActivity extends Activity implements Reportable {
    public void conduct() {
        System.out.println("Academic activity: ISA, SEA, midpoint, study tour and placements.");
    }

    public void generateReport() {
        System.out.println("Academic activity report generated.");
    }
}

class NonAcademicActivity extends Activity implements Reportable {
    public void conduct() {
        System.out.println("Non-academic activity: InfoFest, Plateaunia and Play-on.");
    }

    public void generateReport() {
        System.out.println("Non-academic activity report generated.");
    }
}

// REQUIREMENT 6: Class as a type and message passing
interface NIRFReportable {
    void prepareNIRFReport();
}

class StudentReport implements NIRFReportable {
    public void prepareNIRFReport() {
        System.out.println("Student statistics added to the report.");
    }
}

class StaffReport implements NIRFReportable {
    public void prepareNIRFReport() {
        System.out.println("Teaching and non-teaching staff details added.");
    }
}

// REQUIREMENT 7: Overloading
class ReportGenerator {
    void generate(String title) {
        System.out.println("Report: " + title);
    }

    void generate(String title, int year) {
        System.out.println("Report: " + title + " for year " + year);
    }
}

// REQUIREMENT 8: Generic class and generic function
class NIRFData<T> {
    private T data;

    NIRFData(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }
}

class GenericUtility {
    static <T> void displayItem(T item) {
        System.out.println(item);
    }
}

// REQUIREMENT 9: Custom exception
class InvalidStudentException extends Exception {
    InvalidStudentException(String message) {
        super(message);
    }
}

public class DCSTNIRFManagementSystem {

    // Requirement 6: Accept any object implementing the interface
    static void processReport(NIRFReportable report) {
        report.prepareNIRFReport();
    }

    // Requirement 10: Deep cloning using serialization
    static Student deepClone(Student student) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(student);
        out.close();

        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bytes.toByteArray()));
        Student copy = (Student) in.readObject();
        in.close();
        return copy;
    }

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("      DCST NIRF MANAGEMENT SYSTEM");
        System.out.println("==========================================");

        // REQUIREMENT 1
        System.out.println("\n1. OBJECT REFERENCING AND CONSTRUCTORS");
        Address sharedAddress = new Address("Goa");
        Student s1 = new Student(2663, "Vishal Velip", sharedAddress);
        Student s2 = new Student(2664, "Student Two", sharedAddress);

        System.out.println("Before changing shared address:");
        s1.display();
        s2.display();

        s2.address.city = "Panaji";
        System.out.println("After changing address through s2:");
        s1.display();
        s2.display();
        System.out.println("Both students share the same Address object.");

        // REQUIREMENT 2
        System.out.println("\n2. STATIC MEMBERS AND ENUMS");
        Student.displayCount();
        CourseType course = CourseType.UNDERGRADUATE;
        System.out.println("Course type: " + course);
        System.out.println("Static counter is shared by all Student objects.");

        // REQUIREMENT 3
        System.out.println("\n3. MULTIPLE INHERITANCE");
        StudentDevelopmentOfficer officer = new StudentDevelopmentOfficer();
        officer.performWork();
        officer.prepareReport();
        System.out.println("Java classes extend one class but can implement multiple interfaces.");
        System.out.println("Java does not support multiple inheritance of classes.");

        // REQUIREMENT 4
        System.out.println("\n4. ACCESS MODIFIERS");
        TeachingStaff staff = new TeachingStaff(
                "Teaching Staff", 35000, "Computer Science");
        System.out.println("Public name: " + staff.staffName);
        staff.displayDepartment();
        staff.displayStaff();
        System.out.println("Private salary is accessed through the class method.");

        // REQUIREMENT 5
        System.out.println("\n5. ABSTRACT CLASSES AND INTERFACES");
        Activity academic = new AcademicActivity();
        Activity nonAcademic = new NonAcademicActivity();
        academic.conduct();
        nonAcademic.conduct();
        ((Reportable) academic).generateReport();
        ((Reportable) nonAcademic).generateReport();

        // REQUIREMENT 6
        System.out.println("\n6. CLASS AS A TYPE AND MESSAGE PASSING");
        processReport(new StudentReport());
        processReport(new StaffReport());

        // REQUIREMENT 7
        System.out.println("\n7. POLYMORPHISM");
        Activity activity = new AcademicActivity();
        activity.conduct(); // Overriding
        ReportGenerator generator = new ReportGenerator();
        generator.generate("Annual NIRF Report");
        generator.generate("Annual NIRF Report", 2026); // Overloading

        // REQUIREMENT 8
        System.out.println("\n8. GENERICS AND COLLECTION FRAMEWORK");
        NIRFData<String> genericData = new NIRFData<>("DCST");
        System.out.println("Generic data: " + genericData.getData());
        GenericUtility.displayItem("Generic function works.");

        List<String> courses = new ArrayList<>();
        courses.add("B.Sc. Computer Science");
        courses.add("M.Sc. Computer Science");
        System.out.println("List: " + courses);

        Queue<String> queue = new LinkedList<>();
        queue.add("ISA");
        queue.add("SEA");
        System.out.println("Queue: " + queue);

        Set<String> activities = new HashSet<>();
        activities.add("InfoFest");
        activities.add("InfoFest");
        activities.add("Plateaunia");
        System.out.println("Set (unique items): " + activities);

        Map<String, String> subjects = new HashMap<>();
        subjects.put("Semester 1", "Programming Fundamentals");
        subjects.put("Semester 2", "Data Structures");
        System.out.println("Map: " + subjects);

        // REQUIREMENT 9
        System.out.println("\n9. EXCEPTION HANDLING");
        try {
            int number = 10 / 0;
            System.out.println(number);
        } catch (ArithmeticException e) {
            System.out.println("Caught runtime error: " + e);
        } finally {
            System.out.println("Finally block executed.");
        }

        try {
            validateStudent(0);
        } catch (InvalidStudentException e) {
            System.out.println("Custom exception: " + e.getMessage());
        }

        // REQUIREMENT 10
        System.out.println("\n10. REFLECTION, PERSISTENCE, I/O, RTTI AND CLONING");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a student name for console I/O: ");
        String enteredName = scanner.nextLine();

        try (FileWriter writer = new FileWriter("student_data.txt")) {
            writer.write("Student ID: 2663\n");
            writer.write("Student Name: " + enteredName + "\n");
            writer.write("Course: B.Sc. Computer Science\n");
            System.out.println("Data written to student_data.txt.");
        } catch (IOException e) {
            System.out.println("File I/O error: " + e.getMessage());
        }

        Address cloneAddress = new Address("Goa");
        Student original = new Student(2663, enteredName, cloneAddress);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("student.ser"))) {
            out.writeObject(original);
            System.out.println("Object persisted to student.ser.");
        } catch (IOException e) {
            System.out.println("Persistence error: " + e.getMessage());
        }

        System.out.println("Reflection class: " + original.getClass().getName());
        for (Method method : original.getClass().getDeclaredMethods()) {
            System.out.println("Method: " + method.getName());
        }

        if (original instanceof Student) {
            System.out.println("RTTI: object is an instance of Student.");
        }

        try {
            Student shallowCopy = original.clone();
            System.out.println("Shallow clone created: " + shallowCopy.name);
            System.out.println("Shared address before change: "
                    + original.address.city);
            shallowCopy.address.city = "Margao";
            System.out.println("Original address after shallow clone change: "
                    + original.address.city);

            Student deepCopy = deepClone(original);
            deepCopy.address.city = "Panaji";
            System.out.println("Original address after deep clone change: "
                    + original.address.city);
            System.out.println("Deep clone address: " + deepCopy.address.city);
        } catch (Exception e) {
            System.out.println("Cloning error: " + e.getMessage());
        }

        // Case study summary
        System.out.println("\nDCST CASE STUDY SUMMARY");
        System.out.println("Academic activities: ISA, midpoint, SEA, study tours, placements,");
        System.out.println("seminars, workshops, internships, projects and guest lectures.");
        System.out.println("Non-academic activities: InfoFest, Plateaunia, Play-on,");
        System.out.println("sports, cultural events, clubs and community outreach.");
        System.out.println("Programs/courses: verify current official DCST and GBS offerings.");
        System.out.println("Example subjects: Programming, Data Structures, DBMS, Networks,");
        System.out.println("Operating Systems, Software Engineering and Data Science.");

        System.out.println("\nAll 10 OOP requirement demonstrations completed.");
        System.out.println("==========================================");
        scanner.close();
    }

    static void validateStudent(int id) throws InvalidStudentException {
        if (id <= 0) {
            throw new InvalidStudentException("Student ID must be positive.");
        }
    }
}
