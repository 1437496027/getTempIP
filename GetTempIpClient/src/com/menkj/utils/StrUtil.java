package com.menkj.utils;

import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**   
 *    
 * ��Ŀ���ƣ�tyylc   
 * �����ƣ� StrUtil   
 * ��������   
 * �����ˣ� lsc  
 * ����ʱ�䣺Aug 30, 2011 1:06:56 PM   
 * �޸��ˣ� lsc   
 * �޸�ʱ�䣺Aug 30, 2011 1:06:56 PM   
 * �޸ı�ע��   
 * @version    
 *    
 */

public class StrUtil {
	
	/**
	 * ���� map������json��ʽ�ַ���
	 * map �а���map
	 * 
	 * @return
	 *      ��mapΪ�գ���Żؿմ� ""
	 *      
	 */
	public static String getJsonByMap(Map map){
		
		//����resultMap
		Set<String> key = (Set<String>) map.keySet();
		
		StringBuffer sb = new StringBuffer();
		int i=0;
        for (Iterator it = key.iterator(); it.hasNext();) {
        	
            Map contentMap = (Map)map.get(it.next());
            if(contentMap.isEmpty()) return "";
            
            if(i != 0){//��һ��û�ж���
            	sb.append(",");
            }
            sb.append("{");
            //����contentMap
            Set contentset = contentMap.entrySet();
            int j=0;
            for (Iterator it1 = contentset.iterator(); it1.hasNext();) {
                Map.Entry entry = (Map.Entry) it1.next();
                if(j != 0) sb.append(","); //��һ��û�ж���
                sb.append(entry.getKey()+":'"+entry.getValue()+"'");
                j++;
            }    		
            sb.append("}");
            i++;
        }
        
		return sb.toString();
	}
	
	/**
	 * ��ȡϵͳ�������
	 */
	public static String[] getSystemFonts(){
		
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//��ȡϵͳ�Դ��������
		
	}
	
