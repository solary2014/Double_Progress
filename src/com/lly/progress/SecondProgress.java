package com.lly.progress;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class SecondProgress extends Service {

	private MyBinder binder;
	private MyConn conn;
	
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
		if(conn==null){
			conn = new MyConn();
		}
		Log.i("lly2","SecondServiceonCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//this.bindService(new Intent(SecondProgress.this, FristProgress.class), conn, Context.BIND_IMPORTANT);
		this.startService(new Intent(SecondProgress.this, FristProgress.class));
		return START_STICKY;
	}
	
	class MyConn implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("lly2","SecondService∆Ù∂Ø");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			SecondProgress.this.startService(new Intent(SecondProgress.this, FristProgress.class));
			SecondProgress.this.bindService(new Intent(SecondProgress.this, FristProgress.class), conn, Context.BIND_IMPORTANT);
		}
		
		
	}
	
	class MyBinder extends ProgressInface.Stub{

		@Override
		public String getServiceName() throws RemoteException {
			// TODO Auto-generated method stub
			return "SecondProgress";
		}
		
	}

}
