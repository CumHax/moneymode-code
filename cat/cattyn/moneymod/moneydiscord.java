//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.cattyn.moneymod;

import net.minecraft.client.Minecraft;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class moneydiscord
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static Thread thread;
    static String astate;
    
    public static void setState(final String state) {
        moneydiscord.astate = state;
    }
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        String server2 = Minecraft.getMinecraft().isSingleplayer() ? "singleplayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP;
        if (server2 == null) {
            server2 = "Main Menu";
        }
        moneydiscord.rpc.Discord_Initialize("771754886231621652", handlers, true, "");
        moneydiscord.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        moneydiscord.presence.details = Minecraft.getMinecraft().getSession().getUsername() + " | " + server2;
        moneydiscord.presence.state = moneydiscord.astate;
        moneydiscord.presence.largeImageKey = "money";
        moneydiscord.rpc.Discord_UpdatePresence(moneydiscord.presence);
        String server3;
        (moneydiscord.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                server3 = (Minecraft.getMinecraft().isSingleplayer() ? "singleplayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP);
                if (server3 == null) {
                    server3 = "Main Menu";
                }
                moneydiscord.rpc.Discord_RunCallbacks();
                moneydiscord.presence.details = Minecraft.getMinecraft().getSession().getUsername() + " | " + server3;
                moneydiscord.presence.state = moneydiscord.astate;
                moneydiscord.rpc.Discord_UpdatePresence(moneydiscord.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        if (moneydiscord.thread != null && !moneydiscord.thread.isInterrupted()) {
            moneydiscord.thread.interrupt();
        }
        moneydiscord.rpc.Discord_Shutdown();
    }
    
    static {
        moneydiscord.astate = "crystalpvp for monke";
        rpc = DiscordRPC.INSTANCE;
        moneydiscord.presence = new DiscordRichPresence();
    }
}
