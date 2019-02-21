         /**
		  * 检查输入的IP地址是否正确
		  * 输入:str  字符串
		  *  返回:true 或 flase; true表示格式正确
		  */
		function isIP(str){
			 if (str === ""){ return true; }
		     var arg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		     if (str.match(arg) == null) {
		         return false;
		     }
		     else {
		         return true;
		     }
		}
		//校验(国内)邮政编码
		function isPostalCode(s)
		{
		    var pattern =/^[0-9]{6}$/;
		     if(s!="")
		     {
		         if(!pattern.exec(s))
		         {
		            return false;
		         }
		     }
		     return true;
		}

		//校验手机号码：必须以数字开头，除数字外，可含有“-”
		function isMobile(s){
			var reg = /^((13[0-9])|(15[0-3,5-9])|(17[0,6-8])|(18[0,3,5-9]))\d{8}$/;
			return reg.test(s);
		}

		//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
		function isTel(s)
		{
		//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"

		 var pattern =/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		 //var pattern =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/; 
		     if(s!="")
		     {
		         if(!pattern.exec(s))
		         {
		           return false;
		         }else
		           return true;
		     }else
		        return false;
		}
		//校验邮箱
		function isEmail(str) {
			if(str == null || str==''){
				return false;
			}
			var emailPattern = /^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i;
			if (!emailPattern.test(str)) { 
				return false;
			}
			return true;
		}	
		
		//校验数字
		function isNumber(str){
			if(str == null || str==''){
				return false;
			}
			 //定义正则表达式部分
		    var reg = /^\d+$/;
		    return reg.test(str);
		}
		//校验正整数
		function isInteger(str){
			if(str == null || str==''){
				return false;
			}
			var regu = /^[-]{0,1}[0-9]{1,}$/;
			return regu.test(str);
		}
		//校验浮点数
		function isFloat(str){
			if(str == null || str==''){
				return false;
			}
			var regu = /^\d+\.\d\d$/;
			return regu.test(str);
		}
		//校验日期
		function checkDate(strDate){
		    re=/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;//正则表达式
		    if(re.test(strDate))//判断日期格式符合YYYY-MM-DD
		    {
		       return true;
		    }
		    return false;
		}
		 
		//比较时间 startDate>endDate return false;
		function compareDate(startDate,endDate){
			var start = new Date((startDate).replace(/-/g,"/"));
            var end = new Date((endDate).replace(/-/g,"/"));             
            if(start > end)
            {
               return false;
            }else{
              return true;
           }           
		}
		
		Date.prototype.format = function(format)
		{
		 var o = {
		 "M+" : this.getMonth()+1, //month
		 "d+" : this.getDate(),    //day
		 "h+" : this.getHours(),   //hour
		 "m+" : this.getMinutes(), //minute
		 "s+" : this.getSeconds(), //second
		 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		 "S" : this.getMilliseconds() //millisecond
		 }
		 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		 for(var k in o)if(new RegExp("("+ k +")").test(format))
		 format = format.replace(RegExp.$1,
		 RegExp.$1.length==1 ? o[k] :
		 ("00"+ o[k]).substr((""+ o[k]).length));
		 return format;
		}
		
		//获得跟当前的时间差 离当前时间差多少分钟
		function getTimeDifference(str){
			var oldTime = (new Date(str)).getTime();
			var nowTime = new Date().getTime();
			var leave1 = nowTime - oldTime;
			var minutes=Math.floor(leave1/(60*1000));
			return minutes;
		}
		//获取字符串长度
		function strlen(str){  
		    var len = 0;  
		    for (var i=0; i<str.length; i++) {   
		     var c = str.charCodeAt(i);   
		    //单字节加1   
		     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {   
		       len++;   
		     }   
		     else {   
		      len+=2;   
		     }
		    }   
		    return len;
		}  
		
