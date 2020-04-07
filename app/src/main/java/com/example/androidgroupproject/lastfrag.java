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
    private long id;
    private AppCompatActivity parentActivity;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        long i = dataFromActivity.getLong(Test.ID );

        View view = inflater.inflate(R.layout.fragment_lastfrag, container, false);
        ImageView img = view.findViewById(R.id.zz);
        Picasso.get().load(dataFromActivity.getString(Test.IMAGEURL).toString()).into(img);
        TextView message = view.findViewById(R.id.pp);
        message.setText("Longitude - "+dataFromActivity.getString(Test.ITEM_LONG));
        return view;
    }
}
