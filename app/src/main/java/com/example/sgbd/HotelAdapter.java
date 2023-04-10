package com.example.sgbd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.TestViewHolder> {

    private List<Hotel> mHotels;

    public HotelAdapter(List<Hotel> hotels) {
        mHotels = hotels;
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
        Hotel hotel = mHotels.get(position);

        holder.price.setText(String.valueOf(hotel.getPrix()));
        holder.capacity.setText(String.valueOf(hotel.getCap()));
        holder.area.setText(String.valueOf(hotel.getSup()));

        holder.cNom.setText(String.valueOf(hotel.getcNom()));
        holder.hNom.setText(String.valueOf(hotel.gethNom()));
        holder.etoile.setRating((float) hotel.getCat());
        holder.etoile.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.etoile.setRating((float) hotel.getCat());
            }
        });

        holder.cap.setText(String.valueOf(hotel.getCap()));
        holder.numCh.setText(String.valueOf(hotel.getNumCh()));
        holder.adressHotel.setText(String.valueOf(hotel.gethAd()));

        holder.hphone = hotel.getPhone();
        holder.hemail = hotel.getEmail();


    }

    @Override
    public int getItemCount() {
        if (mHotels == null) {
            return 0;
        }
        return mHotels.size();
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

        public String hphone;
        public String hemail;

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
            hphone = "";
            hemail = "";
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
                        EmployeeActivity.showUpdateHotelPopup(view, adressHotel.getText().toString(), hNom.getText().toString(),
                                numCh.getText().toString(), cNom.getText().toString(), etoile.getNumStars(), hphone, hemail);

                    }

                }
            });

        }
    }

}