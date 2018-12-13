$package('IQB.depositDetial');
var firstdate="";
var lastdate="";
IQB.depositDetial = function(){
	
   var _box = null;
   var _this ={
		   //设置开始日期的默认值
		   selectStartTime: function(){
		       var date_ = new Date();  
			   var year = date_.getFullYear();  
			   var month = date_.getMonth() + 1;  
			    firstdate = year + '-' + month + '-01';
			    lastdate = year + '-' + month + '-' + date_.getDate();  
			   $('#dateStart').val(firstdate);
			  
			   $('#dateEnd').val(lastdate);
			  },
			  
			 
		   selectServiceType:function(){
			   $('#service-type').prepend("<option value=''>请选择</option>");
			    IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'COMM_BIZ_TYPE'}, function(result){
				$('#service-type').select2({theme: 'bootstrap', data: result.iqbResult.result});
				return result.iqbResult.result;
						});		
	            },
	        	selectChannelCode: function(){//获取渠道名称
	        		
					$('#channel-name').prepend("<option value=''>请选择</option>");
					IQB.postAsync(urls['rootUrl']+'/customer/unIntcpt-getChannelListByOrgCode',null,function(result){
					   $('#channel-name').select2({theme: 'bootstrap', data: result.iqbResult.result});
					   return result.iqbResult.result;
					})
				},
				//获取省
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
					
					applyPeriod: function(){
						$('#periods-applyTerm').prepend("<option value=''>请选择</option>");
						IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'HOUSE_LOAN_TERM'}, function(result){
							$('#periods-applyTerm').select2({theme: 'bootstrap', data: result.iqbResult.result});
							return result.iqbResult.result;
									});		
				
					},
					
		
			config:{
	       	 action:{
	    		 
	        	 },
	    	 event:{
	    		 reset: function(){
	    			 _box.handler.reset();
	    			 $('#periods-applyTerm').val(null).trigger('change');
	    			 $('#channel-name').val(null).trigger('change');
	    			 $('#service-type').val(null).trigger('change');
	    			 $('#selectId-province').val(null).trigger('change');
	    			 $('#status-order').val(null).trigger('change');
	    			
	    			 _this. selectStartTime();
	    		     
	    		 }
	    	  },
	    	  
	    	 dataGrid:{
	    		 url:urls['rootUrl']+'/order/getDepositDetail',
	    		
	    		 queryParams:{
	    			
	    			 queryStartTime:selectStartTime11(),
	    			 queryEndTime:selectStartTime12()
	    		 },
	    	    isExport:true,
		    	 filename: '%YY%%MM%%DD%%hh%%mm%%ss%',
		    	 cols: '2,4,5,6,7,8,9,10,11,12,13,14,15'
	    	 },
	    	 
	    	 
	     },
	 init: function(){
		
		 $('#channel-name').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		 $('#status-order').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		 $('#selectId-city').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		 $('#btn-search').click(_this.selectSearch);
		 _this.selectServiceType(); 
	     _this.selectChannelCode();
	     _this.applyPeriod();
	     _this.selectProvince();
	     _this.selectStartTime();
	     _box = new DataGrid2(_this.config);
		 _box.init();
	     
	 },
	}
	
	return _this;
}();


$(function(){
	  IQB.depositDetial.init();
	  datepicker(dateStart);
		datepicker(dateEnd);
	  
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

  function selectStartTime11(){
      var date_ = new Date();  
	   var year = date_.getFullYear();  
	   var month = date_.getMonth() + 1;  
	    firstdate = year + '-' + month + '-01';
	    return firstdate;
	  }
  
  function selectStartTime12(){
	  var date_ = new Date();  
	   var year = date_.getFullYear();  
	   var month = date_.getMonth() + 1;  
	   var  day = date_.getDate();
	   lastdate = year + '-' + month + '-' + day;  
	   return lastdate;
	  }
  
   $.changeStatus = function(val){
	  if(val=='1')
		  {
		  return '<span class="label label-warning" style="font-size:13px;">未收取</span>';
		  
		  }
	  if(val=='2'){
		  return '<span class="label label-success" style="font-size:13px;">已收取</span>';
	  }
	  return ''
   }

