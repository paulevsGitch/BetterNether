package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBigWarpedTree;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureTwistedVines;
import paulevs.betternether.structures.plants.StructureWarpedFungus;
import paulevs.betternether.structures.plants.StructureWarpedRoots;

public class OldWarpedWoods extends NetherBiome {
	public OldWarpedWoods(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(26, 5, 26)
				.setLoop(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_WARPED_FOREST_MOOD)
				.setParticles(ParticleTypes.WARPED_SPORE, 0.025F)
				.setDefaultMobs(false)
				.addMobSpawn(EntityType.ENDERMAN, 1, 4, 4)
				.addMobSpawn(EntityType.STRIDER, 60, 1, 2));
		addStructure("big_warped_tree", new StructureBigWarpedTree(), StructureType.FLOOR, 0.1F, false);
		addStructure("warped_fungus", new StructureWarpedFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("warped_roots", new StructureWarpedRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("twisted_vine", new StructureTwistedVines(), StructureType.FLOOR, 0.1F, true);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.3F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		BlocksHelper.setWithoutUpdate(world, pos, Blocks.WARPED_NYLIUM.defaultBlockState());
	}
}
