package com.ximad.install;

import com.utility.alexarchiver.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MobFlowActivity extends Activity {

	private static final String URL = "http://www.rewardsflow.com/default.aspx?Flow=99995B53-C79E-41F2-8C48-37E9EB7F5D27";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_flow);
        
        WebView w = (WebView)findViewById(R.id.webView1);
        w.getSettings().setJavaScriptEnabled(true);
        final Context context = this;
        w.setWebChromeClient(new WebChromeClient(){
        	@Override
        	public boolean onJsAlert(WebView view, String url, String message,
        			final android.webkit.JsResult result) {
        		
        			new AlertDialog.Builder(context).setTitle("javaScript dialog").
        			setMessage(message).
        			setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							result.confirm();
							startActivity(new Intent(context, FirstActivity.class));
						}
					}).setCancelable(false).create().show();
        		return true;
        	}
        	
        });
        w.loadUrl(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mob_flow, menu);
        return true;
    }

    
}
