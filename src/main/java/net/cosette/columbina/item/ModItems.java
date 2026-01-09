package net.cosette.columbina.item;

import net.cosette.columbina.Columbina;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.cosette.columbina.item.TokenItem;


public class ModItems {

    public static final Item TOKEN = registerItem(
            "token",
            new TokenItem(new FabricItemSettings())
    );

    private static void addItemsToIngredientItemGroups(FabricItemGroupEntries entries) {
        Columbina.LOGGER.info("Adding Columbina items to creative tab");
        entries.accept(TOKEN);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(Columbina.MOD_ID, name),
                item
        );
    }

    public static void RegisterItems() {
        Columbina.LOGGER.info("Columbina: Registering Items");

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroups);

    }
}
