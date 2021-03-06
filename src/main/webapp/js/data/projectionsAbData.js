Ext.onReady(function () {
    /* 初始化数据源 */
    var jsonReetAnaly = new Ext.data.JsonStore({
        id: 'sreet',
        url: path + 'data/analysis/findSReetData',
        root: 'sreet',
        totalProperty: 'count',
        fields: ['ID','ROOMID','POINT','ABRESULTA','ABRESULTB','ABRESULTC','ABRESULTTYPE','ABRESULTAVALUE','VALUE','ABRESULTVALUE','TIME']
    });

    jsonReetAnaly.load({
        params : {
            start : 0,
            limit : 80,
        }
    });

    /* 数据列表 */
    var sReetGrid = new Ext.grid.GridPanel({
        id: 'sReetGrid',
        store: jsonReetAnaly,
        region: 'center',
        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true
        }),
        autoExpandColumn: 'TIME',
        columns: [{
            id: 'POINT',
            header: '记号',
            dataIndex: 'POINT',
            align: 'center',
            width: 70
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
            width: 70,
            css: 'font-size:13px;color:#red;',
            menuDisabled: true,
            hidden: true
        }, {
            id: 'ABRESULTA',
            header: 'A个数',
            dataIndex: 'ABRESULTA',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 80,
            menuDisabled: true
        }, {
            id: 'ABRESULTB',
            header: 'B个数',
            dataIndex: 'ABRESULTB',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 80,
            menuDisabled: true
        }, {
            id: 'ABRESULTC',
            header: 'C个数',
            dataIndex: 'ABRESULTC',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 80,
            menuDisabled: true
        },{
            id: 'ABRESULTTYPE',
            header: '查询组数',
            dataIndex: 'ABRESULTTYPE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 80,
            menuDisabled: true,
            renderer: function (v) {
                if (v == 'A')  {
                    return '4组';
                } else if(v == 'B'){
                    return '3组';
                } else if(v == 'C'){
                    return '2组';
                }
            }
        },{
            id: 'ABRESULTAVALUE',
            header: '预测结果',
            dataIndex: 'ABRESULTAVALUE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 120,
            menuDisabled: true
        },{
            id: 'VALUE',
            header: '实际结果',
            dataIndex: 'VALUE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 80,
            menuDisabled: true
        },{
            id: 'ABRESULTVALUE',
            header: '预测是否正确',
            dataIndex: 'ABRESULTVALUE',
            sortable: true,
            align: 'center',
            width: 80,
            menuDisabled: true,
            renderer: function (v) {
                if (v == 'A')  {
                    return "<img src='../../image/check.png' width='25px' />";
                } else if (v == 'B') {
                    return "<img src='../../image/close.png' width='25px' />";
                } else if (v == 'C') {
                    return "<img src='../../image/error.png' width='25px' />";
                }
            }
        },{
            id: 'TIME',
            header: '统计时间',
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
        enableColumnMove: true, // 允许拖动列
        bbar: new Ext.PagingToolbar({
            pageSize: 80,
            store: jsonReetAnaly,
            displayInfo: true,
            displayMsg: '显示{0}/{1}of{2}',
            emptyMsg: '没有数据',
            plugins: new Ext.ux.ProgressBarPager()
        }),
        buttons: [
            {
                text: '<font style="font-size: 18px;">查看准确性</font>',
                handler: function () {
                    Ext.Ajax.request({
                        url : path + 'data/analysis/selectProbability ',
                        params: {
                            type : 'ABRESULTVALUE'
                        },
                        method: 'POST',
                        waitMsg:'正在查询,请稍后...',
                        waitTitle:'查询中...',
                        success: function (response, options) {
                            var jsonString = response.responseText;
                            var jsObject = JSON.parse(jsonString);    //转换为json对象
                            var countA = parseFloat(jsObject.countA);
                            var countB = parseFloat(jsObject.countB);
                            var countC = parseFloat(jsObject.countC);
                            var total = parseFloat(jsObject.allTotol);
                            var zhuangGailv = Math.round(countA / total * 10000) / 100.00 + "%";
                            var xianGailv = Math.round(countB / total * 10000) / 100.00 + "%";
                            var heGailv = Math.round(countC / total * 10000) / 100.00 + "%";
                            Ext.Msg.alert('提示','对：' + zhuangGailv + ", 错：" + xianGailv + ", 未知：" + heGailv);
                        },
                        failure: function (response, options) {
                            Ext.MessageBox.alert('失败', '请求超时或网络故障，错误编号：' + response.status);
                        }
                    });
                }
            }
        ]
    });

    sReetGrid.setAutoScroll(true);

    new Ext.Viewport({
        layout: 'border',
        items: [sReetGrid],
        buttons: [{
            text: 'One'
        }]
    });

});