package paulevs.betternether.world.structures.piece;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;

public abstract class CustomPiece extends StructurePiece {
	protected CustomPiece(StructurePieceType type, int i, BlockBox blockBox) {
		super(type, i, blockBox);
	}

	protected CustomPiece(StructurePieceType type, NbtCompound tag) {
		super(type, tag);
	}
}