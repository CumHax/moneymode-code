//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class AutoRun extends Module
{
    public AutoRun(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @Override
    public void onEnable() {
        this.mc.player.sendChatMessage("#goto ~100 ~ ~100");
        this.disable();
    }
}
