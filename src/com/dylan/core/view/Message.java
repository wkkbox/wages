package com.dylan.core.view;

import org.springframework.web.servlet.ModelAndView;

public class Message {
	
	public static final boolean REDIRECT = true;
	public static final int ALERT = 0;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int DANGER = 3;
	public static final String NOTIFY_MESSAGE = "notifyMessage";
	public static final String DEFAULT_MESSAGE = "操作成功,一条记录被生效!";
	
	public ModelAndView showView(ModelAndView view,int level){
		return showView(view,DEFAULT_MESSAGE,level);
	}
	
	public ModelAndView showView(ModelAndView view,String msg,int level){
		if(view.getViewName().indexOf(":") != -1){
			view.addObject(NOTIFY_MESSAGE,"redirect");
		}else{
			if(level == INFO){
				view.addObject(NOTIFY_MESSAGE,info(msg));
			}else if(level == WARN){
				view.addObject(NOTIFY_MESSAGE,warnning(msg));
			}else if(level == DANGER){
				view.addObject(NOTIFY_MESSAGE,danger(msg));
			}
		}
		return view;
	}
	
	/**
	 * @description:tip:
	 * @param msg:消息内容
	 * @param className:bg-info,bg-warning,bg-danger,,,
	 * <script>
	 * 		$(function(){
	 *			new PNotify({
	 *				title:'提示操作',
	 *				text:'Message Content',
	 *				addclass:'bg-info'
	 *			});
	 *		});
	 * </script>
	 */
	public String tip(String msg,String className){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("		$(function(){");
		sb.append("			new PNotify({");
		sb.append("				title:'提示操作',");
		sb.append("				text:'").append(msg).append("',");
		sb.append("				addclass:'").append(className).append("'");
		sb.append("			});");
		sb.append("		});");
		sb.append("</script>");
		return sb.toString();
	}
	
	public String info(String message){
		return tip(message,"bg-info");
	}
	public String warnning(String message){
		return tip(message,"bg-warning");
	}
	public String danger(String message){
		return tip(message,"bg-danger");
	}
	
}
