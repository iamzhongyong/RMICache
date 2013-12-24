package test;

import rmicache.RMICacheHolder;

public class MainTest {

	public static void main(String[] args) throws Exception{
		BizService service = new BizService();
		
		//如果直接调用getUserDOById，可能比较耗时
		long s = System.currentTimeMillis();
		service.getUserDOByID(110L);
		service.getUserDOByID(110L);
		service.getUserDOByID(110L);
		System.out.println("直接调用远程，方法调用三次，耗时："+(System.currentTimeMillis() - s));
		//调用getUserDOByIdWraper，这个方法做了线程级别的缓存
		s = System.currentTimeMillis();
		service.getUserDOByIdWraper(110L);
		service.getUserDOByIdWraper(110L);
		service.getUserDOByIdWraper(110L);
		System.out.println("三次包装类调用，方法调用三次,有缓存，耗时："+(System.currentTimeMillis() - s));
		RMICacheHolder.newInstance().clearThreadLocal();
		s = System.currentTimeMillis();
		service.getUserDOByIdWraper(110L);
		System.out.println("线程数据清理，调用一次包装类，耗时："+(System.currentTimeMillis() - s));
		new Thread(){
		public void run() {
			BizService service = new BizService();
			long s = System.currentTimeMillis();
			service.getUserDOByID(110L);
			service.getUserDOByID(110L);
			service.getUserDOByID(110L);
			System.out.println("异步方法调用三次，全部是远程，耗时："+(System.currentTimeMillis() - s));
		}}.start();;
		new Thread(){
			public void run() {
				BizService service = new BizService();
				long s = System.currentTimeMillis();
				service.getUserDOByID(110L);
				service.getUserDOByIdWraper(110L);
				service.getUserDOByIdWraper(110L);
				System.out.println("异步方法调用三次,一次远程，一次缓存，耗时："+(System.currentTimeMillis() - s));
				RMICacheHolder.newInstance().clearThreadLocal();
				s = System.currentTimeMillis();
				service.getUserDOByID(110L);
				service.getUserDOByIdWraper(110L);
				service.getUserDOByIdWraper(110L);
				System.out.println("线程缓存清理后,异步方法调用三次,一次远程，一次缓存，耗时："+(System.currentTimeMillis() - s));
			}}.start();;
		
		Thread.sleep(2000);

	}
}
