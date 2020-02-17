package paulevs.betternether;

import net.minecraft.world.dimension.DimensionType;

public interface IDimensionable
{
	public void setSpawnDimension(DimensionType type);
	
	public DimensionType getSpawnDimension();
	
	public void setUsedStatue(boolean used);
	
	public boolean usedStatue();
}
