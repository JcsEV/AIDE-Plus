/**
 * @Date 
 * @AIDE AIDE+ 
 */
package io.github.zeroaicy.aide.completion;
import java.util.List;
import java.util.ArrayList;

public class QuickCode{
	private String kj;
	private String nameLowerCase;
	private String codeText;

	private String bt;

	public void setBt(String bt){
		this.bt = bt;
	}

	public String getBt(){
		return bt;
	}

	public void setKj(String kj){
		this.kj = kj;
	}

	public void setCodeText(String codeText){
		this.codeText = codeText;
	}

	public String getKj(){
		return kj;
	}

	public String getNameLowerCase(){
		if( nameLowerCase == null){
			nameLowerCase = kj.toLowerCase();
		}
		return nameLowerCase;
	}
	
	public String getCodeText(){
		return codeText;
	}
}
