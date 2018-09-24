package com.expansion.lg.kimaru.training.utils.charts

import android.content.Context
import android.view.View

import com.github.mikephil.charting.data.ChartData

/**
 * Created by kimaru on 3/8/18.
 */

abstract class ChartItem(protected var mChartData: ChartData<*>) {
    abstract val itemType: Int
    abstract fun getView(position: Int, converView: View, c: Context): View

    companion object {
        protected val TYPE_BARCHART = 0
        protected val TYPE_LINECHART = 1
        protected val TYPE_PIECHART = 2
    }
}
