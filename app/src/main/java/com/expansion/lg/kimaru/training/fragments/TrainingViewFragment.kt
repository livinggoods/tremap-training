package com.expansion.lg.kimaru.training.fragments


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.text.SpannableString
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.objs.TrainingTrainee
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.utils.charts.ChartItem
import com.expansion.lg.kimaru.training.utils.charts.LineChartItem
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

import java.util.ArrayList

import butterknife.ButterKnife

/**
 * Created by kimaru on 3/30/17.
 */
class TrainingViewFragment : Fragment(), View.OnClickListener {

    internal var training: Training? = null

    internal var trainingName: TextView
    internal var trainingTrainees: TextView
    internal var trainingClasses: TextView
    internal var trainingSessions: TextView
    internal var trainingAttendance: TextView
    internal var trainingLeadTrainer: TextView
    internal var traineesButton: LinearLayout
    internal var sessionsButton: LinearLayout
    internal var classesButton: LinearLayout
    internal var trainingExamsView: LinearLayout
    internal var trainingImage: ImageView

    private val isConnected: Boolean
        get() = ConnectivityReceiver.isConnected


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.training_view_fragment, container, false)
        MainActivity.backFragment = TrainingsFragment()
        trainingName = v.findViewById(R.id.trainingName)
        trainingTrainees = v.findViewById(R.id.trainingTrainees)
        trainingClasses = v.findViewById(R.id.trainingClasses)
        trainingSessions = v.findViewById(R.id.trainingSessions)
        trainingAttendance = v.findViewById(R.id.trainingAttendance)
        trainingLeadTrainer = v.findViewById(R.id.trainingLeadTrainer)
        trainingImage = v.findViewById(R.id.trainingImage)
        trainingExamsView = v.findViewById(R.id.trainingExamsView)

        Log.d("TREMAP", "{}{}{}{}{}{}{}{}{")
        Log.d("TREMAP", DatabaseHelper.CREATE_TABLE_TRAINING_EXAM)
        Log.d("TREMAP", "{}{}{}{}{}{}{}{}{")


        traineesButton = v.findViewById(R.id.traineesButton)
        sessionsButton = v.findViewById(R.id.sessionsButton)
        classesButton = v.findViewById(R.id.classesButton)
        getTrainingDetailsFromApi()
        getTrainingExamsFromApi()

        val db = DatabaseHelper(context)
        trainingName.text = training!!.trainingName
        trainingTrainees.text = db.getTrainingTraineesByTrainingId(training!!.id).size.toString()
        val m = db.getTrainingClassByTrainingId(training!!.id).size
        trainingClasses.text = db.getTrainingClassByTrainingId(training!!.id).size.toString()
        trainingSessions.text = db.getTrainingSessionsByTrainingId(training!!.id).size.toString()
        Glide.with(this)
                .load(R.drawable.lg_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(trainingImage)



        traineesButton.setOnClickListener {
            val fragment: Fragment
            val fragmentTransaction: FragmentTransaction
            val trainingsTraineesFragment = TrainingsTraineesFragment()
            trainingsTraineesFragment.training = training
            fragment = trainingsTraineesFragment
            fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }

        trainingExamsView.setOnClickListener {
            val fragment: Fragment
            val fragmentTransaction: FragmentTransaction
            val trainingExamsFragment = TrainingsExamsFragment()
            trainingExamsFragment.training = training
            fragment = trainingExamsFragment
            fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }


        sessionsButton.setOnClickListener {
            val fragment: Fragment
            val fragmentTransaction: FragmentTransaction

            val trainingsSessionsFragment = TrainingsSessionsFragment()
            trainingsSessionsFragment.training = training
            fragment = trainingsSessionsFragment
            fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
        classesButton.setOnClickListener {
            val fragment: Fragment
            val fragmentTransaction: FragmentTransaction

            val trainingClassesFragment = TrainingClassesFragment()
            trainingClassesFragment.training = training
            fragment = trainingClassesFragment
            fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (training != null) {
            (activity as AppCompatActivity).supportActionBar!!.title = training!!.trainingName
        }
    }

    override fun onClick(view: View) {
        val fragment: Fragment
        val fragmentTransaction: FragmentTransaction
        val fragmentManager: FragmentManager
        when (view.id) {
            R.id.action_delete -> {
            }
        }
    }

    fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", this.javaClass.getPackage().getName())
    }

    @ColorInt
    private fun getRatingColor(averageVote: Double): Int {
        return ContextCompat.getColor(context, R.color.vote_perfect)
    }

    private fun getTrainingDetailsFromApi() {
        try {
            TrainingDataSync(context).getTrainingDetailsJson(training!!.id)
        } catch (e: Exception) {
        }

    }

    private fun getTrainingExamsFromApi() {
        try {
            TrainingDataSync(context).getTrainingExams(training!!.id)
        } catch (e: Exception) {
        }

    }

}
