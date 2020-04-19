package com.expansion.lg.kimaru.training.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.utils.DisplayDate;
import com.expansion.lg.kimaru.training.utils.FlipAnimator;

import org.json.JSONException;

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

public class TraineeListAdapter extends RecyclerView.Adapter<TraineeListAdapter.ListHolder> implements Filterable {
    private Context context;
    public List<TrainingTrainee> trainees;
    private TrainingTraineeListAdapterListener listener;
    private SparseBooleanArray selectedItems, selectedItemsIndex;
    private boolean reverseAllActions = false;
    private static int currentSelectedIndex = -1;

    //Start of filterable
    private List<TrainingTrainee> originalList;
    private List<TrainingTrainee> traineesFull;
    //End of filterable

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
    public TraineeListAdapter(Context context, List<TrainingTrainee> trainees, TrainingTraineeListAdapterListener listener){
        this.context = context;
        this.trainees = trainees;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        selectedItemsIndex = new SparseBooleanArray();
        traineesFull = new ArrayList<>(trainees);
        originalList = trainees;
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
        try{
            holder.title.setText(trainee.getRegistration().getString("name"));
            holder.subTitle.setText(trainee.getRegistration().getString("phone"));
            holder.message.setText(trainee.getRegistration().getString("phone"));
            holder.timestamp.setText(trainee.getRegistration().getString("client_time"));

            holder.timestamp.setText(new DisplayDate(trainee.getClientTime()*1000).dateOnly());

            // displaying the first letter of From in icon text
            holder.iconText.setText(trainee.getRegistration().getString("name").substring(0,1));

            // change the row state to activated
            holder.itemView.setActivated(selectedItems.get(position, false));

            // handle icon animation
            applyIconAnimation(holder, position);


            applyIconColor(holder, trainee);


            // apply click events
            applyClickEvents(holder, position);
        }catch (Exception e){}
        if (trainee.getCountry().equalsIgnoreCase("KE")){

        }else{

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

    private void applyImportant(ListHolder holder, TrainingTrainee trainingTrainee) {
        if (trainingTrainee.getCountry().equalsIgnoreCase("KE")) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_selected));
        } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp));
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



    public interface TrainingTraineeListAdapterListener{
        void onIconClicked(int position);
        void onIconImportantClicked(int position);
        void onMessageRowClicked(int position);
        void onRowLongClicked(int position);
    }


    @Override
    public Filter getFilter() {
        return traineeFilter;
    }

    private Filter traineeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //new list containing only filtered items
            List<TrainingTrainee> filteredList = new ArrayList<>();
            if (constraint == null){
                filteredList.addAll(originalList);
            } else if (constraint.toString().trim().equals("")) {
                filteredList.addAll(originalList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.e("Query", filterPattern);
                for (TrainingTrainee item : originalList){
                    String name = "";
                    try {
                        name = item.getRegistration().getString("name").toLowerCase();
                    } catch (JSONException ex)  {
                        ex.printStackTrace();
                    }
                    Log.e("Name", name);
                    if (name.startsWith(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            trainees = (ArrayList<TrainingTrainee>) results.values;
            notifyDataSetChanged();
        }
    };

}