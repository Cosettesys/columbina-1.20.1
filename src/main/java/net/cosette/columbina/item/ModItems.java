package net.cosette.columbina.item;

import net.cosette.columbina.Columbina;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;



public class ModItems {

    public static final Item TOKEN = registerItem("token", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroups(FabricItemGroupEntries entries) {
        entries.add(TOKEN);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                Registry.ITEM,
                new ResourceLocation(Columbina.MOD_ID, name),
                item
        );
    }

    public static void RegisterItems() {
        Columbina.LOGGER.info("Columbina: Registering Items");

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroups);

    }
}
