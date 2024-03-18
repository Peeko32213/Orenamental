package com.peeko32213.orenamental.datagen;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.blocks.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Orenamental.MODID, existingFileHelper);
    }
    private final Set<Block> blockSet = new HashSet<>();
    static final Map<BlockFamily.Variant, BiConsumer<BlockTagGenerator, net.minecraft.world.level.block.Block>> SHAPE_CONSUMERS =
            ImmutableMap.<BlockFamily.Variant, BiConsumer<BlockTagGenerator, Block>>builder()
                    .put(BlockFamily.Variant.BUTTON, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.DOOR, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.CHISELED, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.CRACKED, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.CUSTOM_FENCE, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.FENCE, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.FENCE_GATE, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.SIGN, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.SLAB, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.STAIRS, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.PRESSURE_PLATE, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.TRAPDOOR, BlockTagGenerator::addToTag)
                    .put(BlockFamily.Variant.WALL, BlockTagGenerator::addToTagWall)
                    .build();


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {


        OBlockFamilies.getAllFamilies().forEach(blockFamily -> {

            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    blockFamily.getBaseBlock()
            );

            for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
                Block block = family.getValue();
                Block main = blockFamily.getBaseBlock();
                BlockFamily.Variant variant = family.getKey();
                BiConsumer<BlockTagGenerator, Block> biconsumer = BlockTagGenerator.SHAPE_CONSUMERS.get(variant);
                if (biconsumer != null) {
                    biconsumer.accept(this, block);
                }
//
            }
        });


        for(RegistryObject<Block> block : OBlocks.BLOCKS.getEntries()) {
            if(blockSet.contains(block.get())) continue;
            Block block1 = block.get();
            if(block1 instanceof StripDyeBlock) {
                addToTag(block1);
            }

        }
        
        //this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
        //        .addToTag(ModBlocks.RAW_SAPPHIRE_BLOCK.get());
//
        //this.tag(BlockTags.NEEDS_STONE_TOOL)
        //        .addToTag(ModBlocks.NETHER_SAPPHIRE_ORE.get());
//
        //this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
        //        .addToTag(ModBlocks.END_STONE_SAPPHIRE_ORE.get());


    }
    
    public void addToTag(Block block){
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(block);
    }

    public void addToTagWall(Block block){
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(block);
        this.tag(BlockTags.WALLS)
                .add(block);

    }
}
