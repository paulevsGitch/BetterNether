package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.registry.NetherBlocks;

import java.util.Random;

//make some ruined portals blue
@Mixin(BlockAgeProcessor.class)
public class BlockAgeProcessorMixin {
	@Inject(method="processBlock", at=@At(value="HEAD"), cancellable = true)
	void bn_processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos2, StructureBlockInfo structureBlockInfo, StructureBlockInfo structureBlockInfo2, StructurePlaceSettings structurePlaceSettings, CallbackInfoReturnable<StructureBlockInfo> cir){
		final boolean makeBlue = (blockPos.getX() + blockPos.getZ())%3 == 0 ;
		
		if (makeBlue && structureBlockInfo2.state.is(Blocks.OBSIDIAN)){
			final BlockPos structurePos = structureBlockInfo2.pos;
			final Random random = structurePlaceSettings.getRandom(structurePos);
			
			Block block = random.nextFloat() < 0.15F ? NetherBlocks.BLUE_CRYING_OBSIDIAN : NetherBlocks.BLUE_OBSIDIAN;
			cir.setReturnValue(new StructureTemplate.StructureBlockInfo(structurePos, block.defaultBlockState(), structureBlockInfo2.nbt));
			cir.cancel();
			return;
		}
	}
}
