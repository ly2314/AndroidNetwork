package com.ly2314.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    	private EditText _editText;
    	private Button _button;
    	private TextView _textView;
    	
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            _editText = (EditText) rootView.findViewById(R.id.editText1);
            _textView = (TextView) rootView.findViewById(R.id.textBox1);
            _button = (Button) rootView.findViewById(R.id.button1);
            _button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>()
					{
						@Override
						protected String doInBackground(Void... params) {
							return fetch(_editText.getText().toString());
						}
						@Override
						protected void onPostExecute(String result) {
							_textView.setText(result);
							//Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
						}
					};
					task.execute();
				}
			});
            return rootView;
        }
        
        private String fetch(String address)
        {
			try {
				String addr = URLEncoder.encode(address, "utf-8");
				String urlString = "http://maps.googleapis.com/maps/api/geocode/json?address=" + addr + "&sensor=false";
				URL url = new URL(urlString);
	        	URLConnection urlConnection = url.openConnection();
	        	InputStream is = urlConnection.getInputStream();
	        	BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
	        	String result = "", line;
	        	while ((line = buffer.readLine()) != null)
	        	{
	        		result += line;
	        	}
	        	return result;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
			return null;
        }
    }

}
