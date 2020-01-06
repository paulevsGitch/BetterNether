package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BlockCommonPlant extends BlockBaseNotFull implements Fertilizable
{
	public static final IntProperty AGE = IntProperty.of("age", 0, 3);
	
	public BlockCommonPlant(MaterialColor color)
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.BLACK)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(AGE);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return BlocksHelper.isNetherGround(world.getBlockState(pos.down()));
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return state.get(AGE) < 3;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		int age = state.get(AGE);
		if (age < 3)
			return BlocksHelper.isFertile(world.getBlockState(pos.down())) ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0);
		else
			return false;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		int age = state.get(AGE);
		world.setBlockState(pos, state.with(AGE, age + 1));
	}
}