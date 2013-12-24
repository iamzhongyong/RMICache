package test;

import rmicache.RMICacheCallback;
import rmicache.RMICacheHolder;

/**
 * 辅助进行测试的方法
 * @author bixiao.zy
 *
 */
public class BizService {

	/**
	 * 假设这个数据的获取非常消耗时间，在一个线程处理过程中，由于逻辑复杂，不能保证这个方法被人调用多次
	 * 如果系统对于高并发和响应时间有很高的要求，那么多一个耗时的调用，是非常致命的。
	 */
	public UserDO getUserDOByID(Long userId){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		UserDO user = new UserDO();
		
		user.setName("iamzhongyong_"+Thread.currentThread().getName());
		user.setAge(18);
		return user;
	}
	/**
	 * 远程的包装类
	 */
	public UserDO getUserDOByIdWraper(final Long userId){
		return (UserDO) RMICacheHolder.newInstance().getEntry("getUserDOByID", new RMICacheCallback() {		
			public Object RMIGet() {
				return getUserDOByID(userId);
			}
		});
	}

}
