<html>
  <head>
    <script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Expense');
        data.addColumn('number', 'Amount');
        data.addRows([
            ['Bill', 33],
            ['Food', 26],
            ['Transpo', 22],
            ['Entertainment', 10],
            ['Investment', 9] 
        ]);

        var options = {
            title: 'Expense Statistics',
            titleTextStyle: {
                color: '#343C6A', 
                fontSize: 50,
                fontName: 'Dongle',
                bold: false,
            },
            is3D: true,
            legend: { 
                position: 'top',
                textStyle: { 
                    fontSize: 23, 
                    fontName: 'Dongle'
                }
            },
            pieSliceTextStyle: {
                fontName: 'Dongle',
                fontSize: 25
            },
            slices: {
                0: { color: '#F0070E' },
                1: { color: '#F07F07' },
                2: { color: '#F05707' },
                3: { color: '#F02C07' }
            },
            sliceVisibilityThreshold: .099
        };

        var chart = new google.visualization.PieChart(document.getElementById('expense_piechart_3d'));
        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="expense_piechart_3d" style="width: 450px; height: 500px;"></div>
  </body>
</html>