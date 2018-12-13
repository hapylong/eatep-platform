$package('IQB.overdue');
IQB.overdue = function(){
	var _grid = null;
	var _tree = null;
	var _this = {
		cache :{
			
		},
		turnTo : function(data){
			var rowIndex = data;
			var row = $("#datagrid").datagrid2('getRow', rowIndex);
			var orderId=row.tranSeq,amount=parseFloat(row.currcStmtAmt)+parseFloat(row.currLatefeeIn);
			var url = '/etep.web/view/cfm/creditSys/payWay.html?orderId='+orderId+'&amount='+amount;
			url = encodeURI(url);
			window.open(url);
		},
		query : function(){
			$("#btn-search").click(function(){
				_this.refresh(1);
			})
		},
		refresh : function(page){
			clearInterval(_this.cache.t);
			_this.cache.t = setInterval(function(){
				$("#datagrid").datagrid2({url: urls['cfm'] + '/credit/repayment',queryParams : $.extend({}, $("#searchForm").serializeObject(), {pageNum: page}),
					onPageChanged : function(page){
		   				console.log(page);
		   				_this.refresh(page);
		   			}	
				});
			},10000);
		},
		//催收
		collection:function(){
			var rows = $("#datagrid").datagrid2('getCheckedRows');
			if(rows.length > 0){
				var data = {
						"flag" : "1",
						"list" : rows
				};
				IQB.confirm('确认要催收吗？', function(){
					IQB.get(_this.config.action.collectionUrl, data, function(result) {
						var fails = result.iqbResult;
						if(fails.count == 0){
							IQB.alert("催收成功！");
						}else{
							var str = '';
							for(var i=0;i<fails.result.length;i++){
								str += fails.result[i]+",";
							}
							str = str.substring(0,str.length-1);
							IQB.alert(str +"未成功催收！");
						}
					})
					console.log(rows);
				});
			}else{				
				IQB.alert("未选中行");
			}
		},
		config: {
			action: {//页面请求参数
  				collectionUrl : urls['cfm']+ '/SMS/sendSms'
  			},
			event: {//按钮绑定函数，不定义的话采用默认
				
			},
  			dataGrid: {//表格参数  				
	   			url: urls['cfm'] + '/credit/repayment',
	   			onPageChanged : function(page){
	   				console.log(page);
	   				_this.refresh(page);
	   			}
			}
		},
		init: function(){ 
			_grid = new DataGrid2(_this.config); 
			_grid.init();//初始化按钮、表格
			_this.query();
			_this.refresh(1);
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.overdue.init();
});	