package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArffPrinter {

	@SuppressWarnings("unchecked")
	public static void printVectors(List<Object> vectors, int start, int end, int m, List<String> order){
		BufferedWriter bwriter = null;
		// create file
		try{
			File f = new File("C:\\Users\\Konstantinos Pappas\\Desktop\\results\\organizations\\"+start+","+end+","+m+".arff");
	        bwriter = new BufferedWriter(new FileWriter(f));
	        
	        //write arff headers
	        bwriter.write(getMFeaturesHeader(m, order));

	        bwriter.write("@DATA"+System.lineSeparator());
	        
	        //write all vectors
	        for(int i=0; i<vectors.size(); ++i){
	            bwriter.write(getVectorAsArffLine((List<Object>)vectors.get(i), m));
	        }
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				bwriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Returns the header for the arff file
	 * @return
	 */
	private static String getMFeaturesHeader(int m, List<String> order){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("@RELATION ner");
		builder.append(System.lineSeparator());
		builder.append(System.lineSeparator());
		
		for(int i=0; i<m; ++i){
			builder.append("@ATTRIBUTE "+order.get(i)+" NUMERIC");
			builder.append(System.lineSeparator());
		}

		builder.append("@ATTRIBUTE class  {Y, N}");
		builder.append(System.lineSeparator());
		builder.append(System.lineSeparator());
		
		return builder.toString();
	}

	/**
	 * Creates a String representation of feature Vector in the form:
	 * <item1, item2, item3, ...>
	 * 
	 * @param featureVector
	 * @return
	 */
	private static String getVectorAsArffLine(List<Object> featureVector, int m) {
		
		StringBuilder result = new StringBuilder();
		
		int vectorSize = featureVector.size();
		for (int i = 0; i < m; i++) {
			String value = null;
			Object fValue = featureVector.get(i);
			if(fValue != null && fValue.getClass() == Boolean.class){
				value = ((Boolean) fValue)?"1.0":"-1.0";
			} else if (fValue != null && fValue.getClass() == Double.class) {
				value = ((Double) fValue).toString();
			} else if (fValue != null && fValue.getClass() == String.class) {
				value = (String) fValue;
			} else {
				//fValue is null
				value = "?";
			}
			result.append(value);
			/*if (i < vectorSize -1) {*/
				result.append(", ");
			/*}*/
		}
		//also append the class of the vector
		result.append(featureVector.get(vectorSize-1));
		result.append(System.lineSeparator());
		
		return result.toString();
	}
	
}
