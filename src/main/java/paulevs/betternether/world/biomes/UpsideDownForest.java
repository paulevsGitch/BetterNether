package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.decorations.StructureForestLitter;
import paulevs.betternether.world.structures.plants.StructureAnchorTree;
import paulevs.betternether.world.structures.plants.StructureAnchorTreeBranch;
import paulevs.betternether.world.structures.plants.StructureAnchorTreeRoot;
import paulevs.betternether.world.structures.plants.StructureCeilingMushrooms;
import paulevs.betternether.world.structures.plants.StructureHookMushroom;
import paulevs.betternether.world.structures.plants.StructureJungleMoss;
import paulevs.betternether.world.structures.plants.StructureMossCover;
import paulevs.betternether.world.structures.plants.StructureNeonEquisetum;
import paulevs.betternether.world.structures.plants.StructureNetherSakura;
import paulevs.betternether.world.structures.plants.StructureNetherSakuraBush;
import paulevs.betternether.world.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.world.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.world.structures.plants.StructureWhisperingGourd;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.SurfaceRuleBuilder;
import ru.bclib.api.surface.rules.SurfaceNoiseCondition;
import ru.bclib.api.surface.rules.SwitchRuleSource;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class UpsideDownForest extends NetherBiome {
	private static final SurfaceRules.RuleSource CEILEING_MOSS = SurfaceRules.state(NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
	private static final SurfaceRules.RuleSource NETHERRACK_MOSS = SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
	private static final SurfaceRules.ConditionSource NOISE_CEIL_LAYER = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0);
	private static final SurfaceRules.ConditionSource NOISE_FLOOR_LAYER = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17);
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(111, 188, 111)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST);
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return UpsideDownForest::new;
		}
		
		@Override
		public boolean vertical() {
			return true;
		}
		
		@Override
		public SurfaceRuleBuilder surface() {
			return super.surface().rule(3,
				SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
					SurfaceRules.sequence(SurfaceRules.ifTrue(NOISE_CEIL_LAYER, CEILEING_MOSS), NETHERRACK)
				)
			).rule(2,
			   SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				   SurfaceRules.sequence(SurfaceRules.ifTrue(new SurfaceNoiseCondition(){
					   @Override
					   public boolean test(SurfaceRulesContextAccessor context) {
						   return MHelper.RANDOM.nextInt(3) == 0 ;
					   }
				   }, NETHERRACK_MOSS), NETHERRACK)
			   )
			);
		}
	}
	
	@Override
	public boolean hasStalactites() {
		return false;
	}
	
	@Override
	public boolean hasBNStructures() {
		return false;
	}
	
	public UpsideDownForest(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		this.setNoiseDensity(0.5F);
		
		addStructure("anchor_tree", new StructureAnchorTree(), StructureType.CEIL, 0.2F, false);
		addStructure("anchor_tree_root", new StructureAnchorTreeRoot(), StructureType.CEIL, 0.03F, false);
		addStructure("anchor_tree_branch", new StructureAnchorTreeBranch(), StructureType.CEIL, 0.02F, true);
		addStructure("nether_sakura", new StructureNetherSakura(), StructureType.CEIL, 0.01F, true);
		addStructure("nether_sakura_bush", new StructureNetherSakuraBush(), StructureType.FLOOR, 0.01F, true);
		addStructure("moss_cover", new StructureMossCover(), StructureType.FLOOR, 0.6F, false);
		addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.4F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.4F, true);
		addStructure("forest_litter", new StructureForestLitter(), StructureType.FLOOR, 0.1F, false);
		addStructure("ceiling_mushrooms", new StructureCeilingMushrooms(), StructureType.CEIL, 1F, false);
		addStructure("neon_equisetum", new StructureNeonEquisetum(), StructureType.CEIL, 0.1F, true);
		addStructure("hook_mushroom", new StructureHookMushroom(), StructureType.CEIL, 0.03F, true);
		addStructure("whispering_gourd", new StructureWhisperingGourd(), StructureType.CEIL, 0.02F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		//BlocksHelper.setWithoutUpdate(world, pos, random.nextInt(3) == 0 ? NetherBlocks.NETHERRACK_MOSS.defaultBlockState() : Blocks.NETHERRACK.defaultBlockState());
	}
}
