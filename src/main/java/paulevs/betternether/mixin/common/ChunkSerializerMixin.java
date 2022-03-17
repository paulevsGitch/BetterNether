package paulevs.betternether.mixin.common;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.Map.Entry;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
	@Overwrite
	private static CompoundTag packStructureData(StructurePieceSerializationContext structurePieceSerializationContext, ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences) {
        CompoundTag tagResult = new CompoundTag();
        CompoundTag tagStarts = new CompoundTag();
        for (Entry<StructureFeature<?>, StructureStart<?>> start : structureStarts.entrySet()) {
            tagStarts.put(start.getKey().getFeatureName(), start.getValue().createTag(structurePieceSerializationContext, pos));
        }
        tagResult.put("starts", tagStarts);
        CompoundTag tagReferences = new CompoundTag();
        for (Entry<StructureFeature<?>, LongSet> feature : structureReferences.entrySet()) {
            // Structures sometimes can be null
			if (feature.getKey() != null) {
                tagReferences.put(feature.getKey().getFeatureName(), new LongArrayTag(feature.getValue()));
            }
        }
        tagResult.put("References", tagReferences);
        return tagResult;
	}
}
