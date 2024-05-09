package com.peeko32213.orenamental.datagen.loot;

import com.peeko32213.orenamental.blocks.OBlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new HashSet<>();
    private final Set<Block> usedBlocks = new HashSet<>();
    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        knownBlocks.add(pBlock);
    }

    @Override
    protected void generate() {

        //ABlockFamilies.getAllFamilies().forEach(blockFamily -> {
//
        //    this.dropSelf(blockFamily.getBaseBlock());
//
        //    for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
        //        Block block = family.getValue();
        //        Block main = blockFamily.getBaseBlock();
        //        BlockFamily.Variant variant = family.getKey();
//
        //        this.dropSelf(block);
        //        //BiConsumer<BlockTagGenerator, Block> biconsumer = BlockTagGenerator.SHAPE_CONSUMERS.get(variant);
        //        //if (biconsumer != null) {
        //        //    biconsumer.accept(this, block);
        //        //}
//
        //    }
        //});

        OBlockFamilies.getAllFamilies().forEach(blockFamily -> {

            this.dropSelf(blockFamily.getBaseBlock());

            for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
                Block block = family.getValue();
                Block main = blockFamily.getBaseBlock();
                BlockFamily.Variant variant = family.getKey();

                this.dropSelf(block);
                //BiConsumer<BlockTagGenerator, Block> biconsumer = BlockTagGenerator.SHAPE_CONSUMERS.get(variant);
                //if (biconsumer != null) {
                //    biconsumer.accept(this, block);
                //}

            }
        });
    }

    protected void dropSelf(Block pBlock) {
        usedBlocks.add(pBlock);
        this.dropOther(pBlock, pBlock);
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createMultipleDrops(Block pBlock, Item item1, Item item2) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .then(LootItem.lootTableItem(item2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
