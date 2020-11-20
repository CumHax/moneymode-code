//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import cat.yoink.dream.impl.event.MoveEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cat.yoink.dream.impl.event.WalkEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class Strafe extends Module
{
    private final Setting jump;
    private final Setting limiter;
    private final Setting specialMoveSpeed;
    private final Setting potionSpeed;
    private final Setting potionSpeed2;
    private final Setting acceleration;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private int cooldownHops;
    
    public Strafe(final String name, final String description, final Category category) {
        super(name, description, category);
        this.jump = new Setting.Builder(SettingType.BOOLEAN).setName("Auto Jump").setModule(this).setBooleanValue(true).build();
        this.limiter = new Setting.Builder(SettingType.BOOLEAN).setName("Set Ground").setModule(this).setBooleanValue(true).build();
        this.specialMoveSpeed = new Setting.Builder(SettingType.INTEGER).setName("Speed").setModule(this).setIntegerValue(100).setMinIntegerValue(0).setMaxIntegerValue(150).build();
        this.potionSpeed = new Setting.Builder(SettingType.INTEGER).setName("Potion Speed").setModule(this).setIntegerValue(130).setMinIntegerValue(0).setMaxIntegerValue(150).build();
        this.potionSpeed2 = new Setting.Builder(SettingType.INTEGER).setName("Potion Speed 2").setModule(this).setIntegerValue(125).setMinIntegerValue(0).setMaxIntegerValue(150).build();
        this.acceleration = new Setting.Builder(SettingType.INTEGER).setName("Accel").setModule(this).setIntegerValue(2149).setMinIntegerValue(1000).setMaxIntegerValue(2500).build();
        this.stage = 1;
        this.cooldownHops = 0;
        this.addSetting(this.jump);
        this.addSetting(this.limiter);
        this.addSetting(this.specialMoveSpeed);
        this.addSetting(this.potionSpeed);
        this.addSetting(this.potionSpeed2);
        this.addSetting(this.acceleration);
    }
    
    @Override
    public void onEnable() {
        this.moveSpeed = this.getBaseMoveSpeed();
    }
    
    @Override
    public void onDisable() {
        this.moveSpeed = 0.0;
        this.stage = 2;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final WalkEvent event) {
        this.lastDist = Math.sqrt((this.mc.player.posX - this.mc.player.prevPosX) * (this.mc.player.posX - this.mc.player.prevPosX) + (this.mc.player.posZ - this.mc.player.prevPosZ) * (this.mc.player.posZ - this.mc.player.prevPosZ));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (!this.limiter.getBooleanValue() && this.mc.player.onGround) {
            this.stage = 2;
        }
        switch (this.stage) {
            case 0: {
                ++this.stage;
                this.lastDist = 0.0;
                break;
            }
            case 2: {
                double motionY = 0.40123128;
                if ((this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) && this.mc.player.onGround) {
                    if (this.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        motionY += (this.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                    }
                    event.setY(this.mc.player.motionY = motionY);
                    this.moveSpeed *= 2.149;
                    break;
                }
                break;
            }
            case 3: {
                this.moveSpeed = this.lastDist - 0.76 * (this.lastDist - this.getBaseMoveSpeed());
                break;
            }
            default: {
                if (this.mc.player.collidedVertically && this.stage > 0) {
                    this.stage = ((this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                break;
            }
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        double forward = this.mc.player.movementInput.moveForward;
        double strafe = this.mc.player.movementInput.moveStrafe;
        final double yaw = this.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        }
        else if (forward != 0.0 && strafe != 0.0) {
            forward *= Math.sin(0.7853981633974483);
            strafe *= Math.cos(0.7853981633974483);
        }
        event.setX((forward * this.moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw))) * 0.99);
        event.setZ((forward * this.moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * this.moveSpeed * -Math.sin(Math.toRadians(yaw))) * 0.99);
        ++this.stage;
    }
    
    public double getBaseMoveSpeed() {
        double baseSpeed = 0.272;
        if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(this.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
}
