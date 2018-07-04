package io.webnostic.citydata.model;


public class Record<T> {
    private String datasetid;
    private String recordid;
    private T fields;
    private Geometry geometry;
    private String record_timestamp;

    @Override
    public String toString() {
        return "Record{" +
            "datasetid='" + datasetid + '\'' +
            ", recordid='" + recordid + '\'' +
            ", fields=" + fields +
            ", geometry=" + geometry +
            ", record_timestamp='" + record_timestamp + '\'' +
            '}';
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getRecord_timestamp() {
        return record_timestamp;
    }

    public void setRecord_timestamp(String record_timestamp) {
        this.record_timestamp = record_timestamp;
    }
}
