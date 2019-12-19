'use strict'; 
var tls = require('tls'); 
var fs = require('fs'); 

var options = { 
  key: fs.readFileSync('../java/SSL/dam.key'), 
  cert: fs.readFileSync('../java/SSL/micertificado.cer'), 
  rejectUnauthorized: false 
}; 
const PORT = 4444; 
const HOST = '127.0.0.1' 
var client = tls.connect(PORT, HOST, options, function() { 

    if (client.authorized) { 
    console.log("Connection authorized by a Certificate Authority.");
    } else { 
    console.log("Connection not authorized: " + client.authorizationError) 
    } 

client.write('Saludos soy tu cliente\n');
client.write('Tú quien eres áéíóú\n');
client.write('END\n');
}); 

client.on("data", function(response) { 
    console.log('Received: %s [it is %d bytes long]', 
    response.toString().replace(/(\n)/gm,""),
    response.length); 
 
    if (response == "BYE BYE") client.destroy();
}); 

client.on('close', function() { 
    console.log("Connection closed"); 
}); 

client.on('error', function(error) { 
    console.error(error); 
});
