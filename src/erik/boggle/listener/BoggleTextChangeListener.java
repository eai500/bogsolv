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
    private String previousValue = "blah";

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
        if(!editable.toString().equals(previousValue) && editable.toString().length() > 0) {
            if(editable.toString().equalsIgnoreCase("Q")) {
                inputManager.hideSoftInputFromWindow(letter.getWindowToken(), 0);
                editable.replace(0,1,"Qu");

            }
            previousValue = editable.toString();
            next();
        }
    }

    private void next() {
        if(nextLetter != null) {
            nextLetter.requestFocus();
        } else {
            inputManager.hideSoftInputFromWindow(letter.getWindowToken(), 0);
        }
    }
}
