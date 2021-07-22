package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockSoulSandstone;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.decorations.StructureWartDeadwood;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureSoulLily;
import paulevs.betternether.structures.plants.StructureWartSeed;
import paulevs.betternether.structures.plants.StructureWartTree;

public class NetherWartForest extends NetherBiome {
	private static final MutableBlockPos POS = new MutableBlockPos();

	public NetherWartForest(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(151, 6, 6)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				.setParticleConfig(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.05F))
				.addMobSpawn(EntityRegistry.FLYING_PIG, 20, 2, 4));
		this.setNoiseDensity(0.45F);
		addStructure("wart_deadwood", new StructureWartDeadwood(), StructureType.FLOOR, 0.02F, false);
		addStructure("wart_tree", new StructureWartTree(), StructureType.FLOOR, 0.1F, false);
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.2F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.05F, false);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.05F, false);
		addStructure("soul_lily", new StructureSoulLily(), StructureType.FLOOR, 0.2F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		switch (random.nextInt(4)) {
			case 0:
				super.genSurfColumn(world, pos, random);
				break;
			case 1:
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
				break;
			case 2:
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
				break;
			case 3:
				BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
				break;
		}

		int d1 = MHelper.randRange(2, 4, random);
		POS.setX(pos.getX());
		POS.setZ(pos.getZ());

		for (int i = 1; i < d1; i++) {
			POS.setY(pos.getY() - i);
			if (BlocksHelper.isNetherGround(world.getBlockState(POS))) {
				switch (random.nextInt(3)) {
					case 0:
						BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
						break;
					case 1:
						BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
						break;
					case 2:
						BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHERRACK.defaultBlockState());
						break;
				}
			}
			else
				return;
		}

		int d2 = MHelper.randRange(5, 7, random);
		for (int i = d1; i < d2; i++) {
			POS.setY(pos.getY() - i);
			if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
				BlocksHelper.setWithoutUpdate(world, POS, NetherBlocks.SOUL_SANDSTONE.defaultBlockState().setValue(BlockSoulSandstone.UP, i == d1));
			else
				return;
		}
	}
}
