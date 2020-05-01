package paulevs.betternether.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class EntityNagaProjectile extends FlyingEntity
{
	private static final int MAX_LIFE_TIME = 60; // 3 seconds * 20 ticks
	private int lifeTime = 0;
	
	public EntityNagaProjectile(EntityType<? extends EntityNagaProjectile> type, World world)
	{
		super(type, world);
		this.experiencePoints = 0;
	}

	public void setParams(LivingEntity owner, Entity target)
	{
		this.updatePosition(getX(), getEyeY() - this.getHeight(), getZ());
		Vec3d dir = target.getPos().add(0, target.getHeight() * 0.25, 0).subtract(getPos()).normalize().multiply(2);
		this.setVelocity(dir);
		this.prevX = getX() - dir.x;
		this.prevY = getY() - dir.y;
		this.prevZ = getZ() - dir.z;
	}

	@Override
	public boolean hasNoGravity()
	{
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance)
	{
		return distance < 128;
	}

	@Override
	public void tick()
	{
		super.tick();
		world.addParticle(ParticleTypes.LARGE_SMOKE,
				getX() + random.nextGaussian() * 0.2,
				getY() + random.nextGaussian() * 0.2,
				getZ() + random.nextGaussian() * 0.2,
				0, 0, 0);
		world.addParticle(ParticleTypes.SMOKE,
				getX() + random.nextGaussian() * 0.2,
				getY() + random.nextGaussian() * 0.2,
				getZ() + random.nextGaussian() * 0.2,
				0, 0, 0);
		
		HitResult hitResult = ProjectileUtil.getCollision(this, (entity) -> { return  entity.isAlive() && entity instanceof LivingEntity; }, RayTraceContext.ShapeType.COLLIDER);
        if (hitResult.getType() != HitResult.Type.MISS)
        {
           this.onCollision(hitResult);
        }

        lifeTime ++;
        if (lifeTime > MAX_LIFE_TIME)
        	effectKill();

        if (isSame(this.prevX, this.getX()) && isSame(this.prevY, this.getY()) && isSame(this.prevZ, this.getZ()))
        	effectKill();
	}
	
	private boolean isSame(double a, double b)
	{
		return Math.abs(a - b) < 0.1;
	}
	
	protected void onCollision(HitResult hitResult)
	{
		HitResult.Type type = hitResult.getType();
		if (type == HitResult.Type.BLOCK)
		{
			for (int i = 0; i < 10; i++)
			{
				world.addParticle(ParticleTypes.LARGE_SMOKE,
						getX() + random.nextGaussian() * 0.5,
						getY() + random.nextGaussian() * 0.5,
						getZ() + random.nextGaussian() * 0.5,
						random.nextGaussian() * 0.2,
						random.nextGaussian() * 0.2,
						random.nextGaussian() * 0.2);
			}
			effectKill();
		}
		else if (type == HitResult.Type.ENTITY)
		{
			Entity entity = ((EntityHitResult) hitResult).getEntity();
			if (entity != this && entity instanceof LivingEntity && !(entity instanceof EntityNaga))
			{
				LivingEntity living = (LivingEntity) entity;
				if (!(living.hasStatusEffect(StatusEffects.WITHER)))
				{
					living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 40));
					//living.damage(DamageSource.GENERIC, 0.5F);
				}
				effectKill();
			}
		}
	}

	private void effectKill()
	{
		for (int i = 0; i < 10; i++)
		{
			world.addParticle(ParticleTypes.ENTITY_EFFECT,
					getX() + random.nextGaussian() * 0.5,
					getY() + random.nextGaussian() * 0.5,
					getZ() + random.nextGaussian() * 0.5,
					0.1, 0.1, 0.1);
		}
		this.kill();
	}

	@Override
	public boolean canHaveStatusEffect(StatusEffectInstance effect)
	{
		return false;
	}
	
	@Override
	public boolean isSilent()
	{
		return true;
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag)
	{
		super.writeCustomDataToTag(tag);
		tag.putInt("life", lifeTime);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);
		if (tag.contains("life"))
		{
			lifeTime = tag.getInt("life");
		}
	}
}