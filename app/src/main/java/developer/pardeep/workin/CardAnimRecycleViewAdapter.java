package developer.pardeep.workin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by pardeep on 27-06-2016.
 */
public class CardAnimRecycleViewAdapter extends RecyclerView.Adapter<CardAnimRecycleViewAdapter.CustomViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View v) {

    }



    static List<HospitalsData> list=Collections.emptyList();
    Context context;

    public CardAnimRecycleViewAdapter(List<HospitalsData> list, Context context) {
        this.list = list;
        this.context=context;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
         TextView textViewTitle,textViewDescription;

        public CustomViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView)itemView.findViewById(R.id.imageViewHospital);
            textViewTitle = (TextView)itemView.findViewById(R.id.textViewHospital);
            textViewDescription = (TextView)itemView.findViewById(R.id.textViewHospitalDescription);
        }
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayoutcontent,parent,false);
        view.setOnClickListener(this);
        CustomViewHolder customViewHolder=new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.textViewTitle.setText(list.get(position).hospitalName);
        holder.imageView.setImageResource(list.get(position).hospitalImage);
        holder.textViewDescription.setText(list.get(position).hospitalDescription);
        animateRecycerView(holder);
    }

    private void animateRecycerView(CustomViewHolder holder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.animation_intr);
        holder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    public void insert(int position,HospitalsData hospitalsData){
        list.add(position,hospitalsData);
        notifyItemInserted(position);
    }
    public void remove(HospitalsData hospitalsData){
        int position=list.indexOf(hospitalsData);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
