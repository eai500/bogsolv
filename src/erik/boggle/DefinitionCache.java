package erik.boggle;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author erik
 */
public class DefinitionCache {
    private static final ConcurrentHashMap<String, String> definitions = new ConcurrentHashMap<String, String>();

    public static void setDefinition(String word, String definition) {
        definitions.put(word, definition);
    }

    public static String getDefinition(String word) {
        return definitions.get(word);
    }
}
