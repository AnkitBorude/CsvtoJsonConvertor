package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class MyFirst {

	public static void main(String args[]) {
		try
		{
		CsvtoJson convertor=new CsvtoJson("/home/ankitborude/Downloads/demoex.csv","/home/ankitborude/Desktop/AmazonDataset/testing.json");
		convertor.convert();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
class CsvtoJson{
		String csvPath,jsonPath;
	public CsvtoJson(String csvSource,String jsonSource) {
		this.csvPath=csvSource;
		this.jsonPath=jsonSource;
}
	public void convert() throws IOException {
		System.out.println("Converting....");
		File srcfile,destfile;
		int totallines=0;
		ArrayList<String> attributes=new ArrayList<String>();
		ArrayList<String> values=new ArrayList<String>();
		boolean isAttribute=false;
		BufferedReader reader;
		BufferedWriter writer;
		try
		{
			srcfile=new File(csvPath);
			destfile =new File(jsonPath);
			reader=new BufferedReader(new FileReader(srcfile));
			writer=new BufferedWriter(new FileWriter(destfile));
		writer.append("{\n");
		String filename=srcfile.getName();
		writer.append("\""+filename+"\": [\n");
		
		while(true) {
			String line=reader.readLine();
			if(line==null) {
				writer.append("]\n}");
				break;
			}else
			{	if(!isAttribute)
			{
			   attributes=getElementsofLine(line);
			   isAttribute=true;
			}else {
				
				values=getElementsofLine(line);
				StringBuilder builder=new StringBuilder();
				builder.append("{\n");
				
				for(int i=0;i<attributes.size();i++)
				{
					String attr=attributes.get(i);
					String value=values.get(i);
					if(value=="")
					{
						continue;
					}
					builder.append("\""+attr+"\":");
					if(i==values.size()-1)
					{
						if(value.startsWith("\""))
						{
							builder.append(value+"\"");
						}else if(value.endsWith("\"")){
						builder.append("\""+value);
						}else {
							builder.append("\""+value+"\"");
						}
					}else {
						if(value.startsWith("\""))
						{
							builder.append(value+"\",\n");
						}else if(value.endsWith("\"")){
						builder.append("\""+value+",\n");
						}else {
							builder.append("\""+value+"\",\n");
						}
					}
				}
				builder.append("},");
				writer.append(builder.toString());
			}
			
			totallines++;}
		}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Done.... Created "+totallines+" objects");
	}
	public ArrayList<String> getElementsofLine(String line){
		int i=0,j=0;
		ArrayList<String> list=new ArrayList<String>();
			while(line.indexOf(',',i)!=-1)
		   {
			   i=line.indexOf(',',i);
			   list.add(line.substring(j, i));
			   i++;
			   j=i;
		   }
		   list.add(line.substring(j,line.length()));
		return list;
		
	}
	}