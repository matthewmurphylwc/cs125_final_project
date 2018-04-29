package edu.illinois.cs.cs125.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AccuracyGrapher extends AppCompatActivity {

    public final int MAX_TRANS = 10;

    private BarChart mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        mChart = findViewById(R.id.bars);
        mChart.getDescription().setEnabled(false);


        //mChart.setFitBars(true);


        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < MAX_TRANS; i++) {
            float value = (float) (Math.random() * 100);
            yVals.add(new BarEntry(i, (int) value));
        }

        BarDataSet set = new BarDataSet(yVals, "Data Set");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(500);

        mChart.setFitBars(true);

        //setData(10);
    }

    /*

    private void setData(int count) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * 100);
            yVals.add(new BarEntry(i, (int) value));
        }

        BarDataSet set = new BarDataSet(yVals, "Data Set");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData();

        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(500);
    }

    */

}
