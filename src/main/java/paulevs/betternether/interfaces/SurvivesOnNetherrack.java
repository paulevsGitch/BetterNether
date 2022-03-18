package paulevs.betternether.interfaces;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.interfaces.SurvivesOnTags;

import java.util.List;

public interface SurvivesOnNetherrack extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(CommonBlockTags.NETHERRACK);

    @Override
    default List<TagKey<Block>> getSurvivableTags(){
        return TAGS;
    }
}
