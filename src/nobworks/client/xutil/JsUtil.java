package nobworks.client.xutil;

import com.google.gwt.core.client.JavaScriptObject;

public class JsUtil {

	public static native String getLocalStorageValue( String key ) /*-{
		return localStorage.getItem(key);
	}-*/;

	public static native String getLocalStorageKey( int idx ) /*-{
		return localStorage.key(idx);
	}-*/;

	public static native int getLocalStorageSize() /*-{
		return localStorage.length;
	}-*/;

	public static native void setLocalStorageValue( String key, String value ) /*-{
		localStorage.setItem(key, value);
	}-*/;

	public static native void setLocalStorageValue( String key, JavaScriptObject value ) /*-{
		localStorage.setItem(key, value);
	}-*/;

	public static native JavaScriptObject getJsonRow( JavaScriptObject jsonObj, int idx ) /*-{
		return jsonObj[idx];
	}-*/;

	public static native String getJsonRowX( JavaScriptObject jsonObj, int idx ) /*-{
		return JSON.stringify(jsonObj[idx]);
	}-*/;

	public static native String jsonStringify( JavaScriptObject jsonObj ) /*-{
		return JSON.stringify(jsonObj);
	}-*/;

	public static native String getJsonValue( JavaScriptObject jsonObj, int idx ) /*-{
		return jsonObj[idx];
	}-*/;

	public static native String getJsonValue( JavaScriptObject jsonObj, String key ) /*-{
		return jsonObj[key];
	}-*/;

	public static native void setJsonValue( JavaScriptObject jsonObj, String key, String value ) /*-{
		return jsonObj[key] = value;
	}-*/;

	public static native String getJsonValue( JavaScriptObject jsonObj, int idx, String key ) /*-{
		return jsonObj[idx][key];
	}-*/;

	public static native int getJsonLength( JavaScriptObject jsonObj ) /*-{
		return jsonObj.length;
	}-*/;

	public static native void clearLocalStorage() /*-{
		$wnd.clearLocalStorage();
	}-*/;

	public static native void clearWorkingCache() /*-{
		$wnd.clearWorkingCache();
	}-*/;

	public static native void removeAllCacheFiles() /*-{
		$wnd.removeAllCacheFiles();
	}-*/;

	public static native void removeTrashCacheFile( String filename ) /*-{
		$wnd.removeFile(filename);
	}-*/;

	public static native void showLocalStorage() /*-{
		$wnd.showLocalStorage();
	}-*/;

	public static native void createCacheDir( String dir ) /*-{
		$wnd.createCacheDir(dir);
	}-*/;

	public static native void removeCacheDir( String dir ) /*-{
		$wnd.removeCacheDir(dir);
	}-*/;

	public static native void resetPlayer() /*-{
		$wnd.resetPlayer();
	}-*/;

	public static native void reloadX() /*-{
		alert("Reload(Mock)");
	}-*/;

	public static native void reload() /*-{
		$wnd.location.reload();
	}-*/;
	public static native void showCacheFileList() /*-{
		$wnd.showCacheFileList();
	}-*/;
	public static native void resetChannelIFrame() /*-{
		$wnd.resetChannelIFrame();
	}-*/;
	public static native void showChannelName() /*-{
		$wnd.showChannelName();
	}-*/;

	// 不要
	public static native void startPush() /*-{
		$wnd.startPush();
	}-*/;

	public static native String getSerialNo() /*-{
		return $wnd.getSerialNo();
	}-*/;

	public static native String getPlayerID() /*-{
		return $wnd.getPlayerID();
	}-*/;

	public static native String getUserUUID() /*-{
		return $wnd.getUserUUID();
	}-*/;

	public static native String getCache() /*-{
		return $wnd.getCache();
	}-*/;

	public static native String setCache( String value ) /*-{
		return $wnd.setCache(value);
	}-*/;

	public static native String getLog() /*-{
		return $wnd.getLog();
	}-*/;

	public static native String setLog( String value ) /*-{
		return $wnd.setLog(value);
	}-*/;

	public static native boolean isLocalOnline() /*-{
		return navigator.onLine;
	}-*/;
	
	public static native void sendSignageReceiver( String jsonText )
	/*-{
		$wnd.receive( jsonText );
	}-*/;

	public static native void stopCastAndShowDefaultImage()
	/*-{
		$wnd.stopCastAndShowDefaultImage();
	}-*/;

	public static native void isAppEngineEnable() /*-{
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("HEAD", "/NanoSignagePlayer.html", true);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200)
					alert("URL Exists!");
				else if (xmlhttp.status == 404)
					alert("URL doesn't exist!");
				else
					alert("Status is " + xmlhttp.status);
			}
		}
		xmlhttp.send(null)
	}-*/;

	//------- end of class
}
