//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command.commands;

import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.CommandManager;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import cat.yoink.dream.api.command.Command;

public class Clip extends Command
{
    public Clip() {
        super("vclip", "vclip", new String[] { "vclip" });
    }
    
    public Entity getRidingOrPlayer() {
        if (Minecraft.getMinecraft().player == null) {
            return null;
        }
        if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
            return Minecraft.getMinecraft().player.getRidingEntity();
        }
        return (Entity)Minecraft.getMinecraft().player;
    }
    
    private void setPosition(final double x, final double y, final double z) {
        final Entity local = this.getRidingOrPlayer();
        local.setPositionAndUpdate(x, y, z);
        final Minecraft MC = Minecraft.getMinecraft();
        if (local instanceof EntityPlayerSP) {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Position(local.posX, local.posY, local.posZ, MC.player.onGround));
        }
        else {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketVehicleMove(local));
        }
    }
    
    private void offsetY(final double yOffset) {
        final Entity local = this.getRidingOrPlayer();
        this.setPosition(local.posX, local.posY + yOffset, local.posZ);
    }
    
    @Override
    public void onCommand(final String arguments) {
        if (arguments.equals("")) {
            CommandUtil.sendChatMessage(String.format("&7Usage: %svclip [blocks]", CommandManager.getPrefix()));
            return;
        }
        try {
            final Double d = Double.parseDouble(arguments.split(" ")[0]);
            Minecraft.getMinecraft().addScheduledTask(() -> this.offsetY(d));
            CommandUtil.sendChatMessage(String.format("clipped %f blocks", d));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
