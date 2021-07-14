package paulevs.betternether.mixin.client;

import java.util.Collections;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(PresetFlatWorldScreen.class)
public class PresetsScreenMixin {
	@Shadow
	private static void preset(Component text, ItemLike icon, ResourceKey<Biome> registryKey, List<StructureFeature<?>> structures, boolean bl, boolean bl2, boolean bl3, FlatLayerInfo... flatChunkGeneratorLayers) {}

	static {
		preset(new TranslatableComponent("betternether.flat_nether"),
				Blocks.NETHERRACK,
				Biomes.NETHER_WASTES,
				Collections.emptyList(),
				false, false, false,
				new FlatLayerInfo(63, Blocks.NETHERRACK),
				new FlatLayerInfo(1, Blocks.BEDROCK));
	}
}
