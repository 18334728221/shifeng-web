/**
 * @main.js
 * @author yk
 * @date:2015-12-22
 * @modify:2015-12-22
 */
define(function(require, exports, module) {
	    require('../jquery-2.1.4.min');
		var media = require('./media');
		window.media = media;
		require('./tooltip/themes/2/tooltip.css');
		//require('./tooltip/themes/2/tooltip');
		require('./jwplayer/jwplayer');
		require('./jquery.fullscreen');
		jwplayer.key="xDp0ln8T1AyHlwzQ89sGZSaJAy5tEdN1RyZkSQ==";
	    var getPosition;
		var duration;
		var syncListener;
		var TimeFn = null;
		var timer;
		//设置定时器每秒同步一次，每秒取得播放时间一次
		//音量
		window.syncStart=function() {
			if(syncListener != null) return;
			syncListener = setInterval(syncBarTime, 100);
		};
        var time ={
	        duration : null,
	        //显示播放时长与正在播放时间所调用的时间换算方法
			prettyPrintSeconds: function(seconds, leadingMinutes, leadingHours){
				var h;
				var m;
				var s;
				seconds = Math.floor(isNaN(seconds) ? 0 : Math.max(0, seconds));
				h = Math.floor(seconds / 3600);
				m = Math.floor(seconds % 3600 / 60);
				s = seconds % 60;
				return ((h>0||leadingHours) ? (h + ":") : "")
				+ (((h>0||leadingMinutes) && m<10) ? "0" : "")
					+ m + ":" 
					+ (s<10 ? "0" : "") 
					+ s;
			},
			//时长与播放时间的方法
			setCurrentTimeStatus : function(v1,v2) {
				if(v1)	
				$('#time_a').html(this.prettyPrintSeconds(v1));
				timer = this.prettyPrintSeconds(v1);
				if(v2)	
				$('#time_b').html(this.prettyPrintSeconds(v2));
			},
			pianSetCurrentTimeStatus : function(t1,t2) {
				if(t1)	
				$('#p_time_a').html(this.prettyPrintSeconds(t1));
				if(t2)	
				$('#p_time_b').html(this.prettyPrintSeconds(t2));
			},
			convertTime : function(timeStr) {
				var arr = timeStr.split(":");
				if (arr.length == 2) {
					return 60 * parseInt(arr[0],'10') + parseInt(arr[1],'10');
				} else if (arr.length == 3) {
					return 3600 * parseInt(arr[0],'10') + 60 * parseInt(arr[1],'10')
							+ parseInt(arr[2],'10');
				} else {
					return null;
				}
			}	
	    }; 
		
		//语速
		function changePlaybackRate(rateChange) {
			if (jwplayer().getRenderingMode() == "html5") {
				var videoTag = document.querySelector('video');
				if (videoTag.playbackRate) {
					videoTag.playbackRate += rateChange;
				}
			}

			//Small hack to work around a Firefox bug    
			if(navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
				jwplayer().seek(jwplayer().getPosition());
			}
		};
		
		window.time = time;
		//缩放
//		function resizePlayer(){
//			if(jwplayer().getWidth() == 940) { 
//			  jwplayer().resize(600,338);
//			  $(".video-box").width(600).addClass("right");
//			} else {
//			  jwplayer().resize(940,529);
//			  $(".video-box").width(940).removeClass("right");
//			}
//	    };
		//初始化进度条
		 function progressLisStart(duration, position){
			 if($('#guage_btn').data('d')=='true'){
				return;
			}
			
			var a=jwplayer().getBuffer();
			var curSize = 10;
			var bw=$('#guage_btn').width() - curSize;
			var w=position/duration*bw;
			if (w > jwplayer().getWidth()){
				w = jwplayer().getWidth()
			}
			if (parseInt(duration) == parseInt(position)){
				if (parseInt(duration) == 0){
					w = 0;
				}else{
					w = bw;
				}
			}
			//var wa = a/duration*bw;
			$('#guage_btn .b').width(w);
			$('#guage_btn .a').css('left',w >bw?bw:w);
		};
		$('#guage_btn').data('d','false');
		syncBarTime = function(){
			var duration=media.getDuration();
			var position=media.getCurPos();
			media.currentTime=position;
			media.duration=duration;
			progressLisStart(duration, position);
			var g=$('#guage_btn');
			var d=g.data('d');
			if(!d||d=='false'){
				time.setCurrentTimeStatus(position,duration);
			}
		}
		//视频
	    $(function(){
		//音量
		function volumeChange(v){
			$('#v_icon').attr('s','N');
				if(v<20&&v>0){
					$('#v_icon').removeClass().addClass('v_icon3');
				}
				else if(v>60){
					$('#v_icon').removeClass().addClass('v_icon2');
				}
				else if(Math.ceil(v)==0){
					$('#v_icon').removeClass().addClass('v_icon4');
					$('#v_icon').attr('s','Y');
				}
				else{
					$('#v_icon').removeClass().addClass('v_icon');
				}
				$('#v_icon').attr('v',v);
				var w=$('#volume_a').width(),l=v/100*w;
				$('#va').css('left',l);
		        $('#vb').width(l);
				jwplayer().setVolume(v);
		};
		volumeChange(50);
		$(document).bind({
        	'mousemove':function(e){
        		e=e||event;
	       		var g=$('#guage_btn');
		        var d=g.data('d');
		        if(d=='true'){
	        		var x=e.clientX, w=g.width(),l=t=x-g.offset().left;
		        	if(l<0){l=0}else if(l>w-6){l=w-6}
		        	$('#guage_btn .a').css('left',l);
		        	$('#guage_btn .b').width(l);
		        	var d=media.getDuration();
		        	time.setCurrentTimeStatus((t>w?w:t)/w*d,d);
		        	e.stopPropagation();
		        	return false;
		        }
		        //音量
		        var v=$('#volume_a');
		        var vd=v.data('d');
		        if(vd=='true'){
		        	var x=e.clientX, w=v.width(),l=x-v.offset().left;
		        	if(l<0){l=0}else if(l>w){l=w}
		        	volumeChange(parseInt(l/w*100));
		        	e.stopPropagation();
		        	return false;
		        }
		        //起点
		        var start = $('#startTime');
		        var end = $('#endTime');
		        if(start.data("d")=='true'){
		        	var x=e.clientX;
		        	var left = $('#lefttd').offset().left;
		        	var max = end.offset().left;
		        	if(x>max){
		        		x=max;
		        	}
		        	var l=x-left;
		        	if(l<0){
		        	   l=0;
		        	   x=left;
		        	}
		        	//console.log(max + "---" + l);
		        	var twol=max -x;
		        	$('#showtd1').css('width',l);
		        	$('#showtd2').css('width',twol);
		        	$('#startTime').css('left',l);
		        	$('#startTime').css('z-index',9998);
		        	$('#endTime').css('z-index',9008);
		        	e.stopPropagation();
		        	return false;
		        }
		        //终点
		        if(end.data("d")=='true'){
		        	var x=e.clientX;
		        	var max = $('#righttd').offset().left - 8;
		        	var left = start.offset().left;
		        	//console.log(left);
		        	if(x < left){
		        		x = left;
		        	}
		        	if(x > max){
		        		x = max;
		        	}
		        	var twol = x - left;
		        	var l = x - $('#lefttd').offset().left;
		        	var threel = max - x;
		        	//console.log(x + "----" + twol + "----" + threel);
		        	$('#showtd2').css('width',twol);
		        	$('#showtd3').css('width',threel);
		        	$('#endTime').css('left',l);
		        	$('#startTime').css('z-index',9008);
		        	$('#endTime').css('z-index',9998);
		        	e.stopPropagation();
		        	return false;
		        }
	        },
	        'mousedown':function(e){
	       		 e=e||event;
	        	if($(e.target).attr('id')=='ga'){
	         		var g=$('#guage_btn');
		        	g.data('d','true');
		        	return false;
		        }else if($(e.target).attr('id')=='va'){
		        	  var v=$('#volume_a');
		        	  vd=v.data('d','true');
		        	  return false;
		        }else if($(e.target).attr('id')=='startTime'){
		        	  var v=$('#startTime');
		        	  vd=v.data('d','true');
		        	  return false;
		        }else if($(e.target).attr('id')=='endTime'){
		        	  var v=$('#endTime');
		        	  vd=v.data('d','true');
		        	  return false;
		        }
		    },
	        'mouseup':function(e){
	        	e=e||event;
	       		var g=$('#guage_btn'),d=g.data('d');
	       		if(d=='true'){
		        	g.data('d','false');
		        	var x=e.clientX, w=g.width(),l=x-g.offset().left;
		        	if(l<0){l=0}else if(l>w){l=w}
		        	var d=media.getDuration();
	        		media.setCurPos(l/w*d);
		        	return false;
        		}
        		//音量
        		var v=$('#volume_a');
		        var vd=v.data('d');
		        if(vd=='true'){
		        	v.data('d','false');
		        	return false;
		        }
		        //起点
		        var start = $('#startTime');
		        var sd=start.data('d');
		        if(sd=='true'){
		        	start.data('d','false');
		        	var time = media.getDuration();
		        	var total = $('#righttd').offset().left - $('#lefttd').offset().left - 8;
		        	var duration = start.offset().left - $('#lefttd').offset().left;
		            time = duration/total * time;
		        	time = parseInt(time);
		        	showCutBeginPos(time);
		        	showCutDuration();
		        	e.stopPropagation();
		        	media.setCurPos(time);
		        	return false;
		        }
		        //终点
		        var end = $('#endTime');
		        var ed = end.data('d');
		        if(ed=='true'){
		        	end.data('d','false');
		        	var time = media.getDuration();
		        	var total = $('#righttd').offset().left - $('#lefttd').offset().left - 8;
		        	var duration = end.offset().left - $('#lefttd').offset().left;
		        	time = duration/total * time;
		        	time = parseInt(time);
		        	showCutEndPos(time);
		        	showCutDuration();
		        	e.stopPropagation();
		        	media.setCurPos(time);
		        	return false;
		        }
	        },
	        'mouseleave':function(e){
	       		var g=$('#guage_btn'),d=g.data('d');
	       		if(d=='true'){
		        	g.data('d','false');
		        	return false;
	       		}
	        	var start = $('#startTime');
		        var sd=start.data('d');
		        if(sd=='true'){
		        	start.data('d','false');
		        	return false;
		        }
		        var end = $('#endTime');
		        var ed = end.data('d');
		        if(ed=='true'){
		        	end.data('d','false');
		        	return false;
		        }
	        },
	        'click':function(e){
	        	e=e||event;
	        	var id=$(e.target).attr('id');
	        	if(id=='guage_btn'||id=='gb'){
	        		var g=$('#guage_btn'),d=g.data('d');
	       			if(d=='false'){
	       				var x=e.clientX||e.pageX, w=g.width(),l=x-g.offset().left;
			        	var d=media.getDuration();
		        		media.setCurPos(l/w*d);
	       			}
	        	}
	        }
        });
		
	});
	
	
	/*************各种button的操作**************/
	function setrate(label,sec){
	       if(label=='高清'){
			   jwplayer().load([{file:path_hd}]);
			   $("#label_chao").removeClass("selected_label");
			   $("#label_middle").removeClass("selected_label");
			   $("#label_hight").addClass("selected_label");
			}else if(label=='标清'){
				jwplayer().load([{file:path_ld}]);
				$("#label_chao").removeClass("selected_label");
				$("#label_hight").removeClass("selected_label");
				$("#label_middle").addClass("selected_label");
			}else if(label=='超清'){
				jwplayer().load([{file:path_sd}]);
				$("#label_middle").removeClass("selected_label");
				$("#label_hight").removeClass("selected_label");
				$("#label_chao").addClass("selected_label");
			}	
		media.setCurPos(sec)
	}
	$(function(){
		//播放button
        /* $("#playBtn").mouseover(function(){
		    $(this).addClass("playToggleHover").removeClass("playToggleIcon");
		});
		$("#playBtn").mouseout(function(){
		     $(this).addClass("playToggleIcon").removeClass("playToggleHover");
		}); */
		$(".pb").bind("click",function(e){
			e=e||event;
			var gb=$('#guage_btn');
			    width=gb.width();
				dd=gb.data('d');
			var left = parseFloat($(this).css("left"));
			var duration=media.getDuration();
			if(dd=='false'){
	       		var x=e.clientX||e.pageX, width=gb.width(),l=left;
                var duration=media.getDuration();
		        media.setCurPos(l/width*duration);
	       	}
		})
		$(".muted").bind("click",function(){
			var mute = jwplayer().getMute()
			if(mute == false){
				$(this).text("声音")
				jwplayer().setMute(true)
			}else{
				$(this).text("静音")
				jwplayer().setMute(false)
			}
		})
		
		var fsButton = document.getElementById('displayToggleIcon'),
        fsElement = document.getElementById('video-box');
    
	    (function () {
			var viewFullScreen = document.getElementById("displayToggleIcon");
			    fsElement = document.getElementById('video-box');
				var width= window.screen.width;
			        height= window.screen.height;
			 if (viewFullScreen) {
				viewFullScreen.addEventListener("click", function () {
					//var docElm = document.documentElement;
					var fsElement = document.getElementById('video-box');
				    var width= window.screen.width;
			            height= window.screen.height;
					if (fsElement.requestFullscreen) {
						fsElement.requestFullscreen();
						//jwplayer().resize(width,height);
					    $('#menu').addClass('all');
						$("#wideNarrowButton").hide();
						$("#wideNarrowButton2").show();
					}
					else if (fsElement.msRequestFullscreen) {
						fsElement.msRequestFullscreen();
						//jwplayer().resize(width,height);
					    $('#menu').addClass('all');
					    $("#wideNarrowButton").hide();
						$("#wideNarrowButton2").show();
					}
					else if (fsElement.mozRequestFullScreen) {
						fsElement.mozRequestFullScreen();
						jwplayer().resize(width,height);
					    $('#menu').addClass('all');
					    $("#wideNarrowButton").hide();
						$("#wideNarrowButton2").show();
					}
					else if (fsElement.webkitRequestFullScreen) {
						fsElement.webkitRequestFullScreen();
						jwplayer().resize(width,height);
					    $('#menu').addClass('all');
					    $("#wideNarrowButton").hide();
						$("#wideNarrowButton2").show();
					}
				}, false);
			}
			 
			var cancelFullScreen = document.getElementById("cancel-fullscreen");
			if (cancelFullScreen) {
				cancelFullScreen.addEventListener("click", function () {
					var fsElement = document.getElementById('video-box');
					if (document.exitFullscreen) {
						document.exitFullscreen();
						//fsElement.exitFullscreen();
						jwplayer().resize(857,473);
					    $('#menu').removeClass('all');
					    $("#wideNarrowButton").show();
						$("#wideNarrowButton2").hide();
					}
					else if (document.msExitFullscreen) {
						document.msExitFullscreen();
						//fsElement.msExitFullscreen();
						jwplayer().resize(857,473);
					    $('#menu').removeClass('all');
					    $("#wideNarrowButton").show();
						$("#wideNarrowButton2").hide();
					}
					else if (document.mozCancelFullScreen) {
						document.mozCancelFullScreen();
						//fsElement.mozCancelFullScreen();
						jwplayer().resize(857,473);
					    $('#menu').removeClass('all');
					    $("#wideNarrowButton").show();
						$("#wideNarrowButton2").hide();
					}
					else if (document.webkitCancelFullScreen) {
						document.webkitCancelFullScreen();
						//fsElement.webkitCancelFullScreen();
						jwplayer().resize(857,473);
					    $('#menu').removeClass('all');
					    $("#wideNarrowButton").show();
						$("#wideNarrowButton2").hide();
					}
				}, false);
			}
	        
		})();
		

		
		$('#playOrPause').bind('click', function() {
			clearTimeout(TimeFn);
			TimeFn = setTimeout(function(){
				media.playOrPause();
			},300);
		});
		$('#playOrPause').bind('dblclick',function(){
			 clearTimeout(TimeFn);
		});
		//讲义目录
        $(".hello").click(function(){
			var d = jwplayer().getDuration();
			alert(d);
			var time = parseFloat($(this).attr("time"));
			alert(time)
			if(time < d){
				jwplayer().seek(time)
			}
		});
		
		//讲义
		$(".lecture").click(function(){
			var dr = jwplayer().getDuration();
			var s_t = parseInt($(this).attr("id"));
			if(s_t < dr){
				jwplayer().seek(sec)
			}
		});
		$("#playbackSpeedAddControlButton").click(function(){
			changePlaybackRate(0.1);
		});
		$("#playbackSpeedSubtractControlButton").click(function(){
			changePlaybackRate(-0.1);
		});
		
		//字幕
		$("#cc").bind('click',function(){
		    if (jwplayer().getCurrentCaptions() == 1){
				//$(this).attr("title","显示字幕");
				$("#cc").removeClass("ccOpenIcon").addClass("ccIcon");
			    jwplayer().setCurrentCaptions(0)
		    } else if (jwplayer().getCurrentCaptions() == 0){
				//$(this).attr("title","隐藏字幕");
				$("#cc").removeClass("ccIcon").addClass("ccOpenIcon");
			    jwplayer().setCurrentCaptions(1)
		    }
		});
		var status_speed = 0;
		//单击码流按钮事件
		$('#rateBtn').bind('click', function() {
			if(status_speed == 0){
				status_speed = 1;
				$('#speed_con').show();
			}else{
				status_speed = 0;
				$('#speed_con').hide();	
			}
			return false;
		});

		
		 $('.rateSet').bind('click', function(){
			 var label=$(this).attr('label');
			var sec = media.getCurPos();		
			sec = Math.floor(sec);
			setrate(label,sec);
			$('#speed_con').hide();		
		});
		//缩放视频
//		$("#displayToggleIcon").click(function(){
//			resizePlayer();
//		});
		
		$('.section').bind('dblclick', function() {
			for ( var i = 0; i < ss.length; i++) {
				if (ss[i].id == $(this).attr('id')) {
					jwplayer().seek(ss[i].start);
					break;
				}
			}
		});
		$('.ableClick').bind( {
			click : function() {
				var sec = $(this).attr('id');
				if (!sec)
					return;
				jwplayer().seek(sec.substring(5, sec.length));
			},
			mouseover : function() {
				$(this).css('text-decoration', 'underline');
			},
			mouseout : function() {
				$(this).css('text-decoration', 'none');
			}
		});
		var $div_li =$("div.tab_menu ul li");
		$div_li.click(function(){
			$(this).addClass("selected")            
				.siblings().removeClass("selected");  
			var index =  $div_li.index(this); 
			$("div.tab_box > div")   	 
					.eq(index).show()   
					.siblings().hide();
		}).hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		})
	});

})