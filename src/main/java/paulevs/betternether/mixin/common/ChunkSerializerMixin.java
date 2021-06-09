package paulevs.betternether.mixin.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtLongArray;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.gen.feature.StructureFeature;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
	@Overwrite
	private static NbtCompound writeStructures(ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences) {
		NbtCompound tagResult = new NbtCompound();
		NbtCompound tagStarts = new NbtCompound();
		Iterator<Entry<StructureFeature<?>, StructureStart<?>>> startsIterator = structureStarts.entrySet().iterator();

		while (startsIterator.hasNext()) {
			Entry<StructureFeature<?>, StructureStart<?>> start = startsIterator.next();
			tagStarts.put((start.getKey()).getName(), (start.getValue()).toNbt(pos.x, pos.z));
		}

		tagResult.put("Starts", tagStarts);
		NbtCompound tagReferences = new NbtCompound();
		Iterator<Entry<StructureFeature<?>, LongSet>> refIterator = structureReferences.entrySet().iterator();

		while (refIterator.hasNext()) {
			Entry<StructureFeature<?>, LongSet> feature = refIterator.next();
			// Structures sometimes can be null
			if (feature.getKey() != null) {
				tagReferences.put((feature.getKey()).getName(), new NbtLongArray(feature.getValue()));
			}
		}

		tagResult.put("References", tagReferences);
		return tagResult;
	}
}
