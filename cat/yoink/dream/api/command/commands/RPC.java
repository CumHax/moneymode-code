// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command.commands;

import cat.cattyn.moneymod.moneydiscord;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.CommandManager;
import cat.yoink.dream.api.command.Command;

public class RPC extends Command
{
    public RPC() {
        super("rpc", "prikol", new String[] { "rpc" });
    }
    
    @Override
    public void onCommand(final String arguments) {
        if (arguments.equals("")) {
            CommandUtil.sendChatMessage(String.format("&7Usage: %srpc <string>", CommandManager.getPrefix()));
        }
        moneydiscord.setState(arguments);
        CommandUtil.sendChatMessage(String.format("&7Set rpc to %s", arguments));
    }
}
