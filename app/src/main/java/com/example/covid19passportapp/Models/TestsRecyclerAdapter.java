package com.example.covid19passportapp.Models;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19passportapp.R;

import java.util.List;

public class TestsRecyclerAdapter extends RecyclerView.Adapter<TestsRecyclerAdapter.ViewHolder>{

    private List<Test> tests;

    public TestsRecyclerAdapter(List<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.test_result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestsRecyclerAdapter.ViewHolder holder, int position) {
        Test test = tests.get(position);
        holder.id.setText(String.valueOf(test.getID()));
        holder.date.setText(test.getDate().toString("dd/MM/yy"));
        holder.result.setText(test.getResult());
        if (test.getResult().equalsIgnoreCase("positive")) {
            //holder.result.setTextColor(Color.parseColor("#FF0023"));
            holder.testResultRow.setBackgroundResource(R.drawable.test_result_row_bg_positive);
        }
        else if (test.getResult().equalsIgnoreCase("negative")) {
            //holder.result.setTextColor(Color.parseColor("#64B31D"));
            //holder.testResultRow.setBackgroundResource(R.drawable.test_result_row_bg_negative);
        }

    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout testResultRow;
        TextView id;
        TextView date;
        TextView result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.testResultRow = itemView.findViewById(R.id.testResultRow);
            this.id = itemView.findViewById(R.id.testID);
            this.date = itemView.findViewById(R.id.testDate);
            this.result = itemView.findViewById(R.id.testResult);
        }
    }
}
