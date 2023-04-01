package com.example.sgbd;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private List<Test> mTests;

    public TestAdapter(List<Test> tests) {
        mTests = tests;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Test test = mTests.get(position);

        holder.price.setText(String.valueOf(test.getPrix()));
        holder.capacity.setText(String.valueOf(test.getCap()));
        holder.area.setText(String.valueOf(test.getSup()));

        holder.cNom.setText(String.valueOf(test.getcNom()));
        holder.hNom.setText(String.valueOf(test.gethNom()));
        holder.etoile.setRating((float)test.getCat());

        holder.cap.setText(String.valueOf(test.getCap()));
        holder.numCh.setText(String.valueOf(test.getNumCh()));

    }

    @Override
    public int getItemCount() {
        if (mTests == null) {
            return 0;
        }
        return mTests.size();
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {

        public TextView price;
        public TextView capacity;
        public TextView area;

        public TextView cNom;
        public TextView hNom;
        public TextView cat;

        public TextView cap;
        public TextView numCh;

        public RatingBar etoile;

        public Button confirm;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.viewPrice);
            capacity = itemView.findViewById(R.id.viewCapacity);
            area = itemView.findViewById(R.id.viewSize);
            cNom = itemView.findViewById(R.id.viewChain);
            hNom = itemView.findViewById(R.id.hotelNameView);
            cap = itemView.findViewById(R.id.viewCapacity);
            cat = itemView.findViewById(R.id.viewCategory);
            numCh = itemView.findViewById(R.id.viewRoomsNum);
            etoile = itemView.findViewById(R.id.ratingBar3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getContext().getClass() == MainActivity.class) {
                        MainActivity.showDatePickerPopup(view);
                    }

                    if(view.getContext().getClass() == EmployeeActivity.class) {
                        EmployeeActivity.showUpdateHotelPopup(view);
                    }

                }
            });

        }
    }

}