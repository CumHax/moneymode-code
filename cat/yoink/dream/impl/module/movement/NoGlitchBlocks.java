//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBlock;
import cat.yoink.dream.impl.event.EventDamageBlock;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class NoGlitchBlocks extends Module
{
    public NoGlitchBlocks(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onDamageBlock(final EventDamageBlock e) {
        if (this.mc.player != null && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging());
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.fromAngle(-1.0)));
        }
    }
}
