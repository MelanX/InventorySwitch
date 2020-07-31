package de.melanx.invswitch.networking;

import de.melanx.invswitch.InventorySwitch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkUtil {
    private static final String PROTOCOL_VERSION = "1.0";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(InventorySwitch.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        INSTANCE.registerMessage(
                nextID(),
                PickUpEntryPacket.class,
                PickUpEntryPacket::toBytes,
                PickUpEntryPacket::new,
                PickUpEntryPacket::handle
        );
    }
}
