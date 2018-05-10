
// Line
function createConfig() {
    return {
        type: 'line',
        data: {
            labels: ["1s","10s","30s","60s","90s","120s","150s"],
            datasets: [{
                label: "access times",
                borderColor: window.chartColors.red,
                backgroundColor: window.chartColors.red,
                data: [5,44,25,54,68,27,40],
                fill: false,
            }]
        },
        options: {
            responsive: true,
            title:{
                display: true,
                text: 'API access statistics'
            },
            tooltips: {
                position: 'nearest',
                mode: 'index',
                intersect: false,
                yPadding: 10,
                xPadding: 10,
                caretSize: 5,
                backgroundColor: 'rgba(67,208,236,1)',
                titleFontColor: window.chartColors.black,
                bodyFontColor: window.chartColors.black,
                borderColor: 'rgba(0,0,0,1)',
                borderWidth: 2
            },
        }
    };
}

window.onload = function() {
    var container = document.querySelector('.container');
    var div = document.createElement('div');
    div.classList.add('chart-container');

    var canvas = document.createElement('canvas');
    div.appendChild(canvas);
    container.appendChild(div);

    var ctx = canvas.getContext('2d');
    var config = createConfig();
    new Chart(ctx, config);
    console.log(config);
};



//Grouped bar chart

new Chart(document.getElementById("bar-chart-grouped"), {
    type: 'bar',
    data: {
        labels: ["1 hours", "6 hours", "12 hours", "1 days","2 days","5 days","1 week"],
        datasets: [
            {
                label: "Memory",
                backgroundColor: "#0088ff",
                data: [273,221,783,2278,698,245,1158]
            }, {
                label: "CPU",
                backgroundColor: "#ff0000",
                data: [408,847,675,1734,939,664,832]
            }

        ]
    },
    options: {
        title: {
            display: true,
            text: 'CPU and memory usage'
        },
    }
});


