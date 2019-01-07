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
			}/*,
			'->',
			{
				text:'刷新当前页面',
				handler:function(){
					var p = main.getActiveTab();
					p.body.dom.innerHTML=p.body.dom.innerHTML;
				}
			}*/
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
		width:130,
		root:{
			nodeType:'async',
			text:'<font style="font-size: 15px">百家乐数据分析平台</font>',
			expanded:true,
			id:'0',
            children : [
                {id:'1', text : '<font style="font-size: 15px">单机游戏</font>' , leaf : true },
                {id:'3', text : '<font style="font-size: 15px">大局分析</font>' , leaf : true },
                {id:'2', text : '<font style="font-size: 15px">小局分析</font>' , leaf : true },
                {id:'4', text : '<font style="font-size: 15px">数据分析</font>' , leaf : true },
                {id:'12', text : '<font style="font-size: 15px">数据统计</font>' , leaf : true },
                {id:'10', text: '<font style="font-size: 15px">四组预测</font>', expanded: true, children: [
					{id:'5', text : '<font style="font-size: 15px">ABMN预测</font>' , leaf : true },
					{id:'6', text : '<font style="font-size: 15px">ABLS预测</font>' , leaf : true },
					{id:'7', text : '<font style="font-size: 15px">LSMN预测</font>' , leaf : true },
					{id:'8', text : '<font style="font-size: 15px">AB预测</font>' , leaf : true },
					{id:'9', text : '<font style="font-size: 15px">MN预测</font>' , leaf : true },
					{id:'11', text : '<font style="font-size: 15px">LS预测</font>' , leaf : true },
					// {id:'13', text : '<font style="font-size: 15px">V预测</font>' , leaf : true },
					{id:'14', text : '<font style="font-size: 15px">LSV预测</font>' , leaf : true },
					{id:'15', text : '<font style="font-size: 15px">MNV预测</font>' , leaf : true }
				]}
                /*{id:'5', text: '<font style="font-size: 15px">数据建模</font>', expanded: true, children: [
                    {id:'6', text : '<font style="font-size: 15px">数据准备</font>' ,expanded: true,children:[
                        {id:'8', text : '<font style="font-size: 15px">初始化数据</font>' , leaf : true },
                        {id:'9', text : '<font style="font-size: 15px">初始化测试集数据</font>' , leaf : true },
                        {id:'10', text : '<font style="font-size: 15px">初始化训练集数据</font>' , leaf : true },
                        {id:'11', text : '<font style="font-size: 15px">初始化验证集数据</font>' , leaf : true }
					] },
                    {id:'7', text : '<font style="font-size: 15px">数据分析</font>' , leaf : true }
                ]}*/
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
						} else if (node.id == 3) {
                            url = path + "private/main/rootAnalysis";
						} else if (node.id == 4) {
                            url = path + "data/analysis/index";
						} else if (node.id == 12) {
                            url = path + "data/analysis/dateCount"; //数据统计
                        } else if (node.id == 5)  {
                            url = path + "data/analysis/projectionsAbMnData";
						} else if (node.id == 6)  {
                            url = path + "data/analysis/projectionsAbLsData";
                        } else if (node.id == 7)  {
                            url = path + "data/analysis/projectionsLsMnData";
                        } else if (node.id == 8)  {
                            url = path + "data/analysis/projectionsAbData";
                        } else if (node.id == 9)  {
                            url = path + "data/analysis/projectionsMnData";
                        } else if (node.id == 11)  {
                            url = path + "data/analysis/projectionsLsData";
                        } else if (node.id == 13)  {
                            url = path + "data/analysis/projectionsVData";
                        } else if (node.id == 14)  {
                            url = path + "data/analysis/projectionsLsVData";
                        } else if (node.id == 15)  {
                            url = path + "data/analysis/projectionsMnVData";
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
		width:230,
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