package developer.pardeep.workin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ViewFiles extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyvlerViewLayout;
    ViewFileAdapter viewFileAdapter;
    ProgressBar progressBarView;

    LinearLayoutManager linearLayoutManager;
    private static int totalFiles=0;
    private static int indexFiles=0;



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
        //ViewFileAdapter.setViewFileContentArrayList(arrayList);
        recyclerViewAdapter=viewFileAdapter;
        recyvlerViewLayout=linearLayoutManager;
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recyvlerViewLayout);


        sendRequestToServer();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
               // onLoadMoreItems();
                showFileFromArrayList(indexFiles);
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

        progressBarView=(ProgressBar)findViewById(R.id.progressBarVieFiles);
        progressBarView.setVisibility(View.VISIBLE);

        /*System.out.println("server request");
        arrayList.add(new ViewFileContent("25-July-2016", "Reports", R.drawable.repo, "report upload on " + DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Bills",R.drawable.bill,"Bills upload on "+ DateFormat.getDateTimeInstance().format(new Date()),"Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Presciption",R.drawable.pres,"Presciption upload on "+ DateFormat.getDateTimeInstance().format(new Date()),"Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016","Discharge summary",R.drawable.disc,"Discharge summary upload on "+ DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        arrayList.add(new ViewFileContent("25-July-2016", "Reports", R.drawable.ic_report, "report upload on " + DateFormat.getDateTimeInstance().format(new Date()), "Tags.."));
        updateRecyclerView();
        System.out.println("update");*/

        StringRequest stringRequest=new StringRequest(Request.Method.GET, ApiUrl.getGetFileViewDefault(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBarView.setVisibility(View.GONE);
                serverResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBarView.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void serverResponse(String s) {
        System.out.println(s);

        try {
            JSONObject jsonObject=new JSONObject(s);
            boolean response=jsonObject.getBoolean("success");
            if(response){
                int totalFilesCount=jsonObject.getInt("totalCount");
                totalFiles=totalFilesCount;
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                int len=jsonArray.length();

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject childObject=jsonArray.getJSONObject(i);
                    String fileUrl=childObject.getString("file_url").toString();
                    String fileType=childObject.getString("file_type").toString();
                    String fileDescription=childObject.getString("json_desc").toString();
                    System.out.println(fileUrl);
                    System.out.println(fileType);
                    System.out.println(fileDescription);
                    addInList(fileType, fileUrl, fileDescription);
                }
                showFileFromArrayList(indexFiles);
            }
            else {
                Toast.makeText(ViewFiles.this, "Error !" + jsonObject.getString("message").toString(), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showFileFromArrayList(int indexFiles) {
        progressBarView=(ProgressBar)findViewById(R.id.progressBarVieFiles);
        progressBarView.setVisibility(View.VISIBLE);
        int totalDown=0;
        int len=arrayList.size();
        System.out.println("List size:"+len);
        try {
            for(int i=indexFiles;i<arrayList.size();i++){
               ViewFileAdapter.viewFileContentArrayList.add(arrayList.get(i));
                updateRecyclerView();
                totalDown++;
                if(totalDown>4){
                    break;
                }
            }

            progressBarView.setVisibility(View.GONE);

        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("No more files to show");
        }
    }

    private void addInList(String fileType, String fileUrl, String fileDescription) {
        File file=new File(fileUrl);
        String fileName=file.getName();
        String fileModifyUrl="https://medimojo.in/admin/uploads/uploads/"+fileName;
        System.out.println("File name :" + fileModifyUrl);
        if(fileDescription==null){
            fileDescription="File Type :"+fileType;
        }
        arrayList.add(new ViewFileContent("01 August", fileType, fileModifyUrl, fileDescription, ""));
    }

    public void updateRecyclerView(){
       recyclerViewAdapter.notifyDataSetChanged();

        //recyclerViewAdapter.notifyItemRangeChanged(0,recyclerViewAdapter.getItemCount());
    }




}
