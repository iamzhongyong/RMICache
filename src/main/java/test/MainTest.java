package test;

import rmicache.RMICacheHolder;

public class MainTest {

	public static void main(String[] args) throws Exception{
		BizService service = new BizService();
		
		//���ֱ�ӵ���getUserDOById�����ܱȽϺ�ʱ
		long s = System.currentTimeMillis();
		service.getUserDOByID(110L);
		service.getUserDOByID(110L);
		service.getUserDOByID(110L);
		System.out.println("ֱ�ӵ���Զ�̣������������Σ���ʱ��"+(System.currentTimeMillis() - s));
		//����getUserDOByIdWraper��������������̼߳���Ļ���
		s = System.currentTimeMillis();
		service.getUserDOByIdWraper(110L);
		service.getUserDOByIdWraper(110L);
		service.getUserDOByIdWraper(110L);
		System.out.println("���ΰ�װ����ã�������������,�л��棬��ʱ��"+(System.currentTimeMillis() - s));
		RMICacheHolder.newInstance().clearThreadLocal();
		s = System.currentTimeMillis();
		service.getUserDOByIdWraper(110L);
		System.out.println("�߳�������������һ�ΰ�װ�࣬��ʱ��"+(System.currentTimeMillis() - s));
		new Thread(){
		public void run() {
			BizService service = new BizService();
			long s = System.currentTimeMillis();
			service.getUserDOByID(110L);
			service.getUserDOByID(110L);
			service.getUserDOByID(110L);
			System.out.println("�첽�����������Σ�ȫ����Զ�̣���ʱ��"+(System.currentTimeMillis() - s));
		}}.start();;
		new Thread(){
			public void run() {
				BizService service = new BizService();
				long s = System.currentTimeMillis();
				service.getUserDOByID(110L);
				service.getUserDOByIdWraper(110L);
				service.getUserDOByIdWraper(110L);
				System.out.println("�첽������������,һ��Զ�̣�һ�λ��棬��ʱ��"+(System.currentTimeMillis() - s));
				RMICacheHolder.newInstance().clearThreadLocal();
				s = System.currentTimeMillis();
				service.getUserDOByID(110L);
				service.getUserDOByIdWraper(110L);
				service.getUserDOByIdWraper(110L);
				System.out.println("�̻߳��������,�첽������������,һ��Զ�̣�һ�λ��棬��ʱ��"+(System.currentTimeMillis() - s));
			}}.start();;
		
		Thread.sleep(2000);

	}
}
