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
        holder.nameTextView.setText(test.getName());
        holder.ageTextView.setText(String.valueOf(test.getAge()));
        holder.salaryTextView.setText(String.valueOf(test.getSalary()));
    }

    @Override
    public int getItemCount() {
        if (mTests == null) {
            return 0;
        }
        return mTests.size();
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView ageTextView;
        public TextView salaryTextView;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
        }
    }
}