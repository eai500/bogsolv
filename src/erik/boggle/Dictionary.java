package erik.boggle;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author erik
 */
public class Dictionary {
    private static Dictionary instance;
    private static final LetterNode root = new LetterNode();

    private Dictionary(InputStream words) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(words));
        String line;

        try{
            while((line = reader.readLine()) != null) {
                root.insert(line);
            }
        } catch (Exception ignore){

        }
    }

    public static Dictionary getInstance(Context context) {
        if(instance == null) {
            try {
                instance = new Dictionary(context.getAssets().open("words.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public LetterNode getRoot() {
        return root;
    }
}
