	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) IQB.alert("请选择具体商户！");
		return check;
	}
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].merchantShortName + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $(".merch");
		/*cityObj.attr("value", v);*/
		cityObj.val(v);
		$("#menuContent").hide();
	}
	function showMenu() {
		var cityObj = $(".merch");
		var cityOffset = $(".merch").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).toggle();
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	$(function(){
		  var setting = {
				view: {
					dblClickExpand: false
				},
				data: {
					key: {
						name:"merchantShortName"
					},
					simpleData: {
						enable: true,
						pIdKey: "parentId",
						idKey:"id",
						nameKey:"merchantShortName"
					}
				},
				callback: {
					beforeClick: beforeClick,
					onClick: onClick
				}
			};
		    var zNodes;
		    var data = {};
			IQB.get(urls['cfm']+ '/merchant/getMerList', data, function(result) {
				zNodes = result.iqbResult.result;
				//页面初始化
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			})
			//重置
			$("#btn-reset").click(function(){
				$("#searchForm input").val("");
				$("#searchForm select").val("");
				$("#searchForm .merch").val("全部商户");
			})
	});

