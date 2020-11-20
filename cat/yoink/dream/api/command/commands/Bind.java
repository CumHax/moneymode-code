// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command.commands;

import java.util.Iterator;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.module.ModuleManager;
import org.lwjgl.input.Keyboard;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.command.Command;

public class Bind extends Command
{
    public Bind() {
        super("Bind", "Set the Bind", new String[] { "bind" });
    }
    
    @Override
    public void onCommand(final String arguments) {
        if (arguments.equals("")) {
            CommandUtil.sendChatMessage(String.format("&7Usage: %sbind <Module> <Key>", CommandManager.getPrefix()));
            return;
        }
        boolean moduleFound = false;
        final String[] arg = arguments.split(" ");
        final int key = Keyboard.getKeyIndex(arg[1].toUpperCase());
        for (final Module module : ModuleManager.getModules()) {
            if (module.getName().equalsIgnoreCase(arg[0])) {
                try {
                    if (Keyboard.getKeyName(key).equals("NONE")) {
                        module.setBind(256);
                    }
                    else {
                        module.setBind(key);
                    }
                    CommandUtil.sendChatMessage(String.format("bound %s to %s", module.getName(), Keyboard.getKeyName(key)));
                    moduleFound = true;
                }
                catch (Exception ex) {}
            }
        }
        if (!moduleFound) {
            CommandUtil.sendChatMessage("&7Module not found");
        }
    }
}
