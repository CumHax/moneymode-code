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

public class FastSwim extends Module
{
    private final Setting up;
    private final Setting down;
    private final Setting forward;
    private final Setting sprint;
    private final Setting only2b;
    int divider;
    
    public FastSwim(final String name, final String description, final Category category) {
        super(name, description, category);
        this.up = new Setting.Builder(SettingType.BOOLEAN).setName("Speed Up").setModule(this).setBooleanValue(true).build();
        this.down = new Setting.Builder(SettingType.BOOLEAN).setName("Speed Down").setModule(this).setBooleanValue(true).build();
        this.forward = new Setting.Builder(SettingType.BOOLEAN).setName("Speed Forward").setModule(this).setBooleanValue(true).build();
        this.sprint = new Setting.Builder(SettingType.BOOLEAN).setName("Sprint In Water").setModule(this).setBooleanValue(true).build();
        this.only2b = new Setting.Builder(SettingType.BOOLEAN).setName("Only2b").setModule(this).setBooleanValue(true).build();
        this.divider = 5;
        this.addSetting(this.up);
        this.addSetting(this.down);
        this.addSetting(this.forward);
        this.addSetting(this.sprint);
        this.addSetting(this.only2b);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.only2b.getBooleanValue() && !this.mc.isSingleplayer() && this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2t.org")) {
            if (this.sprint.getBooleanValue() && (this.mc.player.isInLava() || this.mc.player.isInWater())) {
                this.mc.player.setSprinting(true);
            }
            if ((this.mc.player.isInWater() || this.mc.player.isInLava()) && this.mc.gameSettings.keyBindJump.isKeyDown() && this.up.getBooleanValue()) {
                this.mc.player.motionY = 0.725 / this.divider;
            }
            if (this.mc.player.isInWater() || this.mc.player.isInLava()) {
                if ((this.forward.getBooleanValue() && this.mc.gameSettings.keyBindForward.isKeyDown()) || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) {
                    this.mc.player.jumpMovementFactor = 0.34f / this.divider;
                }
                else {
                    this.mc.player.jumpMovementFactor = 0.0f;
                }
            }
            if (this.mc.player.isInWater() && this.down.getBooleanValue() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final int divider2 = this.divider * -1;
                this.mc.player.motionY = 2.2 / divider2;
            }
            if (this.mc.player.isInLava() && this.down.getBooleanValue() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final int divider2 = this.divider * -1;
                this.mc.player.motionY = 0.91 / divider2;
            }
        }
        if (!this.only2b.getBooleanValue()) {
            if (this.sprint.getBooleanValue() && (this.mc.player.isInLava() || this.mc.player.isInWater())) {
                this.mc.player.setSprinting(true);
            }
            if ((this.mc.player.isInWater() || this.mc.player.isInLava()) && this.mc.gameSettings.keyBindJump.isKeyDown() && this.up.getBooleanValue()) {
                this.mc.player.motionY = 0.725 / this.divider;
            }
            if (this.mc.player.isInWater() || this.mc.player.isInLava()) {
                if ((this.forward.getBooleanValue() && this.mc.gameSettings.keyBindForward.isKeyDown()) || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) {
                    this.mc.player.jumpMovementFactor = 0.34f / this.divider;
                }
                else {
                    this.mc.player.jumpMovementFactor = 0.0f;
                }
            }
            if (this.mc.player.isInWater() && this.down.getBooleanValue() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final int divider2 = this.divider * -1;
                this.mc.player.motionY = 2.2 / divider2;
            }
            if (this.mc.player.isInLava() && this.down.getBooleanValue() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final int divider2 = this.divider * -1;
                this.mc.player.motionY = 0.91 / divider2;
            }
        }
    }
}
