package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockEyeBase extends BlockBase {
	public BlockEyeBase(Properties settings) {
		super(settings);
		setDropItself(false);
	}

	public boolean allowsSpawning(BlockState state, BlockGetter view, BlockPos pos, EntityType<?> type) {
		return false;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		BlockPos blockPos = pos.above();
		Block up = world.getBlockState(blockPos).getBlock();
		if (up != BlocksRegistry.EYE_VINE && up != Blocks.NETHERRACK)
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.EYE_SEED);
	}
}
