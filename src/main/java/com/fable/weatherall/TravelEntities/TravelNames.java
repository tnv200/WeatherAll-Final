package com.fable.weatherall.TravelEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "TravelNames")
public class TravelNames {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelid")
    private Integer travelid;
    
    @Column(name = "Travelname", nullable = false)
    private String travelname;

	

	public Integer getTravelid() {
		return travelid;
	}

	public void setTravelid(Integer travelid) {
		this.travelid = travelid;
	}

	public String getTravelname() {
		return travelname;
	}

	public void setTravelname(String travelname) {
		this.travelname = travelname;
	}

	
}
