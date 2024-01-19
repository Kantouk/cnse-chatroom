// JavaScript

// Funktion zum Abmelden
function logout() {
    // Extrahiere den Ursprung (Origin) der aktuellen Anwendung
    const currentOrigin = window.location.origin;

    // Baue den vollständigen Pfad zur Logout-Route
    const logoutPath = '/logout';

    // Konstruiere die vollständige URL
    const logoutURL = currentOrigin + logoutPath;

    // Führe den Logout durch (z.B., leite zur Logout-URL weiter)
    window.location.href = logoutURL;
}

// Funktion zum Öffnen des Modals
function openCreateRoomModal() {
    var modal = document.getElementById('roomModal');
    modal.style.display = 'flex'; // Zeige das Modal an
}

// Funktion zum Schließen des Modals
function closeCreateRoomModal() {
    var modal = document.getElementById('roomModal');
    modal.style.display = 'none'; // Verstecke das Modal
}

// Aktion bei Klick auf "Einen neuen Raum erstellen" Button
document.getElementById('createRoomBtn').addEventListener('click', function () {
    alert('Neuen Raum erstellen'); // Hier kannst du die gewünschte Aktion ausführen
    closeCreateRoomModal();
});

// Aktion bei Klick auf "Einem Raum beitreten" Button
document.getElementById('joinRoomBtn').addEventListener('click', function () {
    alert('Einem Raum beitreten'); // Hier kannst du die gewünschte Aktion ausführen
    closeCreateRoomModal();
});

// Schließe das Modal, wenn der Benutzer außerhalb des Modals klickt
window.addEventListener('click', function (event) {
    var modal = document.getElementById('roomModal');
    var modalContent = document.getElementById('modalContent');

    if (event.target === modal) {
        closeCreateRoomModal();
    }
});

// Funktion zum Öffnen der Benutzereinstellungen
function openUserSettings() {
    // Hier füge den Code hinzu, um zu den Benutzereinstellungen zu navigieren oder anzuzeigen
    // Beispiel: window.location.href = 'benutzereinstellungen.html';
    // Ersetze 'benutzereinstellungen.html' durch den tatsächlichen Pfad zu deinen Benutzereinstellungen.
}


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
            sessionStorage.setItem('user_id', user_id);
            return user_id;
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}

document.addEventListener('DOMContentLoaded', function () {
    // Aktion bei Klick auf "Senden" Button
    document.querySelector('.send-btn').addEventListener('click', function () {
        // Hole den Text aus dem Eingabefeld
        getCurrentUserId();
        // getCurrentUsername();

        var messageText = document.querySelector('.chat-input input').value;

        // Überprüfe, ob die Nachricht nicht leer ist
        if (messageText.trim() !== '') {
            // Erstelle das Datenobjekt für die Nachricht
            var messageData = {
                content: messageText,
                chat_id: 1,
                user_id: sessionStorage.getItem('user_id'),
                // Weitere notwendige Daten, abhängig von deiner Anforderung
            };

            console.log(sessionStorage.getItem('user_id'));

            // Sende die Nachricht an den MessageController
            sendMessageToController(messageData);

            // Leere das Eingabefeld
            document.querySelector('.chat-input input').value = '';
        }
    });
});

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
        })
        .catch(error => {
            console.error('Fehler:', error);
        });
}
