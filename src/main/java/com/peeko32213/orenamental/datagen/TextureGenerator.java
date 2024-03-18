package com.peeko32213.orenamental.datagen;

import com.mojang.blaze3d.platform.NativeImage;
import com.peeko32213.orenamental.Orenamental;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.peeko32213.orenamental.Orenamental.prefix;

public class TextureGenerator extends PaletteTextureProvider {

    public static final ResourceLocation DEFAULT_ALUMINIUM_BLOCK = prefix("block/aluminium/default/aluminium_block");
    public static final ResourceLocation DEFAULT_ALUMINIUM_DOOR = prefix("item/aluminium/default/aluminium_door");
    public static final ResourceLocation DEFAULT_ALUMINIUM_DOOR_BOTTOM = prefix("block/aluminium/default/aluminium_door_bottom");
    public static final ResourceLocation DEFAULT_ALUMINIUM_DOOR_TOP = prefix("block/aluminium/default/aluminium_door_top");
    public static final ResourceLocation DEFAULT_ALUMINIUM_GRATE = prefix("block/aluminium/default/aluminium_grate");
    public static final ResourceLocation DEFAULT_ALUMINIUM_PILLAR = prefix("block/aluminium/default/aluminium_pillar");
    public static final ResourceLocation DEFAULT_ALUMINIUM_PILLAR_BOTTOM = prefix("block/aluminium/default/aluminium_pillar_bottom");
    public static final ResourceLocation DEFAULT_ALUMINIUM_PILLAR_TOP = prefix("block/aluminium/default/aluminium_pillar_top");
    public static final ResourceLocation DEFAULT_ALUMINIUM_PILLAR_MIDDLE = prefix("block/aluminium/default/aluminium_pillar_middle");
    public static final ResourceLocation DEFAULT_ALUMINIUM_TRAPDOOR = prefix("block/aluminium/default/aluminium_trapdoor");
    public static final ResourceLocation DEFAULT_CUT_ALUMINIUM = prefix("block/aluminium/default/cut_aluminium");
    public static final ResourceLocation DEFAULT_CUT_ALUMINIUM_PILLAR = prefix("block/aluminium/default/cut_aluminium_pillar");
    public static final ResourceLocation DEFAULT_CUT_ALUMINIUM_PILLAR_BOTTOM = prefix("block/aluminium/default/cut_aluminium_pillar_bottom");
    public static final ResourceLocation DEFAULT_CUT_ALUMINIUM_PILLAR_MIDDLE = prefix("block/aluminium/default/cut_aluminium_pillar_middle");
    public static final ResourceLocation DEFAULT_CUT_ALUMINIUM_PILLAR_TOP = prefix("block/aluminium/default/cut_aluminium_pillar_top");


    public TextureGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, Orenamental.MODID);
    }

    @Override
    protected void genTextures(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer) {
        genAllAluminium(consumer, PaletteRegistry.BLACK_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.BLUE_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.GRAY_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.WHITE_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.BROWN_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.RED_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.ORANGE_ALUMINIUM);
        genAllAluminium(consumer, PaletteRegistry.YELLOW_ALUMINIUM);


    }



    public void genAllAluminium(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer, Palettes palettes) {
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_BLOCK, palettes);
        genAluminiumItem(consumer,DEFAULT_ALUMINIUM_DOOR, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_DOOR_TOP, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_DOOR_BOTTOM, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_GRATE, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_PILLAR, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_PILLAR_BOTTOM, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_PILLAR_TOP, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_PILLAR_MIDDLE, palettes);
        genAluminiumBlock(consumer,DEFAULT_ALUMINIUM_TRAPDOOR, palettes);
        genAluminiumBlock(consumer,DEFAULT_CUT_ALUMINIUM, palettes);
        genAluminiumBlock(consumer,DEFAULT_CUT_ALUMINIUM_PILLAR, palettes);
        genAluminiumBlock(consumer,DEFAULT_CUT_ALUMINIUM_PILLAR_BOTTOM, palettes);
        genAluminiumBlock(consumer,DEFAULT_CUT_ALUMINIUM_PILLAR_MIDDLE, palettes);
        genAluminiumBlock(consumer,DEFAULT_CUT_ALUMINIUM_PILLAR_TOP, palettes);
    }

    public void genAluminiumBlock(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer, ResourceLocation file, Palettes palettes) {
        genPaletteSwap(consumer,file, "block/aluminium", palettes);
    }

    public void genAluminiumItem(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer, ResourceLocation file, Palettes palettes) {
        genPaletteSwap(consumer,file, "item/aluminium", palettes);
    }
}
