package com.peeko32213.orenamental.blocks;

import com.mojang.logging.LogUtils;
import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.datagen.PaletteRegistry;
import com.peeko32213.orenamental.datagen.Palettes;
import com.peeko32213.orenamental.items.OItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Orenamental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBlocks {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Orenamental.MODID);
    public static final List<Supplier<Block>> al = new ArrayList<>();

    public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALUMINIUM_DOOR = registerBlock("aluminium_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), BlockSetType.IRON));
    public static final RegistryObject<Block> ALUMINIUM_GRATE= registerBlock("aluminium_grate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ALUMINIUM_PILLAR= registerBlock("aluminium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALUMINIUM_TRAPDOOR= registerBlock("aluminium_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), BlockSetType.IRON));
    public static final RegistryObject<Block> CUT_ALUMINIUM = registerBlock("cut_aluminium", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CUT_ALUMINIUM_PILLAR = registerBlock("cut_aluminium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> BLACK_ALUMINIUM_BLOCK = registerAluminiumBlockWithFamily(PaletteRegistry.BLACK_ALUMINIUM, DyeColor.BLACK, () -> new StrippableDyeBlock( DyeColor.BLACK, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLUE_ALUMINIUM_BLOCK = registerAluminiumBlockWithFamily(PaletteRegistry.BLUE_ALUMINIUM, DyeColor.BLUE, () -> new StrippableDyeBlock( DyeColor.BLUE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GRAY_ALUMINIUM_BLOCK = registerAluminiumBlockWithFamily(PaletteRegistry.GRAY_ALUMINIUM, DyeColor.GRAY, () -> new StrippableDyeBlock( DyeColor.GRAY, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> WHITE_ALUMINIUM = registerAluminiumBlockWithFamily(PaletteRegistry.WHITE_ALUMINIUM, DyeColor.WHITE, () -> new StrippableDyeBlock( DyeColor.WHITE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BROWN_ALUMINIUM = registerAluminiumBlockWithFamily(PaletteRegistry.BROWN_ALUMINIUM, DyeColor.BROWN, () -> new StrippableDyeBlock( DyeColor.BROWN, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RED_ALUMINIUM = registerAluminiumBlockWithFamily(PaletteRegistry.RED_ALUMINIUM, DyeColor.RED, () -> new StrippableDyeBlock( DyeColor.RED, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ORANGE_ALUMINIUM = registerAluminiumBlockWithFamily(PaletteRegistry.ORANGE_ALUMINIUM, DyeColor.ORANGE, () -> new StrippableDyeBlock( DyeColor.ORANGE, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> YELLOW_ALUMINIUM = registerAluminiumBlockWithFamily(PaletteRegistry.YELLOW_ALUMINIUM, DyeColor.YELLOW, () -> new StrippableDyeBlock( DyeColor.YELLOW, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));




    public static <B extends Block> RegistryObject<B> registerAluminiumBlockWithFamily(Palettes palettes, DyeColor color, Supplier<? extends B> supplier) {
        String name = palettes.getPrefix() + "_aluminium_block";
        RegistryObject<B> block = registerBlock(name, supplier);

        String nameDoor = palettes.getPrefix() + "_aluminium_door";
        RegistryObject<DoorBlock> doorBlock = registerBlock(nameDoor, () -> new StrippableDoorBlock(color,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), BlockSetType.IRON));

        String nameGrate = palettes.getPrefix() + "_aluminium_grate";
        RegistryObject<Block> grateBlock =registerBlock(nameGrate, () -> new StrippableDyeBlock(color, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

        String namePillar = palettes.getPrefix() + "_aluminium_pillar";
        RegistryObject<Block> pillarBlock =registerBlock(namePillar, () -> new StrippableDyeBlock(color, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

        String nameTrapdoor = palettes.getPrefix() + "_aluminium_trapdoor";
        RegistryObject<TrapDoorBlock> trapdoorBlock =registerBlock(nameTrapdoor, () -> new StrippableTrapDoorBlock(color,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), BlockSetType.IRON));

        String nameCut = palettes.getPrefix() + "_cut" + "_aluminium";
        RegistryObject<Block> cutBlock = registerBlock(nameCut, () -> new StrippableDyeBlock(color, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

        String nameCutPillar = palettes.getPrefix() + "_cut" + "_aluminium_pillar";
        RegistryObject<Block> cutPillar = registerBlock(nameCutPillar, () -> new StrippableDyeBlock(color, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

        return block;
    }


    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }


    public static <B extends Block> RegistryObject<B> registerBlockNoItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        return block;
    }

}
