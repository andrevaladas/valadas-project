/**
 * 
 */
package com.chronosystems.poc.ehcache.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chronosystems.poc.ehcache.CacheCreation;
import com.chronosystems.poc.ehcache.util.CacheUtil;

/**
 * @author User
 *
 */
public class UseCaseClass {

	private static final String CACHE_NAME = "myCache1";
	
	
	public List<String> getAllData1(final String threadName){
		return CacheUtil.getListFromCache(threadName, CACHE_NAME, "data1", new CacheCreation<String>(){
			@Override
			public List<String> getAll(){
				System.out.println(threadName+" : UseCaseClass.getAllData1() : the target original method is called to get the values.");
				List<String> list = new ArrayList<String>();
				list.add("data1-value1");
				list.add("data1-value2");
				list.add("data1-value3");
				list.add("data1-value4");
				return list;
			}
		});
	}

		
	public List<String> getAllData2(final String threadName){
		return CacheUtil.getListFromCache(threadName, CACHE_NAME, "data2", new CacheCreation<String>(){
			@Override
			public List<String> getAll(){
				System.out.println(threadName+" : UseCaseClass.getAllData2() : the target original method is called to get the values.");
				List<String> list = new ArrayList<String>();
				list.add("data2-value1");
				list.add("data2-value2");
				list.add("data2-value3");
				list.add("data2-value4");
				return list;
			}
		});
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nbThreads = 3;
		ExecutorService execService = Executors.newFixedThreadPool(nbThreads);
			
		// Create several threads which solicit the Ehcache
		for (int i = 0; i < nbThreads; i++) {
			final int indexFinal = i;

			execService.submit(new Runnable(){
				String threadName= null;
				UseCaseClass useCaseClass = null;
					
				@SuppressWarnings("static-access")
				public void run(){
					try {
						useCaseClass = new UseCaseClass();
						threadName = "thread_"+indexFinal;
						useCaseClass.getAllData1(threadName);
						{
							int sleepTime = getRandomSleepTime(1000, 5000);
							System.out.println(threadName+" will sleep during "+sleepTime+"ms.");
							Thread.currentThread().sleep(sleepTime);
							System.out.println(threadName+" wakes up");
						}
						useCaseClass.getAllData2(threadName);
						{
							int sleepTime = getRandomSleepTime(1000, 5000);
							System.out.println(threadName+" will sleep during "+sleepTime+"ms.");
							Thread.currentThread().sleep(sleepTime);
							System.out.println(threadName+" wakes up");
						}
						useCaseClass.getAllData1(threadName);
						useCaseClass.getAllData2(threadName);
						useCaseClass.getAllData1(threadName);
						useCaseClass.getAllData2(threadName);
					} catch (Throwable e) {
						e.printStackTrace();
					}
						
				}//end-run
					
				private int getRandomSleepTime(int min, int max){
					return min + (int)(Math.random() * ((max - min) + 1));
				}

			}//end-runnable

			);//end-submit

		}//end-for

	}

}
