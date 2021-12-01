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
import paulevs.betternether.structures.plants.StructureGiantMold;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureLucis;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;
import paulevs.betternether.structures.plants.StructureMushroomFir;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureVanillaMushroom;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class NetherMushroomForestData extends NetherBiomeData {
	@Override
	protected void addCustomBuildData(BCLBiomeBuilder builder){
		builder
			.fogColor(166, 38, 95)
			.loop(SoundsRegistry.AMBIENT_MUSHROOM_FOREST)
			.additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
			.mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
			.music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
			.particles(ParticleTypes.MYCELIUM, 0.1F);
	}
	
	public NetherMushroomForestData(String name) {
		super(name);
		
		this.setNoiseDensity(0.5F);
		
		addStructure("large_red_mushroom", new StructureMedRedMushroom(), StructureType.FLOOR, 0.12F, true);
		addStructure("large_brown_mushroom", new StructureMedBrownMushroom(), StructureType.FLOOR, 0.12F, true);
		addStructure("giant_mold", new StructureGiantMold(), StructureType.FLOOR, 0.12F, true);
		addStructure("mushroom_fir", new StructureMushroomFir(), StructureType.FLOOR, 0.2F, true);
		addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR, 0.1F, false);
		addStructure("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR, 0.05F, true);
		addStructure("red_mold", new StructureRedMold(), StructureType.FLOOR, 0.5F, true);
		addStructure("gray_mold", new StructureGrayMold(), StructureType.FLOOR, 0.5F, true);
		addStructure("lucis", new StructureLucis(), StructureType.WALL, 0.05F, false);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
	}
}
