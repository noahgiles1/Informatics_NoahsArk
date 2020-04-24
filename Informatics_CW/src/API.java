import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;


public class API {

		public static void main(String[] args) {
			
			
			try {
				
			
			String url = "https://api.covid19api.com/summary";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			int responseCode = con.getResponseCode();
			System.out.println("Sending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			
			for(String str : getData(response)) {
				System.out.println(str);
			}
			
			
			}
			
			catch (Exception e){
				System.out.println(e);
			}
				
			
		}
		
		public static String[] getData(StringBuffer response) {
			Gson gson = new Gson();
			MyPojo myPojo = gson.fromJson(response.toString(), MyPojo.class);
			
			for(int i=0; i < myPojo.getCountries().length; i++) {
				System.out.println(myPojo.getCountries()[i]);
			}
			
			return new String[] {
				"Global Stats: " + myPojo.getGlobal()
			};
			
		}
}
