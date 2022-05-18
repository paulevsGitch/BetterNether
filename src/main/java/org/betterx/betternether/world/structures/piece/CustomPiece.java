package org.betterx.betternether.world.structures.piece;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public abstract class CustomPiece extends StructurePiece {
    protected CustomPiece(StructurePieceType type, int i, BoundingBox blockBox) {
        super(type, i, blockBox);
    }

    protected CustomPiece(StructurePieceType type, CompoundTag tag) {
        super(type, tag);
    }
}