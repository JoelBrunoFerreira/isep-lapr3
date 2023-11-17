package org.isep.Domain;

public class Local {

    private String Id;
    private double lat;
    private double lng;


    public Local(String Id, double lat, double lng){
        this.Id = Id;
        this.lat = lat;
        this.lng = lng;
    }

    private String getId(){
        return this.Id;
    }
    private double getLat(){
        return this.lat;
    }
    private double getLng(){
        return this.lng;
    }


}
