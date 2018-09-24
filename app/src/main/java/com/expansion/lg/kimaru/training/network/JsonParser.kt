package com.expansion.lg.kimaru.training.network

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

import android.util.Log

// constructor
class JsonParser {

    fun getJSONFromUrl(urlString: String): String {

        // Making HTTP request

        try {
            Log.d("TREMAP", urlString)
            Log.d("TREMAP", "===================================")
            Log.d("TREMAP", "GETTING DATA")
            Log.d("TREMAP", "===================================")
            val url = URL(urlString)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            val `in` = BufferedInputStream(httpURLConnection.inputStream)

            // Read the BufferedInputStream
            val r = BufferedReader(InputStreamReader(`in`))
            val sb = StringBuilder()
            var line: String
            while ((line = r.readLine()) != null) {
                sb.append(line)
            }
            json = sb.toString()
            httpURLConnection.disconnect()
        } catch (e: MalformedURLException) {
            Log.d("Tremap", "MALFORMED -- : " + e.message)
        } catch (e: IOException) {
            Log.d("Tremap", "IO ERROR -- : " + e.message)
        } finally {

        }

        return json

    }

    companion object {

        internal var json = ""
    }
}