// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import cat.yoink.dream.impl.event.AddCollisionBoxToListEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Block.class })
public class BlockMixin
{
    @Inject(method = { "addCollisionBoxToList" }, at = { @At("HEAD") }, cancellable = true)
    private void CollistionList(final IBlockState state, final World worldIn, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes, @Nullable final Entity entityIn, final boolean isActualState, final CallbackInfo callback) {
        final AddCollisionBoxToListEvent collisionBoxEvent = new AddCollisionBoxToListEvent(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        MinecraftForge.EVENT_BUS.post((Event)collisionBoxEvent);
        if (collisionBoxEvent.isCanceled()) {
            callback.cancel();
        }
    }
}
