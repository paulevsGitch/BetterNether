package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.bones.StructureBoneReef;
import paulevs.betternether.structures.decorations.StructureStalactiteCeil;
import paulevs.betternether.structures.decorations.StructureStalactiteFloor;
import paulevs.betternether.structures.plants.StructureGoldenLumabusVine;
import paulevs.betternether.structures.plants.StructureJellyfishMushroom;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSepiaBoneGrass;

public class NetherSulfuricBoneReef extends NetherBiome
{
	public NetherSulfuricBoneReef(String name)
	{
		super(new BiomeDefenition(name)
				.setFogColor(154, 144, 49)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.ASH, 0.01F)), false);
		
		addStructure("bone_stalactite", new StructureStalactiteFloor(BlocksRegistry.BONE_STALACTITE, BlocksRegistry.BONE_BLOCK), StructureType.FLOOR, 0.05F, true);
		
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("bone_reef", new StructureBoneReef(), StructureType.FLOOR, 0.2F, true);
		addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructureType.FLOOR, 0.02F, true);
		addStructure("sulfuric_bone_grass", new StructureSepiaBoneGrass(), StructureType.FLOOR, 0.1F, false);
		
		addStructure("bone_stalagmite", new StructureStalactiteCeil(BlocksRegistry.BONE_STALACTITE, BlocksRegistry.BONE_BLOCK), StructureType.CEIL, 0.05F, true);
		
		addStructure("golden_lumabus_vine", new StructureGoldenLumabusVine(), StructureType.CEIL, 0.3F, true);
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.SEPIA_MUSHROOM_GRASS.getDefaultState());
	}
}
