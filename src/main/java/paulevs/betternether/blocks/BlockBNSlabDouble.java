package paulevs.betternether.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBNSlabDouble extends BlockBNSlab
{
	public BlockBNSlabDouble(String name, Material material, MapColor color, SoundType sound)
	{
		super(name + "_slab_double", material, color, sound);
	}

	@Override
	public boolean isDouble()
	{
		return true;
	}
}
