package erik.boggle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author erik
 */
public class SolutionsAdapter extends BaseAdapter {
    private Context context;
    private List<WordListItem> wordItems;

    public SolutionsAdapter(Context context, List<WordListItem> wordItems) {
        this.context = context;
        this.wordItems = wordItems;
    }

    public int getCount() {
        return wordItems.size();
    }

    public Object getItem(int position) {
        return wordItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        WordListItem entry = wordItems.get(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.word_row_2, null);

            holder = new ViewHolder();
            holder.wordView = (TextView)convertView.findViewById(R.id.wordRowWord);
            holder.definitionView = (TextView)convertView.findViewById(R.id.wordRowDefinition);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.wordView.setText(entry.getWord());
        holder.definitionView.setText(getDefinitionString(entry.getDefinitions()));

        return convertView;
    }

    private String getDefinitionString(List<String> definitions) {
        StringBuilder builder = new StringBuilder();
        if(definitions != null) {
            for(String def : definitions) {
                builder.append(def).append("\n");
            }
        }
        return builder.toString();
    }

    static class ViewHolder {
        TextView wordView;
        TextView definitionView;
    }
}
