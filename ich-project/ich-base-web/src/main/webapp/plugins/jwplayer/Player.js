//获取播放器插件对象引用
function getPlayerCtrl() {
	return window.playerInstance;
}

function createXml(str) {
    if (document.all) {
        var xmlDom = new ActiveXObject("Microsoft.XMLDOM")
        xmlDom.loadXML(str)
        return xmlDom
    }
    else
        return new DOMParser().parseFromString(str, "text/xml")
}

function QueryString(sName) {
    var sSource = String(window.document.location);
    var sReturn = "";
    var sQUS = "?";
    var sAMP = "&";
    var sEQ = "=";
    var iPos;

    iPos = sSource.indexOf(sQUS);

    var strQuery = sSource.substr(iPos, sSource.length - iPos);
    var strLCQuery = strQuery.toLowerCase();
    var strLCName = sName.toLowerCase();

    iPos = strLCQuery.indexOf(sQUS + strLCName + sEQ);
    if (iPos == -1) {
        iPos = strLCQuery.indexOf(sAMP + strLCName + sEQ);
        if (iPos == -1)
            return "";
    }

    sReturn = strQuery.substr(iPos + sName.length + 2, strQuery.length - (iPos + sName.length + 2));
    var iPosAMP = sReturn.indexOf(sAMP);

    if (iPosAMP == -1)
        return sReturn;
    else {
        sReturn = sReturn.substr(0, iPosAMP);
    }
    return sReturn;
}

function get_dbgMode_Cookie() {
    var val = null;
    var name = "dbgMode";
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]+)(;|$)"));
    if (arr != null) {
        val = unescape(arr[2]);
    }
    return val;
}

function hideDebugToolTips() {
    var tipsId = "divDbgToolTips";
    $("#" + tipsId).hide();
}

function debugToolTips(data) {
    var hidDbg = $("#hid_DebugMode");
    if (hidDbg.length == 0 || hidDbg.val() != "1") {
        var ckDbg = get_dbgMode_Cookie();
        if (ckDbg == null || ckDbg != "1") {
            return;
        }
    }
    var tipsId = "divDbgToolTips";
    var objTips = $("#" + tipsId);
    if (objTips.length == 0) {
        $(document.body).append("<div id=\"" + tipsId + "\" style=\"display:block; float:left; position:absolute; z-index:9999; top:0; left:0; width:100%; height:auto !important; min-height:30px; overflow:visible; background-color:yellow; border-bottom: 1px solid #808080;\"><div style=\"text-align:right; width:50px; height:30px; float:right;\"><input type=\"button\" value=\"关闭\" onclick=\"hideDebugToolTips()\" title=\"关闭调试信息层\" /></div></div>");
    }
    objTips = $("#" + tipsId);
    if (objTips.length == 0) { alert(data); }
    objTips.append("<div style=\"margin:5px; border-bottom: 1px solid #B0B0B0; line-height:19px; font-size:12px;\">" + data.replace(/</g, "&lt;") + "</div>");
}

function getObject(id) {
    var objCtrl;
    if (document.getElementById) {
        objCtrl = document.getElementById(id);
        if ((typeof objCtrl == "object" || typeof objCtrl == "function") && objCtrl != null) { return objCtrl; }
    }
    if (document.all) {
        objCtrl = document.all[id];
        if ((typeof objCtrl == "object" || typeof objCtrl == "function") && objCtrl != null) { return objCtrl; }
    }
    return false;
}

function handlerImageOnError(img) {
    try {
        if (!img || img.nodeName != "IMG") { return; }
        if (img.tabIndex == 0) {
            img.tabIndex = 1;
            img.src = "";
        }
    } catch (e) {
        window.status = e.message;
    }
}

function getAttachmentUrl() {
    return window.ctx + "/public/resAttachment/getImage.do?attachmentId=";
}

