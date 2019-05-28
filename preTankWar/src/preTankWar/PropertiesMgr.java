package preTankWar;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {
	static Properties props = new Properties();
	static {

		try {
			//加载配置文件
			props.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}
	
	private PropertiesMgr() {};
	
	public static String getProperty(String key) {
		
		return props.getProperty(key);	//返回key所对应的映射值
	}
}
