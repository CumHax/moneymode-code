//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Surround extends Module
{
    private final Setting jumpDisable;
    private final Setting autoToggle;
    private final Setting chainPopToggle;
    
    public Surround(final String name, final String description, final Category category) {
        super(name, description, category);
        this.jumpDisable = new Setting.Builder(SettingType.BOOLEAN).setName("Toggle on Jump").setModule(this).setBooleanValue(true).build();
        this.autoToggle = new Setting.Builder(SettingType.BOOLEAN).setName("Toggles").setModule(this).setBooleanValue(true).build();
        this.chainPopToggle = new Setting.Builder(SettingType.BOOLEAN).setName("TotemPopToggle").setModule(this).setBooleanValue(true).build();
        this.addSetting(this.jumpDisable);
        this.addSetting(this.autoToggle);
        this.addSetting(this.chainPopToggle);
    }
    
    public static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!Minecraft.getMinecraft().world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (!this.mc.player.onGround && this.jumpDisable.getBooleanValue()) {
            this.toggle();
            return;
        }
        final Vec3d vec3d = getInterpolatedPos((Entity)this.mc.player, 0.0f);
        BlockPos northBlockPos = new BlockPos(vec3d).north();
        BlockPos southBlockPos = new BlockPos(vec3d).south();
        BlockPos eastBlockPos = new BlockPos(vec3d).east();
        BlockPos westBlockPos = new BlockPos(vec3d).west();
        final int newSlot = this.findBlockInHotbar();
        if (newSlot == -1) {
            return;
        }
        final BlockPos centerPos = this.mc.player.getPosition();
        final double y = centerPos.getY();
        double x = centerPos.getX();
        double z = centerPos.getZ();
        final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);
        final int oldSlot = this.mc.player.inventory.currentItem;
        this.mc.player.inventory.currentItem = newSlot;
        if (!hasNeighbour(northBlockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = northBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    northBlockPos = neighbour;
                    break;
                }
            }
        }
        if (!hasNeighbour(southBlockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = southBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    southBlockPos = neighbour;
                    break;
                }
            }
        }
        if (!hasNeighbour(eastBlockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = eastBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    eastBlockPos = neighbour;
                    break;
                }
            }
        }
        if (!hasNeighbour(westBlockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = westBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    westBlockPos = neighbour;
                    break;
                }
            }
        }
        if (this.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable() && this.isEntitiesEmpty(northBlockPos)) {
            if (this.mc.player.onGround) {
                if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
            }
            placeBlockScaffold(northBlockPos, true);
        }
        if (this.mc.world.getBlockState(southBlockPos).getMaterial().isReplaceable() && this.isEntitiesEmpty(southBlockPos)) {
            if (this.mc.player.onGround) {
                if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
            }
            placeBlockScaffold(southBlockPos, true);
        }
        if (this.mc.world.getBlockState(eastBlockPos).getMaterial().isReplaceable() && this.isEntitiesEmpty(eastBlockPos)) {
            if (this.mc.player.onGround) {
                if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
            }
            placeBlockScaffold(eastBlockPos, true);
        }
        if (this.mc.world.getBlockState(westBlockPos).getMaterial().isReplaceable() && this.isEntitiesEmpty(westBlockPos)) {
            if (this.mc.player.onGround) {
                if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() + 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() - 0.5;
                    this.centerPlayer(x, y, z);
                }
                if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                    x = centerPos.getX() - 0.5;
                    z = centerPos.getZ() + 0.5;
                    this.centerPlayer(x, y, z);
                }
            }
            placeBlockScaffold(westBlockPos, true);
        }
        this.mc.player.inventory.currentItem = oldSlot;
        if ((this.autoToggle.getBooleanValue() || this.chainPopToggle.getBooleanValue()) && (this.mc.world.getBlockState(new BlockPos(vec3d).north()).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(new BlockPos(vec3d).north()).getBlock() == Blocks.BEDROCK) && (this.mc.world.getBlockState(new BlockPos(vec3d).south()).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(new BlockPos(vec3d).south()).getBlock() == Blocks.BEDROCK) && (this.mc.world.getBlockState(new BlockPos(vec3d).west()).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(new BlockPos(vec3d).west()).getBlock() == Blocks.BEDROCK) && (this.mc.world.getBlockState(new BlockPos(vec3d).east()).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(new BlockPos(vec3d).east()).getBlock() == Blocks.BEDROCK)) {
            this.chainPopToggle.setBooleanValue(false);
            this.toggle();
        }
    }
    
    private int findBlockInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private void centerPlayer(final double x, final double y, final double z) {
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, true));
        this.mc.player.setPosition(x, y, z);
    }
    
    private double getDst(final Vec3d vec) {
        return this.mc.player.getDistance(vec.x, vec.y, vec.z);
    }
    
    private boolean isEntitiesEmpty(final BlockPos pos) {
        return this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem) && !(e instanceof EntityXPOrb)).count() == 0L;
    }
    
    public static void placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                ((IMinecraft)Minecraft.getMinecraft()).setRightClickDelayTimer(0);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
                return;
            }
        }
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Minecraft.getMinecraft().player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
}
