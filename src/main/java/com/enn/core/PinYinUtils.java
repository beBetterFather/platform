package com.enn.core;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Description 汉字和拼音的转换类
 * @author jinsiwei
 */
public class PinYinUtils {
	
	/**
	 * @param
	 * @Description:
	 * 	传入中文汉字，转换出对应的拼音
	 * 	当汉字是多音字时，取汉字全拼的第一种读音
	 */
	public static String getPinYin(String src){
		char[] r1 = null;
		r1 = src.toCharArray();
		String[] t2 = new String[5];
		
		//设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        
        String t4 = "";
        try{
        	for(int i=0; i<r1.length; i++){
            	//判断是否是汉字
            	if(Character.toString(r1[i]).matches("^[\u4E00-\u9FFF]+$")){
            		t2 = PinyinHelper.toHanyuPinyinStringArray(r1[i], t3);
            		t4 += t2[0];
            	}else{
            		t4 += Character.toString(r1[i]);
            	}
            }
        }catch(BadHanyuPinyinOutputFormatCombination e){
        	return null;
        }
        return t4;
	}
}
