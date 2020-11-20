//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cat.yoink.dream.mixin.mixins.accessor.IItemRenderer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import net.minecraft.client.renderer.ItemRenderer;
import cat.yoink.dream.api.module.Module;

public class LowHands extends Module
{
    ItemRenderer itemRenderer;
    private final Setting main;
    private final Setting off;
    
    public LowHands(final String name, final String description, final Category category) {
        super(name, description, category);
        this.itemRenderer = this.mc.entityRenderer.itemRenderer;
        this.main = new Setting.Builder(SettingType.INTEGER).setName("MainHand").setModule(this).setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.off = new Setting.Builder(SettingType.INTEGER).setName("OffHand").setModule(this).setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.addSetting(this.main);
        this.addSetting(this.off);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        ((IItemRenderer)this.itemRenderer).setOffHand(this.off.getIntegerValue() / 10.0f);
        ((IItemRenderer)this.itemRenderer).setMainHand(this.main.getIntegerValue() / 10.0f);
    }
}
