package com.iqb.eatep.ec.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.iqb.etep.common.base.CommonReturnInfo;
import com.iqb.etep.common.base.IReturnInfo;

/**
 * 
 * Description: 电子合同返回码
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public enum EcReturnInfo implements IReturnInfo{
    
    /** 通用异常0100  **/
    EC_COMMON_01000001("ec_common_01000001", "传入信息为空", "传入信息为空"),
    EC_COMMON_01000002("ec_common_01000002", "JSON格式转换异常", "JSON格式转换异常"),
    EC_COMMON_01000003("ec_common_01000003", "入参校验失败", "入参校验失败"),
    EC_COMMON_01000004("ec_common_01000004", "未查询到对应的信息", "未查询到对应的信息"),
    EC_COMMON_01000005("ec_common_01000005", "上上签注册或上传截图失败", "上上签注册或上传截图失败"),
    

    /** 合同解析异常0101  **/
    EC_POI_01010001("ec_poi_01010001", "传入信息为空", "传入信息为空"),
    EC_POI_01010002("ec_poi_01010002", "JSON格式转换异常", "JSON格式转换异常"),
    EC_POI_01010003("ec_poi_01010003", "没有找到对应的ec通道", "没有找到对应的ec通道"),
    EC_POI_01010004("ec_poi_01010004", "没有找到对应的合同模板", "没有找到对应的合同模板"),
    EC_POI_01010005("ec_poi_01010005", "传入文件地址无效", "传入文件地址无效"),
    EC_POI_01010006("ec_poi_01010006", "文档转换html发生异常", "文档转换html发生异常"),

    /** ec日志相关  **/
    EC_LOG_01020001("ec_log_01020001", "json转换字符串为空", "json转换字符串为空"),
    
    /** ec业务配置相关  **/
    EC_BIZ_01030001("ec_biz_01030001", "ec根据传入条件查询模板列表", "ec根据传入条件查询模板列表"),
    EC_BIZ_01030002("ec_biz_01030002", "保存业务配置请求数据不完整", "保存业务配置请求数据不完整"),
    EC_BIZ_01030003("ec_biz_01030003", "业务配置code重复", "业务配置code重复"),
    EC_BIZ_01030004("ec_biz_01030004", "上上签异步通知消息处理失败", "上上签异步通知消息处理失败"),
    EC_BIZ_01030005("ec_biz_01030005", "从数据字典获取ec接口实现类异常", "从数据字典获取ec接口实现类异常"),
    EC_BIZ_01030006("ec_biz_01030006", "从业务实现类中获取签署人信息异常,请检查是否配置了相关签署方信息", "从业务实现类中获取签署人信息异常,请检查是否配置了相关签署方信息"),
    EC_BIZ_01030007("ec_biz_01030007", "生成电子合同传入参数校验异常", "生成电子合同传入参数校验异常"),
    EC_BIZ_01030008("ec_biz_01030008", "生成电子合同未获取到对应数据", "生成电子合同未获取到对应数据"),
    EC_BIZ_01030009("ec_biz_01030009", "生成电子合同预览地址异常", "生成电子合同预览地址异常"),
    EC_BIZ_01030010("ec_biz_01030010", "传入的用户信息列表用户类型为空", "传入的用户信息列表用户类型为空"),
    EC_BIZ_01030011("ec_biz_01030011", "根据bizid未查询到电子合同信息列表", "根据bizid未查询到电子合同信息列表"),
    EC_BIZ_01030012("ec_biz_01030012", "根据bizid未查询到电子合同签署人信息列表", "根据bizid未查询到电子合同签署人信息列表"),
    EC_BIZ_01030013("ec_biz_01030013", "执行用户注册未查询到用户信息", "执行用户注册未查询到用户信息"),
    EC_BIZ_01030014("ec_biz_01030014", "查询合同接口传入参数校验失败", "查询合同接口传入参数校验失败"),
    EC_BIZ_01030015("ec_biz_01030015", "调用上上签注册接口入参校验失败 ", "调用上上签注册接口入参校验失败"),
    EC_BIZ_01030016("ec_biz_01030016", "模板替换发生异常 ", "模板替换发生异常"),
    EC_BIZ_01030017("ec_biz_01030017", "获取签章位置异常 ", "获取签章位置异常"),
    EC_BIZ_01030018("ec_biz_01030018", "获取签署人类型失败 ", "获取签署人类型失败"),
    EC_BIZ_01030019("ec_biz_01030019", "回调业务系统异常 ", "回调业务系统异常"),
    EC_BIZ_01030020("ec_biz_01030020", "判断是否需要签署电子合同", "判断是否需要签署电子合同"),
    EC_BIZ_01030021("ec_biz_01030021", "判断是否需要签署电子合同:传入的业务类型为空", "判断是否需要签署电子合同:传入的业务类型为空"),
    EC_BIZ_01030022("ec_biz_01030022", "判断是否需要签署电子合同:传入的机构代码为空", "判断是否需要签署电子合同:传入的机构代码为空"),
    EC_BIZ_01030023("ec_biz_01030023", "该信息已在上上签进行了注册", "该信息已在上上签进行了注册"),
    EC_BIZ_01030024("ec_biz_01030024", "传入的查询时间有误", "传入的查询时间有误"),
    EC_BIZ_01030025("ec_biz_01030025", "传入的合同唯一标识有误", "传入的合同唯一标识有误"),
    EC_BIZ_01030026("ec_biz_01030026", "未传递门店编号", "未传递门店编号"),
    EC_BIZ_01030027("ec_biz_01030027", "合同文件缺失", "合同文件缺失"),
    
    /** 上上签服务相关  **/
    EC_SSQ_01030001("ec_ssq_01030001", "请求上上签服务参数校验失败", "请求上上签服务参数校验失败"),
    EC_SSQ_01030002("ec_ssq_01030002", "上上签服务网络连接异常", "上上签服务网络连接异常"),
    
    
      
    EC_SSQ_01030006("ec_ssq_01030006", "不是该合同签署人,无法签署", "不是该合同签署人,无法签署"),//120111
    EC_SSQ_01030003("ec_ssq_01030003", "已经签署,无法再次签署", "已经签署,无法再次签署"),//120122
    //合同已结束
    EC_SSQ_01030004("ec_ssq_01030004", "签署人已存在,无法重复添加", "签署人已存在,无法重复添加"),//120150
    EC_SSQ_01030005("ec_ssq_01030005", "合同未完成签署,无法结束", "合同未完成签署,无法结束"),//120241
    
    /** 业务签名流程相关  **/
    EC_DO_SIGN_01050001("ec_do_sign_01050001", "传入的需要签名的用户信息不足", "传入的需要签名的用户信息不足"),
    EC_DO_SIGN_01050002("ec_do_sign_01050002", "调用上上签用户注册接口返回失败信息", "调用上上签用户注册接口返回失败信息"),
    EC_DO_SIGN_01050003("ec_do_sign_01050003", "未查询到签署方用户信息", "未查询到签署方用户信息"),
    EC_DO_CA_01050004("ec_do_ca_01050004", "调用上上签申请个人CA证书接口返回失败信息", "调用上上签申请个人CA证书接口返回失败信息"),
    EC_DO_SSQ_01050005("ec_do_ssq_01050005", "调用上上签接口返回失败", "调用上上签接口返回失败"),
    EC_DO_SSQ_01050006("ec_do_ssq_01050006", "调用上上签接口返回content为空", "调用上上签接口返回content为空"),
    
    
    EC_POI_09010001("ec01010003", "没有找到对应的ec通道", "没有找到对应的ec通道");
    
    
    
    /** 响应代码 **/
    private String retCode = "";
    
    /** 提示信息-用户提示信息 **/
    private String retUserInfo = "";
    
    /** 响应码含义-实际响应信息 **/
    private String retFactInfo = "";
        
    /** 
     * @param retCode 响应代码
     * @param retFactInfo 响应码含义-实际响应信息 
     * @param retUserInfo  提示信息-用户提示信息
     */
    private EcReturnInfo(String retCode, String retFactInfo, String retUserInfo) {
        this.retCode = retCode;
        this.retFactInfo = retFactInfo;
        this.retUserInfo = retUserInfo;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetFactInfo() {
        return retFactInfo;
    }

    public void setRetFactInfo(String retFactInfo) {
        this.retFactInfo = retFactInfo;
    }

    public String getRetUserInfo() {
        return retUserInfo;
    }

    public void setRetUserInfo(String retUserInfo) {
        this.retUserInfo = retUserInfo;
    }
    
    /**
     * 通过响应代码 获取对应的ReturnInfo
     * @param retCode-返回码
     * @return 响应枚举类型
     */
    public IReturnInfo getReturnCodeInfoByCode(IReturnInfo returnInfo) {
        if (map.get(returnInfo.getRetCode()) != null) {
            return map.get(returnInfo.getRetCode());
        } else {
            return CommonReturnInfo.BASE00000099;
        }
    }
    
    /**
     * 重写toString
     */
    public String toString() {
        return new StringBuffer("{retCode:").append(retCode)
                .append(";retFactInfo(实际响应信息):").append(retFactInfo)
                .append(";retUserInfo(客户提示信息):").append(retUserInfo).append("}").toString();
    }

    /**存放全部枚举的缓存对象*/
    private static Map<String,EcReturnInfo> map = new HashMap<String,EcReturnInfo>();
    
    /**将所有枚举缓存*/
    static{
        EnumSet<EcReturnInfo> currEnumSet = EnumSet.allOf(EcReturnInfo.class);
        
        for (EcReturnInfo retCodeType : currEnumSet) {
            map.put(retCodeType.getRetCode(), retCodeType);
        }
    }

}
