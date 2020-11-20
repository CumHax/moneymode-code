//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.gui.clickgui.button;

import java.awt.Color;
import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.module.Module;
import net.minecraft.client.Minecraft;

public class SettingButton
{
    public final Minecraft mc;
    private final int H;
    private Module module;
    private int X;
    private int Y;
    private int W;
    
    public SettingButton(final Module module, final int x, final int y, final int w, final int h) {
        this.mc = Minecraft.getMinecraft();
        this.module = module;
        this.X = x;
        this.Y = y;
        this.W = w;
        this.H = h;
    }
    
    public void render(final int mX, final int mY) {
    }
    
    public void mouseDown(final int mX, final int mY, final int mB) {
    }
    
    public void mouseUp(final int mX, final int mY) {
    }
    
    public void keyPress(final int key) {
    }
    
    public void close() {
    }
    
    public void drawButton(final int mX, final int mY) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 170).getRGB());
        }
        else {
            money.clickGUI.drawGradient(this.X, this.Y, this.X + this.W, this.Y + this.H, new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 150).getRGB());
        }
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public int getX() {
        return this.X;
    }
    
    public void setX(final int x) {
        this.X = x;
    }
    
    public int getY() {
        return this.Y;
    }
    
    public void setY(final int y) {
        this.Y = y;
    }
    
    public int getW() {
        return this.W;
    }
    
    public int getH() {
        return this.H;
    }
    
    public boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
}
