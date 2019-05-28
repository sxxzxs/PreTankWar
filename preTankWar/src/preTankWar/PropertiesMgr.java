package preTankWar;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {
	static Properties props = new Properties();
	static {

		try {
			//load(InputStream inStream)从输入字节流中读取属性列表(键和元素对),返回值是void。
			props.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}
	
	private PropertiesMgr() {};
	
	public static String getProperty(String key) {
		
		return props.getProperty(key);	//返回key所对应的元素对(有点像Map里的映射)
	}
}
