var usercode=null;
$(function(){
		
		var reqUrl = urls['rootUrl'] + '/sysLogin/getSysLoginInfo';
		$.ajax({
			url: reqUrl,
			type: 'post',
			async: true,
		 	success: function(result){//请求成功
				usercode=result.iqbResult.result.userCode;
				connect(usercode);
				//connectRece();
//				$('#orgName').html(result.iqbResult.result.orgShortName);	
		 	},
		});
	
});	


function connect(usercode) {
   var socket = new SockJS('/eatep.house.front/notification');
   stompClient = Stomp.over(socket);
   stompClient.heartbeat.incoming = 0;
   stompClient.heartbeat.outgoing = 0;
   var headers = {};
   var user = '/user/'+usercode+'/notifications';
   stompClient.connect('','', function(frame) {
   stompClient.subscribe(user, function(message){
           showMessage(message.body);
       });
   },onError);
}
function onError(e) {
	  console.log("STOMP ERROR", e);
	}
function showMessage(body) {
	var message = JSON.parse(body);
	Lobibox.notify('info', {
        icon: false,
        title: message.subject,
        sound:false,
        delay: 20000,
        msg: message.content
    });
}