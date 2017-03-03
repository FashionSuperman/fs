package com.fashionSuperman.fs.core.mvc.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * HttpResponse 返回数据包装类 伪造输出流,
 * 将之后的写出操作全部保存到ByteArrayOutputStream,
 * 用于之后过滤器中取出,用于接口调用统一返回格式
 * @description 
 * @author FashionSuperman
 * @date 2017年2月24日 下午2:44:31
 * @version 1.0
 */
public class HttpResponseWrapper  extends HttpServletResponseWrapper  {

	private ByteArrayOutputStream buffer = null;
	private ServletOutputStream out = null;
	private PrintWriter writer = null;

	public HttpResponseWrapper(HttpServletResponse resp) throws IOException {
		super(resp);
		buffer = new ByteArrayOutputStream();// 真正存储数据的流
		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
	}

	/**重载父类获取outputstream的方法*/
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	/**重载父类获取writer的方法*/
	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException {
		return writer;
	}

	/**重载父类获取flushBuffer的方法*/
	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	/**将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据*/
	public byte[] getResponseData() throws IOException {
		flushBuffer();
		return buffer.toByteArray();
	}

	/**内部类，对ServletOutputStream进行包装*/
	private class WapperedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream bos = null;
	
		public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
			bos = stream;
		}
	
		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}
		@Override
		public void write(byte[] b) throws IOException {
			bos.write(b, 0, b.length);
		}
	}

}
