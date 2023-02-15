package com.example.fcm

import android.app.Activity
import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class FirebaseNotificationSender(private var fcmServerKey: String? = null,
                                 private var userFcmToken: String? = null,
                                 private var title: String? = null,
                                 private var body: String? = null,
                                 private var mContext: Context? = null,
                                 private var mActivity: Activity? = null,
                                 private var requestQueue: RequestQueue? = null,
                                 private val postUrl : String = "https://fcm.googleapis.com/fcm/send") {

    fun sendNotifications() {
        fcmServerKey = mContext?.getString(R.string.Key)
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try {
            mainObj.put("to", userFcmToken)
            val notiObject = JSONObject()
            notiObject.put("title", title)
            notiObject.put("body", body)
            notiObject.put("icon", R.mipmap.ic_launcher_round)
            mainObj.put("notification", notiObject)
            val request: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                postUrl,
                mainObj,
                Response.Listener {
                    // code run is got response
                },
                Response.ErrorListener {
                    // code run is got error
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val header: MutableMap<String, String> = HashMap()
                    header["content-type"] = "application/json"
                    header["authorization"] = "key=$fcmServerKey"
                    return header
                }
            }
            requestQueue!!.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}