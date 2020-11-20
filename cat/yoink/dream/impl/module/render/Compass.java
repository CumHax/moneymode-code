//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.math.MathHelper;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.client.gui.ScaledResolution;
import cat.yoink.dream.api.util.Colors;
import java.awt.Color;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Compass extends Module
{
    private final Setting Rainbow;
    Color c;
    private Colors colors;
    ScaledResolution resolution;
    
    public Compass(final String name, final String description, final Category category) {
        super(name, description, category);
        this.Rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.colors = new Colors();
        this.addSetting(this.Rainbow);
        this.resolution = new ScaledResolution(this.mc);
    }
    
    private double getX(final double rad) {
        return Math.sin(rad) * 30.0;
    }
    
    private double getY(final double rad) {
        final double epicPitch = MathHelper.clamp(this.mc.getRenderViewEntity().rotationPitch + 30.0f, -90.0f, 90.0f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * 30.0;
    }
    
    private double getPosOnCompass(final Direction dir) {
        final double yaw = Math.toRadians(MathHelper.wrapDegrees(this.mc.getRenderViewEntity().rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent egoe) {
        if (egoe.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            final double centerX = this.resolution.getScaledWidth() * 1.11;
            final double centerY = this.resolution.getScaledHeight_double() * 1.8;
            for (final Direction dir : Direction.values()) {
                final double rad = this.getPosOnCompass(dir);
                if (this.Rainbow.getBooleanValue()) {
                    this.renderStringWave(dir.name(), (int)(centerX + this.getX(rad)), (int)(centerY + this.getY(rad)), 0.5f);
                }
                else {
                    final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                    fr.drawStringWithShadow(dir.name(), (float)(int)(centerX + this.getX(rad)), (float)(int)(centerY + this.getY(rad)), (dir == Direction.N) ? new Color(255, 0, 0, 255).getRGB() : new Color(255, 255, 255, 255).getRGB());
                }
            }
        }
    }
    
    public void renderStringWave(final String s, final int x, final int y, final float bright) {
        int updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            final String str = s.charAt(i) + "";
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, (float)updateX, (float)y, this.colors.effect(i * 3500000L, bright, 100).getRGB());
            updateX += Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i));
        }
    }
    
    private enum Direction
    {
        N, 
        W, 
        S, 
        E;
    }
}
