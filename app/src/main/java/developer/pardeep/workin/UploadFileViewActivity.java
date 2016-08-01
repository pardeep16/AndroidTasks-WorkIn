package developer.pardeep.workin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class UploadFileViewActivity extends AppCompatActivity implements View.OnClickListener {

    Button uploadButtonGallery,uploadCameraButton;
    private static int reqCodeFromGallery=1;
    private static int reqCodeFromCamera=2;
    private static String imageAbsolutePath="";
    private static int fileCount=0;
    FloatingActionButton floatingUploadButton;

    FloatingActionButton uploadFloatingButton;

    LinearLayout parentLayout;
    TextView textView;

    /*
    Image upload content arraylist
     */

    ArrayList<UploadContent> uploadList=new ArrayList<>();


    /*
    array to store imagePath temporarily
     */
    private static String[] imagePaths=new String[10];



    ArrayList<View> viewList=new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file_view);
        uploadButtonGallery=(Button)findViewById(R.id.galleryButton);
        uploadCameraButton=(Button)findViewById(R.id.captureButton);
        
        uploadButtonGallery.setOnClickListener(this);
        uploadCameraButton.setOnClickListener(this);
        parentLayout=(LinearLayout)findViewById(R.id.linearLayoutUploadFile);
        textView=(TextView)findViewById(R.id.textViewNoFileSelect);


        floatingUploadButton=(FloatingActionButton)findViewById(R.id.floatingButtonUpload);

        /*
        upload floating Button
         */
        uploadFloatingButton=(FloatingActionButton)findViewById(R.id.floatingButtonUpload);
        uploadFloatingButton.setOnClickListener(this);

        if(fileCount==0){
            floatingUploadButton.setVisibility(View.GONE);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_file_view, menu);
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
        
        if(v==uploadButtonGallery){
            chooseFromGallery();
        }
        else if(v==uploadCameraButton){
            captureImage();
        }
        else if(v==uploadFloatingButton){
            boolean verify=verifyUploadData();
            String msg="";
            if(verify){
                /*AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle("Upload data");
                for(int i=0;i<uploadList.size();i++){
                    System.out.println("Image patth :"+uploadList.get(i).getImagePath());
                    System.out.println("File type :"+uploadList.get(i).getFileType());
                    System.out.println("Notes :" + uploadList.get(i).getFileNotes());
                    msg.concat("\n" + uploadList.get(i).getImagePath());
                    alertDialog.setMessage(msg);
                }
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();*/
                System.out.println("Total list size :"+uploadList.size());
                for(int i=0;i<uploadList.size();i++){
                    System.out.println("Image patth :"+uploadList.get(i).getImagePath());
                    System.out.println("File type :"+uploadList.get(i).getFileType());
                    System.out.println("Notes :" + uploadList.get(i).getFileNotes());
                    /*msg.concat("\n" + uploadList.get(i).getImagePath());*/
                }
                UploadToServer uploadToServer=new UploadToServer();
                uploadToServer.setListUpload(uploadList);
                System.out.println("upl");
                Intent intentService=new Intent(UploadFileViewActivity.this,UploadToServer.class);
                startService(intentService);
                System.out.println("down");
            }
        }
        
    }

    private boolean verifyUploadData() {
        boolean flag=false;
        for(int i=0;i<viewList.size();i++){
            View view=viewList.get(i);
            RadioGroup radioGroup=(RadioGroup)view.findViewById(R.id.radioGroupFileType);
            int selectId=-1;
            selectId=radioGroup.getCheckedRadioButtonId();
            if(selectId<0){
                Toast.makeText(UploadFileViewActivity.this, "Please Select file type", Toast.LENGTH_SHORT).show();
                flag=false;
                return flag;
            }
            else {
                RadioButton radioButton=(RadioButton)view.findViewById(selectId);
                EditText editText=(EditText)view.findViewById(R.id.editTextComment);
                ImageView imageView=(ImageView)view.findViewById(R.id.imageViewUpload);
                TextView textpath=(TextView)view.findViewById(R.id.imageTag);
               /* Uri getimagePath=Uri.parse(""+imageView);*/

                String path=textpath.getText().toString();
                String fileType=radioButton.getText().toString();
                String notes=editText.getText().toString();
                if(notes.equalsIgnoreCase("")){
                    notes="image upload on :"+ new Date().getTime();
                }
                uploadList.add(new UploadContent(path,fileType,notes));
                flag=true;
            }

        }
        return flag;

    }

    private void captureImage() {
        if(fileCount>9){
            Toast.makeText(UploadFileViewActivity.this, "File selected exceed limit!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent imageCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(imageCapture, reqCodeFromCamera);
        }
    }

    private void chooseFromGallery() {
        if(fileCount>9){
            Toast.makeText(UploadFileViewActivity.this, "File selected exceed limit!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, reqCodeFromGallery);
        }
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==reqCodeFromGallery && data!=null){
            Uri imageData=data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};

            //Get cursor
            Cursor cursor=getContentResolver().query(imageData, filePathColumn, null, null, null);
            // move the cursor to first row
            cursor.moveToFirst();

            //get Column Index
            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            imageAbsolutePath=cursor.getString(columnIndex);
            
            if(imageAbsolutePath!=null){
                setImageInLinearLayout(imageAbsolutePath);
            }
        }
        else if(requestCode==reqCodeFromCamera && data!=null){
            Uri imageData=data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};

            //Get cursor
            Cursor cursor=getContentResolver().query(imageData, filePathColumn, null, null, null);
            // move the cursor to first row
            cursor.moveToFirst();

            //get Column Index
            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            imageAbsolutePath=cursor.getString(columnIndex);

            if(imageAbsolutePath!=null){
                setImageInLinearLayout(imageAbsolutePath);
            }
        }
    }

    private void setImageInLinearLayout(String imageAbsolutePath) {



        final View view;
        textView.setText("");
        textView.setVisibility(View.GONE);
        floatingUploadButton.setVisibility(View.VISIBLE);

        LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
        view=inflater.inflate(R.layout.imageview_upload,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.imageViewUpload);

        final TextView textViewImage=(TextView)view.findViewById(R.id.imageName);
        TextView textPath=(TextView)view.findViewById(R.id.imageTag);
        textPath.setText(imageAbsolutePath);
        textPath.setVisibility(View.GONE);

        Button deleteButton=(Button)view.findViewById(R.id.buttonForDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                parentLayout.removeView(view);
                fileCount--;
                //textViewImage.setText("File Upload :"+fileCount);
                if (fileCount == 0) {
                    textView.setVisibility(View.VISIBLE);
                    floatingUploadButton.setVisibility(View.GONE);
                    textView.setText("No file is choosen yet!");
                }
            }
        });
        File file=new File(imageAbsolutePath);
        String getName=file.getName();
        fileCount++;
        textViewImage.setText("Image :" + getName.trim());

        Bitmap bitmap= BitmapFactory.decodeFile(imageAbsolutePath);
        imageView.setImageBitmap(bitmap);

        viewList.add(view);
       /* imagePaths[fileCount]=imageAbsolutePath;*/

        parentLayout.addView(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fileCount=0;
    }
}
