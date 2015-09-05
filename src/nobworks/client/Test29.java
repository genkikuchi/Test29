package nobworks.client;

import java.util.HashMap;
import java.util.Map;

import nobworks.client.channel.Channel;
import nobworks.client.channel.ChannelListener;
import nobworks.client.common.FailListener;
import nobworks.client.xutil.JsUtil;
import nobworks.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

// Test-1 GWT2.6
// Test-2 by MBP
public class Test29 implements EntryPoint {
	
	private static Channel channel;
	private static String channelUUID = "channel-A";	// same as scheduleUUID
	private static String playerID = "player-A";
	private static String playerUUID = "uuid-A";
	private static String cache = "false";	// true/false
	private static String log = "false";	// true/false
	private static Map<String,String> tokenMap = new HashMap<String,String>();
	
	public void onModuleLoad() {
		GWT.log( "Enter onModuleLoad()" );
		channelStart();
	}
	private void channelStart() {
		DOM.getElementById( "channelUUID" ).setAttribute( "value", channelUUID );
		DOM.getElementById( "playerUUID" ).setAttribute( "value", playerUUID );
		DOM.getElementById( "cache" ).setAttribute( "value", cache );
		DOM.getElementById( "log" ).setAttribute( "value", log );
		startPushGWT();
	}
	private static void startPushGWT() {
		createChannel( true );
	}
	private static void createChannel( final boolean requestContents ) {
		channel = new Channel( channelUUID );
 GWT.log( "*==== Enter createChannel / channelUUID=" + channelUUID );
		channel.addChannelListener( new ChannelListener() {
			@Override
			public void onMessage( String jsonText ) {
GWT.log( "------[Channel onMessage()] json=" + jsonText );
				if ( jsonText.startsWith( "channelReconnect" ) ) {
GWT.log( "[NanoSignagePlayer] **********Channel start Re-connect!!!!" );
					tokenMap.put( channel.getToken(), null );
					JsUtil.resetChannelIFrame();
					createChannel( false );
					// チャネルがまだ生きているために、同じチャネル名に複数送られてしまう。
					return;
				}
				if ( jsonText.startsWith( "stopPlay" ) ) {
					String jsonBak = tokenMap.get( channel.getToken() );
					if ( jsonBak != null && jsonBak.equals( jsonText ) ) {
// ClientUtil.consoleLog( "[NanoSignagePlayer] □□□□action=stop Skip token:" + channel.getToken() );
						// 内容が変わっていないならスキップして頭から再送されることを防ぐ。
						return;
					}
// ClientUtil.consoleLog( "[NanoSignagePlayer] **********stop Play" );
					tokenMap.put( channel.getToken(), jsonText );
					JsUtil.stopCastAndShowDefaultImage();
//					sendLogGWT( "stop" );
					return;
				}
				if ( jsonText.startsWith( "resend:" ) ) {	// resend prefix
// ClientUtil.consoleLog( "[NanoSignagePlayer] !!!!! resend" );
					sendLogGWT( "resend" );
					jsonText = jsonText.substring( 7 );
				}
				if ( jsonText.startsWith( "cacheclear" ) ) {	// Channel 指定によるクリア
//					clearWorkingCache();
					sendLogGWT( "cacheclear" );
					return;
				}
				if ( jsonText.startsWith( "{\"cacheclearplayer" ) ) {	// Player 指定によるクリア
//					clearCachePlayer( jsonText );
					return;
				}
				if ( jsonText.startsWith( "{\"reloadplayer" ) ) {	// Player 指定によるリロード
//					reloadPlayer( jsonText );
					return;
				}
				if ( jsonText.startsWith( "{\"resetplayer" ) ) {
//					resetPlayer( jsonText );
					return;
				}
				if ( jsonText.startsWith( "hearbeat" ) ) {
//					clearWorkingCache();
					sendLogGWT( "hearbeat" );
				}
				if ( requestContents ) {
					String jsonBak = tokenMap.get( channel.getToken() );
					if ( jsonBak != null && jsonBak.equals( jsonText ) ) {
						GWT.log( "Skip cache JSON" );
						// 接続中チャネルのJSON内容が変わっていないならスキップして頭から再送されることを防ぐ。
						return;
					}
					
//					launcher.setJsonText( jsonText );
					if ( cache != null && cache.equals( "true" ) ) {
						// Local Storage キャッシュ
//						launcher.recoverCacheJsonText();
					} else {
//						launcher.logSetup();
//						JsUtil.sendSignageReceiver( jsonText );
					}
					tokenMap.put( channel.getToken(), jsonText );
				}
			}

			@Override
			public void onOpen() {
				// Channel API 準備完了
GWT.log( "[NanoSignagePlayer] channel onOpen()" );
Window.alert( "channel onOpen()" );
				// 現在時刻のスケジュールを Tomcat に検索要求する。
				if ( requestContents ) {
					requestStartUpSchedule();
				}
				// new LocalStorageWindow();
				// pushDummyJson();
			}

			@Override
			public void onError( int code, String description ) {
				// オフラインの場合は正常
				if ( JsUtil.isLocalOnline() == false ) {
					GWT.log( "OFFLINE===============" );
					return;
				}
				// タイムアウトあり->再接続
				// GAE 接続確認
				// Tomcat 接続確認
//				resetChannelIFrame(); -> reload()
//				createChannel( true ); -> reload()
				final int codeTmp = code;
				final String descriptionTmp = description;
				GWT.log( "\tERRRRRRRRRRRRRRRRRor [Channel onError] " + codeTmp + " " + descriptionTmp );
//				JsUtil.reload();
			}

			@Override
			public void onClose() {
// ClientUtil.consoleLog( "\t**********Channel onClose():start Re-connect!!!!" );
				JsUtil.resetChannelIFrame();
				createChannel( true );
			}
		} );
		channel.join( new FailListener() {
			@Override
            public void onFailure( Object obj ) {
//				resetChannelIFrame(); -> reload()
//				createChannel( true ); -> reload()
				GWT.log( "channel join onFailure -> reload" );
//				JsUtil.reload();
            }
		} );
	}
	private static void sendLogGWT( String action ) {
	}
	private static void requestStartUpSchedule() {
	}
	public static native void alert( String msg ) /*-{
		$wnd.alert( msg );
	}-*/;
}
