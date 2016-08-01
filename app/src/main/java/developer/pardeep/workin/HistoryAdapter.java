package developer.pardeep.workin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pardeep on 15-06-2016.
 */
public class HistoryAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return HistoryLocationContent.listItemCount;
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        view=convertView;
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater.from(parent.getContext()));
            view=layoutInflater.inflate(R.layout.historylistview,parent,false);
        }
        TextView textView=(TextView)view.findViewById(R.id.textViewHistoryListView);
        textView.setText(HistoryLocationContent.addressList[position]);

        return view;
    }
}
