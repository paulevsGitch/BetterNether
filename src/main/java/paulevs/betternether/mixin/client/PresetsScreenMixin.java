package paulevs.betternether.mixin.client;

import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.PresetsScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;

@Environment(EnvType.CLIENT)
@Mixin(PresetsScreen.class)
public class PresetsScreenMixin
{
	@Shadow
	private static void addPreset(String name, ItemConvertible icon, Biome biome, List<String> structures, FlatChunkGeneratorLayer... layers) {}
	
	static
	{
		addPreset(I18n.translate("betternether.flat_nether"),
				Blocks.NETHERRACK,
				Biomes.NETHER_WASTES,
				Arrays.asList("decoration", "nether_city"),
				new FlatChunkGeneratorLayer(63, Blocks.NETHERRACK),
				new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
	}
}
