package com.vb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Document(collection = "records")
public class CapacityRecord {

    @Transient
    public static final String SEQUENCE_NAME = "records_sequence";

    @Id
    private final Long id;

    private Date timeStamp;
    @Field(targetType = FieldType.STRING)
    private VeniceBeachStudio studio;
    private Integer capacity;

    public CapacityRecord(Long id, Date timeStamp, VeniceBeachStudio studio, Integer capacity) {
        this.timeStamp = timeStamp;
        this.studio = studio;
        this.capacity = capacity;
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public VeniceBeachStudio getStudio() {
        return studio;
    }

    public void setStudio(VeniceBeachStudio studio) {
        this.studio = studio;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

}
