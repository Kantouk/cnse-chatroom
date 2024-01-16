// JavaScript

// Funktion zum Anmelden
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
