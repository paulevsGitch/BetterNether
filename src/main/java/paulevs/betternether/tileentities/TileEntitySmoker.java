package paulevs.betternether.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySmoker extends TileEntity implements ITickable
{
	private boolean isEmitter;

	@Override
	public void update()
	{
		if (this.world.isRemote && this.isEmitter);// && this.world.isRemote)
		{
			//this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, 0, 0);
		}
		//System.out.println(pos.toString() + " " + this.isEmitter);
	}
	
	public void setEmitter(boolean isEmitter)
	{
		this.isEmitter = isEmitter;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.isEmitter = compound.getBoolean("emitter");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("emitter", this.isEmitter);
        return compound;
    }
}
