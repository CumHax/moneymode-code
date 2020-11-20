//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.module;

import cat.cattyn.moneymod.money;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.util.CommandUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;

public class Module
{
    private String name;
    private String description;
    private Category category;
    private int bind;
    private boolean enabled;
    private boolean toggled;
    public final Minecraft mc;
    
    public Module(final String name, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.category = category;
    }
    
    public Module(final String name, final String description, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void enable() {
        this.setEnabled(true);
        this.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (ModuleManager.getModule("ToggleMSG").isEnabled() && !this.getName().equalsIgnoreCase("ClickGUI")) {
            CommandUtil.sendChatMessage(this.getName() + ChatFormatting.GREEN + " Enabled.");
        }
    }
    
    public void disable() {
        this.setEnabled(false);
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (ModuleManager.getModule("ToggleMSG").isEnabled() && !this.getName().equalsIgnoreCase("ClickGUI")) {
            CommandUtil.sendChatMessage(this.getName() + ChatFormatting.RED + " Disabled.");
        }
    }
    
    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void addSetting(final Setting setting) {
        money.settingManager.addSetting(setting);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public void setBind(final int bind) {
        this.bind = bind;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
