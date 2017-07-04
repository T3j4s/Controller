package com.example.virtualjoystick;
/*
 * Author : Zero
 * Date   :23/2/15
 * time   :1:15AM
 * */
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Connect extends Activity {
	Button con;
	EditText iptb;
	TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        
        
		con=(Button)this.findViewById(R.id.connect);
        iptb=(EditText)this.findViewById(R.id.tbox);
        status=(TextView)this.findViewById(R.id.status);
        
        con.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Global.serverIP=""+iptb.getText();
				MakeConnection con=new MakeConnection();
				Thread th=new Thread(con);
				th.start();
				try {
					th.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Global.clientSocket!=null){
					status.setText("Status : Connected ");
					Intent i=new Intent("com.VirtualJoyStick.CONTROLER" );
			        startActivity(i);
				}else status.setText("Status :  "+con.getError());
			}
		});
        
        

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.connect, menu);
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
	protected void onStop() {
		super.onStop();
		
		this.finish();
	}
    
    
}

//--------------------------------------------------------------------------------------------------------------
//Class which perform's operation.
class MakeConnection implements Runnable{
	private String error="No Error";
	public void run(){
		try {
			Global.clientSocket=new Socket(Global.serverIP,2020);
		} catch (UnknownHostException e) {
			error=e.getLocalizedMessage();
		} catch (IOException e) {
			error=e.getLocalizedMessage();
		}
		
		
	}
	
	boolean isConnected(){
		if(Global.clientSocket!=null)return true;
		else return false;
		
	}
	void stopConnection(){
		 try {
			Global.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	String getError(){return error;}
}