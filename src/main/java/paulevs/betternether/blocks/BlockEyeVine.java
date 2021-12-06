package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;

public class BlockEyeVine extends BlockBaseNotFull {
	protected static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);

	public BlockEyeVine() {
		super(FabricBlockSettings.of(Materials.NETHER_PLANT)
				.mapColor(MaterialColor.COLOR_RED)
				.sounds(SoundType.CROP)
				.noCollision()
				.dropsNothing()
				.breakInstantly()
				.nonOpaque());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		setDropItself(false);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		Block up = world.getBlockState(pos.above()).getBlock();
		Block down = world.getBlockState(pos.below()).getBlock();
		if (up != this && up != Blocks.NETHERRACK)
			return Blocks.AIR.defaultBlockState();
		else if (down != this && !(down instanceof BlockEyeBase))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(NetherBlocks.EYE_SEED);
	}
}
