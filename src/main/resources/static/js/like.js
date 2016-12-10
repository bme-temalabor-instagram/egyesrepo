var webappAddress = "http://localhost:8080/";

function toggle(el) {
    if (el.className == "notlike") {
        fillHeart(el);
        like(el.id);
    }
    else if (el.className == "like") {
        emptyHeart(el);
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
            if (response === "ok") {
                var likesSpan = document.getElementById("txt"+photo_id);
                console.log(likesSpan.innerHTML);
                var numOfLikes = parseInt(likesSpan.innerHTML, 10);
                numOfLikes++;
                likesSpan.innerHTML = numOfLikes;
            }
        }
    );
}

function unlike(photo_id) {
    var params = "&photo_id=" + photo_id;

    sendPostRequest(webappAddress + "unlike",
        params,
        function (xhr) {
            var response = xhr.responseText;
            if (response === "ok") {
                var likesSpan = document.getElementById("txt"+photo_id);
                console.log(likesSpan.innerHTML);
                var numOfLikes = parseInt(likesSpan.innerHTML, 10);
                numOfLikes--;
                likesSpan.innerHTML = numOfLikes;
            }
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
    el.src = 'images/like.png';
    el.className = "like";
}

function emptyHeart(el) {
    el.src = 'images/notlike.png';
    el.className = "notlike";
}