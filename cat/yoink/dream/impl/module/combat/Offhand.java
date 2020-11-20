//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Offhand extends Module
{
    private final Setting stopInGUI;
    private final Setting swordGap;
    private final Setting soft;
    private final Setting minHealth;
    private final Setting delay;
    private final Setting mode;
    private final Setting packetFix;
    private int previtemSlot;
    
    public Offhand(final String name, final String description, final Category category) {
        super(name, description, category);
        this.stopInGUI = new Setting.Builder(SettingType.BOOLEAN).setName("Stop In Gui").setModule(this).setBooleanValue(true).build();
        this.swordGap = new Setting.Builder(SettingType.BOOLEAN).setName("Sword Gap").setModule(this).setBooleanValue(true).build();
        this.soft = new Setting.Builder(SettingType.BOOLEAN).setName("Soft").setModule(this).setBooleanValue(true).build();
        this.minHealth = new Setting.Builder(SettingType.INTEGER).setName("Min Health").setModule(this).setIntegerValue(16).setMinIntegerValue(1).setMaxIntegerValue(36).build();
        this.delay = new Setting.Builder(SettingType.INTEGER).setName("Delay").setModule(this).setIntegerValue(1).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.mode = new Setting.Builder(SettingType.ENUM).setName("Mode").setModule(this).setEnumValue("Crystal").setEnumValues(new ArrayList<String>(Arrays.asList("Crystal", "Gapple", "Bed", "Totem"))).build();
        this.packetFix = new Setting.Builder(SettingType.BOOLEAN).setName("Packet Fix").setModule(this).setBooleanValue(true).build();
        this.previtemSlot = -6;
        this.addSetting(this.stopInGUI);
        this.addSetting(this.swordGap);
        this.addSetting(this.soft);
        this.addSetting(this.minHealth);
        this.addSetting(this.mode);
        this.addSetting(this.packetFix);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.stopInGUI.getBooleanValue() && this.mc.currentScreen != null) {
            return;
        }
        final int itemSlot = this.getItemSlot();
        if (itemSlot == -1) {
            return;
        }
        if (itemSlot == this.previtemSlot) {
            return;
        }
        this.previtemSlot = itemSlot;
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
    }
    
    private int getItemSlot() {
        Item itemToSearch = Items.TOTEM_OF_UNDYING;
        if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() > this.minHealth.getIntegerValue()) {
            if (this.swordGap.getBooleanValue() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                itemToSearch = Items.GOLDEN_APPLE;
            }
            else {
                final String enumValue = this.mode.getEnumValue();
                switch (enumValue) {
                    case "Crystal": {
                        itemToSearch = Items.END_CRYSTAL;
                        break;
                    }
                    case "Gapple": {
                        itemToSearch = Items.GOLDEN_APPLE;
                        break;
                    }
                    case "Bed": {
                        itemToSearch = Items.BED;
                        break;
                    }
                }
            }
        }
        if (this.mc.player.inventory.getStackInSlot(45).getItem() == itemToSearch) {
            return -1;
        }
        for (int i = 9; i < 36; ++i) {
            if (this.mc.player.inventory.getStackInSlot(i).getItem() == itemToSearch) {
                return (i < 9) ? (i + 36) : i;
            }
        }
        return -1;
    }
}
