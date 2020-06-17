package paulevs.betternether.entity;

import java.util.EnumSet;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.SoundsRegistry;

public class EntitySkull extends HostileEntity implements Flutterer
{
	private static double particleX;
	private static double particleY;
	private static double particleZ;
	private int attackTick;
	private int dirTickTick;
	
	public EntitySkull(EntityType<? extends EntitySkull> type, World world)
	{
		super(type, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.lookControl = new FreflyLookControl(this);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.experiencePoints = 1;
		this.flyingSpeed = 0.5F;
	}
	
	public static DefaultAttributeContainer getAttributeContainer()
	{
		return MobEntity
				.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
				.build();
	}
	
	/*@Override
	protected void initGoals()
	{
		this.goalSelector.add(1, new AttackPlayerGoal());
		//this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(5, new WanderAroundGoal());
		this.goalSelector.add(5, new LookAroundGoal(this));
	}*/
	
	class FreflyLookControl extends LookControl
	{
		FreflyLookControl(MobEntity entity)
		{
			super(entity);
		}

		protected boolean shouldStayHorizontal()
		{
			return false;
		}
	}
	
	@Override
	public void onPlayerCollision(PlayerEntity player)
	{
		player.damage(DamageSource.GENERIC, 3);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if (random.nextInt(3) == 0)
		{
			updateParticlePos();
			this.world.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0, 0);
		}
		if (random.nextInt(3) == 0)
		{
			updateParticlePos();
			this.world.addParticle(ParticleTypes.DRIPPING_LAVA, particleX, particleY, particleZ, 0, 0, 0);
		}
		if (random.nextInt(3) == 0)
		{
			updateParticlePos();
			this.world.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0, 0, 0);
		}
		
