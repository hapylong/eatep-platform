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

$package('IQB.houseEvaluation');
IQB.houseEvaluation = function(){
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
		approve: function(){
			$.each($('#update-form').find('input[type = "text"]'), function(i, n){
				var value = $.trim($(n).val());
				$(n).val(value);
			});
			if($('#update-form').form('validate')){
				var marketAssess = $('#marketAssess').val();
				var houseAssess = $('#houseAssess').val();
				var suggestAssess = $('#suggestAssess').val();
				var actualAssess = $('#actualAssess').val();
				var houseDesc = $('#houseDesc').val();
				
				var authData= {}
				authData.procAuthType = "2";
				var variableData={};
				variableData.procApprStatus='1';
				variableData.procApprOpinion='同意';
				var procData={}
				procData.procTaskId = procTaskId;
				var bizData={};
				bizData.marketAssess= marketAssess;
				bizData.houseAssess= houseAssess;
				bizData.suggestAssess= suggestAssess;
				bizData.actualAssess= actualAssess;
				bizData.houseDesc= houseDesc;
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
				_this.showFile();
				$('#marketAssess').on('focusout', function(){
					var marketAssess = $.trim($('#marketAssess').val());
					 $('#marketAssess').val(marketAssess);
					if($('#marketAssess').validatebox('isValid')){
						var houseAssess = (marketAssess * 0.9).toFixed(2);
						$('#houseAssess').val(houseAssess);
						var suggestAssess = marketAssess;
						if(result.iqbResult.result.businessSubType == '1001'){
							suggestAssess = (suggestAssess * 0.9 * 0.7).toFixed(2);
						}else if(result.iqbResult.result.businessSubType == '1002'){
							if(result.iqbResult.result.balance == null){
								result.iqbResult.result.balance = 0;
							}
							suggestAssess = (suggestAssess * 0.9 * 0.7 - result.iqbResult.result.balance).toFixed(2);
						}else{
							suggestAssess = (suggestAssess * 0.9 * 0.7).toFixed(2);
						}
						$('#suggestAssess').val(suggestAssess);
					};
				});
				$('#marketAssess').val(Formatter.ifNull(result.iqbResult.result.marketAssess));
				$('#marketAssess').focus();
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
			IQB.post(urls['house'] + '/upload/getFileList', {orderNo: _this.cache.id, fileKind: [61]}, function(result){
				var is = false;
				$.each(result.iqbResult.result, function(i, n){
					var extensionName = Formatter.getExtensionName(n.fileName);
					if(Formatter.extensionName.doc.contain(extensionName)){
						//$('#td-' + n.fileKind).append('<div class="alert alert-warning" role="alert"><button onclick="IQB.houseDocument.removeFile(event);" filePath="' + n.filePath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['house'] + '/fileUpload/download?fileName=' + n.fileName + '&filePath=' + n.filePath + '">' + n.fileName + '</a></div>');
					}else if(Formatter.extensionName.pic.contain(extensionName)){						
						$('#btn-upload').hide();
						$('#printScreen').append('<a onclick="IQB.houseEvaluation.viewerShow(event);">' + n.fileName +'</a>');
						$('#img').prop('src', urls['imgUrl'] + n.filePath);
						$('#img').prop('alt',  n.fileName);
						$('#btn-remove').append('<a filePath="' + n.filePath + '" onclick="IQB.houseEvaluation.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a>');
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
					option.orderId = _this.cache.id;
					option.fileType = 'pic';
					option.fileKind = fileType;
					option.fileName = Formatter.getFileName($('#file').val());
					option.filePath = result.iqbResult.result;
					IQB.post(urls['house'] + '/upload/uploadFile', option, function(resultInfo){
						$('#file').val('');
						$('#btn-upload').prop('disabled', false).hide();
						$('#btn-upload').find('span').first().removeClass('fa fa-spinner fa-pulse').addClass('fa fa-folder-open-o');		 
						$('#printScreen').append('<a onclick="IQB.houseEvaluation.viewerShow(event);">' + option.fileName +'</a>');
						$('#img').prop('src', urls['imgUrl'] + option.filePath);
						$('#img').prop('alt',  option.fileName);
						$('#btn-remove').append('<a filePath="' + option.filePath + '" onclick="IQB.houseEvaluation.removeFile(event);"><span class="glyphicon glyphicon-trash"></span></a>');
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
					//$('a[rel^="prettyPhoto"]').prettyPhoto();
		 		});
			});		
		},
		init: function(){ 				
			
			_this.initOrderInfo();
			//_this.initOrderHistory();
			_this.initSelect();	
			$('#btn-approve').on('click', function(){_this.approve()});
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-upload').on('click', function(){$('#file').attr('fileType', 61);$('#file').click();});
			$('#file').on('change', function(){var fileName = $('#file').val();if(fileName){_this.uploadFile();}});
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.houseEvaluation.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		