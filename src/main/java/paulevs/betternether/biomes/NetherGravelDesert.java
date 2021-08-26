package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureAgave;
import paulevs.betternether.structures.plants.StructureBarrelCactus;
import paulevs.betternether.structures.plants.StructureNetherCactus;

public class NetherGravelDesert extends NetherBiome {
	public NetherGravelDesert(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(170, 48, 0)
				.setLoop(SoundsRegistry.AMBIENT_GRAVEL_DESERT)
				.setMood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
				.setAdditions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
				.setMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
				.setParticles(ParticleTypes.ASH, 0.02F));
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
