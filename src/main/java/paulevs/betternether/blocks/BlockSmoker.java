package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.blocks.shapes.TripleShape;

public class BlockSmoker extends BlockBaseNotFull
{
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	
	public BlockSmoker()
	{
		super(FabricBlockSettings.copyOf(Materials.COMMON_WOOD)
				.materialColor(MaterialColor.BROWN)
				.build());
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.TOP));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (world.isAir(pos.up()))
			world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return state.get(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		Block side = world.getBlockState(pos.up()).getBlock();
		if (side != this)
			return state.with(SHAPE, TripleShape.TOP);
		side = world.getBlockState(pos.down()).getBlock();
		if (side == this)
			return state.with(SHAPE, TripleShape.MIDDLE);
		else
			return state.with(SHAPE, TripleShape.BOTTOM);
	}
}
