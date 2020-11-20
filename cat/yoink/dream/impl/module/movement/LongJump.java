//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.event.entity.living.LivingEvent;
import cat.yoink.dream.impl.event.MoveEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class LongJump extends Module
{
    private final Setting speed;
    private final Setting packet;
    private boolean jumped;
    private boolean boostable;
    
    public LongJump(final String name, final String description, final Category category) {
        super(name, description, category);
        this.speed = new Setting.Builder(SettingType.INTEGER).setName("Speed").setModule(this).setIntegerValue(30).setMinIntegerValue(1).setMaxIntegerValue(100).build();
        this.packet = new Setting.Builder(SettingType.BOOLEAN).setName("Packet").setModule(this).setBooleanValue(false).build();
        this.jumped = false;
        this.boostable = false;
        this.addSetting(this.speed);
        this.addSetting(this.packet);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.jumped) {
            if (this.mc.player.onGround || this.mc.player.capabilities.isFlying) {
                this.jumped = false;
                this.mc.player.motionX = 0.0;
                this.mc.player.motionZ = 0.0;
                if (this.packet.getBooleanValue()) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.onGround));
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX + this.mc.player.motionX, 0.0, this.mc.player.posZ + this.mc.player.motionZ, this.mc.player.onGround));
                }
                return;
            }
            if (this.mc.player.movementInput.moveForward == 0.0f && this.mc.player.movementInput.moveStrafe == 0.0f) {
                return;
            }
            final double yaw = this.getDirection();
            this.mc.player.motionX = -Math.sin(yaw) * ((float)Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ) * (this.boostable ? (this.speed.getIntegerValue() / 10.0f) : 1.0f));
            this.mc.player.motionZ = Math.cos(yaw) * ((float)Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ) * (this.boostable ? (this.speed.getIntegerValue() / 10.0f) : 1.0f));
            this.boostable = false;
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mc.player.movementInput.moveForward == 0.0f && this.mc.player.movementInput.moveStrafe == 0.0f && this.jumped) {
            this.mc.player.motionX = 0.0;
            event.setX(this.mc.player.motionZ = 0.0);
            event.setY(0.0);
        }
    }
    
    @SubscribeEvent
    public void onJump(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() == this.mc.player && (this.mc.player.movementInput.moveForward != 0.0f || this.mc.player.movementInput.moveStrafe != 0.0f)) {
            this.jumped = true;
            this.boostable = true;
        }
    }
    
    private double getDirection() {
        float rotationYaw = this.mc.player.rotationYaw;
        if (this.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (this.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (this.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (this.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (this.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
}
