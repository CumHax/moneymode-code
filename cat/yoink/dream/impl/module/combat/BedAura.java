//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import java.util.Comparator;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class BedAura extends Module
{
    private final Setting range;
    private final Setting rotate;
    private final Setting dimensionCheck;
    private final Setting refill;
    boolean moving;
    
    public BedAura(final String name, final String description, final Category category) {
        super(name, description, category);
        this.range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this).setIntegerValue(4).setMinIntegerValue(0).setMaxIntegerValue(6).build();
        this.rotate = new Setting.Builder(SettingType.BOOLEAN).setName("Rotate").setModule(this).setBooleanValue(true).build();
        this.dimensionCheck = new Setting.Builder(SettingType.BOOLEAN).setName("DimensionCheck").setModule(this).setBooleanValue(true).build();
        this.refill = new Setting.Builder(SettingType.BOOLEAN).setName("Hotbar Refill").setModule(this).setBooleanValue(false).build();
        this.moving = false;
        this.addSetting(this.range);
        this.addSetting(this.rotate);
        this.addSetting(this.dimensionCheck);
        this.addSetting(this.refill);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.refill.getBooleanValue()) {
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                if (this.mc.player.inventory.getStackInSlot(i) == ItemStack.EMPTY) {
                    slot = i;
                    break;
                }
            }
            if (this.moving && slot != -1) {
                this.mc.playerController.windowClick(0, slot + 36, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.moving = false;
                slot = -1;
            }
            if (slot != -1 && !(this.mc.currentScreen instanceof GuiContainer) && this.mc.player.inventory.getItemStack().isEmpty()) {
                int t = -1;
                for (int j = 0; j < 45; ++j) {
                    if (this.mc.player.inventory.getStackInSlot(j).getItem() == Items.BED && j >= 9) {
                        t = j;
                        break;
                    }
                }
                if (t != -1) {
                    this.mc.playerController.windowClick(0, t, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                    this.moving = true;
                }
            }
        }
        this.mc.world.loadedTileEntityList.stream().filter(e -> e instanceof TileEntityBed).filter(e -> this.mc.player.getDistance((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ()) <= this.range.getIntegerValue()).sorted(Comparator.comparing(e -> this.mc.player.getDistance((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ()))).forEach(bed -> {
            if (!this.dimensionCheck.getBooleanValue() || this.mc.player.dimension != 0) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
        });
    }
}
