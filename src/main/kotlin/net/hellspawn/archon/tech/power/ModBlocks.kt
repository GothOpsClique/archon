package net.hellspawn.archon.tech.power

import net.hellspawn.archon.tech.Archontech.MOD_ID
import net.hellspawn.archon.tech.power.block.GeneratorBlock
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import java.util.function.Function

object ModBlocks {
    lateinit var GENERATOR_BLOCK: Block

    fun registerAll() {
        // TODO: Change this from stone to something else
        GENERATOR_BLOCK = register(
            "generator",
            Function { settings -> GeneratorBlock(settings ?: AbstractBlock.Settings.create()) },
            AbstractBlock.Settings.copy(Blocks.STONE),
            true
        )!!
    }

    fun <T : BlockEntity> createTicker(
        type: BlockEntityType<T>,
        expectedType: BlockEntityType<T>,
        ticker: BlockEntityTicker<T>
    ): BlockEntityTicker<T>? {
        return if (type == expectedType) ticker else null
    }

    private fun register(
        name: String,
        blockFactory: Function<AbstractBlock.Settings?, Block?>,
        settings: AbstractBlock.Settings,
        shouldRegisterItem: Boolean = true
    ): Block? {
        val blockKey = keyOfBlock(name)
        val block: Block? = blockFactory.apply(settings.registryKey(blockKey))

        if (shouldRegisterItem) {
            val itemKey = keyOfItem(name)
            val blockItem = BlockItem(block, Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey())
            Registry.register(Registries.ITEM, itemKey, blockItem)
        }

        return Registry.register(Registries.BLOCK, blockKey, block)
    }

    private fun keyOfBlock(name: String): RegistryKey<Block?> {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name))
    }

    private fun keyOfItem(name: String): RegistryKey<Item?> {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name))
    }
}