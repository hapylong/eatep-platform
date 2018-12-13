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


$package('IQB.businessApproval');
IQB.businessApproval = function(){
	var _this = {
		cache: {			
			
		},	
		approve: function(){
			var approveForm = $('#approveForm').serializeObject();
			if(approveForm.approveStatus == '1'){
				if($.trim(approveForm.approveOpinion) == ''){
					console.info('if in approveOpinion:' + approveForm.approveOpinion);
					approveForm.approveOpinion = '同意';
				}
				var option = {};
				var variableData = {}
				variableData.procAuthType = '2';
				variableData.procApprStatus = approveForm.approveStatus;
				variableData.procApprOpinion = approveForm.approveOpinion;
				var bizData = {}
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
		openApproveWin: function(){
			var projectName = $('#projectName').text();
			//项目信息完整性校验
			if(!projectName){
				IQB.alert('项目信息不完善，无法审核');
				return false;
			}
			$('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
		},
		closeApproveWin : function() {
			$('#approve-win').modal('hide');
		},
		openUpdateWin: function(){//打开对话框	
			//禁用表单验证
			$('#updateForm').form('disableValidation');			
			var orderId = $('#orderId').text();
			var orderAmt = Formatter.removeMoneyPrefix($('#orderAmt').text());
			var planId = $('#planId').text();
			var chargeWay = Formatter.chargeWayReverse($('#chargeWay').text());
			$('#update-orderId').val(orderId);
			$('#update-orderAmt').val(orderAmt);
			$('#update-planId').val(planId).trigger('change');
			$('#update-chargeWay').val(chargeWay).trigger('change');
			//启用表单验证
			$('#updateForm').form('enableValidation');
			$('#update-win').modal({backdrop: 'static', keyboard: false, show: true});				
		},
		update: function(){//修改订单信息
			//表单校验
			if($('#updateForm').form('validate')){
				$('#updateForm').prop('action', urls['cfm'] + '/workFlow/modifyOrderInfo');
				IQB.save($('#updateForm'), function(result){
					_this.closeUpdateWin();
					_this.initApprovalTask();				
				})
			}			
		},
		closeUpdateWin: function(){//关闭对话框			
			$('#update-win').modal('hide');			
		},
		openUpdateProjectNameWin: function(){//打开对话框	
			//禁用表单验证
			$('#updateProjectNameForm').form('disableValidation');			
			var projectName = $('#projectName').text();
			var guarantee = $('#guarantee').text();
			if(projectName){
				$('#updateProjectName-projectName').val(projectName);
			}
			if(guarantee){
				$('#updateProjectName-guarantee').val(guarantee).trigger('change');
			}
			//启用表单验证
			$('#updateProjectNameForm').form('enableValidation');
			$('#updateProjectName-win').modal({backdrop: 'static', keyboard: false, show: true});			
		},
		updateProjectName: function(){//修改项目信息
			//表单校验
			if($('#updateProjectNameForm').form('validate')){
				var projectName = $('#updateProjectName-projectName').val();
				var guarantee = $('#updateProjectName-guarantee').val();
				
				var option = {};
				option.orderId = $('#orderId').text();
				option.projectName = projectName;
				option.guarantee = guarantee;			
				$.each(_this.cache.guaranteeArry, function(i, n){
					if(option.guarantee == n.customerName){
						option.guaranteeName =  n.corporateName;
					}
				});
				IQB.post(urls['cfm'] + '/business/newModifyOrder', option, function(resultInfo){
					_this.showImg();
					_this.closeUpdateProjectNameWin();
	 			});
			};		
		},
		closeUpdateProjectNameWin: function(){//关闭对话框			
			$('#updateProjectName-win').modal('hide');			
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
		initSelect: function(){//初始化select组件
			IQB.post(urls['cfm'] + '/workFlow/getMerAllPlan', {orderId: window.procBizId}, function(result){
				var arry = [];
				$.each(result, function(i, n){
					var obj = {};
					obj.id = n.id;
					obj.text = n.planFullName;
					arry.push(obj);
				});
				$('#update-planId').select2({theme: 'bootstrap', data: arry});
			});
			$('#update-chargeWay').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
			IQB.post(urls['cfm'] + '/customer/getCustomerInfoByCustomerType', {customerType: 5}, function(result){
				var arry = [];
				$.each(result.iqbResult.result, function(i, n){
					var obj = {};
					obj.id = n.customerName;
					obj.text = n.customerName;
					arry.push(obj);
				});
				$('#updateProjectName-guarantee').select2({theme: 'bootstrap', data: arry});
				//缓存
				_this.cache.guaranteeArry = result.iqbResult.result;
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
									'<div class="caption">' +		
										'<button type="button" class="btn btn-danger btn-xs" data-loading-text="正在移除图片" autocomplete="off" imgPath="' + n.imgPath + '" onclick="IQB.businessApproval.removeImg(event);"><span class="glyphicon glyphicon-trash"></span> 移除</button>' + 	
									'</div>' + 
							   '</div>';	
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
					var imgType = $('#img').attr('imgType');
					var option = {};
					option.orderId = $('#orderId').text();
					option.imgType = imgType;
					option.imgName = Formatter.getFileName($('#img').val());
					option.imgPath = result.iqbResult.result;
					IQB.post(urls['cfm'] + '/image/uploadImage', option, function(resultInfo){
						$('#btn-upload').button('reset');
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
						      			'<a href="' + urls['imgUrl'] + option.imgPath + '" rel="prettyPhoto[one]" title="' + option.imgName + '"><img src="' + urls['imgUrl'] + option.imgPath + '" style="width: 135px; height: 135px;"></a>' +
						      			'<div class="caption">' +		
						      				'<button type="button" class="btn btn-danger btn-xs" data-loading-text="正在移除图片" autocomplete="off" imgPath="' + option.imgPath + '" onclick="IQB.businessApproval.removeImg(event);"><span class="glyphicon glyphicon-trash"></span> 移除</button>' + 	
						      			'</div>' + 
						      		'</div>';
						$('#td-' + imgType).append(html);
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
			$('#btn-updateProjectName').on('click', function(){_this.openUpdateProjectNameWin();});
			$('#btn-updateProjectName-save').on('click', function(){_this.updateProjectName();});
			$('#btn-updateProjectName-close').on('click', function(){_this.closeUpdateProjectNameWin();});
			$('#btn-uploadTypeOne').on('click', function(){$('#img').attr('imgType', 1);$('#img').click();});
			$('#btn-uploadTypeTwo').on('click', function(){$('#img').attr('imgType', 2);$('#img').click();});
			$('#btn-uploadTypeThree').on('click', function(){$('#img').attr('imgType', 3);$('#img').click();});
			$('#btn-uploadTypeFour').on('click', function(){$('#img').attr('imgType', 4);$('#img').click();});
			$('#btn-uploadTypeSix').on('click', function(){$('#img').attr('imgType', 6);$('#img').click();});
			$('#btn-uploadTypeSeven').on('click', function(){$('#img').attr('imgType', 7);$('#img').click();});
			$('#btn-uploadTypeEight').on('click', function(){$('#img').attr('imgType', 8);$('#img').click();});
			$('#btn-uploadTypeNine').on('click', function(){$('#img').attr('imgType', 9);$('#img').click();});
			$('#img').on('change', function(){var imgName = $('#img').val();if(imgName){_this.uploadImg();}});			
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.businessApproval.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		