package main.java.com.generater;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

public class Generator {
	public static void main(String[] args) throws FileNotFoundException {
		
		
        String csvFile = "./resources/airbnb.csv";
        Boolean skipFirst = true;
        int n = getLength(csvFile);
        if (n == 0) throw new FileNotFoundException("File is Empty");
        
        if (skipFirst) n--;
        
        String[] data = getData(csvFile,142000, n, 2, skipFirst);
       
        //JSONObject obj = generateJsonObj("RandomEven",data, 0, data.length-1 , 10000 , 10);
        //writeToJson(obj,"./WebContent/test.json");
        
        JSONArray obj = generateJsonArrayForBar(2, data);
        writeToJson(obj,"./WebContent/test1.json");
    }
	
	

	@SuppressWarnings("unchecked")
	public static JSONArray generateJsonArrayForBar(int split ,String[] data) {
		JSONArray list = new JSONArray();
		int start = 0;
		int end = data.length-1;
		int splitLen = (end+1) / split;
		
		for (int i = 1; i <= split ; i++) {
			int endTemp = i==split ? end : start + splitLen;
			getBar(data, list, start, endTemp);
			start = start + splitLen + 1;
		}
		
		return list;
	}



	@SuppressWarnings("unchecked")
	public static void getBar(String[] data, JSONArray list, int start, int end) {
		
		String current = data[start];
		JSONObject obj = new JSONObject();
		obj.put("name", data[start] + "---" + data[end]);
		obj.put("rowRange", start + "---" + end);
		obj.put("size", end - start + 1);
		
		JSONArray listTemp = new JSONArray();
		
		int count = 1;
		int i = start+1;
		int subCat = 1;
		while (i< end){
			if (current.equals(data[i])) {
				count++;
			} else {
				JSONObject objTemp = new JSONObject();
				objTemp.put("name", current);
				objTemp.put("count", count);
				listTemp.add(objTemp);
				count = 1;
				current = data[i];
				subCat++;
			}
			i++;
		}
		//add the last one
		JSONObject objTemp = new JSONObject();
		objTemp.put("name", current);
		objTemp.put("count", count);
		listTemp.add(objTemp);
		
		obj.put("children", listTemp);
		obj.put("subCat", subCat);
		
		list.add(obj);
	}



	@SuppressWarnings("unchecked")
	public static JSONObject generateJsonObj(String model, String[] data, int start, int end , int minNum ,int cellPerLevel) {
		JSONObject obj = new JSONObject();
		
		if (model.equals("RandomEven")) {
			obj.put("name", data[start] + "---" + data[end]);
			obj.put("rowRange", start + "---" + end);
			obj.put("size", end - start +1);
			int subCat = 0;
			
			if (end - start > minNum){
				int splitLen = (end - start) / cellPerLevel;
				JSONArray list = new JSONArray();
				while (start + 2 * splitLen < end) {
					list.add(generateJsonObj(model,data, start, start+splitLen, minNum, cellPerLevel));
					subCat ++;
					start += splitLen;
					start++;
				}
				list.add(generateJsonObj(model,data, start, end, minNum, cellPerLevel));
				subCat++;
				obj.put("children", list);
			} 
			obj.put("subCat", subCat);
			
		}
		
		if (model.equals("ByName")) {
			obj.put("name", data[start] + "---" + data[end]);
			obj.put("size", end - start +1);
			obj.put("rowRange", start + "---" + end);
			int i = start;
			JSONArray list = new JSONArray();
			while (i < end){
				
				String name = data[i];
				int s = i;
				
				while (i < end && data[i].equals(data[i+1])) i++;
				JSONObject objTemp = new JSONObject();
				
				objTemp.put("name", name);
				objTemp.put("size", i - s + 1);
				objTemp.put("rowRange", s + "---" + i);
				list.add(objTemp);
				i++;
			} 
			obj.put("children", list);
		}
		
		return obj;
	}

	public static void writeToJson(JSONObject obj, String location) {
		 try (FileWriter file = new FileWriter(location)) {
	            file.write(obj.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void writeToJson(JSONArray obj, String location) {
		 try (FileWriter file = new FileWriter(location)) {
	            file.write(obj.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	public static int getLength(String csvFile){
		int len = 0;
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while (br.readLine() != null) {
                len ++;
            }
            System.out.println(len);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return len;
	}
	
	public static String[] getData(String csvFile, int start ,int end , int col ,boolean skipFirst) {
		if (start > end) return new String[0];
		BufferedReader br = null;
		int i = 0;
		String line = "";
		String cvsSplitBy = ",";
		String[] data = new String[end-start];
		int current = 0;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            if (skipFirst) br.readLine();
            while ( current < start && start < end && (line = br.readLine()) != null){
            	current++;
            }
            
            while ( start < end && (line = br.readLine()) != null) {
                // use comma as separator
                data[i++] = line.split(cvsSplitBy)[col];
                start++;
            }
            System.out.println(data[0]);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
	}
	
}
