package com.peeko32213.orenamental.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StrippableTrapDoorBlock extends TrapDoorBlock implements StripDyeBlock {

    private static final Map<DyeColor, BlockState> colorMap = new HashMap<>();
    private final DyeColor color;

    public StrippableTrapDoorBlock(DyeColor color, Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
        this.color = color;
        this.colorMap.put(color, this.defaultBlockState());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if(stack.getItem() instanceof DyeItem item) {
            BlockState state = this.colorMap.getOrDefault(item.getDyeColor(), OBlocks.ALUMINIUM_TRAPDOOR.get().defaultBlockState());
            BlockState state2 = state.setValue(FACING, pState.getValue(FACING)).setValue(OPEN, pState.getValue(OPEN)).setValue(WATERLOGGED, pState.getValue(WATERLOGGED)).setValue(POWERED, pState.getValue(POWERED)).setValue(HALF, pState.getValue(HALF));
            pLevel.setBlockAndUpdate(pPos, state2);
            stack.shrink(1);
            return InteractionResult.CONSUME;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            BlockState state1 = OBlocks.ALUMINIUM_TRAPDOOR.get().defaultBlockState();
            BlockState state2 = state1.setValue(FACING, state.getValue(FACING)).setValue(OPEN, state.getValue(OPEN)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(POWERED, state.getValue(POWERED)).setValue(HALF, state.getValue(HALF));
            return state2;
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
