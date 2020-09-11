package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBigWarpedTree;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureTwistedVines;
import paulevs.betternether.structures.plants.StructureWarpedFungus;
import paulevs.betternether.structures.plants.StructureWarpedRoots;

public class OldWarpedWoods extends NetherBiome
{
	public OldWarpedWoods(String name)
	{
		super(new BiomeDefinition(name)
				.setFogColor(26, 5, 26)
				.setLoop(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_WARPED_FOREST_MOOD)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.WARPED_SPORE, 0.025F))
				.addMobSpawn(EntityRegistry.FLYING_PIG, 20, 2, 4));
		addStructure("big_warped_tree", new StructureBigWarpedTree(), StructureType.FLOOR, 0.1F, false);
		addStructure("warped_fungus", new StructureWarpedFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("warped_roots", new StructureWarpedRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("twisted_vine", new StructureTwistedVines(), StructureType.FLOOR, 0.1F, true);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.3F, true);
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		BlocksHelper.setWithoutUpdate(world, pos, Blocks.WARPED_NYLIUM.getDefaultState());
	}
}
