var net = require('net');
//var iconv = require('iconv-lite');

var client = new net.Socket();

client.connect(4444, '127.0.0.1', function () {

    // https://github.com/nodejs/node/blob/master/lib/buffer.js
    // ascii  base64 hex ucs2/ucs-2/utf16le/utf-16le  utf8/utf-8  binary/latin1

    //client.setEncoding('binary');
    console.log('Connected');
    client.write('Saludos soy tu cliente\n');
    client.write('Tú quien eres áéí\n');
    client.write('END\n');
});

//https://stackoverflow.com/questions/14551608/list-of-encodings-that-node-js-supports
client.on('data', function (response) {
    /*
    ["437", "737", "775", "850", "852", "855",
        "856", "857", "858", "860", "861", "862", "863", "864",
        "865", "866", "869", "874", "922", "1046", "1124",
        "1125", "1129", "1133", "1161", "1162", "1163", "1250",
        "1251", "1252", "1253", "1254", "1255", "1256", "1257",
        "1258", "28591", "28592", "28593", "28594", "28595",
        "28596", "28597", "28598", "28599", "28600", "28601",
        "28603", "28604", "28605", "28606", "macintosh", "ibm860",

    ].forEach(charset => {
        var output = iconv.encode(response, charset);
        //console.log(charset + ": " + output);
    });

*/
    console.log('Received: ' + response);

    if (response == "BYE BYE") client.destroy();
});

client.on('close', function () {
    console.log('Connection closed');
});