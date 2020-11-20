//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Step extends Module
{
    private final Setting height;
    
    public Step(final String name, final String description, final Category category) {
        super(name, description, category);
        this.addSetting(this.height = new Setting.Builder(SettingType.INTEGER).setName("Height").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(10).build());
    }
    
    @Override
    public void onDisable() {
        this.mc.player.stepHeight = 0.5f;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player != null || this.mc.world != null) {
            this.mc.player.stepHeight = (float)this.height.getIntegerValue();
        }
    }
}
