package com.example.hammermod.item;

import com.example.hammermod.HammerMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HammerMod.MOD_ID);

    public static final RegistryObject<Item> WOODEN_MINING_HAMMER =
            registerHammer("wooden_mining_hammer", ToolMaterial.WOOD, 2.0F, -3.2F, 240, false);

    public static final RegistryObject<Item> STONE_MINING_HAMMER =
            registerHammer("stone_mining_hammer", ToolMaterial.STONE, 2.0F, -3.2F, 520, false);

    public static final RegistryObject<Item> IRON_MINING_HAMMER =
            registerHammer("iron_mining_hammer", ToolMaterial.IRON, 2.0F, -3.2F, 1000, false);

    public static final RegistryObject<Item> DIAMOND_MINING_HAMMER =
            registerHammer("diamond_mining_hammer", ToolMaterial.DIAMOND, 2.0F, -3.2F, 2500, false);

    public static final RegistryObject<Item> NETHERITE_MINING_HAMMER =
            registerHammer("netherite_mining_hammer", ToolMaterial.NETHERITE, 2.0F, -3.2F, 3600, true);

    private static RegistryObject<Item> registerHammer(
            String name,
            ToolMaterial material,
            float attackDamageBonus,
            float attackSpeed,
            int durability,
            boolean fireResistant
    ) {
        return ITEMS.register(name, () -> {
            Item.Properties properties = new Item.Properties()
                    .setId(ITEMS.key(name))
                    .pickaxe(material, attackDamageBonus, attackSpeed)
                    .durability(durability);

            if (fireResistant) {
                properties.fireResistant();
            }

            return new HammerItem(properties);
        });
    }
}
