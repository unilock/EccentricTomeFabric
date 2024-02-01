package website.eccentric.tome;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import website.eccentric.tome.network.TomeChannel;

public class EccentricTome implements ModInitializer {
    public static final String ID = "eccentrictome";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final RecipeSerializer<?> ATTACHMENT = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("attachment"), new SimpleCraftingRecipeSerializer<>(AttachmentRecipe::new));
    public static final Item TOME = Registry.register(BuiltInRegistries.ITEM, id("tome"), new TomeItem());

	@Override
	public void onInitialize() {
		TomeChannel.register();

		ForgeConfigRegistry.INSTANCE.register(ID, ModConfig.Type.COMMON, Configuration.SPEC);
		ModConfigEvents.loading(ID).register(config -> onModConfig());
		ModConfigEvents.reloading(ID).register(config -> onModConfig());

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(TOME));
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
