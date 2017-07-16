package test2;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyService {

	public ArrayList<String> search(String title) {
		String clientId = "vD0fkj0A4tNlZ5Sr8EfX";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "biicc6PyY4";//애플리케이션 클라이언트 시크릿값";
        ArrayList<String> result = null;
        try {
            //String text = URLEncoder.encode("그린팩토리", "UTF-8");
        	//String text = URLEncoder.encode(title, "UTF-8");
        	String text = URLEncoder.encode(title, "UTF-8");
            //String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            String apiURL = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="+ text + "&display=100"; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = null;
            if(responseCode==200) { // 정상 호출
                //br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            	doc = dBuilder.parse(con.getInputStream());
                doc.getDocumentElement().normalize();
            	
            } else {  // 에러 발생
                //br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
            	doc = dBuilder.parse(con.getInputStream());
                doc.getDocumentElement().normalize();
			}
			NodeList nList = doc.getElementsByTagName("item");
			result = new ArrayList<>();
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				/*
				 * String inputLine; response = new StringBuffer(); int count = 0; while
				 * ((inputLine = br.readLine()) != null) { response.append(inputLine + "\n");
				 * System.out.println(count++); } br.close();
				 * System.out.println(response.toString());
				 */
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("image : " + getTagValue("image", eElement));

					result.add(getTagValue("image", eElement));
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return result;

	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}
