package com.iqb.eatep.ec.base;

import com.iqb.etep.common.utils.Attr;

/**
 * 
 * Description: 电子合同常量类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class EcAttr extends Attr {
    
    /** 符号相关  **/
    public class SymbolConst {
        /** 下划线  **/
        public static final String UNDERLINE = "_";
    }
    
    /** 模板key相关常量  **/
    public class MapKeysConst{
        /** 业务代码  **/
        public static final String BIZ_CODE = "bizCode";
        /** 签名用户列表  **/
        public static final String SIGN_USERLIST = "signUserList";
        
        /** 上上签用户相关  **/
        public class SSQUserInfo{
            /** 上上签用户代码  **/
            public static final String SSQ_USER_CODE = "factorCode";
            /** 上上签用户类型  **/
            public static final String SSQ_USER_TYPE = "ecSignerType";
            /** 上上签用户id  **/
            public static final String SSQ_USER_ID = "ssqUid";
        }
        
        /** 上上签合同相关  **/
        public class SSQContractConst{
            /** 文件名  **/
            public static final String FILENAME = "ecFileName";
            /** 发件人信息  **/
            public static final String SENDUSER = "sendUser";
            /** 收件人信息列表  **/
            public static final String USERLIST = "receiveUserList";
            /** ecFileBlob **/
            public static final String ECFILEBLOB = "ecFileBlob";
            /** contlist **/
            public static final String CONTLIST = "contlist";
        }
        /** 模板id **/
        public static final String TEMPLATE_ID = "templateId";
    }

    /** ec模板相关常量  **/
    public class EcTemplateConst {
        /** 缓存文件名称  **/
        public static final String EC_TEMP_DEST_NAME = "tempDestName_";
        /** ec模板代码key  **/
        public static final String EC_TEMP_CODE_KEY = "ecTplCode";
        /** ec模板名称key  **/
        public static final String EC_TEMP_NAME_KEY = "ecTplName";
        /** 手动签署  **/
        public static final String EC_SIGN_TYPE_MANUAL = "1";
        /** 自动签署  **/
        public static final String EC_SIGN_TYPE_AUTO = "2";
        /** 电子合同  **/
        public static final String EC_TYPE_DZ = "dz";
        /** 纸质合同  **/
        public static final String EC_TYPE_ZZ = "zz";
    }
    
    /** ec签署人相关常量  **/
    public class EcSignerConst {
        /** 是否完成证书申请key  **/
        public static final String APPLY_CERT_COMPLETE_KEY = "isApplyCertComplete";
        /** 已经完成证书申请  **/
        public static final String APPLY_CERT_COMPLETE = "1";
        /** ec签署人代码  **/
        public static final String EC_SIGNER_CODE_KEY = "ecSignerCode";
        /** ec签署人签名地址  **/
        public static final String EC_SIGNER_SIGN_URL_KEY = "ecSignerSignUrl";
        /** ec签署人签名地址列表  **/
        public static final String EC_SIGNER_SIGN_URL_LIST_KEY = "ecSignerSignUrlList";
        /** 发件人用户使用文件类型  **/
        public static final String EC_SIGNER_USER_FILE_TYPE_LOCALHOST = "1";
        /** 当用户不存在时生成系统自动签名  **/
        public static final String EC_SIGNER_SIGN_IMAGE_TYPE = "1";
        /** 移动端签署  **/
        public static final String EC_SIGN_DEVICE_TYPE_MOBILE = "2";
    }

    /** 三方ec相关常量 **/
    public class ThirdEcConst {
        /** 上上签 **/
        public static final String SSQ = "ssq";
        /** 其他 **/
        public static final String QT = "00";
        /** 上上签相关key值常量  **/
        public class SSQKeysConst{
            /** 上上签用户id **/
            public static final String SSQ_UID = "uid";
            /** 上上签接口返回成功code **/
            public static final String SSQ_SUCC_CODE = "100000";
            /** 上上签接口返回code key值 **/
            public static final String SSQ_RET_CODE_KEY = "code";
            /** 上上签接口返回content key值 **/
            public static final String SSQ_RET_CONTENT_KEY = "content";
            /** 上上签接口返回docid key值 **/
            public static final String SSQ_RET_DOCiD_KEY = "docid";
            /** 上上签接口返回docId key值 **/
            public static final String SSQ_RET_DOCID_KEY = "docId";
            /** 上上签接口返回user key值 **/
            public static final String SSQ_RET_USER_KEY = "user";
            /** 上上签接口返回signID key值 **/
            public static final String SSQ_RET_SIGNID_KEY = "signid";
            /** 上上签接口返回info key值 **/
            public static final String SSQ_RET_INFO = "info";
            /** 上上签接口返回content key值 **/
            public static final String SSQ_RET_CONTENT = "content";
            /** 上上签接口返回continfo key值 **/
            public static final String SSQ_RET_CONTINFO = "continfo";
        }
    }
    
    /** 业务交互常量  **/
    public class BizConst{
        /** 业务id key **/
        public static final String BIZ_ID_KEY = "bizId";
        /** orgCode key **/
        public static final String ORG_CODE_KEY = "orgCode";
        /** bizType key **/
        public static final String EC_BIZ_TYPE = "bizType";
        /** notifyUrl key **/
        public static final String EC_NOTIFY_URL = "notifyUrl";
        /** taskUrl key **/
        public static final String EC_TASK_URL = "taskUrl";
        /** 合同列表 key **/
        public static final String EC_LIST_KEY = "ecList";
        /** ec代码key  **/
        public static final String EC_CODE_KEY = "ecCode";
        /** ec名称key  **/
        public static final String EC_NAME_KEY = "ecName";
        /** ec访问地址key  **/
        public static final String EC_URL_KEY = "ecUrl";
        /** ec签署人签名地址列表  **/
        public static final String EC_SIGNER_SIGN_URL_LIST_KEY = "ecSignerSignUrlList";
        /** ec用户类型  **/
        public static final String EC_SIGNER_TYPE = "ecSignerType";
        /** ec用户类型  **/
        public static final String EC_SIGNER_CODE = "ecSignerCode";
        /** ec用户类型  **/
        public static final String EC_SIGNER_NOTIFY_URL = "ecSignNotifyUrl";
        /** ec状态  **/
        public static final String EC_STATUS = "status";
        /** ec查看地址  **/
        public static final String EC_VIEW_URL = "ecViewUrl";
        /** 合同类型（dz:电子合同 zz:纸质合同）  **/
        public static final String EC_TYPE = "ecType";
        /** 签署顺序  **/
        public static final String TEMPLATE_SIGN_SEQ = "templateSignSeq";
        /** ec下载地址  **/
        public static final String EC_DOWNLOAD_URL = "ecDownloadUrl";
        /** ec下载次数  **/
        public static final String EC_DOWNLOAD_TIMES = "ecDownloadTimes";
        /** ec生成时间  **/
        public static final String EC_TIME = "ecTime";
        /** ec第三方签署id **/
        public static final String EC_TP_SIGNID = "tpSignid";
        /** ec手动签署人签署信息  **/
        public static final String EC_SIGNER_LIST = "ecSignerList";
        /** ec签署人代码  **/
        public static final String EC_SIGNER_CODE_KEY = "ecSignerCode";
        /** ec签署人签名地址  **/
        public static final String EC_SIGNER_SIGN_URL_KEY = "ecSignerSignUrl";
        /** ecTemplateAttr key  **/
        public static final String EC_TEMPLATE_ATTR = "ecTemplateAttr";
        /** EC_TEMPLATE_SIGNER_REPLACE_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_KEY = "ECSIGNER-";
        /** EC_TEMPLATE_SIGNER_REPLACE_NAME_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_NAME_KEY = "NAME-";
        /** EC_TEMPLATE_SIGNER_REPLACE_IDCARD_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_IDCARD_KEY = "IDCARD-";
        /** EC_TEMPLATE_SIGNER_REPLACE_PHONE_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_PHONE_KEY = "PHONE-";
        /** EC_TEMPLATE_SIGNER_REPLACE_ADDRESS_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_ADDRESS_KEY = "ADDRESS-";
        /** EC_TEMPLATE_SIGNER_REPLACE_EMAIL_KEY key  **/
        public static final String EC_TEMPLATE_SIGNER_REPLACE_EMAIL_KEY = "EMAIL-";
        /** EC_TEMPLATE_BUSINESS_LICENSE_NUM_KEY key  **/
        public static final String EC_TEMPLATE_BUSINESS_LICENSE_NUM_KEY = "BUSINESS_LICENSE_NUM-";
        /** EC_TEMPLATE_STORE_NAME_KEY key  **/
        public static final String EC_TEMPLATE_STORE_NAME_KEY = "STORE_NAME-";
        /** EC_TEMPLATE_OPEN_BANK_KEY key  **/
        public static final String EC_TEMPLATE_OPEN_BANK_KEY = "OPEN_BANK-";
        /** EC_TEMPLATE_BANK_CARD_NUM_KEY key  **/
        public static final String EC_TEMPLATE_BANK_CARD_NUM_KEY = "BANK_CARD_NUM-";
        /** EC_TEMPLATE_EMERGENCY_CONTRACT_KEY key  **/
        public static final String EC_TEMPLATE_EMERGENCY_CONTRACT_KEY = "EMERGENCY_CONTRACT-";
        /** EC_TEMPLATE_EMERGENCY_CONTRACT_INFO_KEY key  **/
        public static final String EC_TEMPLATE_EMERGENCY_CONTRACT_INFO_KEY = "EMERGENCY_CONTRACT_INFO-";
        /** EC_TEMPLATE_SERVICE_CALL_KEY key  **/
        public static final String EC_TEMPLATE_SERVICE_CALL_KEY = "SERVICE_CALL-";
        /** EC_SIGN_CONTRACT_NO key  **/
        public static final String EC_SIGN_CONTRACT_NO_KEY = "EC_SIGN_CONTRACT_NO-";
        /** EC_TEMPLATE_SIGNER_REPLACE_KEY key  **/
        public static final String EC_SIGNER_CERT_TYPE = "0";
        /** EC_SEAL_PAGE_NUM key  **/
        public static final String EC_SEAL_PAGE_NUM = "ecSealPageNum";
        /** EC_SEAL_POSITION_X key  **/
        public static final String EC_SEAL_POSITION_X = "ecSealPositionX";
        /** EC_SEAL_POSITION_Y key  **/
        public static final String EC_SEAL_POSITION_Y = "ecSealPositionY";
        /** EC_SIGN_TYPE key  **/
        public static final String EC_SIGN_TYPE = "signType";
        /** STORE_NO key  **/
        public static final String STORE_NO = "storeNo";
        /** WORD_TABLE_STAGES_KEY **/
        public static final String WORD_TABLE_PRE = "WORD_TABLE_";
    }
    
    /** 字典相关常量  **/
    public class DictConst{
        /** ec签署人  **/
        public static final String EC_SIGNER_IMPL_CLASS = "EC_SIGNER_IMPL_CLASS";
        /** ec签署人实现类  **/
        public static final String EC_SIGNER_TYPE = "ec_signer_type";
        /** ec签署人信息读取方式  **/
        public static final String EC_SIGNER_SOURCE = "ec_signer_source";
        /** dictTypeCode  **/
        public static final String DICT_TYPE_CODE = "dictTypeCode";
        /** 签署方类型-线上借款人  **/
        public static final String EC_SIGNER_TYPE_X = "x";
        /** 签署方类型-门店  **/
        public static final String EC_SIGNER_TYPE_3 = "3";
        /** 签署方类型-融租公司  **/
        public static final String EC_SIGNER_TYPE_2 = "2";
        /** 签署方类型-实际借款人  **/
        public static final String EC_SIGNER_TYPE_S = "s";
        /** 签署方类型-企业  **/
        public static final String EC_SIGNER_TYPE_ENTERPRISE = "2";
        /** 签署方类型-个人  **/
        public static final String EC_SIGNER_TYPE_PERSONAL = "1";
    }
    
    /** 状态常量  **/
    public class StatusAttr {
        /** 通用  是:1 否:0 **/
        public static final String COMMON_YES = "1";
        public static final String COMMON_NO = "0";
        /** 是保留类型  **/
        public static final String IS_RETAIN_YES = "1";
        /** 不是保留类型  **/
        public static final String IS_RETAIN_NO = "0";
        /** 签署方状态：已注册  **/
        public static final String FACTOR_STATUS_HASREG = "1";
        /** 已完成证书申请 **/
        public static final String IS_APPLY_COMPLETE_YES = "1";
        /** 手动签署  **/
        public static final String IS_MANUAL_YES = "1";
        /** 未完成签署  **/
        public static final String EC_INFO_STATUS_UNFINISH = "0";
        /** 签署完成  **/
        public static final String EC_INFO_STATUS_SIGN_FINISH = "1";
        /** 签署中  **/
        public static final String EC_INFO_STATUS_INSIGNING = "2";
        /** 签署是失败  **/
        public static final String EC_INFO_STATUS_SIGN_FAIL = "0";
        /** 异步签署成功  **/
        public static final String EC_ASYN_SIGN_SUCC = "1";
    }

}
