//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Fov extends Module
{
    private final Setting fovSlider;
    public float defaultFov;
    
    public Fov(final String name, final String description, final Category category) {
        super(name, description, category);
        this.addSetting(this.fovSlider = new Setting.Builder(SettingType.INTEGER).setName("Fov").setModule(this).setIntegerValue(120).setMinIntegerValue(0).setMaxIntegerValue(180).build());
    }
    
    @SubscribeEvent
    public void fovOn(final EntityViewRenderEvent.FOVModifier e) {
        e.setFOV((float)this.fovSlider.getIntegerValue());
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.defaultFov = this.mc.gameSettings.fovSetting;
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.mc.gameSettings.fovSetting = this.defaultFov;
    }
}
