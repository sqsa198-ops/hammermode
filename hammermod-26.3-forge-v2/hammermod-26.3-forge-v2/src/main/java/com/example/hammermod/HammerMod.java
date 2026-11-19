package com.example.hammermod;

import com.example.hammermod.event.HammerEvents;
import com.example.hammermod.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HammerMod.MOD_ID)
public class HammerMod {
    public static final String MOD_ID = "hammermod";

    public HammerMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Регистрация всех молотов
        ModItems.ITEMS.register(modBusGroup);

        // Добавление молотов во вкладку «Инструменты и приспособления»
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup)
                .addListener(HammerMod::addCreative);

        // Подписка на событие разрушения блока (EventBus 7)
        BlockEvent.BreakEvent.BUS.addListener(HammerEvents::onBlockBreak);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WOODEN_MINING_HAMMER.get());
            event.accept(ModItems.STONE_MINING_HAMMER.get());
            event.accept(ModItems.IRON_MINING_HAMMER.get());
            event.accept(ModItems.DIAMOND_MINING_HAMMER.get());
            event.accept(ModItems.NETHERITE_MINING_HAMMER.get());
        }
    }
}
