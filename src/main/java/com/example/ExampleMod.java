package com.example;

import uk.co.enderfall.sdk.api.EnderfallMod;
import uk.co.enderfall.sdk.api.ModContext;
import uk.co.enderfall.sdk.api.ResourceId;
import uk.co.enderfall.sdk.api.data.ModelSpec;
import uk.co.enderfall.sdk.api.registry.Registration;
import uk.co.enderfall.sdk.api.registry.ItemRef;

public final class ExampleMod implements EnderfallMod {
    public static final Registration.Items ITEMS = Registration.items("example_mod");
    public static final ItemRef EXAMPLE_ITEM = ITEMS.item("example_item");

    @Override
    public void initialize(ModContext context) {
        Registration.register(context, ITEMS);
        context.dataGeneration().register(data -> {
            data.itemModel(context.id("example_item"),
                    new ModelSpec(ModelSpec.Kind.GENERATED_ITEM,
                            ResourceId.of("minecraft", "item/ender_pearl")));
            data.translation("en_us", "item.example_mod.example_item", "Example Item");
        });
        context.logger().info("example_mod initialized on {}", context.platform().targetId());
    }
}
