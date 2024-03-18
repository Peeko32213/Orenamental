package com.peeko32213.orenamental.datagen;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.blocks.OBlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.function.BiConsumer;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(PackOutput output) {
        super(output, Orenamental.MODID, "en_us");
    }
    static final Map<BlockFamily.Variant, BiConsumer<LanguageGenerator, net.minecraft.world.level.block.Block>> SHAPE_CONSUMERS =
            ImmutableMap.<BlockFamily.Variant, BiConsumer<LanguageGenerator, Block>>builder()
                    .put(BlockFamily.Variant.BUTTON, LanguageGenerator::add)
                    .put(BlockFamily.Variant.DOOR, LanguageGenerator::add)
                    .put(BlockFamily.Variant.CHISELED, LanguageGenerator::add)
                    .put(BlockFamily.Variant.CRACKED, LanguageGenerator::add)
                    .put(BlockFamily.Variant.CUSTOM_FENCE, LanguageGenerator::add)
                    .put(BlockFamily.Variant.FENCE, LanguageGenerator::add)
                    .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, LanguageGenerator::add)
                    .put(BlockFamily.Variant.FENCE_GATE, LanguageGenerator::add)
                    .put(BlockFamily.Variant.SIGN, LanguageGenerator::add)
                    .put(BlockFamily.Variant.SLAB, LanguageGenerator::add)
                    .put(BlockFamily.Variant.STAIRS, LanguageGenerator::add)
                    .put(BlockFamily.Variant.PRESSURE_PLATE, LanguageGenerator::add)
                    .put(BlockFamily.Variant.TRAPDOOR, LanguageGenerator::add)
                    .put(BlockFamily.Variant.WALL, LanguageGenerator::add)
                    .build();
    @Override
    protected void addTranslations() {

        add("itemGroup.orenamental", "Orenamental");
        OBlockFamilies.getAllFamilies().forEach(blockFamily -> {

            add(blockFamily.getBaseBlock());

            for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
                Block block = family.getValue();
                Block main = blockFamily.getBaseBlock();
                BlockFamily.Variant variant = family.getKey();
                BiConsumer<LanguageGenerator, Block> biconsumer = LanguageGenerator.SHAPE_CONSUMERS.get(variant);
                if (biconsumer != null) {
                    biconsumer.accept(this, block);
                }

            }
        });
    }

    public void add(Block key) {

        String keyDescription = key.getDescriptionId();
        String[] parts = keyDescription.replace("block.aeon.", "").split("_");
        StringBuilder modifiedString = new StringBuilder();
        int i = 0;
        for (String part : parts) {
            if(0 == i){
                modifiedString.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            } else {
                modifiedString.append(" ").append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            }
            i++;
        }
        modifiedString.trimToSize();
        String finalModifiedString = modifiedString.toString();
        add(key.getDescriptionId(), finalModifiedString);
    }


}

