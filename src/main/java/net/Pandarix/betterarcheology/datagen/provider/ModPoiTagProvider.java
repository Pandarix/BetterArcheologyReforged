package net.Pandarix.betterarcheology.datagen.provider;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.villager.ModVillagers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class ModPoiTagProvider extends PoiTypeTagsProvider
{
    public ModPoiTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, BetterArcheology.MOD_ID, existingFileHelper);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void addTags(HolderLookup.Provider pProvider)
    {
        if(ModVillagers.ARCHEOLOGY_TABLE_POI.getKey() != null){
            this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                    .add(ModVillagers.ARCHEOLOGY_TABLE_POI.getKey());
        }
    }
}