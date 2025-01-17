package net.blay09.mods.excompressum.block;

import net.blay09.mods.excompressum.ExCompressum;
import net.blay09.mods.excompressum.compat.Compat;
import net.blay09.mods.excompressum.compat.botania.BotaniaCompat;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ExCompressum.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static Block[] compressedBlocks;
    public static Block[] heavySieves;
    public static Block[] woodenCrucibles;
    public static Block[] baits;
    public static Block autoHammer;
    public static Block autoCompressedHammer;
    public static Block autoHeavySieve;
    public static Block autoSieve;
    public static Block manaSieve;
    public static Block evolvedOrechid;
    public static Block autoCompressor;
    public static Block rationingAutoCompressor;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();

        compressedBlocks = registerEnumBlock(registry, CompressedBlockType.values(), it -> CompressedBlock.namePrefix + it, CompressedBlock::new);
        heavySieves = registerEnumBlock(registry, HeavySieveType.values(), it -> it + HeavySieveBlock.nameSuffix, HeavySieveBlock::new);
        woodenCrucibles = registerEnumBlock(registry, WoodenCrucibleType.values(), it -> it + WoodenCrucibleBlock.nameSuffix, WoodenCrucibleBlock::new);
        baits = registerEnumBlock(registry, BaitType.values(), it -> it + BaitBlock.nameSuffix, BaitBlock::new);

        registry.registerAll(
                autoHammer = new AutoHammerBlock().setRegistryName(AutoHammerBlock.name),
                autoSieve = new AutoSieveBlock().setRegistryName(AutoSieveBlock.name),
                manaSieve = new ManaSieveBlock().setRegistryName(new ResourceLocation(ExCompressum.MOD_ID, "mana_sieve")),
                autoCompressedHammer = new AutoCompressedHammerBlock().setRegistryName(AutoCompressedHammerBlock.name),
                autoHeavySieve = new AutoHeavySieveBlock().setRegistryName(AutoHeavySieveBlock.name),
                autoCompressor = new AutoCompressorBlock().setRegistryName(AutoCompressorBlock.name),
                rationingAutoCompressor = new RationingAutoCompressorBlock().setRegistryName(RationingAutoCompressorBlock.name),
                evolvedOrechid = BotaniaCompat.createOrechidBlock().setRegistryName(new ResourceLocation(ExCompressum.MOD_ID, "evolved_orechid"))
        );
    }

    private static <T extends Enum<T> & IStringSerializable> Block[] registerEnumBlock(IForgeRegistry<Block> registry, T[] types, Function<String, String> nameFactory, Function<T, Block> factory) {
        Block[] blocks = new Block[types.length];
        for (T type : types) {
            blocks[type.ordinal()] = factory.apply(type).setRegistryName(nameFactory.apply(type.getString()));
        }
        registry.registerAll(blocks);
        return blocks;
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registerEnumBlockItems(registry, compressedBlocks);
        registerEnumBlockItems(registry, heavySieves);
        registerEnumBlockItems(registry, woodenCrucibles);
        registerEnumBlockItems(registry, baits);

        registry.registerAll(
                blockItem(autoHammer),
                blockItem(autoCompressedHammer),
                blockItem(autoSieve),
                blockItem(autoHeavySieve),
                blockItem(autoCompressor),
                blockItem(rationingAutoCompressor),
                blockItem(manaSieve, optionalItemProperties(BotaniaCompat.MOD_ID)),
                blockItem(evolvedOrechid, optionalItemProperties(BotaniaCompat.MOD_ID))
        );
    }

    private static void registerEnumBlockItems(IForgeRegistry<Item> registry, Block[] blocks) {
        for (Block block : blocks) {
            registry.register(blockItem(block));
        }
    }

    private static Item blockItem(Block block) {
        return blockItem(block, itemProperties());
    }

    private static Item blockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    }

    private static Item.Properties itemProperties() {
        return new Item.Properties().group(ExCompressum.itemGroup);
    }

    private static Item.Properties optionalItemProperties(String modId) {
        Item.Properties properties = new Item.Properties();
        if (ModList.get().isLoaded(modId)) {
            return properties.group(ExCompressum.itemGroup);
        }

        return properties;
    }

	/*public static void registerModels() {
		// Baits
		ResourceLocation[] baitVariants = new ResourceLocation[BaitType.values.length];
        for(int i = 0; i < baitVariants.length; i++) {
            baitVariants[i] = new ResourceLocation(ExCompressum.MOD_ID, "bait_" + BaitType.values[i].getName());
        }
        Item baitItem = Item.getItemFromBlock(bait);
        ModelBakery.registerItemVariants(baitItem, baitVariants);
        ModelLoader.setCustomMeshDefinition(baitItem, itemStack -> {
			BaitType type = itemStack.getItemDamage() >= 0 && itemStack.getItemDamage() < BaitType.values.length ? BaitType.values[itemStack.getItemDamage()] : null;
			if(type != null) {
				return new ModelResourceLocation(new ResourceLocation(ExCompressum.MOD_ID, "bait_" + type.getName()), "inventory");
			} else {
				return new ModelResourceLocation("missingno");
			}
		});

        // Compressed Blocks
		ResourceLocation[] variants = new ResourceLocation[CompressedBlockType.values.length];
		for(int i = 0; i < variants.length; i++) {
			variants[i] = new ResourceLocation(ExCompressum.MOD_ID, "compressed_" + CompressedBlockType.values[i].getName());
		}
		Item compressedBlockItem = Item.getItemFromBlock(compressedBlock);
		ModelBakery.registerItemVariants(compressedBlockItem, variants);
		ModelLoader.setCustomMeshDefinition(compressedBlockItem, itemStack -> {
			CompressedBlockType type = CompressedBlockType.fromId(itemStack.getItemDamage());
			if(type != null) {
				return new ModelResourceLocation(new ResourceLocation(ExCompressum.MOD_ID, "compressed_" + type.getName()), "inventory");
			} else {
				return new ModelResourceLocation("missingno");
			}
		});
		ModelLoader.setCustomStateMapper(compressedBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(BlockState state) {
				return new ModelResourceLocation(new ResourceLocation(ExCompressum.MOD_ID, "compressed_" + state.getValue(CompressedBlock.VARIANT).getName()), "normal");
			}
		});

		// Wooden Crucible
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(woodenCrucible), itemStack -> {
			WoodenCrucibleType type = itemStack.getItemDamage() >= 0 && itemStack.getItemDamage() < WoodenCrucibleType.values.length ? WoodenCrucibleType.values[itemStack.getItemDamage()] : null;
			if(type != null) {
				return new ModelResourceLocation(WoodenCrucibleBlock.registryName, "variant=" + type.getName());
			} else {
				return new ModelResourceLocation("missingno");
			}
		});

		// Heavy Sieve
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(heavySieve), itemStack -> {
			HeavySieveType type = itemStack.getItemDamage() >= 0 && itemStack.getItemDamage() < HeavySieveType.values.length ? HeavySieveType.values[itemStack.getItemDamage()] : null;
			if (type != null) {
				if (ExRegistro.doMeshesHaveDurability()) {
					return new ModelResourceLocation(HeavySieveBlock.registryName, "variant=" + type.getName() + ",with_mesh=false");
				} else {
					return new ModelResourceLocation(HeavySieveBlock.registryName, "variant=" + type.getName() + ",with_mesh=false"); // it's false here too because it was a dumb idea based on wrong thinking; don't want to remove it now though
				}
			} else {
				return new ModelResourceLocation("missingno");
			}
		});

		// Auto Hammer
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(autoHammer), stack -> new ModelResourceLocation(BlockAutoHammer.registryName, "facing=north,ugly=" + ((stack.getItemDamage() & 8) == 8)));
	}*/

}
