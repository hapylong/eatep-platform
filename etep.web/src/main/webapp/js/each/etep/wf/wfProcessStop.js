$package('IQB.wfProcessStop');
IQB.wfProcessStop = function() {
	var _box = null;
	var _this = {
		formatterOfSkipPage: function(val, row, rowIndex){
			return '<button type="button" class="btn btn-link" onclick="IQB.wfProcessStop.skipPage(\'' + row.procBizId + '\',\'' + row.procInstId + '\',\'' + row.procDisplayurl + '\')">' + val + '</button>';
		},
		//跳转订单详情页面
		skipPage: function(procBizId, procInstId,procDisplayurl){
			window.parent.IQB.main2.openTab("activiti-seedetail", "流程详情",procDisplayurl+'?procBizId=' + procBizId + '&procInstId=' + procInstId, true, true, null);			
		},
			
		stop : function() {
			var url = _this.config.action.stop;
			var records = _box.util.getCheckedRows();
			if (_box.util.checkSelectOne(records)) {
				var authData = {}
				authData.procAuthType = "2";
				
				var variableData = {}
				
				var procData={}
				procData.procInstId = records[0]['procInstId'];
				
				var bizData = {}
				bizData.procBizId = records[0]['procBizId'];
				bizData.procBizType = records[0]['procBiztype'];
				bizData.procOrgCode = records[0]['procOrgcode'];
				
				var option = {};
				option.variableData=variableData;
				option.authData=authData;
				option.procData=procData;
				option.bizData = bizData;
				
				IQB.post(url, option, function(result){
					if(result.success=="1") {
						IQB.alert('流程终止处理成功！');
						_box.handler.refresh();
					} else {
						IQB.alert(result.retUserInfo);
					}
				});
			}
		},
		
		config : {
			action : {
				stop : urls['rootUrl'] + '/WfTask/endProcess',// 流程终止
			},
			dataGrid : {
				url : urls['rootUrl'] + '/WfMonitor/orgProcessList',
				singleCheck : true,
				queryParams: {
					procAuthType : '2'
				}
			}
		},
		init : function() {
			_box = new DataGrid2(_this.config);
			_box.init();
			$('#btn-stop').click(function() {
				_this.stop()
			});
			$('#search-procStatus').select2({minimumResultsForSearch: 'Infinity', theme: 'bootstrap', placeholder: '请选择流程状态', allowClear: true}).val(null).trigger("change");		
		}
	}
	return _this;
}();

$(function() {
	IQB.wfProcessStop.init();
});