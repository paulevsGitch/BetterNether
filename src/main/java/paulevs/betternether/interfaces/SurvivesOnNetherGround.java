package paulevs.betternether.interfaces;

import net.minecraft.locale.Language;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import ru.bclib.interfaces.SurvivesOnSpecialGround;

public interface SurvivesOnNetherGround extends SurvivesOnSpecialGround {
    @Override
    default String getSurvivableBlocksString(){
        return Language.getInstance().getOrDefault("betternether.survive.nethergound");
    }

    @Override
    default boolean isSurvivable(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }
}
