import java.util.Scanner;

class ReciprocalCalc {
    public static void processInput() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter a fraction (a/b): ");
            String input = scanner.next();
            
            String[] parts = input.split("/");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid format. Please enter in the form a/b.");
            }
            
            int numerator = Integer.parseInt(parts[0]);
            int denominator = Integer.parseInt(parts[1]);
            
            if (numerator == 0) {
                throw new ArithmeticException("Cannot divide by zero.");
            }
            
            double reciprocal = (double) denominator / numerator;
            System.out.println("Reciprocal: " + reciprocal);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter integers in the form a/b.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("Input processing complete.");
        }
    }
    
    public static void main(String[] args) {
        processInput();
    }
}