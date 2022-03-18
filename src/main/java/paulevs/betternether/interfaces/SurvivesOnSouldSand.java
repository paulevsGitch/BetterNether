package paulevs.betternether.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import ru.bclib.interfaces.SurvivesOnBlocks;

import java.util.List;

public interface SurvivesOnSouldSand extends SurvivesOnBlocks {
    List<Block> GROUND = List.of(Blocks.SOUL_SAND);

    @Override
    default List<Block> getSurvivableBlocks(){
        return GROUND;
    }
}