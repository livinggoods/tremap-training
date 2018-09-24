package com.expansion.lg.kimaru.training.adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.SparseBooleanArray
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.objs.SessionAttendance
import com.expansion.lg.kimaru.training.objs.TrainingTrainee

import java.util.ArrayList

/**
 * Created by kimaru on 3/19/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class SessionAttendanceListAdapter(private val context: Context, private val sessionAttendances: List<SessionAttendance>, private val listener: SessionAttendanceListAdapterListener) : Adapter<SessionAttendanceListAdapter.ListHolder>() {
    private val selectedItems: SparseBooleanArray
    private val selectedItemsIndex: SparseBooleanArray
    private val reverseAllActions = false
    private val selectedAttendees = ArrayList<SessionAttendance>()
    val selectedTrainees: List<SessionAttendance>
        get() = this.selectedAttendees

    inner class ListHolder(view: View) : ViewHolder(view), View.OnLongClickListener {
        var title: TextView
        var message: TextView
        var checkboxAttended: CheckBox

        init {
            title = view.findViewById(R.id.title)
            message = view.findViewById(R.id.message)
            checkboxAttended = view.findViewById(R.id.checkbox_attended)
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View): Boolean {
            listener.onRowLongClicked(adapterPosition)
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            return true
        }
    }

    init {
        selectedItems = SparseBooleanArray()
        selectedItemsIndex = SparseBooleanArray()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.attendance_list_item, parent, false)
        return ListHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val sessionAttendance = sessionAttendances[position]
        // need to initialize the DB
        val databaseHelper = DatabaseHelper(context)
        // Extract the trainee
        val trainingTrainee = databaseHelper.getTrainingTraineeById(sessionAttendance.traineeId)
        var traineeName = ""
        try {
            traineeName = trainingTrainee!!.registration.getString("name")
        } catch (e: Exception) {
        }

        holder.title.text = traineeName
        holder.message.text = ""

        //in some cases, it will prevent unwanted situations
        holder.checkboxAttended.setOnCheckedChangeListener(null)
        holder.checkboxAttended.isChecked = sessionAttendance.isAttended
        holder.checkboxAttended.setOnCheckedChangeListener { compoundButton, isAttending ->
            val attendance = sessionAttendances[holder.adapterPosition]
            attendance.isAttended = isAttending
            databaseHelper.addSessionAttendance(attendance)

            if (isAttending) {
                selectedAttendees.add(attendance)
            } else {
                selectedAttendees.remove(attendance)
            }
            //listener.onRowLongClicked(getAdapterPosition());
            listener.onTraineeSelected()
        }
    }

    override fun getItemCount(): Int {
        return sessionAttendances.size
    }

    fun toggleSelection(pos: Int) {
        currentSelectedIndex = pos
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos)
            selectedItemsIndex.delete(pos)
        } else {
            selectedItems.put(pos, true)
            selectedItemsIndex.put(pos, true)
        }
        notifyItemChanged(pos)
    }

    interface SessionAttendanceListAdapterListener {
        fun onRowLongClicked(position: Int)
        fun onTraineeSelected()


    }

    fun removeUploadedItem(s: SessionAttendance, position: Int) {
        selectedAttendees.add(position, s)
    }

    companion object {
        private var currentSelectedIndex = -1
    }
}
