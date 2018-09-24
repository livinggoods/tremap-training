package com.expansion.lg.kimaru.training.utils.charts

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View

import com.expansion.lg.kimaru.training.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

/**
 * Created by kimaru on 3/8/18.
 */

class PieChartItem(cd: ChartData<*>, c: Context) : ChartItem(cd) {

    private val mTf: Typeface
    private val mCenterText: SpannableString

    override val itemType: Int
        get() = ChartItem.TYPE_PIECHART

    init {

        mTf = Typeface.createFromAsset(c.assets, "OpenSans-Regular.ttf")
        mCenterText = generateCenterText()
    }

    override fun getView(position: Int, convertView: View, c: Context): View {
        var convertView = convertView

        var holder: ViewHolder? = null

        if (convertView == null) {

            holder = ViewHolder()

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null)
            holder.chart = convertView.findViewById<View>(R.id.chart) as PieChart

            convertView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
        }

        // apply styling
        holder.chart!!.description.isEnabled = false
        holder.chart!!.holeRadius = 52f
        holder.chart!!.transparentCircleRadius = 57f
        holder.chart!!.centerText = mCenterText
        holder.chart!!.setCenterTextTypeface(mTf)
        holder.chart!!.setCenterTextSize(9f)
        holder.chart!!.setUsePercentValues(true)
        holder.chart!!.setExtraOffsets(5f, 10f, 50f, 10f)

        mChartData.setValueFormatter(PercentFormatter())
        mChartData.setValueTypeface(mTf)
        mChartData.setValueTextSize(11f)
        mChartData.setValueTextColor(Color.WHITE)
        // set data
        holder.chart!!.data = mChartData as PieData

        val l = holder.chart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart!!.animateY(900)

        return convertView
    }

    private fun generateCenterText(): SpannableString {
//        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        //        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        //        s.setSpan(new RelativeSizeSpan(.9f), 14, 20, 0);
        //        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, 20, 0);
        //        s.setSpan(new RelativeSizeSpan(1.4f), 20, s.length(), 0);
        //        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 20, s.length(), 0);
        return SpannableString("Training\ncreated by\nDGK")
    }

    private class ViewHolder {
        internal var chart: PieChart? = null
    }
}
