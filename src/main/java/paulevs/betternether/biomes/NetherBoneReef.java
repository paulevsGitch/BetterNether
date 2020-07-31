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
import paulevs.betternether.structures.plants.StructureBoneGrass;
import paulevs.betternether.structures.plants.StructureFeatherFern;
import paulevs.betternether.structures.plants.StructureJellyfishMushroom;
import paulevs.betternether.structures.plants.StructureLumabusVine;
import paulevs.betternether.structures.plants.StructureReeds;

public class NetherBoneReef extends NetherBiome
{
	public NetherBoneReef(String name)
	{
		super(new BiomeDefenition(name)
				.setFogColor(47, 221, 202)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.WARPED_SPORE, 0.01F)), false);
		
		addStructure("bone_stalactite", new StructureStalactiteFloor(BlocksRegistry.BONE_STALACTITE, BlocksRegistry.BONE_BLOCK), StructureType.FLOOR, 0.05F, true);
		
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("bone_reef", new StructureBoneReef(), StructureType.FLOOR, 0.2F, true);
		addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructureType.FLOOR, 0.02F, true);
		addStructure("feather_fern", new StructureFeatherFern(), StructureType.FLOOR, 0.05F, true);
		addStructure("bone_grass", new StructureBoneGrass(), StructureType.FLOOR, 0.1F, false);
		
		addStructure("bone_stalagmite", new StructureStalactiteCeil(BlocksRegistry.BONE_STALACTITE, BlocksRegistry.BONE_BLOCK), StructureType.CEIL, 0.05F, true);
		
		addStructure("lumabus_vine", new StructureLumabusVine(), StructureType.CEIL, 0.3F, true);
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.MUSHROOM_GRASS.getDefaultState());
	}
}
