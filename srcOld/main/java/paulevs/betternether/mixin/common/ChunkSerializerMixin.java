package paulevs.betternether.mixin.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.gen.feature.StructureFeature;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
	@Overwrite
	private static CompoundTag writeStructures(ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences) {
		CompoundTag tagResult = new CompoundTag();
		CompoundTag tagStarts = new CompoundTag();
		Iterator<Entry<StructureFeature<?>, StructureStart<?>>> startsIterator = structureStarts.entrySet().iterator();

		while (startsIterator.hasNext()) {
			Entry<StructureFeature<?>, StructureStart<?>> start = startsIterator.next();
			tagStarts.put((start.getKey()).getName(), (start.getValue()).toTag(pos.x, pos.z));
		}

		tagResult.put("Starts", tagStarts);
		CompoundTag tagReferences = new CompoundTag();
		Iterator<Entry<StructureFeature<?>, LongSet>> refIterator = structureReferences.entrySet().iterator();

		while (refIterator.hasNext()) {
			Entry<StructureFeature<?>, LongSet> feature = refIterator.next();
			// Structures sometimes can be null
			if (feature.getKey() != null) {
				tagReferences.put((feature.getKey()).getName(), new LongArrayTag(feature.getValue()));
			}
		}

		tagResult.put("References", tagReferences);
		return tagResult;
	}
}
