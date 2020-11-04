package com.ali.retail.utils.duplicate;

import com.datacentor.dup.DDAlert;
import com.datacentor.dup.DupDetectService;
import com.datacentor.dup.DupDetector;

import java.util.ArrayList;
import java.util.List;

public class DDrPool {

	int max;
	int expire_second=30;//过期秒数
	DDAlert ddalert;
	volatile int num;
	volatile List<Unit> list;
	//max 最大DupDetector的数量
	CleanThread cleaner;
	boolean running=true;
	public DDrPool(int max,DDAlert ddalert){
		this.max=max;
		this.ddalert=ddalert;
		list=new ArrayList<Unit>(max);
		num=0;
		cleaner=new CleanThread();
		cleaner.start();
	}


	public synchronized void destory() throws Exception {
		System.out.println("DDrPool  begin to destory. list.size()="+list.size());
		running=false;
		Thread.sleep(100);
		for(Unit u:list){
			u.ddr.release();
		}
		list.clear();
		num=0;
	}

	class Unit{
		DupDetector ddr;
		long expire_time;
	}
	class CleanThread extends Thread{

		@Override
		public void run() {
			int n=0;
			while(running){
				if(n++>=100){
					pool_clean();
					n=0;
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("CleanThread stoped");
		}

	}


	public synchronized void release(DupDetector dd) {
		if(dd==null) return;

		Unit u=new Unit();
		u.ddr=dd;
		u.expire_time=System.currentTimeMillis()+expire_second*1000;
		list.add(u);
		//	 System.out.println("list.size() ="+list.size());
	}



	public synchronized void pool_clean() {

		for(int i=list.size()-1;i>=0;i--){
			Unit u=list.get(i);
			if(u.expire_time<System.currentTimeMillis()){
				u.ddr.release();
				list.remove(i);
				num--;
				System.out.println("release ddr at "+i);
			}
		}
	}


	public  DupDetector get() throws Exception {
		while(running){

			synchronized (this){
				if(list.size()>0){

					Unit u=list.get(0);
					list.remove(0);
					return u.ddr;
				}
			}
			if(num<max){
				num++;
				return DupDetectService.createDetector(ddalert);
			}

			Thread.sleep(10);
			System.out.println("the number of DupDetector reach the max, sleep 1s. ");
		}
		return null;
	}



	public static void main(String[] args) throws Exception {

		final byte []md5v=new byte[16];
		//系统启动时的初始化
		//String ip="192.168.1.8";
		String ip="127.0.0.1";
		DupDetectService.initialise(ip, 7010);
		final DDrPool pool=new DDrPool(6,new DDAlert());

		for(int i=0;i<10;i++){
			//程序运行过程中调用，get和release一起调用， 用try确保释放
			new Thread(){

				@Override
				public void run() {
					DupDetector dd=null;
					try {
						dd = pool.get();

						dd.dupTry(md5v, DupDetector.MODE_ALL_ID);
					}catch(Exception e){}
					finally{
						if(dd!=null)
							pool.release(dd);
					}
				}


			}.start();
			System.out.println(i);

		}
		Thread.sleep(40000);

		//系统退出时，销毁pool
		pool.destory();
		System.out.println("over");
	}


}
