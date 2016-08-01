package developer.pardeep.workin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewFiles extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyvlerViewLayout;
    ViewFileAdapter viewFileAdapter;


    LinearLayoutManager linearLayoutManager;

    ArrayList<ViewFileContent> arrayList=new ArrayList<ViewFileContent>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewFiles);

        viewFileAdapter=new ViewFileAdapter(getApplicationContext());
        ViewFileAdapter.setViewFileContentArrayList(arrayList);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        ViewFileAdapter.setViewFileContentArrayList(arrayList);
        recyclerViewAdapter=viewFileAdapter;
        recyvlerViewLayout=linearLayoutManager;
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recyvlerViewLayout);


        sendRequestToServer();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                onLoadMoreItems();
            }
        });


    }

    private void onLoadMoreItems() {
        sendRequestToServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_files, menu);
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



    private void sendRequestToServer() {
        System.out.println("server request");
        arrayList.add(new ViewFileContent("25-July-2016", "Reports", R.drawable.repo, "report upload on " + DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Bills",R.drawable.bill,"Bills upload on "+ DateFormat.getDateTimeInstance().format(new Date()),"Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Presciption",R.drawable.pres,"Presciption upload on "+ DateFormat.getDateTimeInstance().format(new Date()),"Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Discharge summary",R.drawable.disc,"Discharge summary upload on "+ DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016", "Reports", R.drawable.ic_report, "report upload on " + DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        updateRecyclerView();
        System.out.println("update");
    }
    public void updateRecyclerView(){
        recyclerViewAdapter.notifyDataSetChanged();

    }



}
