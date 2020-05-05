package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BlockLucisMushroom extends BlockBaseNotFull
{
	private static final VoxelShape V_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final EnumProperty<EnumShape> SHAPE = EnumProperty.of("shape", EnumShape.class);

	public BlockLucisMushroom()
	{
		super(FabricBlockSettings.of(Material.ORGANIC)
				.materialColor(MaterialColor.YELLOW)
				.breakByTool(FabricToolTags.AXES)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(1F)
				.lightLevel(15)
				.nonOpaque());
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(SHAPE, EnumShape.CORNER));
		setDropItself(false);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING, SHAPE);
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return V_SHAPE;
	}

	public enum EnumShape implements StringIdentifiable
	{
		CORNER,
		SIDE,
		CENTER;

		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
		}
	}
}
