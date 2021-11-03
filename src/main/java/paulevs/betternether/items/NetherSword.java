package paulevs.betternether.items;

import net.minecraft.world.item.Tier;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.tool.BaseSwordItem;

public class NetherSword extends BaseSwordItem {
	public NetherSword(Tier material, int attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed, NetherItems.defaultSettings().fireResistant());
	}
}
