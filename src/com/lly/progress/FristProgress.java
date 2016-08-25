package com.lly.progress;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.animation.ScaleAnimation;

public class FristProgress extends Service {

	private Myconn conn;
	private MyBinder binder;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		binder = new MyBinder();
		if(conn == null){
			conn = new Myconn();
		}
		Log.i("lly2","FristServeronCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//this.bindService(new Intent(this, SecondProgress.class), conn, Context.BIND_IMPORTANT);
		this.startService(new Intent(this, SecondProgress.class));
		return START_STICKY;
		
	}
	
	class Myconn implements  ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("lly2","FristServer∆Ù∂Ø");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("lly2","FristServer∆Ù∂Ø ß∞‹");
			FristProgress.this.startService(new Intent(FristProgress.this, SecondProgress.class));
			FristProgress.this.bindService(new Intent(FristProgress.this, ScaleAnimation.class), conn, Context.BIND_IMPORTANT);
		}
		
	}


	
}

class MyBinder extends ProgressInface.Stub{

	@Override
	public String getServiceName() throws RemoteException {
		// TODO Auto-generated method stub
		return "FristProgress";
	}
	
}
