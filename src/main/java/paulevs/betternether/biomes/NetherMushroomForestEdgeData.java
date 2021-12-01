package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureVanillaMushroom;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class NetherMushroomForestEdgeData extends NetherBiomeData {
	@Override
	protected void addCustomBuildData(BCLBiomeBuilder builder){
		builder
			.fogColor(200, 121, 157)
			.loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
			.additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
			.mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
			.music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST);
	}
	
	public NetherMushroomForestEdgeData(String name) {
		super(name);
		
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.05F, true);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.5F, false);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.5F, false);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (random.nextInt(4) > 0)
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
		else if (random.nextBoolean())
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
	}
}