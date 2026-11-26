package org.telegram.ui.neon;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.LinearGradient;
import android.graphics.Shader;
public final class NeonBubbleOverlay {
    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF bounds = new RectF();
    public void draw(Canvas c, Path path, float density, int alpha) {
        path.computeBounds(bounds, true);
        if (bounds.height() <= 0 || bounds.width() <= 0 || alpha <= 0) return;
        p.setShader(null);
        p.setStyle(Paint.Style.STROKE);
        for (int i=4;i>=1;i--) {
            p.setStrokeWidth(i * 2 * density);
            p.setColor(0xffff2340);
            p.setAlpha(alpha * (5-i) / 32);
            c.drawPath(path,p);
        }
        p.setStrokeWidth(density);
        p.setAlpha(alpha * 3 / 4);
        c.drawPath(path,p);
        p.setStyle(Paint.Style.FILL);
        p.setAlpha(255);
        p.setShader(new LinearGradient(0,bounds.top,0,bounds.bottom,
            new int[]{(alpha/6 << 24)|0xffffff,0x00ffffff,0x00000000},
            new float[]{0f,0.3f,1f},Shader.TileMode.CLAMP));
        c.drawPath(path,p);
        p.setShader(null);
    }
}
