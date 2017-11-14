Ext.onReady(function() {
    /* 初始化数据源 */
    var jsonUser = new Ext.data.JsonStore({
        id : 'yhgl',
        url : path + 'private/main/reetAllData',
        root : 'yhgl',
        totalProperty : 'count',
        fields : [ 'ID', 'ROOMID', 'ZHUANG1', 'ZHUANG2', 'ZHUANG3','XIAN1', 'XIAN2', 'XIAN3','TOUZHUMONEY','TOUZHU','ZHUANGVALUE','XIANVALUE','TIME' ]
    });
    jsonUser.load({
        params : {
            start : 0,
            limit : 80
        }
    });

   /* jsonUser.on('beforeload', function(store, options) {
        category_user = Ext.get("category_user").dom.value;
        category_user2 = Ext.get("category_user2").dom.value;
        query = Ext.get("query").dom.value;
        endDate = Ext.get("endDate").dom.value;
        startDate = Ext.get("startDate").dom.value;
        Ext.apply(this.baseParams, {
            category : category_user,
            category2 : category_user2,
            startDate : startDate,
            endDate : endDate,
            query : query
        });
    });*/

    var dateSearchForm = new Ext.form.FormPanel({
        region : 'north',
        frame : true,
        height : 200,
        items : [{
            xtype: 'panel',
            width: 600,
            layout: 'hbox',
            css: 'font-size:25px;',
            items: [{
                xtype: 'panel',
                width: 500,
                layout: 'form',
                labelAlign: 'right',
                labelWidth: 80,
                items: [{
                    xtype: "combo",
                    id: "category_user",
                    name: "category_user",
                    fieldLabel: '<font style="font-size: 15px">查询对象</font>',
                    width: 300,
                    editable: false,
                    style: 'font-size:15px;',
                    labelSeparator: '：',
                    store: ['全部', '庄', '闲', '和'],
                    displayField: 'category',
                    emptyText: "请选择查询类型",
                    triggerAction: 'all',
                }, {
                    xtype: "combo",
                    id: "category_user2",
                    name: "category_user2",
                    fieldLabel: '<font style="font-size: 15px">查询类型</font>',
                    width: 300,
                    editable: false,
                    style: 'font-size:15px;',
                    labelSeparator: '：',
                    store: ['全部', '单数', '双数'],
                    displayField: 'category',
                    emptyText: "请选择查询类型",
                    triggerAction: 'all',
                }, {
                    xtype: 'datefield',
                    fieldLabel: '<font style="font-size: 15px">开始时间</font>',
                    id: 'startDate',
                    name: 'startDate',
                    style: 'font-size:15px;',
                    labelSeparator: '：',//分隔符
                    format: 'Y-m-d h:i:s',//显示日期的格式
                    maxValue: new Date(),//允许选择的最大日期
                    width: 300,
                    value: '2015年01月01日'
                }, {
                    xtype: 'datefield',
                    fieldLabel: '<font style="font-size: 15px">结束时间</font>',
                    id: 'endDate',
                    name: 'endDate',
                    style: 'font-size:15px;',
                    labelSeparator: '：',//分隔符
                    format: 'Y-m-d h:i:s',//显示日期的格式
                    maxValue: new Date(),//允许选择的最大日期
                    width: 300,
                    value: new Date()
                }, {
                    xtype: 'textfield',
                    id: 'query',
                    name: 'query',
                    style: 'font-size:15px;',
                    width: 150,
                    fieldLabel: '<font style="font-size: 15px">查询点数</font>',
                    labelSeparator: '：',
                    listeners: {
                        render: function(obj) {
                            var font=document.createElement("font");
                            font.setAttribute("color","red");
                            font.setAttribute("style","font-size:15px;")
                            var redStar=document.createTextNode('   多个数子用-号分割，如：3-6-9');
                            font.appendChild(redStar);
                            obj.el.dom.parentNode.appendChild(font);
                        }
                    }
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
                        category2 : dateSearchForm.getForm().findField(
                            "category_user2").getValue(),
                        startDate : dateSearchForm.getForm().findField(
                            "startDate").getValue(),
                        endDate : dateSearchForm.getForm().findField(
                            "endDate").getValue(),
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
        autoExpandColumn : 'TIME',
        columns : [ {
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
            width : 150,
            menuDisabled : true
        }, {
            id : 'ZHUANG1',
            header : '庄1',
            dataIndex : 'ZHUANG1',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true
        }, {
            id : 'ZHUANG2',
            header : '庄2',
            dataIndex : 'ZHUANG2',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true
        }, {
            id : 'ZHUANG3',
            header : '庄3',
            dataIndex : 'ZHUANG3',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    return v;
                }
            }
        }, {
            id : 'XIAN1',
            header : '闲1',
            dataIndex : 'XIAN1',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true
        }, {
            id : 'XIAN2',
            header : '闲2',
            dataIndex : 'XIAN2',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true
        }, {
            id : 'XIAN3',
            header : '闲3',
            dataIndex : 'XIAN3',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    return v;
                }
            }
        }, {
            id : 'TOUZHUMONEY',
            header : '投注金额',
            dataIndex : 'TOUZHUMONEY',
            sortable : false,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'TOUZHU',
            header : '投注对象',
            dataIndex : 'TOUZHU',
            sortable : true,
            align : 'center',
            width : 150,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "庄";
                } else if(v == '1'){
                    return "闲";
                } else if(v == '2'){
                    return "和";
                } else {
                    return "未下注";
                }
            }
        }, {
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : false,
            align : 'center',
            menuDisabled : true
        }, {
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : false,
            align : 'center',
            menuDisabled : true
        },{
            id : 'TIME',
            header : '投注时间',
            dataIndex : 'TIME',
            sortable : false,
            align : 'center',
            menuDisabled : true
        } ],
        enableColumnMove : true, // 允许拖动列
        bbar : new Ext.PagingToolbar({
            pageSize : 20,
            store : jsonUser,
            displayInfo : true,
            displayMsg : '显示{0}/{1}of{2}',
            emptyMsg : '没有数据',
            plugins : new Ext.ux.ProgressBarPager()
        }),
        buttons : [ {
            text:'增加',
            handler:function(){
                addForm.getForm().findField("Id").setValue("");
                addForm.getForm().findField("Name").setValue("");
                addForm.getForm().findField("ModuleId").setValue("");
                addForm.getForm().findField("Gender").setValue("");
                addForm.getForm().findField("Profile").setValue("");
                addWin.show();
            }
        },
            {
                text:'修改',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert('提示','请选中要修改的记录!');
                        return;
                    }else{
                        addForm.getForm().findField("Id").setValue(roleRecord.get("Id"));
                        addForm.getForm().findField("Name").setValue(roleRecord.get("Name"));
                        var module = roleRecord.get("ModuleId");
                        if (module == 2) {
                            module = '专家问诊';
                        } else {
                            module = '老年大学';
                        }
                        addForm.getForm().findField("ModuleId").setValue(module);
                        var gender = roleRecord.get("Gender");
                        if (gender == true) {
                            gender = '男';
                        } else {
                            gender = '女';
                        }
                        addForm.getForm().findField("Gender").setValue(gender);
                        addForm.getForm().findField("Profile").setValue(roleRecord.get("Profile"));
                        addWin.show();
                    }
                }
            },
            {
                text:'删除',
                handler:function(){
                    var roleRecord = yhglGrid.getSelectionModel().getSelected();
                    if(!roleRecord){
                        Ext.Msg.alert("提示","请选中要删除的行．");
                        return;
                    }
                    if(roleRecord.get("ROLE_CANDEL")=="0"){
                        Ext.Msg.alert("提示","系统用户不能删除!");
                        return;
                    }
                    Ext.MessageBox.confirm("提示","确定要删除这行记录吗？",function(btn){
                        if(btn=="yes"){
                            var ROLE_ID = roleRecord.get("Id");
                            Ext.Ajax.request({
                                url:path+'private/teacher/teacherDel',
                                method:'post',
                                params:{Id:ROLE_ID},
                                success:function(res,ops){
                                    var jsonObj = Ext.util.JSON.decode(res.responseText);
                                    if(jsonObj.success){
                                        jsonUser.remove(roleRecord);
                                    }
                                },
                                failure:function(res,ops){
                                    alert(res.responseText);
                                }
                            });
                        }
                    },Ext.MessageBox.YESNO);
                }
            }]
    });
    yhglGrid.setAutoScroll(true);
    new Ext.Viewport({
        layout : 'border',
        items : [ dateSearchForm, yhglGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });

    var addWin = new Ext.Window({
        id:'addWin',
        title:'增加',
        width:550,
        height:830,
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
                    if(!addForm.getForm().isValid()){				//没有写入数据则不提交
                        return;
                    };
                    var ROLE_NAME = addForm.getForm().findField("Name").getValue();
                    var id = addForm.getForm().findField("Id").getValue();
                    var idx = jsonUser.find("Name",ROLE_NAME.trim());
                    if(idx>=0 && id == ''){
                        Ext.Msg.alert("提示","此讲师已经录入过，请换一个．");
                        return;
                    }
                    addForm.getForm().submit({
                        url:path+'private/teacher/teacherSave',
                        method:'post',
                        success:function(f,op){
                            if(op.result.success==true){
                                if(op.result.save==true){				//如果是保存的话
                                    var Role = Ext.data.Record.create([
                                        {id:'Id',type:'string'},
                                        {id:'Name',type:'string'},
                                        {id:'Gender',type:'string'},
                                        {id:'ModuleId',type:'string'},
                                        {id:'Profile',type:'string'}
                                    ]);
                                    var role = new Role({Id:op.result.Id,
                                        Name:op.result.Name,
                                        Gender:op.result.Gender,
                                        ModuleId:op.result.ModuleId,
                                        Profile:op.result.Profile
                                    });
                                    jsonUser.add(role);
                                    yhglGrid.getSelectionModel().selectLastRow();
                                }else{
                                    var rec = yhglGrid.getSelectionModel().getSelected();
                                    rec.set("Name",op.result.Name);
                                    rec.set("Gender",op.result.Gender);
                                    rec.set("ModuleId",op.result.ModuleId);
                                    rec.set("Profile",op.result.Profile);
                                }
                                addWin.hide();
                            }else{
                                Ext.Msg.alert("MSG","服务器访问不成功.");
                            }
                        },
                        failure:function(f,op){
                            alert("Error....");
                        }
                    });
                }
            },
            {
                text:'关闭',
                handler:function(){
                    addWin.hide();
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

});