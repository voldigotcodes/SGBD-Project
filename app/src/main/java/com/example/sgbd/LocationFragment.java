package com.example.sgbd;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static List<LocationModel> mDataList;
    private static LocationAdapter mAdapter;

    RecyclerView mRecyclerView;

    private View rootView;


    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_location, container, false);
        }

        mDataList = new ArrayList<>();

        // create adapter
        mAdapter = new LocationAdapter(mDataList);

        // get reference to RecyclerView
        mRecyclerView = rootView.findViewById(R.id.recyclerView);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
        new ManageLocationDatabaseTask(mDataList, mAdapter).execute();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    static class DeleteLocationTask extends AsyncTask<Void, Void, Void> {

        public DeleteLocationTask(int sin){
            //TODO implement the methode to delete the locations
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
    static class UpdateLocationTask extends AsyncTask<Void, Void, Void>{

        public UpdateLocationTask(int oldSin, String addy, String nom, int sin, String enregistrement, String passeword, String email) {
            //TODO implement the methode to update the locations
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}