package com.example.virtualjoystick;
/*
 * Author : Zero
 * Date   :23/2/15
 * time   :1:15AM
 * */
import java.io.IOException;
import java.io.ObjectOutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Controler extends Activity {
	Button left,right,accel,gear,nitro,reverse,brake,select,back,discon,geardown;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controler);
				
		
		
				//initialization	
				ButtonOperation bo=new ButtonOperation();
				left=(Button)this.findViewById(R.id.left);
				right=(Button)this.findViewById(R.id.right);
				accel=(Button)this.findViewById(R.id.accel);
				reverse=(Button)this.findViewById(R.id.reverse);
				
				gear=(Button)this.findViewById(R.id.gear);
				geardown=(Button)this.findViewById(R.id.geardown);
				nitro=(Button)this.findViewById(R.id.nitro);
				brake=(Button)this.findViewById(R.id.brake);
				
				select=(Button)this.findViewById(R.id.select);
				back=(Button)this.findViewById(R.id.back);
				
				discon=(Button)this.findViewById(R.id.discon);
				
				//add Listener
				left.setOnTouchListener(bo);
				right.setOnTouchListener(bo);
				accel.setOnTouchListener(bo);
				reverse.setOnTouchListener(bo);
				
				gear.setOnTouchListener(bo);
				geardown.setOnTouchListener(bo);
				nitro.setOnTouchListener(bo);
				brake.setOnTouchListener(bo);
				
				select.setOnTouchListener(bo);
				back.setOnTouchListener(bo);
				
				discon.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						if(Global.clientSocket!=null){
							try {
								Global.clientSocket.close();
								finish();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.controler, menu);
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
}

//---------------------------------
///Operator class
class ButtonOperation implements View.OnTouchListener{
	ObjectOutputStream out=null;
	
	ButtonOperation(){
		try {
			this.out=new ObjectOutputStream(Global.clientSocket.getOutputStream());
			
		} catch (IOException e) {
			Log.d("Exception : ", e.getLocalizedMessage());
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			
			if(event.getAction()==MotionEvent.ACTION_UP){
					out.writeObject(new String(v.getTag().toString()+"-U"));
					Log.d(v.getTag().toString()," UP ");
				}
			else {
				out.writeObject(new String(v.getTag().toString()+"-D"));
				Log.d(v.getTag().toString()," DOWN ");
			}
		
		} catch (IOException e) {
			Log.d("Exception : ", e.getLocalizedMessage());
		}
		
		return false;
	}


}