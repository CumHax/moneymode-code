//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.FontRenderer;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import cat.yoink.dream.api.module.ModuleManager;
import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cat.yoink.dream.api.setting.SettingManager;
import cat.cattyn.moneymod.money;
import net.minecraft.client.Minecraft;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.util.Colors;
import java.awt.Color;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class ActiveModules extends Module
{
    private final Setting ForgeHax;
    private final Setting Rainbow;
    private final Setting RainbowSat;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    Color c;
    private Colors colors;
    
    public ActiveModules(final String name, final String description, final Category category) {
        super(name, description, category);
        this.ForgeHax = new Setting.Builder(SettingType.BOOLEAN).setName("ForgeHax").setModule(this).setBooleanValue(false).build();
        this.Rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.RainbowSat = new Setting.Builder(SettingType.INTEGER).setName("Rainbow Sat").setModule(this).setIntegerValue(6).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(210).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.colors = new Colors();
        this.addSetting(this.ForgeHax);
        this.addSetting(this.Rainbow);
        this.addSetting(this.RainbowSat);
        this.addSetting(this.red);
        this.addSetting(this.green);
        this.addSetting(this.blue);
    }
    
    public void renderStringWave(final String s, final int x, final int y, final float bright) {
        int updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            final String str = s.charAt(i) + "";
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, (float)updateX, (float)y, this.colors.effect(i * 3500000L, bright, 100).getRGB());
            updateX += Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i));
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], 1.0f, 1.0f);
        final SettingManager settingManager = money.settingManager;
        SettingManager.getSetting("ActiveModules", "Red").setIntegerValue(color_rgb_o >> 16 & 0xFF);
        final SettingManager settingManager2 = money.settingManager;
        SettingManager.getSetting("ActiveModules", "Green").setIntegerValue(color_rgb_o >> 8 & 0xFF);
        final SettingManager settingManager3 = money.settingManager;
        SettingManager.getSetting("ActiveModules", "Blue").setIntegerValue(color_rgb_o & 0xFF);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent egoe) {
        if (egoe.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            this.c = new Color(this.red.getIntegerValue(), this.green.getIntegerValue(), this.blue.getIntegerValue());
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int y = 2;
            final ArrayList<String> list = new ArrayList<String>();
            for (final Module mod : ModuleManager.getModules()) {
                if (mod.isEnabled()) {
                    list.add(mod.getName());
                }
            }
            list.sort((s1, s2) -> Minecraft.getMinecraft().fontRenderer.getStringWidth(s1) - Minecraft.getMinecraft().fontRenderer.getStringWidth(s2));
            Collections.reverse(list);
            for (final String name : list) {
                if (this.Rainbow.getBooleanValue()) {
                    this.cycle_rainbow();
                }
                if (this.ForgeHax.getBooleanValue()) {
                    final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                    fr.drawStringWithShadow(name + "<", (float)(sr.getScaledWidth() - fr.getStringWidth(name) - 6), (float)y, new Color(this.red.getIntegerValue(), this.green.getIntegerValue(), this.blue.getIntegerValue(), 255).getRGB());
                    y += fr.FONT_HEIGHT;
                }
                else {
                    final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                    fr.drawStringWithShadow(name, (float)(sr.getScaledWidth() - fr.getStringWidth(name) - 1), (float)y, new Color(this.red.getIntegerValue(), this.green.getIntegerValue(), this.blue.getIntegerValue(), 255).getRGB());
                    y += fr.FONT_HEIGHT;
                }
            }
        }
    }
}
