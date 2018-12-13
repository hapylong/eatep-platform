
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


$package('IQB.manualApproval');
IQB.manualApproval = function(){
	var _this = {
		cache:{			
			
		},	
		approve : function() {
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
			$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
		},
		closeApproveWin: function() {
			$('#approve-win').modal('hide');
		},
		openUpdateAssessPriceWin: function(){//打开对话框	
			if($('#groupName').text() == '二手车'){
				var orderId = $('#orderId').text();
				var assessPrice = Formatter.removeMoneyPrefix($('#assessPrice').text());
				$('#updateAssessPrice-orderId').val(orderId);
				$('#updateAssessPrice-assessPrice').val(assessPrice);
				$('#update-assessPrice-win').modal({backdrop: 'static', keyboard: false, show: true});		
			}else{
				IQB.alert('新车无法修改车辆估价');
			}			
		},
		updateAssessPrice: function(){//修改订单信息				 
			$('#updateAssessPriceForm').prop('action', urls['cfm'] + '/workFlow/addAssessPrice');
			IQB.save($('#updateAssessPriceForm'), function(result){
				var assessPrice = $('#updateAssessPrice-assessPrice').val();//已经去掉两端空白
				$('#assessPrice').text(Formatter.money(assessPrice));				
				_this.closeUpdateAssessPriceWin();					
			})
		},
		closeUpdateAssessPriceWin: function(){//关闭对话框			
			$('#update-assessPrice-win').modal('hide');			
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
					$('.assessPrice-div').show();
					$('#assessPrice').text(Formatter.money(result.assessPrice));
				}
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
					var extensionName = Formatter.getExtensionName(n.imgPath);
					if(Formatter.extensionName.doc.contain(extensionName)){
						var html = '<div class="alert alert-info" role="alert"><a href="' + urls['cfm'] + '/fileUpload/download?fileName=' + n.imgName + '&filePath=' + n.imgPath + '">' + n.imgName + '</a></div>';
					}else{
						var html = '<div class="alert alert-info" role="alert"><a href="' + urls['imgUrl'] + n.imgPath + '" rel="prettyPhoto[one]" title="' + n.imgName + '">' + n.imgName + '</a></div>';
					}
					$('#td-' + n.imgType).append(html);
				});
				$('a[rel^="prettyPhoto"]').prettyPhoto();
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
			//展示文件
			_this.showFile();
			$('#btn-update-assessPrice').on('click', function(){_this.openUpdateAssessPriceWin()});
			$('#btn-save-assessPrice').on('click', function(){_this.updateAssessPrice()});
			$('#btn-close-assessPrice').on('click', function(){_this.closeUpdateAssessPriceWin()});
			$('#btn-approve').on('click', function(){_this.openApproveWin()});
			$('#btn-unassign').on('click', function(){_this.unassign()});
			$('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});
			
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.manualApproval.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		