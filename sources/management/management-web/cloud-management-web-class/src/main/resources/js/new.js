
$(document).ready(function(){
    $(".radio-click .circle").each(function(i,o){
        $(this).click(function() {
            if (!$(this).hasClass("border-six")) {
                $(".radio-click .circle").removeClass("border-six").addClass("transparent");
                $(this).removeClass("transparent").addClass("border-six");
            }
        })
    })


    //  第一种

    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        title: {
            text: 'Chart Description'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {

                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {

                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {

                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410]
            },
            {

                type: 'line',
                stack: '总量',
                data: [320, 332, 301, 334, 390, 330, 320]
            },
            {

                type: 'line',
                stack: '总量',
                data: [820, 932, 901, 934, 1290, 1330, 1320]
            }

        ]
    };

    myChart.setOption(option);


//第二种


    var option1 = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };



//第三种

    var option2 = {
        title : {
            text: '某站点用户访问来源',
            subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1548, name:'搜索引擎'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

//第四种

// 模拟数据
    var data1 = [];
    var data2 = [];
    var data3 = [];

    var random = function (max) {
        return (Math.random() * max).toFixed(3);
    };

    for (var i = 0; i < 500; i++) {
        data1.push([random(15), random(10), random(1)]);
        data2.push([random(10), random(10), random(1)]);
        data3.push([random(15), random(10), random(1)]);
    }

    var option3 = {
        animation: false,
        legend: {
            data: ['scatter', 'scatter2', 'scatter3']
        },
        tooltip: {
        },
        xAxis: {
            type: 'value',
            min: 'dataMin',
            max: 'dataMax',
            splitLine: {
                show: true
            }
        },
        yAxis: {
            type: 'value',
            min: 'dataMin',
            max: 'dataMax',
            splitLine: {
                show: true
            }
        },
        dataZoom: [
            {
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                start: 1,
                end: 35
            },
            {
                type: 'slider',
                show: true,
                yAxisIndex: [0],
                left: '93%',
                start: 29,
                end: 36
            },
            {
                type: 'inside',
                xAxisIndex: [0],
                start: 1,
                end: 35
            },
            {
                type: 'inside',
                yAxisIndex: [0],
                start: 29,
                end: 36
            }
        ],
        series: [
            {
                name: 'scatter',
                type: 'scatter',
                itemStyle: {
                    normal: {
                        opacity: 0.8
                    }
                },
                symbolSize: function (val) {
                    return val[2] * 40;
                },
                data: data1
            },
            {
                name: 'scatter2',
                type: 'scatter',
                itemStyle: {
                    normal: {
                        opacity: 0.8
                    }
                },
                symbolSize: function (val) {
                    return val[2] * 40;
                },
                data: data2
            },
            {
                name: 'scatter3',
                type: 'scatter',
                itemStyle: {
                    normal: {
                        opacity: 0.8
                    }
                },
                symbolSize: function (val) {
                    return val[2] * 40;
                },
                data: data3
            }
        ]
    }

    $(".chart-radio img").each(function(i,o){
        $(this).click(function(){
            myChart.clear();
            if(i==0){
                myChart.setOption(option);
            } else if(i == 1){
                myChart.setOption(option1);
            } else if(i == 2){
                myChart.setOption(option2);
            } else {
                myChart.setOption(option3);
            }
        })
    })



    $(".signprol .portal").each(function(i,o){
        $(this).click(function(){
            $(".font-click").html($(this).children().html());
        })
    })

})
