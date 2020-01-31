package com.micahf.cameraservice

import io.socket.client.Socket
import uk.co.caprica.picam.PictureCaptureHandler

class SocketIOCaptureHandler(feederId: String, socket: Socket) extends  PictureCaptureHandler[Unit] {
  override def begin(): Unit = {}

  override def pictureData(data: Array[Byte]): Int = {
    println("pcitureData")
    val payload = CapturePayload(feederId, data)
    val json = payload.toJSON()
    println(json.toString())
    socket.emit("pictureCaptured", json)
    data.length
  }

  override def end(): Unit = {}

  override def result(): Unit = {}
}