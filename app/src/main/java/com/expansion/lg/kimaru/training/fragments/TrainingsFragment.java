package com.expansion.lg.kimaru.training.fragments;

/**
 * Created by kimaru on 3/11/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.activity.MainActivity;
import com.expansion.lg.kimaru.training.adapters.TrainingListAdapter;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.swipehelpers.TrainingRecyclerItemTouchHelper;
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
//import TrainingRecyclerItemTouchHelper;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrainingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
//AppCompatActivity implements TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener
public class TrainingsFragment extends Fragment implements TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private OnFragmentInteractionListener mListener;
    TextView textshow;
    FloatingActionButton fab;

    private List<Training> trainings = new ArrayList<>();
    private RecyclerView recyclerView;
    private TrainingListAdapter rAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    private LinearLayout frameLayout;

    SessionManagement sessionManagement;

    Spinner spFilterByCountry;
    SearchView searchView;

    String searchQuery = "";

    public TrainingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        MainActivity.backFragment = new HomeFragment();

        View v;
        v = inflater.inflate(R.layout.fragment_trainings, container, false);
        textshow = (TextView) v.findViewById(R.id.textShow);
        textshow.setVisibility(View.GONE);
        sessionManagement = new SessionManagement(getContext());

        spFilterByCountry = (Spinner) v.findViewById(R.id.sp_filter_by_country);
        spFilterByCountry.setSelection(sessionManagement.getUserFilterByCountry());
        spFilterByCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sessionManagement.setUserFilterByCountry(position);
                getTrainings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        searchView = (SearchView) v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchTraining(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTraining(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchTraining("");
                return false;
            }
        });


        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        frameLayout = v.findViewById(R.id.frame_layout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrainings();
            }
        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new TrainingRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
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
        rAdapter = new TrainingListAdapter(this.getContext(), trainings, new TrainingListAdapter.TrainingListAdapterListener() {
            @Override
            public void onIconClicked(int position) {
                
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                Training training = trainings.get(position);
                TrainingViewFragment trainingViewFragment = new TrainingViewFragment();
                trainingViewFragment.training = training;
                Fragment fragment = trainingViewFragment;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }

            @Override
            public void onIconImportantClicked(int position) {
                onIconClicked(position);
            }

            @Override
            public void onMessageRowClicked(int position) {
                onIconClicked(position);
            }

            @Override
            public void onRowLongClicked(int position) {
                onIconClicked(position);
            }

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(rAdapter);
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
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
        rAdapter.toggleSelection(position);
        int count = rAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void searchTraining(String query) {
        this.searchQuery = query;
        getTrainings();
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
        textshow.setVisibility(View.GONE);
        trainings.clear();

        String country = spFilterByCountry.getSelectedItem().toString();

        try {

            try {
                new TrainingDataSync(getContext()).pollNewTrainings();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            List<Training> trainingList;


            if (country.equalsIgnoreCase("any")) {
                trainingList = databaseHelper.getTrainings();
            } else {
                trainingList = databaseHelper.getTrainingsByCountry(country);
            }

            for (Training training : trainingList) {
                training.setColor(getRandomMaterialColor("400"));
                if (training.getTrainingName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    trainings.add(training);
                }
            }
            rAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception error) {
            Toast.makeText(getContext(), "No Trainings found", Toast.LENGTH_SHORT).show();
            textshow.setText("Trainings");
            textshow.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Trainings");
        setHasOptionsMenu(false);
    }

    private boolean isConnected() {
        return ConnectivityReceiver.isConnected();
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TrainingListAdapter.ListHolder) {

        }
    }
}
