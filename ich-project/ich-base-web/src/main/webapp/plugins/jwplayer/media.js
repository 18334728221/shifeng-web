/**
 * @media.js
 * @author yk
 * @date:2015-12-13
 * @modify:2015-12-13
 */
define(function(require, exports, module) {
	var $ = require('../jquery-2.1.4.min');
	require('./jwplayer/jwplayer');
	var playerInstance = jwplayer("video");
	exports.duration=null;
    exports.currentTime=null;
	exports.play = function() {
		playerInstance.play();
		$('#playOrPause').removeClass().addClass('pause');
		$('#playOrPause').attr('title','单击暂停');
	};
	exports.pause = function() {
		playerInstance.pause();
		$('#playOrPause').removeClass().addClass('fl play');
		$('#playOrPause a').attr('title','单击播放');
	};
	exports.stop = function() {
		playerInstance.stop();
		$('#playOrPause').removeClass().addClass('play');
		$('#playOrPause').attr('title','单击播放');
	};
	exports.playOrPause = function() {
		var state = playerInstance.getState();
		if (state == "PLAYING") {
			playerInstance.pause();
		}else{
			playerInstance.play();
		}
		return false;
	};
	exports.isPlaying = function() {
		return playerInstance.getState()=='PLAYING';
	};
	exports.getState=function(){
		return playerInstance.getState();
	};
	exports.prettyPrintSeconds=function(seconds, leadingMinutes, leadingHours)
		{
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
		};	
	exports.setCurrentTimeStatus = function(v1,v2) {
		if(v1)	
		$('#time_a').html(this.prettyPrintSeconds(v1));
		if(v2)	
		$('#time_b').html(this.prettyPrintSeconds(v2));
	};
	exports.pianSetCurrentTimeStatus = function(t1,t2) {
		if(t1)	
		$('#p_time_a').html(this.prettyPrintSeconds(t1));
		if(t2)	
		$('#p_time_b').html(this.prettyPrintSeconds(t2));
	};
	exports.getDuration = function() {
			// return this.duration;
			// return this.flashObj.getDuration();
			var d = playerInstance.getDuration();
			if (!playerInstance.isCutMode){
				if (typeof (numVideoDuration) != 'undefined' && numVideoDuration != 0){
					d = numVideoDuration;
				}
			}
			return d;
	};
	exports.getCurPos = function() {
			var p = playerInstance.getPosition();
			if (!playerInstance.isCutMode){
				if (typeof (numCutBeginPos) != 'undefined' && numCutBeginPos != 0){
					p = p - numCutBeginPos;
					if (p < 0){
						p = 0;
					}
				}
			}
			return p;
	};
	exports.setCurPos = function(sec) {
		try {
			if(this.duration==null||sec<=this.duration){
				sec = Math.floor(sec);
				if (!playerInstance.isCutMode){
					if (typeof (numCutBeginPos) != 'undefined' && numCutBeginPos != 0){
						sec = sec + numCutBeginPos;
					}
				}
				jwplayer().userSeek(sec);
			}
		} catch (e) {
			alert('设置播放时间时出现错误!');
		}
	};
});