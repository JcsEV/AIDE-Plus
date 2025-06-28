/**
 * @Date 
 * @AIDE AIDE+ 
 */
package io.github.zeroaicy.aide.completion;
import java.util.List;
import java.util.ArrayList;

public class QuickCode{
	private String name;
	private String nameLowerCase;
	private String codeText;



	public void setName(String name){
		this.name = name;
	}

	public void setCodeText(String codeText){
		this.codeText = codeText;
	}

	public String getName(){
		return name;
	}

	public String getNameLowerCase(){
		if( nameLowerCase == null){
			nameLowerCase = name.toLowerCase();
		}
		return nameLowerCase;
	}
	
	public String getCodeText(){
		return codeText;
	}
}
