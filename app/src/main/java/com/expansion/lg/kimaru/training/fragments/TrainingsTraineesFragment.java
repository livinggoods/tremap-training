package com.expansion.lg.kimaru.training.fragments;

/**
 * Created by kimaru on 3/11/17.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.adapters.TraineeListAdapter;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingClass;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.swipehelpers.TraineeRecyclerItemTouchHelper;
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

import java.util.ArrayList;
import java.util.List;

public class TrainingsTraineesFragment extends Fragment implements TraineeRecyclerItemTouchHelper.
        RecyclerItemTouchHelperListener, SearchView.OnQueryTextListener{

    private OnFragmentInteractionListener mListener;
    TextView textshow;
    FloatingActionButton fab;

    private List<TrainingTrainee> trainees = new ArrayList<>();
    private RecyclerView recyclerView;
    private TraineeListAdapter rAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    private FrameLayout frameLayout;

    private EditText searchEditText;
    private SearchView searchView;
    int actionId;

    SessionManagement sessionManagement;
    Training training = null;
    TrainingClass trainingClass = null;


    public TrainingsTraineesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setSubmitButtonEnabled(false);

        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        onEditorAction(actionId);


    }

    public boolean onEditorAction(int actionId){
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideSoftKeyboard();
            return true;
        }
        return false;

    }


    public void hideSoftKeyboard() {
        //searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
                getTrainings();
            }
        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new TraineeRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);
        rAdapter = new TraineeListAdapter(this.getContext(), trainees, new TraineeListAdapter.TrainingTraineeListAdapterListener() {
            @Override
            public void onIconClicked(int position) {
                TrainingTrainee trainee = trainees.get(position);
                onMessageRowClicked(position);
            }

            @Override
            public void onIconImportantClicked(int position) {
                onMessageRowClicked(position);
            }

            @Override
            public void onMessageRowClicked(int position) {
                TrainingTrainee trainee = rAdapter.trainees.get(position); // trainees.get(position);
                // Show Trainee Profile
                TraineeDetailsFragment traineeDetailsFragment = new TraineeDetailsFragment();
                traineeDetailsFragment.trainingTrainee = trainee;
                Fragment fragment = traineeDetailsFragment;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }

            @Override
            public void onRowLongClicked(int position) {
                TrainingTrainee trainee = trainees.get(position);            }

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(rAdapter);
        swipeRefreshLayout.post(
                new Runnable(){
                    @Override
                    public void run(){
                        getTrainings();
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String string) {
       rAdapter.getFilter().filter(string);
        //MainActivity.this.adapter.getFilter().filter(cs);
        return false;
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



    private void getTrainings() {
        swipeRefreshLayout.setRefreshing(true);
        trainees.clear();
        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            List<TrainingTrainee> traineeList = new ArrayList<>();
            if (training != null){
                traineeList = databaseHelper.getTrainingTraineesByTrainingId(training.getId());
            }
            if (trainingClass != null){
                traineeList = databaseHelper.getTrainingTraineesByClassId(String.valueOf(trainingClass.getId()));
            }
            for (TrainingTrainee trainee:traineeList){
                trainee.setColor(getRandomMaterialColor("400"));
                trainees.add(trainee);
            }
            rAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        } catch (Exception error){
            Toast.makeText(getContext(), "No Trainings found", Toast.LENGTH_SHORT).show();
            textshow.setText(error.getMessage());
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (training != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(training.getTrainingName() + " Trainees");
        }
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TraineeListAdapter.ListHolder){
            String name ="";
            try{
                name = trainees.get(viewHolder.getAdapterPosition()).getRegistration().getString("name");
            }catch (Exception e){}

            //incase of Undo
            final TrainingTrainee deletedTraining = trainees.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            //remove the item from view
            rAdapter.removeData(viewHolder.getAdapterPosition());

            //Add Snackbar info plus an action
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(frameLayout, name + " removed from the list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    rAdapter.restoreItem(deletedTraining, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
