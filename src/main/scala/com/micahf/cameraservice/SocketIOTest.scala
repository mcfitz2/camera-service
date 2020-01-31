package com.micahf.cameraservice

import java.io.File
import java.nio.file.{Files, Paths}

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import uk.co.caprica.picam.{Camera, CameraConfiguration, PictureCaptureHandler}
import uk.co.caprica.picam.enums.Encoding
import uk.co.caprica.picam.CameraConfiguration.cameraConfiguration

class MockCamera(config: CameraConfiguration)  {
  def takePicture(handler: PictureCaptureHandler[Unit]): Unit = {
    handler.pictureData(Files.readAllBytes(Paths.get("image.png")))
    handler.result
  }
}
object SocketIOTest {
  val feederId: String = "feeder-1"
  val config: CameraConfiguration = cameraConfiguration
    .width(1920)
    .height(1080)
    .encoding(Encoding.JPEG)
    .quality(85)
  def main(args: Array[String]): Unit = {
    val socket = IO.socket("http://localhost:3000")
    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener {
      override def call(args: Object*): Unit = {
        //socket.disconnect
      }
    }).on("capture", new Emitter.Listener {
      override def call(args: Object*): Unit = {
        println("capturing picture")
        val camera = new MockCamera(config)
        camera.takePicture(new SocketIOCaptureHandler(feederId, socket))
        socket.disconnect
      }
    })
    socket.connect

  }
}
