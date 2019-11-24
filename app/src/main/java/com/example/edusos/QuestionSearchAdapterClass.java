package com.example.edusos;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionSearchAdapterClass extends RecyclerView.Adapter<QuestionSearchAdapterClass.ViewHolder> {

    ArrayList<Question> questionList;
    ArrayList<String> questionKeyList;

    public QuestionSearchAdapterClass(ArrayList<Question> questionList, ArrayList<String> questionKeyList) {
        this.questionList = questionList;
        this.questionKeyList = questionKeyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_search_result_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.subject.setText(questionList.get(position).getSubject());
        holder.question.setText(questionList.get(position).getQuestion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RECYCLER_", String.valueOf(position));
                ArrayList<Question> chosenQuestion = new ArrayList<>();
                chosenQuestion.add(questionList.get(position));
                Intent intent = new Intent(view.getContext(), AnswerQuestionActivity.class);
                intent.putParcelableArrayListExtra("matchedQuestions", questionList); // not needed
                intent.putParcelableArrayListExtra("chosenQuestion", chosenQuestion);
                intent.putExtra("chosenKey", questionList.get(position));

                view.getContext().startActivity(intent);
            }
        });
    }

//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.cardview_subject1);
            question = itemView.findViewById(R.id.cardview_question1);
        }
    }
}
