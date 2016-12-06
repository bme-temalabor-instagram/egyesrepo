function toggle(el){
    if(el.className!="like")
    {
        el.src='images/like.png';
        el.className="like";
    }
    else if(el.className=="like")
    {
        el.src='images/notlike.png';
        el.className="notlike";
    }

    return false;
}

function actualizeData(el) {
    var photo_id = el.id;
    if (photo_id == "") {
        console.log("0 likes");
        return;
    }

    var params = "&photo_id=" + photo_id;
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "http://localhost:8080/load_likes");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.responseType="text";
    xhr.send(params);
    xhr.onreadystatechange = function () {
        //todo itt kell nezni hogy lajkoltunk-e
        if (xhr.readyState === 4) {
            var response = xhr.responseText;
            console.log(response);
        }
    }
}