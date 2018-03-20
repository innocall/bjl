Ext.onReady(function() {
    /* 初始化数据源 */
    var jsonUser = new Ext.data.JsonStore({
        id : 'yhgl',
        url : path + 'private/main/reetAllData',
        root : 'yhgl',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TOUZHUMONEY','TOUZHU','ZHUANGVALUE','XIANVALUE','TIME','POINT','VALUE','JISHUCOUNT','OUSHUCOUNT' ]
    });

    jsonUser.load({
        params : {
            start : 0,
            limit : 80,
        }
    });

    var jsonEpisData = new Ext.data.JsonStore({
        id : 'reetList',
        url : path + 'private/main/findReetById',
        root : 'reetList',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TIME','TOUZHU','ZHUANGVALUE','XIANVALUE','POINT','VALUE','JISHUCOUNT','OUSHUCOUNT'  ]
    });

    var dateSearchForm = new Ext.FormPanel({
        region : 'north',
        frame : true,
        height : 140,
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
                    xtype: "combo",
                    id: "category_user",
                    name: "category_user",
                    fieldLabel: '<font style="font-size: 15px">查询对象</font>',
                    width: 300,
                    editable: false,
                    style: 'font-size:15px;',
                    labelSeparator: '：',
                    store: ['全部', '庄', '闲'],
                    displayField: 'category',
                    emptyText: "请选择查询类型",
                    triggerAction: 'all',
                }, {
                    xtype: 'textfield',
                    id: 'query',
                    name: 'query',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">查询组合</font>',
                    labelSeparator: '：',
                    listeners: {
                        render: function(obj) {
                            var font=document.createElement("font");
                            font.setAttribute("color","red");
                            font.setAttribute("style","font-size:15px;")
                            var redStar=document.createTextNode('   多个数子用-号分割，如：A-6-K');
                            font.appendChild(redStar);
                            obj.el.dom.parentNode.appendChild(font);
                        }
                    }
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype: 'datefield',
                    fieldLabel: '<font style="font-size: 15px">开始时间</font>',
                    id: 'startDate',
                    name: 'startDate',
                    style: 'font-size:15px;',
                    labelSeparator: '：',//分隔符
                    format: 'Y-m-d H:i:s',//显示日期的格式
                    maxValue: new Date(),//允许选择的最大日期
                    width: 300,
                    value: '2015-11-01 12:00:00'
                }, {
                    xtype: 'datefield',
                    fieldLabel: '<font style="font-size: 15px">结束时间</font>',
                    id: 'endDate',
                    name: 'endDate',
                    style: 'font-size:15px;',
                    labelSeparator: '：',//分隔符
                    format: 'Y-m-d H:i:s',//显示日期的格式
                    maxValue: new Date(),//允许选择的最大日期
                    width: 300,
                    value: new Date()
                }]
            }, {
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
                        fieldLabel: '<font style="font-size: 15px">奇数个数</font>',
                        width: 60,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['0', '1', '2', '3', '4', '5', '6', '7', '8',''],
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
                        fieldLabel: '<font style="font-size: 15px">偶数个数</font>',
                        width: 60,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['0', '1', '2', '3', '4', '5', '6', '7', '8',''],
                        displayField: 'category',
                        triggerAction: 'all'
                    }]
                }, {
                    columnWidth: .5,  //该列占用的宽度，标识为50％
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: "combo",
                        id: "ling",
                        name: "ling",
                        fieldLabel: '<font style="font-size: 15px">0的个数</font>',
                        width: 60,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['0', '1', '2', '3', '4', '5', '6', '7', '8',''],
                        displayField: 'category',
                        triggerAction: 'all'
                    }]
                }]
            }]
        }],
        buttons : [ {
            text : '<font style="font-size: 18px">查询</font>',
            handler : function() {
                jsonUser.load({
                    params : {
                        start : 0,
                        limit : 80,
                        category : dateSearchForm.getForm().findField(
                            "category_user").getValue(),
                        query : dateSearchForm.getForm().findField("query")
                            .getValue(),
                        startDate : dateSearchForm.getForm().findField(
                            "startDate").getValue(),
                        endDate : dateSearchForm.getForm().findField(
                            "endDate").getValue(),
                        jishu : dateSearchForm.getForm().findField(
                            "jishu").getValue(),
                        oushu : dateSearchForm.getForm().findField(
                            "oushu").getValue(),
                        ling : dateSearchForm.getForm().findField(
                            "ling").getValue(),
                    }
                });
            }
        } ]
    });

    jsonUser.on('beforeload', function(s) {
        jsonUser.baseParams = {
            category : dateSearchForm.getForm().findField(
                "category_user").getValue(),
            query : dateSearchForm.getForm().findField("query")
                .getValue(),
            startDate : dateSearchForm.getForm().findField(
                "startDate").getValue(),
            endDate : dateSearchForm.getForm().findField(
                "endDate").getValue(),
            jishu : dateSearchForm.getForm().findField(
                "jishu").getValue(),
            oushu : dateSearchForm.getForm().findField(
                "oushu").getValue(),
            ling : dateSearchForm.getForm().findField(
                "ling").getValue(),
        };
    });

        /* 数据列表 */
    var yhglGrid = new Ext.grid.GridPanel({
        id : 'yhglGrid',
        store : jsonUser,
        region : 'center',
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        autoExpandColumn : 'TOUZHU',
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = showData2(v);
                if (value1 != '庄') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = showData2(v);
                if (value1 != '闲') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = showData2(v);
                if (value1 != '庄') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = showData2(v);
                if (value1 != '闲') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
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
                    var value = dateSearchForm.getForm().findField("query").getValue();
                    var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                    var substr = showData2(v);
                    if (value1 != '庄') {
                        if (value.indexOf(substr) >= 0){
                            return "<font style='color: #ff4545'>" + substr +"</font>"
                        }
                    }
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
                    var value = dateSearchForm.getForm().findField("query").getValue();
                    var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                    var substr = showData2(v);
                    if (value1 != '闲') {
                        if (value.indexOf(substr) >= 0){
                            return "<font style='color: #ff4545'>" + substr +"</font>"
                        }
                    }
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
            menuDisabled : true,
            renderer : function(v) {
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = v;
                if (value1 != '庄') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
                return substr;
            }
        }, {
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            menuDisabled : true,
            renderer : function(v) {
                var value = dateSearchForm.getForm().findField("query").getValue();
                var value1 =  dateSearchForm.getForm().findField("category_user").getValue();
                var substr = v;
                if (value1 != '闲') {
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
                }
                return substr;
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
        }, {
            id : 'TOUZHUMONEY',
            header : '投注金额',
            dataIndex : 'TOUZHUMONEY',
            css:'font-size:15px;',
            sortable : true,
            align : 'center',
            menuDisabled : true
        }, {
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
            menuDisabled : true
        }, {
            id : 'TOUZHU',
            header : '投注对象',
            dataIndex : 'TOUZHU',
            sortable : true,
            align : 'center',
            css:'font-size:15px;',
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "下注庄";
                } else if(v == '1'){
                    return "下注闲";
                } else if(v == '2'){
                    return "下注和";
                } else {
                    return "未下注";
                }
            }
        } ],
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
                text:'<font style="font-size: 18px;">查看所在大局</font>',
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
        items : [ dateSearchForm, yhglGrid ],
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
                var roleRecord = yhglGrid.getSelectionModel().getSelected();
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
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
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
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
                    var value = dateSearchForm.getForm().findField("query").getValue();
                    var substr = showData2(v);
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
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
                    var value = dateSearchForm.getForm().findField("query").getValue();
                    var substr = showData2(v);
                    if (value.indexOf(substr) >= 0){
                        return "<font style='color: #ff4545'>" + substr +"</font>"
                    }
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
            menuDisabled : true,
            renderer : function(v) {
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
                return substr;
            }
        }, {
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : true,
            width : 60,
            css:'font-size:18px;',
            align : 'center',
            menuDisabled : true,
            renderer : function(v) {
                var value = dateSearchForm.getForm().findField("query").getValue();
                var substr = showData2(v);
                if (value.indexOf(substr) >= 0){
                    return "<font style='color: #ff4545'>" + substr +"</font>"
                }
                return substr;
            }
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