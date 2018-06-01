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

    /*查询大局中小局*/
    var jsonEpisData = new Ext.data.JsonStore({
        id : 'reetList',
        url : path + 'private/main/findReetById',
        root : 'reetList',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TIME','TOUZHU','ZHUANGVALUE','XIANVALUE','POINT','VALUE','JISHUCOUNT','OUSHUCOUNT'  ]
    });

    var zhuang = 0;
    var xian = 0;
    var he = 0;
    var dateSearchForm = new Ext.FormPanel({
        region : 'north',
        frame : true,
        height : 198,
        labelWidth:80,
        labelAlign:'right',
        items: [{
            layout:'column',   //定义该元素为布局为列布局方式
            border:false,
            labelSeparator:'：',
            items:[{
                columnWidth:.5,  //该列占用的宽度，标识为50％
                layout: 'form',
                border:false,
                items: [{
                    xtype: 'textfield',
                    id: 'one',
                    name: 'one',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">第一局</font>',
                    labelSeparator: '：',
                    listeners: {
                        render: function(obj) {
                            var font=document.createElement("font");
                            font.setAttribute("color","red");
                            font.setAttribute("style","font-size:15px;")
                            var redStar=document.createTextNode('    单双数用-号分割，如：0-2，1-5');
                            font.appendChild(redStar);
                            obj.el.dom.parentNode.appendChild(font);
                        }
                    }
                }, {
                    xtype: 'textfield',
                    id: 'two',
                    name: 'two',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">第二局</font>',
                    labelSeparator: '：',
                    listeners: {
                        render: function(obj) {
                            var font=document.createElement("font");
                            font.setAttribute("color","red");
                            font.setAttribute("style","font-size:15px;");
                            var redStar=document.createTextNode('    单双数用-号分割，如：0-2，1-5');
                            font.appendChild(redStar);
                            obj.el.dom.parentNode.appendChild(font);
                        }
                    }
                },{
                    xtype: 'textfield',
                    id: 'three',
                    name: 'three',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">第三局</font>',
                    labelSeparator: '：',
                    listeners: {
                        render: function(obj) {
                            var font=document.createElement("font");
                            font.setAttribute("color","red");
                            font.setAttribute("style","font-size:15px;");
                            var redStar=document.createTextNode('    单双数用-号分割，如：0-2，1-5');
                            font.appendChild(redStar);
                            obj.el.dom.parentNode.appendChild(font);
                        }
                    }
                }, {
                    layout: 'column',   //定义该元素为布局为列布局方式
                    border: false,
                    labelWidth: 100,
                    items: [{
                        columnWidth: .2,  //该列占用的宽度，标识为50％
                        layout: 'form',
                        border: false,
                        items: [{
                            xtype: "combo",
                            id: "oneType",
                            name: "oneType",
                            fieldLabel: '<font style="font-size: 15px">第一局结果</font>',
                            width: 60,
                            editable: false,
                            style: 'font-size:15px;',
                            labelSeparator: '：',
                            store: ['全部', '庄', '闲', '和'],
                            displayField: 'category',
                            triggerAction: 'all'
                        }]
                    }, {
                        columnWidth: .2,  //该列占用的宽度，标识为50％
                        layout: 'form',
                        border: false,
                        items: [{
                            xtype: "combo",
                            id: "twoType",
                            name: "twoType",
                            fieldLabel: '<font style="font-size: 15px">第二局结果</font>',
                            width: 60,
                            editable: false,
                            style: 'font-size:15px;',
                            labelSeparator: '：',
                            store: ['全部', '庄', '闲', '和'],
                            displayField: 'category',
                            triggerAction: 'all'
                        }]
                    }, {
                        columnWidth: .2,  //该列占用的宽度，标识为50％
                        layout: 'form',
                        border: false,
                        items: [{
                            xtype: "combo",
                            id: "threeType",
                            name: "threeType",
                            fieldLabel: '<font style="font-size: 15px">第三局结果</font>',
                            width: 60,
                            editable: false,
                            style: 'font-size:15px;',
                            labelSeparator: '：',
                            store: ['全部', '庄', '闲', '和'],
                            displayField: 'category',
                            triggerAction: 'all'
                        }]
                    }]
                },{
                    xtype: 'textfield',
                    id: 'allCount',
                    name: 'allCount',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">大局数量</font>',
                    labelSeparator: '：'
                }]
            }]
        }],
        buttons : [ {
            text : '<font style="font-size: 18px">查询</font>',
            handler : function() {
                var ones = dateSearchForm.getForm().findField("one").getValue();
                var twos = dateSearchForm.getForm().findField("two").getValue();
                var three = dateSearchForm.getForm().findField("three").getValue();
                var allCount = dateSearchForm.getForm().findField("allCount").getValue();
                if (ones == '') {
                    Ext.Msg.alert('提示','请输入第一局的单双数');
                    return;
                }
                if (ones.split('-').length != 2) {
                    Ext.Msg.alert('提示','请输入正确格式的单双数');
                    return;
                }
               /* if (twos == '') {
                    Ext.Msg.alert('提示','请输入第二局的单双数');
                    return;
                }
                 if (twos.split('-').length != 2) {
                    Ext.Msg.alert('提示','请输入正确格式的单双数');
                    return;
                }
                if (three == '') {
                    Ext.Msg.alert('提示','请输入第三局的单双数');
                    return;
                }
                if (three.split('-').length != 2) {
                    Ext.Msg.alert('提示','请输入正确格式的单双数');
                    return;
                }*/
                zhuang = 0;
                xian = 0;
                he = 0;
                jsonSearchData.load({
                    params : {
                    /*    start : 0,
                        limit : 80,*/
                        oneType : dateSearchForm.getForm().findField("oneType").getValue(),
                        twoType : dateSearchForm.getForm().findField("twoType").getValue(),
                        threeType : dateSearchForm.getForm().findField("threeType").getValue(),
                        one : ones,
                        two :twos,
                        three :three,
                        allCount :allCount
                    }
                });
            }
        } ]
    });

    jsonSearchData.on('beforeload', function(s) {
        jsonSearchData.baseParams = {
            oneType : dateSearchForm.getForm().findField("oneType").getValue(),
            twoType : dateSearchForm.getForm().findField("twoType").getValue(),
            threeType : dateSearchForm.getForm().findField("threeType").getValue(),
            one : dateSearchForm.getForm().findField("one").getValue(),
            two : dateSearchForm.getForm().findField("two").getValue(),
            three : dateSearchForm.getForm().findField("three").getValue(),
            allCount : dateSearchForm.getForm().findField("allCount").getValue()
        };
    });

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
            header : '奇数个数',
            dataIndex : 'JISHUCOUNT',
            css:'font-size:18px;',
            sortable : true,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'OUSHUCOUNT',
            header : '偶数个数',
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
            menuDisabled : true,
            renderer : function(v) {
                if (v == '庄') {
                    zhuang++;
                } else if (v == '闲') {
                    xian++;
                } else if (v == '和') {
                    he++;
                }
                return v;
            }
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
        }),
        buttons : [
            {
                text:'<font style="font-size: 18px;">查看概率</font>',
                handler:function(){
                    zhuang = parseFloat(zhuang);
                    xian = parseFloat(xian);
                    he = parseFloat(he);
                    var total = zhuang + xian + he;
                    var zhuangGailv = Math.round(zhuang / total * 10000) / 100.00 + "%";
                    var xianGailv = Math.round(xian / total * 10000) / 100.00 + "%";
                    var heGailv = Math.round(he / total * 10000) / 100.00 + "%";
                    Ext.Msg.alert('提示','庄：' + zhuangGailv + ", 闲：" + xianGailv + ", 和：" + heGailv);
                }
            },
            {
                text:'<font style="font-size: 18px;">查看所在大局</font>',
                handler:function(){
                    var roleRecord = dataGrid.getSelectionModel().getSelected();
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
    dataGrid.setAutoScroll(true);

    new Ext.Viewport({
        layout : 'border',
        items : [ dateSearchForm, dataGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });

    var episGrid = new Ext.grid.GridPanel({
        id : 'episGrid',
        store : jsonEpisData,
        region : 'center',
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        height : 480,
        autoExpandColumn : 'TIME',
        columns : [{
            id : 'ID',
            header : '编号',
            dataIndex : 'ID',
            align : 'center',
            hidden : true
        }, {
            id : 'POINT',
            header : '第几局',
            dataIndex : 'POINT',
            sortable : true,
            align : 'center',
            width : 60,
            css:'font-size:18px;',
            menuDisabled : true,
            renderer : function(v) {
                var roleRecord = dataGrid.getSelectionModel().getSelected();
                var value =  roleRecord.get("POINT");
                if (value == v){
                    return "<font style='color: red'>" + v +"</font>"
                }
                return v;
            }
        }, {
            id : 'XIAN1',
            header : '闲牌1',
            dataIndex : 'XIAN1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:#0e49e8;',
            width : 60,
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
            width : 60,
            menuDisabled : true,
            renderer : function(v) {
                var substr = showData2(v);
                return substr;
            }
        }, {
            id : 'XIAN2',
            header : '闲牌2',
            dataIndex : 'XIAN2',
            sortable : true,
            css:'font-size:18px;color:#0e49e8;',
            align : 'center',
            width : 60,
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
            width : 60,
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
            width : 60,
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
            width : 60,
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
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            width : 60,
            align : 'center',
            css:'font-size:18px;',
            menuDisabled : true
        }, {
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : true,
            width : 60,
            css:'font-size:18px;',
            align : 'center',
            menuDisabled : true
        }, {
            id : 'VALUE',
            header : '开注结果',
            dataIndex : 'VALUE',
            css:'font-size:18px;color:red;',
            sortable : true,
            width : 80,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'JISHUCOUNT',
            header : '奇数个数',
            dataIndex : 'JISHUCOUNT',
            css:'font-size:18px;',
            sortable : true,
            width : 80,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'OUSHUCOUNT',
            header : '偶数个数',
            dataIndex : 'OUSHUCOUNT',
            css:'font-size:18px;',
            width : 80,
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
        enableColumnMove : true,
        bbar : new Ext.PagingToolbar({
            pageSize : 80,
            store : jsonEpisData,
            displayInfo : true,
            displayMsg : '显示{0}/{1}of{2}',
            emptyMsg : '没有数据',
            plugins : new Ext.ux.ProgressBarPager()
        })
    });

    /**
     *查看小局列表
     */
    var sepisodesWin = new Ext.Window({
        id : 'sepisodesWin',
        title : '小局列表',
        width : 900,
        height : 480,
        autoHeight : true,
        collapsible : true,
        modal : true,
        items : [episGrid ],
        closable : true,
        closeAction : 'hide',
        resizable : false,
        y : 0,
        listeners : {
            beforeshow : function() {
            }
        }
    });

});