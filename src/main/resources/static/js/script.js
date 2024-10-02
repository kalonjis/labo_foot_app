let stompClient = null; // Keep the stompClient reference outside the function

// Function to connect to WebSocket
function connect() {
    const jwtToken = document.getElementById('jwtInput').value; // Get the JWT token from the input
    const notificationsDiv = document.getElementById('notifications');

    // Check if JWT token is provided
    if (!jwtToken) {
        alert('Please enter a JWT token');
        return;
    }

    // Create a SockJS connection
    const socket = new SockJS('http://127.0.0.1:8080/ws'); // Adjust if your server runs on a different host/port
    stompClient = Stomp.over(socket);

    // Connect to the WebSocket server
    stompClient.connect({ Authorization: `Bearer ${jwtToken}` }, function (frame) {
        console.log('Connected: ' + frame);
        const userId = getUserIdFromJwt(jwtToken);

        // Subscribe to user-specific notifications
        stompClient.subscribe(`/user/notifications`, function (notification) {
            const message = JSON.parse(notification.body).message;
            displayNotification(message);
        });
    }, function (error) {
        console.error('Error connecting to WebSocket: ', error);
    });

    // UI reset
    notificationsDiv.textContent = "";
    document.getElementById('connectButton').disabled = true;
    document.getElementById('disconnectButton').disabled = false;
}

function getUserIdFromJwt(jwtToken) {
    console.log(jwtToken);
    const payload = JSON.parse(atob(jwtToken.split('.')[1]));
    console.log(payload);
    return payload.sub;
}

// Function to disconnect from WebSocket
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect(() => {
            console.log("Disconnected from WebSocket");

            // Enable the connect button and disable the disconnect button
            document.getElementById('connectButton').disabled = false;
            document.getElementById('disconnectButton').disabled = true;
        });
        stompClient = null;
    }
}

// Function to display notifications
function displayNotification(message) {
    const notificationsDiv = document.getElementById('notifications');
    const newNotification = document.createElement('div');
    newNotification.className = 'notification'; // Add a class for styling
    newNotification.innerText = `Notification: ${message}`; // Adjust according to your message structure
    notificationsDiv.appendChild(newNotification);
}

// Add event listeners to the connect and disconnect buttons
document.getElementById('connectButton').addEventListener('click', connect);
document.getElementById('disconnectButton').addEventListener('click', disconnect);
