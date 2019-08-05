package com.expansion.lg.kimaru.training.adapters;



import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimaru on 3/19/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class SessionAttendanceListAdapter extends Adapter<SessionAttendanceListAdapter.ListHolder>{

    private Context context;
    private List<SessionAttendance> sessionAttendances;
    private SessionAttendanceListAdapterListener listener;
    private SparseBooleanArray selectedItems, selectedItemsIndex;
    private boolean reverseAllActions = false;
    private static int currentSelectedIndex = -1;
    private List<SessionAttendance> selectedAttendees = new ArrayList<>();

    public class ListHolder extends ViewHolder implements View.OnLongClickListener{
        public TextView title, message;
        public CheckBox checkboxAttended;

        public ListHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
            checkboxAttended = view.findViewById(R.id.checkbox_attended);
            view.setOnLongClickListener(this);
        }
        @Override
        public boolean onLongClick(View view){
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public SessionAttendanceListAdapter (Context context, List<SessionAttendance> attendances, SessionAttendanceListAdapterListener listener){
        this.context = context;
        this.sessionAttendances = attendances;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        selectedItemsIndex = new SparseBooleanArray();
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.attendance_list_item, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position){
        final SessionAttendance sessionAttendance = sessionAttendances.get(position);
        // need to initialize the DB
        final DatabaseHelper databaseHelper = new DatabaseHelper(context);
        // Extract the trainee
        TrainingTrainee trainingTrainee = databaseHelper.getTrainingTraineeById(sessionAttendance.getTraineeId());
        String traineeName = "";
        try{
            traineeName = trainingTrainee.getRegistration().getString("name");
        }catch (Exception e){}

        holder.title.setText(traineeName);
        holder.message.setText("");

        //in some cases, it will prevent unwanted situations
        holder.checkboxAttended.setOnCheckedChangeListener(null);
        holder.checkboxAttended.setChecked(sessionAttendance.isAttended());
        holder.checkboxAttended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isAttending) {
                SessionAttendance attendance = sessionAttendances.get(holder.getAdapterPosition());
                attendance.setAttended(isAttending);
                databaseHelper.addSessionAttendance(attendance);

                if (isAttending){
                    selectedAttendees.add(attendance);
                }else{
                    selectedAttendees.remove(attendance);
                }
                //listener.onRowLongClicked(getAdapterPosition());
                listener.onTraineeSelected();
            }
        });
    }
    @Override
    public int getItemCount(){
        return sessionAttendances.size();
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

    public interface SessionAttendanceListAdapterListener{
        void onRowLongClicked(int position);
        void onTraineeSelected();


    }
    public List<SessionAttendance> getSelectedTrainees(){
        return this.selectedAttendees;
    };

    public void removeUploadedItem(SessionAttendance s, int position){
        selectedAttendees.add(position, s);
    }
}
