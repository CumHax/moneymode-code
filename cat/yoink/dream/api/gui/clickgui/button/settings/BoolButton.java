// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button.settings;

import cat.yoink.dream.api.util.font.FontUtil;
import java.awt.Color;
import cat.yoink.dream.api.setting.SettingManager;
import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;

public class BoolButton extends SettingButton
{
    private final Setting setting;
    
    public BoolButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.setting = setting;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        if (this.setting.getBooleanValue()) {
            this.drawButton(mX, mY);
            final String name = this.setting.getName();
            final double x = (float)(this.getX() + 6);
            final double y = (float)(this.getY() + 4);
            final SettingManager settingManager = money.settingManager;
            final int integerValue = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager2 = money.settingManager;
            final int integerValue2 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager3 = money.settingManager;
            FontUtil.drawStringWithShadow(name, x, y, new Color(integerValue, integerValue2, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 232).getRGB());
        }
        else {
            if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
                money.clickGUI.drawGradient(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH(), new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 170).getRGB());
            }
            else {
                money.clickGUI.drawGradient(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH(), new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 150).getRGB());
            }
            FontUtil.drawString(this.setting.getName(), (float)(this.getX() + 6), (float)(this.getY() + 4), new Color(255, 255, 255, 232).getRGB());
        }
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY) && (mB == 0 || mB == 1)) {
            this.setting.setBooleanValue(!this.setting.getBooleanValue());
        }
    }
}
