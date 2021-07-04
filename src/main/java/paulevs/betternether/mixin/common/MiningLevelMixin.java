package paulevs.betternether.mixin.common;

import net.fabricmc.fabric.impl.object.builder.FabricBlockInternals;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FabricBlockInternals.MiningLevel.class)
public interface MiningLevelMixin {
    @Accessor("tag") Tag<Item> getTag();
    @Accessor("level") int getLevel();
}
