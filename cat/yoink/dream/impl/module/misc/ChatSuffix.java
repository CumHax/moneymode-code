//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import cat.yoink.dream.impl.event.PacketSendEvent;
import cat.yoink.dream.api.module.Category;
import java.lang.reflect.Field;
import cat.yoink.dream.api.module.Module;

public class ChatSuffix extends Module
{
    private final String suffix = "| moneymod+2";
    protected static final Field msgfield;
    
    public ChatSuffix(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage)event.getPacket()).getMessage();
            if (s.startsWith("/") || s.contains("| moneymod+2")) {
                return;
            }
            s += "| moneymod+2";
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            event.setCanceled(true);
            final CPacketChatMessage newpacket = new CPacketChatMessage(s);
            this.mc.player.connection.sendPacket((Packet)newpacket);
        }
    }
    
    static {
        msgfield = ReflectionHelper.findField((Class)CPacketChatMessage.class, new String[] { "message", "message", "a" });
    }
}
