var webappAddress = "http://localhost:8080/";

function toggle(el) {
    if (el.className == "notlike") {
        like(el);
    }
    else if (el.className == "like") {
        unlike(el);
    }

    return false;
}

function like(el) {
    var params = "&photo_id=" + el.id;

    sendPostRequest(webappAddress + "like",
        params,
        function (xhr) {
            var response = xhr.responseText;
            if (response === "ok") {
                fillHeart(el);
                var likesSpan = document.getElementById("txt" + el.id);
                var numOfLikes = parseInt(likesSpan.innerHTML, 10);
                numOfLikes++;
                likesSpan.innerHTML = numOfLikes.toString();
            }
        }
    );
}

function unlike(el) {
    var params = "&photo_id=" + el.id;

    sendPostRequest(webappAddress + "unlike",
        params,
        function (xhr) {
            var response = xhr.responseText;
            if (response === "ok") {
                emptyHeart(el);
                var likesSpan = document.getElementById("txt" + el.id);
                var numOfLikes = parseInt(likesSpan.innerHTML, 10);
                numOfLikes--;
                likesSpan.innerHTML = numOfLikes.toString();
            }
        }
    );
}

function loadLikes(el) {
    var params = "&photo_id=" + el.id;
    var photo_id = el.id;

    sendPostRequest(webappAddress + "load_likes",
        params,
        function (xhr) {
            var response = xhr.responseText;
            var listOfLikers = document.getElementById("listOfLikers");
            while (listOfLikers.firstChild) {
                listOfLikers.removeChild(listOfLikers.firstChild);
            }
            listOfLikers.innerHTML = response;
        }
    );
}

function setLike(el) {
    var params = "&photo_id=" + el.id;

    sendPostRequest(webappAddress + "is_like",
        params,
        function (xhr) {
            var response = xhr.responseText;
            if (response === "true") {
                fillHeart(el);
            } else {
                emptyHeart(el);
            }
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


function fillHeart(el) {
    if (el.className !== "like") {
        el.src = 'images/like.png';
        el.className = "like";
    }
}

function emptyHeart(el) {
    if (el.className !== "notlike") {
        el.src = 'images/notlike.png';
        el.className = "notlike";
    }
}