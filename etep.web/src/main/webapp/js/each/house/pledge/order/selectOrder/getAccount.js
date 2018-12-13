$package('IQB.getAccount');
IQB.getAccount = function(){
   var _box = null;
    var _this ={
        formatterOrderNo:function(val,row,rowIndex){
        //	window.location.href="selectHouseOrder.html?orderNo=val"
        	return '<button type="button" class="btn btn-link" onclick="IQB.getAccount.skipPage(\''+row.userName+ '\',\'' +val+'\')">' +val+'</button>';
        },
         skipPage: function(userName,val){
        	/*var url = 'customerDetail.html?id='+val+'&userName='+userName;
            window.location.href = url;*/
        	var url = "/etep.web/view/house/pledge/order/orderQuery/customerDetail.html"+'?orderNo=' + val+ '&userName=' + userName;
        	var _url = encodeURI(encodeURI(url));
        	 window.parent.IQB.main2.openTab("customerDetail", "订单详情页面",_url,true, true, null);
         },
		selectChannelCode: function(){//获取渠道名称
         	$('#channel-name').prepend("<option value=''>请选择</option>");
					IQB.postAsync(urls['rootUrl']+'/customer/unIntcpt-getChannelListByOrgCode',null,function(result){
					   $('#channel-name').select2({theme: 'bootstrap', data: result.iqbResult.result});
					   return result.iqbResult.result;
					})
				},
				
	         config:{
	       	 action:{
	    		 
	        	 },
	    	 event:{
	    		 reset: function(){
	    			 _box.handler.reset();
	    		    $('#channel-name').val(null).trigger('change');
	    		    $('#order-status').val(null).trigger('change');
	    		
	    		 }
	    	  },
	    	  
	    	 dataGrid:{
	    		 url: urls['rootUrl'] + '/order/selectOrderInfo',
	    	     isExport:true,
		    	 filename: '%YY%%MM%%DD%%hh%%mm%%ss%',
		    	 cols: '2,4,5,6,7,8,9,10,11,12,13,14,15'
	    	 },
	    	 
	    	 
	     },
	 init: function(){
		
		 $('#channel-name').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		 $('#order-status').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
	    _this.selectChannelCode();
	    _box = new DataGrid2(_this.config);
		 _box.init();
        },
	}
    return _this;
  }();


$(function(){
	  IQB.getAccount.init();
	   datepicker(dateStart);
		
});
/*显示日历*/ 
/*显示日历*/ 
function datepicker(id){
	var date_ipt = $(id)
	  $(date_ipt).datetimepicker({
	    lang:'ch',
	    timepicker:true,
	    format:'Y-m-d',
		allowBlank: true,
		
		
	});
  };
  $.orderStatus = function(val){
	  
		
		if(val=='4'){
			return '<span class="label label-primary" style="font-size:13px;">待还款</span>';
		}
		if(val=='5'){
			return '<span class="label label-primary" style="font-size:13px;">已逾期</span>';
		}
		if(val=='6'){
			return '<span class="label label-primary" style="font-size:13px;">已结清</span>';
		}return '';
		
	}


