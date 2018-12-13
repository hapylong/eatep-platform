$package('IQB.requestFunds');
IQB.requestFunds = function(){
	var _grid = null;
	var _tree = null;
	var _this = {
		cache :{
			
		},
		setMerCodeSelect : function() {
			var data = {};
			IQB.get(_this.config.action.getMerCodeInfo, data, function(result) {
				jQuery("select[name='merchName']").prepend("<option value=''>全部</option>");
				var arr = result.iqbResult.result;
				$(arr).each(
						function(index) {
							jQuery("select[name='merchName']").append("<option value='" + this.merchantNo + "'>"+ this.merchantShortName+ "</option>");
						});
			})
		},
		update:function(){
			var option = {};
			option.orderId = $("#orderId").val();
			option.requestTimes = $("#requestTimes  option:selected").val();
			option.fundSourceId = $("#query2_fund_source  option:selected").val();
			option.plantime =  $("#plantime").val();
			option.desc =  $("#desc").val();
			option.deadline = $("#deadline").val();
			IQB.post(urls['cfm'] + '/workFlow/saveReqParams', option, function(resultInfo){
				if( resultInfo && "error" != resultInfo.retCode){
					_grid.handler.refresh();
				}else if(resultInfo && "error" == resultInfo.retCode){
					IQB.alert('推送失败！');
				}
 			});
		},
		showImg: function(orderId,leftitems){//初始化图片
			$("#query2_fund_source").empty();
			IQB.getDictListByDictType('query2_fund_source', 'FUND_SOURCE');
			var req_data = {'dictTypeCode': 'STAGE_NUMBER'};
			$("select[name='requestTimes']").empty();
			IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', req_data, function(result){
				jQuery("select[name='requestTimes']").prepend("<option value=''>请选择</option>");
				var requestItems = result.iqbResult.result;
				$(requestItems).each(
						function(index) {
							if(requestItems[index].id<=leftitems){
								jQuery("select[name='requestTimes']").append("<option value='" + requestItems[index].id + "'>"+ requestItems[index].text+ "</option>");
							}
						}
						);
				
			})
			//init 申请期数
			
			$('#chargeWay').attr("disabled",true);
			$("#riskType").attr("disabled",true);
			 var mydate = new Date();  
				var str = "" + mydate.getFullYear() + "-";  
				str += (mydate.getMonth()+1) + "-";  
				str += mydate.getDate() + " ";  
				str += mydate.getHours()+":";
				str += mydate.getMinutes()+":00";
			$("#plantime").val(str);
			IQB.post(urls['cfm'] + '/image/getImageList', {orderId: orderId}, function(result){
				$('#projectName').text(Formatter.ifNull(result.iqbResult.projectName));
				$('#guarantee').text(Formatter.ifNull(result.iqbResult.guarantee));
				$('#guaranteeName').text(Formatter.ifNull(result.iqbResult.guaranteeName));
				$.each(result.iqbResult.result, function(i, n){
					n.imgUrl = urls['imgUrl'] + n.imgPath;
					var html = '<div class="thumbnail float-left" style="width: 145px;">' + 
									'<a href="' + n.imgUrl + '" data-lightbox="group" data-title="' + n.imgName + '"><img src="' + n.imgUrl + '" style="width: 135px; height: 135px;"></a>' +
							   '</div>';	
					$('#td-' + n.imgType).append(html);
				});
			});
		},
		config: {
			action: {//页面请求参数
  				getMerCodeInfo : urls['cfm']+ '/merchant/getMerList',
  				getById: urls['cfm'] +'/assetAllocation/getAssetDetails',
  				save: urls['cfm']+'/workFlow/saveReqParams'
  			},
			event: {//按钮绑定函数，不定义的话采用默认
				update: function(){
					_grid.handler.updateAsset(_this.showImg);
					$('#update-win-label').text('新建资产分配');
				},
				reset: function(){//重写save	
					_grid.handler.reset();
					$('#query_fund_source').val(null).trigger('change');
					$('#query_stage_number').val(null).trigger('change');
				}
			},
  			dataGrid: {//表格参数  				
	   			url: urls['cfm'] + '/workFlow/getAllReqMoney',
	   			singleCheck: true//'/assetAllocation/getAssetAllocationList'
			}
		},
		init: function(){ 
			_grid = new DataGrid2(_this.config); 
			_grid.init();//初始化按钮、表格
			_this.setMerCodeSelect();
			this.initSelect();
			this.initBtnClick();
			
		},
		initSelect: function(){
			IQB.getDictListByDictType('query_fund_source', 'FUND_SOURCE');
			IQB.getDictListByDictType('query_stage_number', 'STAGE_NUMBER');
			$('#query_fund_source').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
			//$('#query_merch_name').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
			$('#query_stage_number').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		},
		initBtnClick: function(){
			$('#btn-save').on('click', function(){_this.update()});
		}
	}
	return _this;
}();
$(function(){
	//页面初始化
	IQB.requestFunds.init();
	datepicker(query_start_order_time);
	datepicker(query_end_order_time);
	datepicker(plantime);
	datepicker(deadline);
	$('#update-win').find('.modal-form').mCustomScrollbar({setHeight: 500});
});	
/*显示日历*/ 
function datepicker(id){
	var date_ipt = $(id)
	$(id).datetimepicker({
	    lang:'ch',
	    timepicker:false,
	    format:'Y-m-d  H:i:00',
	    defaultDate : new Date(),
		allowBlank: false
	});
};
function show(val){  
	   var mydate = new Date();  
	   var str = "" + mydate.getFullYear() + "-";  
	   str += (mydate.getMonth()+1) + "-";  
	   str += mydate.getDate() + "";  
	   return str;  
}  
function initRequestPeriod (value){
	 $("[name='orderName']").val(value.orderName);
	
	var all = [6,12,24,36];
	var value = $("#orderItems")[0].value;
	jQuery("select[name='requestPeriod']").prepend("<option value=''>全部</option>");
	$(all).each(
			function(index) {
				jQuery("select[name='requestPeriod']").append("<option value='" + all[index] + "'>"+ all[index]+ "</option>");
			});
}