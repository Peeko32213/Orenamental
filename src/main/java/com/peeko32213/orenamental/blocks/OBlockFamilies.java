package com.peeko32213.orenamental.blocks;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.util.Map;
import java.util.stream.Stream;

public class OBlockFamilies {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();



    private static BlockFamily.Builder familyBuilder(Block pBaseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(pBaseBlock);
        BlockFamily blockfamily = MAP.put(pBaseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(pBaseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }

    public static void registerFamilies(){
        LOGGER.info("Registering Block Families!");
    }
}
