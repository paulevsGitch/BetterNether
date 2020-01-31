package paulevs.betternether.world.structures.piece;

import java.util.Locale;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;

public class StructureTypes
{
	public static final StructurePieceType NETHER_CITY = register(CityPiece::new, "bncity");
	public static final StructurePieceType CAVE = register(CavePiece::new, "bncave");
	
	public static void init()
	{
		
	}
	
	protected static StructurePieceType register(StructurePieceType pieceType, String id)
	{
		return (StructurePieceType)Registry.register(Registry.STRUCTURE_PIECE, (String)id.toLowerCase(Locale.ROOT), pieceType);
	}
}
