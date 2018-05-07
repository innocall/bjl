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
                }]
            }]
        }],
        buttons : [ {
            text : '<font style="font-size: 18px">查询</font>',
            handler : function() {
                var ones = dateSearchForm.getForm().findField("one").getValue();
                var twos = dateSearchForm.getForm().findField("two").getValue();
                if (ones == '') {
                    Ext.Msg.alert('提示','请输入第一局的单双数');
                    return;
                }
                if (twos == '') {
                    Ext.Msg.alert('提示','请输入第二局的单双数');
                    return;
                }
                if (ones.split('-').length != 2) {
                    Ext.Msg.alert('提示','请输入正确格式的单双数');
                    return;
                }
                if (twos.split('-').length != 2) {
                    Ext.Msg.alert('提示','请输入正确格式的单双数');
                    return;
                }
                jsonSearchData.load({
                    params : {
                    /*    start : 0,
                        limit : 80,*/
                        category : dateSearchForm.getForm().findField(
                            "category_user").getValue(),
                        one : ones,
                        two :twos
                    }
                });
            }
        } ]
    });

    jsonSearchData.on('beforeload', function(s) {
        jsonSearchData.baseParams = {
            category : dateSearchForm.getForm().findField(
                "category_user").getValue(),
            one : dateSearchForm.getForm().findField("one")
                .getValue(),
            two : dateSearchForm.getForm().findField(
                "two").getValue()
        };
    });

    var zhuang = 0;
    var xian = 0;
    var he = 0;
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
            menuDisabled : true
        },{
            id : 'ZHUANG1',
            header : '庄牌1',
            dataIndex : 'ZHUANG1',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:red;',
            width : 100,
            menuDisabled : true
        },{
            id : 'XIAN2',
            header : '闲牌2',
            dataIndex : 'XIAN2',
            sortable : true,
            css:'font-size:18px;color:#0e49e8;',
            align : 'center',
            width : 100,
            menuDisabled : true
        },  {
            id : 'ZHUANG2',
            header : '庄牌2',
            dataIndex : 'ZHUANG2',
            sortable : true,
            css:'font-size:18px;color:red;',
            align : 'center',
            width : 100,
            menuDisabled : true
        },{
            id : 'XIAN3',
            header : '闲牌3',
            dataIndex : 'XIAN3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:#0e49e8;',
            width : 100,
            menuDisabled : true
        }, {
            id : 'ZHUANG3',
            header : '庄牌3',
            dataIndex : 'ZHUANG3',
            sortable : true,
            align : 'center',
            css:'font-size:18px;color:red;',
            width : 100,
            menuDisabled : true
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

});