//获取已有的片头信息
function doGetTitles(objWatermark) {
	var titles = { "url":null, "duration":0 };
    var objTitles = $("smil > titles", objWatermark);
    if (objTitles.length > 0) {
        var strTitlesXml = "";
        if (objTitles[0].xml){
        	strTitlesXml = objTitles[0].xml
        }else{
        	strTitlesXml = objTitles[0].innerHTML;
        }
        var re = new RegExp("duration=\"(\\d+)\" src=\"(\\w+)\"", "ig");
        var dataArr = re.exec(strTitlesXml);
        if (dataArr != null) {
            if (objTitles.attr("visible") != "0"){
            	titles.duration = parseInt(dataArr[1], 10);
            	titles.url  = getAttachmentUrl() + dataArr[2];
            }
        }
    }
    return titles;
}

//加载已有的片尾信息
function doGetTrailer(objWatermark) {
	var trailer = { "url":null, "duration":0 };
    var objTrailer = $("smil > trailer", objWatermark);
    if (objTrailer.length > 0) {
        var strTrailerXml = "";
        if (objTrailer[0].xml){
        	strTrailerXml = objTrailer[0].xml;
        }else{
        	strTrailerXml = objTrailer[0].innerHTML;
        }
        var re = new RegExp("duration=\"(\\d+)\" src=\"(\\w+)\"", "ig");
        var dataArr = re.exec(strTrailerXml);
        if (dataArr != null) {
        	if (objTrailer.attr("visible") != "0"){
        		trailer.duration = parseInt(dataArr[1], 10);
        		trailer.url  = getAttachmentUrl() + dataArr[2];
            }
        }
    }
    return trailer;
}


//记录截取的时间点，只有点击保存或完成时才对其进行赋值
var numCutBeginPos = 0;
var numCutEndPos = 0;
var numVideoDuration = 0;
/**
 * 加载已有的碎片化信息
 */
function doLoadSegment(objWatermark) {
    var objSegment = $("smil > segment", objWatermark);
    if (objSegment.length > 0) {
        var strSegmentXml = "";
        if (objSegment[0].xml){
        	strSegmentXml = objSegment[0].xml;
        }else{
        	strSegmentXml = objSegment[0].innerHTML;
        }
        var re = new RegExp("beginpos=\"(\\d+)\" endpos=\"(\\d+)\"", "ig");
        var dataArr = re.exec(strSegmentXml);
        if (dataArr != null) {
            numCutBeginPos = parseInt(dataArr[1], 10);
            numCutEndPos = parseInt(dataArr[2], 10);
            numVideoDuration = numCutEndPos - numCutBeginPos;
        }
    }
}

//加载片头信息
function jwpLoadTitles(objWatermark){
	window.titles = doGetTitles(objWatermark);
	if (titles.url != null){
		autostartFlag = false;
//		mediaPlaylist.push({
//			"image": titles.url,
//			"label": "片头",
//			"duration": titles.duration
//		});
		$("#jumpText").text("片头");
		$("#p_time_a").text(numberToTime(titles.duration));
		$("#p_time_b").text(numberToTime(titles.duration));
		$("#div_titles").html("<img src=\"" + titles.url + "\" style=\"width:"+defaultPlayerWidth+"px; height:"+defaultPlayerHeight+"px\" />");
		$("#div_titles").fadeIn();
		$('#menu').hide();
		$('#jump').show();
		handleTitlesTrailer(0, titles.duration);
		return true;
	}
	return false;
}

//加载片尾信息
function jwpLoadTrailer(objWatermark){
	window.trailer = doGetTrailer(objWatermark);
	if (trailer.url != null){
//		mediaPlaylist.push({
//			"image": trailer.url,       
//			"label": "片尾",
//			"duration": trailer.duration
//		});
		$("#div_trailer").html("<img src=\"" + window.trailer.url + "\" style=\"width:"+defaultPlayerWidth+"px; height:"+defaultPlayerHeight+"px\" />");
		return true;
	}
	return false;
}

var defaultPlayerWidth = 857;
var defaultPlayerHeight = 473;


/**
 * 加载JWPlayer
 * @param resourceModel 资源JSON对象
 * @param playerContainer 播放器容器ID，不带#
 * @param playerWidth 播放器宽度
 * @param playerHeight 播放器高度
 */
