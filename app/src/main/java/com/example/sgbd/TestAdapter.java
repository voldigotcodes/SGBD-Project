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
        holder.area.setText(String.valueOf(String.valueOf(test.getSup())));

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

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            capacity = itemView.findViewById(R.id.capacity);
            area = itemView.findViewById(R.id.area);
        }
    }

}