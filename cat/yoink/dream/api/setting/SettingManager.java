// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.setting;

import java.util.Iterator;
import cat.yoink.dream.api.module.Module;
import java.util.ArrayList;

public class SettingManager
{
    public static final ArrayList<Setting> settings;
    
    public void addSetting(final Setting setting) {
        SettingManager.settings.add(setting);
    }
    
    public ArrayList<Setting> getSettings(final Module module) {
        final ArrayList<Setting> sets = new ArrayList<Setting>();
        for (final Setting setting : SettingManager.settings) {
            if (setting.getModule().equals(module)) {
                sets.add(setting);
            }
        }
        return sets;
    }
    
    public static Setting getSetting(final String moduleName, final String name) {
        for (final Setting setting : SettingManager.settings) {
            if (setting.getModule().getName().equalsIgnoreCase(moduleName) && setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }
    
    static {
        settings = new ArrayList<Setting>();
    }
}
