// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button.settings;

import java.util.Iterator;
import cat.yoink.dream.api.util.font.FontUtil;
import java.awt.Color;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;

public class EnumButton extends SettingButton
{
    private final Setting setting;
    
    public EnumButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.setting = setting;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        this.drawButton(mX, mY);
        FontUtil.drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 6), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        FontUtil.drawStringWithShadow(this.setting.getEnumValue(), (float)(this.getX() + this.getW() - 6 - FontUtil.getStringWidth(this.setting.getEnumValue())), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            if (mB == 0) {
                int i = 0;
                int enumIndex = 0;
                for (final String enumName : this.setting.getEnumValues()) {
                    if (enumName.equals(this.setting.getEnumValue())) {
                        enumIndex = i;
                    }
                    ++i;
                }
                if (enumIndex == this.setting.getEnumValues().size() - 1) {
                    this.setting.setEnumValue(this.setting.getEnumValues().get(0));
                }
                else {
                    ++enumIndex;
                    i = 0;
                    for (final String enumName : this.setting.getEnumValues()) {
                        if (i == enumIndex) {
                            this.setting.setEnumValue(enumName);
                        }
                        ++i;
                    }
                }
            }
            else if (mB == 1) {
                int i = 0;
                int enumIndex = 0;
                for (final String enumName : this.setting.getEnumValues()) {
                    if (enumName.equals(this.setting.getEnumValue())) {
                        enumIndex = i;
                    }
                    ++i;
                }
                if (enumIndex == 0) {
                    this.setting.setEnumValue(this.setting.getEnumValues().get(this.setting.getEnumValues().size() - 1));
                }
                else {
                    --enumIndex;
                    i = 0;
                    for (final String enumName : this.setting.getEnumValues()) {
                        if (i == enumIndex) {
                            this.setting.setEnumValue(enumName);
                        }
                        ++i;
                    }
                }
            }
        }
    }
}
