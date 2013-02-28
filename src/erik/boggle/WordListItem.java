package erik.boggle;

import java.util.List;

/**
 * @author erik
 */
public class WordListItem {
    private String word;
    private String type;
    private List<String> definitions;

    public WordListItem(String word, String type, List<String> definitions) {
        this.word = word;
        this.type = type;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }
}
