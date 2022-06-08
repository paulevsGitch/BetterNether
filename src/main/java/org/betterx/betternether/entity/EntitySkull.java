package org.betterx.betternether.entity;

import org.betterx.betternether.MHelper;
import org.betterx.betternether.registry.SoundsRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

public class EntitySkull extends Monster implements FlyingAnimal {
    private static double particleX;
    private static double particleY;
    private static double particleZ;
    private int attackTick;
    private int dirTickTick;
    private int collideTick;

    public EntitySkull(EntityType<? extends EntitySkull> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.lookControl = new SkullLookControl(this);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.xpReward = 1;
        this.flyingSpeed = 0.5F;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob
                .createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    class SkullLookControl extends LookControl {
        SkullLookControl(Mob entity) {
            super(entity);
        }

        protected boolean resetXRotOnTick() {
            return false;
        }
    }

    @Override
    public void playerTouch(Player player) {
        collideTick++;
        if (collideTick > 25) {
            collideTick = 0;

            boolean shield = player.getUseItem().getItem() instanceof ShieldItem && player.isUsingItem();
            if (shield) {
                player.playSound(
                        SoundEvents.SHIELD_BLOCK,
                        MHelper.randRange(0.8F, 1.2F, random),
                        MHelper.randRange(0.8F, 1.2F, random)
                );
                this.setDeltaMovement(new Vec3(0, 0, 0).subtract(getDeltaMovement()));
            }
            if (player instanceof ServerPlayer) {
                if (shield) {
                    player.getUseItem().hurt(1, random, (ServerPlayer) player);
                    if (player.getUseItem().getDamageValue() > player.getUseItem().getMaxDamage()) {
                        player.broadcastBreakEvent(player.getUsedItemHand());
                        if (player.getUsedItemHand().equals(InteractionHand.MAIN_HAND))
                            player.getInventory().items.clear();
                        else if (player.getUsedItemHand().equals(InteractionHand.OFF_HAND))
                            player.getInventory().offhand.clear();
                        player.stopUsingItem();
                    }
                    return;
                }
                player.hurt(DamageSource.GENERIC, 1);
                if (random.nextInt(16) == 0)
                    player.setSecondsOnFire(3);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (random.nextInt(3) == 0) {
            updateParticlePos();
            this.level.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0, 0);
        }
        if (random.nextInt(3) == 0) {
            updateParticlePos();
            this.level.addParticle(ParticleTypes.DRIPPING_LAVA, particleX, particleY, particleZ, 0, 0, 0);
        }
        if (random.nextInt(3) == 0) {
            updateParticlePos();
            this.level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0, 0, 0);
        }

        if (attackTick > 40 && this.isAlive()) {
            Player target = EntitySkull.this.level.getNearestPlayer(getX(), getY(), getZ(), 20, true);
            if (target != null && this.hasLineOfSight(target)) {
                attackTick = 0;
                Vec3 velocity = target
                        .position()
                        .add(0, target.getBbHeight() * 0.5F, 0)
                        .subtract(EntitySkull.this.position())
                        .normalize()
                        .scale(EntitySkull.this.flyingSpeed);
                setDeltaMovement(velocity);
                this.lookAt(target, 360, 360);
                this.playSound(
                        SoundsRegistry.MOB_SKULL_FLIGHT,
                        MHelper.randRange(0.15F, 0.3F, random),
                        MHelper.randRange(0.9F, 1.5F, random)
                );
            } else if (dirTickTick < 0) {
                dirTickTick = MHelper.randRange(20, 60, random);
                moveRandomDir();
            }
        } else {
            if (dirTickTick < 0) {
                dirTickTick = MHelper.randRange(20, 60, random);
                moveRandomDir();
            }
        }
        attackTick++;
        dirTickTick--;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    private void moveRandomDir() {
        double dx = random.nextDouble() - 0.5;
        double dy = random.nextDouble() - 0.5;
        double dz = random.nextDouble() - 0.5;
        double l = dx * dx + dy * dy + dz * dz;
        if (l == 0)
            l = 1;
        else
            l = (float) Math.sqrt(l);
        l /= this.flyingSpeed;
        dx /= l;
        dy /= l;
        dz /= l;
        setDeltaMovement(dx, dy, dz);
        lookAt(this.position().add(this.getDeltaMovement()));
        this.playSound(
                SoundsRegistry.MOB_SKULL_FLIGHT,
                MHelper.randRange(0.15F, 0.3F, random),
                MHelper.randRange(0.75F, 1.25F, random)
        );
    }

    private void lookAt(Vec3 target) {
        double d = target.x() - this.getX();
        double e = target.z() - this.getZ();
        double g = target.y() - this.getY();

        double h = Math.sqrt(d * d + e * e);
        float i = (float) (Mth.atan2(e, d) * 57.2957763671875D) - 90.0F;
        float j = (float) (-(Mth.atan2(g, h) * 57.2957763671875D));

        this.setXRot(j);
        this.setYRot(i);
    }

    private void updateParticlePos() {
        particleX = random.nextDouble() - 0.5;
        particleY = random.nextDouble() - 0.5;
        particleZ = random.nextDouble() - 0.5;
        double l = particleX * particleX + particleY * particleY + particleZ * particleZ;
        if (l == 0)
            l = 1;
        else
            l = (float) Math.sqrt(l);
        particleX = particleX * 0.5 / l + getX();
        particleY = particleY * 0.5 / l + getY();
        particleZ = particleZ * 0.5 / l + getZ();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getEyeHeight(Pose pose) {
        return this.getDimensions(pose).height * 0.5F;
    }

    @Override
    protected boolean isFlapping() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(
            double heightDifference,
            boolean onGround,
            BlockState landedState,
            BlockPos landedPosition
    ) {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public static boolean canSpawn(
            EntityType<? extends EntitySkull> type,
            LevelAccessor world,
            MobSpawnType spawnReason,
            BlockPos pos,
            RandomSource random
    ) {
        try {
            if (world.getDifficulty() == Difficulty.PEACEFUL || world.getMaxLocalRawBrightness(pos) > 7)
                return false;

            if (pos.getY() >= world.dimensionType().minY()) return false;

            AABB box = new AABB(pos).inflate(256, 256, 256);
            List<EntitySkull> list = world.getEntitiesOfClass(EntitySkull.class, box, (entity) -> {
                return true;
            });
            return list.size() < 4;
        } catch (Exception e) {
            return true;
        }
    }
}