package paulevs.betternether.mixin.common;


import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FlatLevelGeneratorPresetTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FlatLevelGeneratorPresetTags;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.registry.FlatLevelPresets;

@Mixin(FlatLevelGeneratorPresetTagsProvider.class)
public abstract class FlatLevelGeneratorPresetTagsProviderMixin extends TagsProvider<FlatLevelGeneratorPreset> {
    protected FlatLevelGeneratorPresetTagsProviderMixin(DataGenerator dataGenerator,
                                                        Registry<FlatLevelGeneratorPreset> registry) {
        super(dataGenerator, registry);
    }

    @Inject(method="addTags", at=@At("HEAD"))
    void bcl_addTags(CallbackInfo ci){
        this.tag(FlatLevelGeneratorPresetTags.VISIBLE).add(new ResourceKey[]{FlatLevelPresets.BN_FLAT});
    }
}
