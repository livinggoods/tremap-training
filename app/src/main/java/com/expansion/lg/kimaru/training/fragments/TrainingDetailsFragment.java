package com.expansion.lg.kimaru.training.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.utils.charts.BarChartItem;
import com.expansion.lg.kimaru.training.utils.charts.ChartItem;
import com.expansion.lg.kimaru.training.utils.charts.LineChartItem;
import com.expansion.lg.kimaru.training.utils.charts.MyMarkerView;
import com.expansion.lg.kimaru.training.utils.charts.PieChartItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;

/**
 * Created by kimaru on 3/30/17.
 */
public class TrainingDetailsFragment extends Fragment implements  View.OnClickListener{

    Training training = null;

    ImageView trainingImagePoster;
    TextView trainingNameText;
    TextView trainingClassSize;
    TextView trainingDates;
    TextView trainingComment;
    CardView cardTrainingDetail;
    CardView cardMovieOverview;
    CardView cardMovieVideos;
    RecyclerView movieVideos;
    CardView cardMovieReviews;
    RecyclerView movieReviews;
    PieChart mChartTrainees, mChartSession;
    LineChart attendanceChart;
    Typeface mTfLight, mTfRegular;
    Button traineesButton, sessionsButton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.training_details_view, container, false);
        ButterKnife.bind(this, v);
        trainingImagePoster = v.findViewById(R.id.image_training_detail_poster);
        trainingNameText = v.findViewById(R.id.text_training_name);
        trainingClassSize = v.findViewById(R.id.text_movie_user_rating);
        trainingDates = v.findViewById(R.id.text_movie_release_date);
        trainingComment = v.findViewById(R.id.text_training_comment);
        cardTrainingDetail = v.findViewById(R.id.card_training_detail);
        cardMovieOverview = v.findViewById(R.id.card_movie_overview);
        traineesButton = v.findViewById(R.id.traineesButton);
        sessionsButton = v.findViewById(R.id.sessionsButton);
        //cardMovieVideos = v.findViewById(R.id.card_movie_videos);
