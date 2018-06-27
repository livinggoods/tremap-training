package com.expansion.lg.kimaru.training.fragments;


import android.graphics.Color;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.utils.charts.ChartItem;
import com.expansion.lg.kimaru.training.utils.charts.LineChartItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by kimaru on 3/30/17.
 */
public class TrainingViewFragment extends Fragment implements  View.OnClickListener{

    Training training = null;

    TextView trainingName, trainingTrainees, trainingClasses, trainingSessions, trainingAttendance,
            trainingLeadTrainer;
    LinearLayout traineesButton, sessionsButton, classesButton, trainingExamsView;
    ImageView trainingImage;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.training_view_fragment, container, false);
        MainActivity.backFragment = new TrainingsFragment();
        trainingName = v.findViewById(R.id.trainingName);
        trainingTrainees = v.findViewById(R.id.trainingTrainees);
        trainingClasses = v.findViewById(R.id.trainingClasses);
        trainingSessions = v.findViewById(R.id.trainingSessions);
        trainingAttendance = v.findViewById(R.id.trainingAttendance);
        trainingLeadTrainer = v.findViewById(R.id.trainingLeadTrainer);
        trainingImage = v.findViewById(R.id.trainingImage);
        trainingExamsView = v.findViewById(R.id.trainingExamsView);

        Log.d("TREMAP", "{}{}{}{}{}{}{}{}{");
        Log.d("TREMAP", DatabaseHelper.CREATE_TABLE_TRAINING_EXAM);
        Log.d("TREMAP", "{}{}{}{}{}{}{}{}{");


        traineesButton = v.findViewById(R.id.traineesButton);
        sessionsButton = v.findViewById(R.id.sessionsButton);
        classesButton = v.findViewById(R.id.classesButton);
        getTrainingDetailsFromApi();
        getTrainingExamsFromApi();

        DatabaseHelper db = new DatabaseHelper(getContext());
        trainingName.setText(training.getTrainingName());
        trainingTrainees.setText(String.valueOf(db.getTrainingTraineesByTrainingId(training.getId()).size()));
        Integer m = db.getTrainingClassByTrainingId(training.getId()).size();
        trainingClasses.setText(String.valueOf(db.getTrainingClassByTrainingId(training.getId()).size()));
        trainingSessions.setText(String.valueOf(db.getTrainingSessionsByTrainingId(training.getId()).size()));
        Glide.with(this)
                .load(R.drawable.lg_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(trainingImage);



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

        trainingExamsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;
                TrainingsExamsFragment trainingExamsFragment = new TrainingsExamsFragment();
                trainingExamsFragment.training = training;
                fragment = trainingExamsFragment;
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
    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getClass().getPackage().getName());
        return drawableResourceId;
    }
    @ColorInt
    private int getRatingColor(double averageVote) {
        return ContextCompat.getColor(getContext(), R.color.vote_perfect);
    }

    private void getTrainingDetailsFromApi(){
        try{
            new TrainingDataSync(getContext()).getTrainingDetailsJson(training.getId());
        }catch (Exception e){}
    }

    private void getTrainingExamsFromApi(){
        try{
            new TrainingDataSync(getContext()).getTrainingExams(training.getId());
        }catch (Exception e){}
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

}
