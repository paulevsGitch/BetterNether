package paulevs.betternether.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.interfaces.SurvivesOnBlocks;

import java.util.List;

public interface SurvivesOnBoneBlocks extends SurvivesOnBlocks {
    List<Block> BONE_BLOCKS = List.of(Blocks.BONE_BLOCK, NetherBlocks.BONE_BLOCK);

    @Override
    default List<Block> getSurvivableBlocks() { return BONE_BLOCKS; }
}
