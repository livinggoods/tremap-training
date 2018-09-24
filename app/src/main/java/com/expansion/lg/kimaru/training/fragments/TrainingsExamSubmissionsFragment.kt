package com.expansion.lg.kimaru.training.fragments

/**
 * Created by kimaru on 3/11/17.
 */

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
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
import com.expansion.lg.kimaru.training.adapters.TraineesExamSubmissionListAdapter
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.objs.TrainingClass
import com.expansion.lg.kimaru.training.objs.TrainingExam
import com.expansion.lg.kimaru.training.objs.TrainingExamResult
import com.expansion.lg.kimaru.training.objs.TrainingTrainee
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.swipehelpers.TraineeRecyclerItemTouchHelper
import com.expansion.lg.kimaru.training.utils.DividerItemDecoration
import com.expansion.lg.kimaru.training.utils.SessionManagement

import java.util.ArrayList

class TrainingsExamSubmissionsFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    internal var textshow: TextView
    internal var fab: FloatingActionButton

    private val results = ArrayList<TrainingTrainee>()
    private var recyclerView: RecyclerView? = null
    private var rAdapter: TraineesExamSubmissionListAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var actionMode: ActionMode? = null

    private var frameLayout: FrameLayout? = null

    internal var sessionManagement: SessionManagement
    internal var training: Training? = null
    internal var trainingClass: TrainingClass? = null
    internal var exam: TrainingExam? = null
    internal var databaseHelper: DatabaseHelper

    private val isConnected: Boolean
        get() = ConnectivityReceiver.isConnected

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        databaseHelper = DatabaseHelper(context)
        if (training != null) {
            val backFragment = TrainingViewFragment()
            backFragment.training = training
            MainActivity.backFragment = backFragment
        }
        if (trainingClass != null) {
            val backFragment = TrainingClassesFragment()
            backFragment.training = DatabaseHelper(context).getTrainingById(trainingClass!!.trainingId)
            MainActivity.backFragment = backFragment
        }

        val v: View
        v = inflater!!.inflate(R.layout.fragment_recycler, container, false)
        textshow = v.findViewById<View>(R.id.textShow) as TextView
        sessionManagement = SessionManagement(context)
        fab = v.findViewById<View>(R.id.fab) as FloatingActionButton
        frameLayout = v.findViewById(R.id.frame_layout)
        recyclerView = v.findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshLayout = v.findViewById<View>(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { getSubmisions() }
        rAdapter = TraineesExamSubmissionListAdapter(this.context, results, object : TraineesExamSubmissionListAdapter.TraineesExamSubmissionListAdapterListener {
            override fun onIconClicked(position: Int) {
                onMessageRowClicked(position)
            }

            override fun onIconImportantClicked(position: Int) {
                onMessageRowClicked(position)
            }

            override fun onMessageRowClicked(position: Int) {
                // check if there are submissions
                try {
                    val submittedQuestions = databaseHelper
                            .getTrainingExamResultByTrainee(results[position].registrationId)
                    if (submittedQuestions.size <= 0) {
                        Toast.makeText(context, "No submission found", Toast.LENGTH_SHORT).show()
                    } else {
                        val fragment = TrainingExamQuestionSubmissionFragment()
                        fragment.trainee = results[position]
                        fragment.exam = exam
                        val fragmentManager = activity.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        fragmentTransaction.replace(R.id.frame, fragment)
                        fragmentTransaction.commitAllowingStateLoss()
                    }
                } catch (e: Exception) {
                }

            }

            override fun onRowLongClicked(position: Int) {}

        }, exam)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = rAdapter
        swipeRefreshLayout!!.post { getSubmisions() }


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


    private fun getSubmisions() {
        swipeRefreshLayout!!.isRefreshing = true
        // Get results from the cloud
        try {
            TrainingDataSync(context).getTrainingExamResults(exam!!.id!!.toString())
        } catch (e: Exception) {
        }

        results.clear()
        try {


            var trainees: List<TrainingTrainee> = ArrayList()
            trainees = databaseHelper.getTrainingTraineesByTrainingId(training!!.id)

            for (r in trainees) {
                r.color = getRandomMaterialColor("400")
                results.add(r)
            }
            rAdapter!!.notifyDataSetChanged()
            swipeRefreshLayout!!.isRefreshing = false

        } catch (error: Exception) {
            Toast.makeText(context, "No submissions found", Toast.LENGTH_SHORT).show()
            textshow.text = error.message
        }

        swipeRefreshLayout!!.isRefreshing = false
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (training != null) {
            (activity as AppCompatActivity).supportActionBar!!.title = training!!.trainingName!! + " Submissions"
        }
    }


}
