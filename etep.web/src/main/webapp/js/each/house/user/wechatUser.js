$package('IQB.wechatUser');
IQB.wechatUser = function(){
	var _box = null;
	var _this = {
			cache:{},
			openUpdatePwdWin : function() {
				
				var records = _box.util.getCheckedRows();
				if (_box.util.checkSelectOne(records)) {
					$('#update-pwd-win').modal('show');
					var passwordId = records[0].id;
				//	alert(passwordId);
					$('#pwdId').val(passwordId);
				}
			},
			
			closeUpdatePwdWin : function() {
				$('#update-pwd-win').modal('hide');
			},
			saveUpdatePwdWin : function() {
				var userPassword = $('#userPassword').val();
				//alert(userPassword);
				var realUserPassword = $('#realUserPassword').val();
				//alert(realUserPassword);
				if (userPassword == realUserPassword) {
					$('#btn-pwd-save').click(_this.saveUpdatePwdWin);
					$('#updatePwdForm').attr('action',
							_this.config.action.updatePwd);
					IQB.save($('#updatePwdForm'), function(result) {
						$('#update-pwd-win').modal('hide');
						$('#updatePwdForm').form('reset');
						_box.handler.refresh();
					});
				}
			},
			/*saveUpdatePwdWin: function(){		
		          if($('#updatePwdForm').form('validate')){
					IQB.get(_this.config.action.editPwd, $('#updatePwdForm').serializeObject(), function(result) {
						if ($.isSucc(result)) {
							alert("密码修改成功");
							$('#updatePwdForm').form('reset');
						}
					});			
		          }
			},*/
			/*saveUpdatePwdWin: function(){	
				debugger;
		          if($('#updatePwdForm').form('validate')){
					IQB.get(_this.config.action.editPwd, $('#updatePwdForm').serializeObject(), function(result) {
						if ($.isSucc(result)) {
							alert("密码修改成功");
							$('#updatePwdForm').form('reset');
						}
					});			
		          }
			},*/
			
			selectOrgCode: function(){
				$('#orgCode').empty();
				$('#orgCode').prepend("<option value=''>请选择</option>");
				IQB.postAsync(urls['rootUrl']+'/customer/unIntcpt-getChannelListByOrgCode',null,function(result){
				   $('#orgCode').select2({theme: 'bootstrap', data: result.iqbResult.result});
				   return result.iqbResult.result;
				})
			},
			
			
	
			config: {
	
			
			action: {
				updatePwd:urls['rootUrl'] + '/wechat/updateWechatUserPassword',
				
  			},
			event: {
				reset: function(){//重写save	
					_box.handler.reset();
					$('#orgCode').val(null).trigger('change');
			   },
			},
  			dataGrid: {
  				url: urls['rootUrl'] + '/wechat/getWetchatUserInfo',
  				singleCheck: true
  				
	   		/*	idField: 'operType',
	   	        queryParams: {	   				
	   				pageNo: 1,
	   				pageSize: 10,
	   				operType: 1
	   			}*/
			},
		},
           
	
		init: function(){
			_box = new DataGrid2(_this.config); 
            _box.init();
        	_this.selectOrgCode();
        	$('#btn-pwd-update').click(_this.openUpdatePwdWin);
			$('#btn-pwd-close').click(_this.closeUpdatePwdWin);
			$('#btn-pwd-save').click(_this.saveUpdatePwdWin);
		},

    }
	
	return _this;
    }();
    $(function(){
	IQB.wechatUser.init();
	
   });	



