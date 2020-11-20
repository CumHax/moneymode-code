//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.combat;

import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.module.Category;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.module.Module;

public class AutoCrystal extends Module
{
    private final Setting Place;
    private final Setting chat;
    private final Setting AutoSwitch;
    private final Setting AntiWeakness;
    private final Setting Raytrace;
    private final Setting ClientSide;
    private final Setting hitTickDelay;
    private final Setting HitRange;
    private final Setting PlaceRange;
    private final Setting MinDmg;
    BlockPos placingBlock;
    private EntityPlayer target;
    private boolean switchCooldown;
    private boolean isAttacking;
    private int oldSlot;
    private int hitDelayCounter;
    
    public AutoCrystal(final String name, final String description, final Category category) {
        super(name, description, category);
        this.Place = new Setting.Builder(SettingType.BOOLEAN).setName("Place").setModule(this).setBooleanValue(true).build();
        this.chat = new Setting.Builder(SettingType.BOOLEAN).setName("Toggle MSG").setModule(this).setBooleanValue(true).build();
        this.AutoSwitch = new Setting.Builder(SettingType.BOOLEAN).setName("Auto Switch").setModule(this).setBooleanValue(false).build();
        this.AntiWeakness = new Setting.Builder(SettingType.BOOLEAN).setName("Anti Weakness").setModule(this).setBooleanValue(false).build();
        this.Raytrace = new Setting.Builder(SettingType.BOOLEAN).setName("Raytrace").setModule(this).setBooleanValue(false).build();
        this.ClientSide = new Setting.Builder(SettingType.BOOLEAN).setName("Client Side").setModule(this).setBooleanValue(false).build();
        this.hitTickDelay = new Setting.Builder(SettingType.INTEGER).setName("Tick Delay").setModule(this).setIntegerValue(2).setMinIntegerValue(0).setMaxIntegerValue(20).build();
        this.HitRange = new Setting.Builder(SettingType.INTEGER).setName("Hit Range").setModule(this).setIntegerValue(4).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.PlaceRange = new Setting.Builder(SettingType.INTEGER).setName("Place Range").setModule(this).setIntegerValue(4).setMinIntegerValue(0).setMaxIntegerValue(10).build();
        this.MinDmg = new Setting.Builder(SettingType.INTEGER).setName("Min Damage").setModule(this).setIntegerValue(6).setMinIntegerValue(0).setMaxIntegerValue(36).build();
        this.switchCooldown = false;
        this.isAttacking = false;
        this.oldSlot = -1;
        this.addSetting(this.Place);
        this.addSetting(this.AutoSwitch);
        this.addSetting(this.AntiWeakness);
        this.addSetting(this.Raytrace);
        this.addSetting(this.ClientSide);
        this.addSetting(this.hitTickDelay);
        this.addSetting(this.HitRange);
        this.addSetting(this.PlaceRange);
        this.addSetting(this.MinDmg);
        this.addSetting(this.chat);
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
    }
    
    private float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion((World)this.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private float getDamageMultiplied(final float damage) {
        final int diff = this.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
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
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        final EntityEnderCrystal crystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        if (crystal != null && this.mc.player.getDistance((Entity)crystal) <= this.HitRange.getIntegerValue() && this.rayTraceHitCheck(crystal)) {
            if (this.hitDelayCounter >= this.hitTickDelay.getIntegerValue()) {
                this.hitDelayCounter = 0;
                if (this.AntiWeakness.getBooleanValue() && this.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                    if (!this.isAttacking) {
                        this.oldSlot = this.mc.player.inventory.currentItem;
                        this.isAttacking = true;
                    }
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
                        if (stack != ItemStack.EMPTY) {
                            if (stack.getItem() instanceof ItemSword) {
                                newSlot = i;
                                break;
                            }
                            if (stack.getItem() instanceof ItemTool) {
                                newSlot = i;
                                break;
                            }
                        }
                    }
                    if (newSlot != -1) {
                        this.mc.player.inventory.currentItem = newSlot;
                        this.switchCooldown = true;
                    }
                }
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)crystal);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                return;
            }
            ++this.hitDelayCounter;
        }
        else {
            if (this.oldSlot != -1) {
                this.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            this.isAttacking = false;
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
            final List<Entity> entities = (List<Entity>)this.mc.world.playerEntities.stream().sorted((entity1, entity2) -> Float.compare(this.mc.player.getDistance(entity1), this.mc.player.getDistance(entity2))).collect(Collectors.toList());
            final List<BlockPos> blocks = this.findCrystalBlocks();
            BlockPos targetBlock = null;
            double targetBlockDamage = 0.0;
            this.target = null;
            for (final Entity entity3 : entities) {
                if (entity3 == this.mc.player) {
                    continue;
                }
                if (!(entity3 instanceof EntityPlayer)) {
                    continue;
                }
                final EntityPlayer testTarget = (EntityPlayer)entity3;
                if (testTarget.isDead) {
                    continue;
                }
                if (testTarget.getHealth() <= 0.0f) {
                    continue;
                }
                for (final BlockPos blockPos : blocks) {
                    if (testTarget.getDistanceSq(blockPos) >= 169.0) {
                        continue;
                    }
                    final double targetDamage = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)testTarget);
                    final double selfDamage = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)this.mc.player);
                    final float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
                    final float healthSelf = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
                    if (targetDamage < this.MinDmg.getIntegerValue()) {
                        continue;
                    }
                    if (selfDamage >= healthSelf - 0.5) {
                        continue;
                    }
                    if (selfDamage > targetDamage && targetDamage < healthTarget) {
                        continue;
                    }
                    if (targetDamage <= targetBlockDamage) {
                        continue;
                    }
                    targetBlock = blockPos;
                    targetBlockDamage = targetDamage;
                    this.target = testTarget;
                }
                if (this.target != null) {
                    break;
                }
            }
            if (this.target == null) {
                return;
            }
            if (this.Place.getBooleanValue()) {
                if (!offhand && this.mc.player.inventory.currentItem != crystalSlot) {
                    if (this.AutoSwitch.getBooleanValue()) {
                        this.mc.player.inventory.currentItem = crystalSlot;
                        this.switchCooldown = true;
                    }
                    return;
                }
                assert targetBlock != null;
                final RayTraceResult result = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d(targetBlock.getX() + 0.5, targetBlock.getY() - 0.5, targetBlock.getZ() + 0.5));
                EnumFacing f;
                if (result == null || result.sideHit == null) {
                    f = EnumFacing.UP;
                }
                else {
                    f = result.sideHit;
                }
                if (this.switchCooldown) {
                    this.switchCooldown = false;
                    return;
                }
                this.placingBlock = targetBlock;
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetBlock, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
        }
    }
    
    private boolean rayTraceHitCheck(final EntityEnderCrystal crystal) {
        return !this.Raytrace.getBooleanValue() || this.mc.player.canEntityBeSeen((Entity)crystal);
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (this.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || this.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && this.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && this.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)this.getSphere(this.getPlayerPos(), (float)this.PlaceRange.getIntegerValue(), this.PlaceRange.getIntegerValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    @Override
    public void onEnable() {
        this.hitDelayCounter = 0;
    }
    
    @Override
    public void onDisable() {
        this.target = null;
    }
}
