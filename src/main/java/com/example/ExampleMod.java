package com.example;

import uk.co.enderfall.sdk.api.EnderfallMod;
import uk.co.enderfall.sdk.api.ModContext;
import uk.co.enderfall.sdk.api.ResourceId;
import uk.co.enderfall.sdk.api.data.ModelSpec;
import uk.co.enderfall.sdk.api.registry.ItemSpec;

public final class ExampleMod implements EnderfallMod {
    @Override
    public void initialize(ModContext context) {
        context.items().register("example_item", ItemSpec.builder().build());
        context.dataGeneration().register(data -> {
            data.itemModel(context.id("example_item"),
                    new ModelSpec(ModelSpec.Kind.GENERATED_ITEM,
                            ResourceId.of("minecraft", "item/ender_pearl")));
            data.translation("en_us", "item.example_mod.example_item", "Example Item");
        });
        context.logger().info("example_mod initialized on {}", context.platform().targetId());
    }
}
