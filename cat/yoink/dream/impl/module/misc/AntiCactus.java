//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import cat.yoink.dream.impl.event.AddCollisionBoxToListEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class AntiCactus extends Module
{
    public AntiCactus(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onAddCollisionBox(final AddCollisionBoxToListEvent event) {
        if (this.mc.player != null) {
            final AxisAlignedBB bb = new AxisAlignedBB(event.getPos()).expand(0.0, 0.1, 0.0);
            if (event.getBlock() == Blocks.CACTUS && this.isAbovePlayer(event.getPos()) && event.getEntityBox().intersects(bb)) {
                event.getCollidingBoxes().add(bb);
            }
        }
    }
    
    private boolean isAbovePlayer(final BlockPos pos) {
        return pos.getY() >= this.mc.player.posY;
    }
}
