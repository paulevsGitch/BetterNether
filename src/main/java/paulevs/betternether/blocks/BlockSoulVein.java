package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.interfaces.SurvivesOnNetherSand;
import paulevs.betternether.registry.NetherBlocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockSoulVein extends BlockBaseNotFull implements BonemealableBlock, SurvivesOnNetherSand {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

	public BlockSoulVein() {
		super(FabricBlockSettings.of(Materials.NETHER_PLANT)
				.mapColor(MaterialColor.COLOR_PURPLE)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSurviveOnTop(state, world, pos);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (canSurvive(state, world, pos))
			return state;
		else
			return Blocks.AIR.defaultBlockState();
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		popResource(world, pos, new ItemStack(this.asItem()));
	}

	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND)
			world.setBlockAndUpdate(pos.below(), NetherBlocks.VEINED_SAND.defaultBlockState());
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (builder.getParameter(LootContextParams.TOOL).getItem() instanceof ShearsItem)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDrops(state, builder);
	}
}
