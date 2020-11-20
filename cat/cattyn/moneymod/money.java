// 
// Decompiled by Procyon v0.5.36
// 

package cat.cattyn.moneymod;

import org.lwjgl.opengl.Display;
import cat.yoink.dream.api.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import java.awt.Font;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import cat.yoink.dream.api.util.ConfigUtils;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.gui.clickgui.ClickGUI;
import cat.yoink.dream.api.util.font.CustomFontRenderer;
import cat.yoink.dream.api.setting.SettingManager;
import cat.yoink.dream.api.module.ModuleManager;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "moneymod+", name = "Moneymod+", version = "2")
public class money
{
    public static String MODID;
    public static String MODNAME;
    public static String MODVER;
    public static String credits;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static CustomFontRenderer customFontRenderer;
    public static ClickGUI clickGUI;
    public static CommandManager commandManager;
    public static ConfigUtils configUtils;
    
    @Mod.EventHandler
    public void initialize(final FMLInitializationEvent event) {
        money.settingManager = new SettingManager();
        money.moduleManager = new ModuleManager();
        money.customFontRenderer = new CustomFontRenderer(new Font("Verdana", 0, 18), true, false);
        money.clickGUI = new ClickGUI();
        CommandManager.initialize();
        money.configUtils = new ConfigUtils();
        MinecraftForge.EVENT_BUS.register((Object)new EventHandler());
        Display.setTitle(money.MODID + " " + money.MODVER);
    }
    
    static {
        money.MODID = "moneymod+";
        money.MODNAME = "Moneymod+";
        money.MODVER = "2";
        money.credits = "yoink, cattyn";
    }
}
