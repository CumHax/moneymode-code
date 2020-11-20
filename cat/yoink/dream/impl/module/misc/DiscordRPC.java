// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import cat.cattyn.moneymod.moneydiscord;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class DiscordRPC extends Module
{
    public DiscordRPC(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @Override
    public void onEnable() {
        moneydiscord.start();
    }
    
    @Override
    public void onDisable() {
        moneydiscord.stop();
    }
}
