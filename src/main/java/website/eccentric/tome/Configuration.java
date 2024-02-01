package website.eccentric.tome;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {
    public static final ForgeConfigSpec.BooleanValue ALL_ITEMS;
    public static final ForgeConfigSpec.BooleanValue DISABLE_OVERLAY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> NAMES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ALIASES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDE_ITEMS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INCLUDE_ITEM_TAGS;

    public static final HashMap<String, String> ALIAS_MAP = new HashMap<>();

    public static final ForgeConfigSpec SPEC;

    static {
        var BUILDER = new ForgeConfigSpec.Builder()
                .comment("Common configuration settings")
                .push("common");

        ALL_ITEMS = BUILDER
                .comment("Allow all items to be added")
                .define("allitems", false);

        DISABLE_OVERLAY = BUILDER
                .comment("Disable overlay previewing tome conversion")
                .define("disable_overlay", false);

        ITEMS = BUILDER
                .comment("Whitelisted items")
                .defineListAllowEmpty(
                        List.of("items"),
                        () -> List.of(
                                "ad_astra:astrodux",
                                "alexsmobs:animal_dictionary",
                                "compactmachines:personal_shrinking_device",
                                "cookingforblockheads:no_filter_edition",
                                "draconicevolution:info_tablet",
                                "enigmaticlegacy:the_acknowledgment",
                                "evilcraft:origins_of_darkness",
                                "iceandfire:bestiary",
                                "integrateddynamics:on_the_dynamics_of_integration",
                                "occultism:dictionary_of_spirits",
                                "rootsclassic:runic_tablet",
                                "tconstruct:fantastic_foundry",
                                "tconstruct:materials_and_you",
                                "tconstruct:mighty_smelting",
                                "tconstruct:puny_smelting",
                                "tconstruct:tinkers_gadgetry",
                                "theoneprobe:probenote"),
                        Validator::isStringResource);

        NAMES = BUILDER
                .comment("Whitelisted names")
                .defineListAllowEmpty(
                        List.of("names"),
                        () -> List.of(
                                "book",
                                "chronicle",
                                "codex",
                                "compendium",
                                "enchiridion",
                                "grimoire",
                                "guide",
                                "journal",
                                "knowledge",
                                "lexicon",
                                "manual",
                                "nomicon",
                                "pedia",
                                "tome"),
                        Validator::isString);

        ALIASES = BUILDER
                .comment("Mod aliases")
                .defineListAllowEmpty(
                        List.of("aliases"),
                        () -> List.of(
                                "deepresonance=rftoolsbase",
                                "integratedcrafting=integrateddynamics",
                                "integratedterminals=integrateddynamics",
                                "integratedtunnels=integrateddynamics",
                                "mysticalaggraditions=mysticalagriculture",
                                "mythicbotany=botania",
                                "rftoolsbuilder=rftoolsbase",
                                "rftoolscontrol=rftoolsbase",
                                "rftoolsdim=rftoolsbase",
                                "rftoolspower=rftoolsbase",
                                "rftoolspower=rftoolsbase",
                                "rftoolsstorage=rftoolsbase",
                                "rftoolsutility=rftoolsbase",
                                "xnet=rftoolsbase",
                                "zenith=apotheosis"),
                        Validator::isString);

        EXCLUDE = BUILDER
                .comment("Blacklisted mods")
                .defineListAllowEmpty(
                        List.of("exclude"),
                        ArrayList<String>::new,
                        Validator::isString);

        EXCLUDE_ITEMS = BUILDER
                .comment("Blacklisted items")
                .defineListAllowEmpty(
                        List.of("exclude_items"),
                        () -> List.of(
                                "apotheosis:boots_tome",
                                "apotheosis:bow_tome",
                                "apotheosis:chestplate_tome",
                                "apotheosis:extraction_tome",
                                "apotheosis:fishing_tome",
                                "apotheosis:helmet_tome",
                                "apotheosis:improved_scrap_tome",
                                "apotheosis:leggings_tome",
                                "apotheosis:other_tome",
                                "apotheosis:pickaxe_tome",
                                "apotheosis:scrap_tome",
                                "apotheosis:weapon_tome",
                                "archers:archer_spell_book",
                                "ars_nouveau:annotated_codex",
                                "blue_skies:blue_journal",
                                "cookingforblockheads:crafting_book",
                                "cookingforblockheads:recipe_book",
                                "darkutils:book_galactic",
                                "darkutils:book_restore",
                                "darkutils:book_runelic",
                                "darkutils:tome_enchanting",
                                "darkutils:tome_illager",
                                "darkutils:tome_pigpen",
                                "darkutils:tome_runelic",
                                "darkutils:tome_sga",
                                "darkutils:tome_shadows",
                                "fwaystones:scroll_of_infinite_knowledge",
                                "invoke:invoker_spell_book",
                                "minecolonies:ancienttome",
                                "minecraft:book",
                                "minecraft:enchanted_book",
                                "occultism:book_of_binding_afrit",
                                "occultism:book_of_binding_bound_afrit",
                                "occultism:book_of_binding_bound_djinni",
                                "occultism:book_of_binding_bound_foliot",
                                "occultism:book_of_binding_bound_marid",
                                "occultism:book_of_binding_djinni",
                                "occultism:book_of_binding_foliot",
                                "occultism:book_of_binding_marid",
                                "occultism:book_of_calling_djinni_manage_machine",
                                "occultism:book_of_calling_foliot_cleaner",
                                "occultism:book_of_calling_foliot_lumberjack",
                                "occultism:book_of_calling_foliot_transport_items",
                                "projecte:tome",
                                "quark:ancient_tome",
                                "spectrum:gilded_book",
                                "tombstone:book_of_disenchantment",
                                "tombstone:book_of_magic_impregnation",
                                "tombstone:book_of_recycling",
                                "tombstone:book_of_repairing",
                                "wizards:arcane_spell_book",
                                "wizards:fire_spell_book",
                                "wizards:frost_spell_book"),
                        Validator::isStringResource);

        INCLUDE_ITEM_TAGS = BUILDER
                .comment("Whitelisted item tags")
                .defineListAllowEmpty(
                        List.of("include_item_tags"),
                        ArrayList<String>::new,
                        Validator::isStringResource);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static class Validator {
        public static boolean isString(Object object) {
            return object instanceof String;
        }

        public static boolean isStringResource(Object object) {
            return isString(object) && ResourceLocation.isValidResourceLocation((String) object);
        }
    }
}
