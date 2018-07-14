<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*,cn.edu.zucc.g4.bean.*"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">  
    <title>考试时间安排</title>
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
    <![endif]-->

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
            <li><a href="selectionjoin" class="active"><i class="fa fa-bar-chart fa-fw"></i>考试安排</a></li>
            <li><a href="outlogin"><i class="fa fa-eject fa-fw"></i>退出登录</a></li>
          </ul>
        </nav>
      </div>
      <div class="templatemo-content col-1 light-gray-bg">
        <div class="templatemo-top-nav-container">
          <div class="row">
            <nav class="templatemo-top-nav col-lg-12 col-md-12">
              <ul class="text-uppercase">
                <li><a href="#" class="active">第一步 考试时间安排</a></li>
                <li><a href="text-manager2.jsp" class="active">第二步 考试地点安排  </a></li>
                <li><a href="text-manager3.jsp" class="active">第三步 监考老师安排</a></li>
              </ul>
            </nav> 
          </div>
        </div>
        <div class="templatemo-content-container">
          <div class="templatemo-content-widget white-bg">

            <%String year= (String) session.getAttribute("year");
            String term= (String) session.getAttribute("term");
          %>
            <h2 class="margin-bottom-10" style="text-align: center;"><%=year%>学年 第<%=term%>学期</h2>

          </div>
          <form action="testtimearrange" class="templatemo-login-form">
            <div class="templatemo-content-widget white-bg">
              <h2 class="margin-bottom-10" style="font-weight: bolder">考试周安排</h2>
              <h2 style="margin-left: 20%;display: inline-block">开始日期</h2>
              <input type="date" style="width: 200px;">
              <h2 style="margin-left: 10%;display: inline-block">结束日期</h2>
              <input type="date" style="width: 200px;">
              <br>
              <div style="text-align: right">
                <input type="submit" class="templatemo-blue-button" value="自动生成安排表">
              </div>
            </div>
          </form>
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
    <script src="jqvmap/maps/continents/jquery.vmap.north-america.js" type="text/javascript"></script>
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