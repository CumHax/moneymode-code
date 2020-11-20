//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class AutoTrap extends Module
{
    private final Setting blocksPerTick;
    private final Setting disable;
    private final List<Vec3d> positions;
    private boolean finished;
    
    public AutoTrap(final String name, final String description, final Category category) {
        super(name, description, category);
        this.blocksPerTick = new Setting.Builder(SettingType.INTEGER).setName("BPT").setModule(this).setIntegerValue(1).setMinIntegerValue(1).setMaxIntegerValue(10).build();
        this.disable = new Setting.Builder(SettingType.BOOLEAN).setName("Disable").setModule(this).setBooleanValue(true).build();
        this.positions = new ArrayList<Vec3d>(Arrays.asList(new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, 0.0)));
        this.addSetting(this.blocksPerTick);
        this.addSetting(this.disable);
    }
    
    public static boolean isIntercepted(final BlockPos pos) {
        for (final Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static int getSlot(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(block)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void placeBlock(final BlockPos pos) {
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            if (!Minecraft.getMinecraft().world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos)) {
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + enumFacing.getXOffset() * 0.5, pos.getY() + 0.5 + enumFacing.getYOffset() * 0.5, pos.getZ() + 0.5 + enumFacing.getZOffset() * 0.5);
                final float[] old = { Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch };
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.z - Minecraft.getMinecraft().player.posZ, vec.x - Minecraft.getMinecraft().player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight()), Math.sqrt((vec.x - Minecraft.getMinecraft().player.posX) * (vec.x - Minecraft.getMinecraft().player.posX) + (vec.z - Minecraft.getMinecraft().player.posZ) * (vec.z - Minecraft.getMinecraft().player.posZ))))), Minecraft.getMinecraft().player.onGround));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
                Minecraft.getMinecraft().playerController.processRightClickBlock(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(old[0], old[1], Minecraft.getMinecraft().player.onGround));
                return;
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.finished = false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }
        if (this.finished && this.disable.getBooleanValue()) {
            this.disable();
        }
        int blocksPlaced = 0;
        for (final Vec3d position : this.positions) {
            final EntityPlayer closestPlayer = this.getClosestPlayer();
            if (closestPlayer != null) {
                final BlockPos pos = new BlockPos(position.add(this.getClosestPlayer().getPositionVector()));
                if (!Minecraft.getMinecraft().world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                    continue;
                }
                final int oldSlot = Minecraft.getMinecraft().player.inventory.currentItem;
                Minecraft.getMinecraft().player.inventory.currentItem = getSlot(Blocks.OBSIDIAN);
                placeBlock(pos);
                Minecraft.getMinecraft().player.inventory.currentItem = oldSlot;
                if (++blocksPlaced == this.blocksPerTick.getIntegerValue()) {
                    return;
                }
                continue;
            }
        }
        if (blocksPlaced == 0) {
            this.finished = true;
        }
    }
    
    private EntityPlayer getClosestPlayer() {
        EntityPlayer closestPlayer = null;
        double range = 1000.0;
        for (final EntityPlayer playerEntity : Minecraft.getMinecraft().world.playerEntities) {
            if (!playerEntity.equals((Object)Minecraft.getMinecraft().player)) {
                final double distance = Minecraft.getMinecraft().player.getDistance((Entity)playerEntity);
                if (distance >= range) {
                    continue;
                }
                closestPlayer = playerEntity;
                range = distance;
            }
        }
        return closestPlayer;
    }
}
