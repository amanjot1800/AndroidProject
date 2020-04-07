package com.example.androidgroupproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class lastfrag extends Fragment {
    private Bundle dataFromActivity;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        long i = dataFromActivity.getLong(Test.ID );
        // Use to inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_lastfrag, container, false);
        ImageView img = view.findViewById(R.id.zz); // setting the image using the image view
        Picasso.get().load(dataFromActivity.getString(Test.IMAGEURL).toString()).into(img);
        TextView message = view.findViewById(R.id.pp); // setting the text for the longitude
        message.setText(dataFromActivity.getString(Test.ITEM_LONG));
        TextView mess = view.findViewById(R.id.ll); // setting the text for the latitude
        mess.setText(dataFromActivity.getString(Test.ITEM_LAT));
        return view; // returns the view
    }
}
