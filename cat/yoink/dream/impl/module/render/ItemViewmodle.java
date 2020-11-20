//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class ItemViewmodle extends Module
{
    private final Setting FOVSlider;
    private final Setting FOVItems;
    private final Setting ItemsFOVSlider;
    private float fov;
    
    public ItemViewmodle(final String name, final String description, final Category category) {
        super(name, description, category);
        this.FOVSlider = new Setting.Builder(SettingType.INTEGER).setName("FOV").setModule(this).setIntegerValue(130).setMinIntegerValue(110).setMaxIntegerValue(175).build();
        this.FOVItems = new Setting.Builder(SettingType.BOOLEAN).setName("Items").setModule(this).setBooleanValue(true).build();
        this.ItemsFOVSlider = new Setting.Builder(SettingType.INTEGER).setName("ItemsFOV").setModule(this).setIntegerValue(130).setMinIntegerValue(110).setMaxIntegerValue(175).build();
        this.addSetting(this.FOVSlider);
        this.addSetting(this.FOVItems);
        this.addSetting(this.ItemsFOVSlider);
    }
    
    @Override
    public void onEnable() {
        this.fov = this.mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.fovSetting = this.fov;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        this.mc.gameSettings.fovSetting = (float)this.FOVSlider.getIntegerValue();
    }
    
    @SubscribeEvent
    public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
        if (this.FOVItems.getBooleanValue()) {
            m.setFOV((float)this.ItemsFOVSlider.getIntegerValue());
        }
    }
}
