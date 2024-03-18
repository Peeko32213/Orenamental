package com.peeko32213.orenamental.datagen;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class PaletteTextureProvider implements DataProvider {

    private final PackOutput.PathProvider pathProvider;
    private final ExistingFileHelper existingFileHelper;
    private final String modid;

    public PaletteTextureProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper, String modid)
    {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures");
        this.existingFileHelper = existingFileHelper;
        this.modid = modid;
    }
    protected abstract void genTextures(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer);


    protected void genPaletteSwap(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer, ResourceLocation inputFile, String blockNameSpace, Palettes palette)
    {
        String prefix = removeTrailingUnderscore(palette.getPrefix());
        String[] fileNames = inputFile.getPath().split("/");
        String fileName = fileNames[fileNames.length -1];

        String name =  blockNameSpace+"/" + prefix + "/" + prefix + "_" + fileName;

        genTexture(consumer, name, () -> {
            NativeImage inputTexture = loadTexture(inputFile);
            palette.initializeMap(existingFileHelper);
            return inputTexture.mappedCopy(color -> {
                if(color == 0) return color;
                return palette.getSwapValue(color);
            });
        });
    }

    protected void genTexture(BiConsumer<ResourceLocation, Supplier<NativeImage>> consumer, String name, Supplier<NativeImage> supplier)
    {
        consumer.accept(new ResourceLocation(modid, name), supplier);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput)
    {
        Map<ResourceLocation, Supplier<NativeImage>> entries = new HashMap<>();

        genTextures(entries::put);

        return CompletableFuture.allOf(entries.entrySet().stream().map((entry) -> {
            var key = entry.getKey();
            var value = entry.getValue();
            Path path = this.pathProvider.file(key, "png");
            return saveOne(pOutput, value, path);
        }).toArray(CompletableFuture[]::new));
    }

    static CompletableFuture<?> saveOne(CachedOutput pOutput, Supplier<NativeImage> imageSupplier, Path pPath)
    {
        return CompletableFuture.runAsync(() -> {
            try (var image = imageSupplier.get())
            {
                var bytes = image.asByteArray();
                var hash = Hashing.sha1().hashBytes(bytes);
                pOutput.writeIfNeeded(pPath, bytes, hash);
            }
            catch (IOException ioexception)
            {
                LOGGER.error("Failed to save file to {}", pPath, ioexception);
            }
        }, Util.backgroundExecutor());
    }


    protected NativeImage loadTexture(ResourceLocation inputFile)
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


    @Override
    public String getName() {
        return "Texture gen Orenamental";
    }

    public String removeTrailingUnderscore(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '_') {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }
}
