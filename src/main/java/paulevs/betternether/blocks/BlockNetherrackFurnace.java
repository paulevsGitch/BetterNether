package paulevs.betternether.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import paulevs.betternether.tileentities.TileEntityNetherrackFurnace;

public class BlockNetherrackFurnace extends BlockCincinnasiteForge
{
	public BlockNetherrackFurnace()
	{
		super("netherrack_furnace");
		this.setHardness(1.0F);
		this.setResistance(1.0F);
		this.setSoundType(SoundType.STONE);
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityNetherrackFurnace();
    }
}
