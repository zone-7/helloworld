package com.zone7.demo.helloworld.utils;

import net.sf.ezmorph.bean.MorphDynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.Base64Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MapBeanUtil {

	public static String DEFAULT_DATE_FORMATE="yyyy-MM-dd hh:mm:ss";
     
	/**
	 * 对象转化成XMl
	 * @param bean 对象
	 * @param root 跟节点
	 * @param registClasses 
	 * @return
	 */
	public static Element beanToXml(Object bean,String root,Class[] registClasses){
  
		
		Element xml=DocumentHelper.createElement(root);
		
		if(bean==null)return xml;
		
		xml.addAttribute("class", bean.getClass().getName());
			 
		if(bean instanceof String){

			xml.setText(bean.toString()); 
		
		}else if(bean instanceof Date){
			String val=dateToStr((Date)bean, DEFAULT_DATE_FORMATE); 
			xml.setText(val);
		}else if(bean instanceof java.sql.Date){
			
			String val=dateToStr((java.sql.Date)bean, DEFAULT_DATE_FORMATE); 
			xml.setText(val);
		}else if(bean instanceof Timestamp ){
			try{

				String val=dateToStr(new java.sql.Date(((Timestamp)bean).getTime()), DEFAULT_DATE_FORMATE);

				xml.setText(val);
			}catch(Exception e){}

		}else if(bean instanceof Map){

			Map m=((Map)bean);
			for(Iterator it=m.keySet().iterator();it.hasNext();){
				Object key=it.next();
				Object val=m.get(key);

				Element child=beanToXml(val,key.toString(),registClasses);
				xml.add(child);

			}

			return xml;

		}else if(bean instanceof Collection){
			Collection coll=(Collection)bean;
			for(Iterator it=coll.iterator();it.hasNext();){
				Object obj=it.next();
				Element child=beanToXml(obj,obj.getClass().getSimpleName().toLowerCase(),registClasses);
				xml.add(child);
			}
			return xml;

		}else if(bean.getClass().isArray()){
			try{
				Class cls=bean.getClass();
				if( byte[].class.getName().equals( cls.getName()) ){

					byte[] coll=(byte[])bean;

					byte[] coded= Base64Utils.encode(coll);
					String val=new String(coded,"UTF-8");
					Element child=beanToXml(val,root,registClasses);

					xml.add(child);
				}else{


					Object[] coll=(Object[])bean;
					for(int i=0;i<coll.length;i++){
						Object obj=coll[i];
						Element child=beanToXml(obj,obj.getClass().getSimpleName().toLowerCase(),registClasses);
						xml.add(child);
					}

				}

			}catch(Exception e){}
			return xml;

		}else{

			boolean isreg=false;
			for(int i=0;i<registClasses.length;i++){
				if(bean.getClass().getName().equals(registClasses[i].getName())){
					isreg=true;
					break;
				}
			}

			if(isreg){

				Method[] methods=bean.getClass().getMethods();
				for(int i=0;i<methods.length;i++){
					Method m = methods[i];
					String fullName=m.getName();
					if(fullName.indexOf("get")!=0 )continue;

					String name=fullName.replace("get", "");

					if(name.toLowerCase().equals("class"))continue;

					name=name.substring(0,1).toLowerCase()+name.substring(1,name.length());

					try {
						Object val=m.invoke(bean, new Object[]{});
						Element child=beanToXml(val, name, registClasses);
						xml.add(child);
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}

				}

			}else{
				xml.setText(bean.toString());
			}


		}

		return xml;
	}
	public static Object xmlToBean(Element xml){
		if(xml==null)return null;

		String clazz=null;
		Attribute classAtr=xml.attribute("class");
		if(classAtr==null){
			classAtr=xml.attribute("className");
		}
		if(classAtr!=null){
			clazz=classAtr.getValue();
		}


		if(clazz==null)clazz=String.class.getName();

		//用于兼容就版本的包路径
		if(clazz.startsWith("org.zreport.core")){
			clazz = clazz.replace("org.zreport.core", "org.zreport.reporter");
		}


		Object bean=null;


		if(clazz.equals(String.class.getName())){
			bean= xml.getText();

		}else if(clazz.equals(Integer.class.getName())){

			bean= Integer.parseInt(xml.getText());

		}else if(clazz.equals(Double.class.getName())){
			bean= Double.parseDouble( xml.getText());

		}else if(clazz.equals(Date.class.getName())){
			bean= strToDate(  xml.getText() , DEFAULT_DATE_FORMATE );

		}else if(clazz.equals(java.sql.Date.class.getName())){
			bean = new java.sql.Date(strToDate(  xml.getText() , DEFAULT_DATE_FORMATE ).getTime());

		}else if(clazz.equals(Timestamp.class.getName())){
			bean = new Timestamp(strToDate( xml.getText() , DEFAULT_DATE_FORMATE ).getTime() );

		}else if(clazz.equals(Map.class.getName())
				||clazz.equals(HashMap.class.getName()) ){

			bean=new HashMap();

			List els=xml.elements();
			for(Iterator it=els.iterator();it.hasNext();){
				Element el=(Element)it.next();
				String key=el.getName();
				Object val=xmlToBean(el);
				((Map)bean).put(key, val);
			}

		}else if(clazz.equals(Collection.class.getName())
				||clazz.equals(List.class.getName())
				||clazz.equals(ArrayList.class.getName())){

			bean=new ArrayList();
			List els=xml.elements();
			for(Iterator it=els.iterator();it.hasNext();){
				Element el=(Element)it.next();
				Object val=xmlToBean(el);
				((Collection)bean).add(val);
			}

		}else if(clazz.equals(Set.class.getName())
				||clazz.equals(HashSet.class.getName())){

			bean=new HashSet();
			List els=xml.elements();
			for(Iterator it=els.iterator();it.hasNext();){
				Element el=(Element)it.next();
				Object val=xmlToBean(el);
				((Set)bean).add(val);
			}
		}else{
			try {
				bean = Class.forName(clazz).newInstance();

				Method[] methods = bean.getClass().getMethods();

				for(int i=0;i<methods.length;i++){
					Method m=methods[i];
					Class[] types=m.getParameterTypes();

					String fullName=m.getName();

					if(fullName.equals("getInteger")
							||fullName.equals("getClass")
							||fullName.equals("getModifiers"))continue;

					if(fullName.indexOf("set")!=0 || types==null || types.length<=0)continue;

					Class type=types[0];
					String name=fullName.replace("set", "");
					name=name.substring(0,1).toLowerCase()+name.substring(1,name.length());

					Element el=xml.element(name);

					if(el==null)continue;

					Object val = xmlToBean(el);
					try {
						m.invoke(bean, new Object[]{val});
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		return bean;

	}



	/**
	 * 从map 到bean 的转化
	 * @param map
	 * @param bean
	 * @throws Exception
	 */
    public static void mapToBean(Map map,Object bean) {

   		Method[] methods=bean.getClass().getMethods();
   		for(int i=0;i<methods.length;i++){
   			String name=methods[i].getName();

   			if(name.length()<=3 || !name.substring(0,3).equals("set") ){
   				continue;
   			}
   			name=name.substring(3,name.length());


   			name = name.substring(0,1).toLowerCase() + name.substring(1,name.length());

   			if(name.equals("class")){
  				continue;
  			}


  			Object val=map.get(name);
  			if(val!=null){
 	 			Class[] types = methods[i].getParameterTypes();
 	 			if(types.length<=0)continue;
 	 			if(types[0].equals(java.sql.Date.class)){

 	 				if(val instanceof Timestamp){
 	 					val=new java.sql.Date(((Timestamp)val).getTime());
 	 				}else if(val instanceof Date){

 	 					val=new java.sql.Date(((Date)val).getTime());
 	 				}else if(!(val instanceof java.sql.Date) ){
 	 					try{
 	 						val=strToDate( val.toString(), DEFAULT_DATE_FORMATE);

 		 				}catch(Exception e){
 							val=null;
 						}
 	 				}
 	 			}else if(types[0].equals(Date.class)){

 	 				if(val instanceof Timestamp){
 	 					val=new Date(((Timestamp)val).getTime());
 	 				}else if(val instanceof java.sql.Date){

 	 					val=new Date(((java.sql.Date)val).getTime());
 	 				}else if(!(val instanceof Date) ){
 	 					try{
 	 						Date d=strToDate( val.toString(), DEFAULT_DATE_FORMATE);
 	 						val=new java.sql.Date(d.getTime());

 		 				}catch(Exception e){
 		 					val=null;
 						}
 	 				}
 	 			}else if(types[0].equals(BigDecimal.class)){
 	 				if(!(val instanceof BigDecimal) ){
 	 					try{
 	 					val=new BigDecimal(val.toString());
 	 					}catch(Exception e){
 	 						val=null;
 	 					}
 	 				}
 	 			}else if(types[0].equals(Long.class)){
 	 				if(!(val instanceof Long) ){
 	 					try{
 	 						val=new Long(val.toString());
 	 					}catch(Exception e){
 	 						val=null;
 	 					}
 	 				}
 	 			}else if(types[0].equals(Double.class)){
 	 				if(!(val instanceof BigDecimal) ){
 	 					try{
 	 						val=new Double(val.toString());
 	 					}catch(Exception e){
 	 						val=null;
 	 					}
 	 				}
 	 			}else if(types[0].equals(Integer.class)){
 	 				if(!(val instanceof Integer) ){
 	 					try{
 	 						val=new Integer(val.toString());
 	 					}catch(Exception e){
 	 						val=null;
 	 					}
 	 				}
 	 			}else if(types[0].equals(Float.class)){
 	 				if(!(val instanceof Float) ){
 	 					try{
 	 					    val=new Float(val.toString());
 	 					}catch(Exception e){
 	 						val=null;
 	 					}
 	 				}
 	 			}else if(types[0].equals(String.class)){
 	 				if(!(val instanceof String) ){
 	 					val= val.toString() ;
 	 				}
 	 			}else if(types[0].equals(Boolean.class)){

 	 				if(!(val instanceof Boolean) ){
 	 					if(val.toString().toLowerCase().equals("true")){

 	 						val=true;
 	 					}else if(val.toString().toLowerCase().equals("false")){
 	 						val=false;

 	 					}else{
	 	 					try{
	 	 	 					int v=Integer.parseInt(val.toString());
	 	 	 					if(v>0){
	 	 	 						val=true;
	 	 	 					}else{
	 	 	 						val=false;
	 	 	 					}

	 	 	 				}catch(Exception e){
	 	 	 					val=false;
	 	 	 				}
 	 					}
 	 				}



 	 			}else if(types[0].equals(Timestamp.class)){

 	 				if(val instanceof java.sql.Date){

 	 	 				val=new Timestamp(((java.sql.Date)val).getTime());

 	 				}else if(val instanceof Date){

 	 					val=new Timestamp(((Date)val).getTime());
 	 				}else if(!(val instanceof Timestamp) ){
 	 					try{
 	 						val=new Timestamp(strToDate( val.toString(), DEFAULT_DATE_FORMATE).getTime());

 		 				}catch(Exception e){
 							val=null;
 						}
 	 				}
 	 			}


  				try {
					methods[i].invoke(bean,new Object[]{val});
				} catch (IllegalArgumentException e) {

				} catch (IllegalAccessException e) {

				} catch (InvocationTargetException e) {

				}
  			}
   		}

       }


     /**
      * 从bean 到map 的转化
      * @param obj
      * @return
      * @throws Exception
      */
     public static Map beanToMap(Object obj) {
 	    if(obj instanceof Map){
 	    	return (Map)obj;
 	    }

 	    if(obj instanceof MorphDynaBean){
 	    	return morphDynaBeanToMap((MorphDynaBean)obj);
 	    }

 	    Map resMap=new HashMap();

 		Method[] methods=obj.getClass().getMethods();
 		for(int i=0;i<methods.length;i++){
 			String name=methods[i].getName();

 			if(name.length()<=3 || !name.substring(0,3).equals("get") ){
 				continue;
 			}
 			name=name.substring(3,name.length());

 			name = name.substring(0,1).toLowerCase() + name.substring(1,name.length());



			if(name.equals("class")){
				continue;
			}


 			Object val=null;
			try {
				val = methods[i].invoke(obj);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
 			if(val==null)continue;

 			String type=methods[i].getReturnType().getName();
 			if(val instanceof java.sql.Date ){
 				try{

 					val=dateToStr((java.sql.Date)val, DEFAULT_DATE_FORMATE);
 				}catch(Exception e){
 					continue;
 				}
 			}if(val instanceof Date ){
 				try{

 					val=dateToStr(new java.sql.Date(((Date)val).getTime()), DEFAULT_DATE_FORMATE);
 				}catch(Exception e){
 					continue;
 				}
 			}else if(val instanceof Timestamp ){
 				try{

 					val=dateToStr(new java.sql.Date(((Timestamp)val).getTime()), DEFAULT_DATE_FORMATE);
 				}catch(Exception e){
 					continue;
 				}

 			}


 			resMap.put(name, val);
 		}
     	 return resMap;
      }

     public static String dateToStr(Date date,String format){
    	 
    	 SimpleDateFormat df=new SimpleDateFormat(format);
    	 return df.format(date); 
    	 
     }
     public static String dateToStr(java.sql.Date date,String format){
    	 
    	 SimpleDateFormat df=new SimpleDateFormat(format);
    	 return df.format(date); 
    	 
     }
     public static Map morphDynaBeanToMap(MorphDynaBean item){
    	 Map res=new HashMap();
    	 
    	 DynaProperty[] ps=item.getDynaClass().getDynaProperties();
    	 
    	 for(int i=0;i<ps.length;i++){
    		 String name=ps[i].getName();
   		 
    		 Object val=item.get(name);
    		 
    		 res.put(name, val);
    	 }
    	 
    	 return res;
     }
     public static java.sql.Date strToDate(String dateStr,String format){
    	 if(dateStr==null || dateStr.trim().equals(""))return null;
    	 String year="0";
    	 Pattern p=Pattern.compile("y{1,4}",Pattern.CASE_INSENSITIVE);
    	 Matcher m=p.matcher(format);
    	 if(m.find()){
    		 
    		 String yf=m.group();
    		 int y=format.indexOf(yf);
    		 if(y>=0){
    			year=dateStr.substring(y,y+yf.length());  
    		 } 
    		 
    	 }
    	 
    	 String month="0";
    	 p=Pattern.compile("M{1,2}");
    	 m=p.matcher(format);
    	 if(m.find()){ 
    		 String mf=m.group();
    		 int mm=format.indexOf(mf);
    		 if(mm>=0){
    			 month=dateStr.substring(mm,mm+mf.length());  
    		 } 
    		 
    	 }
    	 
    	 String day="0";
    	 p=Pattern.compile("d{1,2}",Pattern.CASE_INSENSITIVE); 
    	 m=p.matcher(format);
    	 
    	 if(m.find()){ 
    		 String df=m.group();
    		 int d=format.indexOf(df);
    		 if(d>=0){
    			 day=dateStr.substring(d,d+df.length());  
    		 }  
    	 }
    	 
    	 String hour="0";
    	 p=Pattern.compile("h{1,2}",Pattern.CASE_INSENSITIVE); 
    	 m=p.matcher(format);
    	 
    	 if(m.find()){ 
    		 String hf=m.group();
    		 int h=format.indexOf(hf);
    		 if(h>=0){
    			 hour=dateStr.substring(h,h+hf.length());  
    		 }  
    	 }
    	 String min="0";
    	 p=Pattern.compile("m{1,2}"); 
    	 m=p.matcher(format);
    	 
    	 if(m.find()){ 
    		 String mif=m.group();
    		 int mi=format.indexOf(mif);
    		 if(mi>=0){
    			 min=dateStr.substring(mi,mi+mif.length());  
    		 }  
    	 }
    	 
    	 String sec="0";
    	 p=Pattern.compile("s{1,2}"); 
    	 m=p.matcher(format);
    	 
    	 if(m.find()){ 
    		 String sf=m.group();
    		 int s=format.indexOf(sf);
    		 if(s>=0){
    			 sec=dateStr.substring(s,s+sf.length());  
    		 }  
    	 }
    	  
    	 
    	 Calendar ca=Calendar.getInstance();
    	 ca.set(Integer.parseInt(year),Integer.parseInt( month)-1, Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(min),Integer.parseInt(sec) );
    	 
    	 return new java.sql.Date(ca.getTimeInMillis());
    	  
    	 
     }
	
	 public static void main(String[] args){
		  
		
		 
		 
		 
	 }
	
}
