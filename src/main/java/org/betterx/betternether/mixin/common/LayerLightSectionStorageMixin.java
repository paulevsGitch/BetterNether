/**
 * package paulevs.betternether.mixin.common;
 * <p>
 * import net.minecraft.core.BlockPos;
 * import net.minecraft.core.SectionPos;
 * import net.minecraft.world.level.chunk.DataLayer;
 * import net.minecraft.world.level.lighting.LayerLightSectionStorage;
 * import org.spongepowered.asm.mixin.Mixin;
 * import org.spongepowered.asm.mixin.Shadow;
 * import org.spongepowered.asm.mixin.injection.At;
 * import org.spongepowered.asm.mixin.injection.Inject;
 * import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
 *
 * @Mixin(LayerLightSectionStorage.class) public class LayerLightSectionStorageMixin {
 * @Shadow protected DataLayer getDataLayer(long sectionPos, boolean cached) {
 * return null;
 * }
 * @Inject(method = "getStoredLevel", at = @At(value = "HEAD"), cancellable = true)
 * private void lightFix(long blockPos, CallbackInfoReturnable<Integer> info) {
 * try {
 * long m = SectionPos.blockToSection(blockPos);
 * DataLayer dataLayer = this.getDataLayer(m, true);
 * info.setReturnValue(
 * dataLayer.get(
 * SectionPos.sectionRelative(BlockPos.getX(blockPos)),
 * SectionPos.sectionRelative(BlockPos.getY(blockPos)),
 * SectionPos.sectionRelative(BlockPos.getZ(blockPos))
 * )
 * );
 * info.cancel();
 * } catch (Exception e) {
 * info.setReturnValue(0);
 * info.cancel();
 * }
 * }
 * <p>
 * }
 **/