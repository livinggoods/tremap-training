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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.adapters.SessionAttendanceListAdapter;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.TrainingSession;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

//import com.expansion.lg.kimaru.training.swipehelpers.TrainingRecyclerItemTouchHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SessionAttendanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
//AppCompatActivity implements TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener
public class SessionAttendanceFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SessionAttendanceListAdapter rAdapter;
    List<SessionAttendance> sessionAttendances = new ArrayList<>();
    FloatingActionButton fab;
    Button btnGetSelected;
    DatabaseHelper databaseHelper;

    TrainingSession session = null;
    public SessionAttendanceFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        TrainingsSessionsFragment backFragment = new TrainingsSessionsFragment();
        backFragment.training = new DatabaseHelper(getContext()).getTrainingById(session.getTrainingId());
        MainActivity.backFragment = backFragment;
        databaseHelper = new DatabaseHelper(getContext());

        View v;
        v =  inflater.inflate(R.layout.fragment_attendance, container, false);
        recyclerView = v.findViewById(R.id.list);
        btnGetSelected = v.findViewById(R.id.btnGetSelected);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSessionAttendanceData();
            }
        });
        rAdapter = new SessionAttendanceListAdapter(this.getContext(), sessionAttendances, new SessionAttendanceListAdapter.SessionAttendanceListAdapterListener() {
            @Override
            public void onRowLongClicked(int position) {

            }
            @Override
            public void onTraineeSelected(){
                String selected = String.valueOf(rAdapter.getSelectedTrainees().size());
                btnGetSelected.setText("("+selected+") selected");
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(rAdapter);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getSessionAttendanceData();
            }
        });

//        btnGetSelected.setVisibility(View.GONE);
        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save the seelected to DB
                for (SessionAttendance s : rAdapter.getSelectedTrainees()){
                    databaseHelper.addSessionAttendance(s);
                }
                getSessionAttendanceData();

                String selected = String.valueOf(rAdapter.getSelectedTrainees().size());
                btnGetSelected.setText("("+selected+") selected");
                // also try uploading
                TrainingDataSync upload = new TrainingDataSync(getContext());
                upload.uploadSessionAttendance(session.getTrainingId());
                upload.startUploadAttendanceTask();
            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void toggleSelection(int position) {

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
            //swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.action_delete:
//                    // delete all the selected messages
//                    deleteMessages();
//                    mode.finish();
//                    return true;
//
//                default:
//                    return false;
//            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    // deleting the messages from recycler view
    private void deleteMessages() {

    }


    private void getSessionAttendanceData() {
        swipeRefreshLayout.setRefreshing(true);
        try{
            new TrainingDataSync(getContext()).getSessionAttendance(session.getTrainingId());
        }catch (Exception e){}
        sessionAttendances.clear();

        try {
            List<SessionAttendance> sessions = new ArrayList<>();
            // sessions = databaseHelper.getSessionAttendancesBySessionId(session.getId());
            sessions = databaseHelper.getNotAttendedSessionAttendancesBySessionId(session.getId(), "0");
            for (SessionAttendance s: sessions){
                sessionAttendances.add(s);
            }
            rAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }catch (Exception e){}
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (session!=null){
            String topic = new DatabaseHelper(getContext()).getSessionTopicById(session.getSessionTopicId().toString()).getName();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Attendance for "+ topic);
        }
        setHasOptionsMenu(false);
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

}
