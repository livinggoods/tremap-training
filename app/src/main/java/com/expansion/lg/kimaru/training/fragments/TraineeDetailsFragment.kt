package com.expansion.lg.kimaru.training.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.objs.SessionAttendance
import com.expansion.lg.kimaru.training.objs.TraineeStatus
import com.expansion.lg.kimaru.training.objs.TrainingTrainee
import com.expansion.lg.kimaru.training.utils.DisplayDate
import com.expansion.lg.kimaru.training.utils.SessionManagement
import com.gadiness.kimarudg.ui.alerts.SweetAlert.SweetAlertDialog

import java.util.ArrayList
import java.util.Calendar

/**
 * Created by kimaru on 3/14/18.
 */

class TraineeDetailsFragment : Fragment() {

    internal var trainingTrainee: TrainingTrainee? = null
    internal var dropCandidate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_trainee_details, container, false)
        val backFragment = TrainingsTraineesFragment()
        backFragment.training = DatabaseHelper(context).getTrainingById(trainingTrainee!!.trainingId)
        MainActivity.backFragment = backFragment
        var traineeName = ""
        var traineePhone = ""
        var traineeGender = ""
        var traineeDob: Long? = 0L
        var sessions: List<SessionAttendance> = ArrayList()
        val db = DatabaseHelper(context)
        try {
            traineeName = trainingTrainee!!.registration.getString("name")
            traineePhone = trainingTrainee!!.registration.getString("phone")
            traineeGender = trainingTrainee!!.registration.getString("gender")
            traineeDob = trainingTrainee!!.registration.getLong("dob")
        } catch (e: Exception) {
        }

        val traineeNameView = view.findViewById<TextView>(R.id.traineeName)
        traineeNameView.text = traineeName
        val traineePhoneView = view.findViewById<TextView>(R.id.traineePhone)
        traineePhoneView.text = traineePhone
        val traineeGenderView = view.findViewById<TextView>(R.id.traineeGender)
        traineeGenderView.text = traineeGender

        val year: Int
        val month: Int
        val day: Int
        year = Integer.valueOf(DisplayDate(traineeDob).yearOnly())
        month = Integer.valueOf(DisplayDate(traineeDob).monthOnly())
        day = Integer.valueOf(DisplayDate(traineeDob).dayOnly())
        val traineeAgeView = view.findViewById<TextView>(R.id.traineeAge)
        traineeAgeView.text = getAge(year, month, day)

        //Attendance
        sessions = db.getSessionAttendancesByTraineeId(trainingTrainee!!.id)
        val traineeSessionsAttended = view.findViewById<TextView>(R.id.sessionsAttended)
        traineeSessionsAttended.text = sessions.size.toString()

        val traineeClassView = view.findViewById<TextView>(R.id.traineeClass)
        traineeClassView.text = "Class " + db.getTrainingClassById(
                trainingTrainee!!.classId!!.toString())!!.className


        // add statuses
        val linearLayout = view.findViewById<View>(R.id.traineeStatusActions) as LinearLayout
        val lParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )

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

        val discontinue = view.findViewById<Button>(R.id.drop)
        discontinue.setOnClickListener { showWarnig() }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (trainingTrainee != null) {
            try {
                (activity as AppCompatActivity).supportActionBar!!.title = trainingTrainee!!.registration.getString("name")
            } catch (e: Exception) {
                (activity as AppCompatActivity).supportActionBar!!.title = "Trainee Profile"
            }

        }
    }

    fun showWarnig() {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to drop the trainee!")
                .setCancelText("No,cancel pls!")
                .setConfirmText("Yes,drop it!")
                .showCancelButton(true)
                .setCancelClickListener { sDialog -> sDialog.cancel() }
                .setConfirmClickListener { sDialog ->
                    dropCandidate = true
                    sDialog.dismissWithAnimation()
                    showDialog()
                }
                .show()
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add a reason")
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val reason = EditText(context)
        reason.hint = "Why?"
        layout.addView(reason)
        builder.setView(layout)

        // Set up the buttons
        builder.setPositiveButton("OK") { dialog, which ->
            val why = reason.text.toString()
            trainingTrainee!!.comment = why
            trainingTrainee!!.traineeStatus = 2
            DatabaseHelper(context).addTrainingTrainee(trainingTrainee)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.set(year, month, day)

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val ageInt = age

        return ageInt.toString()
    }

    companion object {

        fun newInstance(): TraineeDetailsFragment {
            val fragment = TraineeDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
