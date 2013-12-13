package client.modules.spellChecker;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 9/4/13
 * Time: 10:13 AM
 */
public class Dictionary implements Trie {

    private WordNode root = new WordNode();

    private int nodeCount = 1;

    private int wordCount = 0;

    public Dictionary() {}

    public void add(String word) {
        // Create String[] with lower case word
        char[] wordLetters = word.toLowerCase().toCharArray();

        // Increment wordCount
        wordCount++;

        // Set state to root
        WordNode state = root;

        for(int i = 0; i < wordLetters.length; i++) {
            char letter = wordLetters[i];

            WordNode node = state.getChild(letter);

            // Letter exists? Add if doesn't
            if(node == null) {
                // Add new Node to state
                try {
                    state.putChild(letter, new WordNode());
                } catch (Exception e) {
                    System.out.println(word);
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
                // Increment nodeCount
                nodeCount++;
                // Set node as state's new child
                node = state.getChild(letter);
            }

            // End of the line, increment
            if(i == (wordLetters.length - 1)) {
                // Reached the end of the word, let's increment the Node's freq count
                node.frequency++;
            }

            // Move state position to child node
            state = node;
        }

    }

    public Trie.Node find(String word) {
        // Create String[] with lower case word
        char[] wordLetters = word.toLowerCase().toCharArray();

        // Set state to root
        WordNode state = getRootNode();

        for(char letter : wordLetters) {
            // Get child node
            WordNode node = state.getChild(letter);

            // Letter exists? Add if doesn't
            if(node == null) {
                state = node;
                break;
            }

            // Move state position
            state = node;
        }

        if(state != null && state.frequency > 0) {
            // Print upon successfully finding a word.
            return state;
        } else {
            return null;
        }
    }


    //
    // Word Modification Methods
    //

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

    public List<String> deletionDistance(List<String> wordList) {
        List<String> returnWordList = new ArrayList<String>();

        for(String word : wordList) {
            returnWordList.addAll(deletionDistance(word));
        }

        return returnWordList;
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

    public List<String> transpositionDistance(List<String> wordList) {
        List<String> returnWordList = new ArrayList<String>();

        for(String word : wordList) {
            returnWordList.addAll(transpositionDistance(word));
        }

        return returnWordList;
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

    public List<String> alterationDistance(List<String> wordList) {
        List<String> returnWordList = new ArrayList<String>();

        for(String word : wordList) {
            returnWordList.addAll(alterationDistance(word));
        }

        return returnWordList;
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

    public List<String> insertionDistance(List<String> wordList) {
        List<String> returnWordList = new ArrayList<String>();

        for(String word : wordList) {
            returnWordList.addAll(insertionDistance(word));
        }

        return returnWordList;
    }

    //
    // Begin Helper Methods
    //

    public int getWordCount() {
        return this.wordCount;
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    public WordNode getRootNode() {
        return this.root;
    }

    //
    // Required Override Methods
    //

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        recursiveToString(root, "", sb);
        return sb.toString();
    }

    private void recursiveToString(WordNode node, String word, StringBuilder sb) {
        // Base Case
        if(node.getChildrenCount() == 0) {
            return;
        }

        // Check if node make a valid word
        if(node.getValue() > 0) {
            sb.append(String.format("%s %d\n", word, node.getValue()));
        }

        // Check a node's children
        for(int i = 0; i < 26; i++) {
            // Get the char (letter)
            char letter = (char) ('a' + i);
            // Get the child node's child
            WordNode childNode = node.getChild(letter);
            // Sanity check for null
            if(childNode == null) continue;
            // Send down the line to be checked again
            recursiveToString(childNode, word + Character.toString(letter), sb);
        }
    }

    @Override
    public int hashCode() {
        return (wordCount ^ nodeCount ^ Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        // Sanity Comparison
        if(o == null) return false;
        if(o == this) return true;
        if(this.getClass() != o.getClass()) return false;

        // Cast and let's begin
        Dictionary other = (Dictionary)o;

        // Basic comparison (should be the same as just calling hashCode()
        if(getNodeCount() != other.getNodeCount()) return false;
        if(getWordCount() != other.getWordCount()) return false;

        // Recursive Compare
        return recursiveCompare(this.getRootNode(), other.getRootNode());
    }

    private boolean recursiveCompare(WordNode thisNode, WordNode otherNode) {
        for(int i = 0; i < 26; i++) {
            char letter = (char) ('a' + i);
            WordNode thisChildNode = thisNode.getChild(letter);
            WordNode otherChildNode = otherNode.getChild(letter);

            // If both are null then just continue the loop.
            if(thisChildNode == null && otherChildNode == null) continue;

            // Check to make sure Obj - Null / Null - Obj doesn't happen
            if(thisChildNode == null && otherChildNode != null) return false;
            if(thisChildNode != null && otherChildNode == null) return false;

            // Check to see if the children nodes have matching values.
            if(thisChildNode.getValue() != otherChildNode.getValue()) return false;

            // Send down the line
            recursiveCompare(thisChildNode, otherChildNode);
        }

        // Worst case, return true.
        return true;
    }
}