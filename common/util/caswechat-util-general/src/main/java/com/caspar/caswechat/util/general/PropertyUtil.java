package com.caspar.caswechat.util.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 * 属性常量读取
   <bean class="this class">
    	<property name="reversed" value="false" />
        <property name="locations">
        	<list>
				<value>CLASSPATH*:1.properties</value>
				<value>FILE:x:/1.properties</value>
				<value>FILE:x:/2.properties</value>
        	</list>
        </property>
    </bean>
    reversed = false ,逆序 ,下覆盖上 ,默认缺省
    reversed =  true ,正序 ,上覆盖下
    
 * @author caspar.chen
 * @version 1.0.0, 2016-9-26
 * @see PropertyPlaceholderConfigurer
 */
	
public class PropertyUtil extends PropertyPlaceholderConfigurer {
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");  
	private static Properties FIXED_PROPERTIES = new Properties();
	private static Map<String,String> CONSTS = null;
	/**
	 * 默认false,逆序的,下面覆盖上面
	 */
	private boolean reversed = false;
	@SuppressWarnings("rawtypes")
	private Class<? extends Map> mapType = HashMap.class;
	
	public final static void addFixedProperty(String k,String v){
		FIXED_PROPERTIES.setProperty(k,v);
	}
	public final static void addFixedProperties(Map<String,String> m){
		FIXED_PROPERTIES.putAll(m);
	}
	
	@SuppressWarnings("unchecked")
	public void tryToInitMapType(){
		if( CONSTS == null ){
			try {
				CONSTS = mapType.newInstance();
			} catch (Exception e) {
				 throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public void setLocations(Resource... locations){
		tryToInitMapType();
		
		if (ArrayUtil.isNotEmpty(locations) && reversed ){//反转
			CollectionUtils.reverseArray(locations);
		}
		
		List<Resource> existResourceList = new ArrayList<Resource>();
		
		for( Resource resource : locations ){
			if (resource.exists()){
				existResourceList.add(resource);
			}
		}			
		
		for(Resource resource : existResourceList){
			try{
				initProperties(resource);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		Resource[] existResourceArray = new Resource[existResourceList.size()];	
		super.setLocations(existResourceList.toArray(existResourceArray));
	}

	private synchronized static void initProperties(Resource sysEnvResource) throws Exception{
		Properties properties = new Properties();
		InputStream is = sysEnvResource.getInputStream();
		try{			
			properties.load(is);
	    	Set<Entry<Object, Object>> sets = properties.entrySet();
	    	for(Entry<Object, Object> entry : sets){
	    		CONSTS.put(entry.getKey().toString(), entry.getValue().toString());
			}
		}catch(Exception e){
			throw e;
		}finally{
			IOUtils.closeQuietly(is);
			is = null;
		}
	}
	
	@Override
	protected void loadProperties(Properties props) throws IOException {	
		super.loadProperties(props);
		props.putAll(FIXED_PROPERTIES);
		for( Entry<Object,Object> en : FIXED_PROPERTIES.entrySet() ){
			CONSTS.put(en.getKey().toString(),en.getValue().toString());
		}
		show();
	}

	public void show(){
		Map<String,String> properties = new TreeMap<String,String>();
		exportTo(properties);
		StringBuilder sBuilder = new StringBuilder("Show Properties:");
		sBuilder.append(LINE_SEPARATOR).append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		for( Entry<String,String> en : properties.entrySet() ){
			sBuilder.append(LINE_SEPARATOR)
				.append("   ")
				.append(en.getKey())
				.append("=")
				.append(en.getValue())
			;
		}
		sBuilder.append(LINE_SEPARATOR).append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		System.out.println(sBuilder.toString());
	}
	
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	@SuppressWarnings({"rawtypes" })
	public void setMapType(Class<? extends Map> mapType)throws Exception{
		if( Map.class.equals(mapType) ){
			this.mapType = HashMap.class;
		}else{
			this.mapType = mapType;
		}		
	}

	public static void exportHeadedTo(Map<String,String> map,String keyStartWith){

		if( StringUtil.isNotBlank(keyStartWith) ){
			for( Entry<String,String> entry : CONSTS.entrySet() ){
				if( StringUtil.startsWith(entry.getKey(), keyStartWith) ){
					map.put(entry.getKey(),entry.getValue());
				}
			}
		}
		
	}
	public static void exportTo(Map<String,String> map){
		map.putAll(CONSTS);
	}
	public static String get(String key){
		return CONSTS.get(key);
	}
	public static Integer getInteger(String key){
		String value = StringUtil.trim(CONSTS.get(key));
		if( StringUtil.isBlank(value) ){
			return null;
		}
		try{
			return Integer.parseInt(value);
		}catch( NumberFormatException e ){
			return null;
		}
	}
	public static int getInteger(String key,int defaultValue){
		Integer value = getInteger(key);
		if( value == null ){
			return defaultValue;
		}else{
			return value;
		}
	}
	public static Long getLong(String key){
		String value = StringUtil.trim(CONSTS.get(key));
		if( StringUtil.isBlank(value) ){
			return null;
		}
		try{
			return Long.parseLong(value);
		}catch( NumberFormatException e ){
			return null;
		}
	}
	public static long getLong(String key,long defaultValue){
		Long value = getLong(key);
		if( value == null ){
			return defaultValue;
		}else{
			return value;
		}
	}
	public static Boolean getBoolean(String key){
		String value = StringUtil.trim(CONSTS.get(key));
		if( StringUtil.isBlank(value) ){
			return null;
		}
		return Boolean.parseBoolean(value);
	}
	public static boolean getBoolean(String key,boolean defaultValue){
		Boolean value = getBoolean(key);
		if( value == null ){
			return defaultValue;
		}else{
			return value;
		}
	}
	public static Double getDouble(String key) {
		String value = StringUtil.trim(CONSTS.get(key));
		if( StringUtil.isBlank(value) ){
			return null;
		}
		return Double.parseDouble(value);
	}
	public static double getDouble(String key,double defaultValue) {
		Double value = getDouble(key);
		if( value == null ){
			return defaultValue;
		}else{
			return value;
		}
	}	
}
