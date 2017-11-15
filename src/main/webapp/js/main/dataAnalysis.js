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
                    store: ['全部', '庄', '闲'],
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
        autoExpandColumn : 'TOUZHU',
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
            width : 300,
            css:'font-size:13px;',
            menuDisabled : true
        }, {
            id : 'ZHUANG1',
            header : '庄牌1',
            dataIndex : 'ZHUANG1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
               return showData2(v);
            }
        }, {
            id : 'ZHUANG2',
            header : '庄牌2',
            dataIndex : 'ZHUANG2',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                return showData2(v);
            }
        }, {
            id : 'ZHUANG3',
            header : '庄牌3',
            dataIndex : 'ZHUANG3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    return showData2(v);
                }
            }
        }, {
            id : 'XIAN1',
            header : '闲牌1',
            dataIndex : 'XIAN1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                return showData2(v);
            }
        }, {
            id : 'XIAN2',
            header : '闲牌2',
            dataIndex : 'XIAN2',
            sortable : true,
            css:'font-size:18px;',
            align : 'center',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                return showData2(v);
            }
        }, {
            id : 'XIAN3',
            header : '闲牌3',
            dataIndex : 'XIAN3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;',
            width : 100,
            menuDisabled : true,
            renderer : function(v) {
                if (v == '0') {
                    return "";
                } else {
                    return showData2(v);
                }
            }
        }, {
            id : 'ZHUANGVALUE',
            header : '庄点数',
            dataIndex : 'ZHUANGVALUE',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:red;',
            menuDisabled : true
        }, {
            id : 'XIANVALUE',
            header : '闲点数',
            dataIndex : 'XIANVALUE',
            sortable : true,
            css:'font-size:18px;color:#0e49e8;',
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
            }, {
            id : 'TOUZHUMONEY',
            header : '投注金额',
            dataIndex : 'TOUZHUMONEY',
            css:'font-size:15px;',
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
        })
    });

    yhglGrid.setAutoScroll(true);

    //右击行触发事件
    yhglGrid.addListener('rowcontextmenu', rightClickFn);
    yhglGrid.addListener('cellcontextmenu',cellclick);
    new Ext.Viewport({
        layout : 'border',
        items : [ dateSearchForm, yhglGrid ],
        buttons : [ {
            text : 'One'
        } ]
    });

    function rightClickFn(yhglGrid, rowIndex, e) {
        e.preventDefault();
        rightMenu.showAt(e.getXY());
        //gridpanel默认右击是不会选择当前行的，所以必须添加这句代码
        yhglGrid.getSelectionModel().selectRow(rowIndex);
    }

    //获取选中行选中列的值
    function cellclick(grid, rowIndex, columnIndex, e) {
        var record = grid.getStore().getAt(rowIndex);
        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
        //info为一个全局变量
        info = record.get(fieldName);
    }

    function copy() {
        var record = yhglGrid.getSelectionModel().getSelected();
        if(record == undefined) {
            Ext.Msg.alert('提示信息','未选择任何数据！');
        } else {
            copyToClipboard(info);
        }
    }

    var rightMenu = new Ext.menu.Menu( {
        id : 'rightClickCont',
        items : [{
            id:'rMenu1',
            text:'复 制',
            icon:'../../image/clipboardcopy.png',
            handler:copy
        }]
    });

    function copyToClipboard(txt) {
        if(window.clipboardData) {
            window.clipboardData.clearData();
            window.clipboardData.setData("Text", txt);
        } else if(navigator.userAgent.indexOf("Opera") != -1) {
            window.location = txt;
        } else if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
            }
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
            if (!clip)
                return;
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans)
                return;
            trans.addDataFlavor('text/unicode');
            var str = new Object();
            var len = new Object();
            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
            var copytext = txt;
            str.data = copytext;
            trans.setTransferData("text/unicode",str,copytext.length*2);
            var clipid = Components.interfaces.nsIClipboard;
            if (!clip)
                return false;
            clip.setData(trans,null,clipid.kGlobalClipboard);
        }
    }
});