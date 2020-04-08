package paulevs.betternether.entity;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityNaga extends HostileEntity implements RangedAttackMob
{
	public EntityNaga(EntityType<? extends EntityNaga> type, World world)
	{
		super(type, world);
	}

	@Override
	protected void initGoals()
	{
		this.targetSelector.add(1, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 40, 20.0F));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(4, new LookAroundGoal(this));
	}

	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(2.0D);
	}

	@Override
	public void attack(LivingEntity target, float f)
	{
		//ArrowEntity arrow = new ArrowEntity(world, getX(), getEyeY(), getZ());
		//arrow.setVelocity(target.getPos().subtract(getPos()).normalize());
		//world.spawnEntity(arrow);
		//Vec3d dir = target.getPos().subtract(getPos()).normalize();
		//WitherSkullEntity skull = new WitherSkullEntity(this.world, this, dir.x, dir.y, dir.z);
		//skull.setCharged(false);
		//world.spawnEntity(skull);
		//EntityNagaProjectile p = new EntityNagaProjectile(EntityRegistry.NAGA_PROJECTILE, world, getX(), getEyeY(), getZ(), dir.x, dir.y, dir.z);
		///p.setPos(getX(), getEyeY(), getZ());
		//p.setVelocity(target.getPos().subtract(getPos()).normalize());
		//p.owner = this;
		//world.spawnEntity(p);
		
		//Vec3d dir = target.getPos().subtract(getPos()).normalize();
		//DragonFireballEntity fireball = new DragonFireballEntity(world, this, dir.x, dir.y, dir.z);
		//fireball.ef
		//world.spawnEntity(fireball);
		
		//EntityNagaProjectile projectile = new EntityNagaProjectile(EntityRegistry.NAGA_PROJECTILE, world);
		//projectile.setPos(getX(), getEyeY(), getZ());
		//projectile.setParams(this, target);
		//projectile.setPos(getX(), getEyeY(), getZ());
		//projectile.setPos(getX(), getEyeY(), getZ());
		//world.spawnEntity(projectile);
		
		//ShulkerBulletEntity bullet = new ShulkerBulletEntity(world, this, target, Axis.pickRandomAxis(random));
		//world.spawnEntity(bullet);
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.UNDEAD;
	}
}
