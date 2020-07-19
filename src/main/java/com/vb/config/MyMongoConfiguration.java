package com.vb.config;

import com.vb.converter.CapacityRecordToDocumentConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

public class MyMongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    public String getDatabaseName() {
        return "database";
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
        adapter.registerConverter(new CapacityRecordToDocumentConverter());
    }
}
