package client.modules.spellChecker;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/12/13
 * Time: 2:52 PM
 */
public class KnownData implements Serializable {

    private List<String> words;

    public KnownData(ArrayList<String> knownDataValue) {
        this.words = knownDataValue;
    }

    public KnownData() {
        this.words = new ArrayList<>();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    private static String downloadValues(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            URL url = new URL(path);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String temp;
            while ((temp = in.readLine()) != null) {
                stringBuilder.append(temp);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static KnownData getList(String knownDataFullPath) {
        ArrayList<String> knownDataValue = null;

        String knownValuesString = downloadValues(knownDataFullPath);
        String[] values = knownValuesString.split(",");

        knownDataValue = new ArrayList<>(Arrays.asList(values));

        return new KnownData(knownDataValue);
    }
}
