package paulevs.betternether.world.structures;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StructureGeneratorThreadContext {
    public final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();
    public final BlockPos.MutableBlockPos POS2 = new BlockPos.MutableBlockPos();
    public final Set<BlockPos> POINTS = new HashSet<BlockPos>();
    public final Set<BlockPos> MIDDLE = new HashSet<BlockPos>();
    public final Set<BlockPos> TOP = new HashSet<BlockPos>();
    public final Map<BlockPos, Byte> LOGS_DIST = new HashMap<>(1024);
    public final Set<BlockPos> BLOCKS = new HashSet<BlockPos>(2048);
    public final boolean[][][] MASK = new boolean[16][24][16];

    public void clear(){
        POINTS.clear();
        MIDDLE.clear();
        TOP.clear();
        BLOCKS.clear();
        LOGS_DIST.clear();
    }
}
