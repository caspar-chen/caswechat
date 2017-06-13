caswechat 微信全接口封装 Java
========================================
微信公共平台所有接口的SDK封装 java版本，你可以使用SDK简单快速开发微信公众账号。

只需三步就可以完成微信消息和一些事件的处理。

目前已经有两家 世界五百强 在使用该sdk

有问题可加群讨论

QQ群：313456207

### 如何使用
1、配置微信服务号的token，修改配置文件wechat.properties

参数名		参数意思

token		Token, 与接口配置信息中的Token要一致

appid		第三方用户唯一凭证

appSecret	第三方用户唯一凭证密钥


2、建立于微信交互的请求servlet：
	
	/**
	 * 处理微信请求servlet
	 * @author caspar.chen
	 * @version 1.0
	 * 
	 */
	public class WeChatServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;
	
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// 随机字符串
			String echostr = request.getParameter("echostr");
	
			PrintWriter out = null;
			try {
				out = response.getWriter();
				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
				if (SignService.checkSignature(request)) {
					out.print(echostr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}
	
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");
	
			// 调用核心业务类接收消息、处理消息
			String respMessage = CoreService.processWebchatRequest(request);
	
			// 响应消息
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(respMessage);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}

	}
3、创建处理消息的业务逻辑类

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return String
	 */
	public static String processWebchatRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = (TextMessage) MessageService
					.bulidBaseMessage(requestMap,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = (NewsMessage) MessageService
					.bulidBaseMessage(requestMap,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);

			String respContent = "";
			// 文本消息
			if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("测试TITLE");
					article.setDescription("测试Description");
					article.setPicUrl("图片地址");
					article.setUrl("http://m.baidu.com");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageService.bulidSendMessage(newsMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
				} else if ("#".equals(content)) {
					textMessage.setContent("###");
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				}
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				if (eventType.equals(ConstantWeChat.EVENT_TYPE_SUBSCRIBE)) {
					// 关注
					respContent = "感谢您关注偶,这里会给您提供最新的公司资讯和公告！\n";
				} else if (eventType
						.equals(ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息
				} else if (eventType.equals(ConstantWeChat.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					// 自定义菜单点击事件
					if (eventKey.equals("11")) {
						respContent = "天气预报菜单项被点击！";
					} else if (eventKey.equals("12")) {
						respContent = "公交查询菜单项被点击！";
					}
				}
				textMessage.setContent(respContent);
				respMessage = MessageService.bulidSendMessage(textMessage,
						ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}





