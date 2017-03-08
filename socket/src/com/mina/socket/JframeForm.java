package com.mina.socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JframeForm extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel jLabel;
	private JLabel countLabel;
	private JTextField jTextField;
	private JButton jButton;
	private JTextArea textArea;
	private JTextField countField;
	private JLabel ipLabel;//ip
	private JTextField ipField;//ip输入框
	private JLabel portLabel;//端口
	private JTextField portField;
	private JLabel nameLabel;//端口
	private JTextField nameField;
	private JButton conButton;//连接按钮
	
	public JframeForm(String title){
		this.setTitle(title);
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		this.add(getJTextArea(),null);
		this.add(getCountJLabel(),null);
		this.add(getCountField(),null);
		this.add(getJLabel(), null);
		this.add(getJTextField(), null);
		this.add(getJButton(), null);
		this.add(getIpJLabel(),null);
		this.add(getIpField(),null);
		this.add(getPortJLabel(),null);
		this.add(getPortField(),null);
		this.add(getNameJLabel(),null);
		this.add(getNameField(),null);
		this.add(getConJButton(),null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private JLabel getJLabel(){
		if(jLabel == null){
			jLabel = new JLabel();
			jLabel.setBounds(20, 50, 53, 18);
			jLabel.setText("内容");
		}
		return jLabel;
	}
	private JLabel getCountJLabel(){
		if(countLabel == null){
			countLabel = new JLabel();
			countLabel.setBounds(20, 20, 120, 18);
			countLabel.setText("当前使用人数");
		}
		return countLabel;
		
	}
	private JTextField getCountField(){
		if(countField == null) {
			countField = new JTextField();
			countField.setBounds(120, 20, 50, 20);
			countField.setEditable(false);
		}
		return countField;
	}
	
	private JLabel getNameJLabel(){
		if(nameLabel == null){
			nameLabel = new JLabel();
			nameLabel.setBounds(230, 20, 120, 18);
			nameLabel.setText("用户名");
		}
		return nameLabel;
		
	}
	private JTextField getNameField(){
		if(nameField == null) {
			nameField = new JTextField();
			nameField.setBounds(280, 20, 60, 20);
		}
		return nameField;
	}
	
	private JLabel getIpJLabel(){
		if(ipLabel == null){
			ipLabel = new JLabel();
			ipLabel.setBounds(20, 20, 120, 18);
			ipLabel.setText("IP");
		}
		return ipLabel;
		
	}
	private JTextField getIpField(){
		if(ipField == null) {
			ipField = new JTextField();
			ipField.setBounds(50, 20, 80, 20);
		}
		return ipField;
	}
	
	private JLabel getPortJLabel(){
		if(portLabel == null){
			portLabel = new JLabel();
			portLabel.setBounds(140, 20, 120, 18);
			portLabel.setText("端口");
		}
		return portLabel;
		
	}
	private JTextField getPortField(){
		if(portField == null) {
			portField = new JTextField();
			portField.setBounds(180, 20, 40, 20);
		}
		return portField;
	}
	
	private JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new JTextField();
		    jTextField.setBounds(50, 370, 400, 20);
		}
		return jTextField;
	}
	private JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
		    jButton.setBounds(50, 420, 71, 27);
		    jButton.setText("发送");
		}
		return jButton;
	}
	private JButton getConJButton() {
		if(conButton == null) {
			conButton = new javax.swing.JButton();
			conButton.setBounds(350, 20, 120, 27);
			conButton.setText("连接服务器");
		}
		return conButton;
	}
	private JTextArea getJTextArea(){
		if(textArea == null){
			textArea = new JTextArea();
			textArea.setBounds(50, 50, 400, 300);
			textArea.setToolTipText("内容");
			textArea.setEditable(false);
		}
		return textArea;
	}
	
	public String getSendMessage(){
		return jTextField.getText();
	}
	public void setContent(String message){
		textArea.append(message + "\n");
	}
	public JButton getJbutton(){
		return jButton;
	}
	public JTextField getTextField(){
		return jTextField;
	}
	public JTextField getcountField(){
		return countField;
	}
	public void hidden(){
		jButton.setVisible(false);
		jTextField.setVisible(false);
	}
	public void hiddenCount(){
		countField.setVisible(false);
		countLabel.setVisible(false);
	}
	
	public void hideConnection(){
		ipLabel.setVisible(false);
		ipField.setVisible(false);
		portLabel.setVisible(false);
		portField.setVisible(false);
		nameLabel.setVisible(false);
		nameField.setVisible(false);
		conButton.setVisible(false);
	}
	
	public JButton getConButton(){
		return conButton;
	}
	public String getIp(){
		return ipField.getText();
	}
	public String getPort(){
		return portField.getText();
	}
	public String getUserName(){
		return nameField.getText();
	}
}
