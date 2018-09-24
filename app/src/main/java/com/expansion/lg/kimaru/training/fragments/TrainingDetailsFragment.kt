package com.expansion.lg.kimaru.training.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.objs.TrainingTrainee
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.utils.charts.BarChartItem
import com.expansion.lg.kimaru.training.utils.charts.ChartItem
import com.expansion.lg.kimaru.training.utils.charts.LineChartItem
import com.expansion.lg.kimaru.training.utils.charts.MyMarkerView
import com.expansion.lg.kimaru.training.utils.charts.PieChartItem
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils


import org.json.JSONObject

import java.util.ArrayList

import butterknife.ButterKnife
import butterknife.internal.DebouncingOnClickListener

/**
 * Created by kimaru on 3/30/17.
 */
class TrainingDetailsFragment : Fragment(), View.OnClickListener {

    internal var training: Training? = null

    internal var trainingImagePoster: ImageView
    internal var trainingNameText: TextView
    internal var trainingClassSize: TextView
    internal var trainingDates: TextView
    internal var trainingComment: TextView? = null
    internal var cardTrainingDetail: CardView
    internal var cardMovieOverview: CardView
    internal var mChartTrainees: PieChart
    internal var mChartSession: PieChart
    internal var attendanceChart: LineChart
    internal var mTfLight: Typeface
    internal var mTfRegular: Typeface
    internal var traineesButton: Button
    internal var sessionsButton: Button
    internal var classesButton: Button

