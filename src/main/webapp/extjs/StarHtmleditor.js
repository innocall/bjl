/**
 * 重载EXTJS-HTML编辑器
 * 
 * @class HTMLEditor
 * @extends Ext.form.HtmlEditor
 * @author wuliangbo
 */
HTMLEditor = Ext.extend(Ext.form.HtmlEditor, {
	addImage : function() {
		var editor = this;
		var imgform = new Ext.FormPanel({
			region : 'center',
			labelWidth : 55,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			autoScroll : true,
			border : false,
			fileUpload : true,
			items : [{
						xtype : 'textfield',
						fieldLabel : '选择文件',
						name : 'file',
						inputType : 'file',
						allowBlank : false,
						blankText : '文件不能为空',
						height : 25,
						anchor : '90%'
					}],
			buttons : [{
				text : '上传',
				type : 'submit',
				handler : function() {
					if (!imgform.form.isValid()) {return;}
					imgform.form.submit({
						waitMsg : '正在上传......',
						url : 'upload',
						success : function(form, action) {
							var element = document.createElement("img");
							element.setAttribute("style","width:100%");
							element.src = path + action.result.destPath;
							if (Ext.isIE) {
								editor.insertAtCursor(element.outerHTML);
							} else {
								var selection = editor.win.getSelection();
								if (!selection.isCollapsed) {
									selection.deleteFromDocument();
								}
								selection.getRangeAt(0).insertNode(element);
							}
							 form.reset();
					         win.close();
							//win.hide();
						},
						failure : function(form, action) {
							form.reset();
							Ext.MessageBox.alert('警告','服务器错误');
						}
					});
				}
			}, {
				text : '关闭',
				type : 'submit',
				handler : function() {
					win.close(this);
				}
			}]
		})

		var win = new Ext.Window({
			title : "上传图片",
			width : 300,
			height : 150,
			modal : true,
			border : false,
			iconCls : "../ext/picture.png",
			layout : "fit",
			items : imgform
		});
		win.show();
	},
	createToolbar : function(editor) {
		HTMLEditor.superclass.createToolbar.call(this, editor);
		this.tb.insertButton(16, {
			cls : "x-btn-icon",
			icon : "../ext/picture.png",
			handler : this.addImage,
			scope : this
		});
	}
});
Ext.reg('StarHtmleditor', HTMLEditor);
