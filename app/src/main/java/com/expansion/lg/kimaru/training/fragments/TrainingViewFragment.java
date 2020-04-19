package com.expansion.lg.kimaru.training.fragments;


import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.network.UploadCertificationResultsTask;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;

/**
 * Created by kimaru on 3/30/17.
 */
public class TrainingViewFragment extends Fragment implements  View.OnClickListener{

    Training training = null;

    TextView trainingName, trainingTrainees, trainingClasses, trainingSessions, trainingAttendance,
            trainingLeadTrainer, tvSyncMsg;
    LinearLayout traineesButton, sessionsButton, classesButton, layoutSync;
    ImageView trainingImage;
    Button btnSync, trainingExamsView;

    DatabaseHelper db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
    }

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


        traineesButton = v.findViewById(R.id.traineesButton);
        sessionsButton = v.findViewById(R.id.sessionsButton);
        classesButton = v.findViewById(R.id.classesButton);

        layoutSync = v.findViewById(R.id.layout_sync);
        btnSync = v.findViewById(R.id.btn_sync_now);
        tvSyncMsg = v.findViewById(R.id.tv_sync_msg);

        int examResultOfflineCount = db.getOfflineRecordCount(DatabaseHelper.TABLE_EXAM_RESULTS);
        layoutSync.setVisibility(examResultOfflineCount > 0 ? View.VISIBLE : View.GONE);
        tvSyncMsg.setText(String.format("%d pending offline exam records", examResultOfflineCount));

        getTrainingDetailsFromApi();
        getTrainingExamsFromApi();
        syncExamResults();

        DatabaseHelper db = new DatabaseHelper(getContext());
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UploadCertificationResultsTask(getContext()).execute(training.getId());

            }
        });

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

    private void syncExamResults() {
        try {
            new TrainingDataSync(getContext()).startPollUploadExamResults(training.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

}
