package rhrk.com.globalhack7

import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*

private val url = "https://translate.yandex.net/api/v1.5/tr.json/translate"
private val key = "trnsl.1.1.20181013T041636Z.51c8c0788939025f.536b5a9d54fda734f42f1a53167bfcee87e5907b"

class Translate() : AsyncTask<Array<String>, Void, String>() {
    override fun doInBackground(vararg p0: Array<String>?): String {
        System.out.println(Arrays.toString(p0))
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("${url}?key=${key}&text=${p0[0]!![0]}&lang=${p0[0]!![1]}")
                .build()
        val response = client.newCall(request).execute()
        val data = JSONObject(response.body()!!.string())
        System.out.println(data.getJSONArray("text"))
        return (data.getJSONArray("text")[0]).toString()

    }

}
