package paulevs.betternether.mixin.common;

import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapItemSavedData.class)
public abstract class MapStateMixin extends SavedData {
	@Shadow
	public boolean unlimitedTracking;

	@Shadow
	public byte scale;

	public MapStateMixin(int i, int j, byte b, boolean bl, boolean bl2, boolean bl3, ResourceKey<Level> registryKey) {
		super();
	}

	@Shadow @Final public int x;
	@Shadow @Final public int z;
	@Shadow @Final private Map<String, MapDecoration> decorations;

	@Shadow @Final private List<MapItemSavedData.HoldingPlayer> carriedBy;

	@Inject(method = "addDecoration", at = @At(value = "HEAD"), cancellable = true)
	private void updatePlayer(MapDecoration.Type type, LevelAccessor world, String key, double x, double z, double rotation, Component text, CallbackInfo info) {
		if (world != null && world.dimensionType().hasCeiling()) {
			Map<String, MapDecoration> icons = this.decorations;//((MapState) (Object) this).icons;

			int i = 1 << this.scale;
			float f = (float) (x - (double) this.x) / (float) i;
			float g = (float) (z - (double) this.z) / (float) i;
			byte b = (byte) ((int) ((double) (f * 2.0F) + 0.5D));
			byte c = (byte) ((int) ((double) (g * 2.0F) + 0.5D));
			byte e;
			if (f >= -63.0F && g >= -63.0F && f <= 63.0F && g <= 63.0F) {
				rotation += rotation < 0.0D ? -8.0D : 8.0D;
				e = (byte) ((int) (rotation * 16.0D / 360.0D));
			}
			else {
				if (type != MapDecoration.Type.PLAYER) {
					icons.remove(key);
					return;
				}

				if (Math.abs(f) < 320.0F && Math.abs(g) < 320.0F) {
					type = MapDecoration.Type.PLAYER_OFF_MAP;
				}
				else {
					if (!this.unlimitedTracking) {
						icons.remove(key);
						return;
					}

					type = MapDecoration.Type.PLAYER_OFF_LIMITS;
				}

				e = 0;
				if (f <= -63.0F) {
					b = -128;
				}

				if (g <= -63.0F) {
					c = -128;
				}

				if (f >= 63.0F) {
					b = 127;
				}

				if (g >= 63.0F) {
					c = 127;
				}
			}

			icons.put(key, new MapDecoration(type, b, c, e, text));

			info.cancel();
		}
	}
}
