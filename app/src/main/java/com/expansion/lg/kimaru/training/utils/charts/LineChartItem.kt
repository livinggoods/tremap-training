package com.expansion.lg.kimaru.training.utils.charts

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View

import com.expansion.lg.kimaru.training.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.LineData

/**
 * Created by kimaru on 3/8/18.
 */

class LineChartItem(cd: ChartData<*>, c: Context) : ChartItem(cd) {
    private val mTf: Typeface

    override val itemType: Int
        get() = ChartItem.TYPE_LINECHART

    init {
        mTf = Typeface.createFromAsset(c.assets, "OpenSans-Regular.ttf")
    }

    override fun getView(position: Int, convertView: View, context: Context): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            holder = ViewHolder()
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_item_linechart, null)
            holder.chart = convertView.findViewById<View>(R.id.chart) as LineChart
        } else {
            holder = convertView.tag as ViewHolder
        }

        //Some Decos and Style
        holder.chart!!.description.isEnabled = false
        holder.chart!!.setDrawGridBackground(false)

        val xAxis = holder.chart!!.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.typeface = mTf
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)

        val leftAxis = holder.chart!!.axisLeft
        leftAxis.typeface = mTf
        leftAxis.setLabelCount(5, false)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = holder.chart!!.axisRight
        rightAxis.typeface = mTf
        rightAxis.setLabelCount(5, false)
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        // set data
        holder.chart!!.data = mChartData as LineData

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart!!.animateX(750)

        return convertView
    }

    private class ViewHolder {
        internal var chart: LineChart? = null
    }
}
