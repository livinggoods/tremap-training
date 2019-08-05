package com.expansion.lg.kimaru.training.fragments;

/**
 * Created by kimaru on 3/11/17.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.adapters.TraineesExamSubmissionListAdapter;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingClass;
import com.expansion.lg.kimaru.training.objs.TrainingExam;
import com.expansion.lg.kimaru.training.objs.TrainingExamResult;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

import java.util.ArrayList;
import java.util.List;

public class TrainingsExamSubmissionsFragment extends Fragment  {
    private OnFragmentInteractionListener mListener;
    TextView textshow;
    FloatingActionButton fab;

    private List<TrainingTrainee> results = new ArrayList<>();
    private RecyclerView recyclerView;
    private TraineesExamSubmissionListAdapter rAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    private FrameLayout frameLayout;

    SessionManagement sessionManagement;
    Training training = null;
    TrainingClass trainingClass = null;
    TrainingExam exam = null;
    DatabaseHelper databaseHelper;

    public TrainingsExamSubmissionsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        databaseHelper = new DatabaseHelper(getContext());
        if (training!=null) {
            TrainingViewFragment backFragment = new TrainingViewFragment();
            backFragment.training = training;
            MainActivity.backFragment = backFragment;
        }
        if (trainingClass != null){
            TrainingClassesFragment backFragment = new TrainingClassesFragment();
            backFragment.training = new DatabaseHelper(getContext()).getTrainingById(trainingClass.getTrainingId());
            MainActivity.backFragment = backFragment;
        }

        View v;
        v =  inflater.inflate(R.layout.fragment_recycler, container, false);
        textshow = (TextView) v.findViewById(R.id.textShow);
        sessionManagement = new SessionManagement(getContext());
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        frameLayout = v.findViewById(R.id.frame_layout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSubmisions();
            }
        });
        rAdapter = new TraineesExamSubmissionListAdapter(this.getContext(), results, new TraineesExamSubmissionListAdapter.TraineesExamSubmissionListAdapterListener() {
            @Override
            public void onIconClicked(int position) {
                onMessageRowClicked(position);
            }

            @Override
            public void onIconImportantClicked(int position) {
                onMessageRowClicked(position);
            }

            @Override
            public void onMessageRowClicked(int position) {
                // check if there are submissions
                try {
                    List<TrainingExamResult>  submittedQuestions = databaseHelper
                            .getTrainingExamResultByTrainee(results.get(position).getRegistrationId());
                    if (submittedQuestions.size() <= 0 ){
                        Toast.makeText(getContext(), "No submission found", Toast.LENGTH_SHORT).show();
                    }else{
                        TrainingExamQuestionSubmissionFragment fragment = new TrainingExamQuestionSubmissionFragment();
                        fragment.trainee = results.get(position);
                        fragment.exam = exam;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                } catch (Exception e){}
            }

            @Override
            public void onRowLongClicked(int position) {}

        }, exam);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(rAdapter);
        swipeRefreshLayout.post(
                new Runnable(){
                    @Override
                    public void run(){
                        getSubmisions();
                    }
                }
        );


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void toggleSelection(int position) {
        rAdapter.toggleSelection(position);
        int count = rAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }
    /*
    *  Choose a random
    *  Color
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getContext().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            rAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    rAdapter.resetAnimationIndex();
                    // mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    // deleting the messages from recycler view
    private void deleteMessages() {
        rAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                rAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            rAdapter.removeData(selectedItemPositions.get(i));
        }
        rAdapter.notifyDataSetChanged();
    }



    private void getSubmisions() {
        swipeRefreshLayout.setRefreshing(true);
        // Get results from the cloud
        try{
            new TrainingDataSync(getContext()).getTrainingExamResults(exam.getId().toString());
        }catch(Exception e){}
        results.clear();
        try {


            List<TrainingTrainee> trainees = new ArrayList<>();
            trainees = databaseHelper.getTrainingTraineesByTrainingId(training.getId());

            for (TrainingTrainee r:trainees){
                r.setColor(getRandomMaterialColor("400"));
                results.add(r);
            }
            rAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        } catch (Exception error){
            Toast.makeText(getContext(), "No submissions found", Toast.LENGTH_SHORT).show();
            textshow.setText(error.getMessage());
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (training != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(training.getTrainingName() + " Submissions");
        }
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }


}
