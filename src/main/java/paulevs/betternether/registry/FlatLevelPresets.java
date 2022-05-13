package paulevs.betternether.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FlatLevelGeneratorPresetTags;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;

import paulevs.betternether.BetterNether;
import ru.bclib.BCLib;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.api.tag.TagType;

public class FlatLevelPresets {
    public static TagType.Simple<FlatLevelGeneratorPreset> FLAT_LEVEL_PRESETS = TagAPI.registerType(Registry.FLAT_LEVEL_GENERATOR_PRESET_REGISTRY, "tags/worldgen/flat_level_generator_preset", (b)->null);

    public static final ResourceKey<FlatLevelGeneratorPreset> BN_FLAT = bcl_register("flat");


    private static ResourceKey<FlatLevelGeneratorPreset> bcl_register(String string) {
        return ResourceKey.create(Registry.FLAT_LEVEL_GENERATOR_PRESET_REGISTRY, BetterNether.makeID(string));
    }

    public static void register(){
        FLAT_LEVEL_PRESETS.addUntyped(FlatLevelGeneratorPresetTags.VISIBLE, BN_FLAT.location());
    }
}
