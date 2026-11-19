package com.example.hammermod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends Item {
    public HammerItem(Properties properties) {
        super(properties);
    }

    /**
     * Возвращает список координат блоков в области (2*range + 1) x (2*range + 1)
     * в зависимости от грани блока, по которой ударил игрок.
     */
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        double reach = player.blockInteractionRange() + 1.0;
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.scale(reach));

        BlockHitResult hitResult = player.level().clip(new ClipContext(
                eyePos,
                endPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        if (hitResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        Direction face = hitResult.getDirection();

        if (face == Direction.DOWN || face == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (x == 0 && z == 0) continue;
                    positions.add(initialPos.offset(x, 0, z));
                }
            }
        } else if (face == Direction.NORTH || face == Direction.SOUTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    if (x == 0 && y == 0) continue;
                    positions.add(initialPos.offset(x, y, 0));
                }
            }
        } else if (face == Direction.EAST || face == Direction.WEST) {
            for (int z = -range; z <= range; z++) {
                for (int y = -range; y <= range; y++) {
                    if (z == 0 && y == 0) continue;
                    positions.add(initialPos.offset(0, y, z));
                }
            }
        }

        return positions;
    }
}
