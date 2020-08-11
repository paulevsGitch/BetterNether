package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockVeinedSand extends BlockBase
{
	public BlockVeinedSand()
	{
		super(FabricBlockSettings.of(Material.AGGREGATE)
				.materialColor(MaterialColor.BROWN)
				.sounds(BlockSoundGroup.SAND)
				.strength(0.5F, 0.5F));
		this.setDropItself(false);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (world.getBlockState(pos.up()).getBlock() == BlocksRegistry.SOUL_VEIN)
			return state;
		else
			return Blocks.SOUL_SAND.getDefaultState();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(Blocks.SOUL_SAND);
	}
}
