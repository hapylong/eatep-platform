$package('IQB.configure');
IQB.configure = function(){
	var _grid = null;
	var _tree = null;
	var _this = {
		cache :{
			
		},
		config: {
			action: {//页面请求参数
  				insert: urls['cfm'] + '/product/add',
  				update: urls['cfm'] + '/product/mod',
  				getById: urls['cfm'] + '/product/queryById',
  				remove: urls['cfm'] + '/product/del',
  			},
			event: {//按钮绑定函数，不定义的话采用默认
				insert: function(){
					_grid.handler.insert();
					$('#update-win-label').text('添加方案');
					$('#update-win #merchantNo').attr("disabled",false);
					if($("#update-win")){$("#update-win").addClass("z-index")};
					if($(".modal-backdrop")){$(".modal-backdrop").addClass("z-index2")};
					$("#btn-save").click(function(){
						$("#update-win").removeClass("z-index");
						$("#menuContentModel").hide();
						});
					$("#btn-close").click(function(){
						$("#update-win").removeClass("z-index");
						$("#menuContentModel").hide();
						});
				},
				update: function(){
					_grid.handler.update();
					$('#update-win-label').text('修改方案');
					$('#update-win #merchantNo').attr("disabled",true);
				}
			},
  			dataGrid: {//表格参数  				
	   			url: urls['cfm'] + '/product/query',
	   			queryParams: {
	   				type: 1
	   			}
			}
		},
		init: function(){ 
			_grid = new DataGrid(_this.config); 
			_grid.init();//初始化按钮、表格
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.configure.init();
});		