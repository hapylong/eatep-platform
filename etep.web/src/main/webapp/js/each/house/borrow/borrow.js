$package('IQB.borrow');
IQB.borrow = function(){
	var _box = null;
	var _this = {
			cache:{},
            config: {
	         action: {
	         	},
			event: {
				
			  },
  			dataGrid: {
  				url: urls['rootUrl'] + '/borrow/getBorrowList',
         	},
		},
           
	
		init: function(){
			_box = new DataGrid2(_this.config); 
            _box.init();
        	
		},

    }
	
	return _this;
    }();
    $(function(){
	IQB.borrow.init();
	
   });	



