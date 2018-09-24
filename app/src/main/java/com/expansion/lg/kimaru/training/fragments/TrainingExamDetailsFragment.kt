package com.expansion.lg.kimaru.training.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.objs.SessionAttendance
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.objs.TrainingExam
import com.expansion.lg.kimaru.training.utils.DisplayDate
import com.gadiness.kimarudg.ui.alerts.SweetAlert.SweetAlertDialog

import org.json.JSONObject

import java.util.ArrayList
import java.util.Calendar

/**
 * Created by kimaru on 3/14/18.
 */

class TrainingExamDetailsFragment : Fragment() {

    internal var exam: TrainingExam? = null
    internal var examTitle: TextView
    internal var examCode: TextView
    internal var totalSubmitted: TextView
    internal var totalUnsubmitted: TextView
    internal var totalTrainees: TextView
    internal var passmark: TextView
    internal var examStatus: TextView
    internal var summary: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_exam_details, container, false)
        //        View view = inflater.inflate(R.layout.fragment_exam_details, container, false);
        val backFragment = TrainingsExamsFragment()
        backFragment.training = DatabaseHelper(context).getTrainingById(exam!!.trainingId)
        MainActivity.backFragment = backFragment

        val db = DatabaseHelper(context)
        //trainees, get them by training
        val totalRegisteredTrainees = db.getTrainingTraineesByTrainingId(exam!!.trainingId).size
        val totalSubmittedResults = db.getTrainingExamResultByExam(exam!!.id!!.toString()).size
        val totalWaitingResults = totalRegisteredTrainees - totalSubmittedResults

        examTitle = view.findViewById(R.id.examTitle)
        examTitle.text = exam!!.title
        examCode = view.findViewById(R.id.examCode)
        examCode.text = exam!!.examCode
        totalSubmitted = view.findViewById(R.id.totalSubmitted)
        totalSubmitted.text = totalSubmittedResults.toString()
        totalUnsubmitted = view.findViewById(R.id.totalUnsubmitted)
        totalUnsubmitted.text = totalWaitingResults.toString()
        totalTrainees = view.findViewById(R.id.totalTrainees)
        totalTrainees.text = totalRegisteredTrainees.toString()
        passmark = view.findViewById(R.id.passmark)
        passmark.text = exam!!.passmark!!.toString()
        examStatus = view.findViewById(R.id.exam_status)
        summary = view.findViewById(R.id.card_summary)
        summary.setOnClickListener { NavigateToSubmissions() }
        val examJson = exam!!.getCloudExamJson()
        if (!examJson.isNull("exam_status")) {
            try {
                examStatus.text = examJson.getJSONObject("exam_status").getString("title")
            } catch (e: Exception) {
            }

        }

        if (!examJson.isNull("unlock_code")) {
            try {
                examCode.setText(String.format("CODE: %s", examJson.getString("unlock_code")))
            } catch (e: Exception) {
                examCode.text = "CODE : - - - -"
            }

        }

        if (examJson.isNull("passmark")) {
            try {
                passmark.text = examJson.getString("passmark")
            } catch (e: Exception) {
            }

        }


        val sessions = ArrayList<SessionAttendance>()


        // add statuses
        val linearLayout = view.findViewById<View>(R.id.traineeStatusActions) as LinearLayout
        val lParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (exam != null) {
            try {
                (activity as AppCompatActivity).supportActionBar!!.title = exam!!.title
            } catch (e: Exception) {
                (activity as AppCompatActivity).supportActionBar!!.title = "Exam Details"
            }

        }
    }

    fun NavigateToSubmissions() {
        val training = DatabaseHelper(context).getTrainingById(exam!!.trainingId)
        val fragment = TrainingsExamSubmissionsFragment()
        fragment.training = training
        fragment.exam = exam
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    companion object {

        fun newInstance(): TrainingExamDetailsFragment {
            val fragment = TrainingExamDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