//        movieVideos = v.findViewById(R.id.movie_videos);
        getTrainingDetailsFromApi();

        //cardMovieReviews = v.findViewById(R.id.card_movie_reviews);
        //movieReviews = v.findViewById(R.id.movie_reviews);
        mChartTrainees = v.findViewById(R.id.trainees_chart);
        mChartSession = v.findViewById(R.id.session_chart);
        attendanceChart = v.findViewById(R.id.chart_attendance);
        mTfLight = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");
        mTfRegular = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        initViews();
        setupCardsElevation();
        setUpCharts();


        traineesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;
                TrainingsTraineesFragment trainingsTraineesFragment = new TrainingsTraineesFragment();
                trainingsTraineesFragment.training = training;
                fragment = trainingsTraineesFragment;
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        });


        sessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;

                TrainingsSessionsFragment trainingsSessionsFragment = new TrainingsSessionsFragment();
                trainingsSessionsFragment.training = training;
                fragment = trainingsSessionsFragment;
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (training != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(training.getTrainingName());
        }
    }

    @Override
    public void onClick (View view){
        Fragment fragment;
        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager;
        switch (view.getId()){
            case R.id.action_delete:

                break;
        }
    }
    private void initViews() {
        Glide.with(this)
                .load(R.drawable.lg_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(trainingImagePoster);
        if (training != null){
            trainingNameText.setText(training.getTrainingName());
            trainingClassSize.setText(String.valueOf(new DatabaseHelper(getContext()).getTrainingTraineesByTrainingId(training.getId()).size()));
            trainingClassSize.setTextColor(getRatingColor(9.0D));
            String releaseDate = String.format(getString(R.string.training_date),
                    String.valueOf(training.getDateCommenced()), String.valueOf(training.getClientTime()));
            trainingDates.setText(releaseDate);
            trainingComment.setText(training.getComment());
        }

    }
    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getClass().getPackage().getName());
        return drawableResourceId;
    }
    @ColorInt
    private int getRatingColor(double averageVote) {
//        if (averageVote >= VOTE_PERFECT) {
//            return ContextCompat.getColor(getContext(), R.color.vote_perfect);
//        } else if (averageVote >= VOTE_GOOD) {
//            return ContextCompat.getColor(getContext(), R.color.vote_good);
//        } else if (averageVote >= VOTE_NORMAL) {
//            return ContextCompat.getColor(getContext(), R.color.vote_normal);
//        } else {
//            return ContextCompat.getColor(getContext(), R.color.vote_bad);
//        }
        return ContextCompat.getColor(getContext(), R.color.vote_perfect);
    }

    private void setupCardsElevation() {
        setupCardElevation(cardTrainingDetail);
        //setupCardElevation(cardMovieVideos);
        setupCardElevation(cardMovieOverview);
        //setupCardElevation(cardMovieReviews);
    }
    private void setupCardElevation(View view) {
        ViewCompat.setElevation(view,
                convertDpToPixel(getResources().getInteger(R.integer.training_detail_content_elevation_in_dp)));
    }
    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add((new PieEntry((float) 39, "Male")));
        entries.add((new PieEntry((float) 82, "Female")));


        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData cd = new PieData(d);
        cd.setValueFormatter(new PercentFormatter());
        cd.setValueTextSize(11f);
        cd.setValueTextColor(Color.WHITE);
        cd.setValueTypeface(mTfLight);
        return cd;
    }
    private void setUpCharts(){
        mChartTrainees.setUsePercentValues(true);
        mChartTrainees.getDescription().setEnabled(false);
        mChartTrainees.setExtraOffsets(5, 10, 5, 5);
        mChartTrainees.setDragDecelerationFrictionCoef(0.95f);
        mChartTrainees.setCenterTextTypeface(mTfLight);
        mChartTrainees.setCenterText(generateCenterSpannableText());
        mChartTrainees.setDrawHoleEnabled(true);
        mChartTrainees.setHoleColor(Color.WHITE);
        mChartTrainees.setData(generateDataPie());
        mChartTrainees.setTransparentCircleColor(Color.WHITE);
        mChartTrainees.setTransparentCircleAlpha(110);
        mChartTrainees.setHoleRadius(58f);
        mChartTrainees.setTransparentCircleRadius(61f);
        mChartTrainees.setDrawCenterText(true);
        mChartTrainees.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChartTrainees.setRotationEnabled(true);
        mChartTrainees.setHighlightPerTapEnabled(true);
        mChartTrainees.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        Legend l = mChartTrainees.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChartTrainees.setEntryLabelColor(Color.WHITE);
        mChartTrainees.setEntryLabelTypeface(mTfRegular);
        mChartTrainees.setEntryLabelTextSize(12f);

        // Chart 2
        mChartSession.setUsePercentValues(true);
        mChartSession.getDescription().setEnabled(false);
        mChartSession.setExtraOffsets(5, 10, 5, 5);
        mChartSession.setDragDecelerationFrictionCoef(0.95f);
        mChartSession.setCenterTextTypeface(mTfLight);
        mChartSession.setCenterText(generateCenterSpannableText());
        mChartSession.setDrawHoleEnabled(true);
        mChartSession.setHoleColor(Color.WHITE);
        mChartSession.setData(generateDataPie());
        mChartSession.setTransparentCircleColor(Color.WHITE);
        mChartSession.setTransparentCircleAlpha(110);
        mChartSession.setHoleRadius(58f);
        mChartSession.setTransparentCircleRadius(61f);
        mChartSession.setDrawCenterText(true);
        mChartSession.setRotationAngle(0);
        mChartSession.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                Toast.makeText(getContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
        // ena2ble rotation of the chart by touch
        mChartSession.setRotationEnabled(true);
        mChartSession.setHighlightPerTapEnabled(true);
        mChartSession.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        Legend l2 = mChartSession.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);
        l2.setXEntrySpace(0f);
        l2.setYEntrySpace(0f);
        l2.setYOffset(0f);

        // entry label styling
        mChartSession.setEntryLabelColor(Color.WHITE);
        mChartSession.setEntryLabelTypeface(mTfRegular);
        mChartSession.setEntryLabelTextSize(12f);


        //Add line Chart
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        for (int i =0; i< 30; i++) {
            list.add(new LineChartItem(generateDataLine(i + 1), getContext()));
        }


        attendanceChart.getAxisRight().setEnabled(false);

        //attendanceChart.getViewPortHandler().setMaximumScaleY(2f);
        //attendanceChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(15);

//        attendanceChart.setVisibleXRange(20);
//        attendanceChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        attendanceChart.centerViewTo(20, 50, AxisDependency.LEFT);

        attendanceChart.animateX(2500);
        //attendanceChart.invalidate();

        // get the legend (only possible after setting data)
        Legend attendanceChartLegend = attendanceChart.getLegend();

        // modify the legend ...
        attendanceChartLegend.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // attendanceChart.invalidate();
    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(i, e1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(sets);
        return cd;
    }

    private void setData(int count) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random()*100) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet male, female;

        // create a dataset and give it a type
        male = new LineDataSet(values, "Male");
        male.setLineWidth(2.5f);
        male.setCircleRadius(4.5f);
        male.setHighLightColor(Color.rgb(244, 117, 117));
        male.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            e2.add(new Entry(i, values.get(i).getY() - 30));
        }

        female = new LineDataSet(e2, "Female");
        female.setLineWidth(2.5f);
        female.setCircleRadius(4.5f);
        female.setHighLightColor(Color.rgb(244, 117, 117));
        female.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        female.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        female.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(male); // add the datasets
        dataSets.add(female); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        attendanceChart.setData(data);
    }


    private SpannableString generateCenterSpannableText() {
        return new SpannableString(training.getTrainingName());
    }

    private void getTrainingDetailsFromApi(){
        try{
            new TrainingDataSync(getContext()).getTrainingDetailsJson(training.getId());
        }catch (Exception e){}
    }

    private boolean isConnected(){
        return ConnectivityReceiver.isConnected();
    }

}
