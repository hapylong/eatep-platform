$package('IQB.receiveCash');
IQB.receiveCash = function(){
	
   var _box = null;
   var _this ={
		   selectServiceType:function(){
			   $('#service-type').prepend("<option value=''>请选择</option>");
			    IQB.post(urls['rootUrl'] + '/sysDictRest/selectSysDictByType', {'dictTypeCode': 'COMM_BIZ_TYPE'}, function(result){
				$('#service-type').select2({theme: 'bootstrap', data: result.iqbResult.result});
				return result.iqbResult.result;
						});		
	            },
	        	selectChannelCode: function(){
					$('#channel-name').prepend("<option value=''>请选择</option>");
					IQB.postAsync(urls['rootUrl']+'/customer/unIntcpt-getChannelListByOrgCode',null,function(result){
					   $('#channel-name').select2({theme: 'bootstrap', data: result.iqbResult.result});
					   return result.iqbResult.result;
					})
				},
			resetSearchBtn: function(){
			   $('#btn-search').on('click', function(){
				  var queryStartTime = $('#dateStart').val();
				  var queryEndTime= $('#dateEnd').val();
				  var channelCode=$('#channel-name').val();
				  var businessType = $('#service-type').val();
			     _this.config.dataGrid.queryParams = {queryStartTime: queryStartTime, queryEndTime: queryEndTime,
				   channelCode:channelCode, businessType:businessType
				   };
			     
		       _box = new DataGrid2(_this.config);
		       _box.init();
		      
		       $('#btn-export-all').removeClass("disabled"); 
               
			   });
			},
			config:{
	       	 action:{
	    		 
	        	 },
	    	 event:{
	    		 reset: function(){
	    			 $('#service-type').val(null).trigger('change');
	    				$('#channel-name').val(null).trigger('change');
	    			 _box.handler.reset();
	    		 }
	    	  },
	    	 dataGrid:{
	    		 url:urls['rootUrl']+'/order/selectDepositList',
	    		 isExport:true,
	    		 filename: '%YY%%MM%%DD%%hh%%mm%%ss%',
	    		 cols: '2,4,5,6,7,8,9,10,11,12,13,14,15'
	    	 },
	    	 
	     },
	 init: function(){
		 
		 $('#channel-name').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap'});
		
	     _this.selectServiceType(); 
	     _this.resetSearchBtn();
	     $('#btn-export-all').addClass('disabled');
	     _this.selectChannelCode();
	 },
	}
	
	return _this;
}();


$(function(){
	  IQB.receiveCash.init();
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
  $.changeDate = function(val){
	var _val = val.substr(0,10);
	return _val
  }


