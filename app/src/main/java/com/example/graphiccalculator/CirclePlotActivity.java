package com.example.graphiccalculator;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Arrays;
import java.util.Comparator;

public class CirclePlotActivity extends AppCompatActivity {

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_main);
        graph = findViewById(R.id.graph);

        // Calculate the screen width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Set the width and height of the GraphView to the minimum of the two values
        int size = Math.min(screenWidth, screenHeight);
        graph.setLayoutParams(new LinearLayout.LayoutParams(size, size));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showCirclePlot(double a, double h, double b, double g, double f, double c) {
        double radius = Math.sqrt(((g* g)+(f*f)) - a*b*c);
        double centerX = -g / a;
        double centerY = -f / b;

        double x1 = centerX + radius;
        double y1 = centerY;
        double x2 = centerX - radius;
        double y2 = centerY;

        double minX = centerX - radius-10;
        double maxX = centerX + radius+10;
        double minY = centerY - radius-10;
        double maxY = centerY + radius+10;


        double x, y;
        DataPoint[] points = new DataPoint[360];
        for (int i = 0; i < 360; i++) {
            double theta = Math.toRadians(i);
            x = centerX + radius * Math.cos(theta);
            y = centerY + radius * Math.sin(theta);
            points[i] = new DataPoint(x, y);
        }

        // Sort the DataPoint array in ascending order of X values
        Arrays.sort(points, Comparator.comparingDouble(DataPoint::getX));

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        series.setColor(Color.BLUE);
        series.setThickness(2);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(minY);
        graph.getViewport().setMaxY(maxY);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setBorderColor(Color.BLACK);
        graph.getViewport().setBackgroundColor(Color.WHITE);

        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);

        //graph.getViewport().setViewportRatio(1);

        /*StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"x-axis", "second label"});
        staticLabelsFormatter.setVerticalLabels(new String[]{"y-axis"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);*/

        graph.addSeries(series);
        graph.setOnTouchListener((v, event) -> true);
    }
}
