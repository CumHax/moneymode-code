//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util;

import net.minecraft.item.ItemFood;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

public class PlayerUtil extends ClassLoader
{
    private static final Minecraft mc;
    private static Entity en;
    
    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }
    
    public static FacingDirection GetFacing() {
        switch (MathHelper.floor(PlayerUtil.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0:
            case 1: {
                return FacingDirection.South;
            }
            case 2:
            case 3: {
                return FacingDirection.West;
            }
            case 4:
            case 5: {
                return FacingDirection.North;
            }
            case 6:
            case 7: {
                return FacingDirection.East;
            }
            default: {
                return FacingDirection.North;
            }
        }
    }
    
    public static int GetItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 0; i < PlayerUtil.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetRecursiveItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = PlayerUtil.mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetItemSlotNotHotbar(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 9; i < 36; ++i) {
            final Item item = PlayerUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                return i;
            }
        }
        return -1;
    }
    
    public static int GetItemCount(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = PlayerUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == input) {
                items += stack.getCount();
            }
        }
        return items;
    }
    
    public static boolean CanSeeBlock(final BlockPos p_Pos) {
        if (PlayerUtil.mc.player == null) {
            return false;
        }
        if (PlayerUtil.en == null && PlayerUtil.mc.world != null) {
            PlayerUtil.en = (Entity)new EntityChicken(PlayerUtil.mc.player.world);
        }
        PlayerUtil.en.setPosition(p_Pos.getX() + 0.5, p_Pos.getY() + 0.5, p_Pos.getZ() + 0.5);
        return PlayerUtil.mc.world.rayTraceBlocks(new Vec3d(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + PlayerUtil.mc.player.getEyeHeight(), PlayerUtil.mc.player.posZ), new Vec3d(PlayerUtil.en.posX, PlayerUtil.en.posY, PlayerUtil.en.posZ), false, true, false) == null;
    }
    
    public static boolean isCurrentViewEntity() {
        return PlayerUtil.mc.getRenderViewEntity() == PlayerUtil.mc.player;
    }
    
    public static boolean IsEating() {
        return PlayerUtil.mc.player != null && PlayerUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && PlayerUtil.mc.player.isHandActive();
    }
    
    public static int GetItemInHotbar(final Item p_Item) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() == p_Item) {
                return l_I;
            }
        }
        return -1;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlayerUtil.en = null;
    }
    
    public enum FacingDirection
    {
        North, 
        South, 
        East, 
        West;
    }
}
