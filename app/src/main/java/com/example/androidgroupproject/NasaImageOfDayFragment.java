package com.example.androidgroupproject;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NasaImageOfDayFragment extends Fragment {

    private Bundle data;
    private AppCompatActivity parentActivity;

    public NasaImageOfDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nasa_image_of_day, container, false);
        data = getArguments();

        TextView date = (TextView) view.findViewById(R.id.frag_date);
        date.setText(data.getString("date"));

        TextView url = (TextView) view.findViewById(R.id.frag_url);
        url.setText(data.getString("url"));

        TextView hdurl = (TextView) view.findViewById(R.id.frag_hdurl);
        hdurl.setText(data.getString("hdurl"));

        Button hide = (Button) view.findViewById(R.id.nasaHide);
        hide.setOnClickListener( clk -> {
//            EmptyActivity activity = (EmptyActivity) getActivity();
//            activity.finish();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }


}
