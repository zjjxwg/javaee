package org.xwg.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xwg.service.CoreService;
import org.xwg.util.SignUtil;

/**
 * Servlet implementation class CoreServlet
 */
@WebServlet("/coreServlet")
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CoreServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //确认请求来自微信服务器
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature =request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");//时间撮
		// 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr"); 
        PrintWriter out=response.getWriter();
     // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        out.close(); //关闭out对象
        out = null; 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息  
        String responseMessage = CoreService.processRequest(request);
        
        //响应消息,返回给用户
        PrintWriter out=response.getWriter();
        out.print(responseMessage);
        out.close(); 
	}

}
