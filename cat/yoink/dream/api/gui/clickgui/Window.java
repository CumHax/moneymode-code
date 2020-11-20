//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui;

import cat.yoink.dream.api.gui.clickgui.button.SettingButton;
import cat.yoink.dream.api.util.font.FontUtil;
import net.minecraft.client.gui.Gui;
import cat.yoink.dream.api.setting.SettingManager;
import java.awt.Color;
import java.util.Iterator;
import cat.yoink.dream.api.module.Module;
import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.gui.clickgui.button.ModuleButton;
import java.util.ArrayList;

public class Window
{
    private final ArrayList<ModuleButton> buttons;
    private final Category category;
    private final int W;
    private final int H;
    private final ArrayList<ModuleButton> buttonsBeforeClosing;
    private int X;
    private int Y;
    private int dragX;
    private int dragY;
    private boolean open;
    private boolean dragging;
    private int showingButtonCount;
    private boolean opening;
    private boolean closing;
    
    public Window(final Category category, final int x, final int y, final int w, final int h) {
        this.buttons = new ArrayList<ModuleButton>();
        this.buttonsBeforeClosing = new ArrayList<ModuleButton>();
        this.open = true;
        this.category = category;
        this.X = x;
        this.Y = y;
        this.W = w;
        this.H = h;
        int yOffset = this.Y + this.H;
        for (final Module module : money.moduleManager.getModules(category)) {
            final ModuleButton button = new ModuleButton(module, this.X, yOffset, this.W, this.H);
            this.buttons.add(button);
            yOffset += this.H;
        }
        this.showingButtonCount = this.buttons.size();
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], 1.0f, 1.0f);
        final SettingManager settingManager = money.settingManager;
        SettingManager.getSetting("ClickGUI", "Red").setIntegerValue(color_rgb_o >> 16 & 0xFF);
        final SettingManager settingManager2 = money.settingManager;
        SettingManager.getSetting("ClickGUI", "Green").setIntegerValue(color_rgb_o >> 8 & 0xFF);
        final SettingManager settingManager3 = money.settingManager;
        SettingManager.getSetting("ClickGUI", "Blue").setIntegerValue(color_rgb_o & 0xFF);
    }
    
    public void render(final int mX, final int mY) {
        if (this.dragging) {
            this.X = this.dragX + mX;
            this.Y = this.dragY + mY;
        }
        Gui.drawRect(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(0, 0, 0, 200).getRGB());
        final SettingManager settingManager = money.settingManager;
        if (SettingManager.getSetting("ClickGUI", "Rainbow").getBooleanValue()) {
            this.cycle_rainbow();
        }
        final String name = this.category.getName();
        final double x = this.X + 4;
        final double y = this.Y + 4;
        final SettingManager settingManager2 = money.settingManager;
        final int integerValue = SettingManager.getSetting("ClickGUI", "Red").getIntegerValue();
        final SettingManager settingManager3 = money.settingManager;
        final int integerValue2 = SettingManager.getSetting("ClickGUI", "Green").getIntegerValue();
        final SettingManager settingManager4 = money.settingManager;
        FontUtil.drawString(name, x, y, new Color(integerValue, integerValue2, SettingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 255).getRGB());
        if (this.open || this.opening || this.closing) {
            int modY = this.Y + this.H;
            int moduleRenderCount = 0;
            for (final ModuleButton moduleButton : this.buttons) {
                if (++moduleRenderCount < this.showingButtonCount + 1) {
                    moduleButton.setX(this.X);
                    moduleButton.setY(modY);
                    moduleButton.render(mX, mY);
                    if (!moduleButton.isOpen() && this.opening && this.buttonsBeforeClosing.contains(moduleButton)) {
                        moduleButton.processRightClick();
                    }
                    modY += this.H;
                    if (!moduleButton.isOpen() && !moduleButton.isOpening() && !moduleButton.isClosing()) {
                        continue;
                    }
                    int settingRenderCount = 0;
                    for (final SettingButton settingButton : moduleButton.getButtons()) {
                        if (++settingRenderCount < moduleButton.getShowingModuleCount() + 1) {
                            settingButton.setX(this.X);
                            settingButton.setY(modY);
                            settingButton.render(mX, mY);
                            modY += this.H;
                        }
                    }
                }
            }
        }
        if (this.opening) {
            ++this.showingButtonCount;
            if (this.showingButtonCount == this.buttons.size()) {
                this.opening = false;
                this.open = true;
                this.buttonsBeforeClosing.clear();
            }
        }
        if (this.closing) {
            --this.showingButtonCount;
            if (this.showingButtonCount == 0 || this.showingButtonCount == 1) {
                this.closing = false;
                this.open = false;
            }
        }
    }
    
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.X, this.Y, this.W, this.H, mX, mY)) {
            if (mB == 0) {
                this.dragging = true;
                this.dragX = this.X - mX;
                this.dragY = this.Y - mY;
            }
            else if (mB == 1) {
                if (this.open && !this.opening && !this.closing) {
                    this.showingButtonCount = this.buttons.size();
                    this.closing = true;
                    for (final ModuleButton button : this.buttons) {
                        if (button.isOpen()) {
                            button.processRightClick();
                            this.buttonsBeforeClosing.add(button);
                        }
                    }
                }
                else if (!this.open && !this.opening && !this.closing) {
                    this.showingButtonCount = 1;
                    this.opening = true;
                }
            }
        }
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.mouseDown(mX, mY, mB);
            }
        }
    }
    
    public void mouseUp(final int mX, final int mY) {
        this.dragging = false;
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.mouseUp(mX, mY);
            }
        }
    }
    
    public void keyPress(final int key) {
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.keyPress(key);
            }
        }
    }
    
    public void close() {
        for (final ModuleButton button : this.buttons) {
            button.close();
        }
    }
    
    private boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
    
    public int getY() {
        return this.Y;
    }
    
    public void setY(final int y) {
        this.Y = y;
    }
}
