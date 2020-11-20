//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command.commands;

import org.json.alt.simple.JSONObject;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import org.json.alt.simple.parser.JSONParser;
import java.net.URL;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import cat.yoink.dream.api.util.CommandUtil;
import cat.yoink.dream.api.command.CommandManager;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import java.util.UUID;
import cat.yoink.dream.api.command.Command;

public class FakePlayer extends Command
{
    private UUID localPlayerUUID;
    EntityOtherPlayerMP fakePlayer;
    
    public FakePlayer() {
        super("Fakeplayer", "Set the FakePlayer", new String[] { "fakeplayer" });
        try {
            this.localPlayerUUID = UUID.fromString(getUuid("myrh"));
        }
        catch (Exception e) {
            this.localPlayerUUID = UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03");
        }
    }
    
    @Override
    public void onCommand(final String arguments) {
        if (this.localPlayerUUID == null) {
            this.localPlayerUUID = UUID.fromString(getUuid("myrh"));
        }
        if (arguments.equals("")) {
            CommandUtil.sendChatMessage(String.format("&7Usage: %sfakeplayer spawn/remove", CommandManager.getPrefix()));
            return;
        }
        if (arguments.equalsIgnoreCase("spawn")) {
            this.fakePlayer = null;
            if (Minecraft.getMinecraft().world == null) {
                return;
            }
            try {
                this.fakePlayer = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, new GameProfile(this.localPlayerUUID, "myrh"));
            }
            catch (Exception e) {
                this.fakePlayer = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "myrh"));
            }
            this.fakePlayer.copyLocationAndAnglesFrom((Entity)Minecraft.getMinecraft().player);
            this.fakePlayer.rotationYawHead = Minecraft.getMinecraft().player.rotationYawHead;
            Minecraft.getMinecraft().world.addEntityToWorld(-100, (Entity)this.fakePlayer);
            CommandUtil.sendChatMessage("fakeplayer spawned");
        }
        else {
            if (!arguments.equalsIgnoreCase("remove")) {
                CommandUtil.sendChatMessage(String.format("&7Usage: %sfakeplayer spawn/remove", CommandManager.getPrefix()));
                return;
            }
            Minecraft.getMinecraft().world.removeEntity((Entity)this.fakePlayer);
            CommandUtil.sendChatMessage("fakeplayer removed");
        }
    }
    
    public static URL getUUIDURL(final String name) {
        try {
            final URL returnvalue = new URL(name);
            return returnvalue;
        }
        catch (Exception except) {
            except.printStackTrace();
            return null;
        }
    }
    
    public static URL[] convertURLToArray(final URL url) {
        return new URL[] { url };
    }
    
    public static String getUuid(final String name) {
        final JSONParser parser = new JSONParser();
        final URL url = convertURLToArray(getUUIDURL("https://api.mojang.com/users/profiles/minecraft/" + name))[0];
        if (url == null) {
            return null;
        }
        try {
            final String UUIDJson = IOUtils.toString(url, StandardCharsets.UTF_8);
            if (UUIDJson.isEmpty()) {
                return "invalid name";
            }
            final JSONObject UUIDObject = (JSONObject)parser.parse(UUIDJson);
            return reformatUuid(UUIDObject.get("id").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    private static String reformatUuid(final String uuid) {
        String longUuid = "";
        longUuid = longUuid + uuid.substring(1, 9) + "-";
        longUuid = longUuid + uuid.substring(9, 13) + "-";
        longUuid = longUuid + uuid.substring(13, 17) + "-";
        longUuid = longUuid + uuid.substring(17, 21) + "-";
        longUuid += uuid.substring(21, 33);
        return longUuid;
    }
}
