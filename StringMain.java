import java.util.*;

class StringProcessor {
    public String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public int countOccurrences(String text, String sub) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    public String splitAndCapitalize(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
}

public class StringMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringProcessor processor = new StringProcessor();
        
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        System.out.println("Reversed: " + processor.reverseString(input));
        
        System.out.print("Enter substring to count occurrences: ");
        String sub = scanner.nextLine();
        System.out.println("Occurrences: " + processor.countOccurrences(input, sub));
        
        System.out.println("Capitalized: " + processor.splitAndCapitalize(input));
        
        scanner.close();
    }
}