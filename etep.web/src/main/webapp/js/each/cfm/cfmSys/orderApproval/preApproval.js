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

/*var procBizId = '20161024-534020';
var procInstId = '16c323e4-99d5-11e6-b90e-00163e0036f7';*/


$package('IQB.preApproval');
IQB.preApproval = function(){
	var _this = {
		cache: {

		},
		approve: function(){
				
			
			var approveForm = $('#approveForm').serializeObject();
			if(approveForm.approveStatus == "1"){
				if($.trim(approveForm.approveOpinion) == ''){
					approveForm.approveOpinion = "同意";
				}
				var option = {};
				var variableData = {}
				variableData.procAuthType = '2';
				variableData.procApprStatus = approveForm.approveStatus;
				variableData.procApprOpinion = approveForm.approveOpinion;
				var bizData = {}
				bizData.procBizId=procBizId;
				var procData = {}
				procData.procTaskId = procTaskId;
				var option = {};
				option.variableData = variableData;
				option.bizData = bizData;
				option.procData = procData;
				IQB.post(urls['rootUrl'] + '/WfTask/completeProcess', option, function(result){
					$('#approve-win').modal('hide');
					var url = window.location.pathname;
					var param = window.parent.IQB.main2.fetchCache(url);
					var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
					var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
					window.parent.IQB.main2.call(callback, callback2);
				});
			}else if(approveForm.approveStatus == '0'){
				if($.trim(approveForm.approveOpinion) == ''){
					IQB.alert('审批意见必填');
				}else{
					var option = {};
					var variableData = {}
					variableData.procAuthType = '2';
					variableData.procApprStatus = approveForm.approveStatus;
					variableData.procApprOpinion = approveForm.approveOpinion;
					var bizData = {}
					bizData.procBizId=procBizId;
					var procData = {}
					procData.procTaskId = procTaskId;
					var option = {};
					option.variableData = variableData;
					option.bizData = bizData;
					option.procData = procData;
					IQB.post(urls['rootUrl'] + '/WfTask/completeProcess', option, function(result){
						$('#approve-win').modal('hide');
						var url = window.location.pathname;
						var param = window.parent.IQB.main2.fetchCache(url);
						var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
						var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
						window.parent.IQB.main2.call(callback, callback2);
					});
				}
				
			}
		},
		unassign: function(){
			var variableData = {}
			variableData.procAuthType = '2';
			var bizData = {}
			var procData = {}
			procData.procTaskId = procTaskId;
			var option = {};
			option.variableData = variableData;
			option.bizData = bizData;
			option.procData = procData;
			IQB.post(urls['rootUrl'] + '/WfTask/unclaimProcess', option, function(result){
				window.location.href = urls['webUrl'] + '/view/etep/wf/waitHandle.html';
			});
		},
		openApproveWin: function(){
			if($('#td-10').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			if($('#td-15').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			if($('#td-16').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			if($('#td-17').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			if($('#td-18').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			if($('#td-19').find('div').length == 0){
				IQB.alert('风控信息不完善，无法审核');
				return false;
			};
			$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
		},
		closeApproveWin: function(){
			$('#approve-win').modal('hide');
		},
		initApprovalTask: function(){//初始化流程任务
			IQB.post(urls['cfm'] + '/workFlow/getArtificialCheck', {orderId: window.procBizId}, function(result){
				$('#merchName').text(result.merchName);
				$('#realName').text(result.realName);
				$('#regId').text(result.regId);
				$('#orderId').text(result.orderId);
				$('#groupName').text(Formatter.groupName(result.groupName));
				$('#orderAmt').text(Formatter.money(result.orderAmt));
				$('#planFullName').text(result.planFullName);
				$('#chargeWay').text(Formatter.chargeWay(result.chargeWay));
				$('#preAmount').text(Formatter.money(result.preAmount));
				$('#downPayment').text(Formatter.money(result.downPayment));
				$('#margin').text(Formatter.money(result.margin));
				$('#serviceFee').text(Formatter.money(result.serviceFee));
				$('#feeAmount').text(Formatter.money(result.feeAmount));
				$('#orderItems').text(result.orderItems);
				$('#monthInterest').text(Formatter.money(result.monthInterest));
				$('#preAmountStatus').text(Formatter.preAmountStatus(result.preAmountStatus));		
				$('#riskStatus').text(Formatter.riskStatus(result.riskStatus));			
			});
		},
		initApprovalHistory: function(){//初始化流程历史
			$('#datagrid').datagrid2({
				url: urls['rootUrl'] + '/WfTask/queryHistoryHandle',
				queryParams: {
					procInstId: window.procInstId
				}
			});
		},
		showFile: function(){//展示文件
			var option = {};
			option.orderId = window.procBizId;	
			option.imgType = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21];	
			IQB.post(urls['cfm'] + '/image/getImageList', option, function(result){
				$.each(result.iqbResult.result, function(i, n){
					if(Formatter.extensionName.doc.contain(n.imgPath)){
						var html = '<div class="alert alert-info" role="alert"><button onclick="IQB.preApproval.removeFile(event);" imgPath="' + n.imgPath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['cfm'] + '/fileUpload/download?fileName=' + n.imgName + '&filePath=' + n.imgPath + '">' + n.imgName + '</a></div>';
					}else{
						var html = '<div class="alert alert-info" role="alert"><button onclick="IQB.preApproval.removeFile(event);" imgPath="' + n.imgPath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['imgUrl'] + n.imgPath + '" rel="prettyPhoto[one]" title="' + n.imgName + '">' + n.imgName + '</a></div>';
					}
					$('#td-' + n.imgType).append(html);
				});
				$('a[rel^="prettyPhoto"]').prettyPhoto();
			});
		},
		uploadFile: function(){//上传文件
			var extensionName = Formatter.getExtensionName($('#file').val());
			if(Formatter.extensionName.doc.contain(extensionName)){
				$('#btn-upload').button('loading');
				$('#uploadForm').prop('action', urls['cfm'] + '/fileUpload/upload/doc/cfm');
				IQB.postForm($('#uploadForm'), function(result){
					$('#file').val('');
					var fileType = $('#file').attr('fileType');
					var option = {};
					option.orderId = $('#orderId').text();
					option.imgType = fileType;
					option.imgName = Formatter.getFileName($('#file').val());
					option.imgPath = result.iqbResult.result;
					IQB.post(urls['cfm'] + '/image/uploadImage', option, function(resultInfo){
						$('#btn-upload').button('reset');
						var html = '<div class="alert alert-info" role="alert"><button onclick="IQB.preApproval.removeFile(event);" imgPath="' + option.imgPath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['cfm'] + '/fileUpload/download?fileName=' + option.imgName + '&filePath=' + option.imgPath + '">' + option.imgName + '</a></div>';
						$('#td-' + fileType).append(html);
					});
				});
			}else if(Formatter.extensionName.pic.contain(extensionName)){
				$('#btn-upload').button('loading');
				$('#uploadForm').prop('action', urls['cfm'] + '/fileUpload/upload/pic/cfm');
				IQB.postForm($('#uploadForm'), function(result){
					$('#file').val('');
					var fileType = $('#file').attr('fileType');
					var option = {};
					option.orderId = $('#orderId').text();
					option.imgType = fileType;
					option.imgName = Formatter.getFileName($('#file').val());
					option.imgPath = result.iqbResult.result;
					IQB.post(urls['cfm'] + '/image/uploadImage', option, function(resultInfo){
						$('#btn-upload').button('reset');
						var html = '<div class="alert alert-info" role="alert"><button onclick="IQB.preApproval.removeFile(event);" imgPath="' + option.imgPath + '" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><a href="' + urls['imgUrl'] + option.imgPath + '" rel="prettyPhoto[one]" title="' + option.imgName + '">' + option.imgName + '</a></div>';
						$('#td-' + fileType).append(html);
						$('a[rel^="prettyPhoto"]').prettyPhoto();
					});
				});
			}else{
				IQB.alert('格式不支持');
			}
		},
		removeFile: function(e){//移除文件
		    if(e.stopPropagation){//W3C阻止冒泡方法  
		    	e.stopPropagation();  
		    }else{//IE阻止冒泡方法   
		    	e.cancelBubble = true;
		    }  	
			var tarent = e.target;
			var imgPath = $(tarent).attr('imgPath');		
			IQB.post(urls['cfm'] + '/fileUpload/remove', {filePath: imgPath}, function(result){
				IQB.post(urls['cfm'] + '/image/deleteImage', {imgPath: imgPath}, function(resultInfo){
					$(tarent).parent().remove();
	 			});
			});			
		},
		init: function() {
			//标签页样式动态处理
			$('.panel').addClass('special-panel');
			$('a[data-toggle="tab"]').on('show.bs.tab', function(e){var target = e.target;var href = $(target).prop('href');if(href.indexOf('#order-tab') != -1){$('.panel').addClass('special-panel');}else{$('.panel').removeClass('special-panel');}});			
			// 初始化审批任务
			_this.initApprovalTask();
			// 初始化审批历史
			_this.initApprovalHistory();
			//展示文件
			_this.showFile();
			$('#btn-approve').on('click', function(){_this.openApproveWin()});
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});
			$('#btn-uploadTypeTen').on('click', function(){$('#img').attr('imgType', 1);$('#img').click();});
			$('#').on('click', function(){$('#img').attr('imgType', 2);$('#img').click();});
			$('#btn-uploadTypeTen').on('click', function(){$('#file').attr('fileType', 10);$('#file').click();});
			$('#btn-uploadTypeEleven').on('click', function(){$('#file').attr('fileType', 11);$('#file').click();});
			$('#btn-uploadTypeTwelve').on('click', function(){$('#file').attr('fileType', 12);$('#file').click();});
			$('#btn-uploadTypeThirteen').on('click', function(){$('#file').attr('fileType', 13);$('#file').click();});
			$('#btn-uploadTypeFourteen').on('click', function(){$('#file').attr('fileType', 14);$('#file').click();});
			$('#btn-uploadTypeFifteen').on('click', function(){$('#file').attr('fileType', 15);$('#file').click();});
			$('#btn-uploadTypeSixteen').on('click', function(){$('#file').attr('fileType', 16);$('#file').click();});
			$('#btn-uploadTypeSeventeen').on('click', function(){$('#file').attr('fileType', 17);$('#file').click();});
			$('#btn-uploadTypeEighteen').on('click', function(){$('#file').attr('fileType', 18);$('#file').click();});
			$('#btn-uploadTypeNineteen').on('click', function(){$('#file').attr('fileType', 19);$('#file').click();});
			$('#btn-uploadTypeTwenty').on('click', function(){$('#file').attr('fileType', 20);$('#file').click();});
			$('#btn-uploadTypeTwentyOne').on('click', function(){$('#file').attr('fileType', 21);$('#file').click();});
			$('#file').on('change', function(){var fileName = $('#file').val();if(fileName){_this.uploadFile();}});
			
		}
	}
	return _this;
}();

$(function() {
	//页面初始化
	IQB.preApproval.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});


