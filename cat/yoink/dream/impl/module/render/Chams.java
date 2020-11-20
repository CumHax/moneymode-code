//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import cat.yoink.dream.impl.event.EventModelPlayerRender;
import net.minecraft.entity.Entity;
import java.awt.Color;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.impl.event.EventPostRenderLayers;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import cat.yoink.dream.impl.event.EventModelRender;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Chams extends Module
{
    private final Setting mode;
    private final Setting Vr;
    private final Setting Vg;
    private final Setting Vb;
    private final Setting alpha;
    private final Setting lines;
    private final Setting width;
    
    public Chams(final String name, final String description, final Category category) {
        super(name, description, category);
        this.mode = new Setting.Builder(SettingType.ENUM).setName("Mode").setModule(this).setEnumValue("ESP").setEnumValues(new ArrayList<String>(Arrays.asList("ESP", "Walls"))).build();
        this.Vr = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.Vg = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.Vb = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.alpha = new Setting.Builder(SettingType.INTEGER).setName("Alpha").setModule(this).setIntegerValue(130).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.lines = new Setting.Builder(SettingType.BOOLEAN).setName("Lines").setModule(this).setBooleanValue(false).build();
        this.width = new Setting.Builder(SettingType.INTEGER).setName("Width (Lines)").setModule(this).setIntegerValue(10).setMinIntegerValue(0).setMaxIntegerValue(100).build();
        this.addSetting(this.mode);
        this.addSetting(this.Vr);
        this.addSetting(this.Vg);
        this.addSetting(this.Vb);
        this.addSetting(this.alpha);
        this.addSetting(this.lines);
        this.addSetting(this.width);
    }
    
    @SubscribeEvent
    public void renderPre(final EventModelRender event) {
        if (this.mode.getEnumValue().equalsIgnoreCase("Walls")) {
            if (!(event.entity instanceof EntityOtherPlayerMP)) {
                return;
            }
            GlStateManager.pushMatrix();
            GL11.glDisable(2929);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3553);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scaleFactor);
            GL11.glEnable(2929);
            GL11.glColor4f(this.Vr.getIntegerValue() / 255.0f, this.Vg.getIntegerValue() / 255.0f, this.Vb.getIntegerValue() / 255.0f, 1.0f);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scaleFactor);
            GL11.glEnable(3553);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void renderPost(final EventPostRenderLayers event) {
        if (this.mode.getEnumValue().equalsIgnoreCase("Walls")) {
            boolean entitytext = true;
            try {
                entitytext = true;
            }
            catch (Exception e) {
                CommandUtil.sendChatMessage("Chams Exception");
                e.printStackTrace();
                entitytext = false;
            }
            if (!entitytext) {
                return;
            }
            final Color c = new Color(this.Vr.getIntegerValue(), this.Vg.getIntegerValue(), this.Vb.getIntegerValue());
            if (event.entity instanceof EntityOtherPlayerMP) {
                GL11.glPushMatrix();
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -100000.0f);
                GL11.glPushAttrib(1048575);
                if (!this.lines.getBooleanValue()) {
                    GL11.glPolygonMode(1028, 6914);
                }
                else {
                    GL11.glPolygonMode(1028, 6913);
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, this.alpha.getIntegerValue() / 255.0f / 2.0f);
                if (this.lines.getBooleanValue()) {
                    GL11.glLineWidth(this.width.getIntegerValue() / 10.0f);
                }
                event.modelBase.render((Entity)event.entity, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scaleIn);
                GL11.glPopAttrib();
                GL11.glPolygonOffset(1.0f, 100000.0f);
                GL11.glDisable(32823);
                GL11.glPopMatrix();
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerModel(final EventModelPlayerRender event) {
        final Color c = new Color(this.Vr.getIntegerValue(), this.Vg.getIntegerValue(), this.Vb.getIntegerValue());
        if (event.type == 0) {
            GL11.glPushMatrix();
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1.0E7f);
            GL11.glPushAttrib(1048575);
            if (!this.lines.getBooleanValue()) {
                GL11.glPolygonMode(1028, 6914);
            }
            else {
                GL11.glPolygonMode(1028, 6913);
            }
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, this.alpha.getIntegerValue() / 255.0f / 2.0f);
            if (this.lines.getBooleanValue()) {
                GL11.glLineWidth(this.width.getIntegerValue() / 10.0f);
            }
        }
        else if (event.type == 1) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0f, 1.0E7f);
            GL11.glDisable(32823);
            GL11.glPopMatrix();
        }
    }
}
