package com.vipcell.krreno.mywebsample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MyAndroidToJsInterface, View.OnClickListener {
    private WebView browser;
    private EditText txt1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        browser = (WebView) findViewById(R.id.webview);
        Button btn1 = (Button) findViewById(R.id.btn1);
        txt1 = (EditText) findViewById(R.id.txt1);

        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(this, "jsNativeInterface");
        browser.loadUrl("file:///android_asset/test.html");
        btn1.setOnClickListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {

        String jsCall = ("javascript:callFromAndroidToJS('" + txt1.getText().toString() + "')");
        if (android.os.Build.VERSION.SDK_INT < 19) {
            browser.loadUrl(jsCall);
        } else {
            browser.evaluateJavascript(jsCall,null);
        }

    }

    @Override
    @JavascriptInterface
    public void metodoDemo1() {
        Toast.makeText(this, "Invocado el metodo: metodoDemo1", Toast.LENGTH_SHORT).show();
    }

    @Override
    @JavascriptInterface
    public String metodoDemo2() {
        String toReturn = null;

        try {
            JSONObject json = new JSONObject();
            json.put("timestamp", System.currentTimeMillis());
            json.put("autor", "http://www.carlos-garcia.es");
            toReturn = json.toString();
        } catch (JSONException e) {
            // no se dará
        }

        return toReturn;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.vipcell.krreno.mywebsample/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.vipcell.krreno.mywebsample/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
