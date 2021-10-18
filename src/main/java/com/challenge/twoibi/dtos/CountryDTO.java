package com.challenge.twoibi.dtos;

public class CountryDTO {
    private Long id;
    private String name;
    private String capital;
    private String region;
    private String subRegion;
    private String area;

    public CountryDTO() {
    }

    private CountryDTO(Long id,String name, String capital, String region, String subRegion, String area) {
        this.id = id;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public static class Builder{
        private Long id;
        private String name;
        private String capital;
        private String region;
        private String subRegion;
        private String area;

        public Builder id(Long id){
            this.id=id;
            return this;
        }

        public Builder name(String name){
            this.name=name;
            return this;
        }

        public Builder capital(String capital){
            this.capital=capital;
            return this;
        }

        public Builder region(String region){
            this.region=region;
            return this;
        }

        public Builder subRegion(String subRegion){
            this.subRegion=subRegion;
            return this;
        }
        public Builder area(String area){
            this.area=area;
            return this;
        }


        public CountryDTO build(){
            return new CountryDTO(this.id,this.name,this.capital,this.region,this.subRegion, this.area);
        }

    }
}
