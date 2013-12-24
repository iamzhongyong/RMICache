package test;

import rmicache.RMICacheCallback;
import rmicache.RMICacheHolder;

/**
 * �������в��Եķ���
 * @author bixiao.zy
 *
 */
public class BizService {

	/**
	 * ����������ݵĻ�ȡ�ǳ�����ʱ�䣬��һ���̴߳�������У������߼����ӣ����ܱ�֤����������˵��ö��
	 * ���ϵͳ���ڸ߲�������Ӧʱ���кܸߵ�Ҫ����ô��һ����ʱ�ĵ��ã��Ƿǳ������ġ�
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
	 * Զ�̵İ�װ��
	 */
	public UserDO getUserDOByIdWraper(final Long userId){
		return (UserDO) RMICacheHolder.newInstance().getEntry("getUserDOByID", new RMICacheCallback() {		
			public Object RMIGet() {
				return getUserDOByID(userId);
			}
		});
	}

}
