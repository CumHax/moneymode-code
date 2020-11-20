//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import com.google.common.collect.Sets;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketSoundEffect;
import cat.yoink.dream.impl.event.PacketReceiveEvent;
import cat.yoink.dream.api.module.Category;
import net.minecraft.util.SoundEvent;
import java.util.Set;
import cat.yoink.dream.api.module.Module;

public class NoSoundLag extends Module
{
    private static final Set<SoundEvent> BLACKLIST;
    
    public NoSoundLag(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (NoSoundLag.BLACKLIST.contains(packet.getSound())) {
                event.setCanceled(true);
            }
        }
    }
    
    static {
        BLACKLIST = Sets.newHashSet((Object[])new SoundEvent[] { SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER });
    }
}
