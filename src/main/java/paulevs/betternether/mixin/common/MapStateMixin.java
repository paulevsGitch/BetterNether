package paulevs.betternether.mixin.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.world.PersistentState;
import net.minecraft.world.WorldAccess;

@Mixin(MapState.class)
public abstract class MapStateMixin extends PersistentState {
	@Shadow
	public boolean unlimitedTracking;

	@Shadow
	public byte scale;

	public MapStateMixin(int i, int j, byte b, boolean bl, boolean bl2, boolean bl3, RegistryKey<World> registryKey) {
		super();
	}

	@Shadow @Final public int centerX;
	@Shadow @Final public int centerZ;
	@Shadow @Final private Map<String, MapIcon> icons;

	@Shadow @Final private List<MapState.PlayerUpdateTracker> updateTrackers;

	@Inject(method = "addIcon", at = @At(value = "HEAD"), cancellable = true)
	private void updatePlayer(MapIcon.Type type, WorldAccess world, String key, double x, double z, double rotation, Text text, CallbackInfo info) {
		if (world != null && world.getDimension().hasCeiling()) {
			Map<String, MapIcon> icons = this.icons;//((MapState) (Object) this).icons;

			int i = 1 << this.scale;
			float f = (float) (x - (double) this.centerX) / (float) i;
			float g = (float) (z - (double) this.centerZ) / (float) i;
			byte b = (byte) ((int) ((double) (f * 2.0F) + 0.5D));
			byte c = (byte) ((int) ((double) (g * 2.0F) + 0.5D));
			byte e;
			if (f >= -63.0F && g >= -63.0F && f <= 63.0F && g <= 63.0F) {
				rotation += rotation < 0.0D ? -8.0D : 8.0D;
				e = (byte) ((int) (rotation * 16.0D / 360.0D));
			}
			else {
				if (type != MapIcon.Type.PLAYER) {
					icons.remove(key);
					return;
				}

				if (Math.abs(f) < 320.0F && Math.abs(g) < 320.0F) {
					type = MapIcon.Type.PLAYER_OFF_MAP;
				}
				else {
					if (!this.unlimitedTracking) {
						icons.remove(key);
						return;
					}

					type = MapIcon.Type.PLAYER_OFF_LIMITS;
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

			icons.put(key, new MapIcon(type, b, c, e, text));

			info.cancel();
		}
	}
}
