package developer.pardeep.workin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pardeep on 16-06-2016.
 */
public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.CustomHolder> implements View.OnClickListener {

   public  ArrayList<String> arrayList=new ArrayList<String>();
    public ArrayList<String> descriptionArrayList=new ArrayList<String>();

    public  int countItems=RecycleViewHomeContent.listContent.length;
    public HomeRecycleViewAdapter(){
        for (int i=0;i<RecycleViewHomeContent.listContent.length;i++){
            arrayList.add(RecycleViewHomeContent.listContent[i]);
            descriptionArrayList.add(RecycleViewHomeContent.listDescription[i]);
        }
    }

    @Override
    public void onClick(View v) {
    }

    public static class CustomHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView contentTextView,descriptionTextView;
        public CustomHolder(View view){
            super(view);
           // titleTextView=(TextView)view.findViewById(R.id.textView_upper_CardView);
            contentTextView=(TextView)view.findViewById(R.id.textView_lower_CardView);
            descriptionTextView=(TextView)view.findViewById(R.id.textView_description_card);
        }

    }
    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_card_layout,parent,false);
        view.setOnClickListener(this);
        CustomHolder customHolder=new CustomHolder(view);
        return customHolder;
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
       // holder.titleTextView.setText(RecycleViewHomeContent.listContent[position]);
        holder.contentTextView.setText(arrayList.get(position));
        holder.descriptionTextView.setText(descriptionArrayList.get(position));
    }



    @Override
    public int getItemCount() {
        return countItems;
    }
}
