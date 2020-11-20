// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Reach extends Module
{
    public final Setting range;
    
    public Reach(final String name, final String description, final Category category) {
        super(name, description, category);
        this.addSetting(this.range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this).setIntegerValue(1).setMinIntegerValue(0).setMaxIntegerValue(10).build());
    }
}
