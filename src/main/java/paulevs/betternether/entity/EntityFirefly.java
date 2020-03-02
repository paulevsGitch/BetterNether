package paulevs.betternether.entity;

import java.util.EnumSet;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.registers.EntityRegister;

public class EntityFirefly extends AnimalEntity implements Flutterer
{
	private static final TrackedData<Float> COLOR_RED = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> COLOR_GREEN = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> COLOR_BLUE = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	
	//private BlockPos flower;

	public EntityFirefly(EntityType<? extends EntityFirefly> type, World world)
	{
		super(type, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.lookControl = new FreflyLookControl(this);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.experiencePoints = 1;
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR_RED, random.nextFloat() * 0.5F + 0.5F);
		this.dataTracker.startTracking(COLOR_GREEN, random.nextFloat() * 0.5F + 0.5F);
		this.dataTracker.startTracking(COLOR_BLUE, random.nextFloat() * 0.5F + 0.5F);
	}

	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributes().register(EntityAttributes.FLYING_SPEED);
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(1.0);
		this.getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(1F);
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.5F);
		this.getAttributeInstance(EntityAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
	}

	@Override
	protected EntityNavigation createNavigation(World world)
	{
		BirdNavigation birdNavigation = new BirdNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos)
			{
				return !this.world.getBlockState(pos.down()).isAir();
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
	}

	@Override
	protected void initGoals()
	{
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.fromTag(ItemTags.FLOWERS), false));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.25D));
		this.goalSelector.add(8, new WanderAroundGoal());
		this.goalSelector.add(9, new SwimGoal(this));
	}

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView worldView)
	{
		return worldView.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return stack.getItem() == Items.GLOWSTONE_DUST;
	}

	@Override
	protected boolean hasWings()
	{
		return true;
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.ARTHROPOD;
	}
	
	@Override
	protected void swimUpward(Tag<Fluid> fluid)
	{
		this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1.0F;
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

	public float getRed()
	{
		return this.dataTracker.get(COLOR_RED);
	}

	public float getGreen()
	{
		return this.dataTracker.get(COLOR_GREEN);
	}

	public float getBlue()
	{
		return this.dataTracker.get(COLOR_BLUE);
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag)
	{
		super.writeCustomDataToTag(tag);
		
		tag.putFloat("ColorRed", getRed());
		tag.putFloat("ColorGreen", getGreen());
		tag.putFloat("ColorBlue", getBlue());
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);
		
		if (tag.contains("ColorRed"))
		{
			this.dataTracker.set(COLOR_RED, tag.getFloat("ColorRed"));
		}
		
		if (tag.contains("ColorGreen"))
		{
			this.dataTracker.set(COLOR_GREEN, tag.getFloat("ColorGreen"));
		}
		
		if (tag.contains("ColorBlue"))
		{
			this.dataTracker.set(COLOR_BLUE, tag.getFloat("ColorBlue"));
		}
	}

	@Override
	public PassiveEntity createChild(PassiveEntity mate)
	{
		return EntityRegister.FIREFLY.create(world);
	}

	class FreflyLookControl extends LookControl
	{
		FreflyLookControl(MobEntity entity)
		{
			super(entity);
		}

		protected boolean shouldStayHorizontal()
		{
			return true;
		}
	}

	class WanderAroundGoal extends Goal
	{
		WanderAroundGoal()
		{
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart()
		{
			return EntityFirefly.this.navigation.isIdle() && EntityFirefly.this.random.nextInt(10) == 0;
		}

		public boolean shouldContinue()
		{
			return !EntityFirefly.this.navigation.isIdle();
		}

		public void start()
		{
			Vec3d vec3d = this.getRandomLocation();
			System.out.println(vec3d);
			if (vec3d != null)
			{
				System.out.println("Fly: " + EntityFirefly.this.canFly());
				System.out.println("No Gravity: " + EntityFirefly.this.hasNoGravity());
				
				System.out.println("Move!");
				System.out.println("From " + EntityFirefly.this.getBlockPos() + " to " + vec3d);
				EntityFirefly.this.navigation.setRangeMultiplier(0.5F);
				Path path = EntityFirefly.this.navigation.findPathTo((BlockPos)(new BlockPos(vec3d)), 20);
				System.out.println("Path is null: " + (path == null));
				EntityFirefly.this.navigation.startMovingAlong(path, 1.0D);
				//EntityFirefly.this.setVelocity(vec3d.subtract(EntityFirefly.this.getPos()).normalize().multiply(0.5F));
			}
			else
				System.out.println("Vec is null!");
			System.out.println("Idle: " + EntityFirefly.this.navigation.isIdle());
			super.start();
		}

		private Vec3d getRandomLocation()
		{
			Vec3d vec3d3 = EntityFirefly.this.getRotationVec(0.0F);
			Vec3d vec3d4 = TargetFinder.findAirTarget(EntityFirefly.this, 8, 7, vec3d3, 1.5707964F, 2, 1);
			return vec3d4 != null ? vec3d4 : TargetFinder.findGroundTarget(EntityFirefly.this, 8, 4, -2, vec3d3, 1.5707963705062866D);
		}
	}
}
