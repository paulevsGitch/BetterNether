package paulevs.betternether.world.biomes;

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
import paulevs.betternether.registry.NetherFeatures;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureBigWarpedTree;
import paulevs.betternether.world.structures.plants.StructureBlackVine;
import paulevs.betternether.world.structures.plants.StructureTwistedVines;
import paulevs.betternether.world.structures.plants.StructureWarpedFungus;
import paulevs.betternether.world.structures.plants.StructureWarpedRoots;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;

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
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE())
			       .feature(NetherFeatures.NETHER_RUBY_ORE);
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return OldWarpedWoods::new;
		}
		
		@Override
		public boolean spawnVanillaMobs() {
			return false;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface().floor(Blocks.WARPED_NYLIUM.defaultBlockState());
		}
	}
	
	public OldWarpedWoods(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
	}
	
	@Override
	protected void onInit(){
		addStructure("big_warped_tree", new StructureBigWarpedTree(), StructureType.FLOOR, 0.1F, false);
		addStructure("warped_fungus", new StructureWarpedFungus(), StructureType.FLOOR, 0.05F, true);
		addStructure("warped_roots", new StructureWarpedRoots(), StructureType.FLOOR, 0.2F, true);
		addStructure("twisted_vine", new StructureTwistedVines(), StructureType.FLOOR, 0.1F, true);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.3F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		//BlocksHelper.setWithoutUpdate(world, pos, Blocks.WARPED_NYLIUM.defaultBlockState());
	}
}
