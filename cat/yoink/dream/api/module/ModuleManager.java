// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.module;

import java.util.Iterator;
import cat.yoink.dream.impl.module.combat.Auto32k;
import cat.yoink.dream.impl.module.combat.HoleFiller;
import cat.yoink.dream.impl.module.movement.OldStrafe;
import cat.yoink.dream.impl.module.exploit.AntiHunger;
import cat.yoink.dream.impl.module.misc.PortalChat;
import cat.yoink.dream.impl.module.exploit.NoBreakAnimation;
import cat.yoink.dream.impl.module.render.EnchantColor;
import cat.yoink.dream.impl.module.render.NoHurtCam;
import cat.yoink.dream.impl.module.render.CameraClip;
import cat.yoink.dream.impl.module.misc.NoSoundLag;
import cat.yoink.dream.impl.module.render.Chams;
import cat.yoink.dream.impl.module.combat.AutoLog;
import cat.yoink.dream.impl.module.exploit.AntiSurround;
import cat.yoink.dream.impl.module.combat.NewOffhand;
import cat.yoink.dream.impl.module.movement.FastSwim;
import cat.yoink.dream.impl.module.render.NoBob;
import cat.yoink.dream.impl.module.misc.FakeGamemode;
import cat.yoink.dream.impl.module.exploit.BoatClip;
import cat.yoink.dream.impl.module.misc.AutoRespawn;
import cat.yoink.dream.impl.module.combat.BedAuraEcMe;
import cat.yoink.dream.impl.module.combat.KillAura;
import cat.yoink.dream.impl.module.render.Compass;
import cat.yoink.dream.impl.module.misc.XCarry;
import cat.yoink.dream.impl.module.combat.Blink;
import cat.yoink.dream.impl.module.misc.AntiOverlay;
import cat.yoink.dream.impl.module.movement.NoGlitchBlocks;
import cat.yoink.dream.impl.module.misc.AntiFog;
import cat.yoink.dream.impl.module.misc.AntiCactus;
import cat.yoink.dream.impl.module.misc.AntiFire;
import cat.yoink.dream.impl.module.movement.AutoWalk;
import cat.yoink.dream.impl.module.movement.AutoSwim;
import cat.yoink.dream.impl.module.movement.InventoryMove;
import cat.yoink.dream.impl.module.exploit.AntiEndCrystal;
import cat.yoink.dream.impl.module.exploit.NoVoid;
import cat.yoink.dream.impl.module.misc.AntiLevitate;
import cat.yoink.dream.impl.module.misc.AntiBlind;
import cat.yoink.dream.impl.module.combat.AutoTNTCart;
import cat.yoink.dream.impl.module.combat.AutoTrap;
import cat.yoink.dream.impl.module.combat.Offhand;
import cat.yoink.dream.impl.module.movement.Anchor;
import cat.yoink.dream.impl.module.misc.ToggleMSG;
import cat.yoink.dream.impl.module.exploit.LavaFlight;
import cat.yoink.dream.impl.module.combat.SelfWeb;
import cat.yoink.dream.impl.module.combat.BedAura;
import cat.yoink.dream.impl.module.render.LowHands;
import cat.yoink.dream.impl.module.misc.DiscordRPC;
import cat.yoink.dream.impl.module.misc.SkinBlinker;
import cat.yoink.dream.impl.module.render.SkyColor;
import cat.yoink.dream.impl.module.render.NoWeather;
import cat.yoink.dream.impl.module.combat.FastBow;
import cat.yoink.dream.impl.module.combat.AutoArmor;
import cat.yoink.dream.impl.module.render.Fullbright;
import cat.yoink.dream.impl.module.render.Skeleton;
import cat.yoink.dream.impl.module.combat.Burrow;
import cat.yoink.dream.impl.module.combat.Surround;
import cat.yoink.dream.impl.module.exploit.PacketFly;
import cat.yoink.dream.impl.module.movement.Step;
import cat.yoink.dream.impl.module.movement.Strafe;
import cat.yoink.dream.impl.module.combat.AutoRun;
import cat.yoink.dream.impl.module.render.CoordsHUD;
import cat.yoink.dream.impl.module.exploit.PortalGodMode;
import cat.yoink.dream.impl.module.combat.PacketXP;
import cat.yoink.dream.impl.module.movement.NoSlow;
import cat.yoink.dream.impl.module.movement.ReverseStep;
import cat.yoink.dream.impl.module.exploit.FastPlace;
import cat.yoink.dream.impl.module.combat.MultiTask;
import cat.yoink.dream.impl.module.combat.AutoTotem;
import cat.yoink.dream.impl.module.combat.Velocity;
import cat.yoink.dream.impl.module.render.ItemViewmodle;
import cat.yoink.dream.impl.module.combat.AutoCrystal;
import cat.yoink.dream.impl.module.movement.BoatFly;
import cat.yoink.dream.impl.module.exploit.PingBypass;
import cat.yoink.dream.impl.module.render.Fov;
import cat.yoink.dream.impl.module.movement.Sprint;
import cat.yoink.dream.impl.module.render.Watermark;
import cat.yoink.dream.impl.module.render.ActiveModules;
import cat.yoink.dream.impl.module.misc.ChatSuffix;
import cat.yoink.dream.impl.module.movement.LongJump;
import cat.yoink.dream.impl.module.combat.Criticals;
import cat.yoink.dream.impl.module.misc.Timer;
import cat.yoink.dream.impl.module.exploit.PacketMine;
import cat.yoink.dream.impl.module.render.CustomFont;
import cat.yoink.dream.impl.module.render.ClickGUI;
import java.util.ArrayList;

