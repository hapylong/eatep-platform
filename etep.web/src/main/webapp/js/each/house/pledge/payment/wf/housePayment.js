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

$package('IQB.housePayment');
IQB.housePayment = function(){
	var _box = null;
	var _this = {
	initSelect: function(){
				$('select').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
			},
     initCurrentDate: function(){
    	 var date_ = new Date();  
		 var year = date_.getFullYear();  
		 var month = date_.getMonth() + 1;  
		  lastdate = year + '-' + month + '-' + date_.getDate(); 
		 $('#dateStart').val(lastdate);
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
		
     openApproveWin:function(){
    	 $('#approve-win').modal({backdrop: 'static', keyboard: false, show: true});
     },
		approve: function(){			
			var procApprStatus = $('#procApprStatus').val();
			var procApprOpinion = $.trim($('#procApprOpinion').val());
			var salarySource =$('#salarySource').val();
			var desc=$('#desc').val();
			var dateStart=$('#dateStart').val();
		    
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
			bizData.salarySource= salarySource;
			bizData.desc= desc;
			bizData.dateStart= dateStart;
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
			
			
//			var procApprOpinion = $.trim($('#procApprOpinion').val());
//			if(procApprStatus == 1){
//				procApprOpinion = $.trim($('#procApprOpinion').val()) || '同意';
//			}else{
//				procApprOpinion = $.trim($('#procApprOpinion').val()) || '反对';
//			}
//			debugger;
//			var option = {variableData: {procAuthType: '2', procApprStatus: procApprStatus, procApprOpinion:procApprOpinion},
//					   bizData: {salarySource:salarySource,desc:desc,dateStart:dateStart,procBizId: window.procBizId},
//					 procData: {procTaskId: window.procTaskId}};
//			console.log(option);
//			//debugger;
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
		showFile: function(){	
			IQB.post(urls['house'] + '/upload/getFileList', {orderNo: window.procBizId, fileKind: [1, 9,11,14]}, function(result){
				$.each(result.iqbResult.result, function(i, n){
					var extensionName = Formatter.getExtensionName(n.fileName);
					if(Formatter.extensionName.doc.contain(extensionName)){
                     }else if(Formatter.extensionName.pic.contain(extensionName)){
						var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
							      			'<a href="javascript:void(0)"><img src="' + urls['imgUrl'] + n.filePath + '" style="width: 135px; height: 135px;"></a>' +
							      			'<div class="caption">' +
							      				'<h5>' + n.fileName + '</h5>' +
							      			'</div>' + 
							      		'</div>';
							$('#td-' + n.fileKind).append(html);
						}															
				});
				
			});
		},
		
		
     initOrderInfo:function(){
    	  IQB.post(urls['house']+'/loan/getOrderReview', {businessId: window.procBizId},function(result){
    		
    		  $('#userName').text(result.iqbResult.result.userName);
    		  $('#mobile').text(result.iqbResult.result.mobile);
    		  $('#idNo').text(result.iqbResult.result.idCard);
    		  $('#bank').text(result.iqbResult.result.bank);
    		  $('#bankIdNo').text(result.iqbResult.result.bankId);
    		  $('#approvalAmt').text(result.iqbResult.result.approvalAmt);
    		  $('#approvalTerm').text(result.iqbResult.result.applyTerm);
    		  $('#rePayType').text(result.iqbResult.result.returnType);
    		  $('#monthRate').text(result.iqbResult.result.monthlyInterest);
    		  $('#repaySource').text(result.iqbResult.result.returnSource);
    		  $('#penaltyRate').text(result.iqbResult.result.defaultInterest);
    		  $('#desc').text(result.iqbResult.result.description);
    		  /**
    		   * 图片信息显示
    		   */
    		  
    		  
    	  });
      },
     init: function(){
    	 $('.panel').addClass('special-panel');
			$('a[data-toggle="tab"]').on('show.bs.tab', function(e){var target = e.target;var href = $(target).prop('href');if(href.indexOf('#order-tab') != -1){$('.panel').addClass('special-panel');}else{$('.panel').removeClass('special-panel');}});			
		   _this.initOrderInfo();
	       _this.initCurrentDate();	
	       _this.showFile();
	       _this.initSelect();
	       _this.initOrderHistory();
	       $('#btn-approve').on('click', function(){_this.openApproveWin()});
	       $('#btn-unassign').on('click', function(){_this.unassign()});
	       $('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});
		    },
        }
	return _this;
    }();
   
  /*显示日历*/ 
    function datepicker(id){
    	var date_ipt = $(id)
    	  $(date_ipt).datetimepicker({
            minView:'year',
    	    lang:'ch',
    	    timepicker:false,
    	    format:'Y-m-d',
    	    allowBlank: true,
    });
  };

  $(function(){
  	//页面初始化
  	IQB.housePayment.init();
  	 datepicker(dateStart);
  	//禁用浏览器右击菜单,可选
  	document.oncontextmenu = function(){return false;}
  });	



