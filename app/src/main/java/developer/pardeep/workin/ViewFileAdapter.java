package developer.pardeep.workin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pardeep on 25-07-2016.
 */
public class ViewFileAdapter extends RecyclerView.Adapter<ViewFileAdapter.CustomFileHolder> {

    public static ArrayList<ViewFileContent> viewFileContentArrayList=new ArrayList<ViewFileContent>();
    Context context;

    public ViewFileAdapter(Context applicationContext) {
        context=applicationContext;

    }

    @Override
    public CustomFileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewfiles_cardview,parent,false);
        CustomFileHolder customFileHolder=new CustomFileHolder(view);

        return customFileHolder;
    }

    @Override
    public void onBindViewHolder(CustomFileHolder holder, int position) {
        holder.fileImageView.setImageResource(viewFileContentArrayList.get(position).getImageData());
        holder.fileTypeTextView.setText(viewFileContentArrayList.get(position).getFileType());
        holder.dateTextView.setText(viewFileContentArrayList.get(position).getDateOfFile());
        holder.notesTextView.setText(viewFileContentArrayList.get(position).getNotesOfFile());
        holder.tagsTextView.setText(viewFileContentArrayList.get(position).getTagsOfFile());
    }

    @Override
    public int getItemCount() {
        return viewFileContentArrayList.size();
    }

    public class CustomFileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateTextView,fileTypeTextView,notesTextView,tagsTextView;

        Button dialogButton,tagsButton,buttonClickFile;
        ImageView fileImageView;

        public CustomFileHolder(View itemView) {
            super(itemView);
            dateTextView=(TextView)itemView.findViewById(R.id.textViewDateOfFile);
            fileTypeTextView=(TextView)itemView.findViewById(R.id.textViewFileCategory);
            notesTextView=(TextView)itemView.findViewById(R.id.textViewNotes);
            tagsTextView=(TextView)itemView.findViewById(R.id.textViewTags);
            fileImageView=(ImageView)itemView.findViewById(R.id.imageViewFile);
            buttonClickFile=(Button)itemView.findViewById(R.id.buttonToFileView);
            buttonClickFile.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v==buttonClickFile){
                Toast.makeText(context, "Image Click", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void setViewFileContentArrayList(ArrayList<ViewFileContent> viewFileContentArrayList) {
        ViewFileAdapter.viewFileContentArrayList = viewFileContentArrayList;
    }
}
