package com.expansion.lg.kimaru.training.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.TrainingExam;
import com.expansion.lg.kimaru.training.objs.TrainingExamResult;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.utils.FlipAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimaru on 3/15/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class TraineesExamSubmissionListAdapter extends Adapter<TraineesExamSubmissionListAdapter.ListHolder>{
    private Context context;
    private List<TrainingTrainee> trainees;
    private TraineesExamSubmissionListAdapterListener listener;
    private SparseBooleanArray selectedItems, selectedItemsIndex;
    private boolean reverseAllActions = false;
    private static int currentSelectedIndex = -1;
    private TrainingExam exam;
    private int noOfQuestions = 0;
    DatabaseHelper databaseHelper;

    public class ListHolder extends ViewHolder implements View.OnLongClickListener{
        public TextView title, subTitle, message, iconText, timestamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout container;
        public RelativeLayout viewBackground, viewForeground, iconContainer, iconBack, iconFront;

        public ListHolder(View view){
            super(view);

            title = (TextView) view.findViewById(R.id.from);
            subTitle = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            iconImp = (ImageView) view.findViewById(R.id.icon_star);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            container = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view){
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }
    public TraineesExamSubmissionListAdapter(Context context, List<TrainingTrainee> trainingTrainees,
                                             TraineesExamSubmissionListAdapterListener listener, TrainingExam exam){
        this.context = context;
        this.trainees = trainingTrainees;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        selectedItemsIndex = new SparseBooleanArray();
        this.exam = exam;
        try {
            noOfQuestions = exam.getCloudExamJson().getJSONArray("questions").length();
        }catch (Exception e){}
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_list_row, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position){
        TrainingTrainee trainee = trainees.get(position);

        //Get all submissions by this trainee for this exam
        List<TrainingExamResult>  submittedQuestions = databaseHelper
                .getTrainingExamResultByExamTraineeId(exam.getId().toString(), trainee.getRegistrationId());

        int noOfSubmittedQuestions = submittedQuestions.size();
        String submissionStatus = "";
        String examStatus = "UNKNOWN";
        String examTotalMarks = "--";
        Integer totalMarks = 0;

        if (noOfSubmittedQuestions == 0){
            submissionStatus = "Undone";
            examStatus = "UNKNOWN";
        }else if (noOfSubmittedQuestions < noOfQuestions){
            submissionStatus = "Incomplete";
            for (TrainingExamResult result : submittedQuestions){
                totalMarks += result.getQuestionScore();
            }
            examTotalMarks = String.format("Marks so far %s", totalMarks.toString());
            examStatus = "CALCULATING ... ";
        }else {
            submissionStatus = "Complete";
            for (TrainingExamResult result : submittedQuestions){
                totalMarks += result.getQuestionScore();
            }
            examTotalMarks = totalMarks.toString();
            examStatus = totalMarks >= exam.getPassmark() ? "PASS" : "FAILED";

            if (exam.getPassmark() == 0) {
                // Trainer did not set a passmark for this exam
                examStatus = "UNKNOWN";
            }
        }
        holder.subTitle.setText(examTotalMarks);

        //@TODO: create configuration for pass status (pass, average, fail, or unknown - in case someone has not submitted the exam)

        holder.message.setText(examStatus);

        holder.timestamp.setText(submissionStatus);
        try{
            holder.title.setText(trainee.getRegistration().getString("name"));
            // displaying the first letter of From in icon text
            holder.iconText.setText(trainee.getRegistration().getString("name").substring(0,1));
            // change the row state to activated
        }catch (Exception e){}
        holder.itemView.setActivated(selectedItems.get(position, false));
        // handle icon animation
        applyIconAnimation(holder, position);
        applyIconColor(holder, trainee);
        // apply click events
        applyClickEvents(holder, position);
        applyImportant(holder, submissionStatus);

    }

    private void applyClickEvents(ListHolder holder, final int position){
        holder.iconContainer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.onIconClicked(position);
            }
        });
        holder.iconImp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.onIconImportantClicked(position);
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageRowClicked(position);
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyIconAnimation(ListHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(context, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllActions && selectedItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(context, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }

    private void applyIconColor(ListHolder holder, TrainingTrainee trainingTrainee) {
        holder.imgProfile.setImageResource(R.drawable.bg_circle);
        holder.imgProfile.setColorFilter(trainingTrainee.getColor());
        holder.iconText.setVisibility(View.VISIBLE);
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllActions = false;
        selectedItemsIndex.clear();
    }

    private void applyImportant(ListHolder holder, String submissionStatus) {
        if (submissionStatus.equalsIgnoreCase("Complete")) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_completed_24px));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.LightGreen));
        } else if (submissionStatus.equalsIgnoreCase("Undone")) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_not_done));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.Red));
        }else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_waiting_24px));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
        }
    }

    @Override
    public int getItemCount(){
        return trainees.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            selectedItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            selectedItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllActions = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        trainees.remove(position);
        notifyItemRemoved(position);
        resetCurrentIndex();
    }
    public void restoreItem(TrainingTrainee trainingTrainee, int position) {
        trainees.add(position, trainingTrainee);
        // notify item added by position
        notifyItemInserted(position);
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }



    public interface TraineesExamSubmissionListAdapterListener{
        void onIconClicked(int position);
        void onIconImportantClicked(int position);
        void onMessageRowClicked(int position);
        void onRowLongClicked(int position);
    }
}
