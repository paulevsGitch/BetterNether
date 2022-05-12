package paulevs.betternether.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
@Mixin(PresetFlatWorldScreen.class)
public abstract class PresetsScreenMixin {
	@Shadow
	private static void preset(
			Component component,
			ItemLike itemLike,
			ResourceKey<Biome> resourceKey,
			Set<ResourceKey<StructureSet>> set,
			boolean bl,
			boolean bl2,
			FlatLayerInfo... flatLayerInfos) {

	}

	static {
		preset(Component.translatable("betternether.flat_nether"),
				Blocks.NETHERRACK,
				Biomes.NETHER_WASTES,
				Collections.emptySet(),
				false, false,
				new FlatLayerInfo(63, Blocks.NETHERRACK),
				new FlatLayerInfo(1, Blocks.BEDROCK));
	}}
