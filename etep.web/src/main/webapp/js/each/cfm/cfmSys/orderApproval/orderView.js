function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var procBizId = getQueryString('procBizId');
var procInstId = getQueryString('procInstId');

/*var procBizId = '20160930-993216';
var procInstId = '08dd9827-86ed-11e6-b920-00163e0c0777';*/

$package('IQB.orderView');
IQB.orderView = function(){
	var _this = {
		cache: {			
			
		},	
		ret: function() {
			IQB.goBack();
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
				}else{
					$('#btn-upload').prop('disabled', true);
					$('.isPay-table').hide();
					$('.isPay-div').show();	
					$('#preAmountStatus').text(Formatter.preAmountStatus(result.preAmountStatus));
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
					      		   '</div>';
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
			//展示图片
			_this.showImg();
			$('#btn-return').on('click', function(){_this.ret()});
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.orderView.init();
	//禁用浏览器右击菜单,可选
	document.oncontextmenu = function(){return false;}
});		