package com.example.androidgroupproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ArticleDetail extends Fragment {
    private OnFragmentInteractionListener mListener;
    private AppCompatActivity parentActivity;
    private Bundle dataFromActivity;

    public ArticleDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_article_detail, container, false);
        //Long id = dataFromActivity.getLong("id");
        String t = dataFromActivity.getString("title");
        String u = dataFromActivity.getString("url");
        String sect = dataFromActivity.getString("sectionName");

        TextView title = result.findViewById(R.id.titleEdit);
        TextView url = result.findViewById(R.id.urlEdit);
        TextView section = result.findViewById(R.id.sectionEdit);

        title.setText(t);
        url.setText(u);
        section.setText(sect);

        return result;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_article_detail);
//        Intent intent= getIntent();
//        TextView title = findViewById(R.id.titleEdit);
//        TextView url = findViewById(R.id.urlEdit);
//        TextView section = findViewById(R.id.sectionEdit);
//        TextView link = findViewById(R.id.urlEdit);
//
//        String t= intent.getStringExtra("title");
//        String sect= intent.getStringExtra("sectionName");
//        String u= intent.getStringExtra("url");
//
//        title.setText(t);
//        url.setText(u);
//        section.setText(sect);
////        link.setOnClickListener(c->{
////            String urli = u;
////            Intent i = new Intent(Intent.ACTION_VIEW);
////            i.setData( Uri.parse(urli) );
////            startActivity(i);
////        });
//    }
}
