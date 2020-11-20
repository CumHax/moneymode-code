//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class HoleFiller extends Module
{
    private final Setting range;
    private final Setting radius;
    
    public HoleFiller(final String name, final String description, final Category category) {
        super(name, description, category);
        this.range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this).setIntegerValue(3).setMinIntegerValue(1).setMaxIntegerValue(5).build();
        this.radius = new Setting.Builder(SettingType.INTEGER).setName("Radius").setModule(this).setIntegerValue(5).setMinIntegerValue(1).setMaxIntegerValue(10).build();
        this.addSetting(this.range);
        this.addSetting(this.radius);
    }
    
    public int a(final Block block) {
        return this.a(new ItemStack(block).getItem());
    }
    
    public int a(final Item item) {
        try {
            for (int i = 0; i < 9; ++i) {
                if (item == this.mc.player.inventory.getStackInSlot(i).getItem()) {
                    return i;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public void a(final BlockPos blockPos) {
        this.a(EnumHand.MAIN_HAND, blockPos);
    }
    
    public void a(final EnumHand enumHand, final BlockPos blockPos) {
        final Vec3d vec3d = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos offset = blockPos.offset(enumFacing);
            final EnumFacing opposite = enumFacing.getOpposite();
            if (this.mc.world.getBlockState(offset).getBlock().canCollideCheck(this.mc.world.getBlockState(offset), false)) {
                final Vec3d add = new Vec3d((Vec3i)offset).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                if (vec3d.squareDistanceTo(add) <= 18.0625) {
                    final double n = add.x - vec3d.x;
                    final double n2 = add.y - vec3d.y;
                    final double n3 = add.z - vec3d.z;
                    final float[] array = { this.mc.player.rotationYaw + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f - this.mc.player.rotationYaw), this.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(n2, Math.sqrt(n * n + n3 * n3)))) - this.mc.player.rotationPitch) };
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(array[0], array[1], this.mc.player.onGround));
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, offset, opposite, add, enumHand);
                    this.mc.player.swingArm(enumHand);
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    return;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        for (final EntityPlayer entityPlayer : this.mc.world.playerEntities) {
            if (!entityPlayer.getUniqueID().equals(this.mc.player.getUniqueID())) {
                final double doubleValue = this.radius.getIntegerValue();
                final BlockPos position = entityPlayer.getPosition();
                for (final BlockPos blockPos : BlockPos.getAllInBox(position.add(-doubleValue, -doubleValue, -doubleValue), position.add(doubleValue, doubleValue, doubleValue))) {
                    if (this.mc.player.getDistanceSqToCenter(blockPos) > this.range.getIntegerValue()) {
                        continue;
                    }
                    if (!this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() || !this.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable() || !this.mc.world.getBlockState(blockPos.add(0, -1, 0)).getMaterial().isSolid() || !this.mc.world.getBlockState(blockPos.add(1, 0, 0)).getMaterial().isSolid() || !this.mc.world.getBlockState(blockPos.add(0, 0, 1)).getMaterial().isSolid() || !this.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getMaterial().isSolid() || !this.mc.world.getBlockState(blockPos.add(0, 0, -1)).getMaterial().isSolid() || this.mc.world.getBlockState(blockPos.add(0, 0, 0)).getMaterial() != Material.AIR || this.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() != Material.AIR || this.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() != Material.AIR) {
                        continue;
                    }
                    if (!this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                        continue;
                    }
                    final int a = this.a(Blocks.OBSIDIAN);
                    if (a == -1) {
                        continue;
                    }
                    final int currentItem = this.mc.player.inventory.currentItem;
                    this.mc.player.inventory.currentItem = a;
                    this.a(blockPos);
                    this.mc.player.inventory.currentItem = currentItem;
                }
            }
        }
    }
}
