// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketCloseWindow;
import cat.yoink.dream.impl.event.PacketSendEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class XCarry extends Module
{
    public XCarry(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCanceled(true);
        }
    }
}
