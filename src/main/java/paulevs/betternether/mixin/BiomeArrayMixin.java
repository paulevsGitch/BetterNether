package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;
import paulevs.betternether.IBiomeArray;

@Mixin(BiomeArray.class)
public class BiomeArrayMixin implements IBiomeArray
{
	@Shadow
	@Final
	private static int HORIZONTAL_SECTION_COUNT;
	
	@Shadow
	@Final
	private static int VERTICAL_SECTION_COUNT;

	@Shadow
	@Final
	public static int HORIZONTAL_BIT_MASK;
	
	@Shadow
	@Final
	public static int VERTICAL_BIT_MASK;

	@Shadow
	@Final
	private Biome[] data;

	@Override
	public void setBiome(int x, int y, int z, Biome biome)
	{
		int i = x & HORIZONTAL_BIT_MASK;
		int j = MathHelper.clamp(y, 0, VERTICAL_BIT_MASK);
		int k = z & HORIZONTAL_BIT_MASK;
		data[j << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | k << HORIZONTAL_SECTION_COUNT | i] = biome;
	}
}
