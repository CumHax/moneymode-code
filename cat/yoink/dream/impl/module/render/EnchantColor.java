// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import cat.yoink.dream.api.module.ModuleManager;
import java.awt.Color;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class EnchantColor extends Module
{
    public final Setting r;
    public final Setting g;
    public final Setting b;
    public final Setting rainbow;
    
    public EnchantColor(final String name, final String description, final Category category) {
        super(name, description, category);
        this.r = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(120).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.g = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(120).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.b = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(120).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.addSetting(this.r);
        this.addSetting(this.g);
        this.addSetting(this.b);
        this.addSetting(this.rainbow);
    }
    
    public static Color getColor(final long offset, final float fade) {
        if (!((EnchantColor)ModuleManager.getModule("EnchantColor")).rainbow.getBooleanValue()) {
            return new Color(((EnchantColor)ModuleManager.getModule("EnchantColor")).r.getIntegerValue() / 255, ((EnchantColor)ModuleManager.getModule("EnchantColor")).g.getIntegerValue() / 255, ((EnchantColor)ModuleManager.getModule("EnchantColor")).b.getIntegerValue() / 255);
        }
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
}
