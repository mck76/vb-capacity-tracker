package com.vb.converter;

import com.vb.model.CapacityRecord;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CapacityRecordToDocumentConverter implements Converter<CapacityRecord, Document> {
    @Override
    public Document convert(CapacityRecord capacityRecord) {
        Document document = new Document();
        document.put("_id", capacityRecord.getId());
        document.put("studio", capacityRecord.getStudio().getNamePretty());
        document.put("timeStamp", capacityRecord.getTimeStamp());
        document.put("capacity", capacityRecord.getCapacity());
        return document;
    }
}
