package paulevs.betternether.interfaces;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import paulevs.betternether.registry.NetherTags;
import ru.bclib.interfaces.SurvivesOnTags;

import java.util.List;

public interface SurvivesOnNetherSand  extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of( NetherTags.NETHER_SAND);

    @Override
    default List<TagKey<Block>> getSurvivableTags(){
        return TAGS;
    }
}
