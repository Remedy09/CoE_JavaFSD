import java.util.*;

class Anagram {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) return result;
        
        s = s.toLowerCase();
        p = p.toLowerCase();
        
        int[] pCount = new int[26];
        int[] sCount = new int[26];
        int pLength = p.length();
        
        for (int i = 0; i < pLength; i++) {
            if (!Character.isLetter(p.charAt(i)) || !Character.isLetter(s.charAt(i))) return result;
            pCount[p.charAt(i) - 'a']++;
            sCount[s.charAt(i) - 'a']++;
        }
        
        if (Arrays.equals(pCount, sCount)) {
            result.add(0);
        }
        
        for (int i = pLength; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) return result;
            sCount[s.charAt(i) - 'a']++;
            sCount[s.charAt(i - pLength) - 'a']--; 
            
            if (Arrays.equals(pCount, sCount)) {
                result.add(i - pLength + 1);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Anagram finder = new Anagram();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter string s: ");
        String s = scanner.nextLine();
        System.out.print("Enter string p: ");
        String p = scanner.nextLine();
        
        List<Integer> indices = finder.findAnagrams(s, p);
        System.out.println("Starting indices of anagrams: " + indices);
        
        scanner.close();
    }
}
