package net.hellspawn.archon.tech.power

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.hellspawn.archon.tech.Archontech.MOD_ID
import net.hellspawn.archon.tech.power.block.entity.GeneratorBlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModBlockEntities {
    lateinit var GENERATOR: BlockEntityType<GeneratorBlockEntity>

    fun registerAll() {
        GENERATOR = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(MOD_ID, "generator"),
            FabricBlockEntityTypeBuilder.create(
                { pos, state -> GeneratorBlockEntity(GENERATOR, pos, state) },
                ModBlocks.GENERATOR_BLOCK
            ).build()
        )
    }

    fun getTicker(): BlockEntityTicker<GeneratorBlockEntity> =
        BlockEntityTicker { world, pos, state, be -> be.tick() }
}