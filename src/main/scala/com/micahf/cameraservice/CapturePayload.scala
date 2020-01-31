package com.micahf.cameraservice

import java.util.Date

import org.json.JSONObject

case class CapturePayload(feederId: String, image: Array[Byte], timestamp: Date = new Date()) {
  def toJSON(): JSONObject = {
    val json = new JSONObject()
      json.put("feederId",feederId)
      json.put("image",b64encode(image))
      json.put("timestamp",timestamp.getTime().toString)
    json
  }

  def b64encode(bytes: Array[Byte]): String = {
    java.util.Base64.getEncoder.encodeToString(bytes)
  }
}
