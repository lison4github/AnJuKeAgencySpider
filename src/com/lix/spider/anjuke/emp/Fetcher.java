package com.lix.spider.anjuke.emp;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * ץȡ���ӿ;�������Ϣץȡ��
 * 
 * @author lison
 * 
 */
public class Fetcher {

	public static void main(String[] args) {
		
		
		SimpleDateFormat sdf =new  SimpleDateFormat("yyyyMMdd HH:mm:ss") ;
		List<String> urls = shopUrls();
		String fileName = "D:\\gz_anjuke_������_20150611.txt";

		int count = 0;
		for (int i = 0; i < urls.size(); i++) {
			String currentUrl = urls.get(i);
			System.out.println("��" + (i + 1) + "��ץȡ���ӿ;�������Ϣ:" + currentUrl);

			String html = "";
			try {
				html = sendByGet(currentUrl);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			System.out.println("��Ŀ�������ץȡ����,��ʼ���н���.");

			AnJuKeEmp emp = null;

			try {
				emp = parse(currentUrl, html);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			String json = emp.toString();
			System.out.println(json);

			try {
				saveJson2File(fileName, json);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (emp.getShopUrl() != null) {
				count++;
				if (count % 100 == 0 && count != 0) {
					System.out.println(sdf.format(new Date())+" �ɹ�ץȡ��" + count + "���;�����Ϣ.");
				}
			}

			if (i != urls.size()) {
				//int interval = (int)(Math.random()*10) ;
				int interval = (int)(Math.random()*3) ;
				System.out.println("Ϊ��ֹ����������," + interval + "���Ӻ����ץȡ...");
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("ץ��+����+���湤��ȫ������.���ɹ���ȡ��������Ϣ������" + count);
	}

	/**
	 * ���ӿ;����˵��̵�ַ 20150611
	 * 
	 * @return
	 */
	public static List<String> shopUrls() {
		List<String> result = new ArrayList<String>();
		try {
			String tmp = "http://sh.xzl.anjuke.com/dianpu/";
			// http://sh.xzl.anjuke.com/dianpu/320
			// http://sh.xzl.anjuke.com/dianpu/1197120
			//for (int i = 320; i < 1197120; i++) {
			for (int i = 2225; i < 1197120; i++) {
				String url = tmp + i;
				result.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String sendByGet(String url) {
		String result = null;
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();

			HttpGet get = new HttpGet(url);

			get.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			get.setHeader("Content-Type", "UTF-8");
			get.setHeader("Accept-Language",
					"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			//get.setHeader("Connection", "keep-alive");
			get.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");

			HttpResponse response = client.execute(get);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static AnJuKeEmp parse(String fetchUrl, String html) {
		AnJuKeEmp anJuKeEmp = new AnJuKeEmp();
		anJuKeEmp.setFetchUrl(fetchUrl);
		anJuKeEmp.setFetchDate(new Date());
		try {
			if (html == null || "".equals(html.trim())) {
				return anJuKeEmp;
			}

			Document doc = Jsoup.parse(html);
			String shopUrl = doc.select(".on").first().select("a").first()
					.attr("href");
			Element element = doc.select(".jj_ce").first();
			Elements pList = element.select("p");
			if (pList != null && pList.size() > 3) {
				String name = pList.get(0).text();
				String phone = pList.get(1).text();
				String proLevel = pList.get(2).text();
				String company = pList.get(3).text();

				name = new String(name.getBytes(), "UTF-8");
				phone = new String(phone.getBytes(), "UTF-8");
				proLevel = new String(proLevel.getBytes(), "UTF-8");
				company = new String(company.getBytes(), "UTF-8");

				anJuKeEmp = new AnJuKeEmp(name, phone, company, proLevel,
						fetchUrl, shopUrl,html.length());
			} else {
				System.out.println("�޷����͸ô������Ϣ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return anJuKeEmp;
	}

	public static void saveJson2File(String fileName, String json) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName, true);
			fw.write(json + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
