package com.expansion.lg.kimaru.training.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.TrainingExam;
import com.expansion.lg.kimaru.training.utils.DisplayDate;
import com.gadiness.kimarudg.ui.alerts.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kimaru on 3/14/18.
 */

public class TrainingExamDetailsFragment extends Fragment {

    TrainingExam exam = null;
    TextView examTitle, examCode, totalSubmitted, totalUnsubmitted, totalTrainees, passmark,
            examStatus;

    public TrainingExamDetailsFragment(){}

    public static TrainingExamDetailsFragment newInstance(){
        TrainingExamDetailsFragment fragment = new TrainingExamDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_exam_details, container, false);
//        View view = inflater.inflate(R.layout.fragment_exam_details, container, false);
        TrainingsExamsFragment backFragment = new TrainingsExamsFragment();
        backFragment.training = new DatabaseHelper(getContext()).getTrainingById(exam.getTrainingId());
        MainActivity.backFragment = backFragment;

        examTitle = view.findViewById(R.id.examTitle);
        examTitle.setText(exam.getTitle());
        examCode = view.findViewById(R.id.examCode);
        examCode.setText(exam.getExamCode());
        totalSubmitted = view.findViewById(R.id.totalSubmitted);
        totalSubmitted.setText("0");
        totalUnsubmitted = view.findViewById(R.id.totalUnsubmitted);
        totalUnsubmitted.setText("0");
        totalTrainees = view.findViewById(R.id.totalTrainees);
        totalTrainees.setText("0");
        passmark = view.findViewById(R.id.passmark);
        passmark.setText(exam.getPassmark().toString());
        examStatus = view.findViewById(R.id.exam_status);
        JSONObject examJson = exam.getCloudExamJson();
        if (!examJson.isNull("exam_status")){
            try {
                examStatus.setText(examJson.getJSONObject("exam_status").getString("title"));
            }catch (Exception e){}
        }

        if (!examJson.isNull("unlock_code")) {
            try {
                examCode.setText(String.format("CODE: %s", examJson.getString("unlock_code")));
            } catch (Exception e) {}
        }

        if (examJson.isNull("passmark")) {
            try {
                passmark.setText(examJson.getString("passmark"));
            } catch (Exception e) {}
        }


        List<SessionAttendance> sessions = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(getContext());

        // add statuses
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.traineeStatusActions);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (exam != null){
            try{
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(exam.getTitle());
            }catch (Exception e){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Exam Details");
            }
        }
    }



}
