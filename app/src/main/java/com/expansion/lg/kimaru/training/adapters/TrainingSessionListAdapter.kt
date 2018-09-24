package com.expansion.lg.kimaru.training.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.objs.SessionTopic
import com.expansion.lg.kimaru.training.objs.TrainingSession
import com.expansion.lg.kimaru.training.objs.TrainingSessionType
import com.expansion.lg.kimaru.training.utils.FlipAnimator

import java.util.ArrayList

/**
 * Created by kimaru on 3/16/18 using Android Studio.
 * @Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class TrainingSessionListAdapter(private val context: Context, private val sessions: MutableList<TrainingSession>, private val listener: TrainingSessionListAdapterListener) : RecyclerView.Adapter<TrainingSessionListAdapter.ListHolder>() {
    private val selectedItems: SparseBooleanArray
    private val selectedItemsIndex: SparseBooleanArray
    private var reverseAllActions = false

    val selectedItemCount: Int
        get() = selectedItems.size()

    inner class ListHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        var title: TextView
        var subTitle: TextView
        var message: TextView
        var iconText: TextView
        var timestamp: TextView
        var iconImp: ImageView
        var imgProfile: ImageView
        var container: LinearLayout
        var viewBackground: RelativeLayout
        var viewForeground: RelativeLayout
        var iconContainer: RelativeLayout
        var iconBack: RelativeLayout
        var iconFront: RelativeLayout

        init {

            title = view.findViewById<View>(R.id.from) as TextView
            subTitle = view.findViewById<View>(R.id.txt_primary) as TextView
            message = view.findViewById<View>(R.id.txt_secondary) as TextView
            iconText = view.findViewById<View>(R.id.icon_text) as TextView
            timestamp = view.findViewById<View>(R.id.timestamp) as TextView
            iconBack = view.findViewById<View>(R.id.icon_back) as RelativeLayout
            iconFront = view.findViewById<View>(R.id.icon_front) as RelativeLayout
            iconImp = view.findViewById<View>(R.id.icon_star) as ImageView
            imgProfile = view.findViewById<View>(R.id.icon_profile) as ImageView
            container = view.findViewById<View>(R.id.message_container) as LinearLayout
            iconContainer = view.findViewById<View>(R.id.icon_container) as RelativeLayout
            viewBackground = view.findViewById(R.id.view_background)
            viewForeground = view.findViewById(R.id.view_foreground)

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
                R.layout.recycler_list_row, parent, false)
        return ListHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val session = sessions[position]
        val databaseHelper = DatabaseHelper(context)
        val sessionTopic = databaseHelper.getSessionTopicById(session.sessionTopicId!!.toString())
        val sessionType = databaseHelper.getTrainingSessionTypeById(session.trainingSessionTypeId!!.toString())
        try {
            holder.title.text = sessionTopic!!.name
            holder.subTitle.text = sessionTopic.name
            holder.message.text = sessionTopic.name
            holder.timestamp.text = sessionTopic.name
            // displaying the first letter of From in icon text
            holder.iconText.setText(sessionTopic.name.substring(0, 1))

            // change the row state to activated
            holder.itemView.isActivated = selectedItems.get(position, false)

            // handle icon animation
            applyIconAnimation(holder, position)


            applyIconColor(holder, session)


            // apply click events
            applyClickEvents(holder, position)
        } catch (e: Exception) {
            holder.message.text = e.message
        }

    }

    private fun applyClickEvents(holder: ListHolder, position: Int) {
        holder.iconContainer.setOnClickListener { listener.onIconClicked(position) }
        holder.iconImp.setOnClickListener { listener.onIconImportantClicked(position) }
        holder.container.setOnClickListener { listener.onMessageRowClicked(position) }
        holder.container.setOnLongClickListener { view ->
            listener.onRowLongClicked(position)
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            true
        }
    }

    private fun applyIconAnimation(holder: ListHolder, position: Int) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.visibility = View.GONE
            resetIconYAxis(holder.iconBack)
            holder.iconBack.visibility = View.VISIBLE
            holder.iconBack.alpha = 1f
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(context, holder.iconBack, holder.iconFront, true)
                resetCurrentIndex()
            }
        } else {
            holder.iconBack.visibility = View.GONE
            resetIconYAxis(holder.iconFront)
            holder.iconFront.visibility = View.VISIBLE
            holder.iconFront.alpha = 1f
            if (reverseAllActions && selectedItemsIndex.get(position, false) || currentSelectedIndex == position) {
                FlipAnimator.flipView(context, holder.iconBack, holder.iconFront, false)
                resetCurrentIndex()
            }
        }
    }

    private fun applyIconColor(holder: ListHolder, trainingSession: TrainingSession) {
        holder.imgProfile.setImageResource(R.drawable.bg_circle)
        holder.imgProfile.setColorFilter(trainingSession.color)
        holder.iconText.visibility = View.VISIBLE
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private fun resetIconYAxis(view: View) {
        if (view.rotationY != 0f) {
            view.rotationY = 0f
        }
    }

    fun resetAnimationIndex() {
        reverseAllActions = false
        selectedItemsIndex.clear()
    }

    private fun applyImportant(holder: ListHolder, trainingSession: TrainingSession) {
        if (trainingSession.country.equals("KE", ignoreCase = true)) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp))
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_selected))
        } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp))
            holder.iconImp.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal))
        }
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    interface TrainingSessionListAdapterListener {
        fun onIconClicked(position: Int)
        fun onIconImportantClicked(position: Int)
        fun onMessageRowClicked(position: Int)
        fun onRowLongClicked(position: Int)
    }

    private fun resetCurrentIndex() {
        currentSelectedIndex = -1
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

    fun clearSelections() {
        reverseAllActions = true
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Int> {
        val items = ArrayList<Int>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            items.add(selectedItems.keyAt(i))
        }
        return items
    }

    fun removeData(position: Int) {
        sessions.removeAt(position)
        notifyItemRemoved(position)
        resetCurrentIndex()
    }

    fun restoreItem(trainingSession: TrainingSession, position: Int) {
        sessions.add(position, trainingSession)
        // notify item added by position
        notifyItemInserted(position)
    }

    companion object {
        private var currentSelectedIndex = -1
    }
}