    private val isConnected: Boolean
        get() = ConnectivityReceiver.isConnected


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.training_details_view, container, false)
        ButterKnife.bind(this, v)
        trainingImagePoster = v.findViewById(R.id.image_training_detail_poster)
        trainingNameText = v.findViewById(R.id.text_training_name)
        trainingClassSize = v.findViewById(R.id.text_movie_user_rating)
        trainingDates = v.findViewById(R.id.text_movie_release_date)
        //trainingComment = v.findViewById(R.id.text_training_comment);
        cardTrainingDetail = v.findViewById(R.id.card_training_detail)
        cardMovieOverview = v.findViewById(R.id.card_movie_overview)
        traineesButton = v.findViewById(R.id.trainees)
        sessionsButton = v.findViewById(R.id.sessions)
        classesButton = v.findViewById(R.id.classes)
        //cardMovieVideos = v.findViewById(R.id.card_movie_videos);
        //        movieVideos = v.findViewById(R.id.movie_videos);
        getTrainingDetailsFromApi()

        //cardMovieReviews = v.findViewById(R.id.card_movie_reviews);
        //movieReviews = v.findViewById(R.id.movie_reviews);
        mChartTrainees = v.findViewById(R.id.trainees_chart)
        mChartSession = v.findViewById(R.id.session_chart)
        attendanceChart = v.findViewById(R.id.chart_attendance)
        mTfLight = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")
        mTfRegular = Typeface.createFromAsset(context.assets, "OpenSans-Regular.ttf")
        initViews()
        setupCardsElevation()
        setUpCharts()


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

    private fun initViews() {
        Glide.with(this)
                .load(R.drawable.lg_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(trainingImagePoster)
        if (training != null) {
            trainingNameText.text = training!!.trainingName
            trainingClassSize.text = DatabaseHelper(context).getTrainingTraineesByTrainingId(training!!.id).size.toString()
            trainingClassSize.setTextColor(getRatingColor(9.0))
            val releaseDate = String.format(getString(R.string.training_date),
                    training!!.dateCommenced.toString(), training!!.clientTime.toString())
            trainingDates.setText(releaseDate)
            //trainingComment.setText(training.getComment());
        }

    }

    fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", this.javaClass.getPackage().getName())
    }

    @ColorInt
    private fun getRatingColor(averageVote: Double): Int {
        //        if (averageVote >= VOTE_PERFECT) {
        //            return ContextCompat.getColor(getContext(), R.color.vote_perfect);
        //        } else if (averageVote >= VOTE_GOOD) {
        //            return ContextCompat.getColor(getContext(), R.color.vote_good);
        //        } else if (averageVote >= VOTE_NORMAL) {
        //            return ContextCompat.getColor(getContext(), R.color.vote_normal);
        //        } else {
        //            return ContextCompat.getColor(getContext(), R.color.vote_bad);
        //        }
        return ContextCompat.getColor(context, R.color.vote_perfect)
    }

    private fun setupCardsElevation() {
        setupCardElevation(cardTrainingDetail)
        //setupCardElevation(cardMovieVideos);
        setupCardElevation(cardMovieOverview)
        //setupCardElevation(cardMovieReviews);
    }

    private fun setupCardElevation(view: View) {
        ViewCompat.setElevation(view,
                convertDpToPixel(resources.getInteger(R.integer.training_detail_content_elevation_in_dp).toFloat()))
    }

    fun convertDpToPixel(dp: Float): Float {
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private fun generateDataPie(): PieData {

        val entries = ArrayList<PieEntry>()

        val databaseHelper = DatabaseHelper(context)
        //trainees
        var traineeList: List<TrainingTrainee> = ArrayList()
        traineeList = databaseHelper.getTrainingTraineesByTrainingId(training!!.id)
        var male = 0
        var female = 0

        for (trainee in traineeList) {
            //JSONObj
            try {
                val gender = trainee.registration.getString("gender")
                if (gender.equals("male", ignoreCase = true)) {
                    male += 1
                } else {
                    female += 1
                }
            } catch (e: Exception) {
            }

        }

        entries.add(PieEntry(male.toFloat(), "Male"))
        entries.add(PieEntry(female.toFloat(), "Female"))


        val d = PieDataSet(entries, "")

        // space between slices
        d.sliceSpace = 2f
        d.setColors(*ColorTemplate.MATERIAL_COLORS)

        val cd = PieData(d)
        cd.setValueFormatter(PercentFormatter())
        cd.setValueTextSize(11f)
        cd.setValueTextColor(Color.WHITE)
        cd.setValueTypeface(mTfLight)
        return cd
    }

    private fun setUpCharts() {
        mChartTrainees.setUsePercentValues(true)
        mChartTrainees.description.isEnabled = false
        mChartTrainees.setExtraOffsets(5f, 10f, 5f, 5f)
        mChartTrainees.dragDecelerationFrictionCoef = 0.95f
        mChartTrainees.setCenterTextTypeface(mTfLight)
        mChartTrainees.centerText = generateCenterSpannableText()
        mChartTrainees.isDrawHoleEnabled = true
        mChartTrainees.setHoleColor(Color.WHITE)
        mChartTrainees.data = generateDataPie()
        mChartTrainees.setTransparentCircleColor(Color.WHITE)
        mChartTrainees.setTransparentCircleAlpha(110)
        mChartTrainees.holeRadius = 58f
        mChartTrainees.transparentCircleRadius = 61f
        mChartTrainees.setDrawCenterText(true)
        mChartTrainees.rotationAngle = 0f
        // enable rotation of the chart by touch
        mChartTrainees.isRotationEnabled = true
        mChartTrainees.isHighlightPerTapEnabled = true
        mChartTrainees.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        val l = mChartTrainees.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 0f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        mChartTrainees.setEntryLabelColor(Color.WHITE)
        mChartTrainees.setEntryLabelTypeface(mTfRegular)
        mChartTrainees.setEntryLabelTextSize(12f)

        // Chart 2
        mChartSession.setUsePercentValues(true)
        mChartSession.description.isEnabled = false
        mChartSession.setExtraOffsets(5f, 10f, 5f, 5f)
        mChartSession.dragDecelerationFrictionCoef = 0.95f
        mChartSession.setCenterTextTypeface(mTfLight)
        mChartSession.centerText = generateCenterSpannableText()
        mChartSession.isDrawHoleEnabled = true
        mChartSession.setHoleColor(Color.WHITE)
        mChartSession.data = generateDataPie()
        mChartSession.setTransparentCircleColor(Color.WHITE)
        mChartSession.setTransparentCircleAlpha(110)
        mChartSession.holeRadius = 58f
        mChartSession.transparentCircleRadius = 61f
        mChartSession.setDrawCenterText(true)
        mChartSession.rotationAngle = 0f
        // ena2ble rotation of the chart by touch
        mChartSession.isRotationEnabled = true
        mChartSession.isHighlightPerTapEnabled = true
        mChartSession.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        val l2 = mChartSession.legend
        l2.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l2.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l2.orientation = Legend.LegendOrientation.HORIZONTAL
        l2.setDrawInside(false)
        l2.xEntrySpace = 0f
        l2.yEntrySpace = 0f
        l2.yOffset = 0f

        // entry label styling
        mChartSession.setEntryLabelColor(Color.WHITE)
        mChartSession.setEntryLabelTypeface(mTfRegular)
        mChartSession.setEntryLabelTextSize(12f)


        //Add line Chart
        val list = ArrayList<ChartItem>()
        for (i in 0..29) {
            list.add(LineChartItem(generateDataLine(i + 1), context))
        }


        attendanceChart.axisRight.isEnabled = false

        //attendanceChart.getViewPortHandler().setMaximumScaleY(2f);
        //attendanceChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(15)

        //        attendanceChart.setVisibleXRange(20);
        //        attendanceChart.setVisibleYRange(20f, AxisDependency.LEFT);
        //        attendanceChart.centerViewTo(20, 50, AxisDependency.LEFT);

        attendanceChart.animateX(2500)
        //attendanceChart.invalidate();

        // get the legend (only possible after setting data)
        val attendanceChartLegend = attendanceChart.legend

        // modify the legend ...
        attendanceChartLegend.form = Legend.LegendForm.LINE

        // // dont forget to refresh the drawing
        // attendanceChart.invalidate();
    }

    private fun generateDataLine(cnt: Int): LineData {

        val e1 = ArrayList<Entry>()

        for (i in 0..11) {
            e1.add(Entry(i.toFloat(), ((Math.random() * 65).toInt() + 40).toFloat()))
        }

        val d1 = LineDataSet(e1, "New DataSet $cnt, (1)")
        d1.lineWidth = 2.5f
        d1.circleRadius = 4.5f
        d1.highLightColor = Color.rgb(244, 117, 117)
        d1.setDrawValues(false)

        val e2 = ArrayList<Entry>()

        for (i in 0..11) {
            e2.add(Entry(i.toFloat(), e1[i].y - 30))
        }

        val d2 = LineDataSet(e2, "New DataSet $cnt, (2)")
        d2.lineWidth = 2.5f
        d2.circleRadius = 4.5f
        d2.highLightColor = Color.rgb(244, 117, 117)
        d2.color = ColorTemplate.VORDIPLOM_COLORS[0]
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0])
        d2.setDrawValues(false)

        val sets = ArrayList<ILineDataSet>()
        sets.add(d1)
        sets.add(d2)

        return LineData(sets)
    }

    private fun setData(count: Int) {

        val values = ArrayList<Entry>()

        for (i in 0 until count) {

            val `val` = (Math.random() * 100).toFloat() + 3
            values.add(Entry(i.toFloat(), `val`))
        }

        val male: LineDataSet
        val female: LineDataSet

        // create a dataset and give it a type
        male = LineDataSet(values, "Male")
        male.lineWidth = 2.5f
        male.circleRadius = 4.5f
        male.highLightColor = Color.rgb(244, 117, 117)
        male.setDrawValues(false)

        val e2 = ArrayList<Entry>()

        for (i in 0 until count) {
            e2.add(Entry(i.toFloat(), values[i].y - 30))
        }

        female = LineDataSet(e2, "Female")
        female.lineWidth = 2.5f
        female.circleRadius = 4.5f
        female.highLightColor = Color.rgb(244, 117, 117)
        female.color = ColorTemplate.VORDIPLOM_COLORS[0]
        female.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0])
        female.setDrawValues(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(male) // add the datasets
        dataSets.add(female) // add the datasets

        // create a data object with the datasets
        val data = LineData(dataSets)

        // set data
        attendanceChart.data = data
    }


    private fun generateCenterSpannableText(): SpannableString {
        return SpannableString(training!!.trainingName)
    }

    private fun getTrainingDetailsFromApi() {
        try {
            TrainingDataSync(context).getTrainingDetailsJson(training!!.id)
        } catch (e: Exception) {
        }

    }

}
