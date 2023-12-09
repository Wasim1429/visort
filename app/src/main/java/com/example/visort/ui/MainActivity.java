package com.example.visort.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visort.R;
import com.example.visort.core.algorithm.BubbleSort;
import com.example.visort.core.algorithm.CountSort;
import com.example.visort.core.algorithm.HeapSort;
import com.example.visort.core.algorithm.InsertionSort;
import com.example.visort.core.algorithm.MergeSort;
import com.example.visort.core.algorithm.PingeonholeSort;
import com.example.visort.core.algorithm.QuickSort;
import com.example.visort.core.algorithm.SelectionSort;
import com.example.visort.core.algorithm.ShellSort;
import com.example.visort.core.algorithm.ThreadState;
import com.example.visort.data.models.BarchartModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public com.github.mikephil.charting.charts.BarChart barChart;
    private ArrayList<BarEntry> barEntryArrayList;
    private ArrayList<String> label_names;
    private ArrayList<BarchartModel> barChartModelArrayList;
    private int LIMIT = 0;
    private int ALGORITHM_INDEX = 0;
    private CharSequence[] mSortingAlgorithms, mAnimationTimes;
    private MaterialButton choose_algorithm, view_algorithm;
    private Slider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose_algorithm = findViewById(R.id.choose_algorithm);
        view_algorithm = findViewById(R.id.view_algorithm);
        barChart = findViewById(R.id.bar_chart);
        slider = findViewById(R.id.slider);

        view_algorithm.setEnabled(false);
        choose_algorithm.setEnabled(false);

        barEntryArrayList = new ArrayList<>();
        label_names = new ArrayList<>();
        barChartModelArrayList = new ArrayList<>();
        mSortingAlgorithms = new CharSequence[]{
                "Insertion Sort Algorithm",
                "Bubble Sort Algorithm",
                "Merge Sort Algorithm",
                "Quick Sort Algorithm",
                "Shell Sort Algorithm",
                "Selection Sort Algorithm",
                "Heap Sort Algorithm",
                "Count Sort Algorithm",
//                "Bucket Sort Algorithm",
                "Pigeonhole Sort Algorithm"
        };
        mAnimationTimes = new CharSequence[]{
                "50 milliseconds",
                "100 milliseconds",
                "150 milliseconds",
                "200 milliseconds",
                "250 milliseconds"
        };

        slider.addOnChangeListener((slider, value, fromUser) -> {
            if (value > 0) {
                randomizeGraph((int) value);
            } else {
                slider.setValue((float) 20);
                randomizeGraph(20);
            }

        });
        choose_algorithm.setOnClickListener(v -> chooseAlgorithm());
        view_algorithm.setOnClickListener(v -> chooseAnimationTime());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ThreadState.threadAlive) {
            slider.setValue((float) 20);
            randomizeGraph(20);
        }
    }

    private void visualize() {
        if (!ThreadState.threadAlive) {
            if (LIMIT > 0) {
                choose_algorithm.setEnabled(false);
                view_algorithm.setEnabled(false);
                slider.setEnabled(false);
                barChart.setEnabled(false);
                switch (ALGORITHM_INDEX) {
                    case 0: {
                        new InsertionSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 1: {
                        new BubbleSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 2: {
                        new MergeSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 3: {
                        new QuickSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 4: {
                        new ShellSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 5: {
                        new SelectionSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 6: {
                        new HeapSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    case 7: {
                        new CountSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
//                    case 8: {
//                        new BucketSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
//                        break;
//                    }
                    case 8: {
                        new PingeonholeSort(barChart, barEntryArrayList, label_names, barChartModelArrayList, slider, this);
                        break;
                    }
                    default:
                        Toast.makeText(this, "Internal Error Occurred. Please Try Again", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Internal Error Occurred. Please Try Again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "An algorithm is in process. Terminate the app to set up another algorithm", Toast.LENGTH_SHORT).show();

        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi"})
    private void chooseAlgorithm() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Choose Sorting Algorithm");
        builder.setSingleChoiceItems(mSortingAlgorithms, ALGORITHM_INDEX, (dialog, which) -> {
            ALGORITHM_INDEX = which;
            view_algorithm.setEnabled(true);
            dialog.dismiss();
        });
        builder.show();

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi"})
    private void chooseAnimationTime() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Choose Animation Delay Time");
        builder.setSingleChoiceItems(mAnimationTimes, 0, (dialog, which) -> {
            switch (which) {
                case 0:
                    ThreadState.delayTime = 50;
                    break;
                case 1:
                    ThreadState.delayTime = 100;
                    break;
                case 2:
                    ThreadState.delayTime = 150;
                    break;
                case 4:
                    ThreadState.delayTime = 200;
                    break;
                case 5:
                    ThreadState.delayTime = 250;
                    break;
            }
            dialog.dismiss();
            visualize();
        });
        builder.show();

    }

    private void randomizeGraph(int value) {
        barChartModelArrayList.clear();
        choose_algorithm.setEnabled(true);
        LIMIT = value;
        if (LIMIT > 0) {
            for (int i = 0; i < LIMIT; i++) {
                int randomValue = new Random().nextInt(1000);
                barChartModelArrayList.add(new BarchartModel(String.valueOf(i), randomValue));
            }
            displayGraph();
        }

    }

    private void displayGraph() {
        barEntryArrayList.clear();
        label_names.clear();

        for (int i = 0; i < barChartModelArrayList.size(); i++) {
            int val = barChartModelArrayList.get(i).getValue();
            barEntryArrayList.add(new BarEntry(i, val));
            label_names.add(" ");
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, " ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText(" ");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_names));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(label_names.size());
        barChart.invalidate();
    }
}