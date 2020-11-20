//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class AutoLog extends Module
{
    private final Setting health;
    private final Setting disableOnLog;
    
    public AutoLog(final String name, final String description, final Category category) {
        super(name, description, category);
        this.health = new Setting.Builder(SettingType.INTEGER).setName("MinHealth").setModule(this).setIntegerValue(8).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.disableOnLog = new Setting.Builder(SettingType.BOOLEAN).setName("Disable On Log").setModule(this).setBooleanValue(true).build();
        this.addSetting(this.health);
        this.addSetting(this.disableOnLog);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.player.getHealth() < this.health.getIntegerValue()) {
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
            if (this.disableOnLog.getBooleanValue()) {
                this.disable();
            }
        }
    }
}
