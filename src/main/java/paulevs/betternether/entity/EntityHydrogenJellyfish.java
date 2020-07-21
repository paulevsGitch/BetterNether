package paulevs.betternether.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import paulevs.betternether.registry.SoundsRegistry;

public class EntityHydrogenJellyfish extends AnimalEntity implements Flutterer
{
	private static final TrackedData<Float> SCALE = DataTracker.registerData(EntityHydrogenJellyfish.class, TrackedDataHandlerRegistry.FLOAT);
	
	private Vec3d preVelocity;
	private Vec3d newVelocity = new Vec3d(0, 0, 0);
	private int timer;
	private int timeOut;
	private float prewYaw;
	private float nextYaw;
	
	public EntityHydrogenJellyfish(EntityType<? extends EntityHydrogenJellyfish> type, World world)
	{
		super(type, world);
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(SCALE, 0.5F + random.nextFloat());
	}
	
	public static DefaultAttributeContainer getAttributeContainer()
	{
		return MobEntity
				.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 0.5)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.05)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20.0)
				.build();
	}
	
	@Override
	protected boolean hasWings()
	{
		return true;
	}
	
	@Override
	protected void swimUpward(Tag<Fluid> fluid)
	{
		this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
	}
	
	@Override
	public boolean canClimb()
	{
		return false;
	}
	
	@Override
    public boolean hasNoGravity()
	{
        return true;
    }

	@Override
	public void writeCustomDataToTag(CompoundTag tag)
	{
		super.writeCustomDataToTag(tag);

		tag.putFloat("Scale", getScale());
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);

		if (tag.contains("Scale"))
		{
			this.dataTracker.set(SCALE, tag.getFloat("Scale"));
		}

		this.calculateDimensions();
	}

	public float getScale()
	{
		return this.dataTracker.get(SCALE);
	}

	public EntityDimensions getDimensions(EntityPose pose)
	{
		return super.getDimensions(pose).scaled(this.getScale());
	}
	
	@Override
	public void onPlayerCollision(PlayerEntity player)
	{
		player.damage(DamageSource.GENERIC, 3);
	}

	@Override
	public void calculateDimensions()
	{
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		super.calculateDimensions();
		this.setPos(x, y, z);
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data)
	{
		if (SCALE.equals(data))
		{
			this.calculateDimensions();
		}
	}
	
	@Override
	protected void mobTick()
	{
		timer ++;
		if (timer > timeOut)
		{
			prewYaw = this.yaw;
			nextYaw = random.nextFloat() * 360;
			
			double rads = Math.toRadians(nextYaw + 90);
			
			double vx = Math.cos(rads) * this.flyingSpeed;
			double vz = Math.sin(rads) * this.flyingSpeed;
			
			BlockPos bp = getBlockPos();
			double vy = random.nextDouble() * this.flyingSpeed * 0.75;
			if (world.getBlockState(bp).isAir() &&
				world.getBlockState(bp.down(2)).isAir() &&
				world.getBlockState(bp.down(3)).isAir() &&
				world.getBlockState(bp.down(4)).isAir())
			{
				vy = -vy;
			}
			
			preVelocity = newVelocity;
			newVelocity = new Vec3d(vx, vy, vz);
			timer = 0;
			timeOut = random.nextInt(300) + 120;
		}
		if (timer <= 120)
		{
			if (this.yaw != nextYaw)
			{
				float delta = timer / 120F;
				this.yaw = lerpAngleDegrees(delta, prewYaw, nextYaw);
				this.setVelocity(
						MathHelper.lerp(delta, preVelocity.x, newVelocity.x),
						MathHelper.lerp(delta, preVelocity.y, newVelocity.y),
						MathHelper.lerp(delta, preVelocity.z, newVelocity.z)
						);
			}
		}
		else
		{
			this.setVelocity(newVelocity);
		}
	}
	
	public static float lerpAngleDegrees(float delta, float first, float second)
	{
		return first + delta * MathHelper.wrapDegrees(second - first);
	}
	
	@Override
	public int getLimitPerChunk()
	{
		return 1;
	}
	
	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		if (world.isClient)
		{
			float scale = getScale() * 3;
			for (int i = 0; i < 20; i++)
				this.world.addParticle(ParticleTypes.EXPLOSION,
						getX() + random.nextGaussian() * scale,
						getEyeY() + random.nextGaussian() * scale,
						getZ() + random.nextGaussian() * scale,
						0, 0, 0);
		}
		else
		{
			Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
			this.world.createExplosion(this, getX(), getEyeY(), getZ(), 7 * getScale(), destructionType);
		}
	}
	
	@Override
	public SoundEvent getAmbientSound()
	{
		return SoundsRegistry.MOB_JELLYFISH;
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.1F;
	}

	@Override
	public PassiveEntity createChild(PassiveEntity mate)
	{
		return null;
	}
	
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier)
	{
		return false;
	}
	
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}
	
	@Override
	public boolean damage(DamageSource source, float amount)
	{
		if (source == DamageSource.WITHER || source instanceof EntityDamageSource)
		{
			return super.damage(source, amount);
		}
		return false;
	}
	
	public static boolean canSpawn(EntityType<? extends EntityHydrogenJellyfish> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random)
	{
		Box box = new Box(pos);
		box.expand(64, 256, 64);
		List<EntityHydrogenJellyfish> list = world.getEntities(EntityHydrogenJellyfish.class, box, (entity) -> { return true; });
		return list.size() < 4;
	}
}
