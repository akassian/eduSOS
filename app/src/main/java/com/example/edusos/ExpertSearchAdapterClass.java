package com.example.edusos;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpertSearchAdapterClass extends RecyclerView.Adapter<ExpertSearchAdapterClass.ViewHolder> {

    ArrayList<Expert> expertList;
    ArrayList<String> expertKeyList;

    public ExpertSearchAdapterClass(ArrayList<Expert> expertList, ArrayList<String> expertKeyList) {
        this.expertList = expertList;
        this.expertKeyList = expertKeyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expert_search_result_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Expert expertObj = expertList.get(position);
        holder.name.setText(expertObj.getName());

        holder.email.setText("Email: "+expertObj.getGoogleAccount()+"@gmail.com");
        if (expertObj.getRating() != null ) {
            holder.rating.setText("Rating: "+expertObj.getRating().toString());
        } else {
            holder.rating.setText("No Rating");
        }
        if (expertObj.getRatePerQuestion() != null ) {
            holder.ratePerQuestion.setText("Rate per question: $"+expertObj.getRatePerQuestion().toString());
        } else {
            holder.ratePerQuestion.setText("Rate per question: No Charge");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expert chosenExpert = expertList.get(position);
                Intent intent = new Intent(view.getContext(), ExpertProfileActivity.class);
                intent.putExtra("chosenKey", expertKeyList.get(position));
                intent.putExtra("name", chosenExpert.getName());
                intent.putExtra("googleAcc", chosenExpert.getGoogleAccount());
                Log.d("ACC_adapter",chosenExpert.getGoogleAccount() );
                intent.putExtra("phone", chosenExpert.getPhone());
                intent.putStringArrayListExtra("subjects", chosenExpert.getSubjects());
                intent.putExtra("rating", chosenExpert.getRating());
                intent.putExtra("ratePerQuestion", chosenExpert.getRatePerQuestion());
                intent.putExtra("questionsAnswered", chosenExpert.getQuestionsAnswered());
                intent.putExtra("online", chosenExpert.getOnline());


//                Log.d("RECYCLER_", String.valueOf(position));
//                ArrayList<Expert> chosenExpert = new ArrayList<>();
//                chosenExpert.add(expertList.get(position));
//                Intent intent = new Intent(view.getContext(), AnswerQuestionActivity.class);
//                //intent.putParcelableArrayListExtra("matchedQuestions", expertList); // not needed
////

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
        return expertList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, rating, ratePerQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardview_name);
            email = itemView.findViewById(R.id.cardview_email);
            rating = itemView.findViewById(R.id.cardview_rating);
            ratePerQuestion = itemView.findViewById(R.id.cardview_ratePerQuestion);
        }
    }
}
