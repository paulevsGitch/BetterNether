package paulevs.betternether.world.structures.piece;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;

public class StructureTypes
{
	public static final StructurePieceType NETHER_CITY = register(CityPiece::new, "bncity");
	public static final StructurePieceType CAVE = register(CavePiece::new, "bncave");
	public static final StructurePieceType DESTRUCTION = register(DestructionPiece::new, "bndestr");
	
	public static void init() {}
	
	protected static StructurePieceType register(StructurePieceType pieceType, String id)
	{
		return Registry.register(Registry.STRUCTURE_PIECE, new Identifier(BetterNether.MOD_ID, id), pieceType);
	}
}
