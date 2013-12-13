package client.modules.spellChecker;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 9/4/13
 * Time: 10:29 AM
 */
public class Spelling implements SpellingCorrector {

    public Dictionary dictionary = new Dictionary();

    @Override
    public void useDictionary(String dictionaryFileName) throws FileNotFoundException {
        // Cycle through BufferReader list

        Scanner reader = new Scanner(new File(dictionaryFileName));

        reader.useDelimiter("(([0-9]))|(\\s+)");

        while (reader.hasNext()) {

            String line  = reader.next();

            // Can we convert this all to regex tho?
            if(line.isEmpty() || (line.length() < 2) || line.matches("(^([^0-9])*[0-9])")) {
                continue;
            }

            dictionary.add(line);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {

        // Check if we can get a non-edited word
        WordNode node = null;
        node = (WordNode)dictionary.find(inputWord);
        if(node != null) return inputWord;

        // Perform Edit Distances
        List<String> similarWordList= new ArrayList<String>();
        String topString = null;

        // Perform Edit Distance One!
        similarWordList.addAll( dictionary.deletionDistance(inputWord)      );
        similarWordList.addAll( dictionary.transpositionDistance(inputWord) );
        similarWordList.addAll( dictionary.alterationDistance(inputWord)    );
        similarWordList.addAll( dictionary.insertionDistance(inputWord)     );


        // Check Edit Distance One!
        Collections.sort(similarWordList);
        topString = getTopNode(similarWordList);
        if(topString != null) return topString;

        // Perform Edit Distance Two!
        // Duplicating to save on like 50 searches. Memory is cheap, right?
        List<String> copiedList = new ArrayList<String>(similarWordList);

        // Reset so don't run the non-existent edit distance 1 words
        similarWordList = new ArrayList<String>();

        similarWordList.addAll( dictionary.deletionDistance(copiedList)      );
        similarWordList.addAll( dictionary.transpositionDistance(copiedList) );
        similarWordList.addAll( dictionary.alterationDistance(copiedList)    );
        similarWordList.addAll( dictionary.insertionDistance(copiedList)     );

        // Check Edit Distance Two!
        Collections.sort(similarWordList);
        topString = getTopNode(similarWordList);
        if(topString != null) return topString;

        // We didn't find anything at all!
        throw new NoSimilarWordFoundException();
    }

    private String getTopNode(List<String> stringList) {

        if(stringList.size() == 0) return null;

        WordNode topNode = null;
        String topString = null;

        for (String word : stringList) {
            WordNode tempNode = (WordNode) dictionary.find(word);

            // Word not fount, skip.
            if(tempNode == null) continue;

            if(topNode == null) {
                topNode = tempNode;
                topString = word;
                continue;
            }

            if(tempNode.getValue() > topNode.getValue()) {
                topNode = tempNode;
                topString = word;
            }

        }

        return topString;
    }

    public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
        /**
         * Create an instance of your corrector here
         */
        Spelling spelling = new Spelling();
        String output;

        try {

            // Attempt to load and run the program
            spelling.useDictionary(args[0]);
            output = spelling.suggestSimilarWord(args[1]);

        } catch (ArrayIndexOutOfBoundsException e) {

            // If there are bad args, bubble a NoSimilarWordFoundException
            throw new NoSimilarWordFoundException();

        }

        // Return the output of suggestSimilarWord
        System.out.println(output);

    }
}