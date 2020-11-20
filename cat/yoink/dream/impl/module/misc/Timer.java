// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import cat.yoink.dream.mixin.mixins.accessor.ITimer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Timer extends Module
{
    private final Setting speed;
    
    public Timer(final String name, final String description, final Category category) {
        super(name, description, category);
        this.addSetting(this.speed = new Setting.Builder(SettingType.INTEGER).setName("Speed").setModule(this).setIntegerValue(20).setMinIntegerValue(1).setMaxIntegerValue(300).build());
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        ((ITimer)((IMinecraft)this.mc).getTimer()).setTickLength(50.0f / (this.speed.getIntegerValue() / 10.0f));
    }
    
    @Override
    public void onDisable() {
        ((ITimer)((IMinecraft)this.mc).getTimer()).setTickLength(50.0f);
    }
}
