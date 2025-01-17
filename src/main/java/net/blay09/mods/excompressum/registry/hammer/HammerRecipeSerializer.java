package net.blay09.mods.excompressum.registry.hammer;

import com.google.gson.JsonObject;
import net.blay09.mods.excompressum.ExCompressum;
import net.blay09.mods.excompressum.registry.LootTableProvider;
import net.blay09.mods.excompressum.registry.ExCompressumRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class HammerRecipeSerializer extends ExCompressumRecipeSerializer<HammerRecipe> {

    public HammerRecipeSerializer() {
        setRegistryName(new ResourceLocation(ExCompressum.MOD_ID, "hammer"));
    }

    @Override
    public HammerRecipe readFromJson(ResourceLocation id, JsonObject json) {
        Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
        LootTableProvider lootTable = readLootTableProvider(json, "lootTable");
        return new HammerRecipe(id, input, lootTable);
    }

    @Override
    public HammerRecipe read(ResourceLocation id, PacketBuffer buffer) {
        Ingredient input = Ingredient.read(buffer);
        LootTableProvider lootTable = readLootTableProvider(buffer);
        return new HammerRecipe(id, input, lootTable);
    }

    @Override
    public void write(PacketBuffer buffer, HammerRecipe recipe) {
        recipe.getInput().write(buffer);
        writeLootTable(buffer, recipe.getRecipeId(), recipe.getLootTable());
    }

}
