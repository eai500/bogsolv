package erik.boggle.listener;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author erik
 */
public class BoggleTextChangeListener implements TextWatcher {
    private final EditText letter;
    private final EditText nextLetter;
    private InputMethodManager inputManager;

    public BoggleTextChangeListener(EditText letter, EditText nextLetter, InputMethodManager inputManager) {
        this.nextLetter = nextLetter;
        this.letter = letter;
        this.inputManager = inputManager;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String input = editable.toString();
        if(input.length() == 1 && !isUppercaseAndOneChar(input)) {
            String uppercased = input.toUpperCase();
            if(uppercased.equals("Q")) {
                editable.replace(0,1,"Qu");
            } else {
                editable.replace(0, 1, uppercased);
            }
            new NextLater().execute(nextLetter);
        }
    }

    private boolean isUppercaseAndOneChar(String s) {
        return s.length() == 1 && s.charAt(0) == s.toUpperCase().charAt(0);
    }

    private void next() {
        if(nextLetter != null) {
            nextLetter.requestFocus();
        } else {
            inputManager.hideSoftInputFromWindow(letter.getWindowToken(), 0);
        }
    }

    //todo -remove this hack
    private class NextLater extends AsyncTask<EditText, Void, Void> {
        @Override
        protected Void doInBackground(EditText... editTexts) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            next();
        }
    }
}
