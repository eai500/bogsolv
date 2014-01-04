package erik.boggle.listener;

import android.text.Editable;
import android.text.InputType;
import android.text.method.BaseKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * @author erik
 */
public class BackspaceListener extends BaseKeyListener {
    private EditText previousLetter;

    public BackspaceListener(EditText previousLetter) {
        this.previousLetter = previousLetter;
    }

    @Override
    public int getInputType() {
        return InputType.TYPE_CLASS_TEXT;
    }

    @Override
    public boolean backspace(View view, Editable content, int keyCode, KeyEvent event) {
        boolean deleted = super.backspace(view, content, keyCode, event);
        if(!deleted && previousLetter != null) {
            previousLetter.setText("");
            previousLetter.requestFocus();
        }
        return deleted;
    }
}
