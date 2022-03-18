package paulevs.betternether.interfaces;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.interfaces.SurvivesOnTags;

import java.util.List;

public interface SurvivesOnSouldGround  extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of( CommonBlockTags.SOUL_GROUND);

    @Override
    default List<TagKey<Block>> getSurvivableTags(){
        return TAGS;
    }
}
