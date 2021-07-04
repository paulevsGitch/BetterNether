package paulevs.betternether.mixin.common;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.impl.object.builder.FabricBlockInternals;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import paulevs.betternether.registry.IExtraDataMixin;

@Mixin(FabricBlockInternals.ExtraData.class)
public abstract class ExtraDataMixin implements IExtraDataMixin {
    @Shadow @Final private List<FabricBlockInternals.MiningLevel> miningLevels;

    public List<MiningLevelMixin> getTags(){
        List<MiningLevelMixin> list = new ArrayList<>(miningLevels.size());
        for(FabricBlockInternals.MiningLevel l : miningLevels){
            list.add(((MiningLevelMixin)(Object)l));
        }
        return list;
    }
}
