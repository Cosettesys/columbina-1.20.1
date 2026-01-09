package net.cosette.columbina;

import net.cosette.columbina.command.ColumbinaCommands;
import net.cosette.columbina.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Columbina implements ModInitializer {
	public static final String MOD_ID = "columbina";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		System.out.println("[COLUMBINA TEST] onInitialize called!");
		LOGGER.info("Columbina mod initialized!");
		ModItems.RegisterItems();


		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ColumbinaCommands.register(dispatcher);
		});



	}
}