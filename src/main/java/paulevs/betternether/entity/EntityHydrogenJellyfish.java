package paulevs.betternether.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHydrogenJellyfish extends FlyingEntity implements Flutterer
{
	private static final TrackedData<Float> SCALE = DataTracker.registerData(EntityHydrogenJellyfish.class, TrackedDataHandlerRegistry.FLOAT);

	public EntityHydrogenJellyfish(EntityType<? extends EntityHydrogenJellyfish> type, World world)
	{
		super(type, world);
		//this.moveControl = new JellyfishMoveControl(this, 1, true);
		//this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		//this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(SCALE, 0.5F + random.nextFloat());
	}
	
	/*@Override
	protected void initGoals()
	{
		this.goalSelector.add(1, new FlyRandomlyGoal2(this));
	}*/
	
	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributes().register(EntityAttributes.FLYING_SPEED);
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(8.0);
		this.getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(0.5F);
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.5F);
	}
	
	/*@Override
	protected EntityNavigation createNavigation(World world)
	{
		BirdNavigation birdNavigation = new BirdNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos)
			{
				BlockState state = this.world.getBlockState(pos.down());
				boolean valid = !state.isAir() && state.getMaterial() != Material.LAVA;
				if (valid)
				{
					state = this.world.getBlockState(pos);
					valid = state.isAir() || !state.getMaterial().blocksMovement();
					valid = valid && state.getBlock() != BlocksRegister.EGG_PLANT;
					valid = valid && !state.getBlock().getMaterial(state).blocksMovement();
				}
				return valid;
			}

			public void tick()
			{
				super.tick();
			}
		};
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(false);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}*/
	
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
		this.dealDamage(this, player);
	}

	@Override
	public void calculateDimensions()
	{
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		super.calculateDimensions();
		this.setPosition(x, y, z);
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data)
	{
		if (SCALE.equals(data))
		{
			this.calculateDimensions();
		}
	}

	/*static class FlyRandomlyGoal extends Goal
	{
		private final EntityHydrogenJellyfish jellyfish;
		private int timer;
		private int timeStop;
		private float preSpeedX;
		private float preSpeedY;
		private float preSpeedZ;
		private float speedX;
		private float speedY;
		private float speedZ;

		public FlyRandomlyGoal(EntityHydrogenJellyfish jellyfish)
		{
			this.jellyfish = jellyfish;
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart()
		{
			return this.jellyfish.random.nextInt(10) == 0;
		}

		public boolean shouldContinue()
		{
			return timer < timeStop;
		}

		@Override
		public void start()
		{
			BlockPos pos = this.jellyfish.getBlockPos();
			World world = this.jellyfish.world;
			Random random = this.jellyfish.random;
			
			//float angle = this.jellyfish.yaw + (float) Math.toRadians(random.nextFloat() * 20 - 10);
			float dirX = random.nextFloat() * 10;//(float) Math.sin(angle) * 10;
			float dirZ = random.nextFloat() * 10;// (float) Math.cos(angle) * 10;
			float dirY = (world.isAir(pos.down()) && world.isAir(pos.down(2))) ? -random.nextFloat() * 0.5F - 0.5F : random.nextFloat();
			float l = dirX * dirX + dirY * dirY + dirZ * dirZ;
			if (l == 0)
			{
				dirX = 1;
				l = 1;
			}
			l = (float) Math.sqrt(l);
			dirX /= l;
			dirY /= l;
			dirZ /= l;
			float fs = this.jellyfish.flyingSpeed;
			this.preSpeedX = this.speedX;
			this.preSpeedY = this.speedY;
			this.preSpeedY = this.speedY;
			this.speedX = dirX * fs;
			this.speedY = dirY * fs;
			this.speedZ = dirZ * fs;
			
			timer = 0;
			timeStop = random.nextInt(20) + 20;
		}
		
		@Override
		public void tick()
		{
			this.timer ++;
			//this.jellyfish.setVelocity(this.speedX, this.speedY, this.speedZ);
			float delta = MathHelper.clamp(this.timer * 0.1F, 0, 1);
			this.jellyfish.setVelocity(
					MathHelper.lerp(delta, this.preSpeedX, this.speedX),
					MathHelper.lerp(delta, this.preSpeedY, this.speedY),
					MathHelper.lerp(delta, this.preSpeedZ, this.speedZ)
					);
			//this.jellyfish.navigation.startMovingTo(x, y, z, speed)
			super.tick();
		}
	}
	
	static class FlyRandomlyGoal2 extends Goal
	{
		private final EntityHydrogenJellyfish jellyfish;
		
		public FlyRandomlyGoal2(EntityHydrogenJellyfish jellyfish)
		{
			this.jellyfish = jellyfish;
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		@Override
		public boolean canStart()
		{
			return jellyfish.random.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinue()
		{
			return jellyfish.navigation.method_23966();
		}
		
		@Override
		public void start()
		{
			BlockPos pos = this.jellyfish.getBlockPos();
			World world = this.jellyfish.world;
			Random random = this.jellyfish.random;
			
			double x = jellyfish.getX() + random.nextDouble() * 20 - 10;
			double z = jellyfish.getZ() + random.nextDouble() * 20 - 10;
			double y = (world.isAir(pos.down()) && world.isAir(pos.down(2))) ? jellyfish.getY() - 2 : jellyfish.getY() + random.nextDouble() * 4 - 2;
			
			Path path = jellyfish.navigation.findPathTo(x, y, z, 1);
			if (path != null)
				jellyfish.navigation.startMovingAlong(path, 1.0D);
			else
				jellyfish.setVelocity(0, -0.2, 0);
		}
	}

	static class JellyfishMoveControl extends MoveControl
	{
		private final int field_20349;
		private final boolean field_20350;

		public JellyfishMoveControl(MobEntity mobEntity, int i, boolean bl)
		{
			super(mobEntity);
			this.field_20349 = i;
			this.field_20350 = bl;
		}

		//protected float changeAngle(float from, float to, float max)
		//{
		//	return super.changeAngle(from, to, 1);
		//}

		@Override
		public void tick()
		{
			if (this.state == MoveControl.State.MOVE_TO)
			{
				this.state = MoveControl.State.WAIT;
				this.entity.setNoGravity(true);
				double d = this.targetX - this.entity.getX();
				double e = this.targetY - this.entity.getY();
				double f = this.targetZ - this.entity.getZ();
				double g = d * d + e * e + f * f;
				if (g < 2.500000277905201E-7D)
				{
					this.entity.setUpwardSpeed(0.0F);
					this.entity.setForwardSpeed(0.0F);
					return;
				}

				float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F;
				this.entity.yaw = this.changeAngle(this.entity.yaw, h, 2.0F);
				float j;
				if (this.entity.onGround)
				{
					j = (float)(this.speed * this.entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue());
				}
				else
				{
					j = (float)(this.speed * this.entity.getAttributeInstance(EntityAttributes.FLYING_SPEED).getValue());
				}

				this.entity.setMovementSpeed(j);
				double k = (double)MathHelper.sqrt(d * d + f * f);
				float l = (float)(-(MathHelper.atan2(e, k) * 57.2957763671875D));
				this.entity.pitch = this.changeAngle(this.entity.pitch, l, (float)this.field_20349);
				this.entity.setUpwardSpeed(e > 0.0D ? j : -j);
			}
			else
			{
				if (!this.field_20350)
				{
					this.entity.setNoGravity(false);
				}

				this.entity.setUpwardSpeed(0.0F);
				this.entity.setForwardSpeed(0.0F);
			}
		}
	}*/
	
	Vec3d preVelocity;
	Vec3d newVelocity = new Vec3d(0, 0, 0);
	int timer;
	int timeOut;
	
	float prewYaw;
	float nextYaw;

	@Override
	protected void mobTick()
	{
		timer ++;
		if (timer > timeOut)
		{
			prewYaw = this.yaw;
			nextYaw = random.nextFloat() * 360;
			
			double rads = Math.toRadians(nextYaw + 90);
			
			double vx = Math.cos(rads) * 0.1;
			double vz = Math.sin(rads) * 0.1;
			
			preVelocity = newVelocity;
			newVelocity = new Vec3d(vx, 0, vz);
			timer = 0;
			timeOut = random.nextInt(100) + 40;
			System.out.println("Time change!");
			//this.yaw = (float) Math.toDegrees(MathHelper.atan2(newVelocity.z, newVelocity.x) - 90);
			
		}
		if (timer <= 40)
		{
			if (this.yaw != nextYaw)
			{
				float delta = timer / 40F;
				float yaw2 = MathHelper.lerpAngleDegrees(delta, prewYaw, nextYaw);
				this.setYaw(yaw2);
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
		//this.setYaw((float) MathHelper.atan2(newVelocity.x, newVelocity.z));
		//this.yaw += 1;
	}
}
