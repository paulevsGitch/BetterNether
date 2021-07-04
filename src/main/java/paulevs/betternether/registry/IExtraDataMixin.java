package paulevs.betternether.registry;

import java.util.List;

import paulevs.betternether.mixin.common.MiningLevelMixin;

public interface IExtraDataMixin {
    public List<MiningLevelMixin> getTags();
}
