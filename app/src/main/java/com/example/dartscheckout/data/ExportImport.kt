package com.example.dartscheckout.data

import android.content.Context
import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject

fun checkoutsToJson(checkouts: Map<Int, List<CheckoutVariant>>): String {
    val root = JSONObject()
    root.put("version", 1)
    val map = JSONObject()
    checkouts.toSortedMap().forEach { (num, variants) ->
        val varArr = JSONArray()
        variants.forEach { v ->
            val vObj = JSONObject()
            vObj.put("label", v.label)
            val throwsArr = JSONArray()
            v.throws.forEach { throwsArr.put(it) }
            vObj.put("throws", throwsArr)
            varArr.put(vObj)
        }
        map.put(num.toString(), varArr)
    }
    root.put("checkouts", map)
    return root.toString(2)
}

fun jsonToCheckouts(json: String): Map<Int, List<CheckoutVariant>>? {
    return try {
        val root = JSONObject(json)
        val map = root.getJSONObject("checkouts")
        val result = mutableMapOf<Int, List<CheckoutVariant>>()
        val keys = map.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val num = key.toIntOrNull() ?: continue
            val varArr = map.getJSONArray(key)
            val variants = mutableListOf<CheckoutVariant>()
            for (i in 0 until varArr.length()) {
                val vObj = varArr.getJSONObject(i)
                val label = vObj.getString("label")
                val throwsArr = vObj.getJSONArray("throws")
                val throws = mutableListOf<String>()
                for (j in 0 until throwsArr.length()) {
                    throws.add(throwsArr.getString(j))
                }
                variants.add(CheckoutVariant(label = label, throws = throws))
            }
            result[num] = variants
        }
        result
    } catch (e: Exception) {
        null
    }
}

fun writeTextToUri(context: Context, uri: Uri, text: String) {
    context.contentResolver.openOutputStream(uri)?.use { os ->
        os.write(text.toByteArray(Charsets.UTF_8))
    }
}

fun readTextFromUri(context: Context, uri: Uri): String? =
    context.contentResolver.openInputStream(uri)?.use { input ->
        input.readBytes().toString(Charsets.UTF_8)
    }
