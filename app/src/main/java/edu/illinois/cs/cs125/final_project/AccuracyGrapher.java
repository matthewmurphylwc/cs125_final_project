package edu.illinois.cs.cs125.final_project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AccuracyGrapher extends AppCompatActivity {

    public final int MAX_TRANS = 10;

    private BarChart mChart;

    //LineChart lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        mChart = (BarChart) findViewById(R.id.bars);
        mChart.getDescription().setEnabled(false);

        setData(10);
        mChart.setFitBars(true);


        /**
         *
        lineChart = (LineChart) findViewById(R.id.lineChart);
        //lineChart.setRotation(180);

        ArrayList<String> xAXIS = new ArrayList<>();
        ArrayList<Entry> yAXISsin = new ArrayList<>();
        //ArrayList<Entry> yAXIScos = new ArrayList<>();

        float[] arr = new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int k = 0; k < MAX_TRANS; k++) {
            if (k == array.length) {
                break;
            }
            arr[k] = method().array[k];
        }

        double x = 0;
        int numDataPoints = arr.length;
        for (int i = 0; i < numDataPoints; i++) {
            //float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            //float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            float numbers = Float.parseFloat(String.valueOf(arr[i]));

            x += 1;

            yAXISsin.add(new Entry(numbers, i));
            //yAXIScos.add(new Entry(cosFunction, i));
            xAXIS.add(i, String.valueOf(x));
        }

        String[] xaxis = new String[xAXIS.size()];

        for (int j = 0; j < xAXIS.size(); j++) {
            xaxis[j] = xAXIS.get(j).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXISsin, "Accuracy");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.BLUE);

        /**
         *
        LineDataSet lineDataSet2 = new LineDataSet(yAXIScos, "cos");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
         */

        /**
        lineDataSets.add(lineDataSet1);
        //lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));

        lineChart.fitScreen();

        lineChart.setVisibleXRangeMaximum(10f);
        */

    }

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


}
