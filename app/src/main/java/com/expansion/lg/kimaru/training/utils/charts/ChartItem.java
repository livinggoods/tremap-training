package com.expansion.lg.kimaru.training.utils.charts;

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;

/**
 * Created by kimaru on 3/8/18.
 */

public abstract class ChartItem {
    protected static final int TYPE_BARCHART = 0;
    protected static final int TYPE_LINECHART = 1;
    protected static final int TYPE_PIECHART = 2;

    protected ChartData<?> mChartData;
    public ChartItem(ChartData<?> chartData){
        this.mChartData = chartData;
    }
    public abstract int getItemType();
    public abstract View getView(int position, View converView, Context c);
}
