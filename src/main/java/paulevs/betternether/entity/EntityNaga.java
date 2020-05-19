package paulevs.betternether.entity;

import java.util.Random;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.registry.SoundsRegistry;

public class EntityNaga extends HostileEntity implements RangedAttackMob, Monster
{
	public EntityNaga(EntityType<? extends EntityNaga> type, World world)
	{
		super(type, world);
		this.experiencePoints = 10;
	}

	@Override
	protected void initGoals()
	{
		this.targetSelector.add(1, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 40, 20.0F));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(5, new LookAroundGoal(this));
	}
	
	public static DefaultAttributeContainer getAttributeContainer()
	{
		return MobEntity
				.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
				.add(EntityAttributes.GENERIC_ARMOR, 2.0)
				.build();
	}

	@Override
	public void attack(LivingEntity target, float f)
	{
		EntityNagaProjectile projectile = new EntityNagaProjectile(EntityRegistry.NAGA_PROJECTILE, world);
		projectile.setPos(getX(), getEyeY(), getZ());
		projectile.setParams(this, target);
		world.spawnEntity(projectile);
		this.playSound(SoundsRegistry.MOB_NAGA_ATTACK, MHelper.randRange(3F, 5F, random), MHelper.randRange(0.75F, 1.25F, random));
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.UNDEAD;
	}
	
	@Override
	public boolean canHaveStatusEffect(StatusEffectInstance effect)
	{
		return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
	}
	
	@Override
	public SoundEvent getAmbientSound()
	{
		return SoundsRegistry.MOB_NAGA_IDLE;
	}

	@Override
	protected boolean isDisallowedInPeaceful()
	{
		return true;
	}

	@Override
	public int getBodyYawSpeed()
	{
		return 1;
	}
	
	public static boolean canSpawn(EntityType<? extends EntityNaga> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random)
	{
		return world.getDifficulty() != Difficulty.PEACEFUL;
	}
}
