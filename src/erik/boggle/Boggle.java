package erik.boggle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import erik.boggle.listener.BoggleTextChangeListener;
import erik.boggle.util.SolvedWords;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Boggle extends Activity {
    Map<Integer, EditText> letters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loadDictionary();
        loadLetters();
        findViewById(R.id.solveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateBoard()) {
                    SolvedWords.setSolvedWords(getSolvedWords());
                    Intent showSolutions = new Intent(getApplicationContext(), Solutions.class);
                    startActivity(showSolutions);
                }
            }
        });
        findViewById(R.id.clearButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (findViewById(R.id.a1)).requestFocus();
                for(EditText letter : letters.values()) {
                    letter.setText(null);
                }
            }
        });
    }

    private void loadDictionary() {
        new DictTask().execute((Object)null);
    }

    private class DictTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object... objects) {
            Dictionary.getInstance(getApplicationContext());
            return null;
        }
    }

    private Collection<String> getSolvedWords() {
        return new Solver().solve(getBoard(), getApplicationContext());
    }

    private char[][] getBoard() {
        char[][] board = new char[4][4];
        int a1 = R.id.a1;

        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                char c = ((EditText)findViewById(a1++)).getText().toString().toLowerCase().charAt(0);
                board[i][j] = c;
            }
        }
        return board;
    }

    private boolean validateBoard() {
        int a1 = R.id.a1;
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                EditText text = ((EditText)findViewById(a1++));
                if(!validateLetter(text.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateLetter(Editable entry) {
        return entry != null && !"".equals(entry.toString().trim()) && Character.isLetter(entry.charAt(0));
    }

    private void loadLetters() {
        letters = new HashMap<Integer, EditText>();
        int a1 = R.id.a1;
        for(int i = a1; i<a1+16; i++) {
            letters.put(i, loadLetter(i));
        }
    }



    private EditText loadLetter(int id) {
        EditText letter = ((EditText)findViewById(id));
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if(findViewById(id + 1) instanceof EditText) {
            letter.addTextChangedListener(new BoggleTextChangeListener(letter, (EditText)findViewById(id+1), imm));
        } else {
            letter.addTextChangedListener(new BoggleTextChangeListener(letter, null, imm));
        }
        return letter;
    }
}
