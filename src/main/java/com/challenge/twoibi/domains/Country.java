package com.challenge.twoibi.domains;

import javax.persistence.*;

@Entity(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String capital;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String subRegion;

    @Column(nullable = false)
    private double area;

    //Required by Hibernate
    public Country() {
    }

    private Country(Long id,String name, String capital, String region, String subRegion, double area) {
        this.id=id;
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subRegion = subRegion;
        this.area = area;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String capital;
        private String region;
        private String subRegion;
        private double area;

        public Builder id(Long id){
            this.id=id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder capital(String capital) {
            this.capital = capital;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder subRegion(String subRegion) {
            this.subRegion = subRegion;
            return this;
        }

        public Builder area(double area) {
            this.area = area;
            return this;
        }

        public Country build() {
            return new Country(id,name, capital, region, subRegion, area);
        }
    }

}
