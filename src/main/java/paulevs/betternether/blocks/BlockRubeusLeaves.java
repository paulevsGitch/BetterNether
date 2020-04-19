package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.client.IRenderTypeable;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockRubeusLeaves extends LeavesBlock implements IRenderTypeable
{
	public BlockRubeusLeaves()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).sounds(BlockSoundGroup.GRASS).nonOpaque().build());
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos posFrom)
	{
		int i = getDistanceFromLog(newState) + 1;
		if (i != 1 || (Integer) state.get(DISTANCE) != i)
		{
			world.getBlockTickScheduler().schedule(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, IWorld world, BlockPos pos)
	{
		int i = 7;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Direction[] var5 = Direction.values();
		int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7)
		{
			Direction direction = var5[var7];
			mutable.set(pos, direction);
			i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (i == 1)
			{
				break;
			}
		}

		return (BlockState) state.with(DISTANCE, i);
	}

	private static int getDistanceFromLog(BlockState state)
	{
		if (state.getBlock() == BlocksRegistry.RUBEUS_LOG || state.getBlock() == BlocksRegistry.RUBEUS_BARK)
		{
			return 0;
		}
		else
		{
			return state.getBlock() instanceof LeavesBlock ? (Integer) state.get(DISTANCE) : 7;
		}
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.CUTOUT;
	}
}
