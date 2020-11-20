//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui;

import org.lwjgl.input.Mouse;
import cat.yoink.dream.api.module.ModuleManager;
import cat.cattyn.moneymod.money;
import java.util.Iterator;
import cat.yoink.dream.api.module.Category;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    private final ArrayList<Window> windows;
    
    public ClickGUI() {
        this.windows = new ArrayList<Window>();
        int xOffset = 3;
        for (final Category category : Category.values()) {
            final Window window = new Window(category, xOffset, 3, 105, 15);
            this.windows.add(window);
            xOffset += 110;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.doScroll();
        for (final Window window : this.windows) {
            window.render(mouseX, mouseY);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Window window : this.windows) {
            window.mouseDown(mouseX, mouseY, mouseButton);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Window window : this.windows) {
            window.mouseUp(mouseX, mouseY);
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Window window : this.windows) {
            window.keyPress(keyCode);
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawGradient(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        this.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }
    
    public void onGuiClosed() {
        for (final Window window : this.windows) {
            window.close();
        }
        final ModuleManager moduleManager = money.moduleManager;
        ModuleManager.getModule("ClickGUI").disable();
    }
    
    private void doScroll() {
        final int w = Mouse.getDWheel();
        if (w < 0) {
            for (final Window window : this.windows) {
                window.setY(window.getY() - 8);
            }
        }
        else if (w > 0) {
            for (final Window window : this.windows) {
                window.setY(window.getY() + 8);
            }
        }
    }
}
