package net.blay09.mods.excompressum.newregistry.hammer;

import net.blay09.mods.excompressum.ExCompressum;
import net.blay09.mods.excompressum.api.LootTableProvider;
import net.blay09.mods.excompressum.newregistry.ExCompressumRecipe;
import net.blay09.mods.excompressum.newregistry.ModRecipeTypes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class HammerRecipe extends ExCompressumRecipe {
    public static final IRecipeType<HammerRecipe> TYPE = IRecipeType.register(ExCompressum.MOD_ID + ":hammer");

    private final Ingredient input;
    private final LootTableProvider lootTable;

    public HammerRecipe(ResourceLocation id, Ingredient input, LootTableProvider lootTable) {
        super(id, TYPE);
        this.input = input;
        this.lootTable = lootTable;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.hammerRecipe;
    }

    public Ingredient getInput() {
        return input;
    }

    public LootTableProvider getLootTable() {
        return lootTable;
    }

}
