<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*,cn.edu.zucc.g4.bean.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">  
    <title>Visual Admin Dashboard - Maps</title>
    <meta name="description" content="">
    <meta name="author" content="templatemo">

    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/templatemo-style.css" rel="stylesheet">
    
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
      <script>var jq202 = jQuery.noConflict(true); </script>
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
          <h3 class="margin-bottom-10" style="text-align: center;">考试安排结果</h3>
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
                  btn.href = "selectdate?pagename=text-manager-finally.jsp&date1="+searchdate1+"&date2="+searchdate2;
              }
              else if(ipt=="name"){
              	claname = $("#claname").val();
                  btn.href = "selectname?pagename=text-manager-finally.jsp&claname="+claname;
              }
              else if(ipt=="ID"){
              	claid=$("#claid").val();
                  btn.href = "selectid?pagename=text-manager-finally.jsp&claid="+claid;
              }
      	}
      </script>
          <div class="templatemo-content-widget white-bg">
            <div id="search" style="height: 35px;width: 70%;display: inline-block">
              <input id="claname" type="text" class="form-control"  style="height: 35px;" placeholder="请输入课程名称">
            </div>
            <select id="selecttype" class="form-control" style="width: 20%;display: inline-block;" onchange="changedate(this.value)">
                <option value="name"  selected="true">按课程名称查找</option>
                <option value="ID">按课程ID查找</option>
                <option value="date">按日期查找</option>
              </select>
             <a id="search_btn" href=""><input type="submit" class="templatemo-blue-button" value="查找" onclick="searchbtn()"></a>
          </div>
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
                    <td>监考老师1</td>
                    <td>监考老师2</td>
                  </tr>
                  </thead>
                  <tbody id="tablevalue">

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
      
    // end function drawMaps

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
           
          });  
        }
     

      });
    </script>
    <script type="text/javascript">
        //数据
         var testdata=[];
         
       <%

       ArrayList<TestCheckBean> objlist= (ArrayList<TestCheckBean>) request.getAttribute("examtestlist");
       SimpleDateFormat time=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
       if(objlist==null||objlist.size()==0){
    	   objlist = (ArrayList<TestCheckBean>) session.getAttribute("finallylist");
    	   
      	   
       }

      		 for(int i=0;i<objlist.size();i++) {
		%>    			 
      		 		var tmp = {time:<%="'"+objlist.get(i).getCheckTime()+"'"%>, 
      		 				id:<%="'"+objlist.get(i).getCourseId()+"'"%>, 
      		 				name:<%="'"+objlist.get(i).getCourseName()+"'"%>,
      		 				place:<%="'"+objlist.get(i).getCheckPlace()+"'"%>,
      		 				invigilator1:<%="'"+objlist.get(i).getInvigilator1()+"'"%>,
      		 				invigilator2:<%="'"+objlist.get(i).getInvigilator2()+"'"%>};
      		 		testdata.push(tmp);
      		 		
		<%
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
                      c_tr.innerHTML='<td>'+this[i].time+'</td>'+
                          '<td>'+this[i].id+'</td>'+
                          '<td>'+this[i].name+'</td>'+
                          '<td>'+this[i].place+'</td>'+
                          '<td>'+this[i].invigilator1+'</td>'+
                          '<td>'+this[i].invigilator2+'</td>'+
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
        		
        		function(){loadmodify()
        			}	
        		
        	 ) 
  			function loadmodify(){
			  $(".tbl").each(function(){  
					
			      var op = 0;
			      var modifyuser=$(this).children().eq(6).children();
			      var courseId=modifyuser.parent().parent().children("td").get(1).innerHTML;
			      var time=modifyuser.parent().parent().children("td").get(0).innerHTML;
			      var place=modifyuser.parent().parent().children("td").get(3).innerHTML;
			      var teacher1=modifyuser.parent().parent().children("td").get(4).innerHTML; 
			      var teacher2=modifyuser.parent().parent().children("td").get(5).innerHTML; 
			      modifyuser.bind("click",function(){ 
			    	  
			    	  var tr=modifyuser.parent().parent();	    	
			    	  if(op == 0) {
		                    op = 1;
		                  
		                    $.ajax({
						         url: "modifyfinallytime/"+courseId+"/"+time,					
						         contentType: "application/json;charset=utf-8",	
						         dataType:"json",
						         type: "post",			
						         success:function(data){			
						        	 var text = "<select class=\"form-control\" onchange=\"changeTime($(this))\">";
						        	 console.log(data.length)
						        	 for(var i =0 ; i<data.length;i++) {
						        		 console.log(data[i])
						        		 if(data[i] == time) 
						        			 text = text + "<option selected = \"selected\" >"+data[i]+"</option>";
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
		                    
		                    $.ajax({
						         url: "modifyfinallyclass/"+time+"/"+place,					
						         contentType: "application/json;charset=utf-8",	
						         dataType:"json",
						         type: "post",			
						         success:function(data){			
						        	 var text = "<select class=\"form-control\">";
						        	 for(var i =0 ; i<data.length;i++) {
						        		 if(data[i] == place) 
						        			 text = text + "<option selected = \"selected\">"+data[i]+"</option>";
						        		 else 
						        			 text = text + "<option>"+data[i]+"</option>";
						        	 }
						        	 
						        
						        	 tr.children("td").get(3).innerHTML = text+"</select>";
						         },
						
						         error:function(XMLHttpRequest, textStatus, errorThrown){ 
						
						        	 console.log(XMLHttpRequest.status);
				                       console.log(XMLHttpRequest.readyState);
				                       console.log(textStatus);
							
							        }
				      			});
		                    
		                    $.ajax({
					         url: "modifyfinallyteacher/"+time+"/"+teacher1+"/"+teacher2,					
					         contentType: "application/json;charset=utf-8",	
					         dataType:"json",
					         type: "post",			
					         success:function(data){			
					        	 var text = "<select class=\"form-control\"\">";
					        	 for(var i =0 ; i<data.length;i++) {
					        		 if(data[i] == teacher1) 
					        			 text = text + "<option selected = \"selected\">"+data[i]+"</option>";
					        		 else 
					        			 text = text + "<option>"+data[i]+"</option>";
					        	 }
					        	 
					        	 var text1 = "<select class=\"form-control\">";
					        	 for(var i =0 ; i<data.length;i++) {
					        		 if(data[i] == teacher2) 
					        			 text1 = text1 + "<option selected = \"selected\">"+data[i]+"</option>";
					        		 else 
					        			 text1 = text1 + "<option>"+data[i]+"</option>";
					        	 }
					        	 tr.children("td").get(4).innerHTML = text+"</select>";
					        	 tr.children("td").get(5).innerHTML = text1+"</select>";
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
		                    data={
		                    		
		                    		checkPlace:tr.children("td").get(3).firstChild.value,
		                    		invigilator1:tr.children("td").get(4).firstChild.value,
		                    		invigilator2:tr.children("td").get(5).firstChild.value
		                    }
		                    $.ajax({
						         url: "modifyfinallyresult/"+courseId+"/"+place+"/"+tr.children("td").get(0).firstChild.value			,	
						         contentType: "application/json;charset=utf-8",	
						         data: JSON.stringify(data),	
						         dataType:"json",
						         type: "post",			
						         success:function(data){			
						        	location.reload(true);
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
        function changeTime(tr){
        	tr = tr.parent().parent();
        	time=tr.children("td").get(0).firstChild.value;
        	console.log(time);
        	var place="请选择考场";
        	var teacher="请选择监考教师";
        	alert(12);
        	 $.ajax({
		         url: "modifyfinallyclass/"+time+"/"+place,					
		         contentType: "application/json;charset=utf-8",	
		         dataType:"json",
		         type: "post",			
		         success:function(data){			
		        	 var text = "<select class=\"form-control\">";
		        	 for(var i =0 ; i<data.length;i++) {
		        			 text = text + "<option>"+data[i]+"</option>";
		        	 }
		        	 
		        	 tr.children("td").get(3).innerHTML = text+"</select>";
		         },
		
		         error:function(XMLHttpRequest, textStatus, errorThrown){ 
		
		        	 console.log(XMLHttpRequest.status);
                       console.log(XMLHttpRequest.readyState);
                       console.log(textStatus);
		
		        }
  			});
        	 $.ajax({
		         url: "modifyfinallyteacher/"+time+"/"+teacher,					
		         contentType: "application/json;charset=utf-8",	
		         dataType:"json",
		         type: "post",			
		         success:function(data){			
		        	 var text = "<select class=\"form-control\">";
		        	 for(var i =0 ; i<data.length;i++) {		
		        			 text = text + "<option>"+data[i]+"</option>";
		        	 }		        	 		        	
		        	 tr.children("td").get(4).innerHTML = text+"</select>";
		        	 tr.children("td").get(5).innerHTML = text+"</select>";
		         },
		
		         error:function(XMLHttpRequest, textStatus, errorThrown){ 
		
		        	 console.log(XMLHttpRequest.status);
                       console.log(XMLHttpRequest.readyState);
                       console.log(textStatus);
		
		        }
  			});
        }
    </script>
  </body>
</html>