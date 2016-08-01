package developer.pardeep.workin;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pardeep on 22-07-2016.
 */
public class UploadToServer extends IntentService {

    /*private String filePath;*/
    private String imagePath;
    private String fileType;
    private String notes;

    Handler handler;
    private static ArrayList<UploadContent> listUpload=null;
    public UploadToServer(){
        super("UploadToServer");

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("Intent services");
        System.out.println(listUpload.size());
        while (listUpload.size()>0){
            int i=0;
            imagePath = listUpload.get(i).getImagePath();
            fileType = listUpload.get(i).getFileType();
            notes = listUpload.get(i).getFileNotes();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("upload :");
                    System.out.println(imagePath);
                    System.out.println(fileType);
                    System.out.println(notes);
                    Toast.makeText(UploadToServer.this, "upload: " + imagePath + "\n" + fileType + "\n" + notes, Toast.LENGTH_SHORT).show();
                }
            });

            boolean isFileUpload=doFileUpload(imagePath,fileType,notes);
            if(isFileUpload){
                listUpload.remove(i);
                System.out.println(listUpload.size());
            }
        }

    }

    public static void setListUpload(ArrayList<UploadContent> listUpload) {
        System.out.println("call");
        UploadToServer.listUpload = listUpload;
    }

    private boolean doFileUpload(String filePath, String fileType, String notes) {

        int serverResponseCode = 0;

        String fileName = filePath;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(filePath);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :");
            return false;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                File file=new File(filePath);
                String fileName1=file.getName();

                FileInputStream fileInputStream = new FileInputStream(sourceFile);
               // URL url = new URL("http://xdeveloper.royalwebhosting.net/fileupload.php");
                URL url=new URL("http://aceapis.gear.host/upload");
               // URL url=new URL("http://api.medimojo.in/photos/upload/");
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"photos\";filename=\""
                                + fileName1 + "" + lineEnd);

                        dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    System.out.println("File Upload Completed.");

                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();

                handler.post(new Runnable() {
                    public void run() {
                        //System.out.println("Got Exception : see logcat ");
                        Toast.makeText(UploadToServer.this, "Unable to upload File! Check Network connection",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.i("FileUpload", "FileUpload:RES Message: " + line);
                }
                rd.close();
            } catch (IOException ioex) {
                Log.e("FileUpload", "error: " + ioex.getMessage(), ioex);
            }
        }
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler=new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;

    }
}
