package rmicache;

import java.util.HashMap;
import java.util.Map;

/**
 * ������Ϣ�ĳ�����
 * @author bixiao.zy
 *
 */
public class RMICacheHolder {

	private RMICacheHolder() {}
	
	private static RMICacheHolder rmiCacheHolder = new RMICacheHolder();
	
	/** �����ʵ�����, map�ṹ��һ���߳����ڣ����ܻ����������Ķ��� */
	private ThreadLocal<Map<String/*������ǩ����Ϣ*/,Object/*�̻߳���Ķ���*/>> cacheEntry = new ThreadLocal<Map<String,Object>>(){
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String,Object>();
		}
	};
	
	/**���쵥��*/
	public static RMICacheHolder newInstance(){
		return rmiCacheHolder;
	}
	
	/**���ݷ���ǩ������ȡ����������û�У���ͨ��callback����ʽ�����뵽������*/
	public Object getEntry(String methedSign,RMICacheCallback callback){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		Object cacheValue = cacheObject.get(methedSign);
		if(null == cacheValue){
			cacheValue = callback.RMIGet();
			cacheObject.put(methedSign, cacheValue);
		}
		return cacheValue;
	}
	
	/**���ݷ�����ǩ������ȡ�����Ļ������*/
	public Object getEntry(String methodSign){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		return cacheObject.get(methodSign);
	}
	
	/**����֮�У���������*/
	public void putEntry(String methodSign,Object obj){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		cacheObject.put(methodSign, obj);
	}
	
	/**�����̻߳����е����ݣ��������ڴ�������ǻ����̳߳ص�ʹ�ã������ⲻ��������������*/
	public void clearThreadLocal(){
		RMICacheHolder.newInstance().cacheEntry.set(new HashMap<String,Object>());
	}
	
}
