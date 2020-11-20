//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.FontRenderer;
import cat.cattyn.moneymod.money;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.Minecraft;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.util.Colors;
import java.awt.Color;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Watermark extends Module
{
    private final Setting ver;
    private final Setting rainbow;
    private final Setting sat;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting x;
    private final Setting y;
    Color c;
    private Colors colors;
    
    public Watermark(final String name, final String description, final Category category) {
        super(name, description, category);
        this.ver = new Setting.Builder(SettingType.BOOLEAN).setName("Version").setModule(this).setBooleanValue(true).build();
        this.rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.sat = new Setting.Builder(SettingType.INTEGER).setName("RainbowSat").setModule(this).setIntegerValue(6).setMinIntegerValue(1).setMaxIntegerValue(9).build();
        this.red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(210).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.x = new Setting.Builder(SettingType.INTEGER).setName("X").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(900).build();
        this.y = new Setting.Builder(SettingType.INTEGER).setName("Y").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(540).build();
        this.colors = new Colors();
        this.addSetting(this.ver);
        this.addSetting(this.rainbow);
        this.addSetting(this.sat);
        this.addSetting(this.red);
        this.addSetting(this.green);
        this.addSetting(this.blue);
        this.addSetting(this.x);
        this.addSetting(this.y);
    }
    
    public void renderStringWave(final String s, final int x, final int y, final float bright) {
        int updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            final String str = s.charAt(i) + "";
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, (float)updateX, (float)y, this.colors.effect(i * 3500000L, bright, 100).getRGB());
            updateX += Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i));
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent egoe) {
        if (egoe.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            String Watermark;
            if (this.ver.getBooleanValue()) {
                Watermark = money.MODNAME + " " + money.MODVER;
            }
            else {
                Watermark = money.MODNAME;
            }
            if (this.rainbow.getBooleanValue()) {
                this.renderStringWave(Watermark, this.x.getIntegerValue(), this.y.getIntegerValue(), this.sat.getIntegerValue() / 10.0f);
            }
            else {
                this.c = new Color(this.red.getIntegerValue(), this.green.getIntegerValue(), this.blue.getIntegerValue());
                final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                fr.drawStringWithShadow(Watermark, (float)this.x.getIntegerValue(), (float)this.y.getIntegerValue(), this.c.getRGB());
            }
        }
    }
}
