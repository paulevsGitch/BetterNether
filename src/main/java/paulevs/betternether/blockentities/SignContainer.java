package paulevs.betternether.blockentities;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.GenericContainer;
import net.minecraft.container.LecternContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.registry.Registry;

public class SignContainer// extends Container
{
	/*public static final ContainerType<SignContainer> SIGN_CONTAINER = Registry.register(Registry.CONTAINER, "bn_sign", new ContainerType<SignContainer>((i, playerInventory) -> {
	      return new SignContainer(i);
	   }));
	
	public SignContainer(int syncId)
	{
		super(SIGN_CONTAINER, syncId);
	}
	
	public static SignContainer create(int syncId, PlayerInventory inventory)
	{
		return new SignContainer(syncId);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return !player.isSpectator();
	}*/
}
