// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.setting;

import java.util.ArrayList;
import cat.yoink.dream.api.module.Module;

public class Setting
{
    private String name;
    private Module module;
    private SettingType type;
    private boolean booleanValue;
    private int integerValue;
    private int minIntegerValue;
    private int maxIntegerValue;
    private String enumValue;
    private ArrayList<String> enumValues;
    
    public Setting(final String name, final Module module, final int intValue, final int intMinValue, final int intMaxValue) {
        this.setName(name);
        this.setModule(module);
        this.setIntegerValue(intValue);
        this.setMinIntegerValue(intMinValue);
        this.setMaxIntegerValue(intMaxValue);
        this.setType(SettingType.INTEGER);
    }
    
    private Setting(final String name, final Module module, final boolean boolValue) {
        this.setName(name);
        this.setModule(module);
        this.setBooleanValue(boolValue);
        this.setType(SettingType.BOOLEAN);
    }
    
    private Setting(final String name, final Module module, final String enumValue, final ArrayList<String> enumValues) {
        this.setName(name);
        this.setModule(module);
        this.setEnumValue(enumValue);
        this.setEnumValues(enumValues);
        this.setType(SettingType.ENUM);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public SettingType getType() {
        return this.type;
    }
    
    public void setType(final SettingType type) {
        this.type = type;
    }
    
    public boolean getBooleanValue() {
        return this.booleanValue;
    }
    
    public void setBooleanValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    
    public int getIntegerValue() {
        return this.integerValue;
    }
    
    public void setIntegerValue(final int integerValue) {
        this.integerValue = integerValue;
    }
    
    public int getMinIntegerValue() {
        return this.minIntegerValue;
    }
    
    public void setMinIntegerValue(final int minIntegerValue) {
        this.minIntegerValue = minIntegerValue;
    }
    
    public int getMaxIntegerValue() {
        return this.maxIntegerValue;
    }
    
    public void setMaxIntegerValue(final int maxIntegerValue) {
        this.maxIntegerValue = maxIntegerValue;
    }
    
    public String getEnumValue() {
        return this.enumValue;
    }
    
    public void setEnumValue(final String enumValue) {
        this.enumValue = enumValue;
    }
    
    public ArrayList<String> getEnumValues() {
        return this.enumValues;
    }
    
    public void setEnumValues(final ArrayList<String> enumValues) {
        this.enumValues = enumValues;
    }
    
    public static class Builder
    {
        private String name;
        private Module module;
        private final SettingType type;
        private boolean booleanValue;
        private int integerValue;
        private int minIntegerValue;
        private int maxIntegerValue;
        private String enumValue;
        private ArrayList<String> enumValues;
        
        public Builder(final SettingType type) {
            this.type = type;
        }
        
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder setModule(final Module module) {
            this.module = module;
            return this;
        }
        
        public Builder setBooleanValue(final boolean booleanValue) {
            this.booleanValue = booleanValue;
            return this;
        }
        
        public Builder setIntegerValue(final int integerValue) {
            this.integerValue = integerValue;
            return this;
        }
        
        public Builder setMinIntegerValue(final int minIntegerValue) {
            this.minIntegerValue = minIntegerValue;
            return this;
        }
        
        public Builder setMaxIntegerValue(final int maxIntegerValue) {
            this.maxIntegerValue = maxIntegerValue;
            return this;
        }
        
        public Builder setEnumValue(final String enumValue) {
            this.enumValue = enumValue;
            return this;
        }
        
        public Builder setEnumValues(final ArrayList<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }
        
        public Setting build() {
            switch (this.type) {
                case BOOLEAN: {
                    return new Setting(this.name, this.module, this.booleanValue, null);
                }
                case INTEGER: {
                    return new Setting(this.name, this.module, this.integerValue, this.minIntegerValue, this.maxIntegerValue);
                }
                case ENUM: {
                    return new Setting(this.name, this.module, this.enumValue, this.enumValues, null);
                }
                default: {
                    return null;
                }
            }
        }
    }
}
