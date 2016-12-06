var webappAddress = "http://localhost:8080/";

function toggle(el) {
    if (el.className != "like") {
        el.src = 'images/like.png';
        el.className = "like";
    }
    else if (el.className == "like") {
        el.src = 'images/notlike.png';
        el.className = "notlike";
    }

    return false;
}

function actualizeData(el) {
    var photo_id = el.id;
    if (photo_id.charAt(0) == '-') {
        console.log("0 likes");
        return;
    }

    loadLikes(photo_id);
}

function loadLikes(photo_id) {
    var params = "&photo_id=" + photo_id;

    sendPostRequest(webappAddress + "load_likes",
        params,
        function (xhr) {
            var response = xhr.responseText;
            console.log(response);
        }
    );
}

function sendPostRequest(address, params, responseHandler) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", address);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.responseType = "text";
    xhr.send(params);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            responseHandler(xhr);
        }
    };
}