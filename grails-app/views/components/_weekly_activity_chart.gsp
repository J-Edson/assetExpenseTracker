<html>
  <head>
    <script type="text/javascript">
      let weeklyActivity = document.getElementById('weeklyActivity').value
      let arr = JSON.parse(weeklyActivity);
      arr.unshift(['', 'Income', 'Expense']);
      console.log(arr)
      google.charts.load('current', {'packages':['bar']});
      google.charts.setOnLoadCallback(drawWeeklyChart);

      function drawWeeklyChart() {
        var data = google.visualization.arrayToDataTable(arr);
        var options = {
            chart: {
                title: 'Weekly Activity'
            },
            titleTextStyle: {
                color: '#343C6A', 
                fontSize: 50,
                fontName: 'Dongle'
            },
            series: {
                0: { color: '#26E1D3' },
                1: { color: '#F02C07' }
            },
            bar: {groupWidth: "30%"},
            legend: { 
                textStyle: { 
                    fontSize: 23, 
                    fontName: 'Dongle'
                }
            },
            hAxis: {
                textStyle: {
                    fontSize: 20,
                    fontName: 'Dongle'
                }
            }
            
        };

        var chart = new google.charts.Bar(document.getElementById('activity_chart'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
      }
    </script>
  </head>
  <body>
    <div id="activity_chart" style="width: 1000px; height: 325px;"></div>
  </body>
</html>