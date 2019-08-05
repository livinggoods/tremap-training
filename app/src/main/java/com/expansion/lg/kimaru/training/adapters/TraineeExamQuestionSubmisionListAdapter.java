package com.expansion.lg.kimaru.training.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimaru on 3/15/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549  /usr/lib/jvm/java-8-oracle/jre
 * @web gakuu.co.ke
 */

public class TraineeExamQuestionSubmisionListAdapter extends Adapter<TraineeExamQuestionSubmisionListAdapter.ListHolder>{
    private Context context;
    // private List<TrainingExamResult> trainingExamResult;
    private TraineeExamQuestionSubmisionListAdapterListener listener;
    private SparseBooleanArray selectedItems, selectedItemsIndex;
    private boolean reverseAllActions = false;
    private static int currentSelectedIndex = -1;
    private TrainingTrainee trainee;
    private TrainingExam exam;
    private JSONArray questions;

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
    public TraineeExamQuestionSubmisionListAdapter(Context context, List<TrainingExamResult>  result,
                                                   TrainingTrainee trainee, TrainingExam exam,
                                                   TraineeExamQuestionSubmisionListAdapterListener listener){
        this.context = context;
        //this.trainingExamResult = result;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        selectedItemsIndex = new SparseBooleanArray();
        this.trainee = trainee;
        this.exam = exam;
        this.questions = exam.getQuestions();
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_list_row, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position){


        try{
            JSONObject questionJson = questions.getJSONObject(position);
            String question = questionJson.getString("question");
            String questionId = String.valueOf(questionJson.getInt("id"));


            TrainingExamResult result = new DatabaseHelper(context)
                    .getTrainingExamResultByExamQuestionAndTrainee(questionId,
                            exam.getId().toString(), trainee.getId());

            holder.title.setText(question);
            holder.subTitle.setText(result.getAnswer());
            holder.message.setText("Scored: "+String.valueOf(result.getQuestionScore()));

            holder.timestamp.setText(String.valueOf(questionJson.getInt("allocated_marks")));


            // displaying the first letter of From in icon text
            holder.iconText.setText(String.valueOf(position+1));

            // change the row state to activated
            holder.itemView.setActivated(selectedItems.get(position, false));

            // handle icon animation
            applyIconAnimation(holder, position);

            applyIconColor(holder);

            // show image
            Boolean gottenRight = false;
            JSONArray choices = questionJson.getJSONArray("choices");
            for (int y =0; y < choices.length(); y++){
                JSONObject choice = choices.getJSONObject(y);
                if (choice.getInt("id") == result.getChoiceId() && choice.getBoolean("is_answer")){
                    gottenRight = true;
                    break;
                }
            }
            applyImportant(holder, gottenRight);


            // apply click events
            applyClickEvents(holder, position);
        }catch (Exception e){
            Log.d("Tremap ERR", e.getMessage());
        }
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

    private void applyIconColor(ListHolder holder) {
        holder.imgProfile.setImageResource(R.drawable.bg_circle);
        holder.imgProfile.setColorFilter(getRandomMaterialColor("400"));
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

    private void applyImportant(ListHolder holder, Boolean isCorrect) {
        if (isCorrect) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.primary_material_dark));
        } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_down_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.icon_warning));
        }
    }

    @Override
    public int getItemCount(){
        return questions.length();
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
        questions.remove(position);
        notifyItemRemoved(position);
        resetCurrentIndex();
    }
    public void restoreItem(TrainingExamResult result, int position) {
//        try {
//            questions.put(position, result);
//        }catch (Exception e){}
        // notify item added by position
        notifyItemInserted(position);
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }



    public interface TraineeExamQuestionSubmisionListAdapterListener{
        void onIconClicked(int position);
        void onIconImportantClicked(int position);
        void onMessageRowClicked(int position);
        void onRowLongClicked(int position);
    }

    /*
    *  Choose a random
    *  Color
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
}
