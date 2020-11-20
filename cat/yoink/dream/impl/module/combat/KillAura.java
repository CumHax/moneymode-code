//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import cat.yoink.dream.impl.event.PacketSendEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import cat.yoink.dream.api.module.ModuleManager;
import net.minecraft.item.ItemSword;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class KillAura extends Module
{
    private final Setting range;
    private final Setting swordOnly;
    private final Setting criticals;
    private final Setting caCheck;
    private final Setting swingOffhand;
    private boolean isAttacking;
    
    public KillAura(final String name, final String description, final Category category) {
        super(name, description, category);
        this.range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this).setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.swordOnly = new Setting.Builder(SettingType.BOOLEAN).setName("Sword Only").setModule(this).setBooleanValue(true).build();
        this.criticals = new Setting.Builder(SettingType.BOOLEAN).setName("Criticals").setModule(this).setBooleanValue(true).build();
        this.caCheck = new Setting.Builder(SettingType.BOOLEAN).setName("AC Check").setModule(this).setBooleanValue(false).build();
        this.swingOffhand = new Setting.Builder(SettingType.BOOLEAN).setName("Swing Offhand").setModule(this).setBooleanValue(false).build();
        this.isAttacking = false;
        this.addSetting(this.range);
        this.addSetting(this.swordOnly);
        this.addSetting(this.criticals);
        this.addSetting(this.caCheck);
        this.addSetting(this.swingOffhand);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.player.isDead) {
            return;
        }
        final List<Entity> targets = (List<Entity>)this.mc.world.loadedEntityList.stream().filter(entity -> entity != this.mc.player).filter(entity -> this.mc.player.getDistance(entity) <= this.range.getIntegerValue()).filter(entity -> !entity.isDead).filter(entity -> entity instanceof EntityPlayer).filter(entity -> entity.getHealth() > 0.0f).sorted(Comparator.comparing(e -> this.mc.player.getDistance(e))).collect(Collectors.toList());
        targets.forEach(target -> {
            if (!this.swordOnly.getBooleanValue() || this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                if (!this.caCheck.getBooleanValue() || !((AutoCrystal)ModuleManager.getModule("AutoCrystal")).isToggled()) {
                    this.attack(target);
                }
            }
        });
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity && this.criticals.getBooleanValue() && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && this.mc.player.onGround && this.isAttacking) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.10000000149011612, this.mc.player.posZ, false));
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
        }
    }
    
    public void attack(final Entity e) {
        if (this.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            this.isAttacking = true;
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, e);
            if (this.swingOffhand.getBooleanValue()) {
                this.mc.player.swingArm(EnumHand.OFF_HAND);
            }
            else {
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            this.isAttacking = false;
        }
    }
}
