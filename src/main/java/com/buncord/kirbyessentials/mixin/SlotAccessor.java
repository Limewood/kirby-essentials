package com.buncord.kirbyessentials.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Slot.class)
public interface SlotAccessor {

	@Mutable
	@Accessor("x")
	void setX(int x);

	@Mutable
	@Accessor("y")
	void setY(int y);

}
