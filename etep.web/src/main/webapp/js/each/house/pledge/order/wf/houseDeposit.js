function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var procTaskId = getQueryString('procTaskId');
var procBizId = getQueryString('procBizid');
var procInstId = getQueryString('procInstId');

$package('IQB.houseDeposit');
IQB.houseDeposit = function(){
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
		update: function(){
			$.each($('#update-form').find('input[type = "text"]'), function(i, n){
				var value = $.trim($(n).val());
				$(n).val(value);
			});
			if($('#update-form').form('validate')){
				var transctionNo = $('#transctionNo').val();
				IQB.confirm('请确认流水号无误，确定后不可更改', function(){
					var authData= {}
					authData.procAuthType = "2";
					var variableData={};
					variableData.procApprStatus='1';
					variableData.procApprOpinion='同意';
					var procData={}
					procData.procTaskId = window.procTaskId;
					var bizData={};
					bizData.transctionNo= transctionNo;
					bizData.procBizId= window.procBizId;
					var option = {};
					option.authData=authData;
					option.variableData = variableData;
					option.bizData = bizData;
					option.procData = procData;
					
					IQB.post(urls['house'] + '/WfTask/completeProcess', option, function(result){
						if(result.success=="1") {
							var url = window.location.pathname;
							var param = window.parent.IQB.main2.fetchCache(url);
							var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
							var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
							window.parent.IQB.main2.call(callback, callback2);
						} else {
							IQB.alert(result.retUserInfo);
						}
					});
					
//					var option = {variableData: {procAuthType: '2', procApprStatus: '1', procApprOpinion: '同意'}, bizData: {transctionNo: transctionNo, procBizId: window.procBizId}, procData: {procTaskId: window.procTaskId}};
//					IQB.post(urls['house'] + '/WfTask/completeProcess', option, function(result){
//						var url = window.location.pathname;
//						var param = window.parent.IQB.main2.fetchCache(url);
//						var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
//						var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
//						window.parent.IQB.main2.call(callback, callback2);
//					});
				}, function(){
					return true;
				});
			}			
		},
		unassign: function() {
			var authData= {}
			authData.procAuthType = "2";
			var variableData={}
			var bizData={}
			var procData={}
			procData.procTaskId = procTaskId;
			var option = {};
			option.authData=authData;
			option.variableData=variableData;
			option.bizData=bizData;
			option.procData=procData;
			IQB.getById(urls['rootUrl'] + '/WfTask/unclaimProcess', option, function(result) {
				if(result.success=="1") {
					var url = window.location.pathname;
					var param = window.parent.IQB.main2.fetchCache(url);
					var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
					var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
					window.parent.IQB.main2.call(callback, callback2);
				} else {
					IQB.alert(result.retUserInfo);
				}
			});
		},
		initOrderInfo: function(){
			//订单信息相关查询
			IQB.post(urls['house'] + '/order/getOrderByBusiness', {businessId: window.procBizId}, function(result){
				/**
				 * 订单信息-申请信息
				 * */
				$('#channelCode').text(result.iqbResult.result.channelCode);//渠道名称	
				$('#orderNo').text(Formatter.ifNull(result.iqbResult.result.orderNo));//订单号 
				$('#userName').text(Formatter.ifNull(result.iqbResult.result.userName));//借款人姓名		
				$('#idCard').text(Formatter.ifNull(result.iqbResult.result.idCard));//借款人身份证号
				$('#applyMoney').text(Formatter.money(result.iqbResult.result.applyMoney) + '万元');//申请借款金额
				_this.initDictionary('applyTerm', result.iqbResult.result.applyTerm);//借款期数				
				$('#mobile').text(Formatter.ifNull(result.iqbResult.result.mobile));//借款人电话
				$('#channelUser').text(result.iqbResult.result.channelUser);//申请经纪人	
				_this.initDictionary('married', result.iqbResult.result.married);//借款人婚姻状况	
				
				/**
				 * 订单信息-核准信息
				 * */
				$('#approvalAmt').text(Formatter.money(result.iqbResult.result.approvalAmt) + '万元');//核准额度
				_this.initDictionary('approvalTerm', result.iqbResult.result.approvalTerm);//核准期限 	
				_this.initDictionary('returnType', result.iqbResult.result.returnType);//还款方式	
				$('#monthlyInterest').text(Formatter.percent(result.iqbResult.result.monthlyInterest) + '%');//月利率
				_this.initDictionary('returnSource', result.iqbResult.result.returnSource);//还款来源		
				$('#review').text(Formatter.review(result.iqbResult.result.review));//是否上评审会
				$('#reviewDesc').text(Formatter.ifNull(result.iqbResult.result.reviewDesc));
				//处理上会理由
				if(result.iqbResult.result.review == 1){
					$('#reviewDesc').parent().parent().parent().show();
				}else{
					$('#reviewDesc').parent().parent().parent().hide();					
				}
				$('#expand').text(Formatter.review(result.iqbResult.result.expand));//是否展期
				$('#expandInterest').text(Formatter.percent(result.iqbResult.result.expandInterest) + '%');//展期利率
				_this.initDictionary('expandTerm', result.iqbResult.result.expandTerm);//展期期限
				$('#defaultInterest').text(Formatter.percent(result.iqbResult.result.defaultInterest) + '%');//罚息利率
				$('#description').text(Formatter.ifNull(result.iqbResult.result.description));//备注
				
				/**
				 * 到账信息
				 * */
				$('#ensureAmt').text(Formatter.money(result.iqbResult.result.ensureAmt) + '元');//应收保证金
				
				/**
				 * 房屋信息
				 * */	
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
		initSelect: function(){
			$('select').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		},
		showFile: function(){	
			IQB.post(urls['house'] + '/upload/getFileList', {orderNo: window.procBizId, fileKind: [41]}, function(result){
				var is = false;
				$.each(result.iqbResult.result, function(i, n){
					var extensionName = Formatter.getExtensionName(n.fileName);
					if(Formatter.extensionName.doc.contain(extensionName)){
						//$('#td-' + n.fileKind).append('<div class="alert alert-warning" role="alert"><button onclick="IQB.houseDocument.removeFile(event);" filePath="' + n.filePath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['house'] + '/fileUpload/download?fileName=' + n.fileName + '&filePath=' + n.filePath + '">' + n.fileName + '</a></div>');
					}else if(Formatter.extensionName.pic.contain(extensionName)){						
						$('#btn-upload').hide();
						$('#printScreen').append('<a onclick="IQB.houseDeposit.viewerShow(event);">' + n.fileName +'</a>');
						$('#img').prop('src', urls['imgUrl'] + n.filePath);
						$('#img').prop('alt', n.fileName);
						$('#btn-remove').append('<a filePath="' + n.filePath + '" onclick="IQB.houseDeposit.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a>');						
						is = true;
					}					
				});
				if(is){
					_this.cache.viewer.img = new Viewer(document.getElementById('img'), {});
				}				
			});
		},
		uploadFile: function(){//上传文件
			var extensionName = Formatter.getExtensionName($('#file').val());
			if(Formatter.extensionName.doc.contain(extensionName)){
				$('#file').val('');
				IQB.alert('格式不支持');
			}else if(Formatter.extensionName.pic.contain(extensionName)){
				$('#btn-upload').prop('disabled', true);
				$('#btn-upload').find('span').first().removeClass('fa fa-folder-open-o').addClass('fa fa-spinner fa-pulse');
				$('#uploadForm').prop('action', urls['house'] + '/fileUpload/upload/pic/house');
				IQB.postForm($('#uploadForm'), function(result){
					var fileType = $('#file').attr('fileType');		
					var option = {};
					option.orderId = window.procBizId;
					option.fileType = 'pic';
					option.fileKind = fileType;
					option.fileName = Formatter.getFileName($('#file').val());
					option.filePath = result.iqbResult.result;
					IQB.post(urls['house'] + '/upload/uploadFile', option, function(resultInfo){
						$('#file').val('');
						$('#btn-upload').prop('disabled', false).hide();
						$('#btn-upload').find('span').first().removeClass('fa fa-spinner fa-pulse').addClass('fa fa-folder-open-o');		 
						$('#printScreen').append('<a onclick="IQB.houseDeposit.viewerShow(event);">' + option.fileName +'</a>');
						$('#img').prop('src', urls['imgUrl'] + option.filePath);
						$('#img').prop('alt', option.fileName);
						$('#btn-remove').append('<a filePath="' + option.filePath + '" onclick="IQB.houseDeposit.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a>');
						if(_this.cache.viewer.img){
							_this.cache.viewer.img.update();
						}else{
							_this.cache.viewer.img = new Viewer(document.getElementById('img'), {});
						}
					});
				});
			}else{
				$('#file').val('');
				IQB.alert('格式不支持');
			}
		},
		removeFile: function(event){
			if(event.stopPropagation){//W3C阻止冒泡方法  
				event.stopPropagation();  
			}else{//IE阻止冒泡方法   
				event.cancelBubble = true;
			}  	
			var tarent = event.currentTarget;
			$(tarent).prop('disabled', true);
			$(tarent).find('span').first().removeClass('glyphicon glyphicon-trash').addClass('fa fa-spinner fa-pulse');
			var filePath = $(tarent).attr('filePath');		
			IQB.post(urls['house'] + '/fileUpload/remove', {filePath: filePath}, function(result){
				IQB.post(urls['house'] + '/upload/deleteFile', {filePath: filePath}, function(resultInfo){
					$('#btn-upload').show();
					$('#printScreen').html('');
					$('#btn-remove').html('');
		 		});
			});		
		},
		init: function(){ 		
			_this.initOrderInfo();
			_this.initOrderHistory();
			_this.showFile();
			$('#btn-update').on('click', function(){_this.update()});
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-upload').on('click', function(){$('#file').attr('fileType', 41);$('#file').click();});
			$('#file').on('change', function(){var fileName = $('#file').val();if(fileName){_this.uploadFile();}});
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.houseDeposit.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		