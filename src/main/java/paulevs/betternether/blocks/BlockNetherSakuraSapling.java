package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureNetherSakura;

public class BlockNetherSakuraSapling extends BlockBaseNotFull implements BonemealableBlock {
	private static final VoxelShape SHAPE = Block.box(4, 2, 4, 12, 16, 12);
	private static final StructureNetherSakura STRUCTURE = new StructureNetherSakura();

	public BlockNetherSakuraSapling() {
		super(FabricBlockSettings.of(Material.PLANT)
				.mapColor(MaterialColor.COLOR_PINK)
				.luminance(10)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.dropsNothing()
				.breakInstantly()
				.noCollision()
				.ticksRandomly()
		);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.above()));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return random.nextInt(16) == 0;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		STRUCTURE.grow(world, pos, random, false);
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state))
			performBonemeal(world, random, pos, state);
	}
}
