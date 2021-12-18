package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.bones.StructureBoneReef;
import paulevs.betternether.world.structures.decorations.StructureStalactiteCeil;
import paulevs.betternether.world.structures.decorations.StructureStalactiteFloor;
import paulevs.betternether.world.structures.plants.StructureBoneGrass;
import paulevs.betternether.world.structures.plants.StructureFeatherFern;
import paulevs.betternether.world.structures.plants.StructureJellyfishMushroom;
import paulevs.betternether.world.structures.plants.StructureLumabusVine;
import paulevs.betternether.world.structures.plants.StructureReeds;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;

public class NetherBoneReef extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(47, 221, 202)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .particles(ParticleTypes.WARPED_SPORE, 0.01F)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE());
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherBoneReef::new;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface().floor(NetherBlocks.MUSHROOM_GRASS.defaultBlockState());
		}
	}
	
	@Override
	public boolean hasStalactites() {
		return false;
	}
	
	public NetherBoneReef(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
	}
	
	@Override
	protected void onInit(){
		addStructure("bone_stalactite", new StructureStalactiteFloor(NetherBlocks.BONE_STALACTITE, NetherBlocks.BONE_BLOCK), StructureType.FLOOR, 0.05F, true);

		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("bone_reef", new StructureBoneReef(), StructureType.FLOOR, 0.2F, true);
		addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructureType.FLOOR, 0.02F, true);
		addStructure("feather_fern", new StructureFeatherFern(), StructureType.FLOOR, 0.05F, true);
		addStructure("bone_grass", new StructureBoneGrass(), StructureType.FLOOR, 0.1F, false);

		addStructure("bone_stalagmite", new StructureStalactiteCeil(NetherBlocks.BONE_STALACTITE, NetherBlocks.BONE_BLOCK), StructureType.CEIL, 0.05F, true);

		addStructure("lumabus_vine", new StructureLumabusVine(), StructureType.CEIL, 0.3F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		//BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.MUSHROOM_GRASS.defaultBlockState());
	}
}
