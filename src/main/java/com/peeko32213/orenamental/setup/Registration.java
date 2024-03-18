package com.peeko32213.orenamental.setup;

import com.mojang.logging.LogUtils;
import com.peeko32213.orenamental.blocks.OBlocks;
import com.peeko32213.orenamental.creativetabs.OTabs;
import com.peeko32213.orenamental.items.OItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


public class Registration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(){

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        OBlocks.BLOCKS.register(bus);
        OItems.ITEMS.register(bus);
        OTabs.TABS.register(bus);
    }
}
