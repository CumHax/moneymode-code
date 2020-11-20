//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class FastBow extends Module
{
    public FastBow(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.mc.player.isHandActive() && this.mc.player.getItemInUseMaxCount() >= 3) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(this.mc.player.getActiveHand()));
            this.mc.player.stopActiveHand();
        }
    }
}
