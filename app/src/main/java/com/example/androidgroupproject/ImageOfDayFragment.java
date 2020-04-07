package com.example.androidgroupproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImageOfDayFragment extends Fragment {

    /**
     * The bundle in which all the data is stored
     */
    private Bundle data;
    /**
     * Used to stop current activity and other reasons
     */
    private AppCompatActivity parentActivity;
    /**
     * Image set to the fragment after loading from the phone memory
     */
    Bitmap image;
    /**
     * Tells whether to use phone layout or tablet layout
     */
    private boolean isTablet;

    /**
     * Empty Construvtor
     */
    public ImageOfDayFragment() {
        // Required empty public constructor
    }


    /**
     * Creates the Fragment View
     * @param inflater infalter object
     * @param container Container object
     * @param savedInstanceState Bundle object
     * @return View to be inflated and used elsewhere
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nasa_image_of_day, container, false);
        data = getArguments();

        if (isImageDownloaded(data.getString("date"))){

            FileInputStream inputStream = null;

            try {
                inputStream = parentActivity.openFileInput(data.getString("date"));
            } catch (FileNotFoundException ex){
                Toast.makeText(parentActivity, "Image not found", Toast.LENGTH_LONG).show();
            }
            image = BitmapFactory.decodeStream(inputStream);
        }


        TextView date = (TextView) view.findViewById(R.id.frag_date);
        date.setText(data.getString("date"));

        TextView url = (TextView) view.findViewById(R.id.frag_url);
        url.setText(data.getString("url"));

        TextView hdurl = (TextView) view.findViewById(R.id.frag_hdurl);
        hdurl.setText(data.getString("hdurl"));

        ImageView imageView = view.findViewById(R.id.frag_imageViewNASA);
        imageView.setImageBitmap(image);

        isTablet = data.getBoolean("isTablet");

        Button hide = (Button) view.findViewById(R.id.nasaHide);
        hide.setOnClickListener( clk -> {

            if (!isTablet) {
                EmptyActivity activity = (EmptyActivity) getActivity();
                activity.finish();
            }

        });

        return view;
    }

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }

    /**
     * This method tells if the image exists in the file system or not
     * @param date the date to look after the image name
     * @return true if image exists, otherwise false
     */
    public boolean isImageDownloaded(String date){
        File file = parentActivity.getBaseContext().getFileStreamPath(date);
        return file.exists();
    }

}
