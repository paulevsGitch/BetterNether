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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureAnchorTreeBranch;
import paulevs.betternether.structures.plants.StructureWillow;
import ru.bclib.blocks.FeatureHangingSaplingBlock;
import ru.bclib.blocks.FeatureSaplingBlock;
import ru.bclib.world.features.DefaultFeature;

class AnchorTreeFeature extends DefaultFeature {
	private static final StructureAnchorTreeBranch STRUCTURE = new StructureAnchorTreeBranch();
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		STRUCTURE.grow(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random(), false);
		return true;
	}
}

public class BlockAnchorTreeSapling extends FeatureHangingSaplingBlock implements BonemealableBlock {
	private static final DefaultFeature FEATURE = new AnchorTreeFeature();
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return BlocksHelper.isNetherrack(blockState);
	}
	
	@Override
	protected Feature<?> getFeature(BlockState state) {
		return FEATURE;
	}
}
