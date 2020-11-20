//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import java.util.Arrays;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.HashMap;
import cat.yoink.dream.api.util.CommandUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.math.RoundingMode;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.block.Block;
import java.util.List;
import cat.yoink.dream.api.setting.Setting;
import java.text.DecimalFormat;
import cat.yoink.dream.api.module.Module;

public class Auto32k extends Module
{
    private static final DecimalFormat df;
    private final Setting placeRange;
    private final Setting yOffset;
    private final Setting moveToHotbar;
    private final Setting placeCloseToEnemy;
    private final Setting placeObiOnTop;
    private final Setting debug;
    private int swordSlot;
    private static boolean isSneaking;
    private static final List<Block> shulkerList;
    private static final List<Block> blacklist;
    
    public Auto32k(final String name, final String description, final Category category) {
        super(name, description, category);
        this.placeRange = new Setting.Builder(SettingType.INTEGER).setName("Place Range").setModule(this).setIntegerValue(4).setMinIntegerValue(1).setMaxIntegerValue(6).build();
        this.yOffset = new Setting.Builder(SettingType.INTEGER).setName("Y Hopper Offset").setModule(this).setIntegerValue(1).setMinIntegerValue(1).setMaxIntegerValue(4).build();
        this.moveToHotbar = new Setting.Builder(SettingType.BOOLEAN).setName("Move To Hotbar").setModule(this).setBooleanValue(true).build();
        this.placeCloseToEnemy = new Setting.Builder(SettingType.BOOLEAN).setName("Place close to enemy").setModule(this).setBooleanValue(false).build();
        this.placeObiOnTop = new Setting.Builder(SettingType.BOOLEAN).setName("Place Obi on Top").setModule(this).setBooleanValue(true).build();
        this.debug = new Setting.Builder(SettingType.BOOLEAN).setName("Debug Messages").setModule(this).setBooleanValue(true).build();
        this.addSetting(this.placeRange);
        this.addSetting(this.yOffset);
        this.addSetting(this.moveToHotbar);
        this.addSetting(this.placeCloseToEnemy);
        this.addSetting(this.placeObiOnTop);
        this.addSetting(this.debug);
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    @Override
    public void onEnable() {
        Auto32k.df.setRoundingMode(RoundingMode.CEILING);
        int hopperSlot = -1;
        int shulkerSlot = -1;
        int obiSlot = -1;
        this.swordSlot = -1;
        for (int i = 0; i < 9 && (hopperSlot == -1 || shulkerSlot == -1 || obiSlot == -1); ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.HOPPER) {
                        hopperSlot = i;
                    }
                    else if (Auto32k.shulkerList.contains(block)) {
                        shulkerSlot = i;
                    }
                    else if (block == Blocks.OBSIDIAN) {
                        obiSlot = i;
                    }
                }
            }
        }
        if (hopperSlot == -1) {
            if (this.debug.getBooleanValue()) {
                CommandUtil.sendChatMessage("[Auto32k] Hopper missing, disabling.");
            }
            this.disable();
            return;
        }
        if (shulkerSlot == -1) {
            if (this.debug.getBooleanValue()) {
                CommandUtil.sendChatMessage("[Auto32k] Shulker missing, disabling.");
            }
            this.disable();
            return;
        }
        final int range = (int)Math.ceil(this.placeRange.getIntegerValue());
        final List<BlockPos> placeTargetList = this.getSphere(this.getPlayerPos(), (float)range, range, false, true, 0);
        final Map<BlockPos, Double> placeTargetMap = new HashMap<BlockPos, Double>();
        BlockPos placeTarget = null;
        boolean useRangeSorting = false;
        for (final BlockPos placeTargetTest : placeTargetList) {
            for (final Entity entity : this.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (entity == this.mc.player) {
                    continue;
                }
                if (this.yOffset.getIntegerValue() != 0 && Math.abs(this.mc.player.getPosition().getY() - placeTargetTest.getY()) > Math.abs(this.yOffset.getIntegerValue())) {
                    continue;
                }
                if (!this.isAreaPlaceable(placeTargetTest)) {
                    continue;
                }
                final double distanceToEntity = entity.getDistance((double)placeTargetTest.getX(), (double)placeTargetTest.getY(), (double)placeTargetTest.getZ());
                placeTargetMap.put(placeTargetTest, placeTargetMap.containsKey(placeTargetTest) ? (placeTargetMap.get(placeTargetTest) + distanceToEntity) : distanceToEntity);
                useRangeSorting = true;
            }
        }
        if (placeTargetMap.size() > 0) {
            final Map map;
            placeTargetMap.forEach((k, v) -> {
                if (!this.isAreaPlaceable(k)) {
                    map.remove(k);
                }
                return;
            });
            if (placeTargetMap.size() == 0) {
                useRangeSorting = false;
            }
        }
        if (useRangeSorting) {
            if (this.placeCloseToEnemy.getBooleanValue()) {
                placeTarget = Collections.min((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
            else {
                placeTarget = Collections.max((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
        }
        else {
            for (final BlockPos pos : placeTargetList) {
                if (this.isAreaPlaceable(pos)) {
                    placeTarget = pos;
                    break;
                }
            }
        }
        if (placeTarget == null) {
            if (this.debug.getBooleanValue()) {
                CommandUtil.sendChatMessage("[Auto32k] No valid position in range to place!");
            }
            this.disable();
            return;
        }
        if (this.debug.getBooleanValue()) {
            CommandUtil.sendChatMessage("[Auto32k] Place Target: " + placeTarget.getX() + " " + placeTarget.getY() + " " + placeTarget.getZ() + " Distance: " + Auto32k.df.format(this.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)placeTarget))));
        }
        this.mc.player.inventory.currentItem = hopperSlot;
        this.placeBlock(new BlockPos((Vec3i)placeTarget));
        this.mc.player.inventory.currentItem = shulkerSlot;
        this.placeBlock(new BlockPos((Vec3i)placeTarget.add(0, 1, 0)));
        if (this.placeObiOnTop.getBooleanValue() && obiSlot != -1) {
            this.mc.player.inventory.currentItem = obiSlot;
            this.placeBlock(new BlockPos((Vec3i)placeTarget.add(0, 2, 0)));
        }
        if (Auto32k.isSneaking) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            Auto32k.isSneaking = false;
        }
        this.mc.player.inventory.currentItem = shulkerSlot;
        final BlockPos hopperPos = new BlockPos((Vec3i)placeTarget);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(hopperPos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        this.swordSlot = shulkerSlot + 32;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (!(this.mc.currentScreen instanceof GuiContainer)) {
            return;
        }
        if (!this.moveToHotbar.getBooleanValue()) {
            this.disable();
            return;
        }
        if (this.swordSlot == -1) {
            return;
        }
        boolean swapReady = true;
        if (((GuiContainer)this.mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty()) {
            swapReady = false;
        }
        if (!((GuiContainer)this.mc.currentScreen).inventorySlots.getSlot(this.swordSlot).getStack().isEmpty()) {
            swapReady = false;
        }
        if (swapReady) {
            this.mc.playerController.windowClick(((GuiContainer)this.mc.currentScreen).inventorySlots.windowId, 0, this.swordSlot - 32, ClickType.SWAP, (EntityPlayer)this.mc.player);
            this.disable();
        }
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos) {
        for (final Entity entity : this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        if (!this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!this.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable()) {
            return false;
        }
        if (this.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockAir) {
            return false;
        }
        if (this.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockLiquid) {
            return false;
        }
        if (this.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)blockPos)) > this.placeRange.getIntegerValue()) {
            return false;
        }
        final Block block = this.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock();
        return !Auto32k.blacklist.contains(block) && !Auto32k.shulkerList.contains(block) && this.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)blockPos).add(0.0, 1.0, 0.0)) <= this.placeRange.getIntegerValue();
    }
    
    private void placeBlock(final BlockPos pos) {
        if (!this.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!this.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                final Block neighborPos = this.mc.world.getBlockState(neighbor).getBlock();
                if (Auto32k.blacklist.contains(neighborPos) || Auto32k.shulkerList.contains(neighborPos)) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    Auto32k.isSneaking = true;
                }
                this.faceVectorPacketInstant(hitVec);
                this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                ((IMinecraft)this.mc).setRightClickDelayTimer(4);
                return;
            }
        }
    }
    
    private float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = this.getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { this.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - this.mc.player.rotationYaw), this.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - this.mc.player.rotationPitch) };
    }
    
    private Vec3d getEyesPos() {
        return new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
    }
    
    public void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = this.getLegitRotations(vec);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], this.mc.player.onGround));
    }
    
    public boolean checkForNeighbours(final BlockPos blockPos) {
        if (!this.hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.offset(side);
                if (this.hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!this.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        df = new DecimalFormat("#.#");
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        blacklist = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST);
    }
}
