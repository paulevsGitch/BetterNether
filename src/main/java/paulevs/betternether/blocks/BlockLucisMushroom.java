package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class BlockLucisMushroom extends BlockBaseNotFull
{
	private static final VoxelShape V_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final EnumProperty<EnumShape> SHAPE = EnumProperty.of("shape", EnumShape.class);

	public BlockLucisMushroom()
	{
		super(FabricBlockSettings.of(Material.SOLID_ORGANIC)
				.materialColor(MaterialColor.YELLOW)
				.breakByTool(FabricToolTags.AXES)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(1F)
				.lightLevel(15)
				.nonOpaque());
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(SHAPE, EnumShape.CORNER));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING, SHAPE);
	}
	
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return V_SHAPE;
	}

	public enum EnumShape implements StringIdentifiable
	{
		CORNER("corner"),
		SIDE("side"),
		CENTER("center");

		final String name;
		
		EnumShape(String name)
		{
			this.name = name;
		}
		
		@Override
		public String asString()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Lists.newArrayList(new ItemStack(BlocksRegistry.LUCIS_SPORE), new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(2, 4, MHelper.RANDOM)));
	}
}
