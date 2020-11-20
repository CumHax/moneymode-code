//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraft.client.gui.GuiScreen;
import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class ClickGUI extends Module
{
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting rainbow;
    
    public ClickGUI(final String name, final String description, final Category category) {
        super(name, description, category);
        this.red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this).setBooleanValue(false).build();
        this.setBind(25);
        this.addSetting(this.red);
        this.addSetting(this.green);
        this.addSetting(this.blue);
        this.addSetting(this.rainbow);
    }
    
    @Override
    public void onEnable() {
        this.mc.displayGuiScreen((GuiScreen)money.clickGUI);
    }
}
