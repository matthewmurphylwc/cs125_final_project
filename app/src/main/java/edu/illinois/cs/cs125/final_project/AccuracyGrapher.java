package edu.illinois.cs.cs125.final_project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class AccuracyGrapher extends AppCompatActivity {

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        //lineChart.setRotation(90);

        ArrayList<String> xAXIS = new ArrayList<>();
        ArrayList<Entry> yAXISsin = new ArrayList<>();
        ArrayList<Entry> yAXIScos = new ArrayList<>();
        double x = 0;
        int numDataPoints = 100;
        for (int i = 0; i < numDataPoints; i++) {
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));

            x += 0.1;
            yAXISsin.add(new Entry(sinFunction, i));
            yAXIScos.add(new Entry(cosFunction, i));
            xAXIS.add(i, String.valueOf(x));
        }

        String[] xaxis = new String[xAXIS.size()];

        for (int j = 0; j < xAXIS.size(); j++) {
            xaxis[j] = xAXIS.get(j).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXISsin, "sin");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXIScos, "cos");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(10f);

    }


}
