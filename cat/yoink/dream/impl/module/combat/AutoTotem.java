//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class AutoTotem extends Module
{
    public AutoTotem(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player != null && this.mc.world != null && !(this.mc.currentScreen instanceof GuiContainer)) {
            if (this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == Items.TOTEM_OF_UNDYING) {
                return;
            }
            final int slot = this.getItemSlot();
            if (slot != -1) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
            }
        }
    }
    
    private int getItemSlot() {
        for (int i = 0; i < 36; ++i) {
            final Item item = this.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
