import java.io.*;
import java.util.*;

class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(String name, String email) {
        users.add(new User(name, email));
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.getName() + "," + user.getEmail());
                writer.newLine();
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String filename) {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    addUser(parts[0], parts[1]);
                }
            }
            System.out.println("Users loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void displayUsers() {
        for (User user : users) {
            System.out.println("Name: " + user.getName() + ", Email: " + user.getEmail());
        }
    }
}

class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

public class UserManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userManager = new UserService();
        
        System.out.print("Enter filename to save/load users: ");
        String filename = scanner.nextLine();
        
        while (true) {
            System.out.println("\n1. Add User\n2. Save Users to File\n3. Load Users from File\n4. Display Users\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    userManager.addUser(name, email);
                    break;
                case 2:
                    userManager.saveUsersToFile(filename);
                    break;
                case 3:
                    userManager.loadUsersFromFile(filename);
                    break;
                case 4:
                    userManager.displayUsers();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
