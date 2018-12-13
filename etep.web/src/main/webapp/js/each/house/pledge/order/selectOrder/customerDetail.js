function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
var _orderNo =  decodeURI(getQueryString('orderNo'));
var _userName = decodeURI(getQueryString('userName'));

$package('IQB.customerDetail');
IQB.customerDetail = function(){
	var _box = null;
	var _this = {
	   backPage: function(){
		/*	window.parent.IQB.main2.openTab("getAccount","账单查询","/etep.web/view/house/pledge/order/orderQuery/getAccount.html",true,true,null);	*/
		   window.location.href="/etep.web/view/house/pledge/order/orderQuery/getAccount.html"
			},
     	config: {
	
			
			action: {
  			},
			event: {
				reset: function(){//重写save	
				
				},
			},
  			dataGrid: {
  				/*url: urls['rootUrl'] + '/order/selectOrderInfo',*/
  				queryParams:{
  					orderNo:$('#orderNo').val()
  				}
  	        },
		},
           
	
		init: function(){
			_box = new DataGrid2(_this.config); 
            _box.init();
			$('#btn-back').click(_this.backPage);
			$('#userName').val(_userName);//回显
		    $('#orderNo').val(_orderNo);//回显
		    },
        }
	return _this;
    }();
    
    
    $(function(){
	IQB.customerDetail.init();
	});	



$.orderStatus = function(val){
   if(val=='4'){
		return '<span class="label label-primary" style="font-size:13px;">待还款</span>';
	}
	if(val=='5'){
		return '<span class="label label-primary" style="font-size:13px;">已逾期</span>';
	}
	if(val=='6'){
		return '<span class="label label-primary" style="font-size:13px;">已结清</span>';
	}
	
	

	return '';
	
}

