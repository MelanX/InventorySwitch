package de.melanx.invswitch.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface Packet {
    void toBytes(PacketBuffer buf);

    default void handle(Supplier<NetworkEvent.Context> context) {
        if(isValid(context)){
            context.get().enqueueWork(() -> {
                doWork(context);
            });
        }
    }

    boolean isValid(Supplier<NetworkEvent.Context> context);

    void doWork(Supplier<NetworkEvent.Context> context);
}
