package com.example.startup

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun GetWeather (view: View) {
        var city = etCityName.text.toString()
        val url = "https://muslimsalat.com/"+city+".json?key=38d4a54de75776fb76cfb2d8c5eb13c7"
        MyAsyncTask().execute(url)
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //sebelum task bekerja
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL(params[0])
                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000

                var inString = ConvertStreamToString(urlConnect.inputStream)


                publishProgress(inString)
            } catch (ex: Exception) {
            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var JSONObject:JSONObject = JSONObject(values[0]);
                var city: String = JSONObject.getString("query")
                var weather = JSONObject.getJSONObject("today_weather")
                var temperature = weather.getString("temperature")

                tvWeather.text = "suhu di" + city + " saat ini " + temperature + "°"
                println(temperature)
            } catch (ex:Exception) {

            }
        }

        override fun onPostExecute(result: String?) {
           // setelah selesai
        }

    }

    fun ConvertStreamToString(inputStream:InputStream):String{
        val buffereReader = BufferedReader(InputStreamReader(inputStream))
        var line:String
        var allString:String = ""

        try {
           do {
               line = buffereReader.readLine()
//               println(line)
               if(line != null) {
                   allString += line
               }
           } while (line != null)
            inputStream.close()
        } catch (ex:Exception) {

        }

        return allString

    }
}
