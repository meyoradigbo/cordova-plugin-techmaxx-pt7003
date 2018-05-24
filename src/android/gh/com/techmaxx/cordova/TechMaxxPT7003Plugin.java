/*
The MIT License (MIT)
Copyright (c) 2018 Charles Meyor Adigbo
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,f ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package gh.com.techmaxx.cordova;

/*import com.techmaxx.barcode.*;
import com.techmaxx.barcode.decoder.*;
import com.techmaxx.barcode.decoderparams.*;
import com.techmaxx.barcodebase.*; */


import android.pt.printer.Printer;

import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

import java.util.List;
import java.util.ArrayList;

import java.io.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class TechMaxxPT7003Plugin extends CordovaPlugin {

	// IntentFilter is used to get the intent we need.
	private IntentFilter filter;
	
	// A very important class used to communicate with driver and  service. 
	private CallbackContext printPictureCallback;
	private boolean enableBinaryData = false;

    Printer printer = null;
	

	public TechMaxxPT7003Plugin()
	{
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
	{

        //System.out.println("============== execute ===========");
        Log.v("TechMaxxPT7003Plugin", "============== execute ===========: " + action);

        if (action.equals("echo")) {
            String message = args.getString(0);
            this.echo(message, callbackContext);
            return true;
        }

		if (action.equals("initialise")) {
			this.initialise(callbackContext);
			return true;
		}
		

		
		if (action.equals("printPicture"))
		{
			//Log.v("TechMaxxPT7003Plugin", "requestScan");
  		   String  picpath = args.getString(0);
		    printPicture(picpath, callbackContext)
			return true;
		}
		
		if (action.equals("destroy"))
		{
		
			if (mReaderManager != null)
			{
				// Call this from window.onbeforeunload
				Log.v("TechMaxxPT7003Plugin", "destroy(): cleaning up.");			
			
				printer = null;
			}
		
			return true;
		}

		
		Log.v("TechMaxxPT7003Plugin", "============== done   ===========: " + action);

        return false;
    }


    private void echo(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

	public void initialise(CallbackContext callbackContext)
	{
		Log.v("TechMaxxPT7003Plugin", "TechMaxxPT7003Plugin.initialise()");
		try
		{
			Log.v("TechMaxxPT7003Plugin", "com.techmaxx.printer.GeneralString.Intent_PRINT: ");
			if (cordova.getActivity() == null)
			{
				Log.v("TechMaxxPT7003Plugin", "cordova.getActivity() is null");
			} else {
				Log.v("TechMaxxPT7003Plugin", "cordova.getActivity() is something");
			}

			Log.v("TechMaxxPT7003Plugin", "Got printer");

		} catch (Exception e)
		{
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter );
			printWriter.flush();

			String stackTrace = writer.toString();

			Log.v("TechMaxxPT7003Plugin", "Error starting printer manager: " + stackTrace);
		}

		Log.v("TechMaxxPT7003Plugin", "TechMaxxPT7003Plugin.initialise() Done");

		callbackContext.success();
	}

	
    public void printPicture(String picpath, CallbackContext callbackContext)
    {
        printer.open();
        int no_paper_flg = printer.queState();

        if (no_paper_flg == 1)
        {
            Messagebox(this, "no_paper") ;
            return;
        }

        int ret = printer.printPictureByRelativePath(picpath, 200, 200);


        if (ret== 0) {

            Messagebox(this, "success") ;
        }
        else
        {
            Messagebox(this, "fail") ;
        }

		 printer.close();
		 callbackContext.success();

    }

}
