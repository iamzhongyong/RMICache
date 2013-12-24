package rmicache;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存信息的持有类
 * @author bixiao.zy
 *
 */
public class RMICacheHolder {

	private RMICacheHolder() {}
	
	private static RMICacheHolder rmiCacheHolder = new RMICacheHolder();
	
	/** 缓存的实体对象, map结构，一个线程体内，可能缓存多个方法的对象 */
	private ThreadLocal<Map<String/*方法的签名信息*/,Object/*线程缓存的对象*/>> cacheEntry = new ThreadLocal<Map<String,Object>>(){
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String,Object>();
		}
	};
	
	/**构造单例*/
	public static RMICacheHolder newInstance(){
		return rmiCacheHolder;
	}
	
	/**根据方法签名，获取缓存对象，如果没有，则通过callback的形式来放入到缓存中*/
	public Object getEntry(String methedSign,RMICacheCallback callback){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		Object cacheValue = cacheObject.get(methedSign);
		if(null == cacheValue){
			cacheValue = callback.RMIGet();
			cacheObject.put(methedSign, cacheValue);
		}
		return cacheValue;
	}
	
	/**根据方法的签名，获取方法的缓存对象*/
	public Object getEntry(String methodSign){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		return cacheObject.get(methodSign);
	}
	
	/**缓存之中，放入数据*/
	public void putEntry(String methodSign,Object obj){
		Map<String,Object> cacheObject = RMICacheHolder.newInstance().cacheEntry.get();
		cacheObject.put(methodSign, obj);
	}
	
	/**清理线程缓存中的数据，由于现在大多数都是基于线程池的使用，所以这不清理操作必须存在*/
	public void clearThreadLocal(){
		RMICacheHolder.newInstance().cacheEntry.set(new HashMap<String,Object>());
	}
	
}
