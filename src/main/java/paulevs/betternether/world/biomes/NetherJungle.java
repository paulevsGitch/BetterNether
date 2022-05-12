package paulevs.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherEntities.KnownSpawnTypes;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.*;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import ru.bclib.api.surface.SurfaceRuleBuilder;
import ru.bclib.world.biomes.BCLBiomeSettings;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class NetherJungle extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(62, 169, 61)
				   .loop(SoundsRegistry.AMBIENT_NETHER_JUNGLE)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_WARPED_FOREST)
					.structure(BiomeTags.HAS_BASTION_REMNANT);
		}
		
		@Override
		public BiomeSupplier<NetherBiome> getSupplier() {
			return NetherJungle::new;
		}
		
		@Override
		public <M extends Mob> int spawnWeight(KnownSpawnTypes type) {
			int res = super.spawnWeight(type);
			switch(type){
				case GHAST, ZOMBIFIED_PIGLIN,MAGMA_CUBE,ENDERMAN,PIGLIN,STRIDER,HOGLIN,PIGLIN_BRUTE -> res = 0;
				case JUNGLE_SKELETON -> res = 40;
			}
			return res;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface().floor(NetherBlocks.JUNGLE_GRASS.defaultBlockState());
		}
	}
	
	public NetherJungle(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
		super(biomeID, biome, settings);
	}
	
	@Override
	protected void onInit(){
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("stalagnate", new StructureStalagnate(), StructureType.FLOOR, 0.2F, false);
		addStructure("rubeus_tree", new StructureRubeus(), StructureType.FLOOR, 0.1F, false);
		addStructure("bush_rubeus", new StructureRubeusBush(), StructureType.FLOOR, 0.1F, false);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.5F, false);
		addStructure("egg_plant", new StructureEggPlant(), StructureType.FLOOR, 0.05F, true);
		addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructureType.FLOOR, 0.03F, true);
		addStructure("feather_fern", new StructureFeatherFern(), StructureType.FLOOR, 0.05F, true);
		addStructure("jungle_plant", new StructureJunglePlant(), StructureType.FLOOR, 0.1F, false);
		addStructure("lucis", new StructureLucis(), StructureType.WALL, 0.1F, false);
		addStructure("eye", new StructureEye(), StructureType.CEIL, 0.1F, true);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.1F, true);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.1F, true);
		addStructure("flowered_vine", new StructureBloomingVine(), StructureType.CEIL, 0.1F, true);
		addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.2F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);

		addStructures(structureFormat("ruined_temple", -4, StructureType.FLOOR, 10F));
		addStructures(structureFormat("jungle_temple_altar", -2, StructureType.FLOOR, 10F));
		addStructures(structureFormat("jungle_temple_2", -2, StructureType.FLOOR, 10F));

		addStructures(structureFormat("jungle_bones_1", 0, StructureType.FLOOR, 20F));
		addStructures(structureFormat("jungle_bones_2", 0, StructureType.FLOOR, 20F));
		addStructures(structureFormat("jungle_bones_3", 0, StructureType.FLOOR, 20F));

		this.setNoiseDensity(0.5F);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
		//BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.JUNGLE_GRASS.defaultBlockState());
	}
}