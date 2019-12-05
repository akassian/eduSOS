package com.example.edusos;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

        String subjectStr = "Subjects:";
        ArrayList<String> subjects = expertObj.getSubjects();

        if (subjects != null && subjects.size() >0) {
            for (String subject: subjects) {
                subjectStr += " " + subject + " |";
            }
            subjectStr = subjectStr.substring(0, subjectStr.length() - 1);
        } else {
            subjectStr += " N/A";
        }
        holder.subjects.setText(subjectStr);

        if (expertObj.getRating() != null ) {
            holder.rating.setText(expertObj.getRating().toString() + " / 5");
        } else {
            holder.rating.setText("No Rating");
        }
        if (expertObj.getRatePerQuestion() != null ) {
            holder.ratePerQuestion.setText("$" + expertObj.getRatePerQuestion().toString());
        } else {
            holder.ratePerQuestion.setText("No Charge");
        }

        if (expertObj.getOnline()) {
            holder.onlineIndicator.setImageResource(R.drawable.ic_online);
        } else {
            holder.onlineIndicator.setImageResource(R.drawable.ic_offline);
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
                intent.putExtra("imageURL", chosenExpert.getImageURL());

                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return expertList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, subjects, rating, ratePerQuestion;
        ImageView onlineIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expert_result_name);
            subjects = itemView.findViewById(R.id.expert_result_subjects);
            rating = itemView.findViewById(R.id.expert_result_rating);
            ratePerQuestion = itemView.findViewById(R.id.expert_result_ratePerQuestion);
            onlineIndicator = itemView.findViewById(R.id.expert_results_online_indicator);
        }
    }
}
