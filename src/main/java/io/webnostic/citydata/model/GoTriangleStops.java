package io.webnostic.citydata.model;

import java.util.Arrays;

public class GoTriangleStops {
    private int nhits;
    private Parameter parameters;
    private Record<Field>[] records;
    private Facet_group[] facet_groups;

    public static class Parameter {
        private String[] dataset;
        private String timezone;
        private String q;
        private String rows;
        private String format;
        private String[] facet;

        @Override
        public String toString() {
            return "Parameter{" +
                "dataset=" + Arrays.toString(dataset) +
                ", timezone='" + timezone + '\'' +
                ", q='" + q + '\'' +
                ", rows='" + rows + '\'' +
                ", format='" + format + '\'' +
                ", facet=" + Arrays.toString(facet) +
                '}';
        }

        public String[] getDataset() {
            return dataset;
        }

        public void setDataset(String[] dataset) {
            this.dataset = dataset;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getRows() {
            return rows;
        }

        public void setRows(String rows) {
            this.rows = rows;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String[] getFacet() {
            return facet;
        }

        public void setFacet(String[] facet) {
            this.facet = facet;
        }
    }

    public static class Field {
        private double stop_lat;
        private int wheelchair_boarding;
        private String stop_code;
        private double stop_lon;
        private double[] geopoint;
        private String municipality;
        private String stop_id;
        private String stop_name;


        @Override
        public String toString() {
            return "Field{" +
                "stop_lat=" + stop_lat +
                ", wheelchair_boarding=" + wheelchair_boarding +
                ", stop_code='" + stop_code + '\'' +
                ", stop_lon=" + stop_lon +
                ", geopoint=" + Arrays.toString(geopoint) +
                ", municipality='" + municipality + '\'' +
                ", stop_id='" + stop_id + '\'' +
                ", stop_name='" + stop_name + '\'' +
                '}';
        }

        public double getStop_lat() {
            return stop_lat;
        }

        public void setStop_lat(double stop_lat) {
            this.stop_lat = stop_lat;
        }

        public int getWheelchair_boarding() {
            return wheelchair_boarding;
        }

        public void setWheelchair_boarding(int wheelchair_boarding) {
            this.wheelchair_boarding = wheelchair_boarding;
        }

        public String getStop_code() {
            return stop_code;
        }

        public void setStop_code(String stop_code) {
            this.stop_code = stop_code;
        }

        public double getStop_lon() {
            return stop_lon;
        }

        public void setStop_lon(double stop_lon) {
            this.stop_lon = stop_lon;
        }

        public double[] getGeopoint() {
            return geopoint;
        }

        public void setGeopoint(double[] geopoint) {
            this.geopoint = geopoint;
        }

        public String getMunicipality() {
            return municipality;
        }

        public void setMunicipality(String municipality) {
            this.municipality = municipality;
        }

        public String getStop_id() {
            return stop_id;
        }

        public void setStop_id(String stop_id) {
            this.stop_id = stop_id;
        }

        public String getStop_name() {
            return stop_name;
        }

        public void setStop_name(String stop_name) {
            this.stop_name = stop_name;
        }
    }


    public int getNhits() {
        return nhits;
    }

    public void setNhits(int nhits) {
        this.nhits = nhits;
    }

    public Parameter getParameters() {
        return parameters;
    }

    public void setParameters(Parameter parameters) {
        this.parameters = parameters;
    }

    public Record<Field>[] getRecords() {
        return records;
    }

    public void setRecords(Record<Field>[] records) {
        this.records = records;
    }

    public Facet_group[] getFacet_groups() {
        return facet_groups;
    }

    public void setFacet_groups(Facet_group[] facet_groups) {
        this.facet_groups = facet_groups;
    }

    @Override
    public String toString() {
        return "GoTriangleStops{" +
            "nhits=" + nhits +
            ", parameters=" + parameters +
            ", records=" + Arrays.toString(records) +
            ", facet_groups=" + Arrays.toString(facet_groups) +
            '}';
    }
}
