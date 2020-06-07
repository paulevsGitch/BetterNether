package paulevs.betternether.mixin.client;

import java.util.Collections;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.PresetsScreen;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import net.minecraft.world.gen.feature.StructureFeature;

@Environment(EnvType.CLIENT)
@Mixin(PresetsScreen.class)
public class PresetsScreenMixin
{
	@Shadow
	private static void addPreset(Text text, ItemConvertible icon, Biome biome, List<StructureFeature<?>> structures, boolean bl, boolean bl2, boolean bl3, FlatChunkGeneratorLayer... flatChunkGeneratorLayers) {}
	
	static
	{
		addPreset(new TranslatableText("betternether.flat_nether"),
				Blocks.NETHERRACK,
				Biomes.NETHER_WASTES,
				Collections.emptyList(),
				//Arrays.asList("nether_city"),
				false, false, false,
				new FlatChunkGeneratorLayer(63, Blocks.NETHERRACK),
				new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
	}
}
