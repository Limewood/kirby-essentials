package com.buncord.kirbyessentials.items;

import com.buncord.kirbyessentials.models.BnuyHatModel;
import com.buncord.kirbyessentials.models.FancyShirtModel;
import com.buncord.kirbyessentials.models.PikaHatModel;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;

public class CosmeticArmorItem extends ArmorItem {

  public CosmeticArmorItem(
      ArmorMaterial armorMaterial,
      EquipmentSlot equipmentSlot,
      Properties properties
  )
  {
    super(armorMaterial, equipmentSlot, properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void initializeClient(Consumer<IItemRenderProperties> consumer)
  {
    consumer.accept(Rendering.INSTANCE);
  }

  @OnlyIn(Dist.CLIENT)
  private static final class Rendering implements IItemRenderProperties
  {
    private static final Rendering INSTANCE = new CosmeticArmorItem.Rendering();

    private final NonNullLazy<FancyShirtModel<LivingEntity>> fancyShirt = NonNullLazy.of(
        () -> new FancyShirtModel<>(getModel().bakeLayer(FancyShirtModel.LAYER_LOCATION))
    );

    private final NonNullLazy<PikaHatModel<LivingEntity>> pikaHat = NonNullLazy.of(
        () -> new PikaHatModel<>(getModel().bakeLayer(PikaHatModel.LAYER_LOCATION))
    );

    private final NonNullLazy<BnuyHatModel<LivingEntity>> bnuyHat = NonNullLazy.of(
        () -> new BnuyHatModel<>(getModel().bakeLayer(BnuyHatModel.LAYER_LOCATION))
    );

    private Rendering() {}

    @Override
    public HumanoidModel<?> getArmorModel(
        LivingEntity livingEntity,
        ItemStack itemStack,
        EquipmentSlot equipmentSlot,
        HumanoidModel<?> humanoidModel
    ) {
      Item item = itemStack.getItem();

      if (item == ModItems.FANCY_SHIRT.get()) {
        return this.fancyShirt.get();
      }

      if (item == ModItems.PIKA_HAT.get()) {
        return this.pikaHat.get();
      }

      if (item == ModItems.BNUY_HAT.get()) {
        return this.bnuyHat.get();
      }

      return null;
    }

    @OnlyIn(Dist.CLIENT)
    private static EntityModelSet getModel()
    {
      return Minecraft.getInstance().getEntityModels();
    }
  }

}