function LoadJWPlayer(resourceModel, playerContainer, playerWidth, playerHeight){
	if (typeof (resourceModel) == 'undefined'){
		return false;
	}
	window.jwpResModel = resourceModel;
	
	var playerCtn = "video";
    if (typeof (playerContainer) == "string") {
        playerCtn = playerContainer;
    }
    window.jwpPlrCntnr = playerCtn;
    	
    if (typeof (playerWidth) != "number") {
    	playerWidth = defaultPlayerWidth;
    }else{
    	defaultPlayerWidth = playerWidth;
    }

    if (typeof (playerHeight) != "number") {
    	playerHeight  = defaultPlayerHeight;
    }else{
    	defaultPlayerHeight = playerHeight;
    }
    
    //构造播放列表
    var mediaPlaylist = [];
    
    //构造播放视频源，码流切换
	var mediaSources = [];
	//自动播放标识
	autostartFlag = true;
	
	if (typeof (resourceModel) == 'undefined'){
		resourceModel = {};
	}
	
	if (typeof (resourceModel.descriptData) == 'undefined'){
		resourceModel.descriptData = "";
	}
	
	$("#div_titles").hide();
	$("#div_trailer").hide();
	
	
	var objWatermark = createXml(resourceModel.descriptData);
	
	doLoadSegment(objWatermark);
	if (typeof (iCoursewareId) == 'undefined' || iCoursewareId != 0){
		jwpLoadTitles(objWatermark);
	}
	
	if (typeof (resourceModel.liveUrl) == 'undefined'){
		if (typeof (window.resourceId) == 'undefined'){
			window.resourceId = resourceModel.id;
		}
		
		//处理微课、课件点播的视频源信息
		if (resourceModel.resolution != null && resourceModel.resolution.length > 0){
			var resolutions = resourceModel.resolution.split(",");
			var resolutionTexts = {"sd":"标清", "hd":"高清", "ld":"流畅"};
			for (var i=0; i < resolutions.length; i++){
				var labeltext = resolutionTexts.ld;
				if (typeof (resolutionTexts[resolutions[i]]) != 'undefined'){
					labeltext = resolutionTexts[resolutions[i]];
					window["path_"+resolutions[i]] = vodroot + resourceModel.filePath + resolutions[i] + ".mp4";
					var isDefault = false;
					if (resolutions[i] == "ld"){isDefault=true;}
					mediaSources.push({
						"file": window["path_"+resolutions[i]],       
						"label": labeltext,
						"default": isDefault
					  });
				}
			}
		}
		
		if (mediaSources.length == 0){
			path_ld = vodroot + resourceModel.filePath + ".mp4";
			mediaSources.push({
				"file": path_ld,       
				"label": "标清",
				"default": true
			  });
		}
		
		mediaPlaylist.push({
			//码流切换
			sources: mediaSources,
			title: resourceModel.resourceTitle,
			tracks: [
				//字幕功能
				{ 
					file: ctx+"/public/resEditor/captionSrt.do?coursewareId=" + resourceModel.id + "&t=" + Math.random(),     
					label: "English",
					kind: "captions"
				},
				//看点功能
				{ 
					file: ctx+"/public/resEditor/focusVtt.do?coursewareId=" + resourceModel.id + "&t=" + Math.random(),                                      
					kind: "thumbnails"
				}
			]
		});
	}else{
		//处理直播视频源信息
		mediaSources.push({
			"file": resourceModel.liveUrl
			
		  });
		
		mediaPlaylist.push({
			//码流切换
			sources: mediaSources
		});
		
		$('#jump').hide();
		$('#menu').show();
	}
	
	if (typeof (iCoursewareId) == 'undefined' || iCoursewareId != 0){
		//加载片尾信息
		jwpLoadTrailer(objWatermark);
		
		//加载看点信息
		doAddFocusToCtrlbar(objWatermark);
	}
	
	
	//视频加载
	window.playerInstance = jwplayer(playerCtn);
	extendJWPlayer(window.playerInstance);
	
	playerInstance.setup({
		width: playerWidth,
		height: playerHeight,
		autostart: autostartFlag, //自动播放
		controls:false,
		repeat: false,    //重复播放
		mute: true,      //声音控制
		androidhls: true,   //android设备使用
		stretching:'exactfit',
		playlist: mediaPlaylist,
		
		//字幕样式控制
		captions: {                                                        
			color: '#FFFFFF',
			fontSize: 20,
			backgroundOpacity: 0,
			fontfamily:'黑体',
			edgeStyle:'dropshadow'
		},
		//皮肤css
		skin: {
		   name: "bekle",                                                 
		   active: "green",
		   inactive: "#999",
		   background: "black"
		},
		//水印 logo
		logo: {
			file: ctx+"/images/player/logo.png",                                       
			link: ''
		},
		events:{
			//加载时
			onReady:function(){
				
				$("#wideNarrowButton").show();
				$("#wideNarrowButton2").hide();
				$('#playOrPause').removeClass().addClass('play');
				$('#playOrPause').attr('title','单击播放');
//				if (numCutBeginPos != 0){
//					if (!this.isCutMode){
//						//跳到剪辑的开始位置
//						if (this.getPosition() < numCutBeginPos){
//							this.seek(numCutBeginPos);
//						}
//					}
//				}
			},
			//暂停时
			onPause: function(){
				$('#playOrPause').removeClass().addClass('play');
				$('#playOrPause').attr('title','单击播放');
			},
			//播放时
			onPlay: function(event){
				$('#jump').hide();
				if (typeof (iCoursewareId) == 'undefined'){
					$('#menu').show();
				}
				if (this.userSeekPos == -1){
					if (numCutBeginPos != 0){
						if (!this.isCutMode){
							//跳到剪辑的开始位置
							if (this.getPosition() < numCutBeginPos || this.getPosition() >= numCutEndPos){
								this.seek(numCutBeginPos);
							}
						}
					}
				}
				window.syncStart();
				$('#playOrPause').removeClass().addClass('pause');
			    $('#playOrPause').attr('title','单击暂停');
			},
			//闲置时
			onIdle: function(){
				$('#guage_btn .b').width(0);
				$('#guage_btn .a').css('left',0);
			},
			//播放结束
			onComplete:function(){
				onJSPlayComplete();
			},
			onBufferChange:function(){
			},
			onTime:function(event){
				//var duration=event.duration;
				//var position=event.position;
				//window.media.currentTime=position;
				//window.media.duration=duration;
				//window.time.setCurrentTimeStatus(position,duration);
				//window.time.pianSetCurrentTimeStatus(position,duration);
				
				//如果当前播放位置大于截取的结束位置，则停止播放；
				if (this.isCutMode){
					return;
				}
				if (this.GetCutEndPos() == 0){
					return;
				}
				if (parseInt(event.duration) != this.GetCutEndPos()){
					if (event.position > this.GetCutEndPos()){
						//停止播放，触发onComplete事件；
						//this.seek(this.GetCutBeginPos());
						this.pause();
						onJSPlayComplete();
					}
				}
			}
		}
	});
}

