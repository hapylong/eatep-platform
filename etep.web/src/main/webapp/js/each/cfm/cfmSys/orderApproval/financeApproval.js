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

//var procBizid = '20160930-993216';
//var procInstId = '08dd9827-86ed-11e6-b920-00163e0c0777';

$package('IQB.financeApproval');
IQB.financeApproval = function(){
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
			}else if(approveForm.approveStatus == '0') {
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
		openApproveWin: function() {
			var creditName = $('#creditName').text();
			//债权人信息完整性校验
			if(!creditName){
				IQB.alert('债权人信息不完善，无法审核');
				return false;
			}
			$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
		},
		closeApproveWin: function() {
			$('#approve-win').modal('hide');
		},
		openUpdateWin: function(){//打开对话框	
			//禁用表单验证
			$('#updateForm').form('disableValidation');
			
			var orderId = $('#orderId').text();
			var creditName =$('#creditName').text();
			var creditCardNo = $('#creditCardNo').text();
			var creditBankCard = $('#creditBankCard').text();
			var creditBank = $('#creditBank').text();
			var creditPhone = $('#creditPhone').text();
			
			$('#update-orderId').val(orderId);
			$('#update-creditName').val(creditName);
			$('#update-creditCardNo').val(creditCardNo);
			$('#update-creditBankCard').val(creditBankCard);
			if(creditBank){
				$('#update-creditBank').val(creditBank).trigger('change');
			}			
			$('#update-creditPhone').val(creditPhone);
			$('#update-win').modal({backdrop: 'static', keyboard: false, show: true});	
			
			//启用表单验证
			$('#updateForm').form('enableValidation');
		},
		update: function(){//修改订单信息		
			var creditName = $('#creditName').text();
			if(creditName){
				$('#updateForm').prop('action', urls['cfm'] + '/creditorInfo/updateCreditorInfo');
			}else{
				$('#updateForm').prop('action', urls['cfm'] + '/creditorInfo/saveCreditorInfo');				
			}			
			IQB.save($('#updateForm'), function(result){
				_this.closeUpdateWin();
				_this.initApprovalTask();				
			})
		},
		closeUpdateWin: function(){//关闭对话框			
			$('#update-win').modal('hide');			
		},
		initApprovalTask: function(){//初始化流程任务
			IQB.post(urls['cfm'] + '/workFlow/getArtificialCheck', {orderId: window.procBizId}, function(result){
				$('#merchName').text(result.merchName);
				$('#realName').text(result.realName);
				$('#regId').text(result.regId);
				$('#orderId').text(result.orderId);
				$('#groupName').text(Formatter.groupName(result.groupName));
				//处理车辆估价
				if(result.groupName == 'new'){
					$('.assessPrice-div').hide();
				}else{
					$('#assessPrice').text(Formatter.money(result.assessPrice));
					$('.assessPrice-div').show();
				}
				$('#orderAmt').text(Formatter.money(result.orderAmt));
				$('#planFullName').text(result.planFullName);
				$('#planId').text(result.planId);
				$('#chargeWay').text(Formatter.chargeWay(result.chargeWay));
				$('#preAmount').text(Formatter.money(result.preAmount));
				$('#downPayment').text(Formatter.money(result.downPayment));
				$('#margin').text(Formatter.money(result.margin));
				$('#serviceFee').text(Formatter.money(result.serviceFee));
				$('#feeAmount').text(Formatter.money(result.feeAmount));
				$('#orderItems').text(result.orderItems);
				$('#monthInterest').text(Formatter.money(result.monthInterest));
				$('#riskStatus').text(Formatter.riskStatus(result.riskStatus));
				//处理支付结果
				if(result.chargeWay == 1){
					$('#btn-upload').prop('disabled', false);
					$('.isPay-table').show();
					$('.isPay-div').hide();	
					$('.outOrderId-div').hide();					
				}else{
					$('#btn-upload').prop('disabled', true);
					$('.isPay-table').hide();
					$('.isPay-div').show();	
					$('#preAmountStatus').text(Formatter.preAmountStatus(result.preAmountStatus));
					//处理流水号
					if(result.preAmountStatus == 1){
						$('.outOrderId-div').show();	
					}else{
						$('.outOrderId-div').hide();	
					}
				}
			});
			IQB.post(urls['cfm'] + '/creditorInfo/getCreditorInfo', {orderId: window.procBizId}, function(result){
				if(result.iqbResult.result){
					$('#creditName').text(Formatter.ifNull(result.iqbResult.result.creditName));
					$('#creditCardNo').text(Formatter.ifNull(result.iqbResult.result.creditCardNo));
					$('#creditBankCard').text(Formatter.ifNull(result.iqbResult.result.creditBankCard));
					$('#creditBank').text(Formatter.ifNull(result.iqbResult.result.creditBank));
					$('#creditPhone').text(Formatter.ifNull(result.iqbResult.result.creditPhone));
				}				
			});
			IQB.post(urls['cfm'] + '/workFlow/getTranNoByOrderId', {orderId: window.procBizId}, function(result){
				if(result.iqbResult.result){
					$('#outOrderId').text(Formatter.ifNull(result.iqbResult.result.outOrderId));				
				}				
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
		initSelect: function(){//初始化select组件
			$('#update-creditBank').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});	
		},
		showImg: function(){//展示图片
			var option = {};
			option.orderId = window.procBizId;	
			option.imgType = [1, 2, 3, 4, 5, 6, 7, 8, 9];	
			IQB.post(urls['cfm'] + '/image/getImageList', option, function(result){
				$('#projectName').text(Formatter.ifNull(result.iqbResult.projectName));
				$('#guarantee').text(Formatter.ifNull(result.iqbResult.guarantee));
				$('#guaranteeName').text(Formatter.ifNull(result.iqbResult.guaranteeName));
				$.each(result.iqbResult.result, function(i, n){
					if(n.imgType != 5){
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
										'<a href="' + urls['imgUrl'] + n.imgPath + '" rel="prettyPhoto[one]" title="' + n.imgName + '"><img src="' + urls['imgUrl'] + n.imgPath + '" style="width: 135px; height: 135px;"></a>' +
								   '</div>';
					}else{
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
					      				'<a href="' + urls['imgUrl'] + n.imgPath + '" rel="prettyPhoto[two]" title="' + n.imgName + '"><img src="' + urls['imgUrl'] + n.imgPath + '" style="width: 135px; height: 135px;"></a>' +
					      				'<div class="caption">' +		
					      					'<button type="button" class="btn btn-danger btn-xs" data-loading-text="正在移除图片" autocomplete="off" imgPath="' + n.imgPath + '" onclick="IQB.financeApproval.removeImg(event);"><span class="glyphicon glyphicon-trash"></span> 移除</button>' + 	
					      				'</div>' + 
					      		   '</div>';
					}					
					$('#td-' + n.imgType).append(html);				  
				});
				$('a[rel^="prettyPhoto"]').prettyPhoto();
			});
		},
		uploadImg: function(){//上传图片
			var extensionName = Formatter.getExtensionName($('#img').val());
			if(Formatter.extensionName.pic.contain(extensionName)){
				$('#btn-upload').button('loading');
				$('#uploadForm').prop('action', urls['cfm'] + '/fileUpload/upload/pic/cfm');
				IQB.postForm($('#uploadForm'), function(result){
					$('#img').val('');
					var option = {};
					option.orderId = $('#orderId').text();
					option.imgType = 5;
					option.imgName = Formatter.getFileName($('#img').val());
					option.imgPath = result.iqbResult.result;
					IQB.post(urls['cfm'] + '/image/uploadImage', option, function(resultInfo){
						$('#btn-upload').button('reset');
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
						      			'<a href="' + urls['imgUrl'] + option.imgPath + '" rel="prettyPhoto[two]" title="' + option.imgName + '"><img src="' + urls['imgUrl'] + option.imgPath + '" style="width: 135px; height: 135px;"></a>' +
						      			'<div class="caption">' +		
						      				'<button type="button" class="btn btn-danger btn-xs" data-loading-text="正在移除图片" autocomplete="off" imgPath="' + option.imgPath + '" onclick="IQB.financeApproval.removeImg(event);"><span class="glyphicon glyphicon-trash"></span> 移除</button>' + 	
						      			'</div>' + 
						      		'</div>';
						$('#td-5').append(html);
						$('a[rel^="prettyPhoto"]').prettyPhoto();
		 			});
	 			});
			}else{
				IQB.alert('格式不支持');
			}
		},
		removeImg: function(e){//移除图片
			var tarent = e.currentTarget;
			$(tarent).button('loading');						
			var imgPath = $(tarent).attr('imgPath');					
			IQB.post(urls['cfm'] + '/fileUpload/remove', {filePath: imgPath}, function(result){
				IQB.post(urls['cfm'] + '/image/deleteImage', {imgPath: imgPath}, function(resultInfo){
					$(tarent).parent().parent().remove();
	 			});
			});			
		},
		init: function(){ 	
			//标签页样式动态处理
			$('.panel').addClass('special-panel');
			$('a[data-toggle="tab"]').on('show.bs.tab', function(e){var target = e.target;var href = $(target).prop('href');if(href.indexOf('#order-tab') != -1){$('.panel').addClass('special-panel');}else{$('.panel').removeClass('special-panel');}});
			//初始化审批任务
			_this.initApprovalTask();
			//初始化审批历史
			_this.initApprovalHistory();
			//初始化select2组件
			_this.initSelect();	
			//展示图片
			_this.showImg();
			$('#btn-update').on('click', function(){_this.openUpdateWin()});
			$('#btn-save').on('click', function(){_this.update()});
			$('#btn-close').on('click', function(){_this.closeUpdateWin()});
			$('#btn-approve').on('click', function(){_this.openApproveWin()});
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});
			$('#btn-upload').on('click', function(){$('#img').click();});
			$('#img').on('change', function(){var imgName = $('#img').val();if(imgName){_this.uploadImg();}});
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.financeApproval.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		