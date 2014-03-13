package com.ifp.wechat.entity.menu;
/**
 * 父按钮
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class ComplexButton extends Button{
	
	/**
	 * 子按钮列表
	 */
	 private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public ComplexButton(Button[] sub_button) {
		super();
		this.sub_button = sub_button;
	}

	public ComplexButton() {
		super();
	}  
}
