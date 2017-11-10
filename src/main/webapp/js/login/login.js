Ext.onReady(function(){
    var panel = new Ext.form.FormPanel({
        title:'<font style="font-size: 18px;">百家乐数据分析平台</font>',
        width:600,
        frame:true,
        border:false,
        region:'center',
        items:[
            {
                xtype:'panel',
                layout:'column',
                border:false,
                width:600,
                style:'margin-top:10px;',
                items:[
                    {
                        xtype:'panel',
                        width:80,
                        height:80,
                        style :'margin-left:25px;margin-top:10px',
                        columnWidth:.2,
                        labelAlign:'right',
                        html:'<img width=80 height=80 src='+path+'image/login.png></img>'
                    },
                    {
                        xtype:'panel',
                        border:false,
                        columnWidth:.8,
                        layout:'form',
                        labelSeparator:':',
                        defaults:{width:200},
                        labelWidth:70,
                        style:'margin-top:20px',
                        labelAlign:'right',
                        items:[{
                            xtype:'textfield',
                            fieldLabel:'<font style="font-size: 18px;">用户名</font><font color="red">*</font>',
                            name:'username',
                            style:'margin-top:1px;height:25px;',
                            allowBlank:false
                        },{
                            xtype:'textfield',
                            fieldLabel:'<font style="font-size: 18px;">密码</font><font color="red">*</font>',
                            allowBlank:false,
                            name:'password',
                            style:'margin-top:1px;height:25px;',
                            inputType:'password'
                        }]
                    }
                ]
            }
        ]
    });

    var win = new Ext.Window({
        width:500,
        height:250,
        closable:false,
        resizable:false,
        border:false,
        layout:'border',
        y:200,
        buttonAlign:'center',
        items:[panel],
        buttons:[{
            xtype:'button',
            text:'<font style="font-size: 18px;padding-left: 50px;padding-right: 50px;padding-top: 10px;padding-bottom: 17px;">登录</font>',
            region:'center',
            handler:function(){
                var boo = panel.getForm().isValid(); //是否可见
                if(boo) {
                    panel.getForm().doAction('submit',{
                        url:path + 'login/index',
                        method:'post',
                        waitMsg:'正在登录,请稍后...',
                        waitTitle:'登录中...',
                        success:function(bf,opts) {
                            if(opts.result.success==true) {
                                window.location.href = path + 'private/main/index';
                            } else {
                                Ext.Msg.alert('提示','用户名或密码错误!');
                            }
                        },
                        failure:function(bf,opts) {
                            Ext.Msg.alert('提示','用户名或密码错误!');
                        }
                    });
                }
            }
        }]
    });
    win.show();
});