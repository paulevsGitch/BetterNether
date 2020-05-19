package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.TheNetherDimension;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import paulevs.betternether.world.NetherBiomeSource;
import paulevs.betternether.world.NetherBiomeSourceConfig;

@Mixin(TheNetherDimension.class)
public abstract class TheNetherDimensionMixin extends Dimension
{
	public TheNetherDimensionMixin(World world, DimensionType type, float f)
	{
		super(world, type, f);
	}

	@Inject(method = "createChunkGenerator", at = @At("HEAD"), cancellable = true)
	private void makeChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> info)
	{
		CavesChunkGeneratorConfig cavesChunkGeneratorConfig = (CavesChunkGeneratorConfig) ChunkGeneratorType.CAVES.createConfig();
		cavesChunkGeneratorConfig.setDefaultBlock(Blocks.NETHERRACK.getDefaultState());
		cavesChunkGeneratorConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());
		NetherBiomeSourceConfig biomeConfig = new NetherBiomeSourceConfig(world);
		info.setReturnValue(ChunkGeneratorType.CAVES.create(world, new NetherBiomeSource(biomeConfig), cavesChunkGeneratorConfig));
		info.cancel();
	}
}