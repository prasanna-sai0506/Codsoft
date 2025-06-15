import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseRegistrationSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_course_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root"; // Change if needed

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n*****STUDENT COURSE REGISTRATION SYSTEM *****");
                System.out.println("1. Add Student");
                System.out.println("2. Add Course");
                System.out.println("3. Register Student for Course");
                System.out.println("4. Drop Registered Course");
                System.out.println("5. Display Course Listing");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                try (Connection conn = getConnection()) {
                    switch (choice) {
                        case 1 -> addStudent(sc, conn);
                        case 2 -> addCourse(sc, conn);
                        case 3 -> registerStudent(sc, conn);
                        case 4 -> dropCourse(sc, conn);
                        case 5 -> listCourses(conn);
                        case 6 -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please choose 1 to 6.");
                    }
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input! Please enter numbers only.");
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }
    }

    static void addStudent(Scanner sc, Connection conn) throws SQLException {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO student (name) VALUES (?)");
        ps.setString(1, name);
        ps.executeUpdate();
        System.out.println("Student added successfully.");
    }

    static void addCourse(Scanner sc, Connection conn) throws SQLException {
        System.out.print("Enter course code: ");
        String code = sc.nextLine();
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();
        System.out.print("Enter capacity: ");
        int capacity = Integer.parseInt(sc.nextLine());
        System.out.print("Enter schedule (e.g., Mon-Wed 10:00AM): ");
        String schedule = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO course (course_code, title, description, capacity, schedule) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, code);
        ps.setString(2, title);
        ps.setString(3, desc);
        ps.setInt(4, capacity);
        ps.setString(5, schedule);
        ps.executeUpdate();
        System.out.println(" Course added successfully.");
    }

    static void registerStudent(Scanner sc, Connection conn) throws SQLException {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());
        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(sc.nextLine());

        PreparedStatement checkCap = conn.prepareStatement(
            "SELECT capacity FROM course WHERE course_id = ?");
        checkCap.setInt(1, courseId);
        ResultSet courseRs = checkCap.executeQuery();
        if (!courseRs.next()) {
            System.out.println("Course not found.");
            return;
        }
        int totalCapacity = courseRs.getInt("capacity");

        PreparedStatement count = conn.prepareStatement(
            "SELECT COUNT(*) AS count FROM registration WHERE course_id = ?");
        count.setInt(1, courseId);
        ResultSet countRs = count.executeQuery();
        countRs.next();
        int registered = countRs.getInt("count");

        if (registered >= totalCapacity) {
            System.out.println("Course is full. Cannot register.");
            return;
        }

        PreparedStatement reg = conn.prepareStatement(
            "INSERT INTO registration (student_id, course_id) VALUES (?, ?)");
        reg.setInt(1, studentId);
        reg.setInt(2, courseId);
        reg.executeUpdate();
        System.out.println("Student registered successfully.");
    }

    static void dropCourse(Scanner sc, Connection conn) throws SQLException {
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());
        System.out.print("Enter course ID to drop: ");
        int courseId = Integer.parseInt(sc.nextLine());

        PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM registration WHERE student_id = ? AND course_id = ?");
        ps.setInt(1, studentId);
        ps.setInt(2, courseId);
        int result = ps.executeUpdate();

        System.out.println(" Course dropped. Not registered or already dropped.");
    }

    static void listCourses(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM course");
        System.out.printf("%-5s %-10s %-25s %-8s %-20s\n", "ID", "Code", "Title", "Cap", "Schedule");
        while (rs.next()) {
            System.out.printf("%-5d %-10s %-25s %-8d %-20s\n",
                rs.getInt("course_id"),
                rs.getString("course_code"),
                rs.getString("title"),
                rs.getInt("capacity"),
                rs.getString("schedule"));
        }
    }
}
