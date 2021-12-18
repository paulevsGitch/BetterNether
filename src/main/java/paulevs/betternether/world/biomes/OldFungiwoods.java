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
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureGrayMold;
import paulevs.betternether.world.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.world.structures.plants.StructureMedRedMushroom;
import paulevs.betternether.world.structures.plants.StructureOldBrownMushrooms;
import paulevs.betternether.world.structures.plants.StructureOldRedMushrooms;
import paulevs.betternether.world.structures.plants.StructureRedMold;
import paulevs.betternether.world.structures.plants.StructureVanillaMushroom;
import paulevs.betternether.world.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.world.structures.plants.StructureWallRedMushroom;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;

public class OldFungiwoods extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(166, 38, 95)
				   .loop(SoundsRegistry.AMBIENT_MUSHROOM_FOREST)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				   .particles(ParticleTypes.MYCELIUM, 0.1F)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE());
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return OldFungiwoods::new;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface().floor( NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
		}
	}
	
	public OldFungiwoods(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
	}
	
	@Override
	protected void onInit(){
		this.setNoiseDensity(0.5F);
		setGenChance(0.3F);
		
		addStructure("old_red_mushrooms", new StructureOldRedMushrooms(), StructureType.FLOOR, 0.1F, false);
		addStructure("old_brown_mushrooms", new StructureOldBrownMushrooms(), StructureType.FLOOR, 0.1F, false);
		addStructure("large_red_mushroom", new StructureMedRedMushroom(), StructureType.FLOOR, 0.12F, true);
		addStructure("large_brown_mushroom", new StructureMedBrownMushroom(), StructureType.FLOOR, 0.12F, true);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.5F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.9F, true);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.9F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.9F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.9F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		//BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
	}
}
