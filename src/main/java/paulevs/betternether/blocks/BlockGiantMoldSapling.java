package paulevs.betternether.blocks;

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
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.interfaces.SurvivesOnNetherMycelium;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.plants.StructureGiantMold;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class BlockGiantMoldSapling extends BlockBaseNotFull implements BonemealableBlock, SurvivesOnNetherMycelium {
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);
	private static final StructureGiantMold STRUCTURE = new StructureGiantMold();

	public BlockGiantMoldSapling() {
		super(FabricBlockSettings.of(Materials.NETHER_SAPLING)
				.mapColor(MaterialColor.COLOR_LIGHT_GREEN)
				.sounds(SoundType.CROP)
				.noOcclusion()
				.noLootTable()
				.instabreak()
				.noCollission()
				.randomTicks());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return canSurviveOnTop(state, level, pos);
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
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return BlocksHelper.isFertile(world.getBlockState(pos.below())) ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0);
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		STRUCTURE.grow(world, pos, random);
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state))
			performBonemeal(world, random, pos, state);
	}
}
