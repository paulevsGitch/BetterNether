package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.world.features.BCLFeature;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;

import java.util.Optional;
import java.util.function.Consumer;

public class ClusterFeature
        extends Feature<PointedDripstoneConfiguration> {
    public static BCLFeature createAndRegister(String name) {
        SimpleRandomFeatureConfiguration configuration = new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(NetherFeatures.STALAGMITE_NETHERRACK,
                        new PointedDripstoneConfiguration(0.2f, 0.7f, 0.5f, 0.5f),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN,
                                BlockPredicate.solid(),
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))),
                PlacementUtils.inlinePlaced(NetherFeatures.STALAGMITE_NETHERRACK,
                        new PointedDripstoneConfiguration(0.2f, 0.7f, 0.5f, 0.5f),
                        EnvironmentScanPlacement.scanningFor(Direction.UP,
                                BlockPredicate.solid(),
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1)))));

        return BCLFeatureBuilder.start(BetterNether.makeID(name), SIMPLE_RANDOM_SELECTOR)
                                .decoration(GenerationStep.Decoration.LOCAL_MODIFICATIONS)
                                .modifier(CountPlacement.of(UniformInt.of(192, 256)))
                                .modifier(InSquarePlacement.spread())
                                .distanceToTopAndBottom10()
                                .modifier(CountPlacement.of(UniformInt.of(1, 5)))
                                .modifier(RandomOffsetPlacement.of(
                                        ClampedNormalInt.of(0.0f, 3.0f, -10, 10),
                                        ClampedNormalInt.of(0.0f, 0.6f, -2, 2)))
                                .modifier(BiomeFilter.biome())
                                .buildAndRegister(configuration);
    }

    private Block block = NetherBlocks.NETHERRACK_STALACTITE;
    private BlockState baseBlock = null;

    public ClusterFeature() {
        super(PointedDripstoneConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> featurePlaceContext) {
        final WorldGenLevel level = featurePlaceContext.level();
        final BlockPos origin = featurePlaceContext.origin();
        final RandomSource random = featurePlaceContext.random();

        PointedDripstoneConfiguration config = featurePlaceContext.config();
        Optional<Direction> direction = getTipDirection(level, origin, random);
        if (direction.isEmpty()) {
            return false;
        }
        BlockPos blockPos2 = origin.relative(direction.get().getOpposite());
        createPatchOfDripstoneBlocks(level,
                random,
                blockPos2,
                config);

        int i = random.nextFloat() < config.chanceOfTallerDripstone && DripstoneUtils.isEmptyOrWater(
                level.getBlockState(origin.relative(direction.get()))) ? 2 : 1;
        growPointedDripstone(level, origin, blockPos2, direction.get(), i, false);
        return true;
    }

    protected static boolean isValidBase(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected void growPointedDripstone(LevelAccessor levelAccessor,
                                        BlockPos blockPos,
                                        BlockPos blockPos2,
                                        Direction direction,
                                        int i,
                                        boolean bl) {
        if (isValidBase(levelAccessor.getBlockState(blockPos2))) {
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            buildBaseToTipColumn(direction, i, bl, (blockState) -> {
                if (blockState.is(block)) {
                   /* blockState = blockState.setValue(PointedDripstoneBlock.WATERLOGGED,
                            levelAccessor.isWaterAt(mutableBlockPos));*/
                }

                levelAccessor.setBlock(mutableBlockPos, blockState, 2);
                mutableBlockPos.move(direction);
            });
        }
    }

    private BlockState createPointedDripstone(Direction direction, DripstoneThickness dripstoneThickness) {
        return block
                .defaultBlockState()
                //.setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)
                //.setValue(PointedDripstoneBlock.THICKNESS, dripstoneThickness)
                ;
    }

    protected void buildBaseToTipColumn(Direction direction, int i, boolean bl, Consumer<BlockState> consumer) {
        if (i >= 3) {
            consumer.accept(createPointedDripstone(direction, DripstoneThickness.BASE));

            for (int j = 0; j < i - 3; ++j) {
                consumer.accept(createPointedDripstone(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (i >= 2) {
            consumer.accept(createPointedDripstone(direction, DripstoneThickness.FRUSTUM));
        }

        if (i >= 1) {
            consumer.accept(createPointedDripstone(direction,
                    bl ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }

    private static Optional<Direction> getTipDirection(LevelAccessor levelAccessor,
                                                       BlockPos blockPos,
                                                       RandomSource randomSource) {
        boolean bl = isValidBase(levelAccessor.getBlockState(blockPos.above()));
        boolean bl2 = isValidBase(levelAccessor.getBlockState(blockPos.below()));
        if (bl && bl2) {
            return Optional.of(randomSource.nextBoolean() ? Direction.DOWN : Direction.UP);
        }
        if (bl) {
            return Optional.of(Direction.DOWN);
        }
        if (bl2) {
            return Optional.of(Direction.UP);
        }
        return Optional.empty();
    }

    private void createPatchOfDripstoneBlocks(LevelAccessor levelAccessor,
                                              RandomSource randomSource,
                                              BlockPos blockPos,
                                              PointedDripstoneConfiguration pointedDripstoneConfiguration) {
        placeDripstoneBlockIfPossible(levelAccessor, blockPos);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (randomSource.nextFloat() > pointedDripstoneConfiguration.chanceOfDirectionalSpread) continue;
            BlockPos blockPos2 = blockPos.relative(direction);
            placeDripstoneBlockIfPossible(levelAccessor, blockPos2);
            if (randomSource.nextFloat() > pointedDripstoneConfiguration.chanceOfSpreadRadius2) continue;
            BlockPos blockPos3 = blockPos2.relative(Direction.getRandom(randomSource));
            placeDripstoneBlockIfPossible(levelAccessor, blockPos3);
            if (randomSource.nextFloat() > pointedDripstoneConfiguration.chanceOfSpreadRadius3) continue;
            BlockPos blockPos4 = blockPos3.relative(Direction.getRandom(randomSource));
            placeDripstoneBlockIfPossible(levelAccessor, blockPos4);
        }
    }

    protected boolean placeDripstoneBlockIfPossible(LevelAccessor levelAccessor, BlockPos blockPos) {
        BlockState blockState = levelAccessor.getBlockState(blockPos);
        if (true || blockState.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
            if (baseBlock != null)
                levelAccessor.setBlock(blockPos, baseBlock, 2);
            return true;
        } else {
            return false;
        }
    }
}
