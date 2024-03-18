package com.peeko32213.orenamental;

import com.mojang.logging.LogUtils;
import com.peeko32213.orenamental.setup.ClientSetup;
import com.peeko32213.orenamental.setup.ModSetup;
import com.peeko32213.orenamental.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(Orenamental.MODID)
public class Orenamental
{
    public static final String MODID = "orenamental";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Orenamental()
    {
        Registration.init();
        ModSetup.setup();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
           // MinecraftForge.EVENT_BUS.register(ClientEvents.class);
            // static method with no client-only classes in method signature
        }
        //MinecraftForge.EVENT_BUS.register(ForgeEvents.class);


        //GeckoLib.initialize();
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
}
