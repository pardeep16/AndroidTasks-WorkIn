package developer.pardeep.workin;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageShowFragment extends Fragment {

    private static Bitmap showScaleImageBitmap;
    private static String imagePath;
    View view;
    ImageView imageView,compressImageView;
    PhotoViewAttacher photoViewAttacher;

    public ImageShowFragment() {
        // Required empty public constructor
    }
    public ImageShowFragment(Bitmap scaledImageBitmap, String compressImagePath){
        showScaleImageBitmap=scaledImageBitmap;
        imagePath=compressImagePath;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_image_show, container, false);
        imageView=(ImageView)view.findViewById(R.id.imageViewShowFragment);
        compressImageView=(ImageView)view.findViewById(R.id.imageViewCompressShowFragment);
        if(showScaleImageBitmap!=null){
            imageView.setImageBitmap(showScaleImageBitmap);
        }
        if(imagePath!=null){
            System.out.println("Image :"+imagePath);

            File file=new File(imagePath);
            String fileName=file.getName();
            long len=file.length()/1024;

            System.out.println("Image name is :"+fileName);
            System.out.println("Size of Image is :"+len+"KB");

            Toast.makeText(getActivity(), "Image :"+fileName+"\n"+"Size :"+len, Toast.LENGTH_SHORT).show();

            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            compressImageView.setImageBitmap(bitmap);

           /* try {

//                Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,80,outputStream);
//                compressImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/


        }
        photoViewAttacher=new PhotoViewAttacher(imageView);



        return view;
    }

    public class ImageCompress extends AsyncTask<String,Void,String>{



        public ImageCompress(){

        }

        @Override
        protected String doInBackground(String... params) {
            String imageUrrl=params[0];

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }




}
