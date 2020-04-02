package paulevs.betternether.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class EntityNaga extends HostileEntity
{
	public EntityNaga(EntityType<? extends EntityNaga> type, World world)
	{
		super(type, world);
	}
}
