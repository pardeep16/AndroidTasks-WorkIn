package developer.pardeep.workin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pardeep on 29-06-2016.
 */
public class CategorySelectionUnderRecyclerViewAdapter extends RecyclerView.Adapter<CategorySelectionUnderRecyclerViewAdapter.CustomHolder> {
    private static String[] list={"Story 1","Story 2","Story 3","Story 4","Story5"};


    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_selection_under_recyclerview,parent,false);
        CustomHolder customHolder=new CustomHolder(view);
        return customHolder;
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        holder.textView.setText(list[position]);
    }



    @Override
    public int getItemCount() {
        return list.length;
    }

    public class CustomHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textViewUnderRecyclerView);
        }
    }
}
