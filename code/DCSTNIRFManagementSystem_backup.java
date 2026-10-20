import java.io.*;
import java.lang.reflect.Method;


// Class used for cloning and persistence
class Student implements Serializable, Cloneable {

    int studentId;
    String name;
    Address address;

    Student(int studentId, String name, Address address) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
    }

    // Shallow cloning
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }

    void displayStudent() {
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Address    : " + address.city);
    }
}


// Address class for demonstrating deep cloning
class Address implements Serializable {

    String city;

    Address(String city) {
        this.city = city;
    }
}


public class DCSTNIRFManagementSystem {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("       DCST NIRF MANAGEMENT SYSTEM");
        System.out.println("==========================================");

        System.out.println("\n10. REFLECTION, PERSISTENCE, I/O, RTTI & CLONING");
        System.out.println("------------------------------------------");


        // ==========================================
        // 1. CONSOLE I/O
        // ==========================================

        System.out.println("\n1. CONSOLE I/O:");

        String studentName = "Vishal Velip";

        System.out.println("Student Name entered: " + studentName);


        // ==========================================
        // 2. FILE I/O
        // ==========================================

        System.out.println("\n2. FILE I/O:");

        try {

            FileWriter writer =
                    new FileWriter("student_data.txt");

            writer.write(
                    "Student ID: 2663\n" +
                    "Student Name: Vishal Velip\n" +
                    "Course: B.Sc. Computer Science\n"
            );

            writer.close();

            System.out.println(
                    "Student data successfully written to student_data.txt"
            );

        } catch (IOException e) {

            System.out.println(
                    "File I/O Error: " + e.getMessage()
            );
        }


        // ==========================================
        // 3. PERSISTENCE
        // ==========================================

        System.out.println("\n3. PERSISTENCE:");

        Address address =
                new Address("Goa");

        Student student =
                new Student(
                        2663,
                        "Vishal Velip",
                        address
                );

        try {

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream("student.ser")
                    );

            output.writeObject(student);
            output.close();

            System.out.println(
                    "Student object saved to student.ser"
            );

        } catch (IOException e) {

            System.out.println(
                    "Persistence Error: " + e.getMessage()
            );
        }


        // ==========================================
        // 4. REFLECTION
        // ==========================================

        System.out.println("\n4. REFLECTION:");

        Class<?> studentClass = student.getClass();

        System.out.println(
                "Class Name: " + studentClass.getName()
        );

        System.out.println("Methods:");

        Method[] methods =
                studentClass.getDeclaredMethods();

        for (Method method : methods) {

            System.out.println(
                    "- " + method.getName()
            );
        }


        // ==========================================
        // 5. RTTI
        // ==========================================

        System.out.println("\n5. RTTI:");

        if (student instanceof Student) {

            System.out.println(
                    "RTTI confirms that student is an object of Student class."
            );
        }


        // ==========================================
        // 6. SHALLOW CLONING
        // ==========================================

        System.out.println("\n6. SHALLOW CLONING:");

        try {

            Student clonedStudent =
                    student.clone();

            System.out.println("Original Student:");
            student.displayStudent();

            System.out.println("\nCloned Student:");
            clonedStudent.displayStudent();

            System.out.println(
                    "\nShallow clone created successfully."
            );

        } catch (CloneNotSupportedException e) {

            System.out.println(
                    "Cloning Error: " + e.getMessage()
            );
        }


        // ==========================================
        // 7. DEEP CLONING
        // ==========================================

        System.out.println("\n7. DEEP CLONING:");

        try {

            Student deepClonedStudent =
                    deepClone(student);

            deepClonedStudent.address.city =
                    "Panaji";

            System.out.println(
                    "Original Address: "
                    + student.address.city
            );

            System.out.println(
                    "Deep Cloned Address: "
                    + deepClonedStudent.address.city
            );

            System.out.println(
                    "Deep clone has a separate Address object."
            );

        } catch (Exception e) {

            System.out.println(
                    "Deep Cloning Error: "
                    + e.getMessage()
            );
        }


        System.out.println("\nConclusion:");
        System.out.println("Reflection examines class information at runtime.");
        System.out.println("Persistence stores objects for later use.");
        System.out.println("File I/O writes data into a file.");
        System.out.println("RTTI identifies the runtime type of an object.");
        System.out.println("Cloning creates a copy of an object.");


        System.out.println("\n==========================================");
    }


    // Deep cloning using serialization
    static Student deepClone(Student student)
            throws Exception {

        ByteArrayOutputStream byteStream =
                new ByteArrayOutputStream();

        ObjectOutputStream output =
                new ObjectOutputStream(byteStream);

        output.writeObject(student);

        output.close();

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(
                        byteStream.toByteArray()
                );

        ObjectInputStream input =
                new ObjectInputStream(inputStream);

        return (Student) input.readObject();
    }
}