// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command.commands;

import java.io.File;
import java.awt.Desktop;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.Command;

public class OpenFolder extends Command
{
    public OpenFolder() {
        super("OpenFolder", "OpenFolder", new String[] { "openfolder" });
    }
    
    @Override
    public void onCommand(final String arguments) {
        CommandUtil.sendChatMessage(String.format("opening folder", new Object[0]));
        try {
            Desktop.getDesktop().open(new File("moneymod"));
        }
        catch (Exception ex) {}
    }
}
