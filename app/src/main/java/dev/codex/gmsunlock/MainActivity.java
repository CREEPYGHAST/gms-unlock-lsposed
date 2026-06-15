package dev.codex.gmsunlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public final class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int padding = (int) (24 * getResources().getDisplayMetrics().density);
        TextView view = new TextView(this);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setPadding(padding, padding, padding, padding);
        view.setTextSize(16);
        view.setText(
            "GMS Unlock LSPosed\n\n"
                + "Enable this module in LSPosed, keep the Android System scope selected, "
                + "then reboot the device.\n\n"
                + "Removed system features:\n"
                + "- cn.google.services\n"
                + "- com.google.android.feature.services_updater"
        );

        setContentView(view);
    }
}
