package erik.boggle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author erik
 */
public class LetterNode {
    private final Map<Character, LetterNode> children = new HashMap<Character, LetterNode>();
    private Character letter;
    private LetterNode parent;

    public LetterNode() {
        letter = null;
        parent = null;
    }

    private LetterNode(LetterNode parent, String word) {
        this.parent = parent;
        if(".".equals(word)) {
            letter = '.';
        } else {
            this.letter = word.charAt(0);
            insert(cut(word));
        }
    }

    public void insert(String word) {
        char letter = word.charAt(0);
        if(children.containsKey(letter)) {
            children.get(letter).insert(cut(word));
        } else {
            children.put(letter, new LetterNode(this, word));
        }
    }

    private String cut(String word) {
        return word.length() == 1 ? "." : word.substring(1, word.length());
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    public LetterNode getChild(char c) {
        return children.get(c);
    }

    public char getLetter() {
        return letter;
    }

    public LetterNode getParent() {
        return parent;
    }
}