//扩展JWPlayer的功能
function extendJWPlayer(obj){
	if (obj){
		if (typeof obj.GetCutBeginPos == 'undefined'){
			obj.GetCutBeginPos = function(){
				if (typeof (numCutBeginPos) != 'undefined'){
					return numCutBeginPos;
				}
				return 0;
			}
		}
	
		if (typeof obj.GetCutEndPos == 'undefined'){
			obj.GetCutEndPos = function(){
				if (typeof (numCutEndPos) != 'undefined'){
					return numCutEndPos;
				}
				return obj.getDuration();
			}
		}
		
		//标记当前是否处于截取模式
		if (typeof obj.isCutMode == 'undefined'){
			obj.isCutMode = false;
		}
		
		//设置当前是否处于截取模式
		if (typeof obj.SetCutMode == 'undefined'){
			obj.SetCutMode = function(isCutMode){
				this.isCutMode = isCutMode;
				if (this.isCutMode){
					$('#menu').hide();
					$("#div_clip").show();
				}else{
					$("#div_clip").hide();
					$('#menu').show();
				}
			}
		}
		
		if (typeof obj.SetCutBeginPosCtrl == 'undefined'){
			obj.SetCutBeginPosCtrl = function(videoPosNum){
				numCutBeginPos = videoPosNum;
				this.seek(numCutBeginPos);
			}
		}
		
		if (typeof obj.SetCutEndPosCtrl == 'undefined'){
			obj.SetCutEndPosCtrl = function(videoPosNum){
				numCutEndPos = videoPosNum;
				this.seek(numCutEndPos);
			}
		}
		
		if (typeof obj.playVideo == 'undefined'){
			obj.playVideo = function(){
				if (this.getState() != "playing"){
					this.play();
				}
			}
		}
		
		if (typeof obj.getCurrentPosition == 'undefined'){
			obj.getCurrentPosition = function(){
				if (this.isCutMode){
					return this.getPosition();
				}else{
					return this.getPosition() - this.GetCutBeginPos();
				}
			}
		}
		
		if (typeof obj.getVideoDuration == 'undefined'){
			obj.getVideoDuration = function(){
				if (this.isCutMode){
					return this.getDuration();
				}else{
					if (typeof (numVideoDuration) != 'undefined' && numVideoDuration != 0){
						return numVideoDuration;
					}
					return obj.getDuration();
				}
			}
		}
		
		//标记当前是否处于用户seek模式
		if (typeof obj.userSeekPos == 'undefined'){
			obj.userSeekPos = -1;
		}
		
		if (typeof obj.userSeek == 'undefined'){
			obj.userSeek = function(pos){
				this.userSeekPos = parseInt(pos);
				this.seek(this.userSeekPos);
				setTimeout("handleUserSeekState()", 500);
			};
		}
	}
}

