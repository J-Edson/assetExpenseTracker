  <html>
  <head>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['line']});
      google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

      var data = new google.visualization.DataTable();
      data.addColumn('string', '');
      data.addColumn('number', 'Balance');

      data.addRows([
        ['Jan',  37.8],
        ['Feb',  30.9],
        ['Mar',  25.4],
        ['Apr',  11.7],
        ['May',  11.9],
        ['Jun',   8.8],
        ['Jul',   7.6],
        ['Aug',  12.3],
        ['Sep',  16.9],
        ['Oct', 12.8],
        ['Nov',  5.3],
        ['Dec',  6.6]
      ]);

      var options = {
        chart: {
          title: 'Balance History'
        },
        titleTextStyle: {
            color: '#343C6A', 
            fontSize: 50,
            fontName: 'Dongle'
        },
        series: {
            0: { 
              color: '#26E1D3'
            }
        },
        legend: { 
          position: 'none'
        },
        hAxis: {
            textStyle: {
                fontSize: 20,
                fontName: 'Dongle'
            }
        }
      };

      var chart = new google.charts.Line(document.getElementById('balance_chart'));

      chart.draw(data, google.charts.Line.convertOptions(options));
    }
  </script>
  </head>
  <body>
    <div id="balance_chart" style="width: 1000px; height: 325px"></div>
  </body>
</html>