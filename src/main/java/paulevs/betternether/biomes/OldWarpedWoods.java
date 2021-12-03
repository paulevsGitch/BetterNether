package paulevs.betternether.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBigWarpedTree;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureTwistedVines;
import paulevs.betternether.structures.plants.StructureWarpedFungus;
import paulevs.betternether.structures.plants.StructureWarpedRoots;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.world.biomes.BCLBiome;

public class OldWarpedWoods extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(26, 5, 26)
				   .loop(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_WARPED_FOREST_MOOD)
				   .particles(ParticleTypes.WARPED_SPORE, 0.025F)
				   .spawn(EntityType.ENDERMAN, 1, 4, 4)
				   .spawn(EntityType.STRIDER, 60, 1, 2)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT());;
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return OldWarpedWoods::new;
		}
		
		@Override
		public boolean spawnVanillaMobs() {
			return false;
		}
	}
	
	public OldWarpedWoods(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
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
