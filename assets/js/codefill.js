window.onload = function() {
    renderCodefills();


    // 延迟获取codefill坐标位置
    setTimeout(function(){
        getCodefills();
    }, 1);
}

function makeBlanks(num) {
    var result = "  ";

    if (num === 0) return result;

    for (var i = 0; i < num; i++) {
        result += " ";
    }

    return result;
}

window.setText = function setText(fid, blankNum) {
    var fields = document.getElementsByTagName("codefill")
      , foundField;

    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];

        if (field.fid === fid) {
            foundField = field;
            foundField.textContent = makeBlanks(blankNum);
        }

        window.CodefillBridge.update_codefill(getCodefillFrom(field));
    }
}

function renderCodefills() {
    var regexp   = /\[\%\]/g
      , fieldEl  = document.createElement("codefill")
      , snippets = document.getElementsByTagName("code");

    fieldEl.textContent = "  ";

    for (var i = 0; i < snippets.length; i++) {
        var snippet    = snippets[i]
          , oldContent = snippet.innerHTML
          , newContent = oldContent.replace(regexp, fieldEl.outerHTML);

        snippet.innerHTML = newContent;
    }
}

function getCodefillFrom(field) {
    var from  = field.getBoundingClientRect()
      , to    = {};

    to.fid = field.fid;

    for (var key in from) {
        if (from.hasOwnProperty(key) && window.devicePixelRatio) {
            to[key] = Math.round(from[key] * window.devicePixelRatio);
        }
    }

    return JSON.stringify(to);
}

function getCodefills() {
    var fields = document.getElementsByTagName("codefill");

    for (var i = 0; i < fields.length; i++) {
        fields[i].fid = i + 1;

        if (window.CodefillBridge) {
            window.CodefillBridge.add_field(getCodefillFrom(fields[i]));
        }
    }
}