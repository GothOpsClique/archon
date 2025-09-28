package net.hellspawn.archon.tech.power.block
import net.hellspawn.archon.tech.power.ModBlockEntities
import net.hellspawn.archon.tech.power.block.entity.GeneratorBlockEntity
import net.hellspawn.archon.tech.util.log.Log
import net.hellspawn.archon.tech.util.log.LogLevel
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import kotlin.reflect.jvm.jvmName

class GeneratorBlock(settings: Settings) : Block(settings), BlockEntityProvider {
    private val TAG = GeneratorBlock::class.jvmName
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val be = world.getBlockEntity(pos)
        if (be is GeneratorBlockEntity) {
            be.generateEnergy()
        }
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hit: BlockHitResult?
    ): ActionResult? {
        world?.let {
            if(!world.isClient) {
                Log.log(LogLevel.DEBUG, TAG, "onUse - RUNNING ON SERVER!")
            }
        }
        Log.log(LogLevel.DEBUG, TAG, "onUse - Block used at position: $pos, by player: ${player?.name}")
        return super.onUse(state, world, pos, player, hit)
    }

    override fun createBlockEntity(
        pos: BlockPos?,
        state: BlockState?
    ): BlockEntity? {
        return pos?.let { p ->
            state?.let { s ->
                Log.log(LogLevel.DEBUG, TAG, "createBlockEntity - Block created at position: $pos")
                GeneratorBlockEntity(ModBlockEntities.GENERATOR, pos, state)
            }
        }
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (type === ModBlockEntities.GENERATOR) {
            ModBlockEntities.getTicker() as BlockEntityTicker<T>
        } else null
    }
}