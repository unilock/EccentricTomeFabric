package website.eccentric.tome.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;

public interface OpenTomeCallback {
	Event<OpenTomeCallback> EVENT = EventFactory.createArrayBacked(
		OpenTomeCallback.class,
		(listeners) -> (tome) -> {
			for (OpenTomeCallback event : listeners) {
				event.onOpenTome(tome);
			}
		}
	);

	void onOpenTome(ItemStack tome);
}
