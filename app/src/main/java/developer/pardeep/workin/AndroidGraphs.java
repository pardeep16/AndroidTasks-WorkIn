package developer.pardeep.workin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class AndroidGraphs extends AppCompatActivity {

    LineChart lineChart,lineChartSecond;
    PieChart pieChart;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_graphs);
          Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);


          BarChart chart=(BarChart)findViewById(R.id.barChart);
          lineChart=(LineChart)findViewById(R.id.lineChart);

          pieChart=(PieChart)findViewById(R.id.pieChart);

          //BarData data = new BarData(getXAxisValues(), getDataSet());
          //chart.setData(data);

        /*
        Bar Graph
         */
          BarData barData=new BarData(getXAxisValues(),getDataSet());
          chart.setData(barData);
          chart.setDescription("My Chart");
          chart.animateXY(2000, 2000);
          chart.invalidate();



        /*
        Line Chart
         */
          ArrayList<Entry> lineChartEntry=new ArrayList<>();
          lineChartEntry.add(new Entry(4f,0));
          lineChartEntry.add(new Entry(5f,1));
          lineChartEntry.add(new Entry(2f,2));
          lineChartEntry.add(new Entry(3f,3));
          lineChartEntry.add(new Entry(8f,4));
          LineDataSet line=new LineDataSet(lineChartEntry,"Line Chart1");
          ArrayList<LineDataSet> arrayLineDataSet=new ArrayList<>();
          arrayLineDataSet.add(line);



          ArrayList<String> lineChartLabels=new ArrayList<>();
          lineChartLabels.add("jan");
          lineChartLabels.add("Feb");
          lineChartLabels.add("march");
          lineChartLabels.add("April");
          lineChartLabels.add("May");

          LineData lineData=new LineData(lineChartLabels,arrayLineDataSet);
          lineChart.setData(lineData);
          lineChart.setDescription("Line Chart");
          //lineChart.animateXY(200,200);
          lineChart.invalidate();


        /*
        Cubic Line Chart
         */
          lineChartSecond=(LineChart)findViewById(R.id.lineChartCubic);
          LineDataSet lineDataSet2=new LineDataSet(lineChartEntry,"Line chart");
          lineDataSet2.setDrawCircleHole(true);
          ArrayList<LineDataSet> arrayList=new ArrayList<>();
          arrayList.add(lineDataSet2);

          LineData lineData2=new LineData(lineChartLabels,arrayList);

          lineChartSecond.setData(lineData2);
          lineChartSecond.setDescription("Line Chart 2");
          lineChartSecond.invalidate();



        /*
            Pie Chart
         */
          int[] colors={Color.BLUE,Color.RED,Color.GREEN,Color.CYAN};

          ArrayList<Entry> entryArrayList=new ArrayList<>();
          entryArrayList.add(new Entry(25f,0));
          entryArrayList.add(new Entry(30f,1));
          entryArrayList.add(new Entry(30f,2));
          entryArrayList.add(new Entry(10f,3));
          ArrayList<PieDataSet> pieDataSetArrayList=new ArrayList<>();
          PieDataSet pieDataSet=new PieDataSet(entryArrayList,"Pie chart");
          pieDataSet.setColors(colors);
          pieDataSetArrayList.add(pieDataSet);

          ArrayList<String> stringArrayList=new ArrayList<>();
          stringArrayList.add("Proteins");
          stringArrayList.add("Carbohydrates");
          stringArrayList.add("Vegetables");
          stringArrayList.add("Deliciousness");

          PieData pieData=new PieData(stringArrayList,pieDataSet);
          pieChart.setData(pieData);
          pieChart.setDescription("A Healthy Meal");

    }
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> barDataSet=null;
        ArrayList<BarEntry> barEntry=new ArrayList<>();

        BarEntry bar1=new BarEntry(110.0f,1);
        barEntry.add(bar1);
        BarEntry bar2=new BarEntry(100.0f,2);
        barEntry.add(bar2);
        BarEntry bar3=new BarEntry(120.0f,3);
        barEntry.add(bar3);

        BarEntry bar4=new BarEntry(50.0f,4);
        barEntry.add(bar4);

        BarDataSet barData=new BarDataSet(barEntry,"Bar 1");
        barData.setColor(Color.rgb(0, 155, 0));

        barDataSet=new ArrayList<>();
        barDataSet.add(barData);
        return barDataSet;

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Jan");
        arrayList.add("Feb");
        arrayList.add("March");
        arrayList.add("April");

        return arrayList;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AndroidGraphs.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
