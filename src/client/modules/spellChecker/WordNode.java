package client.modules.spellChecker;


/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 9/4/13
 * Time: 10:17 AM
 */
public class WordNode implements Trie.Node {

    public WordNode[] children = new WordNode[26];

    public int frequency = 0;

    public int childCount = 0;

    private int getIndexOf(char letter) {
        // Figure the index
        return letter - 'a';
    }

    public WordNode getChild(char letter) {
        // Get Index
        int index = getIndexOf(letter);
        // Little bit of sanity check
        if(index > 26 || index < 0) return null;
        // Return the new WordNode
        return children[index];
    }

    public void putChild(char letter, WordNode node) throws Exception{
        // Get Index
        int index = getIndexOf(letter);
        // Set Node
        try {
            children[index] = node;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR HERE: ~~~~~~~~~~~~~~~~~~~");
            System.out.println(index + " : " + "\"" + letter + "\"");

            throw new Exception();
        }
        // Increment counter
        childCount++;
    }

    public int getChildrenCount() {
        return this.childCount;
    }

    public WordNode[] getChildren() {
        return this.children;
    }

    public int getValue() {
        return frequency;
    }

}
