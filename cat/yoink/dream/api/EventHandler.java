//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api;

import cat.yoink.dream.api.command.CommandManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.module.ModuleManager;
import cat.cattyn.moneymod.money;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class EventHandler
{
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        final ModuleManager moduleManager = money.moduleManager;
        for (final Module module : ModuleManager.getModules()) {
            if (module.getBind() == Keyboard.getEventKey()) {
                module.toggle();
            }
        }
    }
    
    public static void onChatSend(final ClientChatEvent event) {
        if (!event.getMessage().startsWith("-") || event.getMessage().startsWith("/") || event.getMessage().startsWith(".") || event.getMessage().startsWith("#")) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            for (final Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {}
            }
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketChatMessage(event.getMessage()));
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void clientChatEvent(final ClientChatEvent event) {
        if (event.getMessage().startsWith(CommandManager.getPrefix())) {
            try {
                event.setCanceled(true);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                CommandManager.onCommand(event.getMessage());
                return;
            }
            catch (Exception ex) {}
        }
        onChatSend(event);
    }
}
