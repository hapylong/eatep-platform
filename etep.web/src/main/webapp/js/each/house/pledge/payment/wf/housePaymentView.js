function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var procBizId = getQueryString('procBizid');
var procInstId = getQueryString('procInstId');

$package('IQB.housePaymentView');
IQB.housePaymentView = function(){
	var _box = null;
	var _this = {
	initSelect: function(){
				$('select').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
			},
    /* initCurrentDate: function(){
    	 var date_ = new Date();  
		 var year = date_.getFullYear();  
		 var month = date_.getMonth() + 1;  
		  lastdate = year + '-' + month + '-' + date_.getDate(); 
		 $('#dateStart').val(lastdate);
     },*/
     
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
			var salarySorce =$('#salarySorce').val();
			var desc=$('#desc').val();
			var dateStart=$('#dateStart').val();
		    var procApprOpinion;
			if(procApprStatus == 1){
				procApprOpinion = $.trim($('#procApprOpinion').val()) || '同意';
			}else{
				procApprOpinion = $.trim($('#procApprOpinion').val()) || '反对';
			}
			var option = {variableData: {procAuthType: '2', procApprStatus: procApprStatus, procApprOpinion:procApprOpinion},
					   bizData: {salarySource:salarySource,desc:desc,dateStart:dateStart,procBizId: window.procBizId},
					 procData: {procTaskId: window.procTaskId}};
			console.log(option);
			IQB.post(urls['house'] + '/WfTask/completeProcess', option, function(result){
				var url = window.location.pathname;
				var param = window.parent.IQB.main2.fetchCache(url);
				var callback = '_this.clickCloseTab(\'' + param.tabId + '\')';
				var callback2 = '_this.openTab(\'' + param.lastTab.tabId + '\', \'' + param.lastTab.tabTitle + '\', \'' + param.lastTab.tabUrl + '\',' + false + ',' + false + ',' + null + ')';
				window.parent.IQB.main2.call(callback, callback2);
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
    		  $('#salarySource').val(result.iqbResult.result.loanChannel);
    		  $('#desc').val(result.iqbResult.result.loanDesc);
    		  $('#dateStart').val(TimeFormatter(result.iqbResult.result.loanDate));
    		  /**
    		   * 图片信息显示
    		   */
    		  
    		  
    	  });
      },
     init: function(){
		   _this.initOrderInfo();
	     //  _this.initCurrentDate();	
	       _this.showFile();
	       _this.initSelect();
	       _this.initOrderHistory();
	       $('#btn-approve').on('click', function(){_this.openApproveWin()});
	       $('#btn-approve-save').on('click', function(){_this.approve()});
			$('#btn-approve-close').on('click', function(){_this.closeApproveWin()});
		    },
        }
	return _this;
    }();
   

    
 /* function(val){
    	if(val){
			val = parseInt(val, 10);
			var date = new Date(val);
			return date.format('yyyy年M月d日');
		}
		return '';
	};
*/  
    function TimeFormatter(val){
    	if(val){
			val = parseInt(val, 10);
			var date = new Date(val);
			return date.format('yyyy年M月d日');
		}
		return '';
  };
  $(function(){
  	//页面初始化
  	IQB.housePaymentView.init();
  	//禁用浏览器右击菜单,可选
  	document.oncontextmenu = function(){return false;}
  });	



