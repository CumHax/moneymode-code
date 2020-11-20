// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util;

import java.awt.Color;

public class Colors
{
    public Color effect(final long offset, final float brightness, final int speed) {
        final float hue = (System.nanoTime() + offset * speed) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, brightness, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }
}
