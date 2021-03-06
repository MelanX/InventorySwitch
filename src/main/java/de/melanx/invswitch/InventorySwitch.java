package de.melanx.invswitch;

import com.mojang.brigadier.CommandDispatcher;
import de.melanx.invswitch.commands.SwitchCommand;
import de.melanx.invswitch.commands.WeatherCommand;
import de.melanx.invswitch.networking.NetworkUtil;
import de.melanx.invswitch.rendering.DrawEntriesHandler;
import de.melanx.invswitch.util.Events;
import de.melanx.invswitch.util.Registration;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(InventorySwitch.MODID)
public class InventorySwitch {

    public static final String MODID = "invswitch";
    public static Logger LOGGER = LogManager.getLogger(MODID);
    public static final ClientConfigHandler CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        final Pair<ClientConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfigHandler::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public InventorySwitch() {
        new Events();
        Registration.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(MODID)
                .then(WeatherCommand.register())
                .then(SwitchCommand.register())
        );
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new DrawEntriesHandler());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        registerCommands(event.getDispatcher());
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        Registration.registerBrewingRecipes();
        NetworkUtil.registerMessages();
    }
}