public class ModuleManager
{
    private static final ArrayList<Module> modules;
    
    public ModuleManager() {
        ModuleManager.modules.add(new ClickGUI("ClickGUI", "Toggle modules by clicking on them", Category.RENDER));
        ModuleManager.modules.add(new CustomFont("CustomFont", "Use a custom font render instead of Minecraft's default", Category.RENDER));
        ModuleManager.modules.add(new PacketMine("PacketMine", "Mine blocks with packets", Category.EXPLOIT));
        ModuleManager.modules.add(new Timer("Timer", "Speeds up your game", Category.MISC));
        ModuleManager.modules.add(new Criticals("Criticals", "Deal critical hits without jumping", Category.COMBAT));
        ModuleManager.modules.add(new LongJump("LongJump", "Jumps far", Category.MOVEMENT));
        ModuleManager.modules.add(new ChatSuffix("ChatSuffix", "Adds a suffix to your chat messages", Category.MISC));
        ModuleManager.modules.add(new ActiveModules("ActiveModules", "ActiveModules", Category.RENDER));
        ModuleManager.modules.add(new Watermark("Watermark", "Shows modname on screen", Category.RENDER));
        ModuleManager.modules.add(new Sprint("Sprint", "Toggle sprint", Category.MOVEMENT));
        ModuleManager.modules.add(new Fov("Fov", "Change Fov", Category.RENDER));
        ModuleManager.modules.add(new PingBypass("PingBypass", "0_0", Category.EXPLOIT));
        ModuleManager.modules.add(new BoatFly("BoatFly", "BoatFly++++++++ bypass", Category.MOVEMENT));
        ModuleManager.modules.add(new AutoCrystal("AutoCrystal", "U know...", Category.COMBAT));
        ModuleManager.modules.add(new ItemViewmodle("ItemViewmodel", "ItemViewmodel", Category.RENDER));
        ModuleManager.modules.add(new Velocity("Velocity", "Velocity", Category.COMBAT));
        ModuleManager.modules.add(new AutoTotem("AutoTotem", "AutoTotem", Category.COMBAT));
        ModuleManager.modules.add(new MultiTask("MultiTask", "MultiTask", Category.COMBAT));
        ModuleManager.modules.add(new FastPlace("FastPlace", "FastPlace", Category.EXPLOIT));
        ModuleManager.modules.add(new ReverseStep("ReverseStep", "ReverseStep", Category.MOVEMENT));
        ModuleManager.modules.add(new NoSlow("NoSlowDown", "NoSlowDown", Category.MOVEMENT));
        ModuleManager.modules.add(new PacketXP("FootXP", "FootXP", Category.COMBAT));
        ModuleManager.modules.add(new PortalGodMode("PortalGodMode", "PortalGodMode", Category.EXPLOIT));
        ModuleManager.modules.add(new CoordsHUD("Position", "Position", Category.RENDER));
        ModuleManager.modules.add(new AutoRun("AutoRun", "AutoRun", Category.COMBAT));
        ModuleManager.modules.add(new Strafe("Strafe", "Strafe", Category.MOVEMENT));
        ModuleManager.modules.add(new Step("Step", "Step", Category.MOVEMENT));
        ModuleManager.modules.add(new PacketFly("PacketFly", "PacketFly", Category.EXPLOIT));
        ModuleManager.modules.add(new Surround("Surround", "Surround", Category.COMBAT));
        ModuleManager.modules.add(new Burrow("Burrow", "Burrow", Category.COMBAT));
        ModuleManager.modules.add(new Skeleton("Skeleton", "Skeleton", Category.RENDER));
        ModuleManager.modules.add(new Fullbright("Fullbright", "Fullbright", Category.RENDER));
        ModuleManager.modules.add(new AutoArmor("AutoArmor", "AutoArmor", Category.COMBAT));
        ModuleManager.modules.add(new FastBow("FastBow", "FastBow", Category.COMBAT));
        ModuleManager.modules.add(new NoWeather("NoWeather", "NoWeather", Category.RENDER));
        ModuleManager.modules.add(new SkyColor("SkyColor", "SkyColor", Category.RENDER));
        ModuleManager.modules.add(new SkinBlinker("SkinBlinker", "SkinBlinker", Category.MISC));
        ModuleManager.modules.add(new DiscordRPC("DiscordRPC", "DiscordRPC", Category.MISC));
        ModuleManager.modules.add(new LowHands("LowHands", "LowHands", Category.RENDER));
        ModuleManager.modules.add(new BedAura("BedAura", "BedAura", Category.COMBAT));
        ModuleManager.modules.add(new SelfWeb("SelfWeb", "SelfWeb", Category.COMBAT));
        ModuleManager.modules.add(new LavaFlight("LavaFlight", "LavaFlight", Category.EXPLOIT));
        ModuleManager.modules.add(new ToggleMSG("ToggleMSG", "ToggleMSG", Category.MISC));
        ModuleManager.modules.add(new Anchor("Anchor", "Anchor", Category.COMBAT));
        ModuleManager.modules.add(new Offhand("Offhand", "Offhand", Category.COMBAT));
        ModuleManager.modules.add(new AutoTrap("AutoTrap", "AutoTrap", Category.COMBAT));
        ModuleManager.modules.add(new AutoTNTCart("AutoTNTCart", "AutoTNTCart", Category.COMBAT));
        ModuleManager.modules.add(new AntiBlind("AntiBlind", "AntiBlind", Category.MISC));
        ModuleManager.modules.add(new AntiLevitate("AntiLevitate", "AntiLevitate", Category.MISC));
        ModuleManager.modules.add(new NoVoid("NoVoid", "NoVoid", Category.EXPLOIT));
        ModuleManager.modules.add(new AntiEndCrystal("AntiEndCrystal", "AntiEndCrystal", Category.EXPLOIT));
        ModuleManager.modules.add(new InventoryMove("InventoryMove", "InventoryMove", Category.MOVEMENT));
        ModuleManager.modules.add(new AutoSwim("AutoSwim", "AutoSwim", Category.MOVEMENT));
        ModuleManager.modules.add(new AutoWalk("AutoWalk", "AutoWalk", Category.MOVEMENT));
        ModuleManager.modules.add(new AntiFire("AntiFire", "AntiFire", Category.MISC));
        ModuleManager.modules.add(new AntiCactus("AntiCactus", "AntiCactus", Category.MISC));
        ModuleManager.modules.add(new AntiFog("AntiFog", "AntiFog", Category.MISC));
        ModuleManager.modules.add(new NoGlitchBlocks("NoGlitchBlocks", "NoGlitchBlocks", Category.MISC));
        ModuleManager.modules.add(new AntiOverlay("AntiOverlay", "AntiOverlay", Category.RENDER));
        ModuleManager.modules.add(new Blink("Blink", "Blink", Category.COMBAT));
        ModuleManager.modules.add(new XCarry("XCarry", "XCarry", Category.EXPLOIT));
        ModuleManager.modules.add(new Compass("Compass", "Compass", Category.RENDER));
        ModuleManager.modules.add(new KillAura("KillAura", "KillAura", Category.COMBAT));
        ModuleManager.modules.add(new BedAuraEcMe("1.13 BedAura", "1.13 BedAura", Category.COMBAT));
        ModuleManager.modules.add(new AutoRespawn("AutoRespawn", "AutoRespawn", Category.MISC));
        ModuleManager.modules.add(new BoatClip("BoatClip", "BoatClip", Category.EXPLOIT));
        ModuleManager.modules.add(new FakeGamemode("FakeGamemode", "FakeGamemode", Category.MISC));
        ModuleManager.modules.add(new NoBob("NoBob", "NoBob", Category.RENDER));
        ModuleManager.modules.add(new FastSwim("FastSwim", "FastSwim", Category.MOVEMENT));
        ModuleManager.modules.add(new NewOffhand("NewOffhand", "NewOffhand", Category.COMBAT));
        ModuleManager.modules.add(new AntiSurround("AntiSurround", "AntiSurround", Category.EXPLOIT));
        ModuleManager.modules.add(new AutoLog("AutoLog", "AutoLog", Category.COMBAT));
        ModuleManager.modules.add(new Chams("Chams", "Chams", Category.RENDER));
        ModuleManager.modules.add(new NoSoundLag("NoSoundLag", "Lag Exploit Fix", Category.MISC));
        ModuleManager.modules.add(new CameraClip("CameraClip", "CameraClip", Category.RENDER));
        ModuleManager.modules.add(new NoHurtCam("NoHurtCam", "NoHurtCam", Category.RENDER));
        ModuleManager.modules.add(new EnchantColor("EnchantColor", "EnchantColor", Category.RENDER));
        ModuleManager.modules.add(new NoBreakAnimation("NoBreakAnimation", "NoBreakAnimation", Category.EXPLOIT));
        ModuleManager.modules.add(new PortalChat("PortalChat", "Allows you to chat in portals", Category.MISC));
        ModuleManager.modules.add(new AntiHunger("AntiHunger", "Prevents you from getting hungry", Category.EXPLOIT));
        ModuleManager.modules.add(new OldStrafe("OldStrafe", "OldStrafe", Category.MOVEMENT));
        ModuleManager.modules.add(new HoleFiller("HoleFiller", "Fills holes", Category.COMBAT));
        ModuleManager.modules.add(new Auto32k("Auto32k", "Auto 32k", Category.COMBAT));
    }
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static void onRender() {
        ModuleManager.modules.stream().filter(Module::isEnabled);
    }
    
    public static Module getModule(final String name) {
        for (final Module module : ModuleManager.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public ArrayList<Module> getModules(final Category category) {
        final ArrayList<Module> mods = new ArrayList<Module>();
        for (final Module module : ModuleManager.modules) {
            if (module.getCategory().equals(category)) {
                mods.add(module);
            }
        }
        return mods;
    }
    
    static {
        modules = new ArrayList<Module>();
    }
}