//处理用户Seek事件，解决微课件停止播放时Seek不到指定位置的问题
function handleUserSeekState() {
    var tick = 500;
    var obj = getPlayerCtrl();
    try{
        if (obj) {
        	if (obj.userSeekPos == -1){
        		return;
        	}
            if (parseInt(obj.getPosition()) > obj.userSeekPos){
            	obj.userSeekPos = -1;
            	return;
            }
        } else {
            return;
        }
    }catch(e){throw e; return; }
    setTimeout("handleUserSeekState()", tick);
}

//将以秒为单位的数字时间转为时分秒格式
function numberToTime(numStr) {
    var strTime = "00:00:00";
    if (numStr == null) {
        return strTime;
    }
    if (typeof (numStr) != 'number' && typeof (numStr) != 'string') {
        return strTime;
    }
    var num = parseInt(numStr, 10);
    if (num < 1) {
        return strTime;
    }
    strTime = "" + numberFormat2(num % 60);
    num = (num - num % 60) / 60;
    if (num >= 60) {
        strTime = numberFormat2(num % 60) + ":" + strTime;
        num = (num - num % 60) / 60;
        strTime = numberFormat2(num) + ":" + strTime;
    } else {
        strTime = "00:" + numberFormat2(num) + ":" + strTime;
    }

    return strTime;
}
//数字如果小于10，则在前面补0
function numberFormat2(num) {
    if (num < 10) {
        return "0" + num;
    } else {
        return "" + num;
    }
}

function handleTitlesTrailer(flag, sencond) {
	if (typeof flag == 'undefined'){
		return;
	}
	if (typeof sencond == 'undefined'){
		return;
	}
	var sencondNum = parseInt(sencond, 10);
	if (isNaN(sencondNum)){
		return;
	}

	$("#p_time_a").text(numberToTime(sencond));
	
    try{
    	if (sencondNum < 1){
    		if (flag == 0){
    			$('#menu').show();
    			$('#jump').hide();
    			$("#div_titles").fadeOut();
    			$("#div_titles").hide();
    			$("#div_trailer").hide();
    			var obj = getPlayerCtrl(); 
    			if (obj){
    				obj.play();
    				obj.seek(numCutBeginPos);
    			}
        	}else{
        		$("#jumpText").html("<a href=\"javascript:void exec_replay()\">重播</a>");
        	}
    		return;
    	}
    }catch(e){throw e; return; }
    setTimeout("handleTitlesTrailer("+flag+","+(sencond-1)+")", 1000);
}

function exec_replay(){
	LoadJWPlayer(window.jwpResModel, window.jwpPlrCntnr);
}

