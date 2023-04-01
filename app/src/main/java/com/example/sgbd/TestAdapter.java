package com.example.sgbd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.cat.setText(String.valueOf(test.getCat()));

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

        }
    }

}