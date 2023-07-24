package cursoLambdasStreams.real_appplications;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ConcurrenceLambdas {

	static Runnable printerSm = () -> {
		long util= 0;
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 1000000; i++) {
			util+=i;
		}
		System.out.println("total: " + util);
	};
	
	static Callable<Long> getSum = () -> {
		long util= 0;
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 1000000; i++) {
			util+=i;
		}
		return util;
	};
	
	public static void main(String[] args) {

		var exec = Executors.newSingleThreadExecutor();
		exec.submit(printerSm);
		
		var result = exec.submit(getSum);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exec.shutdown();
		
	}

}
