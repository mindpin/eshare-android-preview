document.addEventListener("DOMContentLoaded", function() {
    function renderCodefills() {
        var regexp   = /\[\%\]/g
          , fieldEl  = document.createElement("codefill")
          , snippets = document.getElementsByTagName("code");

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
            if (window.CodefillBridge && window.CodefillBridge.addField) {
                var from = fields[i].getBoundingClientRect()
                  , to   = {};

                for (var key in from) {
                    if (from.hasOwnProperty(key) && window.devicePixelRatio) {
                        to[key] = Math.round(from[key] * window.devicePixelRatio);
                    }
                }

                window.CodefillBridge.addField(JSON.stringify(to));
            }
        }
    }

    renderCodefills();
    getCodefills();
});

window.onload = function() {
    window.hehe = 1;
    window.realHeight = document.documentElement.scrollHeight;
    window.realWidth = document.documentElement.scrollWidth;
}