package paulevs.betternether.entity;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.EntityRegistry;

public class EntityFlyingPig extends AnimalEntity implements Flutterer
{
	private static final TrackedData<Byte> FLAGS;
	private static final int BIT_ROOSTING = 0;
	private static final int BIT_WARTED = 1;
	private Goal preGoal;

	public EntityFlyingPig(EntityType<? extends EntityFlyingPig> type, World world)
	{
		super(type, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.setPathfindingPenalty(PathNodeType.LAVA, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.experiencePoints = 2;
		this.flyingSpeed = 0.3F;
	}

	@Override
	protected void initGoals()
	{
		this.targetSelector.add(1, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new FindFoodGoal());
		this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(4, new SittingGoal());
		this.goalSelector.add(5, new RoostingGoal());
		this.goalSelector.add(6, new WanderAroundGoal());
		this.goalSelector.add(7, new LookAroundGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
	}
	
	public static DefaultAttributeContainer getAttributeContainer()
	{
		return MobEntity
				.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
				.add(EntityAttributes.GENERIC_ARMOR, 1.0)
				.build();
	}
	
	@Override
	protected EntityNavigation createNavigation(World world)
	{
		BirdNavigation birdNavigation = new BirdNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos)
			{
				return this.world.isAir(pos);
			}
		};
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(FLAGS, MHelper.setBit((byte) 0, BIT_WARTED, random.nextInt(4) == 0));
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag)
	{
		super.writeCustomDataToTag(tag);
		
		tag.putByte("byteData", this.dataTracker.get(FLAGS));
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);
		
		if (tag.contains("byteData"))
		{
			this.dataTracker.set(FLAGS, tag.getByte("byteData"));
		}
	}

	public boolean isRoosting()
	{
		byte b = this.dataTracker.get(FLAGS);
		return MHelper.getBit(b, BIT_ROOSTING);
	}

	public void setRoosting(boolean roosting)
	{
		byte b = this.dataTracker.get(FLAGS);
		this.dataTracker.set(FLAGS, MHelper.setBit(b, BIT_ROOSTING, roosting));
	}
	
	public boolean isWarted()
	{
		byte b = this.dataTracker.get(FLAGS);
		return MHelper.getBit(b, BIT_WARTED);
	}
	
	public void setWarted(boolean warted)
	{
		byte b = this.dataTracker.get(FLAGS);
		this.dataTracker.set(FLAGS, MHelper.setBit(b, BIT_WARTED, warted));
	}

	@Override
	protected float getSoundVolume()
	{
		return MHelper.randRange(0.85F, 1.15F, random);
	}

	@Override
	protected float getSoundPitch()
	{
		return MHelper.randRange(0.3F, 0.4F, random);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_PIG_DEATH;
	}
	
