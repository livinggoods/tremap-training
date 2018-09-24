package com.expansion.lg.kimaru.training.utils.charts

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View

import com.expansion.lg.kimaru.training.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.ChartData

/**
 * Created by kimaru on 3/8/18.
 */

class BarChartItem(cd: ChartData<*>, c: Context) : ChartItem(cd) {

    private val mTf: Typeface

    override val itemType: Int
        get() = ChartItem.TYPE_BARCHART

    init {

        mTf = Typeface.createFromAsset(c.assets, "OpenSans-Regular.ttf")
    }

    override fun getView(position: Int, convertView: View?, c: Context): View {
        var convertView = convertView

        var holder: ViewHolder? = null

        if (convertView == null) {

            holder = ViewHolder()

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null)
            holder.chart = convertView!!.findViewById<View>(R.id.chart) as BarChart

            convertView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
        }

        // apply styling
        holder.chart!!.description.isEnabled = false
        holder.chart!!.setDrawGridBackground(false)
        holder.chart!!.setDrawBarShadow(false)

        val xAxis = holder.chart!!.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.typeface = mTf
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)

        val leftAxis = holder.chart!!.axisLeft
        leftAxis.typeface = mTf
        leftAxis.setLabelCount(5, false)
        leftAxis.spaceTop = 20f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = holder.chart!!.axisRight
        rightAxis.typeface = mTf
        rightAxis.setLabelCount(5, false)
        rightAxis.spaceTop = 20f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        mChartData.setValueTypeface(mTf)

        // set data
        holder.chart!!.data = mChartData as BarData
        holder.chart!!.setFitBars(true)

        // do not forget to refresh the chart
        //        holder.chart.invalidate();
        holder.chart!!.animateY(700)

        return convertView
    }

    private class ViewHolder {
        internal var chart: BarChart? = null
    }
}
