package org.telegram.ui.neon;

import android.content.Context;
import android.content.SharedPreferences;

public final class NeonPreferences {
    private NeonPreferences() {}
    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences("gromanof_neon", Context.MODE_PRIVATE);
    }
    public static boolean enabled(Context context) {
        return prefs(context).getBoolean("enabled", false);
    }
    public static int intensity(Context context) {
        return Math.max(0, Math.min(100, prefs(context).getInt("intensity", 65)));
    }
    public static void save(Context context, boolean enabled, int intensity) {
        prefs(context).edit().putBoolean("enabled", enabled)
            .putInt("intensity", Math.max(0, Math.min(100, intensity))).apply();
    }
}
