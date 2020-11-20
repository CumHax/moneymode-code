//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button.settings;

import java.text.DecimalFormat;
import cat.yoink.dream.api.util.font.FontUtil;
import cat.yoink.dream.api.gui.clickgui.ClickGUI;
import cat.yoink.dream.api.setting.SettingManager;
import java.awt.Color;
import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;

public class SliderButton extends SettingButton
{
    private final Setting setting;
    protected boolean dragging;
    protected int sliderWidth;
    
    SliderButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.dragging = false;
        this.sliderWidth = 0;
        this.setting = setting;
    }
    
    protected void updateSlider(final int mouseX) {
    }
    
    @Override
    public void render(final int mX, final int mY) {
        int x = this.getX();
        final int y = this.getY();
        int w = this.getW();
        final int h = this.getH();
        this.updateSlider(mX);
        if (this.isHover(x, y, w, h - 1, mX, mY)) {
            money.clickGUI.drawGradient(x + this.sliderWidth + 6, y, x + w, y + h, new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 170).getRGB());
            final ClickGUI clickGUI = money.clickGUI;
            final int left = x;
            final int top = y;
            final int right = x + this.sliderWidth + 6;
            final int bottom = y + h;
            final SettingManager settingManager = money.settingManager;
            final int integerValue = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager2 = money.settingManager;
            final int integerValue2 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager3 = money.settingManager;
            final int rgb = new Color(integerValue, integerValue2, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 255).getRGB();
            final SettingManager settingManager4 = money.settingManager;
            final int integerValue3 = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager5 = money.settingManager;
            final int integerValue4 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager6 = money.settingManager;
            clickGUI.drawGradient(left, top, right, bottom, rgb, new Color(integerValue3, integerValue4, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 255).getRGB());
        }
        else {
            money.clickGUI.drawGradient(x + this.sliderWidth + 6, y, x + w, y + h, new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 150).getRGB());
            final ClickGUI clickGUI2 = money.clickGUI;
            final int left2 = x;
            final int top2 = y;
            final int right2 = x + this.sliderWidth + 6;
            final int bottom2 = y + h;
            final SettingManager settingManager7 = money.settingManager;
            final int integerValue5 = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager8 = money.settingManager;
            final int integerValue6 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager9 = money.settingManager;
            final int rgb2 = new Color(integerValue5, integerValue6, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 235).getRGB();
            final SettingManager settingManager10 = money.settingManager;
            final int integerValue7 = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager11 = money.settingManager;
            final int integerValue8 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager12 = money.settingManager;
            clickGUI2.drawGradient(left2, top2, right2, bottom2, rgb2, new Color(integerValue7, integerValue8, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 235).getRGB());
        }
        final ClickGUI clickGUI3 = money.clickGUI;
        final int n = x;
        final int n2 = y;
        final int n3 = x + 6;
        final int n4 = y + h;
        final SettingManager settingManager13 = money.settingManager;
        final int integerValue9 = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
        final SettingManager settingManager14 = money.settingManager;
        final int integerValue10 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
        final SettingManager settingManager15 = money.settingManager;
        ClickGUI.drawRect(n, n2, n3, n4, new Color(integerValue9, integerValue10, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 100).getRGB());
        x += 6;
        w -= 6;
        FontUtil.drawStringWithShadow(this.setting.getName(), (float)(x + 1), (float)(y + 4), new Color(255, 255, 255, 255).getRGB());
        FontUtil.drawStringWithShadow(String.valueOf(this.setting.getIntegerValue()), (float)(x + w - 6 - FontUtil.getStringWidth(String.valueOf(this.setting.getIntegerValue()))), (float)(y + 4), new Color(255, 255, 255, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseUp(final int mouseX, final int mouseY) {
        this.dragging = false;
    }
    
    @Override
    public void close() {
        this.dragging = false;
    }
    
    public static class IntSlider extends SliderButton
    {
        private final Setting intSetting;
        
        public IntSlider(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
            super(module, setting, X, Y, W, H);
            this.intSetting = setting;
        }
        
        @Override
        protected void updateSlider(final int mouseX) {
            final double diff = Math.min(this.getW(), Math.max(0, mouseX - this.getX()));
            final double min = this.intSetting.getMinIntegerValue();
            final double max = this.intSetting.getMaxIntegerValue();
            this.sliderWidth = (int)((this.getW() - 6) * (this.intSetting.getIntegerValue() - min) / (max - min));
            if (this.dragging) {
                if (diff == 0.0) {
                    this.intSetting.setIntegerValue(this.intSetting.getIntegerValue());
                }
                else {
                    final DecimalFormat format = new DecimalFormat("##");
                    final String newValue = format.format(diff / this.getW() * (max - min) + min);
                    this.intSetting.setIntegerValue(Integer.parseInt(newValue));
                }
            }
        }
    }
}
