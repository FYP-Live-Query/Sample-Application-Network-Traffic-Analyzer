var evtSource = new EventSource("http://localhost:8080/sse");

evtSource.onmessage = function(e) {
    var newElement = document.createElement("li");
    var eventList = document.getElementById('list');
    newElement.innerHTML = e.data;
    eventList.appendChild(newElement);
};
