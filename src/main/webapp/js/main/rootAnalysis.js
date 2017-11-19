Ext.onReady(function() {
    /* 初始化数据源 */
    var jsonUser = new Ext.data.JsonStore({
        id : 'yhgl',
        url : path + 'private/main/roomAllData',
        root : 'yhgl',
        totalProperty : 'count',
        fields : [ 'ID', 'TOTALCOUNT', 'ZHUANGCOUNT', 'XIANCOUNT', 'HECOUNT','ZHUANGDUICOUNT', 'XIANDUICOUNT', 'STRARTTIME','ENDTIME','USERID' ]
    });
    jsonUser.load({
        params : {
            start : 0,
            limit : 80
        }
    });

    var jsonEpisData = new Ext.data.JsonStore({
        id : 'reetList',
        url : path + 'private/main/findReetById',
        root : 'reetList',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TIME','TOUZHU','ZHUANGVALUE','XIANVALUE','POINT' ]
    });

    /* 数据列表 */
    var yhglGrid = new Ext.grid.GridPanel({
        id : 'yhglGrid',
        store : jsonUser,
        region : 'center',
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        autoExpandColumn : 'STRARTTIME',
        columns : [ {
            id : 'ID',
            header : '编号',
            dataIndex : 'ID',
            align : 'center',
            hidden : true
        }, {
            id : 'TOTALCOUNT',
            header : '小局数',
            dataIndex : 'TOTALCOUNT',
            sortable : true,
            align : 'center',
            width : 100,
            css:'font-size:13px;',
            menuDisabled : true
        }, {
            id : 'ZHUANGCOUNT',
            header : '开庄',
            dataIndex : 'ZHUANGCOUNT',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true
        }, {
            id : 'XIANCOUNT',
            header : '开闲',
            dataIndex : 'XIANCOUNT',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            width : 100,
            menuDisabled : true
        }, {
            id : 'HECOUNT',
            header : '开和',
            dataIndex : 'HECOUNT',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true
        }, {
            id : 'ZHUANGDUICOUNT',
            header : '庄对',
            dataIndex : 'ZHUANGDUICOUNT',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true
        }, {
            id : 'XIANDUICOUNT',
            header : '闲对',
            dataIndex : 'XIANDUICOUNT',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            width : 100,
            menuDisabled : true
        }, {
            id : 'STRARTTIME',
            header : '时间',
            dataIndex : 'STRARTTIME',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true
        }],
        enableColumnMove : true, // 允许拖动列
        bbar : new Ext.PagingToolbar({
            pageSize : 80,
            store : jsonUser,
            displayInfo : true,
            displayMsg : '显示{0}/{1}of{2}',
            emptyMsg : '没有数据',
            plugins : new Ext.ux.ProgressBarPager()
        }),
        buttons : [
            {
                text:'<font style="font-size: 18px;">添加</font>',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要查看记录!');
                        return;
                    }else{
                        // Ext.getCmp("detailForm_Name").setText(roleRecord.get("Title"));
                        var roomId = roleRecord.get("ROOMID");
                        jsonEpisData.load({
                            params : {
                                roomId : roomId
                            }
                        });
                        sepisodesWin.show();
                    }
                }
            },
            {
                text:'<font style="font-size: 18px;">删除</font>',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要查看记录!');
                        return;
                    }else{
                        // Ext.getCmp("detailForm_Name").setText(roleRecord.get("Title"));
                        var roomId = roleRecord.get("ROOMID");
                        jsonEpisData.load({
                            params : {
                                roomId : roomId
                            }
                        });
                        sepisodesWin.show();
                    }
                }
            },
            {
                text:'<font style="font-size: 18px;">查看</font>',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要查看记录!');
                        return;
                    }else{
                        // Ext.getCmp("detailForm_Name").setText(roleRecord.get("Title"));
                        var roomId = roleRecord.get("ROOMID");
                        jsonEpisData.load({
                            params : {
                                roomId : roomId
                            }
                        });
                        sepisodesWin.show();
                    }
                }
            }
        ]
    });

    yhglGrid.setAutoScroll(true);

    new Ext.Viewport({
        layout : 'border',
        items : [yhglGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });


});