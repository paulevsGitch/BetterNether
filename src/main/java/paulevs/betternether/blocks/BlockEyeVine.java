package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockEyeVine extends BlockBaseNotFull
{
	protected static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);

	public BlockEyeVine()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.RED)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.dropsNothing()
				.breakInstantly()
				.nonOpaque());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		setDropItself(false);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		Block up = world.getBlockState(pos.up()).getBlock();
		Block down = world.getBlockState(pos.down()).getBlock();
		if (up != this && up != Blocks.NETHERRACK)
			return Blocks.AIR.getDefaultState();
		else if (down != this && !(down instanceof BlockEyeBase))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksRegistry.EYE_SEED);
	}
}
