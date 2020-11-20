// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command;

import java.util.Iterator;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.commands.Clip;
import cat.yoink.dream.api.command.commands.FakePlayer;
import cat.yoink.dream.api.command.commands.Bind;
import cat.yoink.dream.api.command.commands.OpenFolder;
import cat.yoink.dream.api.command.commands.RPC;
import cat.yoink.dream.api.command.commands.Prefix;
import java.util.ArrayList;

public class CommandManager
{
    public static ArrayList<Command> commands;
    public static String prefix;
    
    public static void initialize() {
        CommandManager.commands.add(new Prefix());
        CommandManager.commands.add(new RPC());
        CommandManager.commands.add(new OpenFolder());
        CommandManager.commands.add(new Bind());
        CommandManager.commands.add(new FakePlayer());
        CommandManager.commands.add(new Clip());
    }
    
    public static void onCommand(final String input) {
        boolean commandFound = false;
        final String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String startCommand = split[0];
        final String args = input.substring(startCommand.length()).trim();
        for (final Command command : CommandManager.commands) {
            for (final String alias : command.getAlias()) {
                if (startCommand.equals(getPrefix() + alias)) {
                    commandFound = true;
                    command.onCommand(args);
                }
            }
        }
        if (!commandFound) {
            CommandUtil.sendChatMessage("&cCommand not found");
        }
    }
    
    public static ArrayList<Command> getCommands() {
        return CommandManager.commands;
    }
    
    public static String getPrefix() {
        return CommandManager.prefix;
    }
    
    public static void setPrefix(final String prefix) {
        CommandManager.prefix = prefix;
    }
    
    static {
        CommandManager.commands = new ArrayList<Command>();
        CommandManager.prefix = ".";
    }
}
