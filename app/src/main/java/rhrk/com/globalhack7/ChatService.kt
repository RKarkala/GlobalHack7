//package rhrk.com.globalhack7
//
//import android.os.AsyncTask
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import org.json.JSONObject
//import java.util.*
//
//private val url = "https://api2.scaledrone.com/7LCPo3EPZ708kdZm/observable-room/publish"
//private val key = "prf187gXg0IPt5fEKCcH8sfKJ6lgwBxm"
//
//class chatService() : AsyncTask<Array<String>, Void, String>() {
//    override fun doInBackground(vararg p0: Array<String>?): String {
//        System.out.println(Arrays.toString(p0))
//        val client = OkHttpClient()
//        val request = Request.Builder()
//                .url("${url}?key=${key}&text=${p0[0]!![0]}&lang=${p0[0]!![1]}")
//                .build()
//        val response = client.newCall(request).execute()
//        val data = JSONObject(response.body()!!.string())
//        System.out.println(data.getJSONArray("text")[0])
//        return data.getJSONArray("text")[0].toString()
//
//    }
//
//}
