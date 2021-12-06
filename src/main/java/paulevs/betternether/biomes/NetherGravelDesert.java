package paulevs.betternether.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureAgave;
import paulevs.betternether.structures.plants.StructureBarrelCactus;
import paulevs.betternether.structures.plants.StructureNetherCactus;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.world.biomes.BCLBiome;

public class NetherGravelDesert extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(170, 48, 0)
				   .mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
				   .loop(SoundsRegistry.AMBIENT_GRAVEL_DESERT)
				   .additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
				   .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
				   .particles(ParticleTypes.ASH, 0.02F)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT());;
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherGravelDesert::new;
		}
	}
	
	public NetherGravelDesert(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("nether_cactus", new StructureNetherCactus(), StructureType.FLOOR, 0.02F, true);
		addStructure("agave", new StructureAgave(), StructureType.FLOOR, 0.02F, true);
		addStructure("barrel_cactus", new StructureBarrelCactus(), StructureType.FLOOR, 0.02F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		for (int i = 0; i < 1 + random.nextInt(3); i++) {
			BlockPos p2 = pos.below(i);
			if (BlocksHelper.isNetherGround(world.getBlockState(p2)))
				if (world.isEmptyBlock(p2.below())) {
				BlocksHelper.setWithoutUpdate(world, p2, Blocks.NETHERRACK.defaultBlockState());
				return;
				}
				else
				BlocksHelper.setWithoutUpdate(world, p2, Blocks.GRAVEL.defaultBlockState());
		}
	}
}
