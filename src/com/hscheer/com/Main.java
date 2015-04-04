package com.hscheer.com;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame
{

	Main(String name)
	{
		super(name);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Main window = new Main("互联网特定序号文件下载器");

		window.setVisible(true);
		window.setSize(870, 400);
		window.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		window.setLayout(new FlowLayout());

		JLabel addressJLabel = new JLabel("地址（请放到||中）");
		final JTextField addressField = new JTextField("www.baidu.com/demo/|123|.rar");
		addressField.setColumns(30);
		JLabel beginJLabel = new JLabel("开始的数字");
		final JTextField beginField = new JTextField("1");
		beginField.setColumns(2);
		JLabel numJLabel = new JLabel("循环的次数");
		final JTextField numField = new JTextField("1");
		numField.setColumns(2);
		JButton button = new JButton("get");

		final TextArea infoArea = new TextArea(20, 100);
		infoArea.setText("ready? go!>>\n");

		window.add(addressJLabel);
		window.add(addressField);
		window.add(beginJLabel);
		window.add(beginField);
		window.add(numJLabel);
		window.add(numField);
		window.add(button);
		window.add(infoArea);

		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String addString = addressField.getText();
				int begin = Integer.parseInt(beginField.getText());
				int num = Integer.parseInt(numField.getText());

				// 拆分字符串
				String[] add = new String[3];
				add = addString.split("\\|");

				int top = num + begin;

				for (int i = begin; i < top; i++)
				{
					String newaddress = add[0] + i + add[2];
					URL url = null;
					try
					{
						url = new URL(newaddress);
						URLConnection urlConnection = url.openConnection();
						// 文件类型和大小
						String filetype = urlConnection.getContentType();
						int filelenth = urlConnection.getContentLength();

						FileOutputStream fos = new FileOutputStream(i + "" + add[2]);
						InputStream is = urlConnection.getInputStream();
						int data;
						infoArea.append(newaddress + "--->下载中..." + "		文件类型：" + filetype + "		文件大小：" + filelenth + "\n");
						while ((data = is.read()) != -1)
						{
							fos.write(data);
						}
						is.close();
						fos.close();

						infoArea.append(newaddress + "--->下载成功\n");

					} catch (Exception e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						infoArea.append("打开URL失败，请检查URL的正确性--->" + newaddress + "\n");
					}

				}

				// 结束
				infoArea.append("finished.\n");
			}
		});

		window.setVisible(true);

	}
}
