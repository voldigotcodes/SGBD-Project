package com.example.sgbd;

import android.content.Context;
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
/*  private List<MyModel> mData;

    // constructor and other adapter methods

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyModel model = mData.get(position);

            Intent intent = new Intent(mContext, MyNewActivity.class);
            intent.putExtra("key", model.getData());
            mContext.startActivity(intent);
        }
    }*/
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
        holder.adressHotel.setText(String.valueOf(test.gethAd()));


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
        public TextView adressHotel;

        public Button confirm;
        private Context mContext;
/* private TextView mTextView;
        private Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyModel model = mData.get(position);

            Intent intent = new Intent(mContext, MyNewActivity.class);
            intent.putExtra("key", model.getData());
            mContext.startActivity(intent);*/

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
            adressHotel = itemView.findViewById(R.id.adressView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getContext().getClass() == MainActivity.class) {

                        Intent intent = new Intent(mContext, makeReservation.class);
                        intent.putExtra("Adresse", adressHotel.getText().toString());
                        intent.putExtra("No. chambre", numCh.getText().toString());
                        mContext.startActivity(intent);
                    }

                    if(view.getContext().getClass() == EmployeeActivity.class) {
                        EmployeeActivity.showUpdateHotelPopup(view);
                    }

                }
            });

        }
    }

}