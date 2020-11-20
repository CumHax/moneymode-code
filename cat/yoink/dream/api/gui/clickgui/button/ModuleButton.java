//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import cat.yoink.dream.api.util.font.FontUtil;
import cat.yoink.dream.api.setting.SettingManager;
import java.awt.Color;
import java.util.Iterator;
import cat.yoink.dream.api.gui.clickgui.button.settings.BindButton;
import cat.yoink.dream.api.gui.clickgui.button.settings.EnumButton;
import cat.yoink.dream.api.gui.clickgui.button.settings.SliderButton;
import cat.yoink.dream.api.gui.clickgui.button.settings.BoolButton;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.setting.Setting;
import cat.cattyn.moneymod.money;
import java.util.ArrayList;
import cat.yoink.dream.api.module.Module;
import net.minecraft.client.Minecraft;

public class ModuleButton
{
    private final Minecraft mc;
    private final Module module;
    private final ArrayList<SettingButton> buttons;
    private final int W;
    private final int H;
    private int X;
    private int Y;
    private boolean open;
    private int showingModuleCount;
    private boolean opening;
    private boolean closing;
    
    public ModuleButton(final Module module, final int x, final int y, final int w, final int h) {
        this.mc = Minecraft.getMinecraft();
        this.buttons = new ArrayList<SettingButton>();
        this.module = module;
        this.X = x;
        this.Y = y;
        this.W = w;
        this.H = h;
        int n = 0;
        for (final Setting setting : money.settingManager.getSettings(module)) {
            SettingButton settingButton = null;
            if (setting.getType().equals(SettingType.BOOLEAN)) {
                settingButton = new BoolButton(module, setting, this.X, this.Y + this.H + n, this.W, this.H);
            }
            else if (setting.getType().equals(SettingType.INTEGER)) {
                settingButton = new SliderButton.IntSlider(module, setting, this.X, this.Y + this.H + n, this.W, this.H);
            }
            else if (setting.getType().equals(SettingType.ENUM)) {
                settingButton = new EnumButton(module, setting, this.X, this.Y + this.H + n, this.W, this.H);
            }
            if (settingButton != null) {
                this.buttons.add(settingButton);
                n += this.H;
            }
        }
        this.buttons.add(new BindButton(module, this.X, this.Y + this.H + n, this.W, this.H));
    }
    
    public void render(final int mX, final int mY) {
        if (this.module.isEnabled()) {
            if (this.isHover(this.X, this.Y, this.W, this.H - 1, mX, mY)) {
                money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 170).getRGB());
            }
            else {
                money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 150).getRGB());
            }
            final String name = this.module.getName();
            final double x = (float)(this.X + 5);
            final double y = (float)(this.Y + 4);
            final SettingManager settingManager = money.settingManager;
            final int integerValue = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
            final SettingManager settingManager2 = money.settingManager;
            final int integerValue2 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
            final SettingManager settingManager3 = money.settingManager;
            FontUtil.drawStringWithShadow(name, x, y, new Color(integerValue, integerValue2, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 255).getRGB());
        }
        else {
            if (this.isHover(this.X, this.Y, this.W, this.H - 1, mX, mY)) {
                money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 171).getRGB());
            }
            else {
                money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 151).getRGB());
            }
            FontUtil.drawString(this.module.getName(), (float)(this.X + 5), (float)(this.Y + 4), new Color(255, 255, 255, 255).getRGB());
        }
        if (this.opening) {
            ++this.showingModuleCount;
            if (this.showingModuleCount == this.buttons.size()) {
                this.opening = false;
                this.open = true;
            }
        }
        if (this.closing) {
            --this.showingModuleCount;
            if (this.showingModuleCount == 0) {
                this.closing = false;
                this.open = false;
            }
        }
        if (this.isHover(this.X, this.Y, this.W, this.H - 1, mX, mY) && this.module.getDescription() != null && !this.module.getDescription().equals("")) {
            FontUtil.drawStringWithShadow(this.module.getDescription(), 2.0, new ScaledResolution(this.mc).getScaledHeight() - FontUtil.getFontHeight() - 2, new Color(-221985596, true).getRGB());
        }
    }
    
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.X, this.Y, this.W, this.H - 1, mX, mY)) {
            if (mB == 0) {
                this.module.toggle();
                if (this.module.getName().equals("ClickGUI")) {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (mB == 1) {
                this.processRightClick();
            }
        }
        if (this.open) {
            for (final SettingButton settingButton : this.buttons) {
                settingButton.mouseDown(mX, mY, mB);
            }
        }
    }
    
    public void mouseUp(final int mX, final int mY) {
        for (final SettingButton settingButton : this.buttons) {
            settingButton.mouseUp(mX, mY);
        }
    }
    
    public void keyPress(final int key) {
        for (final SettingButton settingButton : this.buttons) {
            settingButton.keyPress(key);
        }
    }
    
    public void close() {
        for (final SettingButton button : this.buttons) {
            button.close();
        }
    }
    
    private boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
    
    public void setX(final int x) {
        this.X = x;
    }
    
    public void setY(final int y) {
        this.Y = y;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public ArrayList<SettingButton> getButtons() {
        return this.buttons;
    }
    
    public int getShowingModuleCount() {
        return this.showingModuleCount;
    }
    
    public boolean isOpening() {
        return this.opening;
    }
    
    public boolean isClosing() {
        return this.closing;
    }
    
    public void processRightClick() {
        if (!this.open) {
            this.showingModuleCount = 0;
            this.opening = true;
        }
        else {
            this.showingModuleCount = this.buttons.size();
            this.closing = true;
        }
    }
}
