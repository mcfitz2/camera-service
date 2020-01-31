var app = require('express')();
var http = require('http').createServer(app);
var io = require('socket.io')(http);
var fs = require("fs")
io.on('connection', function(socket){
  console.log('a user connected');
  socket.emit("capture")
  socket.on("pictureCaptured", (msg) => {
    console.log(msg)
    let buff = new Buffer(msg.image, 'base64');
    fs.writeFileSync(msg.timestamp + '.png', buff)
  })
});

http.listen(3000, function(){
  console.log('listening on *:3000');
});;