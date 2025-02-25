package expo.modules.updates.statemachine

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import org.json.JSONObject

/**
The state machine context, with information intended to be consumed by application JS code.
 */
data class UpdatesStateContext(
  private val isUpdateAvailable: Boolean = false,
  private val isUpdatePending: Boolean = false,
  private val isRollback: Boolean = false,
  private val isChecking: Boolean = false,
  private val isDownloading: Boolean = false,
  private val isRestarting: Boolean = false,
  private val latestManifest: JSONObject? = null,
  private val downloadedManifest: JSONObject? = null,
  private val checkError: UpdatesStateError? = null,
  private val downloadError: UpdatesStateError? = null
) {

  val json: Map<String, Any>
    get() {
      val map: MutableMap<String, Any> = mutableMapOf(
        "isUpdateAvailable" to isUpdateAvailable,
        "isUpdatePending" to isUpdatePending,
        "isRollback" to isRollback,
        "isChecking" to isChecking,
        "isDownloading" to isDownloading,
        "isRestarting" to isRestarting
      )
      if (latestManifest != null) {
        map["latestManifest"] = latestManifest
      }
      if (downloadedManifest != null) {
        map["downloadedManifest"] = downloadedManifest
      }
      if (checkError != null) {
        map["checkError"] = checkError.json
      }
      if (downloadError != null) {
        map["downloadError"] = downloadError.json
      }
      return map
    }

  /**
   * Creates a WritableMap to be sent to JS on a state change.
   */
  val writableMap: WritableMap
    get() {
      val contextMap = Arguments.createMap()
      contextMap.putBoolean("isUpdateAvailable", isUpdateAvailable)
      contextMap.putBoolean("isUpdatePending", isUpdatePending)
      contextMap.putBoolean("isRollback", isRollback)
      contextMap.putBoolean("isChecking", isChecking)
      contextMap.putBoolean("isDownloading", isDownloading)
      contextMap.putBoolean("isRestarting", isRestarting)
      if (latestManifest != null) {
        contextMap.putString("latestManifestString", latestManifest.toString())
      }
      if (downloadedManifest != null) {
        contextMap.putString("downloadedManifestString", downloadedManifest.toString())
      }
      if (checkError != null) {
        val errorMap = Arguments.createMap()
        errorMap.putString("message", checkError.message)
        contextMap.putMap("checkError", errorMap)
      }
      if (downloadError != null) {
        val errorMap = Arguments.createMap()
        errorMap.putString("message", downloadError.message)
        contextMap.putMap("downloadError", errorMap)
      }
      val result = Arguments.createMap()
      result.putMap("context", contextMap)
      return result
    }
}
