//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util;

import cat.yoink.dream.api.command.CommandManager;
import org.lwjgl.input.Keyboard;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingManager;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.module.ModuleManager;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import net.minecraft.client.Minecraft;

public class ConfigUtils
{
    Minecraft mc;
    public File moneymod;
    public File Settings;
    
    public ConfigUtils() {
        this.mc = Minecraft.getMinecraft();
        this.moneymod = new File(this.mc.gameDir + File.separator + "moneymod");
        if (!this.moneymod.exists()) {
            this.moneymod.mkdirs();
        }
        this.Settings = new File(this.mc.gameDir + File.separator + "moneymod" + File.separator + "Settings");
        if (!this.Settings.exists()) {
            this.Settings.mkdirs();
        }
        this.loadMods();
        this.loadSettingsList();
        this.loadBinds();
        this.loadPrefix();
    }
    
    public void saveMods() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "EnabledModules.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    out.write(module.getName());
                    out.write("\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadMods() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "EnabledModules.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (final Module m : ModuleManager.getModules()) {
                    if (m.getName().equals(line)) {
                        m.enable();
                    }
                }
            }
            br.close();
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }
    
    public void saveSettingsList() {
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "Number.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Setting i : SettingManager.settings) {
                if (i.getType() == SettingType.INTEGER) {
                    out.write(i.getName() + ":" + i.getIntegerValue() + ":" + i.getModule().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "Boolean.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Setting i : SettingManager.settings) {
                if (i.getType() == SettingType.BOOLEAN) {
                    out.write(i.getName() + ":" + i.getBooleanValue() + ":" + i.getModule().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "String.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Setting i : SettingManager.settings) {
                if (i.getType() == SettingType.ENUM) {
                    out.write(i.getName() + ":" + i.getEnumValue() + ":" + i.getModule().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
        }
    }
    
    public void loadSettingsList() {
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "Number.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Setting mod = SettingManager.getSetting(mm.getName(), name);
                        if (mod.getType() != SettingType.INTEGER) {
                            continue;
                        }
                        mod.setIntegerValue(Integer.parseInt(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "Boolean.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Setting mod = SettingManager.getSetting(mm.getName(), name);
                        mod.setBooleanValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(this.Settings.getAbsolutePath(), "String.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Setting mod = SettingManager.getSetting(mm.getName(), name);
                        mod.setEnumValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void saveBinds() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "Binds.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Module module : ModuleManager.getModules()) {
                out.write(module.getName() + ":" + Keyboard.getKeyName(module.getBind()));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void savePrefix() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "Prefix.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(CommandManager.prefix);
            out.close();
        }
        catch (Exception var11) {
            var11.printStackTrace();
        }
    }
    
    public void loadPrefix() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "Prefix.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            final String line;
            if ((line = br.readLine()) != null) {
                CommandManager.setPrefix(line);
            }
            br.close();
        }
        catch (Exception var11) {
            var11.printStackTrace();
        }
    }
    
    public void loadBinds() {
        try {
            final File file = new File(this.moneymod.getAbsolutePath(), "Binds.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String bind = curLine.split(":")[1];
                for (final Module m : ModuleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }
            br.close();
        }
        catch (Exception var11) {
            var11.printStackTrace();
        }
    }
}
