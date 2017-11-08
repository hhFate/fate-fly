function createEditor(dom) {
	KindEditor.ready(function(K) {
		var editor = K.create(dom, {
			uploadJson : '/file/uploadImg',
			fileManagerJson : 'file/filelist',
			allowFileManager : true,
			width : '100%',
			htmlTags : {
				font : [ 'color', 'size', 'face', '.background-color' ],
				span : [ '.color', '.background-color', '.font-size',
						'.font-family', '.background', '.font-weight',
						'.font-style', '.text-decoration', '.vertical-align',
						'.line-height' ],
				div : [ 'align', '.border', '.margin', '.padding',
						'.text-align', '.color', '.background-color',
						'.font-size', '.font-family', '.font-weight',
						'.background', '.font-style', '.text-decoration',
						'.vertical-align', '.margin-left', 'id', 'class',
						'data-date', 'data-date-format', 'style' ],
				table : [ 'border', 'cellspacing', 'cellpadding', 'width',
						'height', 'align', 'bordercolor', '.padding',
						'.margin', '.border', 'bgcolor', '.text-align',
						'.color', '.background-color', '.font-size',
						'.font-family', '.font-weight', '.font-style',
						'.text-decoration', '.background', '.width', '.height',
						'.border-collapse' ],
				'td,th' : [ 'align', 'valign', 'width', 'height', 'colspan',
						'rowspan', 'bgcolor', '.text-align', '.color',
						'.background-color', '.font-size', '.font-family',
						'.font-weight', '.font-style', '.text-decoration',
						'.vertical-align', '.background', '.border' ],
				a : [ 'href', 'target', 'name', 'id', 'data-*', 'ref' ],
				embed : [ 'src', 'width', 'height', 'type', 'loop',
						'autostart', 'quality', '.width', '.height', 'align',
						'allowscriptaccess' ],
				img : [ 'src', 'width', 'height', 'border', 'alt', 'title',
						'align', '.width', '.height', '.border' ],
				'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [ 'align',
						'.text-align', '.color', '.background-color',
						'.font-size', '.font-family', '.background',
						'.font-weight', '.font-style', '.text-decoration',
						'.vertical-align', '.text-indent', '.margin-left' ],
				'pre,span' : [ 'class' ],
				i : [ 'class' ],
				property : [ 'name', 'value', 'ref' ],
				bean : [ 'id', 'class', 'p:connection-factory-ref', '/' ],
				hr : [ 'class', '.page-break-after' ],
				'script,iframe' : [ 'src', 'style' ],
				'br,tbody,tr,strong,b,sub,sup,em,u,strike,s,del,code' : [],
				'dt,dd' : [ 'style' ],
				'audio,video' : [ 'src', 'preload', 'loop', 'controls',
						'autoplay', 'height', 'width' ],
				'button,input' : [ 'class', 'id', 'size', 'type', 'value',
						'placeholder', 'onclick' ],
				link : [ 'rel', 'href' ]
			},
			resizeType : 1,
			urlType : "domain",
			afterBlur : function(e) {
				$(dom).val(editor.html());
			},
			afterChange : function() {
				$('.word_count1').html(this.count()); // 字数统计包含HTML代码
			}

		});
		html = editor.html();

		// 同步数据后可以直接取得textarea的value
		editor.sync();
		html = $(dom).val(); // jQuery

	});

}