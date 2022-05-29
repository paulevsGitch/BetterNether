package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.tag.CommonBlockTags;
import org.betterx.bclib.world.features.BCLFeature;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalactite;
import org.betterx.betternether.registry.NetherFeatures;

import java.util.Optional;
import java.util.function.Consumer;

public class ScatterFeature
        extends Feature<ScatterFeatureConfig> {
    public static BCLFeature createAndRegister(String name,
                                               int minPerChunk,
                                               int maxPerChunk,
                                               ScatterFeatureConfig cfg) {
        SimpleRandomFeatureConfiguration configuration = new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(NetherFeatures.STALAGMITES,
                        cfg,
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN,
                                BlockPredicate.solid(),
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))),
                PlacementUtils.inlinePlaced(NetherFeatures.STALAGMITES,
                        cfg,
                        EnvironmentScanPlacement.scanningFor(Direction.UP,
                                BlockPredicate.solid(),
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1)))));

        return BCLFeatureBuilder.start(BetterNether.makeID(name), SIMPLE_RANDOM_SELECTOR)
                                .decoration(GenerationStep.Decoration.LOCAL_MODIFICATIONS)
                                .modifier(CountPlacement.of(UniformInt.of(minPerChunk, maxPerChunk)))
                                .modifier(InSquarePlacement.spread())
                                .distanceToTopAndBottom10()
                                .modifier(CountPlacement.of(UniformInt.of(2, 5)))
                                .modifier(RandomOffsetPlacement.of(
                                        ClampedNormalInt.of(0.0f, 2.0f, -6, 6),
                                        ClampedNormalInt.of(0.0f, 0.6f, -2, 2)))
                                .modifier(BiomeFilter.biome())
                                .buildAndRegister(configuration);
    }

    public ScatterFeature() {
        super(ScatterFeatureConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<ScatterFeatureConfig> featurePlaceContext) {
        final WorldGenLevel level = featurePlaceContext.level();
        final BlockPos origin = featurePlaceContext.origin();
        final RandomSource random = featurePlaceContext.random();

        ScatterFeatureConfig config = featurePlaceContext.config();
        Optional<Direction> direction = getTipDirection(level, origin, random, config);
        if (direction.isEmpty()) {
            return false;
        }
        BlockPos blockPos2 = origin.relative(direction.get().getOpposite());
        createPatchOfBlocks(level,
                random,
                blockPos2,
                config);

        int i = (int) (random.nextFloat() * (1 + config.maxHeight() - config.minHeight()) + config.minHeight());
        growCluster(level, origin, blockPos2, direction.get(), i, config);
        return true;
    }

    protected static boolean isValidBase(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected void growCluster(LevelAccessor levelAccessor,
                               BlockPos blockPos,
                               BlockPos blockPos2,
                               Direction direction,
                               int height,
                               ScatterFeatureConfig config) {
        if (isValidBase(levelAccessor.getBlockState(blockPos2))) {
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            buildBaseToTipColumn(height, (blockState) -> {
                levelAccessor.setBlock(mutableBlockPos, blockState, 2);
                mutableBlockPos.move(direction);
            }, config);
        }
    }

    protected BlockState createClusterBlock(int size, ScatterFeatureConfig config) {
        return config.clusterBlock().setValue(BlockStalactite.SIZE, Math.max(0, Math.min(7, size)));
    }

    protected void buildBaseToTipColumn(int height, Consumer<BlockState> consumer, ScatterFeatureConfig config) {
        for (int j = height; j >= 0; j--) {
            consumer.accept(createClusterBlock(j, config));
        }
    }

    private static Optional<Direction> getTipDirection(LevelAccessor levelAccessor,
                                                       BlockPos blockPos,
                                                       RandomSource randomSource,
                                                       ScatterFeatureConfig config) {
        boolean onCeil = config.floorChance() < 1 && isValidBase(levelAccessor.getBlockState(blockPos.above()));
        boolean onFloor = config.floorChance() > 0 && isValidBase(levelAccessor.getBlockState(blockPos.below()));

        if (onCeil && onFloor) {
            return Optional.of(config.isFloor(randomSource) ? Direction.DOWN : Direction.UP);
        }
        if (onCeil) {
            return Optional.of(Direction.DOWN);
        }
        if (onFloor) {
            return Optional.of(Direction.UP);
        }
        return Optional.empty();
    }

    private void createPatchOfBlocks(LevelAccessor levelAccessor,
                                     RandomSource randomSource,
                                     BlockPos blockPos,
                                     ScatterFeatureConfig config) {
        placeClusterBlockIfPossible(levelAccessor, blockPos, config);
        BlockPos pos;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (randomSource.nextFloat() > config.chanceOfDirectionalSpread()) continue;
            pos = blockPos.relative(direction);
            placeClusterBlockIfPossible(levelAccessor, pos, config);

            if (randomSource.nextFloat() > config.chanceOfSpreadRadius2()) continue;
            pos = pos.relative(Direction.getRandom(randomSource));
            placeClusterBlockIfPossible(levelAccessor, pos, config);

            if (randomSource.nextFloat() > config.chanceOfSpreadRadius3()) continue;
            pos = pos.relative(Direction.getRandom(randomSource));
            placeClusterBlockIfPossible(levelAccessor, pos, config);
        }
    }

    protected void placeClusterBlockIfPossible(LevelAccessor levelAccessor,
                                               BlockPos blockPos,
                                               ScatterFeatureConfig config) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        if (blockState.is(CommonBlockTags.STALAGMITE_REPLACEABLE)) {
            if (config.baseState().isPresent())
                levelAccessor.setBlock(blockPos, config.baseState().get(), 2);
        }
    }
}
