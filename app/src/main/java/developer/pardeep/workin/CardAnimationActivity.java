package developer.pardeep.workin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CardAnimationActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayout;
    CardAnimRecycleViewAdapter cardAnimRecycleViewAdapter;
    private static String hspName="";
    private static String hspDesc="";
    private static int itemPosition;
    private Paint paint = new Paint();
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_animation);
        recyclerView=(RecyclerView)findViewById(R.id.recyleViewCardAnimation);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<HospitalsData> customList=fillListData();
       // recyclerViewLayout=new LinearLayoutManager(this);
        cardAnimRecycleViewAdapter=new CardAnimRecycleViewAdapter(customList,getApplicationContext());
        recyclerViewAdapter=cardAnimRecycleViewAdapter;
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        //StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,1);
        recyclerViewLayout=linearLayoutManager;
        recyclerView.setLayoutManager(recyclerViewLayout);

        /*recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                System.out.println(page +"total :"+totalItemsCount);
                customLoadMoreData(page);
            }
        });*/
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                customLoadMoreData();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
      /*  RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);*/

       onSwipeView();

         }

    private void customLoadMoreData() {

        if(!hspName.equalsIgnoreCase("") && !hspDesc.equalsIgnoreCase("")) {
            cardAnimRecycleViewAdapter.insert(itemPosition, new HospitalsData(hspName, R.drawable.hosp_icon, hspDesc));
            hspName="";
            hspDesc="";
        }
    }

    private void onSwipeView() {
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT |ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               if(direction==ItemTouchHelper.RIGHT){
                   int position=viewHolder.getAdapterPosition();
                    hspName=CardAnimRecycleViewAdapter.list.get(position).hospitalName;
                    hspDesc=CardAnimRecycleViewAdapter.list.get(position).hospitalDescription;
                    CardAnimRecycleViewAdapter.list.remove(position);

                    recyclerViewAdapter.notifyItemRemoved(position);

                }
                else {
                   int position=viewHolder.getAdapterPosition();
                    showCustomDialog();


                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

              /*  Bitmap bitmapIcon;
                View itemView=viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;
                if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
                    if(dX>0){


                    }
                    else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        bitmapIcon = BitmapFactory.decodeResource(getResources(), R.drawable.loc);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(bitmapIcon,null,icon_dest,paint);

                    }
                }*/

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
             super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                Bitmap bitmapIcon;
                View itemView=viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;
                if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
                    if(dX>0){


                    }
                    else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        bitmapIcon = BitmapFactory.decodeResource(getResources(), R.drawable.loc);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(bitmapIcon,null,icon_dest,paint);



                    }
                }


            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showCustomDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Action");
        alertDialog.setMessage("Under working...");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                recyclerView.setAdapter(recyclerViewAdapter);

            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }


    private List<HospitalsData> fillListData() {
        List<HospitalsData> list=new ArrayList<>();
        list.add(new HospitalsData("Apollo",R.drawable.hosp_icon,"Apollo Hospitals is India's leading super speciality hospital. Our team of over 5000 doctors give you the best of modern healthcare to ensure you stay healthy."));
        list.add(new HospitalsData("Medanta",R.drawable.hosp_icon,"Medanta is a multi-specialty medical institute located in Gurgaon Ahirwal in the National Capital Region of India. It was established in 2009, with cardiac surgeon, Naresh Trehan."));
        list.add(new HospitalsData("Fortis",R.drawable.hosp_icon,"Fortis has best hospitals in India giving world class treatment of cancer, heart, liver & kidney transplant, EYE, Knee & hip replacement"));
        list.add(new HospitalsData("Max",R.drawable.hosp_icon,"Max Hospital in Delhi is one of the top hospitals in India. It has over 8 hospitals in Delhi with over 1500 physicians/Doctors, 3000 support staff and 800 beds."));
        list.add(new HospitalsData("AIIMS",R.drawable.hosp_icon,"The All India Institutes of Medical Sciences are a group of autonomous public medical colleges of higher education. These institutes have been declared by Act of Parliament as institutions of national importance."));
        list.add(new HospitalsData("PGI",R.drawable.hosp_icon,"The Postgraduate Institute of Medical Education and Research is a medical and research institution located in Chandigarh, India. It has educational, medical research and training facilities for its students."));
        list.add(new HospitalsData("Sir Ganga Ram Hospital",R.drawable.hosp_icon,"Sir Ganga Ram Hospital, Delhi NCR India, Multi speciality hospital, Healthcare service, premier medical hospital, medical research, medical training"));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CardAnimationActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
