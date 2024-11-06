package net.licketysplitter.maplecraft.item;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.item.custom.AntlerItem;
import net.licketysplitter.maplecraft.item.custom.MapleSyrupBottleItem;
import net.licketysplitter.maplecraft.item.custom.SapBucketItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MaplecraftMod.MOD_ID);

    public static final RegistryObject<Item> SUGAR_GLASS_SHARD = ITEMS.register("sugar_glass_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAPLE_SYRUP_BOTTLE = ITEMS.register("maple_syrup_bottle",
            () -> new MapleSyrupBottleItem(new Item.Properties().food(ModFoodProperties.MAPLE_SYRUP_BOTTLE).stacksTo(16)));

    public static final RegistryObject<Item> DEER_SPAWN_EGG = ITEMS.register("deer_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DEER, 0x8a6640, 0xf2e0ca,
                    new Item.Properties()));

    public static final RegistryObject<Item> VENISON = ITEMS.register("venison",
            () -> new Item(new Item.Properties().food(ModFoodProperties.VENISON)));

    public static final RegistryObject<Item> COOKED_VENISON = ITEMS.register("cooked_venison",
            () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_VENISON)));

    /* ANTLERS */
    public static final RegistryObject<Item> TWO_POINT_ANTLER = ITEMS.register("two_point_antler",
            () -> new AntlerItem(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON), "two"));
    public static final RegistryObject<Item> FOUR_POINT_ANTLER = ITEMS.register("four_point_antler",
            () -> new AntlerItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "four"));
    public static final RegistryObject<Item> SIX_POINT_ANTLER = ITEMS.register("six_point_antler",
            () -> new AntlerItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "six"));
    public static final RegistryObject<Item> EIGHT_POINT_ANTLER = ITEMS.register("eight_point_antler",
            () -> new AntlerItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), "eight"));

    public static final RegistryObject<Item> SAP_BUCKET = ITEMS.register("sap_bucket",
            () -> new SapBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> GREEN_APPLE = ITEMS.register("green_apple",
            () -> new Item(new Item.Properties().food(ModFoodProperties.GREEN_APPLE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
