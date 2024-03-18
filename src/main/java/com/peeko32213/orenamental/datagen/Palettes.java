package com.peeko32213.orenamental.datagen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Palettes {
    public static final Logger LOGGER = LogUtils.getLogger();
    private Map<Integer, Integer> palleteSwapMap = new HashMap<>();
    private ResourceLocation inputFile;
    private String prefix;

    public Palettes(ResourceLocation palette, String prefix) {
        this.inputFile = palette;
        this.prefix = prefix;
    }

    public Map<Integer, Integer> getPalleteSwapMap () {
        return  palleteSwapMap;
    }

    public int getSwapValue(int val){
        if(!palleteSwapMap.containsKey(val)) {
             LOGGER.error("Missing value for {} in {}", String.valueOf(val), inputFile.toString());
             return val;
        }
        return palleteSwapMap.get(val);
    }

    public void initializeMap(ExistingFileHelper existingFileHelper) {
        if(palleteSwapMap.isEmpty()) {
            NativeImage loadedVal = loadPalette(existingFileHelper);
            extractPalette(loadedVal);
        }
    }

    protected void extractPalette(NativeImage inputTexture)
    {
        int height = inputTexture.getHeight();

        for(int i = 0; i < height; i ++) {
            int val = inputTexture.getPixelRGBA(0,i);
            int swapVal = inputTexture.getPixelRGBA(1, i);
            palleteSwapMap.put(val, swapVal);
        }
    }

    protected NativeImage loadPalette(ExistingFileHelper existingFileHelper)
    {
        try (var stream = existingFileHelper.getResource(inputFile, PackType.CLIENT_RESOURCES, ".png", "textures").open())
        {
            return NativeImage.read(stream);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public int argbToRgba(int argb) {
        int alpha = (argb >> 24) & 0xFF;
        int red = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue = argb & 0xFF;

        return (red << 24) | (green << 16) | (blue << 8) | alpha;
    }
}
