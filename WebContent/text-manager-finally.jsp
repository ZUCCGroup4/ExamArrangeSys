<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">  
    <title>Visual Admin Dashboard - Maps</title>
    <meta name="description" content="">
    <meta name="author" content="templatemo">
    
    <link href='http://fonts.useso.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="jqvmap/jqvmap.css" media="screen" rel="stylesheet" type="text/css" /> 
    <link href="css/templatemo-style.css" rel="stylesheet">
    
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
      <script>var jq202 = jQuery.noConflict(true); </script>
    <![endif]-->
    <script>
      var op = 0;
      function changevalue() {
          var tr = document.getElementById("tablevalue").childNodes;
          if(op == 0) {
              op = 1;
              for(var i = 0; i < 2; i ++) {
                  if(tr[i].localName == "td")
                      tr[i].innerHTML = "<input type=\"text\" value=\"1111\">";
              }
              for(var i = 2; i < tr.length - 2; i ++) {
                  if(i >= 5 && i <= 6) continue;
                  if(tr[i].localName == "td")
                      tr[i].innerHTML = "<select class=\"form-control\"></select>";
              }
          }
          else {
              op = 0;
              for(var i = 0; i < tr.length - 2; i ++) {
                  if(tr[i].localName == "td")
                    tr[i].innerHTML = "1111";
              }
          }
      }
      (function($){
          function loadteacher() {
              $.ajax({
                  dataType:"json",    //数据类型
                  contentType: "application/x-www-form-urlencoded;charset=utf-8", //内容格式
                  url:"/getteacher",
                  type:"POST", //传输方式
                  success:function(data,textStatus){
                      alert("教师数据得到成功");
                  }
              });
          }
      })(jq172);
    </script>
    <style>
      #modify {
        border-radius: 5px;
        border: 0px;
        width: 80px;
        height: 30px;
        background-color: green;
        color: white;
      }
      #modify:hover {
        background-color: darkgreen;
      }
      td {
        width: 230px;
      }
    </style>
  </head>
  <body>
    <div class="templatemo-flex-row">
    <div class="templatemo-sidebar">
      <header class="templatemo-site-header">
        <h1>考试管理系统</h1>
      </header>
      <div class="mobile-menu-icon">
        <i class="fa fa-bars"></i>
      </div>
      <nav class="templatemo-left-nav">
        <ul>
          <li><a href="index"><i class="fa fa-home fa-fw"></i>日志查看</a></li>
          <li><a href="#" class="active"><i class="fa fa-bar-chart fa-fw"></i>考试安排</a></li>
          <li><a href="outlogin"><i class="fa fa-eject fa-fw"></i>退出登录</a></li>
        </ul>
      </nav>
    </div>
    <div class="templatemo-content col-1 light-gray-bg">
      <div class="templatemo-top-nav-container">
        <div class="row">
          <nav class="templatemo-top-nav col-lg-12 col-md-12">
            <ul class="text-uppercase">
            </ul>
          </nav>
        </div>
      </div>
      <div class="templatemo-content-container">
        <div class="templatemo-content-widget white-bg">
          <h2 class="margin-bottom-10" style="text-align: center;">2018-2019学年 第1学期</h2>
          <h3 class="margin-bottom-10" style="text-align: center;">考试安排结果</h3>
        </div>
        <script>
            function changedate(value) {
                var ipt = document.getElementById("search");
                if(value == "date") {
                    ipt.innerHTML = "<input type=\"date\" class=\"form-control\"  style=\"height: 35px;width: 45%;display: inline-block\">" +
                        "&nbsp&nbsp到&nbsp&nbsp<input type=\"date\" class=\"form-control\"  style=\"height: 35px;width: 45%;display: inline-block\">";
                }
                else if(value == "name") {
                    ipt.innerHTML = "<input type=\"text\" class=\"form-control\"  style=\"height: 35px;\" placeholder=\"请输入课程名称\">";
                }
                else if(value == "ID") {
                    ipt.innerHTML = "<input type=\"text\" class=\"form-control\"  style=\"height: 35px;\" placeholder=\"请输入课程ID\">";
                }
            }
        </script>
        <form action="search" class="templatemo-login-form">
          <div class="templatemo-content-widget white-bg">
            <div id="search" style="height: 35px;width: 70%;display: inline-block">
              <input type="text" class="form-control"  style="height: 35px;" placeholder="请输入课程名称">
            </div>
            <select class="form-control" style="width: 20%;display: inline-block;" onchange="changedate(this.value)">
              <option value="name">按课程名称查找</option>
              <option value="ID">按课程ID查找</option>
              <option value="date">按日期查找</option>
            </select>
            <input type="submit" class="templatemo-blue-button" value="查找">
          </div>
        </form>
        <!--<form action="text-manager4.html" class="templatemo-login-form">-->
          <div class="col-1">
            <div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
              <div class="table-responsive">
                <table class="table table-striped table-bordered">
                  <thead>
                  <tr>
                    <td>考试时间</td>
                    <td>课程编号</td>
                    <td>课程名称</td>
                    <td>考试地点</td>
                    <td>教师编号</td>
                    <td>修改</td>
                  </tr>
                  </thead>
                  <tbody>
                  <tr id="tablevalue">
                    <td>13</td>
                    <td>13</td>
                    <td>1</td>
                    <td>2</td>
                    <td>3</td>
                    <td><button id="modify" onclick="changevalue()">修改</button></td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        <!--</form>-->
        <footer class="text-right">
          <p>ZUCC JAVA方向短学期第四组--考试安排系统</p>
        </footer>
      </div>
    </div>
  </div>
    
    <!-- JS -->
    <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>      <!-- jQuery -->
    <script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script> <!--  jQuery Migrate Plugin -->
    <script type="text/javascript" src="js/templatemo-script.js"></script>      <!-- Templatemo Script -->
    <script type="text/javascript" src="jqvmap/jquery.vmap.js"></script>
    <script type="text/javascript" src="jqvmap/maps/jquery.vmap.world.js"></script>
    <script type="text/javascript" src="jqvmap/data/jquery.vmap.sampledata.js"></script>
    <script src="jqvmap/maps/continents/jquery.vmap.africa.js" type="text/javascript"></script>
    <script src="jqvmap/maps/continents/jquery.vmap.asia.js" type="text/javascript"></script>
    <script src="jqvmap/maps/continents/jquery.vmap.australia.js" type="text/javascript"></script>
    <script src="jqvmap/maps/continents/jquery.vmap.europe.js" type="text/javascript"></script>
    <script src="jqvmap/maps/continents/jquery.vmap.south-america.js" type="text/javascript"></script>
    <script src="jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>
    <script type="text/javascript">
      
      function drawMaps(){
        $('#vmap_world').vectorMap({
          map: 'world_en',
          backgroundColor: '#ffffff',
          color: '#333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_africa').vectorMap({
          map: 'africa_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        }); 
        $('#vmap_asia').vectorMap({
          map: 'asia_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_australia').vectorMap({
          map: 'australia_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_europe').vectorMap({
          map: 'europe_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_northamerica').vectorMap({
          map: 'north-america_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_southamerica').vectorMap({
          map: 'south-america_en',
          backgroundColor: '#ffffff',
          color: '#333333',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          values: sample_data,
          scaleColors: ['#C8EEFF', '#006491'],
          normalizeFunction: 'polynomial'
        });
        $('#vmap_usa').vectorMap({
          map: 'usa_en',
          enableZoom: true,
          showTooltip: true,
          selectedRegion: 'MO'
        });  
      } // end function drawMaps

      $(document).ready(function() {

        if($.browser.mozilla) {
          //refresh page on browser resize
          // http://www.sitepoint.com/jquery-refresh-page-browser-resize/
          $(window).bind('resize', function(e)
          {
            if (window.RT) clearTimeout(window.RT);
            window.RT = setTimeout(function()
            {
              this.location.reload(false); /* false to get page from cache */
            }, 200);
          });      
        } else {
          $(window).resize(function(){
            drawMaps();
          });  
        }
        
        drawMaps();

      });
    </script>
  </body>
</html>