Ext.onReady(function () {
    /* 初始化数据源 */
    var jsonReetAnaly = new Ext.data.JsonStore({
        id: 'sreet',
        url: path + 'data/analysis/findSReetData',
        root: 'sreet',
        totalProperty: 'count',
        fields: ['ID','ROOMID','POINT','OLDEVENRESULTA','OLDEVENRESULTB','OLDEVENRESULTC','OLDEVENRESULTTYPE','OLDEVENRESULTAVALUE','VALUE','OLDEVENRESULTVALUE','TIME']
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
            width: 70,
            css: 'font-size:13px;color:#red;',
            menuDisabled: true,
            hidden: true
        }, {
            id: 'OLDEVENRESULTA',
            header: 'MNR庄',
            dataIndex: 'OLDEVENRESULTA',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 70,
            menuDisabled: true
        }, {
            id: 'OLDEVENRESULTB',
            header: 'MNR闲',
            dataIndex: 'OLDEVENRESULTB',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 70,
            menuDisabled: true
        }, {
            id: 'OLDEVENRESULTC',
            header: 'MNR和',
            dataIndex: 'OLDEVENRESULTC',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 70,
            menuDisabled: true
        },{
            id: 'OLDEVENRESULTTYPE',
            header: 'MNR组',
            dataIndex: 'OLDEVENRESULTTYPE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 70,
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
            id: 'OLDEVENRESULTAVALUE',
            header: 'MN预测结果',
            dataIndex: 'OLDEVENRESULTAVALUE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 100,
            menuDisabled: true
        },{
            id: 'VALUE',
            header: '实际结果',
            dataIndex: 'VALUE',
            sortable: true,
            align: 'center',
            css: 'font-size:18px;color:#0e49e8;',
            width: 70,
            menuDisabled: true
        },{
            id: 'OLDEVENRESULTVALUE',
            header: 'MN预测',
            dataIndex: 'OLDEVENRESULTVALUE',
            sortable: true,
            align: 'center',
            width: 60,
            menuDisabled: true,
            renderer: function (v) {
                if (v == 'A')  {
                    return "<img src='../../image/accept.png' />";
                } else if (v == 'B') {
                    return "<img src='../../image/error.png' />";
                } else if (v == 'C') {
                    return "<img src='../../image/error.png' />";
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
                        url : path + 'data/analysis/watchProbability',
                        params: {
                            type : 'OLDEVENRESULTVALUE'
                        },
                        method: 'POST',
                        waitMsg:'正在查询,请稍后...',
                        waitTitle:'查询中...',
                        success: function (response, options) {
                            var jsonString = response.responseText;
                            var jsObject = JSON.parse(jsonString);    //转换为json对象
                            var zhuang = parseFloat(jsObject.zhangSize);
                            var xian = parseFloat(jsObject.xianSize);
                            var he = parseFloat(jsObject.heSize);
                            var total = parseFloat(jsObject.allSize);
                            var zhuangGailv = Math.round(zhuang / total * 10000) / 100.00 + "%";
                            var xianGailv = Math.round(xian / total * 10000) / 100.00 + "%";
                            var heGailv = Math.round(he / total * 10000) / 100.00 + "%";
                            Ext.Msg.alert('提示','庄：' + zhuangGailv + ", 闲：" + xianGailv + ", 和：" + heGailv);
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