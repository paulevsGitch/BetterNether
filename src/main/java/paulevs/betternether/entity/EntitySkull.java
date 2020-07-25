package paulevs.betternether.entity;

import java.util.List;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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
	private int collideTick;
	
	public EntitySkull(EntityType<? extends EntitySkull> type, World world)
	{
		super(type, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.lookControl = new SkullLookControl(this);
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
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
				.build();
	}
	
	class SkullLookControl extends LookControl
	{
		SkullLookControl(MobEntity entity)
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
		collideTick ++;
		if (collideTick > 3)
		{
			collideTick = 0;
			
			boolean shield = player.getActiveItem().getItem() instanceof ShieldItem && player.isUsingItem();
			if (shield)
			{
				player.playSound(SoundEvents.ITEM_SHIELD_BLOCK, MHelper.randRange(0.8F, 1.2F, random), MHelper.randRange(0.8F, 1.2F, random));
				this.setVelocity(new Vec3d(0, 0, 0).subtract(getVelocity()));
			}
			if (player instanceof ServerPlayerEntity)
			{
				if (shield)
				{
					player.getActiveItem().damage(1, random, (ServerPlayerEntity) player);
					if (player.getActiveItem().getDamage() > player.getActiveItem().getMaxDamage())
					{
						player.sendToolBreakStatus(player.getActiveHand());
						if (player.getActiveHand().equals(Hand.MAIN_HAND))
							player.inventory.main.clear();
						else if (player.getActiveHand().equals(Hand.OFF_HAND))
							player.inventory.offHand.clear();
						player.clearActiveItem();
					}
					return;
				}
				player.damage(DamageSource.GENERIC, 1);
				if (random.nextInt(16) == 0)
					player.setOnFireFor(3);
			}
		}
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
		
		if (attackTick > 40 && this.isAlive())
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
				this.playSound(SoundsRegistry.MOB_SKULL_FLIGHT, MHelper.randRange(0.15F, 0.3F, random), MHelper.randRange(0.9F, 1.5F, random));
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
		this.playSound(SoundsRegistry.MOB_SKULL_FLIGHT, MHelper.randRange(0.15F, 0.3F, random), MHelper.randRange(0.75F, 1.25F, random));
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
		if (world.getDifficulty() == Difficulty.PEACEFUL || world.getLightLevel(pos) > 7)
			return false;
		
		Box box = new Box(pos);
		box.expand(256, 256, 256);
		List<EntitySkull> list = world.getEntities(EntitySkull.class, box, (entity) -> { return true; });
		return list.size() < 4;
	}
}