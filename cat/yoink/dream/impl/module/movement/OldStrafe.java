//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.util.math.AxisAlignedBB;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class OldStrafe extends Module
{
    private final Setting jump;
    int waitCounter;
    int forward;
    private static final AxisAlignedBB WATER_WALK_AA;
    
    public OldStrafe(final String name, final String description, final Category category) {
        super(name, description, category);
        this.jump = new Setting.Builder(SettingType.BOOLEAN).setName("AutoJump").setModule(this).setBooleanValue(true).build();
        this.forward = 1;
        this.addSetting(this.jump);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        final boolean boost = Math.abs(this.mc.player.rotationYawHead - this.mc.player.rotationYaw) < 90.0f;
        if (this.mc.player.moveForward != 0.0f) {
            if (!this.mc.player.isSprinting()) {
                this.mc.player.setSprinting(true);
            }
            float yaw = this.mc.player.rotationYaw;
            if (this.mc.player.moveForward > 0.0f) {
                if (this.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += ((this.mc.player.movementInput.moveStrafe > 0.0f) ? -45.0f : 45.0f);
                }
                this.forward = 1;
                this.mc.player.moveForward = 1.0f;
                this.mc.player.moveStrafing = 0.0f;
            }
            else if (this.mc.player.moveForward < 0.0f) {
                if (this.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += ((this.mc.player.movementInput.moveStrafe > 0.0f) ? 45.0f : -45.0f);
                }
                this.forward = -1;
                this.mc.player.moveForward = -1.0f;
                this.mc.player.moveStrafing = 0.0f;
            }
            if (this.mc.player.onGround) {
                this.mc.player.setJumping(false);
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                final float f = (float)Math.toRadians(yaw);
                if (this.jump.getBooleanValue()) {
                    this.mc.player.motionY = 0.405;
                    final EntityPlayerSP player = this.mc.player;
                    player.motionX -= MathHelper.sin(f) * 0.1f * (double)this.forward;
                    final EntityPlayerSP player2 = this.mc.player;
                    player2.motionZ += MathHelper.cos(f) * 0.1f * (double)this.forward;
                }
                else if (this.mc.gameSettings.keyBindJump.isPressed()) {
                    this.mc.player.motionY = 0.405;
                    final EntityPlayerSP player3 = this.mc.player;
                    player3.motionX -= MathHelper.sin(f) * 0.1f * (double)this.forward;
                    final EntityPlayerSP player4 = this.mc.player;
                    player4.motionZ += MathHelper.cos(f) * 0.1f * (double)this.forward;
                }
            }
            else {
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                final double currentSpeed = Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ);
                double speed = boost ? 1.0064 : 1.001;
                if (this.mc.player.motionY < 0.0) {
                    speed = 1.0;
                }
                final double direction = Math.toRadians(yaw);
                this.mc.player.motionX = -Math.sin(direction) * speed * currentSpeed * this.forward;
                this.mc.player.motionZ = Math.cos(direction) * speed * currentSpeed * this.forward;
            }
        }
    }
    
    static {
        WATER_WALK_AA = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.99, 1.0);
    }
}
