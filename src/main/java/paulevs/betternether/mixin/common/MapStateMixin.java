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
	public MapStateMixin(int i, int j, byte b, boolean bl, boolean bl2, boolean bl3, ResourceKey<Level> registryKey) {
		super();
	}
	
	@Shadow public boolean unlimitedTracking;
	@Shadow public byte scale;
	@Shadow @Final public int x;
	@Shadow @Final public int z;
	@Shadow @Final private Map<String, MapDecoration> decorations;
	@Shadow protected abstract void removeDecoration(String string);
	@Shadow protected abstract void setDecorationsDirty();
	@Shadow private int trackedDecorationCount;

	@Inject(method = "addDecoration", at = @At(value = "HEAD"), cancellable = true)
	private void updatePlayer(MapDecoration.Type type, LevelAccessor level, String key, double x, double z, double rotation, Component text, CallbackInfo info) {
		if (level != null && level.dimensionType().hasCeiling()) {
			//Code derived and adapted from Vanilla Minecraft Code in net.minecraft.world.item.MapItemSaveData.update
			MapDecoration mapDecoration;
			
			byte displayRotation;
			int scale = 1 << this.scale;
			float px = (float) (x - (double) this.x) / (float) scale;
			float pz = (float) (z - (double) this.z) / (float) scale;
			byte mapX = (byte) ((double) (px * 2.0f) + 0.5);
			byte mapZ = (byte) ((double) (pz * 2.0f) + 0.5);
			final int VALID_REGION = 63;
			if (px >= -VALID_REGION && pz >= -VALID_REGION && px <= VALID_REGION && pz <= VALID_REGION) {
				displayRotation = (byte) ((rotation + (rotation < 0.0 ? -8.0 : 8.0)) * 16.0 / 360.0);
				//We do want the actual rotation of the player here
//				if (this.dimension == Level.NETHER && level != null) {
//					int time = (int) (level.getLevelData().getDayTime() / 10L);
//					displayRotation = (byte) (time * time * 34187121 + time * 121 >> 15 & 0xF);
//				}
			}
			else if (type == MapDecoration.Type.PLAYER) {
				final int MAX = 320;
				if (Math.abs(px) < MAX && Math.abs(pz) < MAX) {
					type = MapDecoration.Type.PLAYER_OFF_MAP;
				} else if (this.unlimitedTracking) {
					type = MapDecoration.Type.PLAYER_OFF_LIMITS;
				} else {
					this.removeDecoration(key);
					info.cancel();
					return;
				}
				displayRotation = 0;
				if (px <= -VALID_REGION) {
					mapX = -128;
				}
				if (pz <= -VALID_REGION) {
					mapZ = -128;
				}
				if (px >= VALID_REGION) {
					mapX = 127;
				}
				if (pz >= 63.0f) {
					mapZ = 127;
				}
			}
			else {
				this.removeDecoration(key);
				info.cancel();
				return;
			}
			MapDecoration tempDecoration = new MapDecoration(type, mapX, mapZ, displayRotation, text);
			if (!tempDecoration.equals(mapDecoration = this.decorations.put(key, tempDecoration))) {
				if (mapDecoration != null && mapDecoration.getType().shouldTrackCount()) {
					--this.trackedDecorationCount;
				}
				if (type.shouldTrackCount()) {
					++this.trackedDecorationCount;
				}
				this.setDecorationsDirty();
			}
			info.cancel();
		}
	}
}
