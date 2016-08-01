package developer.pardeep.workin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyleViewAdapter;
    RecyclerView.LayoutManager recycleViewLayout;
    ListView listView;
    HomeRecycleViewAdapter homeRecycleViewAdapter;
    ImageButton refreshImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=(RecyclerView)findViewById(R.id.recycleViewHomeScreen);
        refreshImageButton=(ImageButton)findViewById(R.id.refreshButton);

        refreshImageButton.setOnClickListener(this);
        refreshImageButton.setVisibility(View.GONE);

        //Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("WorkIn");

        recyleViewAdapter=new HomeRecycleViewAdapter();
        homeRecycleViewAdapter=new HomeRecycleViewAdapter();
        recyleViewAdapter=homeRecycleViewAdapter;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        recycleViewLayout=linearLayoutManager;

        recyclerView.setLayoutManager(recycleViewLayout);


  /*  RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
      itemAnimator.setAddDuration(1000);
       itemAnimator.setRemoveDuration(1000);
       ecyclerView.setItemAnimator(itemAnimator);*/

        // for fixed size list
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyleViewAdapter);

        onSwipeLayout();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        String string=homeRecycleViewAdapter.arrayList.get(position);
                        switch (string) {
                            case "Broadcast Receivers and IntentServices":
                                //Toast.makeText(HomeActivity.this, "Item Click", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(HomeActivity.this,BroadcastReceiverActivity.class));
                                break;
                            case "Facebook Login":
                                startActivity(new Intent(HomeActivity.this,FacebookLoginActivity.class));
                                break;
                            case "Google Login":
                                startActivity(new Intent(HomeActivity.this,GoogleLogIn.class));
                                break;
                            case "Location(Google Fused Location API)":
                                startActivity(new Intent(HomeActivity.this,LocationFindActivity.class));
                                break;
                            case "Android Graphs":
                                startActivity(new Intent(HomeActivity.this,AndroidGraphs.class));
                                break;
                            case "RecycleView Card Animations":
                                startActivity(new Intent(HomeActivity.this,CardAnimationActivity.class));
                                break;
                            case "RecyclerView with CardLayout and ListView":
                                startActivity(new Intent(HomeActivity.this,CategoriesSelectionRecyclerView.class));
                                break;
                            case "Pacer POC":
                                startActivity(new Intent(HomeActivity.this,PacerActiviy.class));
                                break;
                            case "Firebase Implementation":
                                startActivity(new Intent(HomeActivity.this,FirebaseActivity.class));
                                break;
                            case "Image Upload":
                                startActivity(new Intent(HomeActivity.this,ImageUpload.class));
                                break;
                            case "Medimojo POC":
                                startActivity(new Intent(HomeActivity.this,MedimoSample.class));
                                break;
                            case "Upload POC":
                                startActivity(new Intent(HomeActivity.this,UploadFiles.class));
                                break;
                            case "Download POC":
                                startActivity(new Intent(HomeActivity.this,ViewFiles.class));
                                break;
                            default:
                                Toast.makeText(HomeActivity.this, "default click", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        );;

        //refresh Button for  recyclerview list



    }

   private void onSwipeLayout() {
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT ,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               // System.out.println("****Item swipe*****");
                int position=viewHolder.getAdapterPosition();
                if( direction==ItemTouchHelper.RIGHT){
                    homeRecycleViewAdapter.arrayList.remove(viewHolder.getAdapterPosition());
                    homeRecycleViewAdapter.descriptionArrayList.remove(viewHolder.getAdapterPosition());
                    recyleViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    homeRecycleViewAdapter.countItems-=1;
                    if(homeRecycleViewAdapter.countItems==0){
                        refreshImageButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
      itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
       if(v==refreshImageButton){
           refreshImageButton.setVisibility(View.GONE);
           /*homeRecycleViewAdapter.countItems=RecycleViewHomeContent.listContent.length;
           recyleViewAdapter=new HomeRecycleViewAdapter();
           recycleViewLayout=new LinearLayoutManager(this);
           recyclerView.setAdapter(recyleViewAdapter);
           onSwipeLayout();*/
           startActivity(new Intent(HomeActivity.this,HomeActivity.class));
       }
    }


}
