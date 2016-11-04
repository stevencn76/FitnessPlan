package truestrength.fitnessplan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;
import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.service.DataService;

public class DaysSummaryActivity extends AppCompatActivity {
    private ColumnChartView chartView;
    private PreviewColumnChartView previewChartView;
    private ColumnChartData data;
    private ColumnChartData previewData;

    private Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayssummary);

        plan = (Plan)getIntent().getSerializableExtra("plan");

        chartView = (ColumnChartView)findViewById(R.id.chartView);
        previewChartView = (PreviewColumnChartView)findViewById(R.id.previewChartView);

        load();

        chartView.setColumnChartData(data);
        chartView.setZoomEnabled(false);
        chartView.setScrollEnabled(false);

        previewChartView.setColumnChartData(previewData);
        previewChartView.setViewportChangeListener(new ViewportListener());

        previewX(false);

        setTitle("Summary For Days");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load() {
        List<Day> dayList = DataService.getInstance(this).getDaysByPlan(plan.getId());

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;

        for(Day td : dayList) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(td.getProgress(), ChartUtils.pickColor()));
            columns.add(new Column(values));
        }

        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));

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
