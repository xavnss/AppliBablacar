$(document).ready(function () {
    var stompClient = null;

    console.log('trying: ');
    var socket = new SockJS('/websocket-error');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/rideList', function (ride) {
            updateRide(JSON.parse(ride.body));
        });
    });

    function sendRide() {
    	console.log("sendRide to /app/aNewRide");
        stompClient.send("/app/aNewRide", {}, JSON.stringify({'fromCity': $("#name").val()}));
    }

    function updateRide(message) {
    	console.log("Update ride list with content:");
//    	requette ajax vers controller
    	var data = {}
    	data["greetings"] = $("#greetings").val();
    	
    	$.ajax({
    		type : "GET",
    		contentType : "application/json",
    		url : "/ride/appendRide",
    		data : message,
    		dataType : 'html',
    		timeout : 100000,
    		success : function(data) {
    			console.log("SUCCESS: ", data);
    			 $("#greetings").append("<tr>" + data + "</tr>");
    		},
    		error : function(e) {
    			console.log("ERROR: ", e);
    		},
    		done : function(e) {
    			console.log("DONE");
    		}
    	});
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	injection de la reponse
    	
       
    }

});