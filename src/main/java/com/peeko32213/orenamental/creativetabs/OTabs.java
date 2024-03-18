package com.peeko32213.orenamental.creativetabs;

import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.items.OItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Orenamental.MODID);

    private static final CreativeModeTab ORE = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 9)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.orenamental"))
            .icon(() -> new ItemStack(Items.NETHER_BRICK))
            .displayItems((d, entries) ->{
                OItems.ITEMS.getEntries().forEach(i -> entries.accept(i.get()));
            })
            .build();



    public static final RegistryObject<CreativeModeTab> ORE_TAB = TABS.register("orenamental", () -> ORE);
}
