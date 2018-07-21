<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*,cn.edu.zucc.g4.bean.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Visual Admin Dashboard - Maps</title>
<meta name="description" content="">
<meta name="author" content="templatemo">

<link
	href='http://fonts.useso.com/css?family=Open+Sans:400,300,400italic,700'
	rel='stylesheet' type='text/css'>
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="jqvmap/jqvmap.css" media="screen" rel="stylesheet"
	type="text/css" />
<link href="css/templatemo-style.css" rel="stylesheet">



<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
      .step {
        display: inline-block;
        width: 100px;
        height: 35px;
        border-radius: 5px;
        border: 0px;
        margin: 0px 10px 0px 10px;
        color: white;
        text-align: center;
      }
      #lststep {
        background-color: red;
      }
      #nxtstep {
        background-color: blue;
        float: right;
      }
      #lststep:hover {
        background-color: darkred;
      }
      #nxtstep:hover {
        background-color: darkblue;
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
                <li><a href="#" >第一步 考试时间安排</a></li>
                <li><a href="text-manager2.jsp" >第二步 考试地点安排  </a></li>
                <li><a href="text-manager3.jsp" >第三步 监考老师安排</a></li>
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
            <h3 class="margin-bottom-10" style="text-align: center;">考试时间安排</h3>
          </div>
          <script>
          function changedate(value) {
              var ipt = document.getElementById("search");
             
              if(value == "date") {
                  ipt.innerHTML = "<input id=\"date1\" type=\"date\" class=\"form-control\"  style=\"height: 35px;width: 45%;display: inline-block\">" +
                      "&nbsp&nbsp到&nbsp&nbsp<input id=\"date2\" type=\"date\" class=\"form-control\"  style=\"height: 35px;width: 45%;display: inline-block\">";
              }
              else if(value == "name") {
                  ipt.innerHTML = "<input id=\"claname\" type=\"text\" class=\"form-control\"  style=\"height: 35px;\" placeholder=\"请输入课程名称\">";
              }
              else if(value == "ID") {
                  ipt.innerHTML = "<input id=\"claid\" type=\"text\" class=\"form-control\"  style=\"height: 35px;\" placeholder=\"请输入课程ID\">";
              }
          }
          </script>
            <script >
        	function searchbtn(){
        		var ipt = document.getElementById("selecttype").value;
                var btn = document.getElementById('search_btn');
                var searchdate1;//1号日期输入框
                var searchdate2;//2号日期输入框
                var sname;//课程名称输入框
                var sid;//课程ID输入框
                if(ipt=="date"){
                	searchdate1 = $("#date1").val();
                    searchdate2 = $("#date2").val();
                    alert(searchdate1);
                    alert(searchdate2);
                    btn.href = "selectdate?pagename=text-manager1-2.jsp&date1="+searchdate1+"&date2="+searchdate2;
                }
                else if(ipt=="name"){
                	claname = $("#claname").val();
                    btn.href = "selectname?pagename=text-manager1-2.jsp&claname="+claname;
                }
                else if(ipt=="ID"){
                	claid=$("#claid").val();
                    btn.href = "selectid?pagename=text-manager1-2.jsp&claid="+claid;
                }
        	}
        </script>
            <div class="templatemo-content-widget white-bg">
              <div id="search" style="height: 35px;width: 70%;display: inline-block">
                <input id="claname" type="text" class="form-control"  style="height: 35px;" placeholder="请输入课程名称">
              </div>
              <select id="selecttype" class="form-control" style="width: 20%;display: inline-block;" onchange="changedate(this.value)">
                <option value="name" selected="true">按课程名称查找</option>
                <option value="ID">按课程ID查找</option>
                <option value="date">按日期查找</option>
              </select>
             <a id="search_btn" href=""><input type="submit" class="templatemo-blue-button" value="查找" onclick="searchbtn()"></a>
            </div>
            <div class="col-1">
            <div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
              <div class="table-responsive">
                <table class="table table-striped table-bordered">
                  <thead>
                  <tr>
                    <td>考试时间</td>
                    <td>课程编号</td>
                    <td>课程名称</td>
                    <td>修改</td>
                  </tr>
                  </thead>
                 
	                  <tbody id="tablevalue">
	 <%-- 
	                   <%
							List objlist=(List) request.getAttribute("terlist");
							  if(objlist!=null){
						          for(int i=0;i<objlist.size();i++){
						        	 ViewCheckBean list=(ViewCheckBean) objlist.get(i);
						%>  
							<tr>
								<td><%=list.getCheck_time()%></td>
								<td><%=list.getCourse_id()%></td>
								<td><%=list.getCourse_name()%></td>
								<td><button id="modify">修改</button></td>
							</tr>
							<% 
										}
							    }
							%> --%>
	                  </tbody> 
                  
                </table>
              </div>
            </div>
          </div>
          <div id="search_msg" class="text-right" style="margin-right: 10px;">
          共有<span id="allnum">27</span>条考试安排记录；
          <button class="fy_btn" data="firstPage">首页</button>|<button class="fy_btn" data="prev">上一页</button>
          第<span id="nowPage">1</span>/<span id="allPage">3</span>页
          <button class="fy_btn" data="next">下一页</button>|<button data="lastPage" class="fy_btn">尾页</button>
          转到<input type="number" id="pagenumber" min="1">页<button data="toPage" class="fy_btn">GO</button>
        </div>
          
            <a href="backManager1" class="step" id="lststep" style="line-height: 33px">上一步</a>

            <!--<button class="step" id="lststep">上一步</button>-->
            <a href="toManager2" class="step" id="nxtstep" style="line-height: 33px">下一步</a>
           <!--  <input type="submit" class="step" id="nxtstep" value="下一步"> -->
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

    </script>
    <script type="text/javascript">
        //数据
       
         var testdata=[];
        
       <%
       
       //ArrayList<ArrayList<TestCheckBean>> objlist = (ArrayList<ArrayList<TestCheckBean>>) session.getAttribute("examlist1");
       ArrayList<ArrayList<TestCheckBean>> objlist= (ArrayList<ArrayList<TestCheckBean>>) request.getAttribute("examtestlist");
       if(objlist==null||objlist.size()==0){
    	   objlist = (ArrayList<ArrayList<TestCheckBean>>) session.getAttribute("examlist1");
       }
      		 for(int i=0;i<objlist.size();i++) {
      			 for(int j=0;j<objlist.get(i).size();j++) {
		%>    			 
      		 		var tmp = {time:<%="'"+objlist.get(i).get(j).getCheckTime()+"'"%>, 
      		 				id:<%="'"+objlist.get(i).get(j).getCourseId()+"'"%>, 
      		 				name:<%="'"+objlist.get(i).get(j).getCourseName()+"'"%>};
      		 		testdata.push(tmp);
      		 		
		<%
      			 }
      		 }
      	%>
     
        var f0 = new loadtable(testdata);
        function loadtable(testdata){
          //操作节点
          var search_msg=document.getElementById('search_msg'),
//              keyword=document.getElementById('keyword'),
              table_list=document.getElementById('tablevalue'),
              Allnum=document.getElementById('allnum'),
              Allpage=document.getElementById('allPage'),
              Nowpage=document.getElementById('nowPage'),
              pagenumber=document.getElementById('pagenumber');

          //初始设置;
          var setnum=10,//设置每页数目
              nowpage,//当前页
              allpage,
              tempdata=[],//存放
              startnum=0,//数据节点
              Condition = function(){
                  var temp=[],allnum=0;
                  for(var i in this){
                      if(this[i]['time'].match(arguments[0]) || this[i]['id'].match(arguments[0]) || this[i]['name'].match(arguments[0])){
                          temp.push(this[i]);
                          allnum+=1;
                      }
                  }
                  return  [temp,allnum];
              },
              Makelist = function(){

                  table_list.innerHTML='';
                  Nowpage.innerText=nowpage;
                  if(nowpage==0)return false;

                  var templist=document.createDocumentFragment();
                  for(var i = startnum;i<Number(startnum+setnum);i++){
                      if(i>=this.length){
                          table_list.appendChild(templist);
                          return false;
                      }

                      var c_tr = document.createElement('tr');
                      c_tr.className="tbl";
                      c_tr.id = 'row'+this[i].rowid;
                      c_tr.innerHTML='<td>'+this[i].time+'</td>'+
                          '<td>'+this[i].id+'</td>'+
                          '<td>'+this[i].name+'</td>'+
                          '<td><button id="modify">修改</button></td>';
                      templist.appendChild(c_tr);

                  }
                  table_list.appendChild(templist);


              },
              Searchkey=function(Co,Ma,obj,key){
                  var resdata = Co.apply(obj,[key]);
                  Allnum.innerText=resdata[1];
                  Allpage.innerText=Math.ceil(resdata[1]/setnum);
                  startnum=0;
                  tempdata = resdata[0];
                  allpage=Math.ceil(resdata[1]/setnum);
                  isdata=(resdata[0]!='')?1:0;
                  nowpage= isdata;
                  pagenumber.setAttribute('max',allpage);
                  Ma.apply(resdata[0]);
              },
              Optpage = function(){
                  switch(arguments[0]){
                      case 'prev':
                          if(nowpage==1)return false;
                          nowpage-=1;
                          startnum-=setnum;
                          break;
                      case 'next':
                          if(nowpage==allpage)return false;
                          nowpage+=1;
                          startnum+=setnum;
                          break;
                      case 'firstPage':
                          nowpage=1;
                          startnum=0;

                          break;

                      case 'lastPage':
                          nowpage=allpage;
                          startnum=(nowpage-1)*setnum;

                          break;

                      case 'toPage':
                          console.log(pagenumber.value)
                          nowpage=Number(pagenumber.value);
                          if(nowpage>allpage || nowpage<1)return false;
                          startnum=(nowpage-1)*setnum;
                          break;
                      default:
                          return false;
                          break;
                  }
                  Makelist.apply(tempdata);
              }

            Searchkey(Condition,Makelist,testdata,'');
//点击翻页
            var btn = search_msg.getElementsByTagName("button");
            for(var i=0;i<btn.length;i++){
                btn[i].onclick=(function(index){
                    return function(){
                        Optpage(btn[index].getAttribute("data"));
                        loadmodify();
                    }
                })(i);
            }
        }
        
    	 $(document).ready(
    		function(){loadmodify()}		 
    	 ) 
    		function loadmodify(){
			  $(".tbl").each(function(){  
	
			      var tmp=$(this).children().eq(5);  
			      var btn=tmp.children();
			      var op = 0;
			      
			      var modifyuser=$(this).children().eq(3).children();
			      modifyuser.bind("click",function(){ 
			    	  var tr=modifyuser.parent().parent();
			    	  var courseId=modifyuser.parent().parent().children("td").get(1).innerHTML; 
			    	  var time=modifyuser.parent().parent().children("td").get(0).innerHTML; 
			    
			    	  if(op == 0) {
		                    op = 1;
		                    $.ajax({
						         url: "modifytime/"+courseId,					
						         contentType: "application/json;charset=utf-8",	
						         dataType:"json",
						         type: "post",			
						         success:function(data){			
						        	 var text = "<select class=\"form-control\">";
						        	 for(var i =0 ; i<data.length;i++) {
						        		 if(data[i] == time) 
						        			 text = text + "<option selected = \"selected\">"+data[i]+"</option>";
						        		 else 
						        			 text = text + "<option>"+data[i]+"</option>";
						        	 }
						        	 tr.children("td").get(0).innerHTML = text+"</select>";
						         },
						
						         error:function(XMLHttpRequest, textStatus, errorThrown){ 
						
						        	 console.log(XMLHttpRequest.status);
				                       console.log(XMLHttpRequest.readyState);
				                       console.log(textStatus);
						
						        }
			      			});
		                }
		                else {
		                    op = 0;
		                    
		                    $.ajax({
						         url: "modifytimeresult/"+courseId+"/"+tr.children("td").get(0).firstChild.value,					
						         contentType: "application/json;charset=utf-8",	
						         dataType:"json",
						         type: "post",			
						         success:function(){			
						        	 tr.children("td").get(0).innerHTML =  tr.children("td").get(0).firstChild.value;
						         },
						
						         error:function(XMLHttpRequest, textStatus, errorThrown){ 
						
						        	 console.log(XMLHttpRequest.status);
				                       console.log(XMLHttpRequest.readyState);
				                       console.log(textStatus);
						
						        }
			      			});	                    
		                }    	  			   
				       
			      })
			  })
    	 }
    </script>
  </body>
</html>