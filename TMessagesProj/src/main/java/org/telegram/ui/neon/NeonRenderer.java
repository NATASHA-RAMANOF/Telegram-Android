package org.telegram.ui.neon;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Color;

/** Experimental UI-only renderer. Call before drawing text; bounds are in pixels. */
public final class NeonRenderer {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    public void draw(Canvas canvas, RectF bounds, float density, boolean outgoing,
                     int accent, float glow, float gloss, float radiusDp) {
        if (bounds.width() <= 0 || bounds.height() <= 0) return;
        glow = Math.max(0f, Math.min(1f, glow));
        gloss = Math.max(0f, Math.min(1f, gloss));
        float radius = Math.max(0f, radiusDp * density);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 5; i >= 1; i--) {
            rect.set(bounds);
            rect.inset(-i * density, -i * density);
            paint.setStrokeWidth(2 * density);
            paint.setColor((accent & 0x00ffffff) | ((int)(glow * (24 - i * 3)) << 24));
            canvas.drawRoundRect(rect, radius + i * density, radius + i * density, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        int top = outgoing ? 0xff790d20 : 0xff302b30;
        int bottom = outgoing ? 0xff390710 : 0xff171417;
        paint.setShader(new LinearGradient(0, bounds.top, 0, bounds.bottom,
                top, bottom, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(bounds, radius, radius, paint);
        paint.setShader(null);
        paint.setColor(Color.WHITE);
        paint.setAlpha((int)(gloss * 45));
        rect.set(bounds.left + radius, bounds.top + density,
                 bounds.right - radius, bounds.top + 2 * density);
        if (rect.width() > 0) canvas.drawRoundRect(rect, density, density, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(density);
        paint.setColor((accent & 0x00ffffff) | ((int)(glow * 220) << 24));
        canvas.drawRoundRect(bounds, radius, radius, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255);
    }
}
