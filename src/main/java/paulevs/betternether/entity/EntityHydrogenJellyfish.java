package paulevs.betternether.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.World;

public class EntityHydrogenJellyfish extends FlyingEntity implements Flutterer
{
	public EntityHydrogenJellyfish(EntityType<? extends EntityHydrogenJellyfish> type, World world)
	{
		super(type, world);
	}

	public void tick()
	{
		if (this.random.nextInt(32) == 0)
			this.setVelocity(this.random.nextDouble(), this.random.nextDouble(), this.random.nextDouble());
	}
}
