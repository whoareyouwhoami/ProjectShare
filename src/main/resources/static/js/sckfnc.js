let socket = null;
let isStomp = false;
let currentURL = window.location.href;
let mid = currentURL.replace("http://localhost:8080/messages/", "");
let message_type = "";
let message_room = ""

function webConnect() {
    let sock = new SockJS("/secured/room");
    let client = Stomp.over(sock);
    let sessionId = "";

    isStomp = true;
    socket = client;

    client.connect({}, function() {
        console.log("INFO: CONNECTED");

        let url = client.ws._transport.url;
        url = url.replace("ws://localhost:8080/secured/room/", "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");

        sessionId = url;
        console.log("CURRENT SESSION: " + url);
        let message_type = mid.replace(/[^A-Za-z]+/g, "");
        console.log("Message type: " + message_type);
        
        let message_room = mid.replace(/[^0-9]+/g, "");
        console.log("temp " + message_room);

        if(message_type === "m") {
            console.log("INFO: SUBSCRIBE PRIVATE MESSAGE");
            client.subscribe("/secured/user/queue/specific-user" + "-user" + sessionId, function(out) {
                message_type = 'private';
                showMessage(JSON.parse(out.body));
            });
        } else {
            console.log("INFO: SUBSCRIBE GROUP MESSAGE");
            client.subscribe("/secured/user/queue/group/p.*" , function(out) {
                message_type = 'group';
                showMessage(JSON.parse(out.body));
            });
        }
    });
}

function sendMessage() {
    console.log("SENDING MESSAGE...");
    let today = new Date();
    let to = document.getElementById("to").value.trim();
    let text = document.getElementById("msg").value.trim();
    let pid = document.getElementById("pid").value;
    let t = today.getHours() + ":" + today.getMinutes();
    let type = mid.replace(/[^A-Za-z]+/g, "");
    let message_id = mid.replace(/[^0-9]+/g, "");

    let msg = {
        "project": pid,
        "message": message_id,
        "receiver": to,
        "content": text,
        "sent": t,
        "type": type
    }

    if (text && isStomp) {
        socket.send("/secured/room", {}, JSON.stringify(msg));
        document.getElementById("msg").value = "";
        if(message_type === 'private') {
            showMessage(msg);
        }
    }
}

function showMessage(msg) {
    let d = document.createElement("div");
    let sm = document.createElement("small");
    let p1 = document.createElement("p");
    let p2 = document.createElement("p");

    let response = document.getElementById("response");

    let content = document.createTextNode(msg.content);
    let sent = document.createTextNode(msg.sent);

    let from = msg.receiver;
    let currentUser = document.getElementById("to").value.trim();
    if(from === currentUser) {
        d.className = 'text-start msg-content'
        from = document.createTextNode("Me");
    } else {
        d.className = 'text-end msg-content'
        from = document.createTextNode(msg.receiver);
    }

    p1.appendChild(from);
    p2.appendChild(content);
    sm.appendChild(sent);
    d.appendChild(p1);
    d.appendChild(p2);
    p1.className = 'fw-bold';
    p1.style.margin = '3px'
    p2.className = 'text-wrap'
    p2.style.margin = '3px'
    response.appendChild(d);
}