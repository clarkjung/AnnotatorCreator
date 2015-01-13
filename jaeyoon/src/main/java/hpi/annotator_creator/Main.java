package hpi.annotator_creator;

import hpi.annotator_creator.tuple.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws IOException {

		final String FILEPATH = "data/excel.xlsx";
		File file = new File("data/test.html");
		Document doc = Jsoup.parse(file, "UTF-8", "");
		Elements data = doc.getElementsByClass("data");
		Elements trs = data.select("tr");
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Annotation");
		int rowIndex = 1;
		
		int i = 0;
		ArrayList<Integer> groupIdArray = new ArrayList<Integer>();
		for(Element tr : trs){
			if(++i == 1) continue;
			Tuple tuple = extractTdContent(tr);
			if(groupIdArray.contains(tuple.getGroup_id())) continue;
			if(tuple.getEvent_text().contains("We raised nearly")) continue;
			if(tuple.getEvent_text().length() < 50 || tuple.getGroup_text().length() < 50) continue;
			
			writeToExcel(sheet, rowIndex, tuple);
			groupIdArray.add(tuple.getGroup_id());
			rowIndex++;
			
			if(rowIndex == 201) break;
		}
		
		try{
			FileOutputStream fos = new FileOutputStream(FILEPATH);
			workbook.write(fos);
			fos.close();

			System.out.println(FILEPATH + " is successfully written");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
		
	}
	
	public static Tuple extractTdContent(Element tr){
		
		Tuple tuple = new Tuple();
		Element temp = tr;
		Elements tds = temp.select("td");
		
		int i = 0;
		for (Element td: tds){
			i++;
			
			if(i == 1){
				continue;
			}else if(i == 2){
				tuple.setGroup_id(Integer.parseInt(td.text()));
				continue;
			}else if(i == 3){
				tuple.setEvent_text(PlainTextParser.html2PlainText(td.text()));
			}else{
				tuple.setGroup_text(PlainTextParser.html2PlainText(td.text()));
			}
		}
		
		return tuple;
	}
	
	public static void writeToExcel(Sheet sheet, int rowIndex, Tuple tuple){
		
		Row row = sheet.createRow(rowIndex);
		int cellIndex = 0;
		row.createCell(cellIndex++).setCellValue(rowIndex);
		row.createCell(cellIndex++).setCellValue(tuple.getEvent_text());
		row.createCell(cellIndex++).setCellValue(tuple.getGroup_text());
		
	}
	
	

}
