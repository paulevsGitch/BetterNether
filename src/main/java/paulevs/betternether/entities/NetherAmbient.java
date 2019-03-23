package paulevs.betternether.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class NetherAmbient extends NetherCreature
{
	public NetherAmbient(World worldIn)
	{
		super(worldIn);
	}

    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return false;
    }
}
