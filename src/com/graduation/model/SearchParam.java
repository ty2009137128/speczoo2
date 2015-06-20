package com.graduation.model;

public class SearchParam {
    private String planId;  //等号搜索

    private String mjd;   //MJD ;      //Local Modified Julian day
    private String mjdMin;
    private String mjdMax;

    private String ra;   //RA; //赤经
    private String raMin;
    private String raMax;

    private String dec; //DEC赤纬
    private String decMin;
    private String decMax;

    private String objtype;//OBJTYPE; //对象格式，例如：star,M31target

    private String snu;//SNU; //u 波段噪声比信号
    private String snuMin;
    private String snuMax;

    private String sng;//SNG; //g 波段噪声比信号
    private String sngMin;
    private String sngMax;

    private String snr;//SNR; //r 波段噪声比信号
    private String snrMin;
    private String snrMax;

    private String sni;//SNI; //i 波段噪声比信号
    private String sniMin;
    private String sniMax;

    private String snz;//SNZ; //z 波段噪声比信号
    private String snzMin;
    private String snzMax;

    private String clz;//CLASS ; //1维处理程序鉴定的对象的类,在数据库中为 class。

    private String z;//Z; //红移
    private String zMin;
    private String zMax;

    private String or_and = "1";//传来的参数决定是用or查询还是and查询

    private String tableName ;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOr_and() {
        return or_and;
    }

    public void setOr_and(String or_and) {
        this.or_and = or_and;
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

    public String getMjdMin() {
        return mjdMin;
    }

    public void setMjdMin(String mjdMin) {
        this.mjdMin = mjdMin;
    }

    public String getMjdMax() {
        return mjdMax;
    }

    public void setMjdMax(String mjdMax) {
        this.mjdMax = mjdMax;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getRaMin() {
        return raMin;
    }

    public void setRaMin(String raMin) {
        this.raMin = raMin;
    }

    public String getRaMax() {
        return raMax;
    }

    public void setRaMax(String raMax) {
        this.raMax = raMax;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getDecMin() {
        return decMin;
    }

    public void setDecMin(String decMin) {
        this.decMin = decMin;
    }

    public String getDecMax() {
        return decMax;
    }

    public void setDecMax(String decMax) {
        this.decMax = decMax;
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

    public String getSnuMin() {
        return snuMin;
    }

    public void setSnuMin(String snuMin) {
        this.snuMin = snuMin;
    }

    public String getSnuMax() {
        return snuMax;
    }

    public void setSnuMax(String snuMax) {
        this.snuMax = snuMax;
    }

    public String getSng() {
        return sng;
    }

    public void setSng(String sng) {
        this.sng = sng;
    }

    public String getSngMin() {
        return sngMin;
    }

    public void setSngMin(String sngMin) {
        this.sngMin = sngMin;
    }

    public String getSngMax() {
        return sngMax;
    }

    public void setSngMax(String sngMax) {
        this.sngMax = sngMax;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getSnrMin() {
        return snrMin;
    }

    public void setSnrMin(String snrMin) {
        this.snrMin = snrMin;
    }

    public String getSnrMax() {
        return snrMax;
    }

    public void setSnrMax(String snrMax) {
        this.snrMax = snrMax;
    }

    public String getSni() {
        return sni;
    }

    public void setSni(String sni) {
        this.sni = sni;
    }

    public String getSniMin() {
        return sniMin;
    }

    public void setSniMin(String sniMin) {
        this.sniMin = sniMin;
    }

    public String getSniMax() {
        return sniMax;
    }

    public void setSniMax(String sniMax) {
        this.sniMax = sniMax;
    }

    public String getSnz() {
        return snz;
    }

    public void setSnz(String snz) {
        this.snz = snz;
    }

    public String getSnzMin() {
        return snzMin;
    }

    public void setSnzMin(String snzMin) {
        this.snzMin = snzMin;
    }

    public String getSnzMax() {
        return snzMax;
    }

    public void setSnzMax(String snzMax) {
        this.snzMax = snzMax;
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

    public String getzMin() {
        return zMin;
    }

    public void setzMin(String zMin) {
        this.zMin = zMin;
    }

    public String getzMax() {
        return zMax;
    }

    public void setzMax(String zMax) {
        this.zMax = zMax;
    }


}
