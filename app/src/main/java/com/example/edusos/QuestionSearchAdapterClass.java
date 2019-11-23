package com.example.edusos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionSearchAdapterClass extends RecyclerView.Adapter<QuestionSearchAdapterClass.ViewHolder> {

    ArrayList<Question> questionList;

    public QuestionSearchAdapterClass(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_search_result_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subject.setText(questionList.get(position).getSubject());
        holder.question.setText(questionList.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.cardview_subject);
            question = itemView.findViewById(R.id.cardview_question);
        }
    }
}
