package com.jt.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * jackson处理对象==>字符串时，对加了@JsonSerialize的调用此对象的接口方法
 * 对每一个方法jackson都会新建一个此类实例，线程安全考虑？ 
 */
public class DateJsonSerializer<Date> extends JsonSerializer<Date>{
	
	/***
	 * FAQ:
	 * 1)这个方法何时调用?将对象转换为JSON串时
	 * 假如在对象的对应的GET方法上,使用了
	 * @JsonSerialize(using=DateJsonSerializer.class)
	 * 2)这个方法中的参数都是什么含义?
	 * 2.1) value: 要转换的对象
	 * 2.2) gen: json 数据转换器
	 * 2.3) serializers 序列化提供者
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(value);
		// 将日期写入到json串中
		gen.writeString(date);
	}
	
}
