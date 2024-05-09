package com.peeko32213.orenamental.datagen;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.blocks.*;
import com.peeko32213.orenamental.items.OItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.peeko32213.orenamental.Orenamental.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Orenamental.MODID, existingFileHelper);
    }
    private final Set<Item> itemSet = new HashSet<>();
    public static BlockFamily family = null;
    static final Map<BlockFamily.Variant, BiConsumer<ItemModelGenerator, Block>> SHAPE_CONSUMERS =
            ImmutableMap.<BlockFamily.Variant, BiConsumer<ItemModelGenerator, Block>>builder()
                    .put(BlockFamily.Variant.BUTTON, ItemModelGenerator::toBlockButton)
                    .put(BlockFamily.Variant.DOOR, ItemModelGenerator::singleTexBlockItem)
                    .put(BlockFamily.Variant.CHISELED, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.CRACKED, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.CUSTOM_FENCE, ItemModelGenerator::toBlockFence)
                    .put(BlockFamily.Variant.FENCE, ItemModelGenerator::toBlockFence)
                    .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.FENCE_GATE, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.SIGN, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.SLAB, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.STAIRS, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.PRESSURE_PLATE, ItemModelGenerator::toBlock)
                    .put(BlockFamily.Variant.TRAPDOOR, ItemModelGenerator::toBlockTrapdoor)
                    .put(BlockFamily.Variant.WALL, ItemModelGenerator::toBlockWall)
                    .build();
    @Override
    protected void registerModels() {

        OBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(blockFamily -> {

            for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
                this.family = blockFamily;
                Block block = family.getValue();
                Block main = blockFamily.getBaseBlock();
                BlockFamily.Variant variant = family.getKey();
                BiConsumer<ItemModelGenerator, Block> biconsumer = ItemModelGenerator.SHAPE_CONSUMERS.get(variant);
                if (biconsumer != null) {
                    biconsumer.accept(this, block);
                }

            }
        });
        for(RegistryObject<Block> item : OBlocks.BLOCKS.getEntries()) {
            if(itemSet.contains(item.get())) continue;
            Block block1 = item.get();
            if(block1 instanceof StripDyeBlock) {
                if(block1 instanceof StrippableDoorBlock) {
                    singleTexBlockItem(item, "aluminium");
                } else if(!(block1 instanceof StrippableTrapDoorBlock)) {
                    toBlock(block1);
                }
                if(block1 instanceof StrippableTrapDoorBlock) {
                    toBlockTrapdoor(block1);

                }

            }

        }

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Orenamental.MODID,"item/" + item.getId().getPath()));
    }



    private void toBlock(RegistryObject<Block> b) {
        toBlockModel(b, b.getId().getPath());
    }

    private void toBlockWall(Block block) {
        Block block1 = block;
        if(this.family != null) {
            block1 = this.family.getBaseBlock();
        }
        wallInventory(BuiltInRegistries.BLOCK.getKey(block).toString(),  prefix("block/" + BuiltInRegistries.BLOCK.getKey(block1).getPath()));
    }
    private ItemModelBuilder singleTexBlockItem(Block  item) {
        return generated(key(item).getPath(), prefix("item/" + key(item).getPath()));
    }
    private void toBlockTrapdoor(Block b) {
        String s = BuiltInRegistries.BLOCK.getKey(b).getPath();
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append("_bottom");
        toBlockModel(b, stringBuilder.toString());
    }

    private void toBlock(Block b) {
        toBlockModel(b, ForgeRegistries.BLOCKS.getKey(b).getPath());
    }

    private void toBlockItem(Block b) {
        toBlockModelItem(b, ForgeRegistries.BLOCKS.getKey(b).getPath());
    }
    private void toBlockFence(Block block) {
        Block block1 = block;
        if(this.family != null) {
            block1 = this.family.getBaseBlock();
        }
        fenceInventory(BuiltInRegistries.BLOCK.getKey(block).toString(),  prefix("block/" + BuiltInRegistries.BLOCK.getKey(block1).getPath()));
    }
    private void toBlockButton(Block block) {
        Block block1 = block;
        if(this.family != null) {
            block1 = this.family.getBaseBlock();
        }
        buttonInventory(BuiltInRegistries.BLOCK.getKey(block).toString(),  prefix("block/" + BuiltInRegistries.BLOCK.getKey(block1).getPath()));
    }
    private void toBlockModel(Block b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlock(Block b, String loc) {
        toBlockModel(b, ForgeRegistries.BLOCKS.getKey(b).getPath(), loc);
    }


    private void toBlockModel(Block b, String model, String loc) {
        String p = model.split("_")[0];
        toBlockModel(b, prefix("block/" + loc  +"/"+  p + "/" + model));
    }

    private void toBlockModelItem(Block b, String model) {
        toBlockModel(b, prefix("item/" + model));
    }
    private void toBlockModel(RegistryObject<Block> b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlockModel(RegistryObject<Block> b, ResourceLocation model) {
        withExistingParent(b.getId().getPath(), model);
    }
    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }
    private ItemModelBuilder singleTexBlockItem(RegistryObject<Block> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }


    private ItemModelBuilder singleTexBlockItem(RegistryObject<Block> item, String loc) {
        String p = item.getId().getPath().split("_")[0];
        return generated(item.getId().getPath(), prefix("item/"  + loc  +"/"+  p + "/" + item.getId().getPath()));
    }

    private ItemModelBuilder singleTexBlock(RegistryObject<Block> item, String loc) {
        String p = item.getId().getPath().split("_")[0];
        return generated(item.getId().getPath(), prefix("block/" + loc  +"/"+  p + "/" +item.getId().getPath()));
    }
    private ItemModelBuilder singleTexBlock(RegistryObject<Block> item) {
        return generated(item.getId().getPath(), prefix("block/" + item.getId().getPath()));
    }
    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder singleTexHandheld(RegistryObject<Item> item) {
        return generatedHandheld(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder generatedHandheld(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(ForgeRegistries.BLOCKS.getKey(b).getPath(), model);
    }
    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}