		if (attackTick > 40)
		{
			PlayerEntity target = EntitySkull.this.world.getClosestPlayer(getX(), getY(), getZ(), 20, true);
			if (target != null && this.canSee(target))
			{
				attackTick = 0;
				Vec3d velocity = target
						.getPos()
						.add(0, target.getHeight() * 0.5F, 0)
						.subtract(EntitySkull.this.getPos())
						.normalize()
						.multiply(EntitySkull.this.flyingSpeed);
				setVelocity(velocity);
				this.lookAtEntity(target, 360, 360);
				this.playSound(SoundsRegistry.MOB_SKULL_FLIGHT, MHelper.randRange(0.3F, 0.5F, random), MHelper.randRange(0.75F, 1.25F, random));
			}
			else if (dirTickTick < 0)
			{
				dirTickTick = MHelper.randRange(20, 60, random);
				moveRandomDir();
			}
		}
		else
		{
			if (dirTickTick < 0)
			{
				dirTickTick = MHelper.randRange(20, 60, random);
				moveRandomDir();
			}
		}
		attackTick ++;
		dirTickTick --;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SKELETON_DEATH;
	}

	private void moveRandomDir()
	{
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
		setVelocity(dx, dy, dz);
		lookAt(this.getPos().add(this.getVelocity()));
		this.playSound(SoundsRegistry.MOB_SKULL_FLIGHT, MHelper.randRange(0.3F, 0.5F, random), MHelper.randRange(0.75F, 1.25F, random));
	}

	private void lookAt(Vec3d target)
	{
		double d = target.getX() - this.getX();
		double e = target.getZ() - this.getZ();
		double g = target.getY() - this.getY();

		double h = MathHelper.sqrt(d * d + e * e);
		float i = (float) (MathHelper.atan2(e, d) * 57.2957763671875D) - 90.0F;
		float j = (float) (-(MathHelper.atan2(g, h) * 57.2957763671875D));

		this.pitch = j;
		this.yaw = i;
	}
	
	private void updateParticlePos()
	{
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
	public float getEyeHeight(EntityPose pose)
	{
		return this.getDimensions(pose).height * 0.5F;
	}
	
	@Override
	protected boolean hasWings()
	{
		return true;
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.UNDEAD;
	}
	
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier)
	{
		return false;
	}
	
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}

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
	public boolean isPushable()
	{
		return false;
	}
	
	public static boolean canSpawn(EntityType<? extends EntitySkull> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random)
	{
		return world.getDifficulty() != Difficulty.PEACEFUL && world.getLightLevel(pos) < 8;
	}
	
	class WanderAroundGoal extends Goal
	{
		WanderAroundGoal()
		{
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart()
		{
			return EntitySkull.this.navigation.isIdle();
		}

		public boolean shouldContinue()
		{
			return EntitySkull.this.navigation.isFollowingPath() && EntitySkull.this.random.nextInt(32) > 0;
		}

		public void start()
		{
			BlockPos pos = this.getRandomLocation();
			Path path = EntitySkull.this.navigation.findPathTo(pos, 1);
			if (path != null)
				EntitySkull.this.navigation.startMovingAlong(path, EntitySkull.this.flyingSpeed);
			else
				EntitySkull.this.setVelocity(0, -0.2, 0);
			super.start();
		}

		private BlockPos getRandomLocation()
		{
			Mutable bpos = new Mutable();
			bpos.set(EntitySkull.this.getX(), EntitySkull.this.getY(), EntitySkull.this.getZ());
			
			Vec3d angle = EntitySkull.this.getRotationVec(0.0F);
			Vec3d airTarget = TargetFinder.findAirTarget(EntitySkull.this, 8, 7, angle, 1.5707964F, 2, 1);

			if (airTarget == null)
			{
				airTarget = TargetFinder.findAirTarget(EntitySkull.this, 32, 10, angle, 1.5707964F, 3, 1);
			}

			if (airTarget == null)
			{
				bpos.setX(bpos.getX() + randomRange(32));
				bpos.setZ(bpos.getZ() + randomRange(32));
				bpos.setY(bpos.getY() + randomRange(32));
				return bpos;
			}
			
			bpos.set(airTarget.getX(), airTarget.getY(), airTarget.getZ());
			BlockPos down = bpos.down();
			if (EntitySkull.this.world.getBlockState(down).isFullCube(EntitySkull.this.world, down))
				bpos.offset(Direction.UP);
			
			return bpos;
		}
		
		private int randomRange(int side)
		{
			Random random = EntitySkull.this.random;
			return random.nextInt(side + 1) - (side >> 1);
		}
	}
	
	class AttackPlayerGoal extends Goal
	{
		Vec3d velocity;
		//int timer;
		
		public AttackPlayerGoal()
		{
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}
		
		@Override
		public boolean canStart()
		{
			return EntitySkull.this.world.isPlayerInRange(EntitySkull.this.getX(), EntitySkull.this.getY(), EntitySkull.this.getZ(), 20);
		}
		
		@Override
		public boolean shouldContinue()
		{
			return EntitySkull.this.navigation.isFollowingPath();//timer < 40;
		}
		
		@Override
		public void start()
		{
			System.out.println("Attack!");
			//timer = 0;
			/*PlayerEntity target = EntitySkull.this.world.getClosestPlayer(EntitySkull.this, 30);
			velocity = target
					.getPos()
					.add(0, target.getHeight() * 0.5F, 0)
					.subtract(EntitySkull.this.getPos())
					.normalize()
					.multiply(EntitySkull.this.flyingSpeed);
			EntitySkull.this.setVelocity(velocity);*/
			//EntitySkull.this.yaw = (float) Math.atan2(velocity.x, velocity.z);
			//EntitySkull.this.pitch = (float) Math.atan2(velocity.y, EntitySkull.this.distanceTo(target));
			PlayerEntity target = EntitySkull.this.world.getClosestPlayer(EntitySkull.this, 30);
			Path path = EntitySkull.this.navigation.findPathTo(target.getBlockPos(), 1);
			if (path != null)
				EntitySkull.this.navigation.startMovingAlong(path, EntitySkull.this.flyingSpeed);
			super.start();
		}
		
		@Override
		public void tick()
		{
			//timer ++;
			super.tick();
			//EntitySkull.this.setVelocity(velocity);
			//EntitySkull.this.lookControl.lookAt(velocity);
		}
	}
}