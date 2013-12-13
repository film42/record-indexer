package client.modules.spellChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/12/13
 * Time: 2:36 PM
 */
public class SpellChecker {

    private Set<String> words;

    public Set<String> getSuggestionsForString(String word) {

        words = new TreeSet<>();

        // First Distance
        words.addAll(alterationDistance(word));
        words.addAll(insertionDistance(word));
        words.addAll(transpositionDistance(word));
        words.addAll(deletionDistance(word));

        Set<String> wordsCopy = new TreeSet<>(words);

        for(String w : wordsCopy) {
            words.addAll(alterationDistance(w));
            words.addAll(insertionDistance(w));
            words.addAll(transpositionDistance(w));
            words.addAll(deletionDistance(w));
        }

        return words;
    }

    public void restrictToList(List<String> list) {
        Set<String> set = new TreeSet<>();

        for(String similar : words) {
            for(String foregin : list) {
                if(foregin.toLowerCase().equals(similar.toLowerCase())) {
                    set.add(foregin);
                }
            }
        }

        words = set;
    }

    public String[] getWordArray() {

        String[] arr = new String[words.size()];

        ArrayList<String> temp = new ArrayList<>(words);

        for(int i = 0; i < temp.size(); i++) {
            arr[i] = temp.get(i);
        }

        return arr;
    }

    public List<String> alterationDistance(String word) {
        // Declare a new String list (to avoid making a comparator).
        List<String> wordList = new ArrayList<String>();

        // Rotate through the word cutting a letter
        for(int i = 0; i <= (word.length() - 1); i++ ) {
            // Build string positions
            String firstHalf = word.substring(0, i);
            String lastHalf = word.substring(i+1);

            for(int y = 0; y < 26; y++) {
                // Create character and final word
                String letter = Character.toString((char) ('a' + y));
                String cutlet = firstHalf + letter + lastHalf;

                // Skip if it's the same
                if(word.equals(cutlet)) continue;

                // Add to list
                wordList.add(cutlet);
            }
        }

        // Return the node with highest value
        return wordList;
    }

    public List<String> insertionDistance(String word) {
        // Declare a new String list (to avoid making a comparator).
        List<String> wordList = new ArrayList<String>();

        // Rotate through the word cutting a letter
        for(int i = 0; i <= (word.length()); i++ ) {
            // Build string positions
            String firstHalf = word.substring(0, i);
            String lastHalf = word.substring(i);

            for(int y = 0; y < 26; y++) {
                // Create character and final word
                String letter = Character.toString((char) ('a' + y));
                String cutlet = firstHalf + letter + lastHalf;

                // Skip if it's the same
                if(word.equals(cutlet)) continue;

                // Add to list
                wordList.add(cutlet);
            }
        }

        // Return the node with highest value
        return wordList;
    }

    public List<String> transpositionDistance(String word) {
        // Declare a new String list (to avoid making a comparator).
        List<String> wordList = new ArrayList<String>();

        // Rotate through the word cutting a letter
        for(int i = 0; i <= (word.length() - 2); i++ ) {
            // Build string positions
            String letterOne = word.substring(i,i+1);
            String letterTwo = word.substring(i+1,i+2);
            String firstHalf = word.substring(0, i);
            String lastHalf = word.substring(i+2);
            String cutlet = firstHalf + letterTwo + letterOne + lastHalf;

            // Add to word List
            wordList.add(cutlet);
        }

        // Return the node with highest value
        return wordList;
    }

    public List<String> deletionDistance(String word) {
        // Declare a new String list (to avoid making a comparator).
        List<String> wordList = new ArrayList<String>();

        // Rotate through the word cutting a letter
        for(int i = 1; i <= word.length(); i++ ) {
            // Build
            String base = word.substring(0,i-1);
            String cutlet = base + word.substring(i);

            wordList.add(cutlet);

        }

        // Return the node with highest value
        return wordList;
    }

}
