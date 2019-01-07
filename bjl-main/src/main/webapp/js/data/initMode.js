Ext.onReady(function() {
    /* 初始化数据源 */
    var myBigTimeout = 9000000; // 9000 sec
    var jsonSearchData = new Ext.data.JsonStore({
        id : 'searchData',
        proxy: new Ext.data.HttpProxy({
            url : path + 'data/analysis/searchData',
            timeout: myBigTimeout
        }),
        root : 'searchData',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','ZHUANGVALUE','XIANVALUE','TIME','POINT','VALUE','JISHUCOUNT','OUSHUCOUNT' ]
    });

    var panel = new Ext.Panel({
        region : 'north',
        frame : true,
        height : 40,
        labelAlign:'left',
        buttons : [ {
            labelAlign:'left',
            text : '<font style="font-size: 18px">初始化数据</font>',
            handler : function() {
                jsonSearchData.load();
            }
        } ]
    });

    jsonSearchData.on('beforeload', function(s) {});

    var dataGrid = new Ext.grid.GridPanel({
        id : 'dataGrid',
        store : jsonSearchData,
        region : 'center',
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        autoExpandColumn : 'TIME',
        columns : [ {
            id : 'POINT',
            header : '记号',
            dataIndex : 'POINT',
            align : 'center',
            hidden : true
        },{
            id : 'ID',
            header : '编号',
            dataIndex : 'ID',
            align : 'center',
            hidden : true
        }, {
            id : 'ROOMID',
            header : '大局ID',
            dataIndex : 'ROOMID',
            sortable : true,
            align : 'center',
            width : 300,
            css:'font-size:13px;color:#red;',
            menuDisabled : true,
            hidden : true
        },{
            id : 'XIAN1',
            header : '闲牌1',
            dataIndex : 'XIAN1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:#0e49e8;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                var substr = showData2(v);
                return substr;
            }
        },{
            id : 'ZHUANG1',
            header : '庄牌1',
            dataIndex : 'ZHUANG1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:red;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                var substr = showData2(v);
                return substr;
            }
        },{
            id : 'XIAN2',
            header : '闲牌2',
            dataIndex : 'XIAN2',
            sortable : true,
            css:'font-size:18px;color:#0e49e8;',
            align : 'center',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                var substr = showData2(v);
                return substr;
            }
        },  {
            id : 'ZHUANG2',
            header : '庄牌2',
            dataIndex : 'ZHUANG2',
            sortable : true,
            css:'font-size:18px;color:red;',
            align : 'center',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                var substr = showData2(v);
                return substr;
            }
        },{
            id : 'XIAN3',
            header : '闲牌3',
            dataIndex : 'XIAN3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:#0e49e8;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id : 'ZHUANG3',
            header : '庄牌3',
            dataIndex : 'ZHUANG3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:red;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            menuDisabled : true
        }, {
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            menuDisabled : true
        },{
            id : 'JISHUCOUNT',
            header : 'N数个数',
            dataIndex : 'JISHUCOUNT',
            css:'font-size:18px;',
            sortable : true,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'OUSHUCOUNT',
            header : 'M数个数',
            dataIndex : 'OUSHUCOUNT',
            css:'font-size:18px;',
            sortable : true,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'VALUE',
            header : '开注结果',
            dataIndex : 'VALUE',
            css:'font-size:18px;color:red;',
            sortable : true,
            align : 'center',
            menuDisabled : true
        },{
            id : 'TIME',
            header : '投注时间',
            dataIndex : 'TIME',
            sortable : true,
            css:'font-size:15px;',
            width : 180,
            align : 'center',
            menuDisabled : true,
            renderer:function(value){
                return formatDate(value,'Y-m-d H:m:s');
            }
        }],
        enableColumnMove : true, // 允许拖动列
        bbar : new Ext.PagingToolbar({
            pageSize : 800,
            store : jsonSearchData,
            displayInfo : true,
            displayMsg : '显示{0}/{1}of{2}',
            emptyMsg : '没有数据',
            plugins : new Ext.ux.ProgressBarPager()
        })
    });
    dataGrid.setAutoScroll(true);

    new Ext.Viewport({
        layout : 'border',
        items : [ panel, dataGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });

});