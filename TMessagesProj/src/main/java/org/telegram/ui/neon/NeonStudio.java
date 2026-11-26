package org.telegram.ui.neon;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;

public final class NeonStudio {
    private NeonStudio() {}
    public static void show(Context context, Runnable onSaved) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int)(20 * context.getResources().getDisplayMetrics().density);
        layout.setPadding(pad,pad,pad,pad);
        Switch enabled = new Switch(context);
        enabled.setText("Enable Neon Glow");
        enabled.setChecked(NeonPreferences.enabled(context));
        layout.addView(enabled);
        TextView label = new TextView(context);
        SeekBar intensity = new SeekBar(context);
        intensity.setMax(100);
        intensity.setProgress(NeonPreferences.intensity(context));
        label.setText("Glow intensity: " + intensity.getProgress() + "%");
        layout.addView(label);
        layout.addView(intensity);
        intensity.setEnabled(enabled.isChecked());
        enabled.setOnCheckedChangeListener((button, checked) -> intensity.setEnabled(checked));
        intensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar bar, int value, boolean user) {
                label.setText("Glow intensity: " + value + "%");
            }
            public void onStartTrackingTouch(SeekBar bar) {}
            public void onStopTrackingTouch(SeekBar bar) {}
        });
        new AlertDialog.Builder(context).setTitle("GROMANOF Neon Studio")
            .setView(layout).setNegativeButton("Cancel", null)
            .setPositiveButton("Save", (dialog, which) -> {
                NeonPreferences.save(context, enabled.isChecked(), intensity.getProgress());
                if (onSaved != null) onSaved.run();
            }).show();
    }
}
