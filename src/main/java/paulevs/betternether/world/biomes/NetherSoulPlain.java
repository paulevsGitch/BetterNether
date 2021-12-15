package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureBlackBush;
import paulevs.betternether.world.structures.plants.StructureSoulGrass;
import paulevs.betternether.world.structures.plants.StructureSoulVein;
import paulevs.betternether.world.surface.NetherNoiseCondition;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;

public class NetherSoulPlain extends NetherBiome {
	private static final SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
	private static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
	private static final SurfaceRules.RuleSource SOUL_SANDSTONE = SurfaceRules.state(NetherBlocks.SOUL_SANDSTONE.defaultBlockState());
	public static final SurfaceRules.ConditionSource NOISE_SOUL_LAYER = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012);
	public static final SurfaceRules.ConditionSource NOISE_NETHER_STATE = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);

	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(196, 113, 239)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY)
				   .particles(ParticleTypes.PORTAL, 0.02F)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_FOSSILE());
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherSoulPlain::new;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super
				.surface()
				.ceil(NetherBlocks.SOUL_SANDSTONE.defaultBlockState())
				.rule(SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, true, false, CaveSurface.FLOOR),
					SurfaceRules.sequence(SurfaceRules.ifTrue(NetherNoiseCondition.DEFAULT, SOUL_SOIL), SOUL_SAND)
				))
				.rule(SurfaceRules.ifTrue(NOISE_NETHER_STATE, SOUL_SOIL))
				.rule(3, SOUL_SANDSTONE)
				;
		}
	}
	
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(245);
	private static final MutableBlockPos POS = new MutableBlockPos();
	
	public NetherSoulPlain(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("soul_vein", new StructureSoulVein(), StructureType.FLOOR, 0.5F, true);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.02F, false);
		addStructure("soul_grass", new StructureSoulGrass(), StructureType.FLOOR, 0.3F, false);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
//		POS.set(pos);
//		int d1 = MHelper.randRange(2, 4, random);
//
//		for (int i = 0; i < d1; i++) {
//			POS.setY(pos.getY() - i);
//			if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
//				if (TERRAIN.eval(pos.getX() * 0.1, pos.getY() * 0.1, pos.getZ() * 0.1) > 0)
//					BlocksHelper.setWithoutUpdate(world, POS, Blocks.SOUL_SOIL.defaultBlockState());
//				else
//				BlocksHelper.setWithoutUpdate(world, POS, Blocks.SOUL_SAND.defaultBlockState());
//			else
//				return;
//		}
//
//		int d2 = MHelper.randRange(5, 7, random);
//		for (int i = d1; i < d2; i++) {
//			POS.setY(pos.getY() - i);
//			if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
//				BlocksHelper.setWithoutUpdate(world, POS, NetherBlocks.SOUL_SANDSTONE.defaultBlockState().setValue(BlockSoulSandstone.UP, i == d1));
//			else
//				return;
//		}
	}
}