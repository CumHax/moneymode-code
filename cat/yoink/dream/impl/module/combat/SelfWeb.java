//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import net.minecraft.util.math.BlockPos;
import cat.yoink.dream.api.module.Module;

public class SelfWeb extends Module
{
    BlockPos feet;
    int d;
    public static float yaw;
    public static float pitch;
    private final Setting delay;
    private final Setting toggled;
    
    public SelfWeb(final String name, final String description, final Category category) {
        super(name, description, category);
        this.delay = new Setting.Builder(SettingType.INTEGER).setName("Delay").setModule(this).setIntegerValue(3).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.toggled = new Setting.Builder(SettingType.BOOLEAN).setName("Toggle").setModule(this).setBooleanValue(true).build();
        this.addSetting(this.delay);
        this.addSetting(this.toggled);
    }
    
    public boolean isInBlockRange(final Entity target) {
        return target.getDistance((Entity)this.mc.player) <= 4.0f;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(pos), false);
    }
    
    private boolean isStackObby(final ItemStack stack) {
        return stack != null && stack.getItem() == Item.getItemById(30);
    }
    
    private boolean doesHotbarHaveWeb() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = this.mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && this.isStackObby(stack)) {
                return true;
            }
        }
        return false;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos);
    }
    
    public static boolean placeBlockLegit(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ);
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 36.0) {
                    Minecraft.getMinecraft().playerController.processRightClickBlock(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, neighbor, side.getOpposite(), hitVec, EnumHand.MAIN_HAND);
                    Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.player.isHandActive()) {
            return;
        }
        this.trap((EntityPlayer)this.mc.player);
    }
    
    public static double roundToHalf(final double d) {
        return Math.round(d * 2.0) / 2.0;
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            this.disable();
            return;
        }
        this.d = 0;
    }
    
    private void trap(final EntityPlayer player) {
        if (player.moveForward == 0.0 && player.moveStrafing == 0.0 && player.moveForward == 0.0) {
            ++this.d;
        }
        if (player.moveForward != 0.0 || player.moveStrafing != 0.0 || player.moveForward != 0.0) {
            this.d = 0;
        }
        if (!this.doesHotbarHaveWeb()) {
            this.d = 0;
        }
        if (this.d == this.delay.getIntegerValue() && this.doesHotbarHaveWeb()) {
            this.feet = new BlockPos(player.posX, player.posY, player.posZ);
            for (int i = 36; i < 45; ++i) {
                final ItemStack stack = this.mc.player.inventoryContainer.getSlot(i).getStack();
                if (stack != null && this.isStackObby(stack)) {
                    final int oldSlot = this.mc.player.inventory.currentItem;
                    if (this.mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                        this.mc.player.inventory.currentItem = i - 36;
                        if (this.mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                            placeBlockLegit(this.feet);
                        }
                        this.mc.player.inventory.currentItem = oldSlot;
                        this.d = 0;
                        if (this.toggled.getBooleanValue()) {
                            this.toggle();
                            break;
                        }
                        break;
                    }
                    else {
                        this.d = 0;
                    }
                }
                this.d = 0;
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.d = 0;
        SelfWeb.yaw = this.mc.player.rotationYaw;
        SelfWeb.pitch = this.mc.player.rotationPitch;
    }
    
    public EnumFacing getEnumFacing(final float posX, final float posY, final float posZ) {
        return EnumFacing.getFacingFromVector(posX, posY, posZ);
    }
    
    public BlockPos getBlockPos(final double x, final double y, final double z) {
        return new BlockPos(x, y, z);
    }
}
