package com.graduation.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class OmlElement {

    private Long recordId;
    private String ra;
    private String dec;
    private String snu;

    public OmlElement(Long recordId, String ra, String dec, String snu) {
        this.recordId = recordId;
        this.ra = ra;
        this.dec = dec;
        this.snu = snu;
    }

    public OmlElement() {
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getSnu() {
        return snu;
    }

    public void setSnu(String snu) {
        this.snu = snu;
    }


}
/*
ra   ------赤经
dec  -------赤纬
sn_u

recored_Id*/