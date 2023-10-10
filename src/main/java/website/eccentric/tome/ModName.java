package website.eccentric.tome;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ModName {
    private static final Map<String, String> modNames = new HashMap<>();

    public static final String PATCHOULI = "patchouli";

    static {
        for (var mod : FabricLoader.getInstance().getAllMods()) {
            modNames.put(mod.getMetadata().getId(), mod.getMetadata().getName());
        }
    }

    public static String from(BlockState state) {
        return orAlias(BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace());
    }

    public static String from(ItemStack stack) {
        var mod = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
        if (mod.equals(PATCHOULI)) {
            var tag = stack.getTag();
            if (tag == null)
                return PATCHOULI;

            var book = tag.getString(Tag.Patchouli.BOOK);
            mod = new ResourceLocation(book).getNamespace();
        }

        return orAlias(mod);
    }

    public static String orAlias(String mod) {
        return Configuration.ALIAS_MAP.getOrDefault(mod, mod);
    }

    public static String name(String mod) {
        return modNames.getOrDefault(mod, mod);
    }
}
