//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class AutoArmor extends Module
{
    private final Setting Force;
    
    public AutoArmor(final String name, final String description, final Category category) {
        super(name, description, category);
        this.addSetting(this.Force = new Setting.Builder(SettingType.BOOLEAN).setName("Force").setModule(this).setBooleanValue(true).build());
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (this.mc.currentScreen instanceof GuiContainer && !(this.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int armorType = 0; armorType < 4; ++armorType) {
            final ItemStack oldArmor = this.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() <= 1) {
                if (stack != null) {
                    if (stack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)stack.getItem();
                        final int armorType2 = armor.armorType.ordinal() - 2;
                        if (armorType2 != 2 || !this.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) {
                            final int armorValue = armor.damageReduceAmount;
                            if (armorValue > bestArmorValues[armorType2]) {
                                bestArmorSlots[armorType2] = slot;
                                bestArmorValues[armorType2] = armorValue;
                            }
                        }
                    }
                }
            }
        }
        for (int armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 != -1) {
                final ItemStack oldArmor2 = this.mc.player.inventory.armorItemInSlot(armorType);
                if (oldArmor2 == null || oldArmor2 != ItemStack.EMPTY || this.mc.player.inventory.getFirstEmptyStack() != -1) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    this.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                    this.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                    break;
                }
            }
        }
    }
}
