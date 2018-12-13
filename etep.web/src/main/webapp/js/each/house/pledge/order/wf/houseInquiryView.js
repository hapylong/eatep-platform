function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var procBizId = getQueryString('procBizid');
var procInstId = getQueryString('procInstId');

$package('IQB.houseInquiryView');
IQB.houseInquiryView = function(){
	var _this = {
		cache: {
			dictTypeCode: {
				applyTerm: 'HOUSE_LOAN_TERM',
				approvalTerm: 'HOUSE_LOAN_TERM',
				expandTerm: 'HOUSE_LOAN_TERM',
				married: 'COMM_MARITAL_STATUS',
				returnType: 'HOUSE_PAYMENT_MODE',
				returnSource: 'HOUSE_PAYMENT_SOURCE',
				province: 'COMM_PROVINCE',
				city: 'COMM_CITY',
				purposes: 'HOUSE_PURPOSE',
				buildingType: 'HOUSE_PROPERTY',
				businessType: 'COMM_BIZ_TYPE',
				businessSubType: 'COMM_BIZ_SUBTYPE',
				mortageBy: 'HOUSE_MORTAGE_TYPE'
			},
			viewer: {}
		},
		viewerShow: function(event){
			$('#img').click();
		},
		initDictionary: function(id, val){
			if(val != null && val != ''){
				IQB.post(urls['house'] + '/sysDictRest/selectSysDictByType', {dictTypeCode: _this.cache.dictTypeCode[id]}, function(result){
					$.each(result.iqbResult.result, function(i, n){
						if(n.id == val){
							$('#' + id).text(n.text);
						}
					});
				});
			}			
		},	
		initOrderInfo: function(){
			//订单信息相关查询
			IQB.post(urls['house'] + '/applyMoney/getByBusiness', {businessId: window.procBizId}, function(result){				
				/**
				 * 房屋信息
				 * */
				//处理id
				_this.cache.id = result.iqbResult.result.id;
				_this.initDictionary('province', result.iqbResult.result.province);//省
				_this.initDictionary('city', result.iqbResult.result.city);//市	
				$('#communityName').text(Formatter.ifNull(result.iqbResult.result.communityName));//社区名称
				$('#shortName').text(Formatter.ifNull(result.iqbResult.result.shortName));//社区别称
				$('#address').text(Formatter.ifNull(result.iqbResult.result.address));//地址
				$('#buildingAcreage').text(Formatter.percent(result.iqbResult.result.buildingAcreage) + '㎡');//建筑面积
				$('#building').text(Formatter.ifNull(result.iqbResult.result.building));//楼栋
				$('#houseNumber').text(Formatter.ifNull(result.iqbResult.result.houseNumber));//门牌号
				_this.initDictionary('purposes', result.iqbResult.result.purposes);//规划用途
				$('#purposesDesc').text(Formatter.ifNull(result.iqbResult.result.purposesDesc));				
				_this.initDictionary('buildingType', result.iqbResult.result.buildingType);//房屋性质
				$('#buildingTypeDesc').text(Formatter.ifNull(result.iqbResult.result.buildingTypeDesc));		
				_this.initDictionary('businessType', result.iqbResult.result.businessType);//业务类型
				_this.initDictionary('businessSubType', result.iqbResult.result.businessSubType);//抵押情况
				_this.initDictionary('mortageBy', result.iqbResult.result.mortageBy);//抵押公司
				$('#mortageName').text(Formatter.ifNull(result.iqbResult.result.mortageName));//抵押公司名称
				$('#balance').text(Formatter.money(result.iqbResult.result.balance) + '万元');//一抵剩余金额
				$('#marketAssess').text(Formatter.money(result.iqbResult.result.marketAssess) + '万元');//市场估价
				$('#houseAssess').text(Formatter.money(result.iqbResult.result.houseAssess) + '万元');//房屋估值
				$('#suggestAssess').text(Formatter.money(result.iqbResult.result.suggestAssess) + '万元');//建议授信额度
				$('#actualAssess').text(Formatter.money(result.iqbResult.result.actualAssess) + '万元');//实际授信额度
				$('#houseDesc').text(Formatter.ifNull(result.iqbResult.result.houseDesc));//备注
				_this.showFile();
			});
		},
		initOrderHistory: function(){
			$('#datagrid').datagrid2({
				url: urls['house'] + '/WfTask/procApproveHistory',
				pagination: false,
				queryParams: {
					procInstId: window.procInstId
				}
			});
		},
		showFile: function(){	
			IQB.post(urls['house'] + '/upload/getFileList', {orderNo: _this.cache.id, fileKind: [61]}, function(result){
				var is = false;
				$.each(result.iqbResult.result, function(i, n){
					var extensionName = Formatter.getExtensionName(n.fileName);
					if(Formatter.extensionName.doc.contain(extensionName)){
						//$('#td-' + n.fileKind).append('<div class="alert alert-warning" role="alert"><button onclick="IQB.houseDocument.removeFile(event);" filePath="' + n.filePath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['house'] + '/fileUpload/download?fileName=' + n.fileName + '&filePath=' + n.filePath + '">' + n.fileName + '</a></div>');
					}else if(Formatter.extensionName.pic.contain(extensionName)){						
						$('#printScreen').append('<a onclick="IQB.houseInquiryView.viewerShow(event);">' + n.fileName +'</a>');
						$('#img').prop('src', urls['imgUrl'] + n.filePath);
						$('#img').prop('alt',  n.fileName);
						is = true;
					}					
				});
				if(is){
					_this.cache.viewer.img = new Viewer(document.getElementById('img'), {});
				}
			});
		},
		init: function(){ 	
			_this.initOrderInfo();
			_this.initOrderHistory();	
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.houseInquiryView.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		