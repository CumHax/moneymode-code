//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.util.CommandUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class AutoTNTCart extends Module
{
    private final Setting announceUsage;
    private final Setting debug;
    private final Setting carts;
    private boolean firstSwap;
    private boolean secondSwap;
    private boolean beginPlacing;
    int tickDelay;
    private int lighterSlot;
    private EntityPlayer closestTarget;
    private BlockPos targetPos;
    
    public AutoTNTCart(final String name, final String description, final Category category) {
        super(name, description, category);
        this.announceUsage = new Setting.Builder(SettingType.BOOLEAN).setName("Announce Usage").setModule(this).setBooleanValue(false).build();
        this.debug = new Setting.Builder(SettingType.BOOLEAN).setName("Debug Mode").setModule(this).setBooleanValue(false).build();
        this.carts = new Setting.Builder(SettingType.INTEGER).setName("Place Delay").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(20).build();
        this.firstSwap = true;
        this.secondSwap = true;
        this.beginPlacing = false;
        this.addSetting(this.announceUsage);
        this.addSetting(this.debug);
        this.addSetting(this.carts);
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null || this.mc.world == null) {
            this.toggle();
            return;
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.tickDelay = 0;
        try {
            this.findClosestTarget();
        }
        catch (Exception ex) {}
        if (this.closestTarget != null) {
            if (this.announceUsage.getBooleanValue()) {
                CommandUtil.sendChatMessage("Attempting to TNT cart " + ChatFormatting.BLUE.toString() + this.closestTarget.getName() + ChatFormatting.WHITE.toString() + " ...");
            }
            this.targetPos = new BlockPos(this.closestTarget.getPositionVector());
        }
        else {
            if (this.announceUsage.getBooleanValue()) {
                CommandUtil.sendChatMessage("No target within range to TNT Cart.");
            }
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.announceUsage.getBooleanValue()) {}
        this.firstSwap = true;
        this.secondSwap = true;
        this.beginPlacing = false;
        this.tickDelay = 0;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        final int tntSlot = this.findTNTCart();
        final int railSlot = this.findRail();
        if (railSlot > -1 && this.firstSwap) {
            this.mc.player.inventory.currentItem = railSlot;
            this.firstSwap = false;
            this.placeBlock(this.targetPos, EnumFacing.DOWN);
            if (this.debug.getBooleanValue()) {
                CommandUtil.sendChatMessage("place rail");
            }
        }
        if (tntSlot > -1 && this.secondSwap && !this.firstSwap) {
            this.mc.player.inventory.currentItem = tntSlot;
            this.secondSwap = false;
            this.beginPlacing = true;
            if (this.debug.getBooleanValue()) {
                CommandUtil.sendChatMessage("swap tnt & place");
            }
        }
        if (!this.firstSwap && !this.secondSwap && this.beginPlacing) {
            if (this.tickDelay > 0) {
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
            if (this.tickDelay == this.carts.getIntegerValue()) {
                final int pickSlot = this.findPick();
                if (pickSlot > -1) {
                    this.mc.player.inventory.currentItem = pickSlot;
                }
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, EnumFacing.DOWN));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetPos, EnumFacing.DOWN));
                if (this.debug.getBooleanValue()) {
                    CommandUtil.sendChatMessage("break rail");
                }
            }
            if (this.tickDelay == this.carts.getIntegerValue() + 5) {
                final int flint = this.findFlint();
                if (flint > -1) {
                    this.mc.player.inventory.currentItem = flint;
                    this.placeBlock(this.targetPos, EnumFacing.DOWN);
                }
                else {
                    this.invGrabFlint();
                    this.mc.player.inventory.currentItem = 0;
                    this.placeBlock(this.targetPos, EnumFacing.DOWN);
                }
                this.toggle();
            }
        }
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)this.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == this.mc.player) {
                continue;
            }
            if (!isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.mc.player.getDistance((Entity)target) > 6.0f) {
                continue;
            }
            if (this.closestTarget != null) {
                continue;
            }
            this.closestTarget = target;
        }
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    private int findTNTCart() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (!(stack.getItem() instanceof ItemBlock)) {
                    final Item item = stack.getItem();
                    if (item instanceof ItemMinecart) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    private int findRail() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockRailBase) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    private int findFlint() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (!(stack.getItem() instanceof ItemBlock)) {
                    final Item item = stack.getItem();
                    if (item instanceof ItemFlintAndSteel) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    private void invGrabFlint() {
        if ((this.mc.currentScreen == null || !(this.mc.currentScreen instanceof GuiContainer)) && this.mc.player.inventory.getStackInSlot(0).getItem() != Items.FLINT_AND_STEEL) {
            for (int i = 9; i < 36; ++i) {
                if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.FLINT_AND_STEEL) {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, (EntityPlayer)this.mc.player);
                    this.lighterSlot = i;
                    break;
                }
            }
        }
    }
    
    private int findPick() {
        int pickSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemPickaxe) {
                    pickSlot = i;
                    break;
                }
            }
        }
        return pickSlot;
    }
    
    private void placeBlock(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        this.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}
