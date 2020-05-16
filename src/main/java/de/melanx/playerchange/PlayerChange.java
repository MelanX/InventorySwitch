package de.melanx.playerchange;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PlayerChange.MODID)
public class PlayerChange {

    public static final String MODID = "playerchange";
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public PlayerChange instance;

    public PlayerChange() {
        instance = this;

        MinecraftForge.EVENT_BUS.register(this);
    }
}
