package paulevs.betternether.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherEntities;
import paulevs.betternether.registry.SoundsRegistry;
import ru.bclib.entity.DespawnableAnimal;

import java.util.*;

public class EntityFirefly extends DespawnableAnimal implements FlyingAnimal {
	private static final HashSet<Block> FLOWERS;
	private static final Vec3i[] SEARCH;

	private static final EntityDataAccessor<Float> COLOR_RED = SynchedEntityData.defineId(EntityFirefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> COLOR_GREEN = SynchedEntityData.defineId(EntityFirefly.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> COLOR_BLUE = SynchedEntityData.defineId(EntityFirefly.class, EntityDataSerializers.FLOAT);

	private boolean mustSit = false;

	public EntityFirefly(EntityType<? extends EntityFirefly> type, Level world) {
		super(type, world);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.lookControl = new FreflyLookControl(this);
		this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.xpReward = 1;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		makeColor(random.nextFloat(), random.nextFloat() * 0.5F + 0.25F, 1);
	}
	
	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob
			.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 1.0)
			.add(Attributes.FLYING_SPEED, 0.6)
			.add(Attributes.MOVEMENT_SPEED, 0.25)
			.add(Attributes.FOLLOW_RANGE, 48.0);
	}
	
	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world) {
			public boolean isStableDestination(BlockPos pos) {
				BlockState state = this.level.getBlockState(pos.below());
				boolean valid = !state.isAir() && state.getMaterial() != Material.LAVA;
				if (valid) {
					state = this.level.getBlockState(pos);
					valid = state.isAir() || !state.getMaterial().blocksMotion();
					valid = valid && state.getBlock() != NetherBlocks.EGG_PLANT;
					valid = valid && !state.getMaterial().blocksMotion();
				}
				return valid;
			}

			public void tick() {
				super.tick();
			}
		};
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(false);
		birdNavigation.setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new SittingGoal());
		this.goalSelector.addGoal(5, new MoveToFlowersGoal());
		this.goalSelector.addGoal(6, new WanderAroundGoal());
		this.goalSelector.addGoal(7, new MoveRandomGoal());
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader worldView) {
		return worldView.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == Items.GLOWSTONE_DUST;
	}

	@Override
	protected boolean isFlapping() {
		return true;
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	protected void jumpInLiquid(TagKey<Fluid> fluid) {
		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	public float getRed() {
		return this.entityData.get(COLOR_RED);
	}

	public float getGreen() {
		return this.entityData.get(COLOR_GREEN);
	}

	public float getBlue() {
		return this.entityData.get(COLOR_BLUE);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putFloat("ColorRed", getRed());
		tag.putFloat("ColorGreen", getGreen());
		tag.putFloat("ColorBlue", getBlue());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("ColorRed")) {
			this.entityData.set(COLOR_RED, tag.getFloat("ColorRed"));
		}

		if (tag.contains("ColorGreen")) {
			this.entityData.set(COLOR_GREEN, tag.getFloat("ColorGreen"));
		}

		if (tag.contains("ColorBlue")) {
			this.entityData.set(COLOR_BLUE, tag.getFloat("ColorBlue"));
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mate) {
		return NetherEntities.FIREFLY.type().create(world);
	}

	@Override
	public boolean isFlying() {
		return !this.onGround;
	}

	class FreflyLookControl extends LookControl {
		FreflyLookControl(Mob entity) {
			super(entity);
		}

		protected boolean resetXRotOnTick() {
			return true;
		}
	}

	class WanderAroundGoal extends Goal {
		WanderAroundGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return EntityFirefly.this.navigation.isDone() && EntityFirefly.this.random.nextInt(10) == 0;
		}

		public boolean canContinueToUse() {
			return EntityFirefly.this.navigation.isInProgress();
		}

		public void start() {
			BlockPos pos = this.getRandomLocation();
			// if (pos != null)
			// {
			Path path = EntityFirefly.this.navigation.createPath(pos, 1);
			if (path != null)
				EntityFirefly.this.navigation.moveTo(path, 1.0D);
			else
				EntityFirefly.this.setDeltaMovement(0, -0.2, 0);
			// }
			super.start();
		}

		private BlockPos getRandomLocation() {
			Level w = EntityFirefly.this.level;
			MutableBlockPos bpos = new MutableBlockPos();
			bpos.set(EntityFirefly.this.getX(), EntityFirefly.this.getY(), EntityFirefly.this.getZ());

			if (w.isEmptyBlock(bpos.below(2)) && w.isEmptyBlock(bpos.below())) {
				int y = bpos.getY() - 1;
				while (w.isEmptyBlock(bpos.below(2)) && y > 0)
					bpos.setY(y--);
				return bpos;
			}

			Vec3 angle = EntityFirefly.this.getViewVector(0.0F);
			Vec3 airTarget = HoverRandomPos.getPos(EntityFirefly.this, 8, 7, angle.x, angle.z, 1.5707964F, 2, 1);

			if (airTarget == null) {
				airTarget = HoverRandomPos.getPos(EntityFirefly.this, 16, 10, angle.x, angle.z, 1.5707964F, 3, 1);
			}

			if (airTarget == null) {
				bpos.setX(bpos.getX() + randomRange(8));
				bpos.setZ(bpos.getZ() + randomRange(8));
				bpos.setY(bpos.getY() + randomRange(2));
				return bpos;
			}

			bpos.set(airTarget.x(), airTarget.y(), airTarget.z());

			return bpos;
		}

		private int randomRange(int side) {
			Random random = EntityFirefly.this.random;
			return random.nextInt(side + 1) - (side >> 1);
		}

		@Override
		public void tick() {
			checkMovement();
			super.tick();
		}
	}

	class MoveToFlowersGoal extends Goal {
		MoveToFlowersGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return EntityFirefly.this.navigation.isDone() && EntityFirefly.this.random.nextInt(30) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return EntityFirefly.this.navigation.isInProgress();
		}

		@Override
		public void start() {
			BlockPos pos = this.getFlowerLocation();
			if (pos != null) {
				Path path = EntityFirefly.this.navigation.createPath((BlockPos) (new BlockPos(pos)), 1);
				EntityFirefly.this.navigation.moveTo(path, 1.0D);
			}
			super.start();
		}

		@Override
		public void stop() {
			if (isFlower(EntityFirefly.this.getFeetBlockState()))
				EntityFirefly.this.mustSit = true;
			super.stop();
		}

		private BlockPos getFlowerLocation() {
			Level w = EntityFirefly.this.level;
			MutableBlockPos bpos = new MutableBlockPos();

			for (Vec3i offset : SEARCH) {
				bpos.set(
						EntityFirefly.this.getX() + offset.getX(),
						EntityFirefly.this.getY() + offset.getY(),
						EntityFirefly.this.getZ() + offset.getZ());
				if (isFlower(w.getBlockState(bpos)))
					return bpos;
			}

			return null;
		}

		private boolean isFlower(BlockState state) {
			Block b = state.getBlock();
			return FLOWERS.contains(b);
		}

		@Override
		public void tick() {
			checkMovement();
			super.tick();
		}
	}

	private void checkMovement() {
		Vec3 vel = EntityFirefly.this.getDeltaMovement();
		if (Math.abs(vel.x) > 0.1 || Math.abs(vel.z) > 0.1) {
			double d = Math.abs(EntityFirefly.this.xo - EntityFirefly.this.getX());
			d += Math.abs(EntityFirefly.this.zo - EntityFirefly.this.getZ());
			if (d < 0.1)
				EntityFirefly.this.navigation.stop();
		}
	}

	class SittingGoal extends Goal {
		int timer;
		int ammount;

		SittingGoal() {}

		@Override
		public boolean canUse() {
			if (EntityFirefly.this.mustSit && EntityFirefly.this.navigation.isDone()) {
				BlockPos pos = new BlockPos(EntityFirefly.this.getX(), EntityFirefly.this.getY(), EntityFirefly.this.getZ());
				BlockState state = EntityFirefly.this.level.getBlockState(pos.below());
				return !state.isAir() && !state.getMaterial().isLiquid();
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return timer < ammount;
		}

		@Override
		public void start() {
			timer = 0;
			ammount = EntityFirefly.this.random.nextInt(21) + 20;
			EntityFirefly.this.mustSit = false;
			EntityFirefly.this.setDeltaMovement(0, -0.1, 0);
			super.start();
		}

		@Override
		public void stop() {
			EntityFirefly.this.setDeltaMovement(0, 0.1, 0);
			super.stop();
		}

		@Override
		public void tick() {
			timer++;
			super.tick();
		}
	}

	class MoveRandomGoal extends Goal {
		int timer;
		int ammount;

		MoveRandomGoal() {}

		@Override
		public boolean canUse() {
			return EntityFirefly.this.navigation.isDone() && EntityFirefly.this.random.nextInt(20) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return timer < ammount;
		}

		@Override
		public void start() {
			timer = 0;
			ammount = EntityFirefly.this.random.nextInt(30) + 10;
			Vec3 velocity = new Vec3(
					EntityFirefly.this.random.nextDouble(),
					EntityFirefly.this.random.nextDouble(),
					EntityFirefly.this.random.nextDouble());
			if (velocity.lengthSqr() == 0)
				velocity = new Vec3(1, 0, 0);
			EntityFirefly.this.setDeltaMovement(velocity.normalize().scale(EntityFirefly.this.flyingSpeed));
			super.start();
		}

		@Override
		public void tick() {
			timer++;
			super.tick();
		}
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundsRegistry.MOB_FIREFLY_FLY;
	}

	@Override
	protected float getSoundVolume() {
		return MHelper.randRange(0.1F, 0.3F, random);
	}

	private void makeColor(float hue, float saturation, float brightness) {
		float red = 0;
		float green = 0;
		float blue = 0;
		float f3 = (hue - (float) Math.floor(hue)) * 6F;
		float f4 = f3 - (float) Math.floor(f3);
		float f5 = brightness * (1.0F - saturation);
		float f6 = brightness * (1.0F - saturation * f4);
		float f7 = brightness * (1.0F - saturation * (1.0F - f4));
		switch ((int) f3) {
			case 0:
				red = (byte) (brightness * 255F + 0.5F);
				green = (byte) (f7 * 255F + 0.5F);
				blue = (byte) (f5 * 255F + 0.5F);
				break;
			case 1:
				red = (byte) (f6 * 255F + 0.5F);
				green = (byte) (brightness * 255F + 0.5F);
				blue = (byte) (f5 * 255F + 0.5F);
				break;
			case 2:
				red = (byte) (f5 * 255F + 0.5F);
				green = (byte) (brightness * 255F + 0.5F);
				blue = (byte) (f7 * 255F + 0.5F);
				break;
			case 3:
				red = (byte) (f5 * 255F + 0.5F);
				green = (byte) (f6 * 255F + 0.5F);
				blue = (byte) (brightness * 255F + 0.5F);
				break;
			case 4:
				red = (byte) (f7 * 255F + 0.5F);
				green = (byte) (f5 * 255F + 0.5F);
				blue = (byte) (brightness * 255F + 0.5F);
				break;
			case 5:
				red = (byte) (brightness * 255F + 0.5F);
				green = (byte) (f5 * 255F + 0.5F);
				blue = (byte) (f6 * 255F + 0.5F);
				break;
		}
		this.entityData.define(COLOR_RED, red / 255F);
		this.entityData.define(COLOR_GREEN, green / 255F);
		this.entityData.define(COLOR_BLUE, blue / 255F);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 5;
	}

	static {
		ArrayList<Vec3i> points = new ArrayList<Vec3i>();
		int radius = 6;
		int r2 = radius * radius;
		for (int x = -radius; x <= radius; x++)
			for (int y = -radius; y <= radius; y++)
				for (int z = -radius; z <= radius; z++)
					if (x * x + y * y + z * z <= r2)
						points.add(new Vec3i(x, y, z));
		points.sort(new Comparator<Vec3i>() {
			@Override
			public int compare(Vec3i v1, Vec3i v2) {
				int d1 = v1.getX() * v1.getX() + v1.getY() * v1.getY() + v1.getZ() * v1.getZ();
				int d2 = v2.getX() * v2.getX() + v2.getY() * v2.getY() + v2.getZ() * v2.getZ();
				return d1 - d2;
			}
		});
		SEARCH = points.toArray(new Vec3i[] {});

		FLOWERS = new HashSet<Block>();
		FLOWERS.add(NetherBlocks.NETHER_GRASS);
		FLOWERS.add(NetherBlocks.SOUL_GRASS);
		FLOWERS.add(NetherBlocks.SWAMP_GRASS);
		FLOWERS.add(NetherBlocks.BLACK_APPLE);
		FLOWERS.add(NetherBlocks.MAGMA_FLOWER);
		FLOWERS.add(NetherBlocks.SOUL_VEIN);
		FLOWERS.add(NetherBlocks.MAT_REED.getStem());
		FLOWERS.add(NetherBlocks.INK_BUSH);
		FLOWERS.add(NetherBlocks.INK_BUSH_SEED);
		FLOWERS.add(NetherBlocks.POTTED_PLANT);
		FLOWERS.add(Blocks.NETHER_WART);
	}

	public static boolean canSpawn(EntityType<? extends EntityFirefly> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
		if (pos.getY() >= world.dimensionType().minY()) return false;
		int h = BlocksHelper.downRay(world, pos, 10);
		if (h > 8)
			return false;
		for (int i = 1; i <= h; i++)
			if (BlocksHelper.isLava(world.getBlockState(pos.below(i))))
				return false;
		return true;
	}

	@Override
	public boolean isPushable() {
		return false;
	}
}