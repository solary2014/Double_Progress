package com.lly.progress;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.style.BulletSpan;
import android.util.Log;

public class JobHandleService extends JobService {

	private int KJobId = 0;


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("INFO","Jobservice create");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("INFO","Jobservice start");
		scheduleJob(getJobInfo());
		return START_STICKY;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onStartJob(JobParameters params) {
		Log.i("INFO","job start");
		scheduleJob(getJobInfo());
		boolean isLocalServiceWork = isServiceWork(this, "com.lly.progress");
		boolean isRemotserviceWork = isServiceWork(this, "com.lly.progress:second");
		if(!isLocalServiceWork||!isRemotserviceWork){
			this.startService(new Intent(this, FristProgress.class));
			this.startService(new Intent(this, SecondProgress.class));
			
		}
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Log.i("INFO","job stop");
		scheduleJob(getJobInfo());
		return true;
	}
	
	public void scheduleJob(JobInfo t){
		Log.i("INFO","scheduling job");
		JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
		tm.schedule(t);
	}
	public JobInfo getJobInfo(){
		JobInfo.Builder builder = new JobInfo.Builder(KJobId++, new ComponentName(this, JobHandleService.class));
		builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
		builder.setPersisted(true);
		builder.setRequiresCharging(false);
		builder.setRequiresDeviceIdle(false);
		builder.setPeriodic(10);
		return builder.build();
		
	}

	public boolean isServiceWork(Context mContext,String serviceName){
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(100);
		if(myList.size()<=0){
			return false;
		}
		for(int i =0;i<myList.size();i++){
			String mName = myList.get(i).service.getClassName().toString();
			if(mName.equals(serviceName)){
				isWork = true;
				break;
			}
		}
		return isWork;
	}
	
	
}
