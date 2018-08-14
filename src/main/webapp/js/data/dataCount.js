Ext.onReady(function () {
    /* 初始化数据源 */
    var jsonUser = new Ext.data.JsonStore({
        id: 'yhgl',
        url: path + 'data/analysis/reetAllData',
        root: 'yhgl',
        totalProperty: 'count',
        fields: ['ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3', 'XIAN1', 'XIAN2', 'XIAN3', 'TOUZHUMONEY', 'TOUZHU', 'ZHUANGVALUE', 'XIANVALUE', 'TIME', 'POINT', 'VALUE', 'JISHUCOUNT', 'OUSHUCOUNT', 'PAICOUNT']
    });

    var jsonEpisData = new Ext.data.JsonStore({
        id: 'reetList',
        url : path + 'private/main/findReetById',
        root: 'reetList',
        totalProperty: 'count',
        fields: ['ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3', 'XIAN1', 'XIAN2', 'XIAN3', 'TIME', 'TOUZHU', 'ZHUANGVALUE', 'XIANVALUE', 'POINT', 'VALUE', 'JISHUCOUNT', 'OUSHUCOUNT']
    });

    var jsonEpisData2 = new Ext.data.JsonStore({
        id: 'jsonEpisData2',
        url: path + 'data/analysis/findReetById',
        root: 'jsonEpisData2',
        totalProperty: 'count',
        fields: ['ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3', 'XIAN1', 'XIAN2', 'XIAN3', 'TIME', 'TOUZHU', 'ZHUANGVALUE', 'XIANVALUE', 'POINT', 'VALUE', 'JISHUCOUNT', 'OUSHUCOUNT']
    });


    var dateSearchForm = new Ext.FormPanel({
        region: 'north',
        frame: true,
        height: 100,
        labelWidth: 80,
        labelAlign: 'right',
        items: [{
            layout: 'column',   //定义该元素为布局为列布局方式
            border: false,
            labelSeparator: '：',
            items: [{
                columnWidth: .5,  //该列占用的宽度，标识为50％
                layout: 'form',
                border: false,
                items: [{
                    layout: 'column',   //定义该元素为布局为列布局方式
                    border: false,
                    labelWidth: 80,
                    items: [{
                        columnWidth: .5,  //该列占用的宽度，标识为50％
                        layout: 'form',
                        border: false,
                        items: [{
                            xtype: "combo",
                            id: "jishu",
                            name: "jishu",
                            fieldLabel: '<font style="font-size: 15px">N数个数</font>',
                            width: 60,
                            editable: false,
                            style: 'font-size:15px;',
                            labelSeparator: '：',
                            store: ['0', '1', '2', '3', '4', '5', '6', 'N'],
                            displayField: 'category',
                            triggerAction: 'all'
                        }]
                    }, {
                        columnWidth: .5,  //该列占用的宽度，标识为50％
                        layout: 'form',
                        border: false,
                        items: [{
                            xtype: "combo",
                            id: "oushu",
                            name: "oushu",
                            fieldLabel: '<font style="font-size: 15px">M数个数</font>',
                            width: 60,
                            editable: false,
                            style: 'font-size:15px;',
                            labelSeparator: '：',
                            store: ['0', '1', '2', '3', '4', '5', '6', 'N'],
                            displayField: 'category',
                            triggerAction: 'all'
                        }]
                    }]
                }]
            }]
        }],
        buttons: [{
            text: '<font style="font-size: 18px">查询</font>',
            handler: function () {
                jsonUser.load({
                    params: {
                        start: 0,
                        limit: 80,
                        jishu: dateSearchForm.getForm().findField(
                            "jishu").getValue(),
                        oushu: dateSearchForm.getForm().findField(
                            "oushu").getValue(),
                    }
                });
            }
        }]
    });

    jsonUser.on('beforeload', function (s) {
        jsonUser.baseParams = {
            jishu: dateSearchForm.getForm().findField(
                "jishu").getValue(),
            oushu: dateSearchForm.getForm().findField(
                "oushu").getValue(),
        };
    });

    /* 数据列表 */
    var yhglGrid = new Ext.grid.GridPanel({
        id: 'yhglGrid',
        store: jsonUser,
        region: 'center',
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true
        }),
        autoExpandColumn: 'TOUZHU',
        columns: [{
            id: 'POINT',
            header: '记号',
            dataIndex: 'POINT',
            align: 'center',
            hidden: true
        }, {
            id: 'ID',
            header: '编号',
            dataIndex: 'ID',
            align: 'center',
            hidden: true
        }, {
            id: 'ROOMID',
            header: '大局ID',
            dataIndex: 'ROOMID',
            sortable: true,
            align: 'center',
            width: 300,
            css: 'font-size:13px;color:#red;',
            menuDisabled: true,
            hidden: true
        }, {
            id: 'XIAN1',
            header: '闲牌1',
            dataIndex: 'XIAN1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG1',
            header: '庄牌1',
            dataIndex: 'ZHUANG1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN2',
            header: '闲牌2',
            dataIndex: 'XIAN2',
            sortable: true,
            css: 'font-size:18px;color:#0e49e8;',
            align: 'center',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG2',
            header: '庄牌2',
            dataIndex: 'ZHUANG2',
            sortable: true,
            css: 'font-size:18px;color:red;',
            align: 'center',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN3',
            header: '闲牌3',
            dataIndex: 'XIAN3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'ZHUANG3',
            header: '庄牌3',
            dataIndex: 'ZHUANG3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 100,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'XIANVALUE',
            header: '闲点数',
            dataIndex: 'XIANVALUE',
            sortable: true,
            css: 'font-size:18px;',
            align: 'center',
            menuDisabled: true
        }, {
            id: 'ZHUANGVALUE',
            header: '庄点数',
            dataIndex: 'ZHUANGVALUE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;',
            menuDisabled: true
        }, {
            id: 'TIME',
            header: '投注时间',
            dataIndex: 'TIME',
            sortable: true,
            css: 'font-size:15px;',
            width: 180,
            align: 'center',
            menuDisabled: true,
            renderer: function (value) {
                return formatDate(value, 'Y-m-d H:m:s');
            }
        }, {
            id: 'TOUZHUMONEY',
            header: '投注金额',
            dataIndex: 'TOUZHUMONEY',
            css: 'font-size:15px;',
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'JISHUCOUNT',
            header: 'N数个数',
            dataIndex: 'JISHUCOUNT',
            css: 'font-size:18px;',
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'OUSHUCOUNT',
            header: 'M数个数',
            dataIndex: 'OUSHUCOUNT',
            css: 'font-size:18px;',
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'VALUE',
            header: '开注结果',
            dataIndex: 'VALUE',
            css: 'font-size:18px;color:red;',
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'TOUZHU',
            header: '投注对象',
            dataIndex: 'TOUZHU',
            sortable: true,
            align: 'center',
            css: 'font-size:15px;',
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "下注庄";
                } else if (v == '1') {
                    return "下注闲";
                } else if (v == '2') {
                    return "下注和";
                } else {
                    return "未下注";
                }
            }
        }, {
            id: 'PAICOUNT',
            header: '重复次数',
            dataIndex: 'PAICOUNT',
            sortable: true,
            align: 'center',
            css: 'font-size:15px;',
            menuDisabled: true
        }],
        enableColumnMove: true, // 允许拖动列
        bbar: new Ext.PagingToolbar({
            pageSize: 80,
            store: jsonUser,
            displayInfo: true,
            displayMsg: '显示{0}/{1}of{2}',
            emptyMsg: '没有数据',
            plugins: new Ext.ux.ProgressBarPager()
        }),
        buttons: [
            {
                text: '<font style="font-size: 18px;">查看详情</font>',
                handler: function () {
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if (!roleRecord) {
                        Ext.Msg.alert('提示', '请选中要查看记录!');
                        return;
                    } else {
                        // Ext.getCmp("detailForm_Name").setText(roleRecord.get("Title"));
                        var XIAN1 = roleRecord.get("XIAN1");
                        var XIAN2 = roleRecord.get("XIAN2");
                        var XIAN3 = roleRecord.get("XIAN3");
                        var ZHUANG1 = roleRecord.get("ZHUANG1");
                        var ZHUANG2 = roleRecord.get("ZHUANG2");
                        var ZHUANG3 = roleRecord.get("ZHUANG3");
                        jsonEpisData.load({
                            params: {
                                XIAN1: XIAN1,
                                XIAN2: XIAN2,
                                XIAN3: XIAN3,
                                ZHUANG1: ZHUANG1,
                                ZHUANG2: ZHUANG2,
                                ZHUANG3: ZHUANG3
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
        layout: 'border',
        items: [dateSearchForm, yhglGrid],
        buttons: [{
            text: 'One'
        }]
    });

    var episGrid = new Ext.grid.GridPanel({
        id: 'episGrid',
        store: jsonEpisData,
        region: 'center',
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true
        }),
        height: 480,
        autoExpandColumn: 'TIME',
        columns: [{
            id: 'ID',
            header: '编号',
            dataIndex: 'ID',
            align: 'center',
            hidden: true
        }, {
            id: 'POINT',
            header: '第几局',
            dataIndex: 'POINT',
            sortable: true,
            align: 'center',
            width: 60,
            css: 'font-size:18px;',
            menuDisabled: true
        }, {
            id: 'XIAN1',
            header: '闲牌1',
            dataIndex: 'XIAN1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG1',
            header: '庄牌1',
            dataIndex: 'ZHUANG1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN2',
            header: '闲牌2',
            dataIndex: 'XIAN2',
            sortable: true,
            css: 'font-size:18px;color:#0e49e8;',
            align: 'center',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG2',
            header: '庄牌2',
            dataIndex: 'ZHUANG2',
            sortable: true,
            css: 'font-size:18px;color:red;',
            align: 'center',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN3',
            header: '闲牌3',
            dataIndex: 'XIAN3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'ZHUANG3',
            header: '庄牌3',
            dataIndex: 'ZHUANG3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'ZHUANGVALUE',
            header: '庄点数',
            dataIndex: 'ZHUANGVALUE',
            sortable: true,
            width: 60,
            align: 'center',
            css: 'font-size:18px;',
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIANVALUE',
            header: '闲点数',
            dataIndex: 'XIANVALUE',
            sortable: true,
            width: 60,
            css: 'font-size:18px;',
            align: 'center',
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'VALUE',
            header: '开注结果',
            dataIndex: 'VALUE',
            css: 'font-size:18px;color:red;',
            sortable: true,
            width: 80,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'JISHUCOUNT',
            header: 'N数个数',
            dataIndex: 'JISHUCOUNT',
            css: 'font-size:18px;',
            sortable: true,
            width: 80,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'OUSHUCOUNT',
            header: 'M数个数',
            dataIndex: 'OUSHUCOUNT',
            css: 'font-size:18px;',
            width: 80,
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'TIME',
            header: '投注时间',
            dataIndex: 'TIME',
            sortable: true,
            css: 'font-size:15px;',
            width: 180,
            align: 'center',
            menuDisabled: true,
            renderer: function (value) {
                return formatDate(value, 'Y-m-d H:m:s');
            }
        }],
        enableColumnMove: true,
        bbar: new Ext.PagingToolbar({
            pageSize: 80,
            store: jsonEpisData,
            displayInfo: true,
            displayMsg: '显示{0}/{1}of{2}',
            emptyMsg: '没有数据',
            plugins: new Ext.ux.ProgressBarPager()
        }),
        buttons: [
            {
                text: '<font style="font-size: 18px;">查看大局</font>',
                handler: function () {
                    var roleRecord = episGrid.getSelectionModel().getSelected();
                    if (!roleRecord) {
                        Ext.Msg.alert('提示', '请选中要查看记录!');
                        return;
                    } else {
                        var ROOMID = roleRecord.get("ROOMID");
                        jsonEpisData2.load({
                            params: {
                                XIAN1: XIAN1
                            }
                        });
                        sepisodesWin2.show();
                    }
                }
            }
        ]
    });

    var episGrid2 = new Ext.grid.GridPanel({
        id: 'episGrid2',
        store: jsonEpisData2,
        region: 'center',
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true
        }),
        height: 480,
        autoExpandColumn: 'TIME',
        columns: [{
            id: 'ID',
            header: '编号',
            dataIndex: 'ID',
            align: 'center',
            hidden: true
        }, {
            id: 'POINT',
            header: '第几局',
            dataIndex: 'POINT',
            sortable: true,
            align: 'center',
            width: 60,
            css: 'font-size:18px;',
            menuDisabled: true
        }, {
            id: 'XIAN1',
            header: '闲牌1',
            dataIndex: 'XIAN1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG1',
            header: '庄牌1',
            dataIndex: 'ZHUANG1',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN2',
            header: '闲牌2',
            dataIndex: 'XIAN2',
            sortable: true,
            css: 'font-size:18px;color:#0e49e8;',
            align: 'center',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'ZHUANG2',
            header: '庄牌2',
            dataIndex: 'ZHUANG2',
            sortable: true,
            css: 'font-size:18px;color:red;',
            align: 'center',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIAN3',
            header: '闲牌3',
            dataIndex: 'XIAN3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'ZHUANG3',
            header: '庄牌3',
            dataIndex: 'ZHUANG3',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:red;',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                if (v == '0') {
                    return "";
                } else {
                    var substr = showData2(v);
                    return substr;
                }
            }
        }, {
            id: 'ZHUANGVALUE',
            header: '庄点数',
            dataIndex: 'ZHUANGVALUE',
            sortable: true,
            width: 60,
            align: 'center',
            css: 'font-size:18px;',
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'XIANVALUE',
            header: '闲点数',
            dataIndex: 'XIANVALUE',
            sortable: true,
            width: 60,
            css: 'font-size:18px;',
            align: 'center',
            menuDisabled: true,
            renderer: function (v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id: 'VALUE',
            header: '开注结果',
            dataIndex: 'VALUE',
            css: 'font-size:18px;color:red;',
            sortable: true,
            width: 80,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'JISHUCOUNT',
            header: 'N数个数',
            dataIndex: 'JISHUCOUNT',
            css: 'font-size:18px;',
            sortable: true,
            width: 80,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'OUSHUCOUNT',
            header: 'M数个数',
            dataIndex: 'OUSHUCOUNT',
            css: 'font-size:18px;',
            width: 80,
            sortable: true,
            align: 'center',
            menuDisabled: true
        }, {
            id: 'TIME',
            header: '投注时间',
            dataIndex: 'TIME',
            sortable: true,
            css: 'font-size:15px;',
            width: 180,
            align: 'center',
            menuDisabled: true,
            renderer: function (value) {
                return formatDate(value, 'Y-m-d H:m:s');
            }
        }],
        enableColumnMove: true,
        bbar: new Ext.PagingToolbar({
            pageSize: 80,
            store: jsonEpisData,
            displayInfo: true,
            displayMsg: '显示{0}/{1}of{2}',
            emptyMsg: '没有数据',
            plugins: new Ext.ux.ProgressBarPager()
        })
    });

    /**
     *查看小局列表
     */
    var sepisodesWin = new Ext.Window({
        id: 'sepisodesWin',
        title: '列表',
        width: 900,
        height: 480,
        autoHeight: true,
        collapsible: true,
        modal: true,
        items: [episGrid],
        closable: true,
        closeAction: 'hide',
        resizable: true,
        y: 0
    });

    /**
     *查看所在大局信息
     */
    var sepisodesWin2 = new Ext.Window({
        id: 'sepisodesWin2',
        title: '列表',
        width: 800,
        height: 480,
        autoHeight: true,
        collapsible: true,
        modal: true,
        items: [episGrid2],
        closable: true,
        closeAction: 'hide',
        resizable: true,
        y: 50
    });

});