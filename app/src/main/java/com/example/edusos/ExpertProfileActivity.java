package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;

import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.edusos.EduUtils.stringToURL;

public class ExpertProfileActivity extends AppCompatActivity {
    DatabaseReference dbExpert;
    Expert expertObj;
    String key;
    String name, email, phone, account;
    URL imageURL;
    Double rating, ratePerQuestion;
    int questionAnswered;
    Boolean online;
    ArrayList<String> subjects;

    private CardView chatWithExpertBtn;
    private CardView emailExpertBtn;
    private CircleImageView imageViewProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);

        TextView textViewName= (TextView) findViewById(R.id.expert_profile_name);
        ImageView imageViewOnlineIndicator = (ImageView) findViewById(R.id.onlineIndicator);
        imageViewProfilePicture = (CircleImageView) findViewById(R.id.profileImage);
        TextView textViewRating = (TextView) findViewById(R.id.expert_profile_rating);
        TextView textViewRatePerQuestion = (TextView) findViewById(R.id.expert_profile_ratePerQuestion);

        TextView textViewQuestionsAnswered = (TextView) findViewById(R.id.expert_profile_questionAnswered);
        ChipGroup chipGroupSubjects = (ChipGroup) findViewById(R.id.expert_profile_subjects);

        Intent intent = getIntent();

        key = intent.getStringExtra("chosenKey");
        Log.d("KEY_", key);

        name = intent.getStringExtra("name");
        account = intent.getStringExtra("googleAcc");
        email  = account + "@gmail.com";
        phone = intent.getStringExtra("phone");
        rating = intent.getDoubleExtra("rating", 4.0);
        ratePerQuestion = intent.getDoubleExtra("ratePerQuestion", 0.0);
        subjects = intent.getStringArrayListExtra("subjects");
        online = intent.getBooleanExtra("online", true);
        questionAnswered = intent.getIntExtra("questionAnswered", 10);
        imageURL = stringToURL(intent.getStringExtra("imageURL"));

        if (subjects != null && subjects.size() >0) {
            for (String subject: subjects) {
                Chip subjectChip = getChip(chipGroupSubjects, subject);
                chipGroupSubjects.addView(subjectChip);
            }
        }

        textViewName.setText(name);
        textViewRatePerQuestion.setText("$ "+ ratePerQuestion.toString());

        textViewRating.setText(rating + " / 5");
        textViewQuestionsAnswered.setText(questionAnswered + " Answers");

        chatWithExpertBtn = findViewById(R.id.chatButton);
        chatWithExpertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatActivity();
            }
        });

        emailExpertBtn = findViewById(R.id.emailButton);
        emailExpertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailActivity();
            }
        });

        if (online) {
            imageViewOnlineIndicator.setImageResource(R.drawable.ic_online);
        } else {
            imageViewOnlineIndicator.setImageResource(R.drawable.ic_offline);
        }

        new ProfilePictureDownloadTask().execute(imageURL);
    }

    public void openChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("googleAcc", account);
        startActivity(intent);
    }

    public void openEmailActivity() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.email_subject_default);
        intent.putExtra(Intent.EXTRA_TEXT, R.string.email_body_default);
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    private Chip getChip(final ChipGroup entryChipGroup, String text) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.chip));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(text);
        chip.setCloseIconVisible(false);
        return chip;
    }


    private class ProfilePictureDownloadTask extends AsyncTask<URL, Void, Bitmap> {
        // Before the tasks execution
        protected void onPreExecute(){

        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            HttpURLConnection connection;

            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                return bmp;
            } catch(IOException e) {
                Log.e("NETWORK", "Image download error");
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result){
            if(result!=null){
                // Display the downloaded image into ImageView
                imageViewProfilePicture.setImageBitmap(result);
            } else {
                result = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_prof_pic_default);
                imageViewProfilePicture.setImageBitmap(result);
            }
        }
    }
}


