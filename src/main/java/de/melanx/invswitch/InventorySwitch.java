package de.melanx.invswitch;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod(InventorySwitch.MODID)
public class InventorySwitch {

    public static final String MODID = "invswitch";
    public InventorySwitch instance;

    public InventorySwitch() {
        instance = this;

        MinecraftForge.EVENT_BUS.addListener(this::serverLoad);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(MODID)
                .then(SwitchCommand.register(dispatcher))
        );
    }

    private void serverLoad(FMLServerStartingEvent event) {
        System.out.println(event.getCommandDispatcher());
        register(event.getCommandDispatcher());
    }
}
