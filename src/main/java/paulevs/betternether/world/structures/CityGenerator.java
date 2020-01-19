package paulevs.betternether.world.structures;

import java.util.List;
import java.util.Locale;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.VillageGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.VillageFeatureConfig;

public class CityGenerator
{
	public static final StructurePieceType NETHER_CITY = register(VillageGenerator.Piece::new, "BNCity");

	public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random, VillageFeatureConfig config)
	{
		CityData.initialize();
	}

	public static class Piece extends PoolStructurePiece
	{
		public Piece(StructureManager structureManager, StructurePoolElement structurePoolElement, BlockPos blockPos, int i, BlockRotation blockRotation, BlockBox blockBox)
		{
			super(NETHER_CITY, structureManager, structurePoolElement, blockPos, i, blockRotation, blockBox);
		}

		public Piece(StructureManager structureManager, CompoundTag compoundTag)
		{
			super(structureManager, compoundTag, NETHER_CITY);
		}
	}

	static StructurePieceType register(StructurePieceType pieceType, String id)
	{
		return (StructurePieceType)Registry.register(Registry.STRUCTURE_PIECE, (String)id.toLowerCase(Locale.ROOT), pieceType);
	}
}
