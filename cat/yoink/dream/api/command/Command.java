// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.command;

public class Command
{
    private String name;
    private String description;
    private String[] alias;
    
    public Command(final String name, final String description, final String[] alias) {
        this.setName(name);
        this.setDescription(description);
        this.setAlias(alias);
    }
    
    public void onCommand(final String arguments) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
}
