package developer.pardeep.workin;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pardeep on 29-06-2016.
 */
public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.CustomHolderView> {

    private static String[] list={"Story 1","Story 2","Story 3","Story 4","Story5"};

    Context context;
    CategorySelectionUnderRecyclerViewAdapter categorySelectionUnderRecyclerViewAdapter=new CategorySelectionUnderRecyclerViewAdapter();
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);


    //ArrayAdapter<String> arrayAdapter;

    public CategorySelectionAdapter(Context context){
        this.context=context;
        //arrayAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1,list);

    }

    @Override
    public CustomHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_selection_card_layout,parent,false);
        CustomHolderView customHolderView=new CustomHolderView(view);
        return customHolderView;
    }

    @Override
    public void onBindViewHolder(CustomHolderView holder, int position) {
       //holder.listView.setAdapter(arrayAdapter);
        //holder.listView.setAdapter(arrayAdapter);
        holder.recyclerView.setAdapter(categorySelectionUnderRecyclerViewAdapter);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        onSwipe(holder);
    }

    private void onSwipe(final CustomHolderView holder) {
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT){
                    int position=viewHolder.getAdapterPosition();
                    holder.recyclerViewAdapter.notifyItemRemoved(position);

                }

            }
        });
        itemTouchHelper.attachToRecyclerView(holder.recyclerView);
    }


    @Override
    public int getItemCount() {
        return 1;
    }
    public class CustomHolderView extends RecyclerView.ViewHolder{
        //ListView listView;
        RecyclerView recyclerView;
        RecyclerView.Adapter recyclerViewAdapter;
        RecyclerView.LayoutManager recyclerViewLayoutManager;
        public CustomHolderView(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.listViewCategorySelection);


           // listView=(ListView)itemView.findViewById(R.id.listViewCategorySelection);
        }

        private void onSwipe() {

                ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT |ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        if(direction==ItemTouchHelper.RIGHT){
                            int position=viewHolder.getAdapterPosition();
                            recyclerViewAdapter.notifyItemRemoved(position);

                        }

                    }
                });
                itemTouchHelper.attachToRecyclerView(recyclerView);

        }
    }


}
