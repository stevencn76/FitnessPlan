package truestrength.fitnessplan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;
import truestrength.fitnessplan.R;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.service.DataService;
import truestrength.fitnessplan.util.DateUtil;

public class PlansSummaryActivity extends AppCompatActivity {
    private ColumnChartView chartView;
    private PreviewColumnChartView previewChartView;
    private ColumnChartData data;
    private ColumnChartData previewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planssummary);

        chartView = (ColumnChartView)findViewById(R.id.chartView);
        previewChartView = (PreviewColumnChartView)findViewById(R.id.previewChartView);

        load();

        chartView.setColumnChartData(data);
        chartView.setZoomEnabled(false);
        chartView.setScrollEnabled(false);

        previewChartView.setColumnChartData(previewData);
        previewChartView.setViewportChangeListener(new ViewportListener());

        previewX(false);

        setTitle("Summary For Plans");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load() {
        List<Plan> planList = DataService.getInstance(this).getAllPlans();

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;

        for(Plan tp : planList) {
            values = new ArrayList<SubcolumnValue>();
            Log.i(MySettings.LOG_TAG, "progress value : " + tp.getProgress());
            SubcolumnValue tsc = new SubcolumnValue(tp.getProgress(), ChartUtils.pickColor());
            tsc.setLabel(DateUtil.getDayMonth(tp.getSqlStartDate()));
            values.add(tsc);
            Column tc = new Column(values);
            tc.setHasLabels(true);
            columns.add(tc);

        }

        data = new ColumnChartData(columns);
        Axis axisX = new Axis();
        axisX.setName("Plan");
        List<AxisValue> axisValuesX = new LinkedList<>();
        axisX.setValues(axisValuesX);
        data.setAxisXBottom(axisX);
        axisX.setHasTiltedLabels(true);

        Axis axisY = new Axis();
        axisY.setHasLines(true);
//        axisY.setName("Percentage");
        List<AxisValue> axisValuesY = new LinkedList<>();
        AxisValue av0 = new AxisValue(0f);
        av0.setLabel("0%");
        axisValuesY.add(av0);
        AxisValue av1 = new AxisValue(25f);
        av1.setLabel("25%");
        axisValuesY.add(av1);
        AxisValue av2 = new AxisValue(50f);
        av2.setLabel("50%");
        axisValuesY.add(av2);
        AxisValue av3 = new AxisValue(75f);
        av3.setLabel("75%");
        axisValuesY.add(av3);
        AxisValue av4 = new AxisValue(100f);
        av4.setLabel("100%");
        axisValuesY.add(av4);
        axisY.setValues(axisValuesY);
        data.setAxisYLeft(axisY);

        previewData = new ColumnChartData(data);
        for (Column column : previewData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }
    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chartView.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChartView.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChartView.setCurrentViewport(tempViewport);
        }
        previewChartView.setZoomType(ZoomType.HORIZONTAL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            chartView.setCurrentViewport(newViewport);
        }

    }
}
