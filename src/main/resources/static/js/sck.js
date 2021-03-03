const msgButton = document.getElementById("btnSend");

document.addEventListener("DOMContentLoaded", function() {
    // Connect WebSocket SockJs
    webConnect();

    msgButton.addEventListener("click", function(event) {
        console.log("BUTTON CLICKED!");
        event.preventDefault();
        if (!isStomp && socket.readyState !== 1) return;
        sendMessage();
    });
});