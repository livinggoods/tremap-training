package com.expansion.lg.kimaru.training.fragments

/**
 * Created by kimaru on 3/11/17.
 */

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.adapters.SessionAttendanceListAdapter
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.SessionAttendance
import com.expansion.lg.kimaru.training.objs.TrainingSession
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration

import java.util.ArrayList

//import com.expansion.lg.kimaru.training.swipehelpers.TrainingRecyclerItemTouchHelper;

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SessionAttendanceFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
//AppCompatActivity implements TrainingRecyclerItemTouchHelper.RecyclerItemTouchHelperListener
class SessionAttendanceFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var rAdapter: SessionAttendanceListAdapter? = null
    internal var sessionAttendances: MutableList<SessionAttendance> = ArrayList()
    internal var fab: FloatingActionButton? = null
    internal var btnGetSelected: Button
    internal var databaseHelper: DatabaseHelper

    internal var session: TrainingSession? = null

    private val isConnected: Boolean
        get() = ConnectivityReceiver.isConnected

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val backFragment = TrainingsSessionsFragment()
        backFragment.training = DatabaseHelper(context).getTrainingById(session!!.trainingId)
        MainActivity.backFragment = backFragment
        databaseHelper = DatabaseHelper(context)

        val v: View
        v = inflater!!.inflate(R.layout.fragment_attendance, container, false)
        recyclerView = v.findViewById(R.id.list)
        btnGetSelected = v.findViewById(R.id.btnGetSelected)
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout!!.setOnRefreshListener { getSessionAttendanceData() }
        rAdapter = SessionAttendanceListAdapter(this.context, sessionAttendances, object : SessionAttendanceListAdapter.SessionAttendanceListAdapterListener {
            override fun onRowLongClicked(position: Int) {

            }

            override fun onTraineeSelected() {
                val selected = rAdapter!!.selectedTrainees.size.toString()
                btnGetSelected.text = "($selected) selected"
            }
        })
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = rAdapter
        swipeRefreshLayout!!.post { getSessionAttendanceData() }

        //        btnGetSelected.setVisibility(View.GONE);
        btnGetSelected.setOnClickListener {
            // save the seelected to DB
            for (s in rAdapter!!.selectedTrainees) {
                databaseHelper.addSessionAttendance(s)
            }
            getSessionAttendanceData()

            val selected = rAdapter!!.selectedTrainees.size.toString()
            btnGetSelected.text = "($selected) selected"
            // also try uploading
            val upload = TrainingDataSync(context)
            upload.uploadSessionAttendance(session!!.trainingId)
            upload.startUploadAttendanceTask()
        }
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
            //swipeRefreshLayout.setEnabled(false);
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

        }
    }

    // deleting the messages from recycler view
    private fun deleteMessages() {

    }


    private fun getSessionAttendanceData() {
        swipeRefreshLayout!!.isRefreshing = true
        try {
            TrainingDataSync(context).getSessionAttendance(session!!.trainingId)
        } catch (e: Exception) {
        }

        sessionAttendances.clear()

        try {
            var sessions: List<SessionAttendance> = ArrayList()
            // sessions = databaseHelper.getSessionAttendancesBySessionId(session.getId());
            sessions = databaseHelper.getNotAttendedSessionAttendancesBySessionId(session!!.id, "0")
            for (s in sessions) {
                sessionAttendances.add(s)
            }
            rAdapter!!.notifyDataSetChanged()
            swipeRefreshLayout!!.isRefreshing = false
        } catch (e: Exception) {
        }

        swipeRefreshLayout!!.isRefreshing = false
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (session != null) {
            val topic = DatabaseHelper(context).getSessionTopicById(session!!.sessionTopicId!!.toString())!!.name
            (activity as AppCompatActivity).supportActionBar!!.title = "Attendance for $topic"
        }
        setHasOptionsMenu(false)
    }

}
