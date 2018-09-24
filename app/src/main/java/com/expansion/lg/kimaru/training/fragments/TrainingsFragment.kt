package com.expansion.lg.kimaru.training.fragments

/**
 * Created by kimaru on 3/11/17.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.adapters.TrainingListAdapter
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.swipehelpers.TrainingRecyclerItemTouchHelper
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration
import com.expansion.lg.kimaru.training.database.DatabaseHelper
//import com.expansion.lg.kimaru.training.swipehelpers.TrainingRecyclerItemTouchHelper;
import com.expansion.lg.kimaru.training.utils.SessionManagement

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TrainingsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
//AppCompatActivity implements TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener
class TrainingsFragment : Fragment(), TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private var mListener: OnFragmentInteractionListener? = null
    internal var textshow: TextView
    internal var fab: FloatingActionButton

    private val trainings = ArrayList<Training>()
    private var recyclerView: RecyclerView? = null
    private var rAdapter: TrainingListAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var actionMode: ActionMode? = null

    private var frameLayout: FrameLayout? = null

    internal var sessionManagement: SessionManagement

    private val isConnected: Boolean
        get() = ConnectivityReceiver.isConnected

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        MainActivity.backFragment = HomeFragment()

        val v: View
        v = inflater!!.inflate(R.layout.fragment_recycler, container, false)
        textshow = v.findViewById<View>(R.id.textShow) as TextView
        sessionManagement = SessionManagement(context)
        fab = v.findViewById<View>(R.id.fab) as FloatingActionButton
        frameLayout = v.findViewById(R.id.frame_layout)
        recyclerView = v.findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshLayout = v.findViewById<View>(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { getTrainings() }
        val itemTouchHelperCallback = TrainingRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
        val itemTouchHelperCallback1 = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView)
        rAdapter = TrainingListAdapter(this.context, trainings, object : TrainingListAdapter.TrainingListAdapterListener {
            override fun onIconClicked(position: Int) {
                val training = trainings[position]
                val trainingViewFragment = TrainingViewFragment()
                trainingViewFragment.training = training
                val fragmentManager = activity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                fragmentTransaction.replace(R.id.frame, trainingViewFragment)
                fragmentTransaction.commitAllowingStateLoss()

            }

            override fun onIconImportantClicked(position: Int) {
                onIconClicked(position)
            }

            override fun onMessageRowClicked(position: Int) {
                onIconClicked(position)
            }

            override fun onRowLongClicked(position: Int) {
                onIconClicked(position)
            }

        })
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = rAdapter
        swipeRefreshLayout!!.post { getTrainings() }


        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    private fun toggleSelection(position: Int) {
        rAdapter!!.toggleSelection(position)
        val count = rAdapter!!.selectedItemCount

        if (count == 0) {
            actionMode!!.finish()
        } else {
            actionMode!!.title = count.toString()
            actionMode!!.invalidate()
        }
    }

    /*
    *  Choose a random
    *  Color
     */
    private fun getRandomMaterialColor(typeColor: String): Int {
        var returnColor = Color.GRAY
        val arrayId = resources.getIdentifier("mdcolor_$typeColor", "array", context.packageName)

        if (arrayId != 0) {
            val colors = resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }


    private inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_action_mode, menu)

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout!!.isEnabled = false
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
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
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            rAdapter!!.clearSelections()
            swipeRefreshLayout!!.isEnabled = true
            actionMode = null
            recyclerView!!.post {
                rAdapter!!.resetAnimationIndex()
                // mAdapter.notifyDataSetChanged();
            }
        }
    }

    // deleting the messages from recycler view
    private fun deleteMessages() {
        rAdapter!!.resetAnimationIndex()
        val selectedItemPositions = rAdapter!!.selectedItems
        for (i in selectedItemPositions.indices.reversed()) {
            rAdapter!!.removeData(selectedItemPositions[i])
        }
        rAdapter!!.notifyDataSetChanged()
    }


    private fun getTrainings() {
        swipeRefreshLayout!!.isRefreshing = true
        trainings.clear()
        try {

            try {
                TrainingDataSync(context).pollNewTrainings()
            } catch (e: Exception) {
            }

            val databaseHelper = DatabaseHelper(context)
            var trainingList: List<Training> = ArrayList()

            trainingList = databaseHelper.getTrainingsByCountry(sessionManagement
                    .userDetails[SessionManagement.KEY_USER_COUNTRY])
            trainingList = databaseHelper.trainings
            for (training in trainingList) {
                training.color = getRandomMaterialColor("400")
                trainings.add(training)
            }
            rAdapter!!.notifyDataSetChanged()
            swipeRefreshLayout!!.isRefreshing = false
        } catch (error: Exception) {
            Toast.makeText(context, "No Trainings found", Toast.LENGTH_SHORT).show()
            textshow.text = "Trainings"
        }

        swipeRefreshLayout!!.isRefreshing = false
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Trainings"
        setHasOptionsMenu(false)
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is TrainingListAdapter.ListHolder) {
            val name = trainings[viewHolder.getAdapterPosition()].trainingName
            //incase of Undo
            val deletedTraining = trainings[viewHolder.getAdapterPosition()]
            val deletedIndex = viewHolder.getAdapterPosition()

            //remove the item from view
            rAdapter!!.removeData(viewHolder.getAdapterPosition())

            //Add Snackbar info plus an action
            // showing snack bar with Undo option
            val snackbar = Snackbar
                    .make(frameLayout!!, name!! + " removed from cart!", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO") {
                // undo is selected, restore the deleted item
                rAdapter!!.restoreItem(deletedTraining, deletedIndex)
            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
    }
}
