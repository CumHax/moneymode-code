// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.module;

public enum Category
{
    COMBAT("Combat"), 
    EXPLOIT("Exploit"), 
    RENDER("Visuals"), 
    MOVEMENT("Movement"), 
    MISC("Miscellaneous");
    
    private String name;
    
    private Category(final String name) {
        this.setName(name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
