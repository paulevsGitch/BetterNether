package org.betterx.betternether.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.world.structures.piece.CavePiece;
import org.betterx.betternether.world.structures.piece.CityPiece;
import org.betterx.betternether.world.structures.piece.DestructionPiece;

public class NetherStructurePieces {
    public static final StructurePieceType NETHER_CITY_PIECE = register("bncity", CityPiece::new);
    public static final StructurePieceType CAVE_PIECE = register("bncave", CavePiece::new);
    public static final StructurePieceType DESTRUCTION_PIECE = register("bndestr", DestructionPiece::new);
    public static final StructurePieceType ANCHOR_TREE_PIECE = register("anchor_tree", DestructionPiece::new);

    private static StructurePieceType register(String id, StructurePieceType pieceType) {
        return Registry.register(Registry.STRUCTURE_PIECE, BetterNether.makeID(id), pieceType);
    }

    public static void ensureStaticLoad() {

    }
}
