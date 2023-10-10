package website.eccentric.tome;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AttachmentRecipe extends CustomRecipe {
    public AttachmentRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }

    @Override
    public boolean matches(CraftingContainer crafting, Level level) {
        var foundTome = false;
        var foundTarget = false;

        for (var i = 0; i < crafting.getContainerSize(); i++) {
            var stack = crafting.getItem(i);
            if (stack.isEmpty())
                continue;

            var item = stack.getItem();
            if (item instanceof BlockItem) {
                return false;
            }
            if (item instanceof TomeItem) {
                if (foundTome)
                    return false;
                foundTome = true;
            } else if (isTarget(stack)) {
                if (foundTarget)
                    return false;
                foundTarget = true;
            } else
                return false;
        }

        return foundTome && foundTarget;
    }

    @Override
    public ItemStack assemble(CraftingContainer crafting, RegistryAccess access) {
        var tome = ItemStack.EMPTY;
        var target = ItemStack.EMPTY;

        for (var i = 0; i < crafting.getContainerSize(); i++) {
            var stack = crafting.getItem(i);
            if (stack.isEmpty())
                continue;

            if (stack.getItem() instanceof TomeItem)
                tome = stack;
            else
                target = stack;
        }

        tome = tome.copy();

        return Tome.attach(tome, target);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public boolean isTarget(ItemStack stack) {
        if (stack.isEmpty())
            return false;

        var mod = ModName.from(stack);

        if (Configuration.ALL_ITEMS.get())
            return true;

        if (Configuration.EXCLUDE.get().contains(mod))
            return false;

        var location = BuiltInRegistries.ITEM.getKey(stack.getItem());
        var locationString = location.toString();
        var locationDamage = locationString + ":" + stack.getDamageValue();

        var excludeItems = Configuration.EXCLUDE_ITEMS.get();
        if (excludeItems.contains(locationString) || excludeItems.contains(locationDamage))
            return false;

        var items = Configuration.ITEMS.get();
        if (items.contains(locationString) || items.contains(locationDamage))
            return true;

        for (var tag : Configuration.INCLUDE_ITEM_TAGS.get()) {
			if (stack.getItem().getDefaultInstance().is(TagKey.create(Registries.ITEM, new ResourceLocation(tag))))
                return true;
        }

        var path = location.getPath();
        for (var name : Configuration.NAMES.get()) {
            if (path.contains(name))
                return true;
        }

        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer crafting) {
        return NonNullList.withSize(crafting.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EccentricTome.ATTACHMENT;
    }
}
