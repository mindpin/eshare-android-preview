window.onload = function() {
    renderCodefills();


    // 延迟获取codefill坐标位置
    setTimeout(function(){
        getCodefills();
    }, 1);
}

window.setText = function setText(fid, text) {
    var fields = document.getElementsByTagName("codefill"),
        foundField;

    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];

        if (field.fid === fid) {
            foundField = field;
            foundField.textContent = text;
            break;
        }
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

function getCodefills() {
    var fields = document.getElementsByTagName("codefill");

    for (var i = 0; i < fields.length; i++) {
        if (window.CodefillBridge && window.CodefillBridge.add_field) {
            var field = fields[i]
              , fid   = i + 1
              , from  = fields[i].getBoundingClientRect()
              , to    = {};

            to.fid = field.fid = fid;

            for (var key in from) {
                if (from.hasOwnProperty(key) && window.devicePixelRatio) {
                    to[key] = Math.round(from[key] * window.devicePixelRatio);
                }
            }

            window.CodefillBridge.add_field(JSON.stringify(to));
        }
    }
}