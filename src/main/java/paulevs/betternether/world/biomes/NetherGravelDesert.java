package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherEntities.KnownSpawnTypes;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureAgave;
import paulevs.betternether.world.structures.plants.StructureBarrelCactus;
import paulevs.betternether.world.structures.plants.StructureNetherCactus;
import paulevs.betternether.world.surface.NetherNoiseCondition;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import ru.bclib.api.surface.SurfaceRuleBuilder;
import ru.bclib.world.biomes.BCLBiomeSettings;
import ru.bclib.world.surface.DoubleBlockSurfaceNoiseCondition;

public class NetherGravelDesert extends NetherBiome {
	public static final SurfaceRules.RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());
	
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
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE());
		}
		
		@Override
		public BiomeSupplier<NetherBiome> getSupplier() {
			return NetherGravelDesert::new;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface()
						.ceil(Blocks.NETHERRACK.defaultBlockState())
						.floor(Blocks.GRAVEL.defaultBlockState())
						.rule(3, SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, GRAVEL))
						.belowFloor(Blocks.GRAVEL.defaultBlockState(), 4, NetherNoiseCondition.DEFAULT)
						//.rule(SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, true, false, CaveSurface.FLOOR), SurfaceRules.ifTrue(NetherNoiseCondition.DEFAULT, GRAVEL)))
				;
		}
		
		@Override
		public <M extends Mob> int spawnWeight(KnownSpawnTypes type) {
			int res = super.spawnWeight(type);
			switch(type){
				case NAGA -> res = 20;
			}
			return res;
		}
	}
	
	public NetherGravelDesert(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
		super(biomeID, biome, settings);
	}
	
	@Override
	protected void onInit(){
		addStructure("nether_cactus", new StructureNetherCactus(), StructureType.FLOOR, 0.02F, true);
		addStructure("agave", new StructureAgave(), StructureType.FLOOR, 0.02F, true);
		addStructure("barrel_cactus", new StructureBarrelCactus(), StructureType.FLOOR, 0.02F, true);
	}
	
	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		//		for (int i = 0; i < 1 + random.nextInt(3); i++) {
		//			BlockPos p2 = pos.below(i);
		//			if (BlocksHelper.isNetherGround(world.getBlockState(p2)))
		//				if (world.isEmptyBlock(p2.below())) {
		//				BlocksHelper.setWithoutUpdate(world, p2, Blocks.NETHERRACK.defaultBlockState());
		//				return;
		//				}
		//				else
		//				BlocksHelper.setWithoutUpdate(world, p2, Blocks.GRAVEL.defaultBlockState());
		//		}
	}
}
