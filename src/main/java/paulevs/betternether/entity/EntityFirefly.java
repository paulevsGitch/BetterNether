package paulevs.betternether.entity;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.blocks.BlockNetherGrass;

public class EntityFirefly extends FlyingEntity// MobEntityWithAi
{
	private static final TrackedData<Float> COLOR_RED = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> COLOR_GREEN = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> COLOR_BLUE = DataTracker.registerData(EntityFirefly.class, TrackedDataHandlerRegistry.FLOAT);
	
	private BlockPos flower;

	public EntityFirefly(EntityType<? extends EntityFirefly> type, World world)
	{
		super(type, world);
		this.moveControl = new FireflyMoveControl(this);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
	}

	/*@Override
	public float getPathfindingFavor(BlockPos pos, WorldView worldView)
	{
		return worldView.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}*/

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR_RED, random.nextFloat() * 0.5F + 0.5F);
		this.dataTracker.startTracking(COLOR_GREEN, random.nextFloat() * 0.5F + 0.5F);
		this.dataTracker.startTracking(COLOR_BLUE, random.nextFloat() * 0.5F + 0.5F);
		this.setNoGravity(true);
	}

	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributes().register(EntityAttributes.FLYING_SPEED);
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(1.0);
		this.getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(0.001);
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.001);
	}

	@Override
	protected void initGoals()
	{
		this.goalSelector.add(1, new FlyRandomlyGoal(this));
		this.goalSelector.add(2, new FindFlowerGoal(this));
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
	
	protected BlockPos findFlower(BlockPos pos, int radius)
	{
		int x1 = pos.getX() - radius;
		int x2 = pos.getX() + radius;
		int y1 = pos.getY() - radius;
		int y2 = pos.getY() + radius;
		int z1 = pos.getZ() - radius;
		int z2 = pos.getZ() + radius;
		Mutable bPos = new Mutable();
		for (int y = y2; y >= y1; y--)
			for (int x = x1; x <= x2; x++)
				for (int z = z1; z <= z2; z++)
				{
					bPos.set(x, y, z);
					if (isFlower(bPos))
						return bPos;
				}
		return null;
	}

	private boolean isFlower(BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		return block instanceof BlockNetherGrass || block instanceof BlockCommonPlant;
	}

	public boolean isWithinDistance(BlockPos pos, int distance)
	{
		return pos.isWithinDistance(getBlockPos(), distance);
	}

	static class FlyRandomlyGoal extends Goal
	{
		private final EntityFirefly firefly;

		public FlyRandomlyGoal(EntityFirefly ghast)
		{
			this.firefly = ghast;
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart()
		{
			MoveControl moveControl = this.firefly.getMoveControl();

			if (!moveControl.isMoving())
			{
				return true;
			}
			else
			{
				double d = moveControl.getTargetX() - this.firefly.getX();
				double e = moveControl.getTargetY() - this.firefly.getY();
				double f = moveControl.getTargetZ() - this.firefly.getZ();
				double g = d * d + e * e + f * f;
				return g < 1.0D || g > 3600.0D;
			}
		}

		public boolean shouldContinue()
		{
			return false;
		}

		public void start()
		{
			Random random = this.firefly.getRandom();
			double x = this.firefly.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double y;
			double z = this.firefly.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			
			BlockPos pos = this.firefly.getBlockPos();
			if (this.firefly.world.isAir(pos.down()) && this.firefly.world.isAir(pos.down(2)) && this.firefly.world.isAir(pos.down(3)))
				y = pos.getY() - 3;
			else
				y = this.firefly.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			
			this.firefly.getMoveControl().moveTo(x, y, z, 0.001);
		}
	}
	
	static class FindFlowerGoal extends Goal
	{
		private final EntityFirefly firefly;
		
		public FindFlowerGoal(EntityFirefly firefly)
		{
			this.firefly = firefly;
		}
		
		@Override
		public boolean canStart()
		{
			return true;
		}
		
		public void tick()
		{
			if (this.firefly.flower != null)
			{
				double x = this.firefly.flower.getX() + 0.5;
				double y = this.firefly.flower.getY() + 0.5;
				double z = this.firefly.flower.getZ() + 0.5;
				
				if (this.firefly.getPos().squaredDistanceTo(x, y, z) < 2)
				{
					this.firefly.flower = null;
					return;
				}
				
				this.firefly.getMoveControl().moveTo(x, y, z, 0.001);
			}
			else if (this.firefly.random.nextInt(16) == 0)
			{
				this.firefly.findFlower();
			}
		}
	}

	static class FireflyMoveControl extends MoveControl
	{
		private final EntityFirefly firefly;
		private int field_7276;

		public FireflyMoveControl(EntityFirefly firefly)
		{
			super(firefly);
			this.firefly = firefly;
			firefly.setNoGravity(true);
		}

		public void tick()
		{
			if (this.state == MoveControl.State.MOVE_TO)
			{
				if (this.field_7276-- <= 0)
				{
					this.field_7276 += this.firefly.getRandom().nextInt(3) + 1;
					Vec3d vec3d = new Vec3d(this.targetX - this.firefly.getX(), this.targetY - this.firefly.getY(),
							this.targetZ - this.firefly.getZ());
					double d = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.method_7051(vec3d, MathHelper.ceil(d)))
					{
						this.firefly.setVelocity(this.firefly.getVelocity().add(vec3d.multiply(0.1D)));
					}
					else
					{
						this.state = MoveControl.State.WAIT;
					}
				}
			}
		}

		private boolean method_7051(Vec3d vec3d, int i)
		{
			Box box = this.firefly.getBoundingBox();

			for (int j = 1; j < i; ++j)
			{
				box = box.offset(vec3d);
				if (!this.firefly.world.doesNotCollide(this.firefly, box))
				{
					return false;
				}
			}

			return true;
		}
	}
	
	private void findFlower()
	{
		int x1 = (int) (this.getX() - 5);
		int x2 = (int) (this.getX() + 5);
		int y1 = (int) (this.getY() - 5);
		int y2 = (int) (this.getY() + 5);
		int z1 = (int) (this.getZ() - 5);
		int z2 = (int) (this.getZ() + 5);
		
		Mutable bPos = new Mutable();
		
		for (int y = y2; y >= y1; y--)
			for (int x = x1; x <= x2; x++)
				for (int z = z1; z <= z2; z++)
				{
					bPos.set(x, y, z);
					if (isFlower(world.getBlockState(bPos)))
					{
						flower = bPos;
						return;
					}
				}
	}
	
	private boolean isFlower(BlockState state)
	{
		Block block = state.getBlock();
		return block instanceof BlockNetherGrass || block instanceof BlockCommonPlant;
	}
}
