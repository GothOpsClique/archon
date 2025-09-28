package net.hellspawn.archon.tech.power.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.storage.ReadView
import net.minecraft.storage.WriteView
import net.minecraft.util.math.BlockPos

class GeneratorBlockEntity(
    type: BlockEntityType<GeneratorBlockEntity>,
    pos: BlockPos,
    state: BlockState
) : BlockEntity(type, pos, state) {
    companion object {
        const val ENERGY_KEY = "energy"
    }

    private var energy: Int = 0

    fun tick() {
        generateEnergy()
    }

    fun generateEnergy() {
        energy += 10
        markDirty() // Mark the block entity as dirty to ensure data is saved
    }

    fun getEnergy(): Int = energy
    @Override
    override fun writeData(view: WriteView) {
        super.writeData(view)
        view.putInt(ENERGY_KEY, energy)
    }

    @Override
    override fun readData(view: ReadView) {
        super.readData(view)
        energy = view.getInt(ENERGY_KEY, 0)
    }
}