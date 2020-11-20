// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button.settings;

import org.lwjgl.input.Keyboard;
import cat.yoink.dream.api.util.font.FontUtil;
import java.awt.Color;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;

public class BindButton extends SettingButton
{
    private final Module module;
    private boolean binding;
    
    public BindButton(final Module module, final int x, final int y, final int w, final int h) {
        super(module, x, y, w, h);
        this.module = module;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        this.drawButton(mX, mY);
        FontUtil.drawStringWithShadow("Bind", (float)(this.getX() + 6), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        if (this.binding) {
            FontUtil.drawStringWithShadow("...", (float)(this.getX() + this.getW() - 6 - FontUtil.getStringWidth("...")), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        }
        else {
            try {
                FontUtil.drawStringWithShadow(Keyboard.getKeyName(this.module.getBind()), (float)(this.getX() + this.getW() - 6 - FontUtil.getStringWidth(Keyboard.getKeyName(this.module.getBind()))), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
            }
            catch (Exception e) {
                FontUtil.drawStringWithShadow("NONE", (float)(this.getX() + this.getW() - 6 - FontUtil.getStringWidth("NONE")), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
            }
        }
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyPress(final int key) {
        if (this.binding) {
            if (key == 211 || key == 14 || key == 0) {
                this.getModule().setBind(256);
            }
            else {
                this.getModule().setBind(key);
            }
            this.binding = false;
        }
    }
}
