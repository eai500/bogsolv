package erik.boggle;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import erik.boggle.util.SolvedWords;
import erik.boggle.ws.DumbDefinitionFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author erik
 */
public class Solutions extends ListActivity {
    private ArrayList<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWords(SolvedWords.getSolvedWords());
         setListAdapter(new SolutionsAdapter(this, getItems(words)));
    }

    public void setWords(Collection<String> words) {
        this.words = words != null ? new ArrayList<String>(words) : new ArrayList<String>();
    }

    private List<WordListItem> getItems(Collection<String> words) {
        List<WordListItem> items = new ArrayList<WordListItem>();
        for(String word : words) {
            items.add(new WordListItem(word, null, null));
        }
        return items;
    }

    private List<String> addPoints(List<String> words) {
        List<String> wordsAndPoints = new ArrayList<String>();
        for(String word : words) {
            wordsAndPoints.add(word + " - " + calculatePoints(word));
        }
        return wordsAndPoints;
    }

    private int calculatePoints(String word) {
        int len = word.length();
        if(len >= 8) return 11;
        if(len == 7 ) return 4;
        if(len == 6) return 3;
        if(len == 5) return 2;
        if(len == 3 || len == 4) return 1;

        return -1;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        WordListItem item =((WordListItem)l.getAdapter().getItem(position));
        if(item.getDefinitions() != null) {
            item.setDefinitions(null);
            ((SolutionsAdapter)l.getAdapter()).notifyDataSetChanged();
        } else {
            ProgressBar progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
            String word = ((WordListItem)l.getAdapter().getItem(position)).getWord();
            new DefinitionSetter(position, word, progressBar).execute();
        }
    }

    private void setDefinition(int position, String definition) {
        ((WordListItem)getListAdapter().getItem(position)).setDefinitions(Arrays.asList(definition));
        ((SolutionsAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class DefinitionSetter extends AsyncTask<String, String, String> {
        private final int position;
        private final String word;
        private final ProgressBar progressBar;

        DefinitionSetter(int position, String word, ProgressBar progressBar) {
            this.position = position;
            this.word = word;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... objects) {
            String definition = DefinitionCache.getDefinition(word);
            if(definition == null) {
                definition = new DumbDefinitionFinder().getDefinition(word);
                DefinitionCache.setDefinition(word, definition);
            }
            return definition;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            setDefinition(position, result);
        }
    }
}


