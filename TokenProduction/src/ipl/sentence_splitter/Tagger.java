/**
 * Sentence Splitter.
 * Copyright (C) 2005  G. Lucarelli
 *
 * This file is part of Sentence Splitter.
 *
 * Sentence Splitter is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Sentence Splitter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package ipl.sentence_splitter;

import java.io.*;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import ipl.sentence_splitter.util.*;
import ipl.sentence_splitter.core.*;

/**
 * <p><b>We use libSVM package.</b><br>
 * Please read the LIBSVM-COPYRIGHT file.<br>
 * </p>
 * <p>Description: Sets \y and \n tags or < sentence > and < / sentence > tags to the
 * documents of a folder, according to a classifier.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 * @see <a href="http://www.csie.ntu.edu.tw/~cjlin/libsvm">libSVM</a>
 */
public class Tagger
{

	//loads the classifier
	public Tagger()
	{
		try
		{
	      model = svm.svm_load_model(sentence_splitter);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	}

	//Marks the files of directory <code>inputdir</code> with \y and \n tags
	//mode 0) or with < sentence > and < / sentence > tags (mode 1).
	public String mark_file(String file_path)
	{
	    String text = FileManager.read(file_path);
	    FeaturesManager featmanager = new FeaturesManager(text);
	    setTags(featmanager);
	    return fix_text(text,
	    		FileManager.get_tagged_text(featmanager.getTokens()));
	}

	//keeps the original format (white spaces and line change characters)//+++++++++++++++++
	//of the text but marks it
	private String fix_text(String text_in, String tagged_text_in)
	{
		StringBuffer text = new StringBuffer(text_in);
		StringBuffer tagged_text = new StringBuffer(tagged_text_in);
		int marker1 = 0;
		int marker2 = 0;
		while(marker1 < text.length())
		{
			//find the next dot in the text
			while(marker1 < text.length() && text.charAt(marker1) != '.')
			{
				marker1++;
			}
			//find the next dot in the tagged text
			while(marker2 < tagged_text.length() && tagged_text.charAt(marker2) != '.')
			{
				marker2++;
			}
			//if the texts finished break
			if(marker1 >= text.length() || marker2 >= tagged_text.length())
			{
				break;
			}
			//copy the marking to the untagged text
			text.insert(marker1+1, tagged_text.charAt(marker2+1) + "" +
					tagged_text_in.charAt(marker2+2));
			//pass the dot
			marker1 += 3;
			marker2++;
		}
		return text.toString();
	}

	//Sets the "end of period" tags according to the classifier
	private void setTags(FeaturesManager featmanager)
	{
		if (model == null)
		{
			System.err.println("\nThere is no classifier!");
			System.exit(1);
		}

		String features[];
		int offset;

		while ((features = featmanager.getNextFeaturesVector()) != null)
		{
			offset = featmanager.getOffset();
			int numOfFeatures = features.length - 1;
			svm_node node[] = new svm_node[numOfFeatures];
			for (int i = 1; i <= numOfFeatures; i++)
			{
				node[i - 1] = new svm_node();
				node[i - 1].index = i;
				node[i - 1].value = Double.parseDouble(features[i]);
			}
			if (svm.svm_predict(model, node) >= 0)
			{
				featmanager.getTokens().get(offset).setEndOfPeriod(true);
			}
		}
	}

	//the classifier
	svm_model model;
	public static String file_separator;
	
	static
	{
		file_separator = System.getProperty("file.separator");
		file_separator = (file_separator.equals("\\"))?"\\\\":file_separator;
	}
	
	private String sentence_splitter = 
		"." + file_separator + "classifier" + file_separator + "CLASSIFIER_EXAMPLE";
}
