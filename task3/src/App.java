import java.util.*;
import java.sql.*;

class DataBaseConnection {

    static final String url = "jdbc:mysql://localhost:3306/task3";
    static final String user = "root";
    static final String pass = "test123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}


class Admin {
    
    Admin() {
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        while (choice != 5) {
            System.out.println("1.Add new Accountant detail \n2.Display all accountant detail \n3.Edit an Accountant Detail \n4.delete an Accountant \n5.exit");
            choice =  sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manipulateAccountant(false,Integer.MIN_VALUE);
                    break;
                case 2:
                    displayAccountant();
                    break;
                case 3:
                    System.out.println("Enter accountant id to be deleted");
                    manipulateAccountant(true, sc.nextInt());
                    break;
                case 4:
                    System.out.println("Enter accountant id to be deleted");
                    deleteAccountant(sc.nextInt());
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
    }

    //adding an accountant
    private void manipulateAccountant(boolean edit , int id){
        Scanner sc = new Scanner(System.in);

        // Collect user input
        System.out.print("Enter Accountant Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();  
        
        sc.close();
        
        try (Connection con = DataBaseConnection.getConnection()) {
            
            String query = "" , query1 = "INSERT INTO accountant (name, email, password) VALUES (?, ?, ?)" , query2 = "UPDATE accountant SET name = ?, email = ?, password = ? WHERE id = ?";
            
            if(edit) query = query2;
            else query = query1; 

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            if(edit) ps.setInt(4, id);

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //displaying accountants
    private void displayAccountant() {

        try (Connection con = DataBaseConnection.getConnection()) {
           
            String query = "SELECT id, name, email, phone FROM accountant";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst()) { 
                System.out.println("No student records found.");
                return;
            }

            System.out.println("\nList of Accountants:");
            System.out.printf("%-5s %-20s %-25s %-15s%n", "ID", "Name", "Email", "Phone");
            System.out.println("-------------------------------------------");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                System.out.printf("%-5d %-20s %-25s %-15s%n", id, name, email, phone);
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete accountants
    private void deleteAccountant(int id){

        try (Connection con = DataBaseConnection.getConnection()) {
            
            String query = "DELETE FROM accountant WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            //detete row
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Accountant with ID " + id + " deleted successfully!");
            } else {
                System.out.println("No accountant found with ID " + id);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }   

    }
    
}

class Accountant {

    Accountant(){
        
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        while( choice != 6){
            System.out.println("1.Add new Student detail \n2.Display all Student detail \n3.Edit a Student Detail \n4.Check due Fee of Student \n5.To delete student \nexit");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    manipulateStudent(false, Integer.MIN_VALUE);
                    break;
                case 2:
                    displayStudents("");
                    break;
                case 3:
                    System.out.println("Enter an id to be edited in student");
                    manipulateStudent(true, sc.nextInt());
                    break;
                case 4:
                    //students with due
                    displayStudents("WHERE due > 0");
                    break;
                case 5:
                    System.out.println("Enter an id to be deleted from student");
                    deleteStudent(sc.nextInt());
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
        sc.close();
    }

    private static void manipulateStudent(Boolean edit, int id){

        try (Connection con = DataBaseConnection.getConnection()) {
 
            Scanner sc = new Scanner(System.in);
            
            //initialization
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
    
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
    
            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            //sc.nextDouble() overflows or skips some scanner inputs
            System.out.print("Enter Fee Amount: ");
            double fee = sc.nextDouble();
            sc.nextLine();
            
            System.out.print("Enter Paid Amount: ");
            double paid = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter Due Amount: ");
            double due = sc.nextDouble();
            sc.nextLine();
            
            System.out.print("Enter Address: ");
            String address = sc.nextLine();
    
            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();

            String query = "", query1 = "INSERT INTO student(name, email, course, fee, paid, due, address, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", query2 = "UPDATE accountant SET name = ?, email = ?, course = ?, fee = ?, paid = ?, due = ?, address = ?, phone = ? WHERE id = ?";
            
            if(edit) query = query2;
            else query = query1;
            
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);

            if(edit) ps.setInt(9,id);
            
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("There has been an exception in your entries, " + e.getMessage());
            e.printStackTrace();
        
        }
    }
    
    //display student
    private static void displayStudents(String Due){

        try (Connection con = DataBaseConnection.getConnection()){

            String query = "SELECT * FROM student " + Due;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) { 
                System.out.println("No student records found.");
                return;
            }

            // student details
            System.out.println("\nStudent Details:");
            System.out.printf("%-5s %-20s %-25s %-15s %-10s %-10s %-10s %-30s %-15s\n", 
                "ID", "Name", "Email", "Course", "Fee", "Paid", "Due", "Address", "Phone");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String course = rs.getString("course");
                double fee = rs.getDouble("fee");
                double paid = rs.getDouble("paid");
                double due = rs.getDouble("due");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                System.out.printf("%-5d %-20s %-25s %-15s %-10.2f %-10.2f %-10.2f %-30s %-15s\n", 
                    id, name, email, course, fee, paid, due, address, phone);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //delete student
    private static void deleteStudent(int id){
        try (Connection con = DataBaseConnection.getConnection()) {
            
            String query = "DELETE FROM student WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            //detete row
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Accountant with ID " + id + " deleted successfully!");
            } else {
                System.out.println("No accountant found with ID " + id);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }   

    }
}

class validate {
    validate(String privilege) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String name = sc.nextLine();
        System.out.println("enter password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM " + privilege + " WHERE name = ? AND password = ?";
        Boolean valid = false;
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = DataBaseConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            valid = rs.next();  //checker

            rs.close();
            pstmt.close();
            DataBaseConnection.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (valid) {
            System.out.println("good to go");
            if(privilege == "admin") { new Admin(); }
            else { new Accountant(); }
            
        }

        else{
            System.out.println("Credentials failed");
        }

    }
}

//main class
public class App {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {

            System.out.println("1. Admin \n2. Accountant \n3.exit");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    new validate("admin");
                    break;
                case 2:
                    new validate("accountant");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

    }
}

