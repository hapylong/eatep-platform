$package('IQB.queryRepay');
IQB.queryRepay = function(){
	var _box = null;
	var _this = {
		 selectProvince:function(selectId, dictType){
				 $('#selectId-province').prepend("<option value=''>请选择</option>");
				 
				IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'COMM_PROVINCE'}, function(result){
					 $('#selectId-province').select2({theme: 'bootstrap', data: result.iqbResult.result}).on('change', function(){
						_this.selectCity($('#selectId-province').val());//调用部门下拉初始化
						});		
					 })
			},
		   //市的级联选择
			selectCity:function(paramter){
			
				$('#selectId-city').empty();
				$('#selectId-city').prepend("<option value=''>请选择</option>");
				var req_data = {'provinceName': paramter};
               IQB.postAsync(urls['rootUrl'] + '/customer/getCustomerCityListByProvince', req_data, function(result){
					$('#selectId-city').select2({theme: 'bootstrap', data: result.iqbResult.result});
					return result.iqbResult.result;
				})
			},
			selectChannelCode: function(){
				$('#channel-name').empty();
				$('#channel-name').prepend("<option value=''>请选择</option>");
				IQB.postAsync(urls['rootUrl']+'/customer/unIntcpt-getChannelListByOrgCode',null,function(result){
				   $('#channel-name').select2({theme: 'bootstrap', data: result.iqbResult.result});
				   return result.iqbResult.result;
				})
			},
			selectServiceType:function(){
				$('#service-type').empty();
				$('#service-type').prepend("<option value=''>请选择</option>")
			    IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'COMM_BIZ_TYPE'}, function(result){
				$('#service-type').select2({theme: 'bootstrap', data: result.iqbResult.result});
				return result.iqbResult.result;
						});		
	
		
			},
			
			
			applyPeriod: function(){
				$('#periods-applyTerm').prepend("<option value=''>请选择</option>");
				IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'HOUSE_LOAN_TERM'}, function(result){
					$('#periods-applyTerm').select2({theme: 'bootstrap', data: result.iqbResult.result});
					return result.iqbResult.result;
							});		
		
			},
	
			config: {
	
			
			action: {
  			},
			event: {
				reset: function(){//重写save	
					_box.handler.reset();
					$('#service-type').val(null).trigger('change');
					$('#order-status').val(null).trigger('change');
					$('#periods-applyTerm').val(null).trigger('change');
					$('#selectId-province').val(null).trigger('change');
					$('#channel-name').val(null).trigger('change');
					
				},
			},
  			dataGrid: {
  			   /*  url: urls['rootUrl'] + '/order/selectOrderInfo'*/
  				
  				isExport: true,
  				filename: '%YY%%MM%%DD%%hh%%mm%%ss%',
  				cols: '2,4,5,6,7,8,9,10,11,12,13,14,15'
	   	     },
		},
           
	
		init: function(){
			_box = new DataGrid2(_this.config); 
            _box.init();
        	$('#selectId-city').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
            $('#order-status').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
        	$('#channel-name').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
        
           _this.selectProvince();
           _this.selectServiceType();
       	   _this.applyPeriod();
       	   _this.selectChannelCode();
       
		},

	}
	
	return _this;
    }();
    $(function(){
	IQB.queryRepay.init();
	datepicker(dateStart);
	datepicker(dateEnd);
   });	
  /*显示日历*/ 
 function datepicker(id){
	var date_ipt = $(id)
	  $(date_ipt).datetimepicker({
        minView:'year',
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

