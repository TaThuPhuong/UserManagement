package com.ttp.usermanagement.common.exstension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <R> CoroutineScope.launch(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit,
) = launch {
    onPreExecute()
    val result =
        withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
            doInBackground()
        }
    onPostExecute(result)
}