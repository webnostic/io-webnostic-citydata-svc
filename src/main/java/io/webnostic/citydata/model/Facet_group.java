package io.webnostic.citydata.model;

import java.util.Arrays;


public class Facet_group {
    private String name;
    private Facet[] facets;

    @Override
    public String toString() {
        return "Facet_group{" +
            "name='" + name + '\'' +
            ", facets=" + Arrays.toString(facets) +
            '}';
    }

    private static class Facet {
        private String name;
        private String path;
        private int count;
        private String state;

        @Override
        public String toString() {
            return "Facet{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", count=" + count +
                ", state='" + state + '\'' +
                '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Facet[] getFacets() {
        return facets;
    }

    public void setFacets(Facet[] facets) {
        this.facets = facets;
    }
}
