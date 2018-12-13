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

/*var procBizId = '20160930-993216';
var procInstId = '08dd9827-86ed-11e6-b920-00163e0c0777';*/

$package('IQB.innerApproval');
IQB.innerApproval = function(){
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
		openApproveWin : function() {
			$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
		},
		closeApproveWin : function() {
			$('#approve-win').modal('hide');
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
		showImg: function(){//展示图片
			var option = {};
			option.orderId = window.procBizId;	
			option.imgType = [1, 2, 3, 4, 6, 7, 8, 9];	
			IQB.post(urls['cfm'] + '/image/getImageList', option, function(result){
				$('#projectName').text(Formatter.ifNull(result.iqbResult.projectName));
				$('#guarantee').text(Formatter.ifNull(result.iqbResult.guarantee));
				$('#guaranteeName').text(Formatter.ifNull(result.iqbResult.guaranteeName));
				$.each(result.iqbResult.result, function(i, n){
					var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
									'<a href="' + urls['imgUrl'] + n.imgPath + '" rel="prettyPhoto[one]" title="' + n.imgName + '"><img src="' + urls['imgUrl'] + n.imgPath + '" style="width: 135px; height: 135px;"></a>' +
							   '</div>';	
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
			//展示图片
			_this.showImg();
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
	IQB.innerApproval.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		