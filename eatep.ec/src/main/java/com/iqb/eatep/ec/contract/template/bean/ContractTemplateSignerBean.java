package com.iqb.eatep.ec.contract.template.bean;
/**
 * Description: 合同模板-签署方实体类
 * 
 * @author baiaypeng
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月21日    baiaypeng       1.0        1.0 Version 
 * </pre>
 */
public class ContractTemplateSignerBean {
    private Integer id;// 物理主键
    private Integer ecTplId;// 合同模板主键
    private String ecSignerType;// 签署方类型
    private Integer ecSealPageNum;// 签章位置页码
    private Integer ecSealPositionX;// 签章位置X坐标
    private Integer ecSealPositionY;// 签章位置Y坐标
    private Integer ecSignType;// 签署类型(1 自动签署 2 手动签署)
    private Integer ecFactorSeq;// 签署权值
    private String isAutoSign;// 签署类型(已舍弃)
    private Integer ecSignerVideoType;// 视频(0 无视频 1 单项视频 2 双向视频)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEcTplId() {
        return ecTplId;
    }

    public void setEcTplId(Integer ecTplId) {
        this.ecTplId = ecTplId;
    }

    public String getEcSignerType() {
        return ecSignerType;
    }

    public void setEcSignerType(String ecSignerType) {
        this.ecSignerType = ecSignerType;
    }

    public Integer getEcSealPageNum() {
        return ecSealPageNum;
    }

    public void setEcSealPageNum(Integer ecSealPageNum) {
        this.ecSealPageNum = ecSealPageNum;
    }

    public Integer getEcSealPositionX() {
        return ecSealPositionX;
    }

    public void setEcSealPositionX(Integer ecSealPositionX) {
        this.ecSealPositionX = ecSealPositionX;
    }

    public Integer getEcSealPositionY() {
        return ecSealPositionY;
    }

    public void setEcSealPositionY(Integer ecSealPositionY) {
        this.ecSealPositionY = ecSealPositionY;
    }

    public Integer getEcSignType() {
        return ecSignType;
    }

    public void setEcSignType(Integer ecSignType) {
        this.ecSignType = ecSignType;
    }

    public Integer getEcFactorSeq() {
        return ecFactorSeq;
    }

    public void setEcFactorSeq(Integer ecFactorSeq) {
        this.ecFactorSeq = ecFactorSeq;
    }

    public String getIsAutoSign() {
        return isAutoSign;
    }

    public void setIsAutoSign(String isAutoSign) {
        this.isAutoSign = isAutoSign;
    }

    public Integer getEcSignerVideoType() {
        return ecSignerVideoType;
    }

    public void setEcSignerVideoType(Integer ecSignerVideoType) {
        this.ecSignerVideoType = ecSignerVideoType;
    }
}