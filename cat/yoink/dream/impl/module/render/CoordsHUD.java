//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.Minecraft;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.util.Colors;
import java.awt.Color;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class CoordsHUD extends Module
{
    private final Setting sat;
    private final Setting x;
    private final Setting y;
    Color c;
    private Colors colors;
    
    public CoordsHUD(final String name, final String description, final Category category) {
        super(name, description, category);
        this.sat = new Setting.Builder(SettingType.INTEGER).setName("RainbowSat").setModule(this).setIntegerValue(6).setMinIntegerValue(1).setMaxIntegerValue(9).build();
        this.x = new Setting.Builder(SettingType.INTEGER).setName("X").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(900).build();
        this.y = new Setting.Builder(SettingType.INTEGER).setName("Y").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(540).build();
        this.colors = new Colors();
        this.addSetting(this.sat);
        this.addSetting(this.x);
        this.addSetting(this.y);
    }
    
    public void renderStringWave(final String s, final int x, final int y, final float bright) {
        int updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            final String str = s.charAt(i) + "";
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, (float)updateX, (float)y, this.colors.effect(i * 3500000L, bright, 100).getRGB());
            updateX += Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i));
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent egoe) {
        if (egoe.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (this.mc.player.dimension == -1) {
                this.renderStringWave("XYZ " + this.mc.player.getPosition().getX() + ", " + this.mc.player.getPosition().getY() + ", " + this.mc.player.getPosition().getZ() + " [" + this.mc.player.getPosition().getX() * 8 + ", " + this.mc.player.getPosition().getZ() * 8 + "]", this.x.getIntegerValue(), this.y.getIntegerValue(), this.sat.getIntegerValue() / 10.0f);
            }
            else {
                this.renderStringWave("XYZ " + this.mc.player.getPosition().getX() + ", " + this.mc.player.getPosition().getY() + ", " + this.mc.player.getPosition().getZ() + " [" + this.mc.player.getPosition().getX() / 8 + ", " + this.mc.player.getPosition().getZ() / 8 + "]", this.x.getIntegerValue(), this.y.getIntegerValue(), this.sat.getIntegerValue() / 10.0f);
            }
        }
    }
}
