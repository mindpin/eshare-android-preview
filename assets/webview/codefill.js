jQuery(function() {
    render_text_fills();

    setTimeout(function() {
        init_text_fills();
    }, 1)
})

function render_text_fills() {
    var $content = jQuery('.content');
    var html = $content.html()
    html = html.replace(/\[\%\]/g, "<codefill>_</codefill>")
    $content.html(html)
}

function init_text_fills() {
    jQuery('codefill').each(function(index, value) {
        var json = {};
        json.fid = index + 1;

        var bound = value.getBoundingClientRect();
        for(var key in bound) {
            json[key] = bound[key] * window.devicePixelRatio - 0.5;
        }

        window.TextFillBridge.add_field(JSON.stringify(json));
    })
}

window.setText = function (fid, text) {
    var fill = jQuery('codefill')[fid - 1];

    jQuery(fill).html(text);

    var json = {};
    json.fid = fid;

    var bound = fill.getBoundingClientRect();
    for(var key in bound) {
        json[key] = bound[key] * window.devicePixelRatio - 0.5;
    }

    window.TextFillBridge.update_codefill(JSON.stringify(json));
}


function make_blanks(text_length) {
    if (text_length == 0) return '_';

    var re = '';
    for (var i = 0; i < text_length; i++) {
        re += '_';
    }

    return re;
}