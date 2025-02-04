import java.io.*;
import java.util.*;

class LogFileAnalyzer {
    private Set<String> keywords;
    
    public LogFileAnalyzer(Set<String> keywords) {
        this.keywords = keywords;
    }
    
    public void analyzeLogFile(String inputFile, String outputFile) {
        Map<String, Integer> logCounts = new HashMap<>();
        
        for (String keyword : keywords) {
            logCounts.put(keyword, 0);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        logCounts.put(keyword, logCounts.get(keyword) + 1);
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            
            writer.write("\nLog Analysis Summary:\n");
            for (Map.Entry<String, Integer> entry : logCounts.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " occurrences\n");
            }
            
        } catch (IOException e) {
            System.err.println("Error processing log file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter input log file path: ");
        String inputFile = scanner.nextLine();
        
        System.out.print("Enter output file path: ");
        String outputFile = scanner.nextLine();
        
        Set<String> keywords = new HashSet<>(Arrays.asList("ERROR", "WARNING", "CRITICAL"));
        LogFileAnalyzer analyzer = new LogFileAnalyzer(keywords);
        analyzer.analyzeLogFile(inputFile, outputFile);
        
        System.out.println("Log analysis completed. Results saved in " + outputFile);
        scanner.close();
    }
}