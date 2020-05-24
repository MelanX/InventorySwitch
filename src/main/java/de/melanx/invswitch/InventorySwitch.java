package de.melanx.invswitch;

import com.mojang.brigadier.CommandDispatcher;
import de.melanx.invswitch.commands.WeatherCommand;
import de.melanx.invswitch.commands.SwitchCommand;
import de.melanx.invswitch.util.Events;
import de.melanx.invswitch.util.Registration;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(InventorySwitch.MODID)
public class InventorySwitch {

    public static final String MODID = "invswitch";
    public static Logger LOGGER = LogManager.getLogger(MODID);

    public InventorySwitch() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        new Events();
        Registration.init();
        MinecraftForge.EVENT_BUS.addListener(this::serverLoad);
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(MODID)
                .then(WeatherCommand.register())
                .then(SwitchCommand.register())
        );
    }

    private void serverLoad(FMLServerStartingEvent event) {
        register(event.getCommandDispatcher());
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        Registration.registerBrewingRecipes();
    }
}
