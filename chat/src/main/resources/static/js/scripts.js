// JavaScript

// Funktion zum Abmelden
function logout() {
    // Extrahiere den Ursprung (Origin) der aktuellen Anwendung
    const currentOrigin = window.location.origin;

    stopMessageChecking();

    sessionStorage.clear();

    // Baue den vollständigen Pfad zur Logout-Route
    const logoutPath = '/logout';

    // Konstruiere die vollständige URL
    const logoutURL = currentOrigin + logoutPath;

    // Führe den Logout durch (z.B., leite zur Logout-URL weiter)
    window.location.href = logoutURL;
}

// Funktion zum Öffnen des Modals
function openRoomModal() {
    var modal = document.getElementById('roomModal');
    modal.style.display = 'flex'; // Zeige das Modal an
}

// Funktion zum Schließen des Modals
function closeRoomModal() {
    var modal = document.getElementById('roomModal');
    modal.style.display = 'none'; // Verstecke das Modal
}

// Aktion bei Klick auf "Einen neuen Raum erstellen" Button
document.getElementById('createRoomBtn').addEventListener('click', function () {
    closeRoomModal();
    openCreateRoomModal();
});

// Ergänzte JavaScript-Funktionen für das Chatraumerstellungs-Modal
function openCreateRoomModal() {
    var createRoomModal = document.getElementById('createRoomModal');
    createRoomModal.style.display = 'flex';
}

function closeCreateRoomModal() {
    var createRoomModal = document.getElementById('createRoomModal');
    createRoomModal.style.display = 'none';
}

// Aktion bei Klick auf "Einem Raum beitreten" Button
document.getElementById('joinRoomBtn').addEventListener('click', function () {
    closeCreateRoomModal();
    openJoinRoomModal();
});

// Ergänzte JavaScript-Funktionen für das Chatbeitritts-Modal
function openJoinRoomModal() {
    var joinRoomModal = document.getElementById('joinRoomModal');
    joinRoomModal.style.display = 'flex';
}

function closeJoinRoomModal() {
    var joinRoomModal = document.getElementById('joinRoomModal');
    joinRoomModal.style.display = 'none';
}

async function joinChat() {
    var chatId = document.getElementById('chatIdInput').value;
    var password = document.getElementById('chatIdPassword').value;

    //Prüfung, ob die Chat-ID nicht leer ist
    if (chatId.trim() === '') {
        alert('Chat-ID darf nicht leer sein');
        return;
    }

    await joinChatFunction(chatId, password);

    // Schließe das Modal
    closeJoinRoomModal();
}

