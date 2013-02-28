package erik.boggle.util;

import java.util.*;

/**
 * @author erik
 */
public class SolvedWords {
    public static Collection<String> solvedWords;

    private static Comparator<String> lengthComparator = new Comparator<String>() {
        @Override
        public int compare(String s, String s1) {
            if(s.length() == s1.length()) {
                return 0;
            }
            return s.length() > s1.length() ? -1 : 1;

        }
    };

    public static List<String> sort(Collection<String> words) {
        List<String> sorted = new ArrayList<String>();
        for(String word : words) {
            if(word.length() > 2) {
                sorted.add(word);
            }
        }
        Collections.sort(sorted, lengthComparator);
        return sorted;
    }

    public static Collection<String> getSolvedWords() {
        return solvedWords;
    }

    public static void setSolvedWords(Collection<String> s) {
        solvedWords = s;
    }
}
