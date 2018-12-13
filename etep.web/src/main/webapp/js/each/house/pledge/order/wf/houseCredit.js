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

$package('IQB.houseCredit');
IQB.houseCredit = function(){
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
		openApproveWin: function(){
			if($('#update-form').form('validate')){
				$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
			}			
		},
		approve: function(){
			var appeal = $('#appeal').val();
			var black = $('#black').val();
			var bankCredit = $('#bankCredit').val();
			var majorIssue = $('#majorIssue').val();
			var procApprStatus = $('#procApprStatus').val();
			var procApprOpinion = $.trim($('#procApprOpinion').val());
//			if(procApprStatus == 1){
//				procApprOpinion = $.trim($('#procApprOpinion').val()) || '同意';
//			}else{
//				procApprOpinion = $.trim($('#procApprOpinion').val()) || '拒绝';
//			}
			
			if (procApprStatus == "1") {
				if(procApprOpinion == '') {
					procApprOpinion = "同意";
				}
			} else {
				if(procApprOpinion == '') {
					IQB.alert('审批意见必填');
					exit;
				}
			}
			
			var authData= {}
			authData.procAuthType = "2";
			var variableData={};
			variableData.procApprStatus=procApprStatus;
			variableData.procApprOpinion=procApprOpinion;
			var procData={}
			procData.procTaskId = procTaskId;
			var bizData={};
			bizData.appeal= appeal;
			bizData.black= black;
			bizData.bankCredit= bankCredit;
			bizData.majorIssue= majorIssue;
			bizData.procBizId= procBizId;
			
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
			
//			var option = {variableData: {procAuthType: '2', procApprStatus: procApprStatus, procApprOpinion: procApprOpinion}, bizData: {appeal: appeal, black: black, bankCredit: bankCredit, majorIssue: majorIssue, procBizId: window.procBizId}, procData: {procTaskId: window.procTaskId}};
//			IQB.post(urls['house'] + '/WfTask/completeProcess', option, function(result){
//				var url = window.location.pathname;
//				var param = window.parent.IQB.main2.fetchCache(url);
//				var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
//				var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
//				window.parent.IQB.main2.call(callback, callback2);
//			});
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
		closeApproveWin : function() {
			$('#approve-win').modal('hide');
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
			IQB.post(urls['house'] + '/upload/getFileList', {orderNo: window.procBizId, fileKind: [51]}, function(result){
				var is = false;
				$.each(result.iqbResult.result, function(i, n){
					var extensionName = Formatter.getExtensionName(n.fileName);
					if(Formatter.extensionName.doc.contain(extensionName)){
						//$('#td-' + n.fileKind).append('<div class="alert alert-warning" role="alert"><button onclick="IQB.houseDocument.removeFile(event);" filePath="' + n.filePath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['house'] + '/fileUpload/download?fileName=' + n.fileName + '&filePath=' + n.filePath + '">' + n.fileName + '</a></div>');
					}else if(Formatter.extensionName.pic.contain(extensionName)){						
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
						      			'<a href="javascript:void(0)"><img data-original="' + urls['imgUrl'] + n.filePath + '" alt="' + n.fileName + '" style="width: 135px; height: 135px;"></a>' +
						      			'<div class="caption">' +
							      		 	'<h5>' + n.fileName + '</h5><h6><a filePath="' + n.filePath + '" onclick="IQB.houseCredit.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a></h6>' +
							      		'</div>' +  
						      		'</div>';
						$('#td-' + n.fileKind).append(html);
						is = true;
					}					
				});
				if(is){
					$('img').lazyload();
					_this.cache.viewer.viewerOne = new Viewer(document.getElementById('viewerOne'), {url: 'data-original'});
				}				
			});
		},
		uploadFile: function(){//上传文件
			var files = $('#file').get(0).files;
			var mark = false;
			$.each(files, function(i, n){
				var extensionName = Formatter.getExtensionName(n.name);
				if(Formatter.extensionName.doc.contain(extensionName)){
					mark = true;				
					return false;
				}else if(Formatter.extensionName.pic.contain(extensionName)){
					
				}else{
					mark = true;						
					return false;
				}
			});			
			if(mark){
				$('#file').val('');
				IQB.alert('格式不支持');
				return false;
			}
			
			$('#btn-upload').prop('disabled', true);
			$('#btn-upload').find('span').first().removeClass('fa fa-folder-open-o').addClass('fa fa-spinner fa-pulse');
			$('#uploadForm').prop('action', urls['house'] + '/fileUpload/multiUpload/pic/house');
			IQB.postForm($('#uploadForm'), function(result){
				var fileType = $('#file').attr('fileType');	
				var arr = [];
				var html = '';
				$.each(files, function(i, n){
					var option = {};
					option.orderId = window.procBizId;
					option.fileType = 'pic';
					option.fileKind = fileType;
					option.fileName = n.name;
					option.filePath = result.iqbResult.result[i];
					arr.push(option);
					html += '<div class="thumbnail float-left" style="width: 145px;">' + 
					      		'<a href="javascript:void(0)"><img src="' + urls['imgUrl'] + option.filePath + '" data-original="' + urls['imgUrl'] + option.filePath + '" alt="' + option.fileName + '" style="width: 135px; height: 135px;"></a>' +
					      		 '<div class="caption">' +
					      		 	'<h5>' + option.fileName + '</h5><h6><a filePath="' + option.filePath + '" onclick="IQB.houseCredit.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a></h6>' +
					      		 '</div>' + 
					      	 '</div>';
				});
				IQB.post(urls['house'] + '/upload/uploadFileList', {files: arr}, function(resultInfo){
					$('#file').val('');
					$('#btn-upload').prop('disabled', false);
					$('#btn-upload').find('span').first().removeClass('fa fa-spinner fa-pulse').addClass('fa fa-folder-open-o');		 
					$('#td-' + fileType).append(html);
					if(_this.cache.viewer.viewerOne){
						_this.cache.viewer.viewerOne.update();
					}else{
						_this.cache.viewer.viewerOne = new Viewer(document.getElementById('viewerOne'), {url: 'data-original'});
					}
				});
			});
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
					$(tarent).parent().parent().parent().remove();
		 		});
			});	
		},
		init: function(){ 	
			$('.panel').addClass('special-panel');
			$('a[data-toggle="tab"]').on('show.bs.tab', function(e){var target = e.target;var href = $(target).prop('href');if(href.indexOf('#order-tab') != -1){$('.panel').addClass('special-panel');}else{$('.panel').removeClass('special-panel');}});			
			
			_this.initOrderInfo();
			_this.initOrderHistory();
			_this.initSelect();	
			_this.showFile();
			$('#btn-approve').on('click', function(){_this.openApproveWin()});
			$('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});	
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-uploadTypeFiftyOne').on('click', function(){$('#file').attr('fileType', 51);$('#file').click();});
			$('#file').on('change', function(){var fileName = $('#file').val();if(fileName){_this.uploadFile();}});
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.houseCredit.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		