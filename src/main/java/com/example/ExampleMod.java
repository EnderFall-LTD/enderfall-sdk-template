package com.example;

import uk.co.enderfall.sdk.api.EnderfallMod;
import uk.co.enderfall.sdk.api.ModContext;
import uk.co.enderfall.sdk.api.registry.ItemSpec;

public final class ExampleMod implements EnderfallMod {
    @Override
    public void initialize(ModContext context) {
        context.items().register("example_item", ItemSpec.builder().build());
        context.logger().info("example_mod initialized on {}", context.platform().targetId());
    }
}