async function joinChatFunction(chatId, password) {
    userId = sessionStorage.getItem('user_id');
    const response = await fetch(`/chats/addParticipant/${chatId}/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ password: password })
    });

    if (!response.ok) {
        const errorMessage = await response.text();
        console.error('Fehler beim Beitritt in den Chat:', errorMessage);
    } else {
        console.log('Erfolgreich dem Chat beigetreten');
        loadUserChats();
    }
}

async function createChatFunction(chatName, password) {

    const response = await fetch('/create-chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: chatName,
            password: password,
        }),
    });

    if (!response.ok) {
        const errorMessage = await response.text();
        console.error('Fehler bei der Chat-Erstellung:', errorMessage);
    } else {
        console.log('Chat erfolgreich erstellt');
    }
}

// Schließe das Modal, wenn der Benutzer außerhalb des Modals klickt
window.addEventListener('click', function (event) {
    var modal = document.getElementById('roomModal');
    var modalContent = document.getElementById('modalContent');

    if (event.target === modal) {
        closeCreateRoomModal();
    }
});


// Funktion zum Abrufen des Benutzernamens über GET-Anfrage
function getCurrentUsername() {
    var usernameEndpointURL = '/user/name';

    return fetch(usernameEndpointURL)
        .then(response => {
            if (!response.ok) {
                throw new Error('Fehler beim Abrufen des Benutzernamens');
            }
            return response.json();
        })
        .then(username => {
            sessionStorage.setItem('username', username);
            return username;
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}

function getCurrentUserId() {
    var userIdEndpointURL = '/user/id';

    return fetch(userIdEndpointURL)
        .then(response => {
            if (!response.ok) {
                throw new Error('Fehler beim Abrufen der BenutzerId');
            }
            return response.json();
        })
        .then(user_id => {
            sessionStorage.removeItem('user_id');
            sessionStorage.setItem('user_id', user_id);
            return user_id;
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}

document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('userIcon').addEventListener('click', function () {
        openUserInfoModal();
    });

    getCurrentUserId();

    loadUserChats();

    // Aktion bei Klick auf "Senden" Button
    document.querySelector('.send-btn').addEventListener('click', function () {
        sendMessage();
    }
    );

    // Aktion bei Drücken der Enter-Taste im Texteingabefeld
    document.querySelector('.chat-input input').addEventListener('keypress', function (e) {
        // Überprüfen, ob die gedrückte Taste die Enter-Taste ist
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
})

function sendMessage() {
    getCurrentUserId();

    var messageText = document.querySelector('.chat-input input').value;
    if (messageText.trim() !== '') {
        var messageData = {
            content: messageText,
            chat_id: sessionStorage.getItem('selectedChatId'),
            user_id: sessionStorage.getItem('user_id'),
        };

        console.log(sessionStorage.getItem('user_id'));
        sendMessageToController(messageData);

        document.querySelector('.chat-input input').value = '';
    }

}

// Funktion zum Senden der Nachricht an den MessageController
function sendMessageToController(messageData) {
    var sendMessageURL = '/messages';
    fetch(sendMessageURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(messageData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Fehler beim Senden der Nachricht');
            }
            return response.json();
        })
        .then(sentMessage => {
            console.log('Nachricht erfolgreich gesendet:', sentMessage);
            loadAndShowMessages(sessionStorage.getItem('selectedChatId'));
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}

async function getAllMessagesByChatId(chat_id) {
    try {
        const response = await fetch(`/messages/chat/${chat_id}`);

        if (!response.ok) {
            throw new Error('Fehler beim Abrufen der Nachrichten');
        }

        const messages = await response.json();
        return messages;
    } catch (error) {
        console.error('Fehler:', error);
        return [];
    }
}




//############################################ UserInfo

function openUserInfoModal() {
    var userInfoModal = document.getElementById('userInfoModal');
    userInfoModal.style.display = 'flex';
    console.log("User-Settings");
    loadUserInfo();
}

function closeUserInfoModal() {
    var userInfoModal = document.getElementById('userInfoModal');
    userInfoModal.style.display = 'none';
}

async function loadUserInfo() {
    try {
        const userInfo = await fetchUserInfo();
        document.getElementById('firstNameSpan').innerText = userInfo.firstName;
        document.getElementById('lastNameSpan').innerText = userInfo.lastName;
        document.getElementById('emailSpan').innerText = userInfo.email;
    } catch (error) {
        console.error('Fehler beim Laden der Benutzerinformationen:', error);
    }
}


async function fetchUserInfo() {
    const response = await fetch('/user/email', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    if (!response.ok) {
        throw new Error('Fehler beim Abrufen der Benutzerinformationen');
    }

    const userInfo = await response.json();
    return userInfo;
}

//############################ Chat-Handling ########################################################################################################


async function addChat() {
    var chatName = document.getElementById('chatNameInput').value;
    var password = document.getElementById('passwordInput').value;

    if (chatName.trim() === '') {
        alert('Chatname darf nicht leer sein');
        return;
    }
    var createdChat = await createChatFunction(chatName, password);

    // Aktualisiere die Chatliste
    loadUserChats();

    // Schließe das Modal
    closeCreateRoomModal();
}

// Globale Variable für die Liste der Chats
window.userChats = [];


function updateChatRooms(userChats) {
    const userRoomsContainer = document.querySelector('.user-rooms');

    userRoomsContainer.innerHTML = '';

    var chatName = document.getElementById('chat-header-text');
    const chatContainer = document.querySelector('.chat-container');

    userChats.forEach(chat => {
        const chatRoomElement = document.createElement('div');
        chatRoomElement.classList.add('chatroom-box');
        chatRoomElement.textContent = chat.name;
        chatRoomElement.setAttribute('chatId', chat.id);

        chatRoomElement.addEventListener('click', function () {
            chatName.textContent = chat.name + "            Chat-Id: " + chat.id + "     (Wird fürs Beitreten verwendet)";
            sessionStorage.removeItem('selectedChatId');
            sessionStorage.setItem('selectedChatId', chat.id);
            console.log("Chat selected");
            stopMessageChecking();
            chatContainer.style.display = "block";
            startMessageChecking(chat.id);
            loadAndShowMessages(chat.id);
        });

        userRoomsContainer.appendChild(chatRoomElement);
    });

    const addRoomBtn = document.createElement('div');
    addRoomBtn.classList.add('add-room-btn');
    addRoomBtn.textContent = '+';
    addRoomBtn.addEventListener('click', function () {
        openRoomModal();
    });
    userRoomsContainer.appendChild(addRoomBtn);
}

async function createChatFunction(chatName, password) {

    const response = await fetch('/chats', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: chatName,
            password: password,
            userId: sessionStorage.getItem('user_id')
        }),
    });

    if (!response.ok) {
        const errorMessage = await response.text();
        console.error('Fehler bei der Chat-Erstellung:', errorMessage);
    } else {
        const createdChat = await response.json();
        console.log('Chat erfolgreich erstellt:', createdChat);
        reloadPage();
        return createdChat;
    }
}

// Funktion zum Laden der Chaträume des Benutzers
async function loadUserChats() {
    try {
        await getCurrentUserId();
        const userChats = await fetchUserChats();
        updateChatRooms(userChats);
    } catch (error) {
        console.error('Fehler beim Laden der Chaträume:', error);
    }
}

// Funktion zum Abrufen der Chaträume des Benutzers
async function fetchUserChats() {
    userId = sessionStorage.getItem('user_id');

    try {
        const response = await fetch(`/chats/user/${userId}`);
        if (!response.ok) {
            throw new Error('Fehler beim Abrufen der Chaträume');
        }

        const userChats = await response.json();
        return userChats;
    } catch (error) {
        console.error('Fehler beim Abrufen der Chaträume:', error);
        console.log(userChats)
        return [];
    }
}

// Funktion zum Neu laden der Seite
function reloadPage() {
    location.reload();
}


// Funktion zum Laden und Anzeigen von Nachrichten
async function loadAndShowMessages(chatId) {
    try {
        const messages = await getAllMessagesByChatId(chatId);

        showMessages(messages);
    } catch (error) {
        console.error('Fehler beim Laden der Nachrichten:', error);
    }
}

// Funktion zum Anzeigen von Nachrichten im Chatfenster
async function showMessages(messages) {
    const chatMessagesContainer = document.querySelector('.chat-messages');

    chatMessagesContainer.innerHTML = '';

    getCurrentUserId();

    messages.forEach(message => {

        const messageElement = document.createElement('div');
        messageElement.classList.add('chat-message');
        const formattedDate = "" + message.timestamp[2] + "." + message.timestamp[1] + "." + message.timestamp[0] + " : " + message.timestamp[3] + "." + message.timestamp[4];

        if (message.userId == sessionStorage.getItem('user_id')) {
            messageElement.classList.add('current-user');
            messageElement.innerHTML = `<span class="message-sender">Du</span><br>${message.content}<br><span class="message-date">${formattedDate}</span>`;
        } else {
            messageElement.innerHTML = `<span class="message-sender">${message.userId}</span><br>${message.content}<br><span class="message-date">${formattedDate}</span>`;
        }

        chatMessagesContainer.appendChild(messageElement);
    });

    chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;
}

//######################################################### Messages-Abfragen ###########################################################

// Interval-ID, um das Interval später stoppen zu können
let messageCheckInterval;

// Starte das Überprüfungsintervall, wenn ein Chatraum ausgewählt wurde
function startMessageChecking(chatId) {
    console.log("intervall starten");
    // Überprüfe alle 5 Sekunden auf neue Nachrichten
    messageCheckInterval = setInterval(() => {
        checkForNewMessages(chatId);
    }, 5000); // 5000 Millisekunden = 5 Sekunden
}

// Stoppe das Überprüfungsintervall
function stopMessageChecking() {
    clearInterval(messageCheckInterval);
}

// Funktion zum Überprüfen neuer Nachrichten und Laden/Anzeigen
async function checkForNewMessages(chatId) {
    try {
        const hasNewMessages = await checkForNewMessagesFromServer(chatId);

        console.log("Checked for new Messages mit chat id: " + chatId + " Neue Nachrichten vorhanden? " + hasNewMessages);

        if (hasNewMessages) {
            await loadAndShowMessages(chatId);
        }
    } catch (error) {
        console.error('Fehler beim Überprüfen neuer Nachrichten:', error);
    }
}

async function checkForNewMessagesFromServer(chatId) {
    //Logik wird noch implementiert in der Zukunft
    return true;
}
