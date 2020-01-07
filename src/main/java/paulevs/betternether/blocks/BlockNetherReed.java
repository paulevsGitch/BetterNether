package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BlockNetherReed extends BlockBase
{
	public static final BooleanProperty TOP = BooleanProperty.of("top");
	
	public BlockNetherReed()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.CYAN)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.breakInstantly()
				.nonOpaque()
				.ticksRandomly()
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(TOP, true));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(TOP);
    }
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		Block up = world.getBlockState(pos.up()).getBlock();
		BlockState down = world.getBlockState(pos.down());
		if (BlocksHelper.isNetherGround(down))
		{
			BlockPos posDown = pos.down();
			boolean lava = BlocksHelper.isLava(world.getBlockState(posDown.north()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.south()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.east()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.west()));
			if (lava)
			{
				return up == this ? this.getDefaultState().with(TOP, false) : this.getDefaultState();
			}
			return Blocks.AIR.getDefaultState();
		}
		else if (down.getBlock() != this)
			return Blocks.AIR.getDefaultState();
		else if (up != this)
			return this.getDefaultState();
		else
			return this.getDefaultState().with(TOP, false);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		BlockPos posDown = pos.down();
		BlockState down = world.getBlockState(posDown);
		if (BlocksHelper.isNetherGround(down))
		{
			boolean lava = BlocksHelper.isLava(world.getBlockState(posDown.north()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.south()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.east()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.west()));
			return lava;
		}
		else
			return down.getBlock() == this;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		if (!canPlaceAt(state, world, pos))
		{
			world.breakBlock(pos, true);
			return;
		}
		if (state.get(TOP).booleanValue())
		{
			BlockPos up = pos.up();
			boolean grow = world.isAir(up);
			if (grow)
			{
				int length = BlocksHelper.getLengthDown(world, pos, this);
				boolean isFertile = BlocksHelper.isFertile(world.getBlockState(pos.down(length)));
				if (isFertile)
					length -= 2;
				grow = (length < 3) && (isFertile ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0));
				if (grow)
				{
					BlocksHelper.setWithoutUpdate(world, up, getDefaultState());
					BlocksHelper.setWithoutUpdate(world, pos, getDefaultState().with(TOP, false));
				}
			}
		}
	}
}
