package com.expansion.lg.kimaru.training.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.TraineeStatus;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.utils.DisplayDate;
import com.expansion.lg.kimaru.training.utils.SessionManagement;
import com.gadiness.kimarudg.ui.alerts.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kimaru on 3/14/18.
 */

public class TraineeDetailsFragment extends Fragment {

    TrainingTrainee trainingTrainee = null;
    boolean dropCandidate = false;

    public TraineeDetailsFragment(){}

    public static TraineeDetailsFragment newInstance(){
        TraineeDetailsFragment fragment = new TraineeDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_trainee_details, container, false);
        TrainingsTraineesFragment backFragment = new TrainingsTraineesFragment();
        backFragment.training = new DatabaseHelper(getContext()).getTrainingById(trainingTrainee.getTrainingId());
        MainActivity.backFragment = backFragment;
        String traineeName="", traineePhone="", traineeGender="";
        Long traineeDob=0L;
        List<SessionAttendance> sessions = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(getContext());
        try{
            traineeName = trainingTrainee.getRegistration().getString("name");
            traineePhone = trainingTrainee.getRegistration().getString("phone");
            traineeGender = trainingTrainee.getRegistration().getString("gender");
            traineeDob = trainingTrainee.getRegistration().getLong("dob");
        }catch (Exception e){}
        TextView traineeNameView = view.findViewById(R.id.traineeName);
        traineeNameView.setText(traineeName);
        TextView traineePhoneView = view.findViewById(R.id.traineePhone);
        traineePhoneView.setText(traineePhone);
        TextView traineeGenderView = view.findViewById(R.id.traineeGender);
        traineeGenderView.setText(traineeGender);

        int year, month, day;
        year = Integer.valueOf(new DisplayDate(traineeDob).yearOnly());
        month = Integer.valueOf(new DisplayDate(traineeDob).monthOnly());
        day = Integer.valueOf(new DisplayDate(traineeDob).dayOnly());
        TextView traineeAgeView = view.findViewById(R.id.traineeAge);
        traineeAgeView.setText(getAge(year, month, day));

        //Attendance
        sessions = db.getSessionAttendancesByTraineeId(trainingTrainee.getId());
        TextView traineeSessionsAttended = view.findViewById(R.id.sessionsAttended);
        traineeSessionsAttended.setText(String.valueOf(sessions.size()));

        TextView traineeClassView = view.findViewById(R.id.traineeClass);
        traineeClassView.setText("Class "+ db.getTrainingClassById(
                trainingTrainee.getClassId().toString()).getClassName());


        // add statuses
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.traineeStatusActions);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        /**
         * The code below is meant to dynamically add the status
         */

//        for(TraineeStatus t : new DatabaseHelper(getContext()).getTraineeStatuses()){
//            Button btn = new Button(getContext());
//            btn.setId(t.getId());
//            btn.setText(t.getName());
//            btn.setBackground(getContext().getDrawable(R.drawable.buttonstyleithgradient));
//            lParams.setMargins(0, 35, 0, 0);
//            lParams.gravity = Gravity.CENTER;
//            btn.setTextColor(Color.parseColor("#ffffff"));
//            btn.setLayoutParams(lParams);
//            linearLayout.addView(btn);
//        }

        Button discontinue = view.findViewById(R.id.drop);
        discontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarnig();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (trainingTrainee != null){
            try{
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(trainingTrainee.getRegistration().getString("name"));
            }catch (Exception e){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Trainee Profile");
            }
        }
    }

    public void showWarnig(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to drop the trainee!")
                .setCancelText("No,cancel pls!")
                .setConfirmText("Yes,drop it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        dropCandidate = true;
                        sDialog.dismissWithAnimation();
                        showDialog();
                    }
                })
                .show();
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add a reason");
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText reason = new EditText(getContext());
        reason.setHint("Why?");
        layout.addView(reason);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String why = reason.getText().toString();
                trainingTrainee.setComment(why);
                trainingTrainee.setTraineeStatus(2);
                new DatabaseHelper(getContext()).addTrainingTrainee(trainingTrainee);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
