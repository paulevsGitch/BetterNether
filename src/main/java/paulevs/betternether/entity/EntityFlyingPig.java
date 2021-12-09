package paulevs.betternether.entity;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherEntities;
import ru.bclib.entity.DespawnableAnimal;

public class EntityFlyingPig extends DespawnableAnimal implements FlyingAnimal {
	private static final EntityDataAccessor<Byte> DATA_SHARED_FLAGS_ID;
	private static final int BIT_ROOSTING = 0;
	private static final int BIT_WARTED = 1;
	private Goal preGoal;

	public EntityFlyingPig(EntityType<? extends EntityFlyingPig> type, Level world) {
		super(type, world);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.xpReward = 2;
		this.flyingSpeed = 0.3F;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.goalSelector.addGoal(2, new FindFoodGoal());
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new SittingGoal());
		this.goalSelector.addGoal(5, new RoostingGoal());
		this.goalSelector.addGoal(6, new WanderAroundGoal());
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob
				.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 6.0)
				.add(Attributes.FOLLOW_RANGE, 12.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.FLYING_SPEED, 0.3)
				.add(Attributes.ATTACK_DAMAGE, 3.0)
				.add(Attributes.ARMOR, 1.0);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world) {
			public boolean isStableDestination(BlockPos pos) {
				return this.level.isEmptyBlock(pos);
			}
		};
		birdNavigation.setCanOpenDoors(false);
		birdNavigation.setCanFloat(true);
		birdNavigation.setCanPassDoors(true);
		return birdNavigation;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_SHARED_FLAGS_ID, MHelper.setBit((byte) 0, BIT_WARTED, random.nextInt(4) == 0));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putByte("byteData", this.entityData.get(DATA_SHARED_FLAGS_ID));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("byteData")) {
			this.entityData.set(DATA_SHARED_FLAGS_ID, tag.getByte("byteData"));
		}
	}

	public boolean isRoosting() {
		byte b = this.entityData.get(DATA_SHARED_FLAGS_ID);
		return MHelper.getBit(b, BIT_ROOSTING);
	}

	public void setRoosting(boolean roosting) {
		byte b = this.entityData.get(DATA_SHARED_FLAGS_ID);
		this.entityData.set(DATA_SHARED_FLAGS_ID, MHelper.setBit(b, BIT_ROOSTING, roosting));
	}

	public boolean isWarted() {
		byte b = this.entityData.get(DATA_SHARED_FLAGS_ID);
		return MHelper.getBit(b, BIT_WARTED);
	}

	public void setWarted(boolean warted) {
		byte b = this.entityData.get(DATA_SHARED_FLAGS_ID);
		this.entityData.set(DATA_SHARED_FLAGS_ID, MHelper.setBit(b, BIT_WARTED, warted));
	}

	@Override
	protected float getSoundVolume() {
		return MHelper.randRange(0.85F, 1.15F, random);
	}

	@Override
	public float getVoicePitch() {
		return MHelper.randRange(0.3F, 0.4F, random);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PIG_DEATH;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.PIG_AMBIENT;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {}

	@Override
	protected void pushEntities() {}

	@Override
	protected boolean isFlapping() {
		return true;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}

	@Override
	protected void tickDeath() {
		if (!level.isClientSide && this.isWarted() && level.getServer().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			this.spawnAtLocation(new ItemStack(Items.NETHER_WART, MHelper.randRange(1, 3, random)));
		}
		super.tickDeath();

	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 5;
	}

	static {
		DATA_SHARED_FLAGS_ID = SynchedEntityData.defineId(EntityFlyingPig.class, EntityDataSerializers.BYTE);
	}

	@Override
	public boolean isFlying() {
		return !this.onGround;
	}

	class WanderAroundGoal extends Goal {
		WanderAroundGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return EntityFlyingPig.this.navigation.isDone() && !EntityFlyingPig.this.isRoosting();
		}

		public boolean canContinueToUse() {
			return EntityFlyingPig.this.navigation.isInProgress() && EntityFlyingPig.this.random.nextInt(32) > 0;
		}

		public void start() {
			if (EntityFlyingPig.this.level.getFluidState(EntityFlyingPig.this.blockPosition()).isEmpty()) {
				BlockPos pos = this.getRandomLocation();
				Path path = EntityFlyingPig.this.navigation.createPath(pos, 1);
				if (path != null)
					EntityFlyingPig.this.navigation.moveTo(path, EntityFlyingPig.this.flyingSpeed);
				else
					EntityFlyingPig.this.setDeltaMovement(0, -0.2, 0);
				EntityFlyingPig.this.setRoosting(false);
			}
			else
				EntityFlyingPig.this.setDeltaMovement(0, 1, 0);
			super.start();
		}

		private BlockPos getRandomLocation() {
			MutableBlockPos bpos = new MutableBlockPos();
			bpos.set(EntityFlyingPig.this.getX(), EntityFlyingPig.this.getY(), EntityFlyingPig.this.getZ());

			Vec3 angle = EntityFlyingPig.this.getViewVector(0.0F);
			Vec3 airTarget = HoverRandomPos.getPos(EntityFlyingPig.this, 8, 7, angle.x, angle.z, 1.5707964F, 2, 1);

			if (airTarget == null) {
				airTarget = HoverRandomPos.getPos(EntityFlyingPig.this, 32, 10, angle.x, angle.z, 1.5707964F, 3, 1);
			}

			if (airTarget == null) {
				bpos.setX(bpos.getX() + randomRange(32));
				bpos.setZ(bpos.getZ() + randomRange(32));
				bpos.setY(bpos.getY() + randomRange(32));
				return bpos;
			}

			bpos.set(airTarget.x(), airTarget.y(), airTarget.z());
			BlockPos down = bpos.below();
			if (EntityFlyingPig.this.level.getBlockState(down).isCollisionShapeFullBlock(EntityFlyingPig.this.level, down))
				bpos.move(Direction.UP);

			while (!EntityFlyingPig.this.level.getFluidState(bpos).isEmpty())
				bpos.move(Direction.UP);

			return bpos;
		}

		private int randomRange(int side) {
			Random random = EntityFlyingPig.this.random;
			return random.nextInt(side + 1) - (side >> 1);
		}

		@Override
		public void stop() {
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}
	}

	class RoostingGoal extends Goal {
		BlockPos roosting;

		@Override
		public boolean canUse() {
			return !(EntityFlyingPig.this.preGoal instanceof SittingGoal) &&
					EntityFlyingPig.this.navigation.isDone() &&
					!EntityFlyingPig.this.isRoosting() &&
					EntityFlyingPig.this.random.nextInt(4) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			return EntityFlyingPig.this.navigation.isInProgress();
		}

		@Override
		public void start() {
			BlockPos pos = this.getRoostingLocation();
			if (pos != null) {
				Path path = EntityFlyingPig.this.navigation.createPath(pos, 1);
				if (path != null) {
					EntityFlyingPig.this.navigation.moveTo(path, EntityFlyingPig.this.flyingSpeed);
					this.roosting = pos;
				}
			}
			super.start();
		}

		@Override
		public void stop() {
			if (this.roosting != null) {
				EntityFlyingPig.this.setPosRaw(roosting.getX() + 0.5, roosting.getY() - 0.25, roosting.getZ() + 0.5);
				EntityFlyingPig.this.setRoosting(true);
				EntityFlyingPig.this.preGoal = this;
			}
			super.stop();
		}

		private BlockPos getRoostingLocation() {
			BlockPos pos = EntityFlyingPig.this.blockPosition();
			Level world = EntityFlyingPig.this.level;
			int up = BlocksHelper.upRay(world, pos, 16);
			pos = pos.relative(Direction.UP, up);
			if (world.getBlockState(pos.above()).getBlock() == Blocks.NETHER_WART_BLOCK)
				return pos;
			else
				return null;
		}
	}

	class SittingGoal extends Goal {
		int timer;
		int ammount;

		@Override
		public boolean canUse() {
			return EntityFlyingPig.this.isRoosting();
		}

		@Override
		public boolean canContinueToUse() {
			return timer < ammount;
		}

		@Override
		public void start() {
			timer = 0;
			ammount = MHelper.randRange(80, 160, EntityFlyingPig.this.random);
			EntityFlyingPig.this.setDeltaMovement(0, 0, 0);
			EntityFlyingPig.this.setYBodyRot(EntityFlyingPig.this.random.nextFloat() * MHelper.PI2);
			super.start();
		}

		@Override
		public void stop() {
			EntityFlyingPig.this.setRoosting(false);
			EntityFlyingPig.this.setDeltaMovement(0, -0.1F, 0);
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}

		@Override
		public void tick() {
			timer++;
			super.tick();
		}
	}

	class FindFoodGoal extends Goal {
		private List<ItemEntity> foods;
		private ItemEntity target;

		@Override
		public boolean canUse() {
			return hasNearFood();
		}

		@Override
		public void start() {
			BlockPos pos = getFood();
			Path path = EntityFlyingPig.this.navigation.createPath(pos, 1);
			if (path != null) {
				EntityFlyingPig.this.navigation.moveTo(path, EntityFlyingPig.this.flyingSpeed);
				EntityFlyingPig.this.setRoosting(false);
			}
			super.start();
		}

		@Override
		public boolean canContinueToUse() {
			return target.isAlive() && EntityFlyingPig.this.navigation.isInProgress();
		}

		@Override
		public void stop() {
			if (target.isAlive() && target.distanceTo(EntityFlyingPig.this) < 1.3) {
				ItemStack stack = ((ItemEntity) target).getItem();

				ItemParticleOption effect = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(stack.getItem()));

				Iterator<?> var14 = level.players().iterator();

				while (var14.hasNext()) {
					ServerPlayer serverPlayerEntity = (ServerPlayer) var14.next();
					if (serverPlayerEntity.distanceToSqr(target.getX(), target.getY(), target.getZ()) < 4096.0D) {
						serverPlayerEntity.connection.send(new ClientboundLevelParticlesPacket(effect, false,
								target.getX(),
								target.getY() + 0.2,
								target.getZ(),
								0.2F, 0.2F, 0.2F, 0, 16));
					}
				}

				EntityFlyingPig.this.eat(level, stack);
				target.kill();
				EntityFlyingPig.this.heal(stack.getCount());
				EntityFlyingPig.this.setDeltaMovement(0, 0.2F, 0);
			}
			EntityFlyingPig.this.preGoal = this;
			super.stop();
		}

		private BlockPos getFood() {
			target = foods.get(EntityFlyingPig.this.random.nextInt(foods.size()));
			return target.blockPosition();
		}

		private boolean hasNearFood() {
			AABB box = new AABB(EntityFlyingPig.this.blockPosition()).inflate(16);
			foods = EntityFlyingPig.this.level.getEntitiesOfClass(ItemEntity.class, box, (entity) -> {
				return ((ItemEntity) entity).getItem().isEdible();
			});
			return !foods.isEmpty();
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mate) {
		EntityFlyingPig pig = NetherEntities.FLYING_PIG.type().create(this.level);
		pig.setWarted(pig.isWarted());
		return pig;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == Items.NETHER_WART;
	}
}