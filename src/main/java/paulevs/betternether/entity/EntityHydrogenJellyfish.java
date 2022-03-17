package paulevs.betternether.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.registry.SoundsRegistry;
import ru.bclib.entity.DespawnableAnimal;

import java.util.List;
import java.util.Random;

public class EntityHydrogenJellyfish extends DespawnableAnimal implements FlyingAnimal {
	private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(EntityHydrogenJellyfish.class, EntityDataSerializers.FLOAT);

	private Vec3 preVelocity;
	private Vec3 newVelocity = new Vec3(0, 0, 0);
	private int timer;
	private int timeOut;
	private float prewYaw;
	private float nextYaw;

	public EntityHydrogenJellyfish(EntityType<? extends EntityHydrogenJellyfish> type, Level world) {
		super(type, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SCALE, 0.5F + random.nextFloat());
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob
				.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 0.5)
				.add(Attributes.FLYING_SPEED, 0.05)
				.add(Attributes.MOVEMENT_SPEED, 0.5)
				.add(Attributes.ATTACK_DAMAGE, 20.0);
	}

	@Override
	protected boolean isFlapping() {
		return true;
	}

	@Override
	protected void jumpInLiquid(Tag<Fluid> fluid) {
		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putFloat("Scale", getScale());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("Scale")) {
			this.entityData.set(SCALE, tag.getFloat("Scale"));
		}

		this.refreshDimensions();
	}

	public float getScale() {
		return this.entityData.get(SCALE);
	}

	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(this.getScale());
	}

	@Override
	public void playerTouch(Player player) {
		player.hurt(DamageSource.GENERIC, 3);
	}

	@Override
	public void refreshDimensions() {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		super.refreshDimensions();
		this.setPosRaw(x, y, z);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		if (SCALE.equals(data)) {
			this.refreshDimensions();
		}
	}

	@Override
	protected void customServerAiStep() {
		timer++;
		if (timer > timeOut) {
			prewYaw = this.getYRot();
			nextYaw = random.nextFloat() * 360;

			double rads = Math.toRadians(nextYaw + 90);

			double vx = Math.cos(rads) * this.flyingSpeed;
			double vz = Math.sin(rads) * this.flyingSpeed;

			BlockPos bp = blockPosition();
			double vy = random.nextDouble() * this.flyingSpeed * 0.75;
			if (level.getBlockState(bp).isAir() &&
					level.getBlockState(bp.below(2)).isAir() &&
					level.getBlockState(bp.below(3)).isAir() &&
					level.getBlockState(bp.below(4)).isAir()) {
				vy = -vy;
			}

			preVelocity = newVelocity;
			newVelocity = new Vec3(vx, vy, vz);
			timer = 0;
			timeOut = random.nextInt(300) + 120;
		}
		if (timer <= 120) {
			if (this.getYRot() != nextYaw) {
				float delta = timer / 120F;
				this.setYRot(lerpAngleDegrees(delta, prewYaw, nextYaw));
				this.setDeltaMovement(
						Mth.lerp(delta, preVelocity.x, newVelocity.x),
						Mth.lerp(delta, preVelocity.y, newVelocity.y),
						Mth.lerp(delta, preVelocity.z, newVelocity.z));
			}
		}
		else {
			this.setDeltaMovement(newVelocity);
		}
	}

	public static float lerpAngleDegrees(float delta, float first, float second) {
		return first + delta * Mth.wrapDegrees(second - first);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		if (level.isClientSide) {
			float scale = getScale() * 3;
			for (int i = 0; i < 20; i++)
				this.level.addParticle(ParticleTypes.EXPLOSION,
						getX() + random.nextGaussian() * scale,
						getEyeY() + random.nextGaussian() * scale,
						getZ() + random.nextGaussian() * scale,
						0, 0, 0);
		}
		else {
			if (source!=DamageSource.OUT_OF_WORLD) {
				Explosion.BlockInteraction destructionType = this.level.getGameRules()
																	   .getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
				this.level.explode(this, getX(), getEyeY(), getZ(), 7 * getScale(), destructionType);
			}
		}
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundsRegistry.MOB_JELLYFISH;
	}

	@Override
	protected float getSoundVolume() {
		return 0.1F;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mate) {
		return null;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source == DamageSource.WITHER || source instanceof EntityDamageSource || source == DamageSource.OUT_OF_WORLD) {
			return super.hurt(source, amount);
		}
		return false;
	}

	public static boolean canSpawn(EntityType<? extends EntityHydrogenJellyfish> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
		try {
			AABB box = new AABB(pos).inflate(64, 256, 64);
			List<EntityHydrogenJellyfish> list = world.getEntitiesOfClass(EntityHydrogenJellyfish.class, box, (entity) -> {
				return true;
			});
			return list.size() < 4;
		}
		catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean isFlying() {
		return !this.onGround;
	}
}
