// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.api.util;

public class TimerUtil
{
    private long current;
    
    public TimerUtil() {
        this.current = System.currentTimeMillis();
    }
    
    public void reset() {
        this.current = System.currentTimeMillis();
    }
    
    public boolean hasReached(final long var1) {
        return System.currentTimeMillis() - this.current >= var1;
    }
    
    public boolean sleep(final long var1) {
        if (this.time() >= var1) {
            this.reset();
            return true;
        }
        return false;
    }
    
    public long getTimePassed() {
        return System.currentTimeMillis() - this.current;
    }
    
    public long time() {
        return System.currentTimeMillis() - this.current;
    }
    
    public boolean hasReached(final long var1, final boolean var3) {
        if (var3) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= var1;
    }
}
