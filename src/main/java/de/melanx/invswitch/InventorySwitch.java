package de.melanx.invswitch;

import com.mojang.brigadier.CommandDispatcher;
import de.melanx.invswitch.commands.AddWeatherCommand;
import de.melanx.invswitch.commands.SwitchCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(InventorySwitch.MODID)
public class InventorySwitch {

    public static final String MODID = "invswitch";
    public static Logger LOGGER = LogManager.getLogger(MODID);

    public InventorySwitch() {
        MinecraftForge.EVENT_BUS.addListener(this::serverLoad);
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(MODID)
                .then(AddWeatherCommand.register())
                .then(SwitchCommand.register())
        );
    }

    private void serverLoad(FMLServerStartingEvent event) {
        System.out.println(event.getCommandDispatcher());
        register(event.getCommandDispatcher());
    }
}
