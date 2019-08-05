package com.expansion.lg.kimaru.training.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;


import butterknife.ButterKnife;

/**
 * Created by kimaru on 3/30/17.
 */
public class TrainingDetailsFragment extends Fragment implements  View.OnClickListener{

    Training training = null;

    ImageView trainingImagePoster;
    TextView trainingNameText;
    TextView trainingClassSize;
    TextView trainingDates;
    TextView trainingComment;
    CardView cardTrainingDetail;
    CardView cardMovieOverview;
    Typeface mTfLight, mTfRegular;
    Button traineesButton, sessionsButton, classesButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.training_details_view, container, false);
        ButterKnife.bind(this, v);
        trainingImagePoster = v.findViewById(R.id.image_training_detail_poster);
        trainingNameText = v.findViewById(R.id.text_training_name);
        trainingClassSize = v.findViewById(R.id.text_movie_user_rating);
        trainingDates = v.findViewById(R.id.text_movie_release_date);
        //trainingComment = v.findViewById(R.id.text_training_comment);
        cardTrainingDetail = v.findViewById(R.id.card_training_detail);
        cardMovieOverview = v.findViewById(R.id.card_movie_overview);
        traineesButton = v.findViewById(R.id.trainees);
        sessionsButton = v.findViewById(R.id.sessions);
        classesButton = v.findViewById(R.id.classes);
        //cardMovieVideos = v.findViewById(R.id.card_movie_videos);
//        movieVideos = v.findViewById(R.id.movie_videos);
        getTrainingDetailsFromApi();

        if (training != null) {
            new TrainingDataSync(getContext()).startPollUploadExamResults(training.getId());
        }

        //cardMovieReviews = v.findViewById(R.id.card_movie_reviews);
        //movieReviews = v.findViewById(R.id.movie_reviews);
        mTfLight = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");
        mTfRegular = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        initViews();
        setupCardsElevation();


        traineesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;
                TrainingsTraineesFragment trainingsTraineesFragment = new TrainingsTraineesFragment();
                trainingsTraineesFragment.training = training;
                fragment = trainingsTraineesFragment;
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        });


        sessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;

                TrainingsSessionsFragment trainingsSessionsFragment = new TrainingsSessionsFragment();
                trainingsSessionsFragment.training = training;
                fragment = trainingsSessionsFragment;
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        });
        classesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;

                TrainingClassesFragment trainingClassesFragment = new TrainingClassesFragment();
                trainingClassesFragment.training = training;
                fragment = trainingClassesFragment;
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (training != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(training.getTrainingName());
        }
    }

    @Override
    public void onClick (View view){
        Fragment fragment;
        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager;
        switch (view.getId()){
            case R.id.action_delete:

                break;
        }
    }
    private void initViews() {
        Glide.with(this)
                .load(R.drawable.lg_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(trainingImagePoster);
        if (training != null){
            trainingNameText.setText(training.getTrainingName());
            trainingClassSize.setText(String.valueOf(new DatabaseHelper(getContext()).getTrainingTraineesByTrainingId(training.getId()).size()));
            trainingClassSize.setTextColor(getRatingColor(9.0D));
            String releaseDate = String.format(getString(R.string.training_date),
                    String.valueOf(training.getDateCommenced()), String.valueOf(training.getClientTime()));
            trainingDates.setText(releaseDate);
            //trainingComment.setText(training.getComment());
        }

    }
    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getClass().getPackage().getName());
        return drawableResourceId;
    }
    @ColorInt
    private int getRatingColor(double averageVote) {
//        if (averageVote >= VOTE_PERFECT) {
//            return ContextCompat.getColor(getContext(), R.color.vote_perfect);
//        } else if (averageVote >= VOTE_GOOD) {
//            return ContextCompat.getColor(getContext(), R.color.vote_good);
//        } else if (averageVote >= VOTE_NORMAL) {
//            return ContextCompat.getColor(getContext(), R.color.vote_normal);
//        } else {
//            return ContextCompat.getColor(getContext(), R.color.vote_bad);
//        }
        return ContextCompat.getColor(getContext(), R.color.vote_perfect);
    }

    private void setupCardsElevation() {
        setupCardElevation(cardTrainingDetail);
        //setupCardElevation(cardMovieVideos);
        setupCardElevation(cardMovieOverview);
        //setupCardElevation(cardMovieReviews);
    }
    private void setupCardElevation(View view) {
        ViewCompat.setElevation(view,
                convertDpToPixel(getResources().getInteger(R.integer.training_detail_content_elevation_in_dp)));
    }
    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private SpannableString generateCenterSpannableText() {
        return new SpannableString(training.getTrainingName());
    }

    private void getTrainingDetailsFromApi(){
        try{
            new TrainingDataSync(getContext()).getTrainingDetailsJson(training.getId());
        }catch (Exception e){}
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

}
