//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.Minecraft;

public class CommandUtil
{
    public static void sendChatMessage(final String message) {
        sendRawChatMessage("&7[&amoneymod+ 2&7] &r" + message);
    }
    
    public static void sendRawChatMessage(final String message) {
        final EntityPlayerSP SPPlayer = Minecraft.getMinecraft().player;
        SPPlayer.sendMessage((ITextComponent)new ChatMessage(message));
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        String text;
        
        public ChatMessage(final String text) {
            final Pattern p = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher m = p.matcher(text);
            final StringBuffer sb = new StringBuffer();
            while (m.find()) {
                final String replacement = "§" + m.group().substring(1);
                m.appendReplacement(sb, replacement);
            }
            m.appendTail(sb);
            this.text = sb.toString();
        }
        
        public String getUnformattedComponentText() {
            return this.text;
        }
        
        public ITextComponent createCopy() {
            return (ITextComponent)new ChatMessage(this.text);
        }
    }
}
