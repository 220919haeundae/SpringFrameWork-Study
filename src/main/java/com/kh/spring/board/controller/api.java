package com.kh.spring.board.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class api {
	public static void main(String[] args) throws IOException {

		StringBuilder urlBuilder = new StringBuilder("http://api.kcisa.kr/openapi/CNV_060/request"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=284eaa37-58e2-4f09-a9a0-6fb9e79f3642"); /* 서비스키 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 세션당 요청레코드수 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지수 */
		urlBuilder.append("&" + URLEncoder.encode("dtype", "UTF-8") + "=" + URLEncoder.encode("전시", "UTF-8")); /* 분류명(연극, 뮤지컬, 오페라, 음악, 콘서트, 국악, 무용, 전시, 기타) */
		urlBuilder.append("&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode("사진", "UTF-8")); /* 제목(2자이상) */
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		//json type으로 응답받고 싶을 때는 아래 주석을 제거하시고 사용바랍니다.
		conn.setRequestProperty("Accept","application/json");
		System.out.println("Response code: " + conn.getResponseCode());

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {

			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

		}
		File file = new File("C:\\workspace/text.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			bw.write(line);
			sb.append(line);

		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

	}

}