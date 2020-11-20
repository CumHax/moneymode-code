//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import java.util.ArrayList;
import java.util.Iterator;
import cat.yoink.dream.api.util.CommandUtil;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cat.yoink.dream.impl.event.PacketSendEvent;
import cat.yoink.dream.api.module.Category;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import java.util.List;
import cat.yoink.dream.api.module.Module;

public class Blink extends Module
{
    private static List<Packet> packets;
    private double oldX;
    private double oldY;
    private double oldZ;
    private EntityOtherPlayerMP fakePlayer;
    
    public Blink(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void packetrec(final PacketSendEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        Blink.packets.add(event.getPacket());
        event.setCanceled(true);
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player != null) {
            this.oldX = this.mc.player.posX;
            this.oldY = this.mc.player.posY;
            this.oldZ = this.mc.player.posZ;
            (this.fakePlayer = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, new GameProfile(UUID.fromString("403e6cb7-a6ca-440a-8041-7fb1e579b5a5"), this.mc.player.getName() + "_fake"))).copyLocationAndAnglesFrom((Entity)Minecraft.getMinecraft().player);
            this.fakePlayer.rotationYawHead = Minecraft.getMinecraft().player.rotationYawHead;
            Minecraft.getMinecraft().world.addEntityToWorld(-101, (Entity)this.fakePlayer);
        }
    }
    
    @Override
    public void onDisable() {
        if (this.mc.player != null) {
            try {
                for (final Packet packet : Blink.packets) {
                    this.mc.player.connection.sendPacket(packet);
                    Blink.packets.remove(packet);
                }
            }
            catch (Exception e) {
                CommandUtil.sendChatMessage("Exception");
                e.printStackTrace();
            }
            Minecraft.getMinecraft().world.removeEntityFromWorld(-101);
            this.fakePlayer = null;
        }
    }
    
    static {
        Blink.packets = new ArrayList<Packet>();
    }
}
