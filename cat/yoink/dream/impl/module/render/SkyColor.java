// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.Color;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class SkyColor extends Module
{
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting rainbow;
    
    public SkyColor(final String name, final String description, final Category category) {
        super(name, description, category);
        this.red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.addSetting(this.red);
        this.addSetting(this.green);
        this.addSetting(this.blue);
        this.addSetting(this.rainbow);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onFogColorRender(final EntityViewRenderEvent.FogColors event) {
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        final int r = rgb >> 16 & 0xFF;
        final int g = rgb >> 8 & 0xFF;
        final int b = rgb & 0xFF;
        if (this.rainbow.getBooleanValue()) {
            event.setRed(r / 255.0f);
            event.setGreen(g / 255.0f);
            event.setBlue(b / 255.0f);
        }
        else {
            event.setRed(this.red.getIntegerValue() / 255.0f);
            event.setGreen(this.green.getIntegerValue() / 255.0f);
            event.setBlue(this.blue.getIntegerValue() / 255.0f);
        }
    }
    
    @SubscribeEvent
    public void fog(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
}
