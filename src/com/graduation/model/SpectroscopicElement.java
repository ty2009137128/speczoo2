package com.graduation.model;

public class SpectroscopicElement {
    private long recordId;
    private String planId;  //PLANID; //Plan ID in use
    private String mjd;   //MJD ;      //Local Modified Julian day
    private String ra;   //RA; //赤经
    private String dec; //DEC赤纬
    private String objtype;//OBJTYPE; //对象格式，例如：star,M31target
    private String snu;//SNU; //u 波段噪声比信号
    private String sng;//SNG; //g 波段噪声比信号
    private String snr;//SNR; //r 波段噪声比信号
    private String sni;//SNI; //i 波段噪声比信号
    private String snz;//SNZ; //z 波段噪声比信号
    private String clz;//CLASS ; //1维处理程序鉴定的对象的类,在数据库中为 class。
    private String z;//Z; //红移
    private String path;

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMjd() {
        return mjd;
    }

    public void setMjd(String mjd) {
        this.mjd = mjd;
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

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getSnu() {
        return snu;
    }

    public void setSnu(String snu) {
        this.snu = snu;
    }

    public String getSng() {
        return sng;
    }

    public void setSng(String sng) {
        this.sng = sng;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getSni() {
        return sni;
    }

    public void setSni(String sni) {
        this.sni = sni;
    }

    public String getSnz() {
        return snz;
    }

    public void setSnz(String snz) {
        this.snz = snz;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