	/**
	* �ж��Ƿ�Ϊ��
	* 0 Ϊ��
	* 1-����
	* */
	public static byte isNull(String args){
		
			byte res= 0;
			if(args==null||args.equals("")||args.equals("null")){
				res = 0;
			}else{
				res = 1;
			}
			return res;
			
		}
	/**
	 * ���ֽ�����תΪ16������ʽ
	 * @param b
	 * @return
	 */
	public static String bytes2Hex(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	/**
	 * ����ת��
	 */
	public static byte getByte(String s ,String i){
		
		return Byte.parseByte((s==null||s.equals("")||s.equals("null"))?i:s);
	}
	
	public static int getInteger(String s ,String i){
		
		return Integer.parseInt((s==null||s.equals("")||s.equals("null"))?i:s);
	}
	public static String getString(String s){
		return (s==null)?"":s;
	}
	
	/**
	 * �ַ����滻 ��ȡʱstrContent.Replace("\r\n","<br/>");
	 * ��  		pname= pname.replaceAll("&quot;","\"");
     *          pname= pname.replaceAll("&amp;","&");
	 */
	public static String waphtml2Str(String str){
		
		if(str==null) {
			return "";
		}
		str=str.replaceAll("&quot;","\"");
		str=str.replaceAll("&amp;","&");
		str=str.replaceAll("&lt;","<");
		str=str.replaceAll("&gt;",">");
	    return str;
	}
	
	
	/**
	 * �ַ����滻 ���
	 * ��  		pname= pname.replaceAll("&quot;","\"");
     *          pname= pname.replaceAll("&amp;","&");
	 */
	public static String str2Waphtml(String str){
		
		if(str==null) {
			return "";
		}
		str=str.trim();
		str=str.replaceAll("\"","&quot;");
		str=str.replaceAll("\'","��");
		str=str.replaceAll("&","&amp;");
		str=str.replaceAll("<","&lt;");
		str=str.replaceAll(">","&gt;");
	    return str;
	}
	
	/**
	 * ��ָ�����Ƚ�ȡ�ַ���
	 * @param strԴ��
	 * @param length ��ȡ�ĳ���
	 * @return
	 * 		���Դ���ĳ���>Ӧ��ȡ�ĳ��ȣ����ַ������н�ȡ�󷵻أ�
	 *      ���Դ���ĳ���<Ӧ��ȡ�ĳ��ȣ��Ż�Դ��
	 */
	public static String substring(String str,int length){
	
			if(str==null) return "";
			if(length==0){
				return str;
			}
			int strlen = str.length();
			return strlen>length?str.substring(0,length)+"..":str;
	}
	
	/**
	 *���źŶ�133,153,180,189
	 *@param phone ��86���ֻ�����
	 *@ return 
	 *         true ƥ��ɹ�
	 *         false ƥ��ʧ��
	 **/
	@Deprecated
	public  static boolean checkPhoneNbl1(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	/**
	 *���źŶ�133,153,180,189,181
	 *@param phone ��86���ֻ�����
	 *@ return 
	 *         true ƥ��ɹ�
	 *         false ƥ��ʧ��
	 **/
	public  static boolean checkPhone4TeleWith86(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	/**
	 * ��ͨ�Ŷ�130��131��132��155��156��186��������3G�û�Ĭ�ϺŶΣ���145������������ר�ã�
	 * ���źŶ�133,153,180,189,181
	 * @param phone
	 * @return
	 */
	public  static boolean checkPhoneNbl(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	
	/**
	 * ������תΪ�ַ���
	 * @param arr
	 * @return
	 */
	
	public String arrayToString(String arr[]){
		  if(arr==null)return "";
		  int allLength=arr.length;
		  String string = "";
		  for(int i=0;i<allLength;i++){
			 /* if(i==0){
				  string=arr[0];
			  }else{
				  string=string+","+arr[i];
			  }
			  */
			  if(arr[i]==null||arr[i].equals("0")){ continue;}
			  string=string+arr[i]+",";
		  }
		  System.out.println("String:"+string);
		  if(string!=null){
			  string = string.substring(0,string.length()-1);
		  }
		  return string;
	  }
	  /**
	   * �������� '1','2','3'���ַ���
	   * @param arr
	   * @return
	   */
	  public static String arrayToString1(String arr[]){
		  if(arr==null)return "";
		  int allLength=arr.length;
		  String string = "";
		  for(int i=0;i<allLength;i++){
			 /* if(i==0){
				  string=arr[0];
			  }else{
				  string=string+","+arr[i];
			  }
			  */
			 // System.out.println(arr[i]);
			  if(arr[i]==null||arr[i].equals("0")){ continue;}
			  string=string+"'"+arr[i]+"',";
		  }
		  //System.out.println("String:"+string);
		  if(string!=null){
			  string = string.substring(0,string.length()-1);
		  }
		 
		  return string;
	  }
	
    /**
	 * �����Ӵ���ĸ���е�n�γ��ֵ�λ�ú�����Ӵ�
	 */
	public static String getSubIndexInStr(String mStr,String nStr, int level,String newStr){
		String nowStr = "";
		if(mStr != null){
			if(level==0){
				nowStr = newStr+nStr;
			}else{
				String mstrs[] = mStr.split(nStr);
				if(mstrs.length>=level){
					for(int j = 0; j < level ; j++){
						nowStr += mstrs[j]+nStr;
					}
					nowStr+=newStr+nStr;
				}
			}
		}
		return nowStr;
	}
	
	/**
	 * ���ַ������ָ������зֽ⣬�����طֽ�������
	 * @param sourceStr   Դ��
	 * @param separator   �ָ���
	 * @return            �ַ�������
	 * 					  ��Դ��Ϊ�գ�����null
	 */
	public static String[] getSplitStrWithArray(String sourceStr , String separator ){

		if(isNull(sourceStr)==1){
			
			String newStr[] = sourceStr.split(separator);
			return newStr;
			
		}else{
			
			return null;
			
		}
	}
	
	/**
	 * ���ݹ̶����Ƚ�ȡ�ַ���
	 * content:����
	 * length:����ȡ�ĳ���
	 * @return
	 */
	public static String cutStringByLength(String content,int length){
		if(content==null) return "";
		int clength = content.length();
		if(length<clength){
			return content.substring(0,length)+"...";
		}
		return content;
	}
	
	/**
	 * ���ݹ̶����Ƚ�ȡ�ַ���
	 * content:����
	 * length:����ȡ�ĳ���
	 * @return
	 */
	public static String cutstring(String content,int length){
		if(content==null) return "";
		int clength = content.length();
		if(length<clength){
			return content.substring(0,length-3)+"...";
		}
		return content;
	}
	
	/**
	 * �ַ����滻 
	 * ���ַ���ָ��λ�õ��ַ����滻Ϊָ����
	 * @param args
	 */
	public static String getReplaceStr(int start,int end,String srcStr,String replaceStr){
		
		if(replaceStr==null || srcStr.length()<end) 
			return srcStr;
		
		StringBuffer sb= new StringBuffer();
		sb.append(srcStr.substring(0,start));
		for(int i=0;i<end-start;i++){
			sb.append(replaceStr);
		}
		
		sb.append(srcStr.substring(end));
		return sb.toString();

	}
	
	/**  
     * �ж�number�����Ƿ�����������ʾ��ʽ  
     * @param number  
     * @return  
     */  
    public static boolean isIntegerNumber(String number){
        number=number.trim();   
        String intNumRegex="\\-{0,1}\\d+";//������������ʽ   
        if(number.matches(intNumRegex))   
            return true;   
        else  
            return false;   
    } 
    /**  
     * �ж�number�����Ƿ��Ǹ�������ʾ��ʽ  
     * @param number  
     * @return  
     */  
    public static boolean isFloatPointNumber(String number){
    	
        number=number.trim();   
        String pointPrefix="(\\-|\\+){0,1}\\d*\\.\\d+";//��������������ʽ-С�������м���ǰ��   
        String pointSuffix="(\\-|\\+){0,1}\\d+\\.";//��������������ʽ-С�����ں���   
        if(number.matches(pointPrefix)||number.matches(pointSuffix))   
            return true;   
        else  
            return false;
        
    }
	
	public static void main(String[] args){
//		if(StrUtil.checkPhoneNbl1("8613335169899")){
//			System.out.println("ƥ��ɹ�");
//		}
		System.out.println(isIntegerNumber("13") || isFloatPointNumber("23"));
	}
}
