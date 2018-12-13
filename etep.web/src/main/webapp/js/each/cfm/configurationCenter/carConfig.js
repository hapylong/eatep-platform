$package('IQB.carConfig');
IQB.carConfig = function(){
	var _grid = null;
	var _tree = null;
	var _this = {
		cache :{
			
		},
		setMerCodeSelect : function() {
			var data = {};
			IQB.get(_this.config.action.getMerCodeInfo, data, function(result) {
				jQuery("select[name='merchantNo']").prepend(
						"<option value=''>全部</option>");
				var arr = result.iqbResult.result;
				$(arr).each(
						function(index) {
							jQuery("select[name='merchantNo']").append(
									"<option value='" + this.merchantNo + "'>"
											+ this.merchantShortName
											+ "</option>");
						});
			})
		},
		config: {
			action: {//页面请求参数
  				insert: urls['cfm'] + '/car/add',
  				update: urls['cfm'] + '/car/mod',
  				getById: urls['cfm'] + '/car/queryById',
  				remove: urls['cfm'] + '/car/del',
  				getMerCodeInfo : urls['cfm']+ '/merchant/getMerList',
  			},
			event: {//按钮绑定函数，不定义的话采用默认
				insert: function(){
					_grid.handler.insert();
					$('#update-win-label').text('添加车型');
					$('#update-win #merchantNo').addClass("merchModel");
					$('#update-win #merchantNo').attr("onclick","showMenuModel();return false;");
					$("#menuContentModel").css("max-height","105px");
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
					$('#update-win-label').text('修改车型');
					$('#update-win #merchantNo').removeClass("merchModel");
					$('#update-win #merchantNo').removeAttr("onclick");
				} 
			},
  			dataGrid: {//表格参数  				
	   			url: urls['cfm'] + '/car/query',
	   			singleCheck: true,
	   			queryParams: {
	   				type: 1
	   			}
			}
		},
		init: function(){ 
			_grid = new DataGrid2(_this.config); 
			_grid.init();//初始化按钮、表格
			_this.setMerCodeSelect();
		}
	}
	return _this;
}();

$(function(){
	//页面初始化
	IQB.carConfig.init();
});		