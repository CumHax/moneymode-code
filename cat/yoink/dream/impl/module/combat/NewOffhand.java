//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import net.minecraft.item.Item;
import java.util.Map;
import cat.yoink.dream.api.module.Module;

public class NewOffhand extends Module
{
    private boolean switching;
    private int last_slot;
    private Map<String, Item> itemMap;
    private final Setting item;
    private final Setting totemHp;
    private final Setting gappleInHole;
    private final Setting gappleInHoleHP;
    private final Setting delay;
    
    public NewOffhand(final String name, final String description, final Category category) {
        super(name, description, category);
        this.switching = false;
        this.item = new Setting.Builder(SettingType.ENUM).setName("Item").setModule(this).setEnumValue("Crystal").setEnumValues(new ArrayList<String>(Arrays.asList("Totem", "Crystal", "Gapple"))).build();
        this.totemHp = new Setting.Builder(SettingType.INTEGER).setName("Totem HP").setModule(this).setIntegerValue(16).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.gappleInHole = new Setting.Builder(SettingType.BOOLEAN).setName("Gap In Hole").setModule(this).setBooleanValue(false).build();
        this.gappleInHoleHP = new Setting.Builder(SettingType.INTEGER).setName("Gap Hole HP").setModule(this).setIntegerValue(16).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.delay = new Setting.Builder(SettingType.BOOLEAN).setName("Delay").setModule(this).setBooleanValue(false).build();
        this.addSetting(this.item);
        this.addSetting(this.totemHp);
        this.addSetting(this.gappleInHole);
        this.addSetting(this.gappleInHoleHP);
        this.addSetting(this.delay);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            final float hp = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
            if (hp <= this.totemHp.getIntegerValue()) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.getBooleanValue() ? 1 : 0);
                return;
            }
            if (this.gappleInHole.getBooleanValue() && hp > this.gappleInHoleHP.getIntegerValue() && this.is_in_hole()) {
                this.swap_items(this.get_item_slot(Items.GOLDEN_APPLE), this.delay.getBooleanValue() ? 1 : 0);
                return;
            }
            final String enumValue = this.item.getEnumValue();
            switch (enumValue) {
                case "Crystal": {
                    this.swap_items(this.get_item_slot(Items.END_CRYSTAL), 0);
                    break;
                }
                case "Gapple": {
                    this.swap_items(this.get_item_slot(Items.GOLDEN_APPLE), this.delay.getBooleanValue() ? 1 : 0);
                    break;
                }
                case "Totem": {
                    this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.getBooleanValue() ? 1 : 0);
                    break;
                }
            }
            if (this.mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.getBooleanValue() ? 1 : 0);
            }
        }
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
        if (step == 1) {
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.switching = false;
        }
        this.mc.playerController.updateController();
    }
    
    private boolean is_in_hole() {
        final BlockPos player_block = this.GetLocalPlayerPosFloored();
        return this.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && this.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && this.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && this.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR;
    }
    
    private int get_item_slot(final Item input) {
        if (input == this.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = this.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.GOLDEN_APPLE) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
    }
}
