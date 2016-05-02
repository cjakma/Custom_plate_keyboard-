package yi.cache.webview;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	private WebView webView;
	private static final String URL = "http://192.168.1.113:8080/myh5test/h5test.html";
	private static final String APP_CACHE_DIRNAME = "/webcache"; // web����Ŀ¼
	private Button btn_night, btn_light;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clearWebViewCache();
		findView();
		findWebView();
	}

	private void findView() {
		webView = (WebView) findViewById(R.id.web_view);
		btn_night = (Button) findViewById(R.id.btn_night);
		btn_light = (Button) findViewById(R.id.btn_light);
		btn_night.setOnClickListener(this);
		btn_light.setOnClickListener(this);
	}

	private void findWebView() {
		initWebView();
		WebSettings settings = webView.getSettings();
		// ����javaScript����
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onLoadResource(WebView view, String url) {
				Log.i(TAG, "onLoadResource url=" + url);

				super.onLoadResource(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i(TAG, "intercept url=" + url);
				view.loadUrl(url);
				return true;
			}

			// ҳ�濪ʼʱ����
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.e(TAG, "onPageStarted");
				super.onPageStarted(view, url, favicon);
			}

			// ҳ�������ɵ���
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				Log.e(TAG, "onJsAlert " + message);

				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				result.confirm();
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				Log.e(TAG, "onJsConfirm " + message);
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
					JsPromptResult result) {
				Log.e(TAG, "onJsPrompt " + url);
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
		webView.loadUrl(URL);
	}

	public void initWebView() {
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		if (isNetworkConnected(this)) {
			// ���黺�����Ϊ���ж��Ƿ������磬�еĻ���ʹ��LOAD_DEFAULT,������ʱ��ʹ��LOAD_CACHE_ELSE_NETWORK
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // ���û���ģʽ
		} else {
			webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // ���û���ģʽ
		}

		// ����DOM storage API ����
		webView.getSettings().setDomStorageEnabled(true);
		// ����database storage API����
		webView.getSettings().setDatabaseEnabled(true);
		// String cacheDirPath = getInnerSDCardPath() + APP_CACHE_DIRNAME;
		String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
		Log.i("cachePath", cacheDirPath);
		// �������ݿ⻺��·��
		webView.getSettings().setDatabasePath(cacheDirPath); // API 19
																// deprecated
		// ����Application caches����Ŀ¼
		webView.getSettings().setAppCachePath(cacheDirPath);
		// ����Application Cache����
		webView.getSettings().setAppCacheEnabled(true);

		Log.i("databasepath", webView.getSettings().getDatabasePath());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_night:
			webView.loadUrl("javascript:load_night()");
			break;
		case R.id.btn_light:
			webView.loadUrl("javascript:load_day()");
			break;
		}
	}

	private void clearWebViewCache() {
		try {
			deleteDatabase("webview.db");
			deleteDatabase("webviewCache.db");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// WebView�����ļ�
//		File appCacheDir = new File(getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME);
		
		//seems the system only find this cache folder no matter which folder you set before. 
		File appCacheDir = new File("/data/data/yi.cache.webview/cache");
		
		Log.e(TAG, "appCacheDir path=" + appCacheDir.getAbsolutePath());

		File webviewCacheDir = new File(getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME);
		Log.e(TAG, "appCacheDir path=" + webviewCacheDir.getAbsolutePath());

		// ɾ��webView����Ŀ¼
		if (webviewCacheDir.exists()) {
			deleteFile(webviewCacheDir);
		}
		// ɾ��webView���棬����Ŀ¼
		if (appCacheDir.exists()) {
			deleteFile(appCacheDir);
		}
	}

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * ��ȡ����SD��·��
	 * 
	 * @return
	 */
	public String getInnerSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	public void deleteFile(File file) {
		Log.i(TAG, "delete file path=" + file.getAbsolutePath());
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
		}
	}
}
