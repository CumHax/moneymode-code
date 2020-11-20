// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class AntiOverlay extends Module
{
    public AntiOverlay(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onEvent(final RenderGameOverlayEvent event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HELMET) || event.getType().equals((Object)RenderGameOverlayEvent.ElementType.PORTAL)) {
            event.setCanceled(true);
        }
    }
}