/**
 * 获取看点图片URL
 * @param vodpos
 * @returns {String}
 */
function getFocusImageUrl(vodpos){
	//由于数据库中保存的是截取后的时间点，而图片是在源视频上截取的，所以要换算成源视频的时间点
	var pos = parseInt(vodpos, 10);
	if (typeof (numCutBeginPos) != 'undefined' && numCutBeginPos != 0){
		pos = pos + numCutBeginPos;
	}
	
	var fimg = numberToTime(pos);
	fimg = fimg.replace(/:/ig, '_');
	return vodroot + window.jwpResModel.filePath + "/v/" + fimg + ".png";
}

/**
 * 将视频的时间点换算为对应进度条上的位置
 * @param posStr
 * @returns {Number}
 */
function posToProgressPos(posStr){
	var width = $("#guage_btn").width();
	if (!$.isNumeric(width) || width < defaultPlayerWidth){ width = defaultPlayerWidth; }
	var pos = parseInt(posStr, 10);
	//截取后的进度条不能再减去截取开始时间；
//	if (typeof (numCutBeginPos) != 'undefined' && numCutBeginPos != 0){
//		pos = pos - numCutBeginPos;
//		if (pos < 0){
//			pos = 0;
//		}
//	}
	var left = pos / numVideoDuration * width;
	if (left > width - 5){
		left = width -5;
	}
	if ($.isNumeric(left)){ return left; }
	return 0;
}

//加载已有的看点信息
function doAddFocusToCtrlbar(objWatermark) {
	$("#guage_btn span").remove();
	if (numVideoDuration == 0 || numCutEndPos == 0){
		return;
	}
    var liParent = $("#gtip");
    $("smil > focus > item", objWatermark).each(function (i) {
    	var beginpos = $(this).attr("beginpos");
    	var newLi = $("<span class=\"pb\" style='left:"+posToProgressPos(beginpos)+"px' onmouseover=\"popFocusTooltip(this, "+beginpos+",'" +$(this).text()+ "');\" onmouseout=\"hideFocusTooltip()\" onclick=\"popFocusClick("+beginpos+");\"></span>");
        liParent.after(newLi);
        liParent = newLi;
    });
}

//水印右键单击事件处理
function popFocusTooltip(sender, vodpos, text) {
	$("#mcTooltipWrapper").stop(true);
	$("#mcTooltipWrapper .mcTooltipInner").html("");
	focusItem = $("<div style=\"width:220px;\"><img src=\""+ getFocusImageUrl(vodpos) +"\" style=\"float:right;margin-left:12px;width:100px;height:75px;\" onerror=\"this.alt='等待生成'\"><b style=\"width: 120px; word-break: break-all; word-wrap: break-word;\">" + text + "</b><br><i></i><br><br></div>");
	focusItem.click(function(){
		popFocusClick(vodpos);
	});
	$("#mcTooltipWrapper .mcTooltipInner").append(focusItem);
    var soffset = $(sender).offset();
    $("#mcTooltipWrapper").show();
    $("#mcTooltipWrapper").offset({ top: soffset.top - 142, left: soffset.left - 90 });
    $("#mcTooltipWrapper").fadeIn();
    
}

function popFocusClick(vodpos){
	var pos = parseInt(vodpos, 10);
	if (typeof (numCutBeginPos) != 'undefined' && numCutBeginPos != 0){
		pos = pos + numCutBeginPos;
	}
	getPlayerCtrl().seek(pos);
}

function hideFocusTooltip(){
	$("#mcTooltipWrapper").fadeOut('slow');
}

function onJSPlayComplete(){
	$('#playOrPause').removeClass().addClass('play');
    $('#playOrPause').attr('title','单击播放');
	$('#guage_btn .b').width(0);
	$('#guage_btn .a').css('left',0);
	if (window.trailer.url != null){
		$("#jumpText").text("片尾");
		$("#p_time_a").text(numberToTime(window.trailer.duration));
		$("#p_time_b").text(numberToTime(window.trailer.duration));
		$("#div_trailer").fadeIn();
		$('#menu').hide();
		$('#jump').show();
		handleTitlesTrailer(1, window.trailer.duration);
	}
}
