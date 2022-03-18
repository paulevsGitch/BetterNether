package paulevs.betternether.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import ru.bclib.interfaces.SurvivesOnBlocks;

import java.util.List;

public interface SurvivesOnMagmaBlock extends SurvivesOnBlocks {
    List<Block> GROUND = List.of(Blocks.MAGMA_BLOCK);

    @Override
    default List<Block> getSurvivableBlocks(){
        return GROUND;
    }
}
