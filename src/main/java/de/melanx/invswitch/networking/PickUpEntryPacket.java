package de.melanx.invswitch.networking;

import de.melanx.invswitch.ClientConfigHandler;
import de.melanx.invswitch.items.ItemLootBox;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PickUpEntryPacket implements Packet {

    ItemStack itemStack;

    public PickUpEntryPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public PickUpEntryPacket(PacketBuffer buf) {
        this.itemStack = buf.readItemStack();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(itemStack);
    }

    @Override
    public boolean isValid(Supplier<NetworkEvent.Context> context) {
        return true;
    }

    @Override
    public void doWork(Supplier<NetworkEvent.Context> context) {
        if (ClientConfigHandler.showPickupNotifier.get()) {
            ItemLootBox.addItemEntry(itemStack);
        }
    }
}
