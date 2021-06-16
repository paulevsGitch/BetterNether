package paulevs.betternether.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureAgave;
import paulevs.betternether.structures.plants.StructureBarrelCactus;
import paulevs.betternether.structures.plants.StructureNetherCactus;

import java.util.Random;

public class NetherGravelDesert extends NetherBiome {
	public NetherGravelDesert(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(170, 48, 0)
				.setLoop(SoundsRegistry.AMBIENT_GRAVEL_DESERT)
				.setMood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
				.setAdditions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
				.setMusic(SoundEvents.MUSIC_NETHER_NETHER_WASTES)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.ASH, 0.02F)));
		addStructure("nether_cactus", new StructureNetherCactus(), StructureType.FLOOR, 0.02F, true);
		addStructure("agave", new StructureAgave(), StructureType.FLOOR, 0.02F, true);
		addStructure("barrel_cactus", new StructureBarrelCactus(), StructureType.FLOOR, 0.02F, true);
	}

	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random) {
		for (int i = 0; i < 1 + random.nextInt(3); i++) {
			BlockPos p2 = pos.down(i);
			if (BlocksHelper.isNetherGround(world.getBlockState(p2)))
				if (world.isAir(p2.down())) {
				BlocksHelper.setWithoutUpdate(world, p2, Blocks.NETHERRACK.getDefaultState());
				return;
				}
				else
				BlocksHelper.setWithoutUpdate(world, p2, Blocks.GRAVEL.getDefaultState());
		}
	}
}
