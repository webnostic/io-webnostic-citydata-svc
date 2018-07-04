package io.webnostic.citydata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;


public class PoliceCrimeReports {
    private int nhits;
    private Parameter parameters;
    private Record<Field>[] records;
    private Facet_group[] facet_groups;

    public static class Parameter {
        private String[] dataset;
        private String timezone;
        private String rows;
        private String format;
        @JsonProperty("geofilter.distance")
        private String[] geofilterDistance;
        private String[] facet;


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
        /**
         * calculated from ucr_code at the time of setting that property based on:
          01** - 04**: CrimeType.VIOLENT_CRIME,
          05** - 07**: CrimeType.PROPERTY_CRIME
          o8** - 99**: CrimeType.OTHER_CRIME
         */
        @JsonIgnore
        private CrimeType crimeType;
        private String dist;
        private String reportedas;
        private String inci_id;
        private String csstatusdt;
        private String hour_rept;
        @JsonIgnore
        private String ucr_type_o;
        private String monthstamp;
        private String reviewdate;
        private String csstatus;
        private String addtime;
        private String ucr_code;
        private String big_zone;
        private String dow2;
        private String dow1;
        private String date_rept;
        private String date_occu;
        private String chrgdesc;
        private String date_fnd;
        private String yearstamp;
        private double[] geo_point_2d;
        private String strdate;
        private String hour_occu;
        private String hour_fnd;

        @Override
        public String toString() {
            return "Field{" +
                "dist='" + dist + '\'' +
                ", reportedas='" + reportedas + '\'' +
                ", inci_id='" + inci_id + '\'' +
                ", csstatusdt='" + csstatusdt + '\'' +
                ", hour_rept='" + hour_rept + '\'' +
                ", monthstamp='" + monthstamp + '\'' +
                ", reviewdate='" + reviewdate + '\'' +
                ", csstatus='" + csstatus + '\'' +
                ", addtime='" + addtime + '\'' +
                ", ucr_code='" + ucr_code + '\'' +
                ", big_zone='" + big_zone + '\'' +
                ", dow2='" + dow2 + '\'' +
                ", dow1='" + dow1 + '\'' +
                ", date_rept='" + date_rept + '\'' +
                ", date_occu='" + date_occu + '\'' +
                ", chrgdesc='" + chrgdesc + '\'' +
                ", date_fnd='" + date_fnd + '\'' +
                ", yearstamp='" + yearstamp + '\'' +
                ", geo_point_2d=" + Arrays.toString(geo_point_2d) +
                ", strdate='" + strdate + '\'' +
                ", hour_occu='" + hour_occu + '\'' +
                ", hour_fnd='" + hour_fnd + '\'' +
                '}';
        }

        public CrimeType getCrimeType() {
            return crimeType;
        }

        public void setCrimeType(CrimeType crimeType) {
            this.crimeType = crimeType;
        }

        public String getDist() {
            return dist;
        }

        public void setDist(String dist) {
            this.dist = dist;
        }

        public String getReportedas() {
            return reportedas;
        }

        public void setReportedas(String reportedas) {
            this.reportedas = reportedas;
        }

        public String getInci_id() {
            return inci_id;
        }

        public void setInci_id(String inci_id) {
            this.inci_id = inci_id;
        }

        public String getCsstatusdt() {
            return csstatusdt;
        }

        public void setCsstatusdt(String csstatusdt) {
            this.csstatusdt = csstatusdt;
        }

        public String getHour_rept() {
            return hour_rept;
        }

        public void setHour_rept(String hour_rept) {
            this.hour_rept = hour_rept;
        }

        public String getUcr_type_o() {
            return ucr_type_o;
        }

        public void setUcr_type_o(String ucr_type_o) {
            this.ucr_type_o = ucr_type_o;
        }

        public String getMonthstamp() {
            return monthstamp;
        }

        public void setMonthstamp(String monthstamp) {
            this.monthstamp = monthstamp;
        }

        public String getReviewdate() {
            return reviewdate;
        }

        public void setReviewdate(String reviewdate) {
            this.reviewdate = reviewdate;
        }

        public String getCsstatus() {
            return csstatus;
        }

        public void setCsstatus(String csstatus) {
            this.csstatus = csstatus;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUcr_code() {
            return ucr_code;
        }

        public void setUcr_code(String ucr_code) {
            this.ucr_code = ucr_code;
            if(ucr_code.startsWith("01") || ucr_code.startsWith("02") || ucr_code.startsWith("03") || ucr_code.startsWith("04")){
                this.crimeType = CrimeType.VIOLENT_CRIME;
            } else if(ucr_code.startsWith("05") || ucr_code.startsWith("06") || ucr_code.startsWith("07")){
                this.crimeType = CrimeType.PROPERTY_CRIME;
            } else {
                this.crimeType = CrimeType.OTHER_CRIME;
            }
        }

        public String getBig_zone() {
            return big_zone;
        }

        public void setBig_zone(String big_zone) {
            this.big_zone = big_zone;
        }

        public String getDow2() {
            return dow2;
        }

        public void setDow2(String dow2) {
            this.dow2 = dow2;
        }

        public String getDow1() {
            return dow1;
        }

        public void setDow1(String dow1) {
            this.dow1 = dow1;
        }

        public String getDate_rept() {
            return date_rept;
        }

        public void setDate_rept(String date_rept) {
            this.date_rept = date_rept;
        }

        public String getDate_occu() {
            return date_occu;
        }

        public void setDate_occu(String date_occu) {
            this.date_occu = date_occu;
        }

        public String getChrgdesc() {
            return chrgdesc;
        }

        public void setChrgdesc(String chrgdesc) {
            this.chrgdesc = chrgdesc;
        }

        public String getDate_fnd() {
            return date_fnd;
        }

        public void setDate_fnd(String date_fnd) {
            this.date_fnd = date_fnd;
        }

        public String getYearstamp() {
            return yearstamp;
        }

        public void setYearstamp(String yearstamp) {
            this.yearstamp = yearstamp;
        }

        public double[] getGeo_point_2d() {
            return geo_point_2d;
        }

        public void setGeo_point_2d(double[] geo_point_2d) {
            this.geo_point_2d = geo_point_2d;
        }

        public String getStrdate() {
            return strdate;
        }

        public void setStrdate(String strdate) {
            this.strdate = strdate;
        }

        public String getHour_occu() {
            return hour_occu;
        }

        public void setHour_occu(String hour_occu) {
            this.hour_occu = hour_occu;
        }

        public String getHour_fnd() {
            return hour_fnd;
        }

        public void setHour_fnd(String hour_fnd) {
            this.hour_fnd = hour_fnd;
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
        return "PoliceCrimeReports{" +
            "nhits=" + nhits +
            ", parameters=" + parameters +
            ", records=" + Arrays.toString(records) +
            ", facet_groups=" + Arrays.toString(facet_groups) +
            '}';
    }
}
