Ext.onReady(function(){
	var north = new Ext.BoxComponent({
		cls:'headerDiv',
		el:'header',
		region:'north',
		height:30,
		margins:'0 0 0 0'
	});
	
	var toolbar = new Ext.Toolbar({
		items:[
			{
				text:'开始',
				menu:[
					{
						text:'关闭所有',
						handler:function(){
							var len = main.items.length;
							for(var i=len-1;i>0;i--){
								main.items.get(i).destroy();
							}
						}
					},
					{
						text:'显示主页',
						handler:function(){
							main.setActiveTab(main.items.get(0));
						}
					},
					{
						text:'刷新当前页',
						handler:function(){
							var p = main.getActiveTab();
							//alert(p.body.dom.innerHTML);
							p.body.dom.innerHTML=p.body.dom.innerHTML;
						}
					},
					'-',
					{
						text:'退出',
						handler:function(){
							window.location.href=path+'private/main/loginOut';
						}
					}
				]
			},
			'->',
			{
				text:'刷新当前页面',
				handler:function(){
					var p = main.getActiveTab();
					p.body.dom.innerHTML=p.body.dom.innerHTML;
				}
			}
		]
	});
	
	var main = new Ext.TabPanel({
		region:'center',
		frame:true,
		tbar:toolbar,
		enableTabScroll:true,
		items:[
		{
			title:'主页',
			xtype:'panel',
			frame:true,
			items:[
				
			]
		}
		],
		activeTab:0
	});
	
	var tree = new Ext.tree.TreePanel({
		id:'tree',
		flex:1,
		width:100,
		root:{
			nodeType:'async',
			text:'<font style="font-size: 15px">百家乐数据分析平台</font>',
			expanded:true,
			id:'0',
            children : [
                {id:'1', text : '<font style="font-size: 15px">数据统计</font>' , leaf : true },
                {id:'2', text : '<font style="font-size: 15px">数据分析</font>' , leaf : true }
            ]
        },
	    listeners:{
	    	click:function(node){
	    		if(node.isLeaf()) {
	    			var tab = main.getItem('Tab_' + node.id);
	    			if(!tab) {
	    				var url = "";
	    				if (node.id == 1) {
                            url = path + "private/main/dataStatistics";
						} else if (node.id == 2) {
                            url = path + "private/main/dataAnalysis";
						}
	    				tab = new Ext.Panel({
	    					id:'Tab_' + node.id,
	    					title:node.text,
	    					closable:true,
	    					frame:true,
	    					html:'<iframe frameborder="0" width="100%" height="100%" src="'+url+'"></iframe>'
	    				});
	    				main.add(tab);
	    			}
	    			main.setActiveTab(tab);
	    		}
	    	}
	    }
	});
	
	var west = new Ext.Panel({
		region:'west',
		titleCollapse:true,
		collapsible:true,
		title:'功能菜单',
		border:true,
		width:180,
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[tree]
	});
	
	/*var south = new Ext.Panel({
		region:'center',
		titleCollapse:true,
		collapsible:true,
		border:true,
		layout:'border',
		items:[west,main]
	});*/
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[north,main,west]
	});
	
});