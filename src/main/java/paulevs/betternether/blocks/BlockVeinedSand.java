package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockVeinedSand extends BlockBase
{
	public BlockVeinedSand()
	{
		super(FabricBlockSettings.of(Material.SAND)
				.materialColor(MaterialColor.BROWN)
				.sounds(BlockSoundGroup.SAND)
				.strength(0.5F, 0.5F)
				.build());
		this.setDropItself(false);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if (world.getBlockState(pos.up()).getBlock() == BlocksRegistry.SOUL_VEIN)
			return state;
		else
			return Blocks.SOUL_SAND.getDefaultState();
	}
}
