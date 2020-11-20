//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util.font;

import net.minecraft.client.Minecraft;
import cat.yoink.dream.api.module.ModuleManager;
import java.awt.GraphicsEnvironment;
import cat.cattyn.moneymod.money;
import net.minecraft.client.gui.FontRenderer;

public class FontUtil
{
    private static final FontRenderer fontRenderer;
    
    public static int getStringWidth(final String text) {
        return customFont() ? (money.customFontRenderer.getStringWidth(text) + 3) : FontUtil.fontRenderer.getStringWidth(text);
    }
    
    public static void drawString(final String text, final double x, final double y, final int color) {
        if (customFont()) {
            money.customFontRenderer.drawString(text, x, y - 1.0, color, false);
        }
        else {
            FontUtil.fontRenderer.drawString(text, (int)x, (int)y, color);
        }
    }
    
    public static void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        if (customFont()) {
            money.customFontRenderer.drawStringWithShadow(text, x, y - 1.0, color);
        }
        else {
            FontUtil.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
    
    public static void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        if (customFont()) {
            money.customFontRenderer.drawCenteredStringWithShadow(text, x, y - 1.0f, color);
        }
        else {
            FontUtil.fontRenderer.drawStringWithShadow(text, x - FontUtil.fontRenderer.getStringWidth(text) / 2.0f, y, color);
        }
    }
    
    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        if (customFont()) {
            money.customFontRenderer.drawCenteredString(text, x, y - 1.0f, color);
        }
        else {
            FontUtil.fontRenderer.drawString(text, (int)(x - getStringWidth(text) / 2), (int)y, color);
        }
    }
    
    public static int getFontHeight() {
        return customFont() ? (money.customFontRenderer.fontHeight / 2 - 1) : FontUtil.fontRenderer.FONT_HEIGHT;
    }
    
    public static boolean validateFont(final String font) {
        for (final String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (s.equals(font)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean customFont() {
        final ModuleManager moduleManager = money.moduleManager;
        return ModuleManager.getModule("CustomFont").isEnabled();
    }
    
    public static float drawStringWithShadow2(final boolean customFont, final String text, final int x, final int y, final int color) {
        if (customFont) {
            return (float)Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
        return (float)Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    static {
        fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
}
