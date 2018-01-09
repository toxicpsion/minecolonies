package com.minecolonies.api.colony;

import com.minecolonies.api.util.NBTUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.minecolonies.api.util.constant.NbtTagConstants.TAG_ID;

/**
 * Not used for now, maybe in 1.13?
 * Capability for the colony tag for chunks
 */
public interface IColonyTagCapability
{
    /**
     * Remove a colony from the list.
     * @param id the id to remove.
     * @return the capability.
     */
    @NotNull
    IColonyTagCapability removecolony(int id);

    /**
     * Set the owning colony.
     * @param id the id to set.
     * @return the capability.
     */
    @NotNull
    IColonyTagCapability setOwningColony(int id);

    /**
     * Get the owning colony.
     * @return the id of it.
     */
    int getOwningColony();

    /**
     * Get a list of all close colonies.
     * @return a list of their ids.
     */
    @NotNull
    List<Integer> getAllCloseColonies();

    /**
     * Add a new colony to the chunk.
     * @param id the id to add.
     * @return the capability.
     */
    @NotNull
    IColonyTagCapability addColony(final int id);

    /**
     * The implementation of the colonyTagCapability.
     */
    public class Impl implements IColonyTagCapability
    {
        /**
         * The list of all close colonies.
         */
        private final List<Integer> colonies = new ArrayList<>();

        /**
         * The colony owning the chunk.
         * 0 If none.
         */
        private int owningColony = 0;

        @Override
        public IColonyTagCapability addColony(final int id)
        {
            colonies.add(id);
            return this;
        }

        @Override
        public IColonyTagCapability removecolony(final int id)
        {
            colonies.add(id);
            if(owningColony == id)
            {
                this.owningColony = 0;
            }
            return this;
        }

        @Override
        public IColonyTagCapability setOwningColony(final int id)
        {
            this.owningColony = id;
            return this;
        }

        @Override
        public int getOwningColony()
        {
            return owningColony;
        }

        @NotNull
        @Override
        public List<Integer> getAllCloseColonies()
        {
            return new ArrayList<>(colonies);
        }
    }

    /**
     * The storage class of the capability.
     */
    public class Storage implements Capability.IStorage<IColonyTagCapability>
    {
        @Override
        public NBTBase writeNBT(@NotNull final Capability<IColonyTagCapability> capability, @NotNull final IColonyTagCapability instance, @Nullable final EnumFacing side)
        {
            return instance.getAllCloseColonies().stream().map(id -> Storage.write(id)).collect(NBTUtils.toNBTTagList());
        }

        @Override
        public void readNBT(@NotNull final Capability<IColonyTagCapability> capability, @NotNull final IColonyTagCapability instance,
                @Nullable final EnumFacing side, @NotNull final NBTBase nbt)
        {
            if(nbt instanceof NBTTagList)
            {
                NBTUtils.streamCompound((NBTTagList) nbt).map(compound -> compound.getInteger(TAG_ID)).forEach(instance::addColony);
            }
        }

        /**
         * Write one colony id to nbt.
         * @param id the id.
         * @return the compound of it.
         */
        private static NBTTagCompound write(@NotNull final int id)
        {
            final NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger(TAG_ID, id);
            return compound;
        }
    }
}
