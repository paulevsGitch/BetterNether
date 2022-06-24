package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v3.levelgen.features.BlockPredicates;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.features.configured.NetherTerrain;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class NetherTerrainPlaced {
    public static final BCLFeature<SimpleBlockFeature, SimpleBlockConfiguration> MAGMA_BLOBS = NetherTerrain
            .MAGMA_BLOBS
            .place(BN.id("magma_blob"))
            .decoration(GenerationStep.Decoration.LAKES)
            .countRange(1, 2)
            .spreadHorizontal(ClampedNormalInt.of(0, 2, -4, -4))
            .stencil()
            .onlyInBiome()
            .onEveryLayer()
            .offset(Direction.DOWN)
            .is(BlockPredicates.ONLY_GROUND)
            .extendDown(0, 3)
            .buildAndRegister();

    public static BCLFeature LAVA_PITS_SPARSE = NetherTerrain
            .LAVA_PITS
            .place(BN.id("lava_pits_sparse"))
            .decoration(GenerationStep.Decoration.LAKES)
            .onEveryLayer()
            .stencil()
            .findSolidFloor(3)
            .offset(Direction.DOWN)
            .inBasinOf(BlockPredicates.ONLY_GROUND_OR_LAVA)
            .onceEvery(6)
            .onlyInBiome()
            .buildAndRegister();

    public static BCLFeature LAVA_PITS_DENSE = NetherTerrain
            .LAVA_PITS
            .place(BN.id("lava_pits_dense"))
            .decoration(GenerationStep.Decoration.LAKES)
            .onEveryLayer()
            .stencil()
            .findSolidFloor(3)
            .offset(Direction.DOWN)
            .inBasinOf(BlockPredicates.ONLY_GROUND_OR_LAVA)
            .onceEvery(2)
            .onlyInBiome()
            .buildAndRegister();

    public static BCLFeature FLOODED_LAVA_PIT = NetherTerrain
            .FLOODED_LAVA_PIT
            .place()
            .decoration(GenerationStep.Decoration.LAKES)
            .all()
            .onEveryLayer()
            .offset(Direction.DOWN)
            .onlyInBiome()
            .buildAndRegister();
    public static void ensureStaticInitialization() {
    }
}
