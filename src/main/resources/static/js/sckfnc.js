let socket = null;
let isStomp = false;
let currentURL = window.location.href;
let mid = currentURL.replace("http://localhost:8080/messages/", "");

function webConnect() {
    let sock = new SockJS("/secured/room");
    let client = Stomp.over(sock);
    let sessionId = "";

    isStomp = true;
    socket = client;

    client.connect({}, function() {
        console.log("INFO: CONNECTED");

        let url = client.ws._transport.url;
        url = url.replace("ws://localhost:8080/secured/room/", "")
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");

        sessionId = url;

        client.subscribe("/secured/user/queue/specific-user" + "-user" + sessionId, function(out) {
           console.log("INFO: SUBSCRIBE");

           let data = JSON.parse(out.body);
           let midURL = currentURL.replace("http://localhost:8080/messages/", "");

           // Show message
           showMessage(JSON.parse(out.body));
        });
    });
}

function sendMessage() {
    let today = new Date();
    let to = document.getElementById("to").innerText;
    let text = document.getElementById("msg").value.trim();
    let pid = document.getElementById("pid").value;
    let t = today.getHours() + ":" + today.getMinutes();

    let msg = {
        "toUser": to,
        "content": text,
        "projectId": pid,
        "roomNumber": mid,
        "time": t
    }

    console.log("Stomp status: " + isStomp);

    if (text && isStomp) {
        socket.send("/secured/room", {}, JSON.stringify(msg));
        document.getElementById("msg").value = "";
        showMessage(msg);
    }
}

function showMessage(msg) {
    let d = document.createElement("div");
    let b = document.createElement("b");
    let sm = document.createElement("small");
    let p1 = document.createElement("p");
    let p2 = document.createElement("p");
    let p3 = document.createElement("p");

    let response = document.getElementById("response");

    let content = document.createTextNode(msg.content);
    let time = document.createTextNode(msg.time);

    let from;
    if(msg.fromUser == null) {
        from = document.createTextNode("Me");
    } else {
        from = document.createTextNode(msg.fromUser);
    }

    b.appendChild(from);
    p1.appendChild(b);
    p2.appendChild(content);
    sm.appendChild(time);
    p3.appendChild(sm);

    d.appendChild(p1);
    d.appendChild(p2);
    d.appendChild(p3);

    response.appendChild(d);
}