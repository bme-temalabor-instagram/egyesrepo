var webappAddress = "http://localhost:8080/";

function toggle(el) {
    if (el.className == "notlike") {
        el.src = 'images/like.png';
        el.className = "like";
        like(el.id);
    }
    else if (el.className == "like") {
        el.src = 'images/notlike.png';
        el.className = "notlike";
        unlike(el.id);
    }

    return false;
}

function like(photo_id) {
    var params = "&photo_id=" + photo_id;

    sendPostRequest(webappAddress + "like",
        params,
        function (xhr) {
            var response = xhr.responseText;
            console.log(response);
        }
    );
}

function unlike(photo_id) {
    var params = "&photo_id=" + photo_id;

    sendPostRequest(webappAddress + "unlike",
        params,
        function (xhr) {
            var response = xhr.responseText;
            console.log(response);
        }
    );
}

function loadLikes(el) {
    var photo_id = el.id;
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