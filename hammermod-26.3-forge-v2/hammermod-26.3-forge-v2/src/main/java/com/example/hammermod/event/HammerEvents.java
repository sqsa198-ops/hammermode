package com.example.hammermod.event;

import com.example.hammermod.item.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

public class HammerEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        // С зажатым Shift копаем только 1 блок
        if (player.isShiftKeyDown()) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (!(mainHand.getItem() instanceof HammerItem)) {
            return;
        }

        BlockPos initialPos = event.getPos();
        if (HARVESTED_BLOCKS.contains(initialPos)) {
            return;
        }

        Level level = player.level();
        BlockState initialState = event.getState();

        if (!mainHand.isCorrectToolForDrops(initialState)) {
            return;
        }

        float initialHardness = initialState.getDestroySpeed(level, initialPos);

        for (BlockPos targetPos : HammerItem.getBlocksToBeDestroyed(1, initialPos, player)) {
            ItemStack currentHand = player.getMainHandItem();
            if (currentHand.isEmpty() || !(currentHand.getItem() instanceof HammerItem)) {
                break;
            }

            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.isAir()) {
                continue;
            }

            float targetHardness = targetState.getDestroySpeed(level, targetPos);
            if (targetHardness < 0) {
                continue;
            }

            if (!currentHand.isCorrectToolForDrops(targetState)) {
                continue;
            }

            // Защита от эксплойта с обсидианом
            if (initialHardness > 0 && targetHardness > initialHardness * 4.0F) {
                continue;
            }

            HARVESTED_BLOCKS.add(targetPos);
            try {
                player.gameMode.destroyBlock(targetPos);
            } finally {
                HARVESTED_BLOCKS.remove(targetPos);
            }
        }
    }
}
