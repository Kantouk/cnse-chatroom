// JavaScript

// Funktion zum Abmelden
function logout() {
    // Extrahiere den Ursprung (Origin) der aktuellen Anwendung
    const currentOrigin = window.location.origin;

    stopMessageChecking();

    sessionStorage.removeItem('user_id');

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

    // Validierung der Eingaben (hier kannst du deine Validierung hinzufügen)

    // Beispielhaft: Prüfe, ob die Chat-ID nicht leer ist
    if (chatId.trim() === '') {
        alert('Chat-ID darf nicht leer sein');
        return;
    }

    // Hier kannst du die Chatbeitritts-Logik einfügen
    // Rufe eine Funktion auf, die den Benutzer dem Chat hinzufügt und anschließend das Modal schließt
    await joinChatFunction(chatId,password);

    // Schließe das Modal
    closeJoinRoomModal();
}

// Beispielhafte asynchrone Funktion für den Chatbeitritt (ersetze durch deine Logik)
async function joinChatFunction(chatId,password) {
    // Hier implementierst du die Logik zum Beitritt in einen Chat
    // Du kannst eine Fetch-Anfrage an den Server senden oder die benötigten Daten verarbeiten
    userId = sessionStorage.getItem('user_id');
    // Beispiel:
    const response = await fetch(`/chats/addParticipant/${chatId}/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify({password:password})
        // Weitere Optionen, falls benötigt
    });

    if (!response.ok) {
        const errorMessage = await response.text();
        console.error('Fehler beim Beitritt in den Chat:', errorMessage);
        // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
    } else {
        console.log('Erfolgreich dem Chat beigetreten');
        loadUserChats();
    }
}




// Beispielhafte asynchrone Funktion für die Chat-Erstellung (ersetze durch deine Logik)
async function createChatFunction(chatName, password) {
    // Hier implementierst du die Logik zur Chat-Erstellung
    // Du kannst eine Fetch-Anfrage an den Server senden oder die benötigten Daten verarbeiten

    // Beispiel:
    const response = await fetch('/create-chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: chatName,
            password: password,
            // Weitere Daten, die du benötigst
        }),
    });

    if (!response.ok) {
        const errorMessage = await response.text();
        console.error('Fehler bei der Chat-Erstellung:', errorMessage);
        // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
    } else {
        console.log('Chat erfolgreich erstellt');
        // Hier kannst du weitere Aktionen nach erfolgreicher Erstellung durchführen
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
                // Gib den Benutzernamen zurück
                
                sessionStorage.setItem('username',username);
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
                // Gib die BenutzerId zurück
                sessionStorage.removeItem('user_id');
                sessionStorage.setItem('user_id', user_id);
                return user_id;
            })
            .catch(error => {
                console.error('Fehler:', error);
            });
    }

    document.addEventListener('DOMContentLoaded', function () {

            // Füge einen Event-Listener für das Benutzericon hinzu
        document.getElementById('userIcon').addEventListener('click', function() {
            openUserInfoModal();
        });

        getCurrentUserId();
        
        // Rufe die Funktion zum Laden der Chaträume auf
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

    function sendMessage(){
        // Hole den Text aus dem Eingabefeld
        getCurrentUserId();
            
        // getCurrentUsername();

        var messageText = document.querySelector('.chat-input input').value;

        // Überprüfe, ob die Nachricht nicht leer ist
         if (messageText.trim() !== '') {
        // Erstelle das Datenobjekt für die Nachricht
            var messageData = {
            content: messageText,
            chat_id: sessionStorage.getItem('selectedChatId'),
            user_id: sessionStorage.getItem('user_id'),
            // Weitere notwendige Daten, abhängig von deiner Anforderung
        };

        console.log(sessionStorage.getItem('user_id'));

        // Sende die Nachricht an den MessageController
        sendMessageToController(messageData);

        // Leere das Eingabefeld
        document.querySelector('.chat-input input').value = '';
}

    }

    // Funktion zum Senden der Nachricht an den MessageController
    function sendMessageToController(messageData) {
        // Definiere die URL der sendMessage-Methode im MessageController
        var sendMessageURL = '/messages';

        // Sende die Nachricht mit AJAX (oder einer anderen Methode deiner Wahl)
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
                // Verarbeite die Antwort des Servers, falls notwendig
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
                // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
                // z.B. Fehler an die Aufruferfunktion weitergeben oder leeres Array zurückgeben
                return [];
            }
    }




//############################################ UserInfo


    // Ergänzte JavaScript-Funktionen für das Benutzerinfo-Modal
    function openUserInfoModal() {
        var userInfoModal = document.getElementById('userInfoModal');
        userInfoModal.style.display = 'flex';
        console.log("User-Settings");
        // Hier kannst du die Benutzerinformationen laden und in das Modal einfügen
        loadUserInfo();
    }

    function closeUserInfoModal() {
        var userInfoModal = document.getElementById('userInfoModal');
        userInfoModal.style.display = 'none';
    }

    async function loadUserInfo() {
        try {
            // Hier kannst du die Benutzerinformationen vom Server abrufen
            const userInfo = await fetchUserInfo();

            // Beispielhaft: Setze die Benutzerinformationen in die Modal-Elemente
            document.getElementById('firstNameSpan').innerText = userInfo.firstName;
            document.getElementById('lastNameSpan').innerText = userInfo.lastName;
            document.getElementById('emailSpan').innerText = userInfo.email;
        } catch (error) {
            console.error('Fehler beim Laden der Benutzerinformationen:', error);
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
        }
    }

    // Beispielhafte asynchrone Funktion zum Abrufen der Benutzerinformationen (ersetze durch deine Logik)
    async function fetchUserInfo() {
        // Hier implementierst du die Logik zum Abrufen der Benutzerinformationen
        // Du kannst eine Fetch-Anfrage an den Server senden oder die benötigten Daten verarbeiten

        // Beispiel:
        const response = await fetch('/user/email', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            // Weitere Optionen, falls benötigt
        });
        if (!response.ok) {
            throw new Error('Fehler beim Abrufen der Benutzerinformationen');
        }

        const userInfo = await response.json();
        return userInfo;
    }

    //############################ Chat-Handling ########################################################################################################
 

    // Aktion bei erfolgreichem Chat-Erstellen
    async function addChat() {
        var chatName = document.getElementById('chatNameInput').value;
        var password = document.getElementById('passwordInput').value;

        // Beispielhaft: Prüfe, ob der Chatname nicht leer ist
        if (chatName.trim() === '') {
            alert('Chatname darf nicht leer sein');
            return;
        }

        // Hier kannst du die Chat-Erstellungs-Logik einfügen
        // Rufe eine Funktion auf, die den Chat erstellt und anschließend das Modal schließt
        var createdChat = await createChatFunction(chatName, password);

        // Aktualisiere die Chatliste
        loadUserChats();

        // Schließe das Modal
        closeCreateRoomModal();
    }

    // Globale Variable für die Liste der Chats
    window.userChats = [];

    // Funktion zum Aktualisieren der Anzeige der Chaträume
    function updateChatRooms(userChats) {
        const userRoomsContainer = document.querySelector('.user-rooms');

        // Leere den Inhalt des Containers
        userRoomsContainer.innerHTML = '';

        var chatName = document.getElementById('chat-header-text');
        const chatContainer = document.querySelector('.chat-container');

        // Durchlaufe die Chaträume und füge sie dem Container hinzu
        userChats.forEach(chat => {
            const chatRoomElement = document.createElement('div');
            chatRoomElement.classList.add('chatroom-box');
            chatRoomElement.textContent = chat.name; // Hier kannst du auch die ID hinzufügen oder andere Informationen
            chatRoomElement.setAttribute('chatId', chat.id);

            // Füge einen Event-Listener für den Klick auf den Chatraum hinzu
            chatRoomElement.addEventListener('click', function () {
                // Hier kannst du weitere Aktionen für den Klick auf den Chatraum durchführen
                // Zum Beispiel den ausgewählten Chatraum setzen und die Nachrichten laden
                chatName.textContent = chat.name + "            Chat-Id: " + chat.id + "     (Wird fürs Beitreten verwendet)";
                sessionStorage.removeItem('selectedChatId');
                sessionStorage.setItem('selectedChatId', chat.id);
                console.log("Chat selected");
                stopMessageChecking();
                chatContainer.style.display="block";
                startMessageChecking(chat.id);
                loadAndShowMessages(chat.id);
            });

            userRoomsContainer.appendChild(chatRoomElement);
        });

        // Füge den Button zum Hinzufügen eines neuen Chatraums hinzu
        const addRoomBtn = document.createElement('div');
        addRoomBtn.classList.add('add-room-btn');
        addRoomBtn.textContent = '+';
        addRoomBtn.addEventListener('click', function () {
            openRoomModal();
        });
        userRoomsContainer.appendChild(addRoomBtn);
    }

    // Beispielhafte asynchrone Funktion für die Chat-Erstellung (ersetze durch deine Logik)
    async function createChatFunction(chatName, password) {
        // ... (wie bisherige Logik)
        // Beispiel:
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
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
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
            // Rufe die Chaträume des Benutzers vom Server ab
            const userChats = await fetchUserChats();

            // Aktualisiere die Anzeige der Chaträume
            updateChatRooms(userChats);
        } catch (error) {
            console.error('Fehler beim Laden der Chaträume:', error);
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
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
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
            return [];
        }
    }

    // Funktion zum Neu laden der Seite
    function reloadPage() {
        location.reload();
    }


    // Neue Funktion zum Laden und Anzeigen von Nachrichten
    async function loadAndShowMessages(chatId) {
        try {
            // Lade die Nachrichten für den Chatraum
            const messages = await getAllMessagesByChatId(chatId);

            // Zeige die Nachrichten im Chatfenster an
            showMessages(messages);
        } catch (error) {
            console.error('Fehler beim Laden der Nachrichten:', error);
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
        }
    }

    // Funktion zum Anzeigen von Nachrichten im Chatfenster
    async function showMessages(messages) {
        const chatMessagesContainer = document.querySelector('.chat-messages');

        // Leere den Inhalt des Chatfensters
        chatMessagesContainer.innerHTML = '';

        getCurrentUserId();

        // Durchlaufe die Nachrichten und füge sie dem Chatfenster hinzu
        messages.forEach(message => {

            const messageElement = document.createElement('div');
            messageElement.classList.add('chat-message');
            const formattedDate = "" + message.timestamp[2]+"."+message.timestamp[1]+"."+message.timestamp[0]+ " : " +message.timestamp[3]+"."+message.timestamp[4];

            // Beispiel: Anzeige des Nachrichteninhalts und Senders
            if (message.userId == sessionStorage.getItem('user_id')) {
                // Nachrichten des aktuellen Benutzers anzeigen
                messageElement.classList.add('current-user');
                messageElement.innerHTML = `<span class="message-sender">Du</span><br>${message.content}<br><span class="message-date">${formattedDate}</span>`;
            } else {
                // Nachrichten von anderen Benutzern anzeigen
                messageElement.innerHTML = `<span class="message-sender">${message.userId}</span><br>${message.content}<br><span class="message-date">${formattedDate}</span>`;
            }

            chatMessagesContainer.appendChild(messageElement);
        });
                

        // Scrolle zum unteren Ende des Chatfensters, um die neuesten Nachrichten anzuzeigen
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
            // Hier implementierst du die Logik zum Überprüfen neuer Nachrichten
            // Du könntest beispielsweise eine Funktion aufrufen, die vom Server prüft, ob neue Nachrichten vorhanden sind.

            // Beispiel: Prüfe auf neue Nachrichten
            const hasNewMessages = await checkForNewMessagesFromServer(chatId);

            console.log("Checked for new Messages mit chat id: "+ chatId+ " Neue Nachrichten vorhanden? "+hasNewMessages);

            // Wenn neue Nachrichten vorhanden sind, lade und zeige sie an
            if (hasNewMessages) {
                await loadAndShowMessages(chatId);
            }
        } catch (error) {
            console.error('Fehler beim Überprüfen neuer Nachrichten:', error);
            // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
        }
    }

    // Funktion, die vom Server aufgerufen wird, um auf neue Nachrichten zu überprüfen
    async function checkForNewMessagesFromServer(chatId) {
        // Hier implementierst du die Logik, um vom Server zu überprüfen, ob neue Nachrichten vorhanden sind.
        // Diese Funktion könnte beispielsweise eine Anfrage an den Server senden.

        // Rückgabe kann ein Boolean-Wert sein, der angibt, ob neue Nachrichten vorhanden sind.
        // Beispiel:
        return true; // In diesem Beispiel wird immer true zurückgegeben. Du musst dies entsprechend deiner Server-Logik anpassen.
    }
