Ext.onReady(function() {
    /* 初始化数据源 */
    var jsonUser = new Ext.data.JsonStore({
        id : 'yhgl',
        url : path + 'private/main/roomAllData',
        root : 'yhgl',
        totalProperty : 'count',
        fields : [ 'ID', 'TOTALCOUNT', 'ZHUANGCOUNT', 'XIANCOUNT', 'HECOUNT','ZHUANGDUICOUNT', 'XIANDUICOUNT', 'STRARTTIME','ENDTIME','USERID','JISHUCOUNT','OUSHUCOUNT' ]
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
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TIME','TOUZHU','ZHUANGVALUE','XIANVALUE','POINT','VALUE','JISHUCOUNT','OUSHUCOUNT'  ]
    });

    //实时添加数据
    var jsonEpisData2 = new Ext.data.JsonStore({
        fields : [
            {name:'ROOMID',type:'string'},
            {name:'ZHUANG1',type:'string'},
            {name:'ZHUANG2',type:'string'},
            {name:'ZHUANG3',type:'string'},
            {name:'XIAN1',type:'string'},
            {name:'XIAN2',type:'string'},
            {name:'XIAN3',type:'string'},
            {name:'XIANVALUE',type:'string'},
            {name:'ZHUANGVALUE',type:'string'},
            {name:'TIME',mapping: 'availability', type: 'date', dateFormat: 'Y-m-d H:m:s'},
            {name:'POINT',type:'int'}
        ]
    });

    var dateSearchForm = new Ext.FormPanel({
        region : 'north',
        frame : true,
        height : 80,
        labelWidth:80,
        labelAlign:'right',
        items: [{
            layout:'column',   //定义该元素为布局为列布局方式
            border:false,
            labelSeparator:'：',
            items:[{
                layout: 'column',   //定义该元素为布局为列布局方式
                border: false,
                labelWidth: 80,
                items: [{
                    columnWidth: .5,  //该列占用的宽度，标识为50％
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: "combo",
                        id: "qxqiang",
                        name: "qxqiang",
                        fieldLabel: '<font style="font-size: 15px">庄闲强</font>',
                        width: 80,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['庄强', '闲强',''],
                        displayField: 'category',
                        triggerAction: 'all'
                    }]
                }, {
                    columnWidth: .5,  //该列占用的宽度，标识为50％
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: "combo",
                        id: "dsqiang",
                        name: "dsqiang",
                        fieldLabel: '<font style="font-size: 15px">单双强</font>',
                        width: 80,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['单数强','双数强',''],
                        displayField: 'category',
                        triggerAction: 'all'
                    }]
                }, {
                    columnWidth: .5,  //该列占用的宽度，标识为50％
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: "combo",
                        id: "lz",
                        name: "lz",
                        fieldLabel: '<font style="font-size: 15px">连庄数</font>',
                        width: 80,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['2', '3', '4', '5', '6', '7', '8',''],
                        displayField: 'category',
                        triggerAction: 'all'
                    }]
                },{
                    columnWidth: .5,  //该列占用的宽度，标识为50％
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: "combo",
                        id: "lx",
                        name: "lx",
                        fieldLabel: '<font style="font-size: 15px">连闲数</font>',
                        width: 80,
                        editable: false,
                        style: 'font-size:15px;',
                        labelSeparator: '：',
                        store: ['2', '3', '4', '5', '6', '7', '8',''],
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
                        qxqiang : dateSearchForm.getForm().findField(
                            "qxqiang").getValue(),
                        dsqiang : dateSearchForm.getForm().findField(
                            "dsqiang").getValue(),
                        lz : dateSearchForm.getForm().findField(
                            "lz").getValue(),
                        lx : dateSearchForm.getForm().findField(
                            "lx").getValue(),
                    }
                });
            }
        } ]
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
            css:'font-size:18px;',
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
            id : 'STRARTTIME',
            header : '时间',
            dataIndex : 'STRARTTIME',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true,
            renderer:function(value){
                return formatDate(value,'Y-m-d H:m:s');
            }
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
                text:'<font style="font-size: 18px;">实时记牌</font>',
                handler:function(){
                    sepisodesWin3.show();
                }
            },
            {
                text:'<font style="font-size: 18px;">添加已有局数</font>',
                handler:function(){
                    addForm.getForm().findField("TOTALCOUNT").setValue("");
                    addForm.getForm().findField("ZHUANGCOUNT").setValue("");
                    addForm.getForm().findField("XIANCOUNT").setValue("");
                    addForm.getForm().findField("HECOUNT").setValue("");
                    addForm.getForm().findField("ZHUANGDUICOUNT").setValue("");
                    addForm.getForm().findField("XIANDUICOUNT").setValue("");
                    editWin.show();
                }
            },
            {
                text:'<font style="font-size: 18px;">删除</font>',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要删除的记录!');
                        return;
                    }
                    Ext.MessageBox.confirm("提示","删除该大局后，该大局的所属小局也同步删除，确定要吗？",function(btn){
                        if(btn=="yes"){
                            var roomId = roleRecord.get("ID");
                            Ext.Ajax.request({
                                url:path+'private/main/deleteRoomById',
                                method:'post',
                                params:{roomId:roomId},
                                success:function(res,ops){
                                    var jsonObj = Ext.util.JSON.decode(res.responseText);
                                    if(jsonObj.success){
                                        jsonUser.remove(roleRecord);
                                    }
                                },
                                failure:function(res,ops){
                                    Ext.Msg.alert('提示',"删除失败");
                                }
                            });
                        }
                    },Ext.MessageBox.YESNO);
                }
            },
            {
                text:'<font style="font-size: 18px;">查看</font>',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要查看记录!');
                        return;
                    }
                    var roomId = roleRecord.get("ID");
                    Ext.getCmp("detailForm_Name").setText(roleRecord.get("TOTALCOUNT"));
                    Ext.getCmp("zhuang").setText(roleRecord.get("ZHUANGCOUNT"));
                    Ext.getCmp("xian").setText(roleRecord.get("XIANCOUNT"));
                    Ext.getCmp("he").setText(roleRecord.get("HECOUNT"));
                    Ext.getCmp("zhuangdui").setText(roleRecord.get("ZHUANGDUICOUNT"));
                    Ext.getCmp("xiandui").setText(roleRecord.get("XIANDUICOUNT"));
                    jsonEpisData.load({
                        params : {
                            roomId : roomId
                        }
                    });
                    sepisodesWin.show();
                }
            }
        ]
    });

    yhglGrid.setAutoScroll(true);

    new Ext.Viewport({
        layout : 'border',
        items : [dateSearchForm,yhglGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });


    var addForm = new Ext.form.FormPanel({
        frame:true,
        labelAlign:'right',
        labelWidth:120,
        fileUpload:true,
        items:[{
                xtype:'numberfield',
                name:'TOTALCOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">总小局数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
            },{
                xtype:'numberfield',
                name:'ZHUANGCOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">开庄数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
            },{
                xtype:'numberfield',
                name:'XIANCOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">开闲数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
             },{
                xtype:'numberfield',
                name:'HECOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">开和数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
            },{
                xtype:'numberfield',
                name:'ZHUANGDUICOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">庄对数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
            },{
                xtype:'numberfield',
                name:'XIANDUICOUNT',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">闲对数</font>',
                labelSeparator:'：',
                hideTrigger:false,//隐藏微调按钮
                allowDecimals:false,//不允许输入小数
                allowBlank:false
            }
        ]
    });

    var addReetForm = new Ext.form.FormPanel({
        frame:true,
        labelAlign:'right',
        labelWidth:120,
        fileUpload:true,
        items:[
            {
                xtype:'textfield',
                name:'XIAN1',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">闲牌1</font>',
                labelSeparator:'：',
                allowBlank:false
            },{
                xtype:'textfield',
                name:'ZHUANG1',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">庄牌1</font>',
                labelSeparator:'：',
                allowBlank:false,
                listeners: {
                render: function (obj) {
                    var font = document.createElement("font");
                    font.setAttribute("color", "red");
                    var redStar = document.createTextNode('   输入规则A-K');
                    font.appendChild(redStar);
                    obj.el.dom.parentNode.appendChild(font);
                 }
             }
            },{
                xtype:'textfield',
                name:'XIAN2',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">闲牌2</font>',
                labelSeparator:'：',
                allowBlank:false
            },{
                xtype:'textfield',
                name:'ZHUANG2',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">庄牌2</font>',
                labelSeparator:'：',
                allowBlank:false
            },{
                xtype:'textfield',
                name:'ZHUANG3',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">庄牌3</font>',
                labelSeparator:'：',
                listeners: {
                    render: function (obj) {
                        var font = document.createElement("font");
                        font.setAttribute("color", "red");
                        var redStar = document.createTextNode('   庄没有补牌请不填写');
                        font.appendChild(redStar);
                        obj.el.dom.parentNode.appendChild(font);
                    }
                }
             },{
                xtype:'textfield',
                name:'XIAN3',
                width:200,
                fieldLabel:'<font style="font-size: 16px;">闲牌3</font>',
                labelSeparator:'：',
                listeners: {
                    render: function (obj) {
                        var font = document.createElement("font");
                        font.setAttribute("color", "red");
                        var redStar = document.createTextNode('   闲没有补牌请不填写');
                        font.appendChild(redStar);
                        obj.el.dom.parentNode.appendChild(font);
                    }
                }
            }
        ]
    });

    /**
     * 添加大局表单
     */
    var editWin = new Ext.Window({
        id:'editWin',
        title:'<font style="font-size: 16px;">添加大局</font>',
        width:450,
        height:830,
        y:20,
        autoHeight:true,
        modal:true,
        items:[addForm],
        closable:true,
        closeAction:'hide',
        resizable:false,
        buttons:[
            {
                text:'保存',
                handler:function(){
                    addForm.getForm().submit({
                        url:path + 'private/main/saveRoom',
                        method:'post',
                        waitMsg:'正在提交,请稍后...',
                        waitTitle:'提交中...',
                        success:function(bf,op) {
                            if(op.result.success==true) {
                                var Video = Ext.data.Record.create([
                                    {id:'ID',type:'string'},
                                    {id:'TOTALCOUNT',type:'string'},
                                    {id:'ZHUANGCOUNT',type:'string'},
                                    {id:'XIANCOUNT',type:'string'},
                                    {id:'HECOUNT',type:'string'},
                                    {id:'ZHUANGDUICOUNT',type:'string'},
                                    {id:'XIANDUICOUNT',type:'string'},
                                    {id:'STRARTTIME',type:'string'}
                                ]);
                                var role = new Video({
                                    ID:op.result.id,
                                    TOTALCOUNT:op.result.juCount,
                                    ZHUANGCOUNT:op.result.zhuangCount,
                                    XIANCOUNT:op.result.xianCount,
                                    HECOUNT:op.result.heCount,
                                    ZHUANGDUICOUNT:op.result.zhuangDuiCount1,
                                    XIANDUICOUNT:op.result.xianDuiCount,
                                    STRARTTIME:op.result.STRARTTIME
                                });
                                jsonUser.add(role);
                                yhglGrid.getSelectionModel().selectLastRow();
                                editWin.hide();
                            } else {
                                Ext.Msg.alert('提示',opts.result.msg);
                            }
                        },
                        failure:function(bf,opts) {
                            Ext.Msg.alert('提示',opts.result.msg);
                        }
                    });
                }
            },
            {
                text:'关闭',
                handler:function(){
                    editWin.hide();
                }
            }
        ],
        listeners:{
            beforeshow:function(){
                //ROLE_NAME.setValue('');
                //ROLE_DESC.setValue('');
            }
        }
    });

    /**
     * 添加小局表单
     */
    var editReetWin = new Ext.Window({
        id:'editReetWin',
        title:'<font style="font-size: 16px;">添加小局</font>',
        width:520,
        height:830,
        y:20,
        autoHeight:true,
        modal:true,
        items:[addReetForm],
        closable:true,
        closeAction:'hide',
        resizable:false,
        buttons:[
            {
                text:'保存',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    var roomId = roleRecord.get("ID");
                    addReetForm.getForm().submit({
                        url:path + 'private/main/saveReet',
                        method:'post',
                        params: {
                            roomId: roomId
                        },
                        waitMsg:'正在提交,请稍后...',
                        waitTitle:'提交中...',
                        success:function(bf,op) {
                            if(op.result.success==true) {
                                var Video = Ext.data.Record.create([
                                    {id:'ID',type:'string'},
                                    {id:'ROOMID',type:'string'},
                                    {id:'ZHUANG1',type:'string'},
                                    {id:'ZHUANG2',type:'string'},
                                    {id:'ZHUANG3',type:'string'},
                                    {id:'XIAN1',type:'string'},
                                    {id:'XIAN2',type:'string'},
                                    {id:'TIME',type:'string'},
                                    {id:'TOUZHU',type:'string'},
                                    {id:'ZHUANGVALUE',type:'string'},
                                    {id:'XIANVALUE',type:'string'},
                                    {id:'POINT',type:'string'},
                                    {id:'XIAN3',type:'string'}
                                ]);
                                var role = new Video({
                                    ID:op.result.id,
                                    ROOMID:op.result.ROOMID,
                                    ZHUANG1:op.result.ZHUANG1,
                                    ZHUANG2:op.result.ZHUANG2,
                                    ZHUANG3:op.result.ZHUANG3,
                                    XIAN1:op.result.XIAN1,
                                    XIAN2:op.result.XIAN2,
                                    TOUZHU:op.result.TOUZHU,
                                    ZHUANGVALUE:op.result.ZHUANGVALUE,
                                    XIANVALUE:op.result.XIANVALUE,
                                    POINT:op.result.POINT,
                                    XIAN3:op.result.XIAN3,
                                    TIME:op.result.TIME
                                });
                                jsonEpisData.add(role);
                                episGrid.getSelectionModel().selectLastRow();
                                editReetWin.hide();
                            } else {
                                Ext.Msg.alert('提示',opts.result.msg);
                            }
                        },
                        failure:function(bf,opts) {
                            Ext.Msg.alert('提示',opts.result.msg);
                        }
                    });
                }
            },
            {
                text:'关闭',
                handler:function(){
                    editReetWin.hide();
                }
            }
        ],
        listeners:{
            beforeshow:function(){
                //ROLE_NAME.setValue('');
                //ROLE_DESC.setValue('');
            }
        }
    });

    var detailForm = new Ext.form.FormPanel({
        region : 'north',
        frame : true,
        items : [ {
            xtype : 'panel',
            layout : 'hbox',
            items : [ {
                xtype : 'label',
                style:'font-size:18px;',
                text : '总局数:',
            }, {
                xtype : 'label',
                id : 'detailForm_Name',
                style:'font-size:18px;',
                width : 70
            }, {
                xtype : 'label',
                style:'font-size:18px;',
                text : '开庄:',
            }, {
                xtype : 'label',
                id : 'zhuang',
                style:'font-size:18px;',
                width : 70
            }, {
                xtype : 'label',
                text : '开闲:',
                style:'font-size:18px;',
            }, {
                xtype : 'label',
                id : 'xian',
                style:'font-size:18px;',
                width : 70
            }, {
                xtype : 'label',
                text : '开和:',
                style:'font-size:18px;',
            }, {
                xtype : 'label',
                id : 'he',
                style:'font-size:18px;',
                width : 70
            }, {
                xtype : 'label',
                text : '庄对:',
                style:'font-size:18px;',
            }, {
                xtype : 'label',
                id : 'zhuangdui',
                style:'font-size:18px;',
                width : 70
            }, {
                xtype : 'label',
                text : '闲对:',
                style:'font-size:18px;',
            }, {
                xtype : 'label',
                id : 'xiandui',
                style:'font-size:18px;',
                width : 70
            }]
        } ]
    });

    var episGrid = new Ext.grid.EditorGridPanel({
        id : 'episGrid',
        store : jsonEpisData,
        region : 'center',
        clicksToEdit:1,
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        height : 460,
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
                    return "<font style='color: #d1961d'>" + v +"</font>"
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
        }, {
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
        }, {
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
        },{
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
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : true,
            width : 60,
            css:'font-size:18px;',
            align : 'center',
            menuDisabled : true
        },{
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            width : 60,
            align : 'center',
            css:'font-size:18px;',
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
        }, {
            id : 'TIME',
            header : '投注时间',
            dataIndex : 'TIME',
            sortable : true,
            css:'font-size:15px;',
            width : 200,
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
        }),
        buttons : [
        {
            text:'<font style="font-size: 18px;">添加</font>',
            handler:function(){
                addReetForm.getForm().findField("ZHUANG1").setValue("");
                addReetForm.getForm().findField("ZHUANG2").setValue("");
                addReetForm.getForm().findField("ZHUANG3").setValue("");
                addReetForm.getForm().findField("XIAN1").setValue("");
                addReetForm.getForm().findField("XIAN2").setValue("");
                addReetForm.getForm().findField("XIAN3").setValue("");
                editReetWin.show();
            }
        },
        {
            text:'<font style="font-size: 18px;">删除</font>',
            handler:function(){
                var roleRecord = episGrid.getSelectionModel().getSelected();
                if(!roleRecord){
                    Ext.Msg.alert('提示','请选中要删除的记录!');
                    return;
                }
                Ext.MessageBox.confirm("提示","确定删除吗？",function(btn){
                    if(btn=="yes"){
                        var id = roleRecord.get("ID");
                        Ext.Ajax.request({
                            url:path+'private/main/deleteReetById',
                            method:'post',
                            params:{id:id},
                            success:function(res,ops){
                                var jsonObj = Ext.util.JSON.decode(res.responseText);
                                if(jsonObj.success){
                                    jsonEpisData.remove(roleRecord);
                                }
                            },
                            failure:function(res,ops){
                                Ext.Msg.alert('提示',"删除失败");
                            }
                        });
                    }
                },Ext.MessageBox.YESNO);
            }
        } ]
    });

    /**
     *查看小局列表
     */
    var sepisodesWin = new Ext.Window({
        id : 'sepisodesWin',
        title : '小局列表',
        width : 900,
        height : 460,
        autoHeight : true,
        collapsible : true,
        modal : true,
        items : [detailForm,episGrid],
        closable : true,
        closeAction : 'hide',
        resizable : false,
        y : 0,
        listeners : {
            beforeshow : function() {
            }
        }
    });

    var episGrid2 = new Ext.grid.EditorGridPanel({
        id : 'episGrid2',
        store : jsonEpisData2,
        region : 'center',
        clicksToEdit:1,
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        height : 480,
        autoExpandColumn : 'TIME',
        columns : [{
            id : 'POINT',
            header : '<font style="font-size: 16px;">第几局</font>',
            dataIndex : 'POINT',
            sortable : true,
            align : 'center',
            width : 70,
            editor:new Ext.form.NumberField({
                allowBlank: false
            }),
            css:'font-size:18px;',
            menuDisabled : true
        }, {
            id : 'XIAN1',
            header : '<font style="font-size: 16px;">闲牌1</font>',
            dataIndex : 'XIAN1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            editor:new Ext.form.TextField({
                    allowBlank: false
            }),
            width : 70,
            menuDisabled : true
        },{
            id : 'ZHUANG1',
            header : '<font style="font-size: 16px;">庄牌1</font>',
            dataIndex : 'ZHUANG1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            editor:new Ext.form.TextField({
                allowBlank: false
            }),
            width : 70,
            menuDisabled : true
        }, {
            id : 'XIAN2',
            header : '<font style="font-size: 16px;">闲牌2</font>',
            dataIndex : 'XIAN2',
            sortable : true,
            css:'font-size:18px;',
            editor:new Ext.form.TextField({
                allowBlank: false
            }),
            align : 'center',
            width : 70,
            menuDisabled : true
        }, {
            id : 'ZHUANG2',
            header : '<font style="font-size: 16px;">庄牌2</font>',
            dataIndex : 'ZHUANG2',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            editor:new Ext.form.TextField({
                allowBlank: false
            }),
            width : 70,
            menuDisabled : true
        }, {
            id : 'XIAN3',
            header : '<font style="font-size: 16px;">闲牌3</font>',
            dataIndex : 'XIAN3',
            sortable : true,
            editor:new Ext.form.TextField(),
            align : 'center',
            css:'font-size:18px;',
            width : 70,
            menuDisabled : true
        },{
            id : 'ZHUANG3',
            header : '<font style="font-size: 16px;">庄牌3</font>',
            dataIndex : 'ZHUANG3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            editor:new Ext.form.TextField(),
            width : 70,
            menuDisabled : true
        }, {
            id : 'XIANVALUE',
            header : '<font style="font-size: 16px;">闲点数</font>',
            dataIndex : 'XIANVALUE',
            sortable : true,
            width : 70,
            editor:new Ext.form.NumberField({
                allowBlank: false
            }),
            css:'font-size:18px;',
            align : 'center',
            menuDisabled : true
        },{
            id : 'ZHUANGVALUE',
            header : '<font style="font-size: 16px;">庄点数</font>',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            width : 70,
            editor:new Ext.form.NumberField({
                allowBlank: false
            }),
            align : 'center',
            css:'font-size:18px;',
            menuDisabled : true
        },{
            id : 'TIME',
            header : '<font style="font-size: 16px;">投注时间</font>',
            dataIndex : 'TIME',
            sortable : true,
            css:'font-size:15px;',
            renderer:function(value){
                return value ? value.dateFormat('Y-m-d H:m:s') : '';
            },
            editor: new Ext.form.DateField({
                format: 'Y-m-d H:m:s'
            }),
            width : 200,
            align : 'center',
            menuDisabled : true
        }],
        enableColumnMove : true,
        bbar : new Ext.PagingToolbar({
            pageSize : 80,
            store : jsonEpisData,
            displayInfo : true,
            displayMsg : '显示{0}/{1}of{2}',
            emptyMsg : '没有数据',
            plugins : new Ext.ux.ProgressBarPager()
        }),
        listeners: {
           /* "afterEdit": {
                fn: afterEdit,
                scope: this
            }*/
        },
        tbar : [{
                text:'<font style="font-size: 16px;">添加</font>',
                handler: function() {
                    var Plant = jsonEpisData2.recordType;
                    var p = new Plant({
                        TIME: new Date()
                    });
                    episGrid2.stopEditing();
                    jsonEpisData2.insert(0, p);
                    episGrid2.startEditing(0, 0);
                }
            },
            "-"
            ,{
                text:'<font style="font-size: 16px;">删除</font>',
                handler:function(){
                    var selModel = episGrid2.getSelectionModel();
                    if (selModel.hasSelection()) {
                        Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
                            if (button == "yes") {
                                var selections = selModel.getSelections();
                                Ext.each(selections, function(item) {
                                    jsonEpisData2.remove(item);
                                    jsonEpisData2.removed.push(item);
                                });
                            }
                        });
                    } else {
                        Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                    }
                 }
            },
            "-"
            ,{
                text:'<font style="font-size: 16px;">关闭</font>',
                handler:function() {
                    Ext.Msg.confirm("警告", "确定要关闭添加吗？", function (button) {
                        if (button == "yes") {
                            sepisodesWin3.hide();
                        }
                    })
                }
            },
            '-',
            {
                text: '<font style="font-size: 16px;">保存</font>',
                handler: function() {
                    var mod = jsonEpisData2.modified;
                    updateData(mod);
                }
            }]
    });

    //发送数据到服务器端进行更新
    function updateData(mod) {
        var json = [];
        Ext.each(mod, function(item) {
            json.push(item.data);
        });
        if (json.length > 0) {
            var zhuangCount = 0;
            var xianCount = 0;
            var heCount = 0;
            var zhuangDuiCount1 = 0;
            var xianDuiCount = 0;
            for(var i=0;i<json.length;i++) {
                if (json[i].ZHUANGVALUE >json[i].XIANVALUE) {
                    zhuangCount = zhuangCount + 1;
                } else if (json[i].ZHUANGVALUE ==json[i].XIANVALUE) {
                    heCount = heCount + 1;
                } else if (json[i].ZHUANGVALUE <json[i].XIANVALUE) {
                    xianCount = xianCount + 1;
                }
                if (json[i].ZHUANG1 ==json[i].ZHUANG2 ||json[i].ZHUANG1 ==json[i].ZHUANG3 ||json[i].ZHUANG1==json[i].ZHUANG3) {
                    zhuangDuiCount1 = zhuangDuiCount1 + 1;
                }
                if (json[i].XIAN1 ==json[i].XIAN2 ||json[i].XIAN1 ==json[i].XIAN3 ||json[i].XIAN2==json[i].XIAN3) {
                    xianDuiCount = xianDuiCount + 1;
                }
            }
            Ext.Ajax.request({
                url: path + 'private/main/submitRootDate',
                params: {
                    data: Ext.util.JSON.encode(json),
                    zhuangCount:zhuangCount,
                    xianCount:xianCount,
                    heCount:heCount,
                    zhuangDuiCount1:zhuangDuiCount1,
                    xianDuiCount:xianDuiCount
                },
                method: "POST",
                waitMsg:'正在提交,请稍后...',
                waitTitle:'提交中...',
                success: function(response) {
                    if(response.status==200) {
                        Ext.Msg.alert("信息", "数据更新成功！", function() {
                            jsonEpisData2.reload;
                            jsonUser.reload;
                            sepisodesWin3.hide();
                        });
                    } else {
                        Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");
                    }
                },
                failure: function(response) {
                    Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");
                }
            });
        } else {
            Ext.Msg.alert("警告", "没有任何需要更新的数据！");
        }
    }

    //编辑后触发的事件，可在此进行数据有效性的验证
   /* function afterEdit(e) {
        if (e.field == 'common') {
            if (e.value == '123') {
                Ext.Msg.alert("错误", "大笨是人物不是植物", function() {
                    episGrid2.startEditing(e.row, e.column)
                });
            }
        }
    }*/

    /**
     *实时记牌列表
     */
    var sepisodesWin3 = new Ext.Window({
        id : 'sepisodesWin3',
        title : '小局列表',
        width : 880,
        height : 480,
        autoHeight : true,
        collapsible : true,
        modal : true,
        closable: false,
        items : [episGrid2],
        closeAction : 'hide',
        resizable : false,
        y : 0,
        listeners : {
            beforeshow : function() {
            }
        }
    });

});