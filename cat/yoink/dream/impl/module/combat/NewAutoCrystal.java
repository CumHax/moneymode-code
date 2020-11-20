//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import cat.yoink.dream.impl.event.PacketReceiveEvent;
import cat.yoink.dream.mixin.mixins.accessor.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import cat.yoink.dream.impl.event.PacketSendEvent;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.entity.EntityLivingBase;
import cat.yoink.dream.api.util.font.FontUtil;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import cat.yoink.dream.api.util.RenderUtil;
import cat.yoink.dream.impl.event.RenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Collection;
import java.util.Arrays;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.setting.Setting;
import java.util.ArrayList;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import cat.yoink.dream.api.module.Module;

public class NewAutoCrystal extends Module
{
    private BlockPos render;
    private Entity renderEnt;
    private boolean switchCooldown;
    private boolean isAttacking;
    private boolean isPlacing;
    private boolean isBreaking;
    private int oldSlot;
    private int newSlot;
    private int waitCounter;
    EnumFacing f;
    private static boolean togglePitch;
    private final ArrayList<BlockPos> PlacedCrystals;
    public boolean isActive;
    private long breakSystemTime;
    private final Setting mode;
    private final Setting explode;
    private final Setting antiWeakness;
    private final Setting place;
    private final Setting raytrace;
    private final Setting rotate;
    private final Setting spoofRotation;
    private final Setting chat;
    private final Setting showDamage;
    private final Setting cacheBreak;
    private final Setting singlePlace;
    private final Setting antiSuicide;
    private final Setting autoSwitch;
    private final Setting endCrystalMode;
    private final Setting placeDelay;
    private final Setting antiSuicideValue;
    private final Setting facePlace;
    private final Setting attackSpeed;
    private final Setting maxSelfDmg;
    private final Setting minBreakDmg;
    private final Setting enemyRange;
    private final Setting walls;
    private final Setting minDmg;
    public final Setting range;
    private final Setting placeRange;
    private final Setting offHandBreak;
    private final Setting singleTick;
    private final Setting cancelCrystal;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting alpha;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    
    public NewAutoCrystal(final String name, final String description, final Category category) {
        super(name, description, category);
        this.switchCooldown = false;
        this.isAttacking = false;
        this.isPlacing = false;
        this.isBreaking = false;
        this.oldSlot = -1;
        this.PlacedCrystals = new ArrayList<BlockPos>();
        this.isActive = false;
        this.mode = new Setting.Builder(SettingType.ENUM).setName("Mode").setModule(this).setEnumValue("Always").setEnumValues(new ArrayList<String>(Arrays.asList("Always", "Smart", "Only Own"))).build();
        this.explode = new Setting.Builder(SettingType.BOOLEAN).setName("Explode").setModule(this).setBooleanValue(true).build();
        this.antiWeakness = new Setting.Builder(SettingType.BOOLEAN).setName("Anti Weakness").setModule(this).setBooleanValue(false).build();
        this.place = new Setting.Builder(SettingType.BOOLEAN).setName("Place").setModule(this).setBooleanValue(true).build();
        this.raytrace = new Setting.Builder(SettingType.BOOLEAN).setName("Raytrace").setModule(this).setBooleanValue(false).build();
        this.rotate = new Setting.Builder(SettingType.BOOLEAN).setName("Rotate").setModule(this).setBooleanValue(true).build();
        this.spoofRotation = new Setting.Builder(SettingType.BOOLEAN).setName("Spoof Rotate").setModule(this).setBooleanValue(false).build();
        this.chat = new Setting.Builder(SettingType.BOOLEAN).setName("Toggle MSG").setModule(this).setBooleanValue(true).build();
        this.showDamage = new Setting.Builder(SettingType.BOOLEAN).setName("Show DMG").setModule(this).setBooleanValue(true).build();
        this.cacheBreak = new Setting.Builder(SettingType.BOOLEAN).setName("CacheBreak").setModule(this).setBooleanValue(false).build();
        this.singlePlace = new Setting.Builder(SettingType.BOOLEAN).setName("Single Place").setModule(this).setBooleanValue(true).build();
        this.antiSuicide = new Setting.Builder(SettingType.BOOLEAN).setName("No Suicide").setModule(this).setBooleanValue(true).build();
        this.autoSwitch = new Setting.Builder(SettingType.BOOLEAN).setName("AutoSwitch").setModule(this).setBooleanValue(false).build();
        this.endCrystalMode = new Setting.Builder(SettingType.BOOLEAN).setName("1.13").setModule(this).setBooleanValue(false).build();
        this.placeDelay = new Setting.Builder(SettingType.INTEGER).setName("Place Delay").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(20).build();
        this.antiSuicideValue = new Setting.Builder(SettingType.INTEGER).setName("Stop Health").setModule(this).setIntegerValue(10).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.facePlace = new Setting.Builder(SettingType.INTEGER).setName("Faceplace").setModule(this).setIntegerValue(8).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.attackSpeed = new Setting.Builder(SettingType.INTEGER).setName("Attack Speed").setModule(this).setIntegerValue(12).setMinIntegerValue(1).setMaxIntegerValue(20).build();
        this.maxSelfDmg = new Setting.Builder(SettingType.INTEGER).setName("Max Self DMG").setModule(this).setIntegerValue(8).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.minBreakDmg = new Setting.Builder(SettingType.INTEGER).setName("Min Break DMG").setModule(this).setIntegerValue(6).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.enemyRange = new Setting.Builder(SettingType.INTEGER).setName("Enemy Range").setModule(this).setIntegerValue(6).setMinIntegerValue(1).setMaxIntegerValue(12).build();
        this.walls = new Setting.Builder(SettingType.INTEGER).setName("Walls Range").setModule(this).setIntegerValue(4).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.minDmg = new Setting.Builder(SettingType.INTEGER).setName("Min DMG").setModule(this).setIntegerValue(6).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.range = new Setting.Builder(SettingType.INTEGER).setName("Hit Range").setModule(this).setIntegerValue(5).setMinIntegerValue(1).setMaxIntegerValue(12).build();
        this.placeRange = new Setting.Builder(SettingType.INTEGER).setName("Place Range").setModule(this).setIntegerValue(5).setMinIntegerValue(1).setMaxIntegerValue(12).build();
        this.offHandBreak = new Setting.Builder(SettingType.BOOLEAN).setName("Offhand Break").setModule(this).setBooleanValue(false).build();
        this.singleTick = new Setting.Builder(SettingType.BOOLEAN).setName("Single Tick").setModule(this).setBooleanValue(false).build();
        this.cancelCrystal = new Setting.Builder(SettingType.BOOLEAN).setName("Cancel Crystal").setModule(this).setBooleanValue(false).build();
        this.red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.alpha = new Setting.Builder(SettingType.INTEGER).setName("Alpha").setModule(this).setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();
        this.addSetting(this.mode);
        this.addSetting(this.place);
        this.addSetting(this.explode);
        this.addSetting(this.rotate);
        this.addSetting(this.spoofRotation);
        this.addSetting(this.raytrace);
        this.addSetting(this.antiWeakness);
        this.addSetting(this.chat);
        this.addSetting(this.showDamage);
        this.addSetting(this.cacheBreak);
        this.addSetting(this.antiSuicide);
        this.addSetting(this.singlePlace);
        this.addSetting(this.autoSwitch);
        this.addSetting(this.endCrystalMode);
        this.addSetting(this.placeDelay);
        this.addSetting(this.antiSuicideValue);
        this.addSetting(this.facePlace);
        this.addSetting(this.attackSpeed);
        this.addSetting(this.maxSelfDmg);
        this.addSetting(this.minBreakDmg);
        this.addSetting(this.antiWeakness);
        this.addSetting(this.enemyRange);
        this.addSetting(this.walls);
        this.addSetting(this.minDmg);
        this.addSetting(this.singleTick);
        this.addSetting(this.range);
        this.addSetting(this.placeRange);
        this.addSetting(this.offHandBreak);
        this.addSetting(this.red);
        this.addSetting(this.green);
        this.addSetting(this.blue);
        this.addSetting(this.alpha);
        this.addSetting(this.cancelCrystal);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        this.isActive = false;
        this.isBreaking = false;
        this.isPlacing = false;
        final BlockPos hitBlock = null;
        if (this.mc.player == null || this.mc.player.isDead) {
            return;
        }
        final EntityEnderCrystal crystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(e -> this.mc.player.getDistance(e) <= this.range.getIntegerValue()).filter(e -> this.crystalCheck(e)).map(entity -> entity).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        if (this.explode.getBooleanValue() && crystal != null) {
            if (this.antiSuicide.getBooleanValue() && this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < this.antiSuicideValue.getIntegerValue()) {
                return;
            }
            if (!this.mc.player.canEntityBeSeen((Entity)crystal) && this.mc.player.getDistance((Entity)crystal) > this.walls.getIntegerValue()) {
                return;
            }
            if (this.antiWeakness.getBooleanValue() && this.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                if (!this.isAttacking) {
                    this.oldSlot = this.mc.player.inventory.currentItem;
                    this.isAttacking = true;
                }
                this.newSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
                    if (stack != ItemStack.EMPTY) {
                        if (stack.getItem() instanceof ItemSword) {
                            this.newSlot = i;
                            break;
                        }
                        if (stack.getItem() instanceof ItemTool) {
                            this.newSlot = i;
                            break;
                        }
                    }
                }
                if (this.newSlot != -1) {
                    this.mc.player.inventory.currentItem = this.newSlot;
                    this.switchCooldown = true;
                }
            }
            if (System.nanoTime() / 1000000L - this.breakSystemTime >= 420 - this.attackSpeed.getIntegerValue() * 20) {
                this.isActive = true;
                this.isBreaking = true;
                if (this.rotate.getBooleanValue()) {
                    this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)this.mc.player);
                }
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)crystal);
            }
            else if (this.offHandBreak.getBooleanValue() && !this.mc.player.getHeldItemOffhand().isEmpty()) {
                this.mc.player.swingArm(EnumHand.OFF_HAND);
                if (this.cancelCrystal.getBooleanValue()) {
                    crystal.setDead();
                    this.mc.world.removeAllEntities();
                    this.mc.world.getLoadedEntityList();
                }
            }
            else {
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (this.cancelCrystal.getBooleanValue()) {
                    crystal.setDead();
                    this.mc.world.removeAllEntities();
                    this.mc.world.getLoadedEntityList();
                }
            }
            if (this.cancelCrystal.getBooleanValue()) {
                crystal.setDead();
                this.mc.world.removeAllEntities();
                this.mc.world.getLoadedEntityList();
            }
            this.breakSystemTime = System.nanoTime() / 1000000L;
            this.isActive = false;
            this.isBreaking = false;
        }
        if (!this.singlePlace.getBooleanValue()) {
            return;
        }
        resetRotation();
        if (this.oldSlot != -1) {
            this.mc.player.inventory.currentItem = this.oldSlot;
            this.oldSlot = -1;
        }
        this.isAttacking = false;
        this.isActive = false;
        this.isBreaking = false;
        int crystalSlot = (this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? this.mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (this.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        }
        else if (crystalSlot == -1) {
            return;
        }
        final List<BlockPos> blocks = this.findCrystalBlocks();
        final List<Entity> entities = new ArrayList<Entity>();
        BlockPos q = null;
        double damage = 0.5;
        final Iterator var9 = entities.iterator();
        while (true) {
            if (!var9.hasNext()) {
                if (damage == 0.5) {
                    this.render = null;
                    this.renderEnt = null;
                    resetRotation();
                    return;
                }
                this.render = q;
                if (this.place.getBooleanValue()) {
                    if (this.antiSuicide.getBooleanValue() && this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < this.antiSuicideValue.getIntegerValue()) {
                        return;
                    }
                    if (!offhand && this.mc.player.inventory.currentItem != crystalSlot) {
                        if (this.autoSwitch.getBooleanValue()) {
                            this.mc.player.inventory.currentItem = crystalSlot;
                            resetRotation();
                            this.switchCooldown = true;
                        }
                        return;
                    }
                    if (this.rotate.getBooleanValue()) {
                        this.lookAtPacket(q.getX() + 0.5, q.getY() - 0.5, q.getZ() + 0.5, (EntityPlayer)this.mc.player);
                    }
                    final RayTraceResult result = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d(q.getX() + 0.5, q.getY() - 0.5, q.getZ() + 0.5));
                    if (this.raytrace.getBooleanValue()) {
                        if (result == null || result.sideHit == null) {
                            q = null;
                            this.f = null;
                            this.render = null;
                            resetRotation();
                            this.isActive = false;
                            this.isPlacing = false;
                            return;
                        }
                        this.f = result.sideHit;
                    }
                    if (this.switchCooldown) {
                        this.switchCooldown = false;
                        return;
                    }
                    this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, q, this.f, new Vec3d(0.0, 0.0, 0.0), EnumHand.MAIN_HAND);
                    if (q != null && this.mc.player != null) {
                        this.isActive = true;
                        this.isPlacing = true;
                        if (this.raytrace.getBooleanValue() && this.f != null) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(q, this.f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        }
                        else if (q.getY() == 255) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(q, EnumFacing.DOWN, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        }
                        else {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(q, this.f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        }
                        final EntityEnderCrystal cry = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(ent -> ent instanceof EntityEnderCrystal).filter(e -> this.mc.player.getDistance(e) <= this.range.getIntegerValue()).filter(e -> this.crystalCheck(e)).map(ent -> ent).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
                        if (this.singleTick.getBooleanValue()) {
                            this.lookAtPacket(q.getX(), q.getY(), q.getZ(), (EntityPlayer)this.mc.player);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)cry);
                        }
                        this.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        this.PlacedCrystals.add(q);
                    }
                    if (NewAutoCrystal.isSpoofingAngles) {
                        if (NewAutoCrystal.togglePitch) {
                            final EntityPlayerSP var10 = this.mc.player;
                            var10.rotationPitch += (float)4.0E-4;
                            NewAutoCrystal.togglePitch = false;
                        }
                        else {
                            final EntityPlayerSP var10 = this.mc.player;
                            var10.rotationPitch -= (float)4.0E-4;
                            NewAutoCrystal.togglePitch = true;
                        }
                    }
                    return;
                }
            }
            final EntityPlayer entity2 = var9.next();
            if (entity2 == this.mc.player && entity2.getHealth() <= 0.0f) {
                for (final BlockPos blockPos : blocks) {
                    final double b = entity2.getDistanceSq(blockPos);
                    final double x = blockPos.getX() + 0.0;
                    final double y = blockPos.getY() + 1.0;
                    final double z = blockPos.getZ() + 0.0;
                    if (entity2.getDistanceSq(x, y, z) < this.enemyRange.getIntegerValue() * this.enemyRange.getIntegerValue()) {
                        final double d = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)entity2);
                        if (d <= damage) {
                            continue;
                        }
                        final double targetDamage = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)entity2);
                        final float targetHealth = entity2.getHealth() + entity2.getAbsorptionAmount();
                        if (targetDamage < this.minDmg.getIntegerValue() && targetHealth > this.facePlace.getIntegerValue()) {
                            continue;
                        }
                        final double self = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)this.mc.player);
                        if (self >= this.maxSelfDmg.getIntegerValue() || self >= this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                            continue;
                        }
                        damage = d;
                        q = blockPos;
                        this.renderEnt = (Entity)entity2;
                    }
                }
            }
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.render != null) {
            RenderUtil.prepare(7);
            RenderUtil.release();
            RenderUtil.prepare(7);
            RenderUtil.release();
        }
        if (this.showDamage.getBooleanValue() && this.render != null && this.renderEnt != null) {
            GlStateManager.pushMatrix();
            RenderUtil.glBillboardDistanceScaled(this.render.getX() + 0.5f, this.render.getY() + 0.5f, this.render.getZ() + 0.5f, (EntityPlayer)this.mc.player, 1.0f);
            final double d = calculateDamage(this.render.getX() + 0.5, this.render.getY() + 1, this.render.getZ() + 0.5, this.renderEnt);
            final String damageText = ((Math.floor(d) == d) ? Integer.valueOf((int)d) : String.format("%.1f", d)) + "";
            GlStateManager.disableDepth();
            GlStateManager.translate(-(this.mc.fontRenderer.getStringWidth(damageText) / 2.0), 0.0, 0.0);
            FontUtil.drawStringWithShadow2(false, damageText, 0, 0, new Color(255, 255, 255).getRGB());
            GlStateManager.popMatrix();
        }
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private boolean crystalCheck(final Entity crystal) {
        if (!(crystal instanceof EntityEnderCrystal)) {
            return false;
        }
        if (this.mode.getEnumValue().equalsIgnoreCase("Always")) {
            return true;
        }
        if (this.mode.getEnumValue().equalsIgnoreCase("Only Own")) {
            for (final BlockPos pos : new ArrayList<BlockPos>(this.PlacedCrystals)) {
                if (pos != null && pos.getDistance((int)crystal.posX, (int)crystal.posY, (int)crystal.posZ) <= this.range.getIntegerValue()) {
                    return true;
                }
            }
        }
        if (!this.mode.getEnumValue().equalsIgnoreCase("Smart")) {
            return false;
        }
        final EntityLivingBase target = (EntityLivingBase)((this.renderEnt != null) ? this.renderEnt : this.GetNearTarget(crystal));
        if (target == null) {
            return false;
        }
        final float targetDmg = calculateDamage(crystal.posX + 0.5, crystal.posY + 1.0, crystal.posZ + 0.5, (Entity)target);
        return targetDmg >= this.minBreakDmg.getIntegerValue() || (targetDmg > this.minBreakDmg.getIntegerValue() && target.getHealth() > this.facePlace.getIntegerValue());
    }
    
    private boolean validTarget(final Entity entity) {
        return entity != null && entity instanceof EntityLivingBase && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0f && entity instanceof EntityPlayer && entity != this.mc.player;
    }
    
    private EntityLivingBase GetNearTarget(final Entity distanceTarget) {
        return (EntityLivingBase)this.mc.world.loadedEntityList.stream().filter(entity -> this.validTarget(entity)).map(entity -> entity).min(Comparator.comparing(entity -> distanceTarget.getDistance(entity))).orElse(null);
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        if (!this.endCrystalMode.getBooleanValue()) {
            return (this.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || this.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && this.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && this.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
        return (this.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || this.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Minecraft.getMinecraft().player.posX), Math.floor(Minecraft.getMinecraft().player.posY), Math.floor(Minecraft.getMinecraft().player.posZ));
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)this.getSphere(getPlayerPos(), (float)this.placeRange.getIntegerValue(), this.placeRange.getIntegerValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)Minecraft.getMinecraft().world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private static float getDamageMultiplied(final float damage) {
        final int diff = Minecraft.getMinecraft().world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        NewAutoCrystal.yaw = yaw1;
        NewAutoCrystal.pitch = pitch1;
        NewAutoCrystal.isSpoofingAngles = true;
    }
    
    private static void resetRotation() {
        if (NewAutoCrystal.isSpoofingAngles) {
            NewAutoCrystal.yaw = Minecraft.getMinecraft().player.rotationYaw;
            NewAutoCrystal.pitch = Minecraft.getMinecraft().player.rotationPitch;
            NewAutoCrystal.isSpoofingAngles = false;
        }
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketSendEvent event) {
        final Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && this.spoofRotation.getBooleanValue() && NewAutoCrystal.isSpoofingAngles) {
            ((ICPacketPlayer)packet).setYaw((float)NewAutoCrystal.yaw);
            ((ICPacketPlayer)packet).setPitch((float)NewAutoCrystal.pitch);
        }
    }
    
    @SubscribeEvent
    public void onReadPacket(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                        e.setDead();
                    }
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.PlacedCrystals.clear();
        this.isActive = false;
        this.isPlacing = false;
        this.isBreaking = false;
        if (this.chat.getBooleanValue() && this.mc.player != null) {
            final String text = "[" + ChatFormatting.GREEN + "moneymod+2" + ChatFormatting.RESET + "]" + ChatFormatting.GREEN + " NewAutoCrystal enabled!";
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(text), 5936);
        }
    }
    
    @Override
    public void onDisable() {
        this.render = null;
        this.renderEnt = null;
        resetRotation();
        this.PlacedCrystals.clear();
        this.isActive = false;
        this.isPlacing = false;
        this.isBreaking = false;
        if (this.chat.getBooleanValue()) {
            final String text = "[" + ChatFormatting.GREEN + "moneymod+2" + ChatFormatting.RESET + "]" + ChatFormatting.RED + " NewAutoCrystal disabled!";
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(text), 5936);
        }
    }
    
    static {
        NewAutoCrystal.togglePitch = false;
    }
}
