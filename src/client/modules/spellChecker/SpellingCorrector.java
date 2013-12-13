package client.modules.spellChecker;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 9/4/13
 * Time: 10:11 AM
 */
public interface SpellingCorrector {

    public static class NoSimilarWordFoundException extends Exception {
    }

    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @param dictionaryFileName File containing the words to be used
     * @throws java.io.IOException If the file cannot be read
     */
    public void useDictionary(String dictionaryFileName) throws IOException;

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion
     * @throws NoSimilarWordFoundException If no similar word is in the dictionary
     */
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException;

}