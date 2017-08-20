package test.dv.com.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private PanelView mPanelView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPanelView = (PanelView) findViewById(R.id.pv);
    }

    public void onSwitchPanel(View view) {
        if (mPanelView.getShowAction() == PanelView.SHOW_LEFT) {
            mPanelView.setShowAction(PanelView.SHOW_RIGHT);
        } else {
            mPanelView.setShowAction(PanelView.SHOW_LEFT);
        }
    }
}
