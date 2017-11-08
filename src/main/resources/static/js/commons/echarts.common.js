function commonPie(id, pie) {
	// 第二个参数可以指定前面引入的主题
	var chart = echarts.init(document.getElementById(id), 'macarons');
	chart.setOption({
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b}: {c} ({d}%)"
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : true
				},
				saveAsImage : {
					show : true
				}
			},
			padding : 1
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : pie.legend
		},
		series : [ {
			name : pie.title,
			type : 'pie',
			radius : [ '50%', '70%' ],
			avoidLabelOverlap : false,
			label : {
				normal : {
					show : false,
					position : 'center'
				},
				emphasis : {
					show : true,
					textStyle : {
						fontSize : '30',
						fontWeight : 'bold'
					}
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : pie.series
		} ]

	});
}

function commonBar(id, bar) {
	// 第二个参数可以指定前面引入的主题
	var chart = echarts.init(document.getElementById(id), 'macarons');
	chart.setOption({
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : true
				},
				magicType : {
					show : true,
					type : [ 'bar', 'line' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			},
			padding : 1
		},
		legend : {
			data : bar.legend
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : bar.xAxis
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : bar.series
	});
}