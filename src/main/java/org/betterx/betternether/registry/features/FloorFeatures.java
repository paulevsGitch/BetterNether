package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;

import java.util.List;

public class FloorFeatures {
    public static final BCLFeature BONE_GRASS_PATCH
            = FastFeatures.patch(BetterNether.makeID("bone_grass"), NetherBlocks.BONE_GRASS);
    public static final BCLFeature FEATHER_FERN_PATCH
            = FastFeatures.patch(BetterNether.makeID("feather_fern"), NetherBlocks.FEATHER_FERN);

    public static final BCLFeature JELLYFISH_MUSHROOM
            = FastFeatures.patch(BetterNether.makeID("jellyfish_mushroom"), 64, 5, 3, new JellyfishMushroomFeature());

    public static final BCLFeature BONES = BCLFeatureBuilder
            .start(BetterNether.makeID("bones"), BCLFeature.TEMPLATE)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("bone_01"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_02"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_03"), 0, StructurePlacementType.FLOOR, 1.0f)
            )));


    public static void ensureStaticInitialization() {
    }

    static BCLFeature sizedCluster(ResourceLocation location,
                                   boolean onFloor,
                                   boolean sparse,
                                   ScatterFeatureConfig.Builder builder) {
        return FastFeatures.scatter(location, onFloor, sparse, builder, NetherFeatures.SCATTER_WITH_SIZE);
    }

    static StructureWorldNBT cfg(ResourceLocation location,
                                 int offsetY,
                                 StructurePlacementType type,
                                 float chance) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }
}
