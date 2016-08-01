package developer.pardeep.workin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class LocationHistoryActivity extends AppCompatActivity {

    ListView listView;
    public static CharSequence[] showList={"Share"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.listViewHistory);
       /* String[] arrayList=new String[10];
        if(HistoryContennt.listItemCount>0){
            for (int i=0;i< HistoryContennt.listItemCount;i++){
                arrayList[i]=HistoryContennt.addressList[i];
            }
        }*/

        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,arrayList);
        HistoryAdapter historyAdapter=new HistoryAdapter();
        listView.setAdapter(historyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationHistoryActivity.this, "Item click", Toast.LENGTH_SHORT).show();
                String msg=HistoryLocationContent.addressList[position];
                showDialogToShare(msg);
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationHistoryActivity.this, "Item click", Toast.LENGTH_SHORT).show();
                String msg=HistoryLocationContent.addressList[position];
                showDialogToShare(msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDialogToShare(final String msg) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setItems(showList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                onItemClick(position,msg);
            }
        });
        alertDialog.setTitle("choose Action");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogShow=alertDialog.create();
        dialogShow.show();
    }

    private void onItemClick(int position, String msg) {
       switch (position){
           case 0:
               Intent intent=new Intent();
               intent.setAction(Intent.ACTION_SEND);
               intent.putExtra(Intent.EXTRA_TEXT, "Hii this is my location :" + "\n" + msg);
               intent.setType("text/plain");
               startActivity(intent);
              // startActivity(Intent.createChooser(intent,getResources().getText(R.string.send_to)));

       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_history, menu);
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
}
