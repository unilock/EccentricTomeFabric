package website.eccentric.tome;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import website.eccentric.tome.network.TomeChannel;

public class EccentricTome implements ModInitializer {
    public static final String ID = "eccentrictome";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final RecipeSerializer<?> ATTACHMENT = Registry.register(Registry.RECIPE_SERIALIZER, id("attachment"), AttachmentRecipe.SERIALIZER = new SimpleRecipeSerializer<>(AttachmentRecipe::new));
    public static final Item TOME = Registry.register(Registry.ITEM, id("tome"), new TomeItem());

	@Override
	public void onInitialize() {
		TomeChannel.register();

		ModLoadingContext.registerConfig(ID, ModConfig.Type.COMMON, Configuration.SPEC);
		ModConfigEvents.loading(ID).register(modConfig -> onModConfig());
		ModConfigEvents.reloading(ID).register(modConfig -> onModConfig());
	}

    private static void onModConfig() {
        Configuration.ALIAS_MAP.clear();
        for (var alias : Configuration.ALIASES.get()) {
            var tokens = alias.split("=");
            Configuration.ALIAS_MAP.put(tokens[0], tokens[1]);
        }
    }

	public static ResourceLocation id(String location) {
		return new ResourceLocation(ID, location);
	}
}
