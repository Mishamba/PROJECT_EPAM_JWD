package com.mishamba.project.service.former.factory;

import com.mishamba.project.service.former.builder.PartsBuilder;

public class PartsBuilderFactory {
    private PartsBuilderFactory() {}

    private static class PartsBuilderFactoryHolder {
        private static final PartsBuilderFactory HOLDER = new PartsBuilderFactory();
    }

    public static PartsBuilderFactory getInstance() {
        return PartsBuilderFactoryHolder.HOLDER;
    }

    public PartsBuilder getPartsBuilder() {
        return new PartsBuilder();
    }
}
