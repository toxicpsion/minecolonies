package com.minecolonies.coremod.entity.ai.citizen.herders;

import com.minecolonies.coremod.colony.jobs.JobSwineHerder;
import net.minecraft.entity.passive.EntityPig;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Asher on 16/9/17.
 */
public class EntityAIWorkSwineHerder extends AbstractEntityAIHerder<JobSwineHerder, EntityPig>
{
    /**
     * Max amount of animals per Hut Level.
     */
    private static final int MAX_ANIMALS_PER_LEVEL = 2;

    /**
     * Creates the abstract part of the AI.
     * Always use this constructor!
     *
     * @param job the job to fulfill
     */
    public EntityAIWorkSwineHerder(@NotNull final JobSwineHerder job)
    {
        super(job, MAX_ANIMALS_PER_LEVEL);
    }

    @Override
    public Class<EntityPig> getAnimalClass()
    {
        return EntityPig.class;
    }
}
