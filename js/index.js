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
    console.log('Received: ' + response);

    if (response == "BYE BYE") client.destroy();
});

client.on('close', function () {
    console.log('Connection closed');
});