	@Override
	public SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_PIG_AMBIENT;
	}

	@Override
	public boolean isPushable()
	{
		return false;
	}

	@Override
	protected void pushAway(Entity entity) {}

	@Override
	protected void tickCramming() {}
	
	@Override
	protected boolean hasWings()
	{
		return true;
	}
	
	@Override
	public boolean canClimb()
	{
		return false;
	}
	
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier)
	{
		return false;
	}
	
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}
	
	@Override
	protected void updatePostDeath()
	{
		if (!world.isClient && this.isWarted() && world.getServer().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
		{
			this.dropStack(new ItemStack(Items.NETHER_WART, MHelper.randRange(1, 3, random)));
		}
		super.updatePostDeath();
		
	}
	
	@Override
	public int getLimitPerChunk()
	{
		return 5;
	}

	static
	{
		FLAGS = DataTracker.registerData(EntityFlyingPig.class, TrackedDataHandlerRegistry.BYTE);
	}
	
	class WanderAroundGoal extends Goal
	{
		WanderAroundGoal()
		{
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart()
		{
			return EntityFlyingPig.this.navigation.isIdle() && !EntityFlyingPig.this.isRoosting();
		}

		public boolean shouldContinue()
		{
			return EntityFlyingPig.this.navigation.isFollowingPath() && EntityFlyingPig.this.random.nextInt(32) > 0;
		}

		public void start()
		{
			if (EntityFlyingPig.this.world.getFluidState(EntityFlyingPig.this.getBlockPos()).isEmpty())
			{
				BlockPos pos = this.getRandomLocation();
				Path path = EntityFlyingPig.this.navigation.findPathTo(pos, 1);
				if (path != null)
					EntityFlyingPig.this.navigation.startMovingAlong(path, EntityFlyingPig.this.flyingSpeed);
				else
					EntityFlyingPig.this.setVelocity(0, -0.2, 0);
				EntityFlyingPig.this.setRoosting(false);
			}
			else
				EntityFlyingPig.this.setVelocity(0, 1, 0);
			super.start();
		}

		private BlockPos getRandomLocation()
		{
			Mutable bpos = new Mutable();
			bpos.set(EntityFlyingPig.this.getX(), EntityFlyingPig.this.getY(), EntityFlyingPig.this.getZ());
			
			Vec3d angle = EntityFlyingPig.this.getRotationVec(0.0F);
			Vec3d airTarget = TargetFinder.findAirTarget(EntityFlyingPig.this, 8, 7, angle, 1.5707964F, 2, 1);

			if (airTarget == null)
			{
				airTarget = TargetFinder.findAirTarget(EntityFlyingPig.this, 32, 10, angle, 1.5707964F, 3, 1);
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
			if (EntityFlyingPig.this.world.getBlockState(down).isFullCube(EntityFlyingPig.this.world, down))
				bpos.move(Direction.UP);
			
			while (!EntityFlyingPig.this.world.getFluidState(bpos).isEmpty())
				bpos.move(Direction.UP);
			
			return bpos;
		}
		
		private int randomRange(int side)
		{
			Random random = EntityFlyingPig.this.random;
			return random.nextInt(side + 1) - (side >> 1);
		}
		
		@Override
		public void stop()
		{
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}
	}
	
	class RoostingGoal extends Goal
	{
		BlockPos roosting;
		
		@Override
		public boolean canStart()
		{
			return !(EntityFlyingPig.this.preGoal instanceof SittingGoal) &&
					EntityFlyingPig.this.navigation.isIdle() &&
					!EntityFlyingPig.this.isRoosting() &&
					EntityFlyingPig.this.random.nextInt(4) == 0;
		}
		
		@Override
		public boolean shouldContinue()
		{
			return EntityFlyingPig.this.navigation.isFollowingPath();
		}
		
		@Override
		public void start()
		{
			BlockPos pos = this.getRoostingLocation();
			if (pos != null)
			{
				Path path = EntityFlyingPig.this.navigation.findPathTo(pos, 1);
				if (path != null)
				{
					EntityFlyingPig.this.navigation.startMovingAlong(path, EntityFlyingPig.this.flyingSpeed);
					this.roosting = pos;
				}
			}
			super.start();
		}

		@Override
		public void stop()
		{
			if (this.roosting != null)
			{
				EntityFlyingPig.this.setPos(roosting.getX() + 0.5, roosting.getY() - 0.25, roosting.getZ() + 0.5);
				EntityFlyingPig.this.setRoosting(true);
				EntityFlyingPig.this.preGoal = this;
			}
			super.stop();
		}
		
		private BlockPos getRoostingLocation()
		{
			BlockPos pos = EntityFlyingPig.this.getBlockPos();
			World world = EntityFlyingPig.this.world;
			int up = BlocksHelper.upRay(world, pos, 16);
			pos = pos.offset(Direction.UP, up);
			if (world.getBlockState(pos.up()).getBlock() == Blocks.NETHER_WART_BLOCK)
				return pos;
			else
				return null;
		}
	}
	
	class SittingGoal extends Goal
	{
		int timer;
		int ammount;

		@Override
		public boolean canStart()
		{
			return EntityFlyingPig.this.isRoosting();
		}

		@Override
		public boolean shouldContinue()
		{
			return timer < ammount;
		}

		@Override
		public void start()
		{
			timer = 0;
			ammount = MHelper.randRange(80, 160, EntityFlyingPig.this.random);
			EntityFlyingPig.this.setVelocity(0, 0, 0);
			EntityFlyingPig.this.setYaw(EntityFlyingPig.this.random.nextFloat() * MHelper.PI2);
			super.start();
		}
		
		@Override
		public void stop()
		{
			EntityFlyingPig.this.setRoosting(false);
			EntityFlyingPig.this.setVelocity(0, -0.1F, 0);
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}

		@Override
		public void tick()
		{
			timer ++;
			super.tick();
		}
	}
	
	class FindFoodGoal extends Goal
	{
		private List<ItemEntity> foods;
		private ItemEntity target;
		
		@Override
		public boolean canStart()
		{
			return hasNearFood();
		}
		
		@Override
		public void start()
		{
			BlockPos pos = getFood();
			Path path = EntityFlyingPig.this.navigation.findPathTo(pos, 1);
			if (path != null)
			{
				EntityFlyingPig.this.navigation.startMovingAlong(path, EntityFlyingPig.this.flyingSpeed);
				EntityFlyingPig.this.setRoosting(false);
			}
			super.start();
		}
		
		@Override
		public boolean shouldContinue()
		{
			return target.isAlive() && EntityFlyingPig.this.navigation.isFollowingPath();
		}
		
		@Override
		public void stop()
		{
			if (target.isAlive() && target.distanceTo(EntityFlyingPig.this) < 1.3)
			{
				ItemStack stack = ((ItemEntity) target).getStack();
				
				ItemStackParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(stack.getItem()));

				Iterator<?> var14 = world.getPlayers().iterator();

				while(var14.hasNext())
				{
					ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)var14.next();
					if (serverPlayerEntity.squaredDistanceTo(target.getX(), target.getY(), target.getZ()) < 4096.0D)
					{
						serverPlayerEntity.networkHandler.sendPacket(new ParticleS2CPacket(effect, false,
								target.getX(),
								target.getY() + 0.2,
								target.getZ(),
								0.2F, 0.2F, 0.2F, 0, 16));
					}
				}
				
				EntityFlyingPig.this.eatFood(world, stack);
				target.kill();
				EntityFlyingPig.this.heal(stack.getCount());
				EntityFlyingPig.this.setVelocity(0, 0.2F, 0);
			}
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}
		
		private BlockPos getFood()
		{
			target = foods.get(EntityFlyingPig.this.random.nextInt(foods.size()));
			return target.getBlockPos();
		}
		
		private boolean hasNearFood()
		{
			Box box = new Box(EntityFlyingPig.this.getBlockPos()).expand(16);
			foods = EntityFlyingPig.this.world.getEntities(ItemEntity.class, box, (entity) -> {
				return ((ItemEntity) entity).getStack().isFood();
			});
			return !foods.isEmpty();
		}
	}

	@Override
	public PassiveEntity createChild(PassiveEntity mate)
	{
		EntityFlyingPig pig = EntityRegistry.FLYING_PIG.create(this.world);
		pig.setWarted(pig.isWarted());
		return pig;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return stack.getItem() == Items.NETHER_WART;
	}
}