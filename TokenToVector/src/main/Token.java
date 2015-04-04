package main;

import java.io.Serializable;

public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 138303813247237501L;
	
	private String content;
	
	private boolean isEndOfSentence;
	
	private boolean isTemporalExpression;
	
	private boolean isPersonName;
	
	private boolean isOrganizationName;
	
	private boolean isLocationName;
	
	private boolean isNotAPersonSureFired;
	
	private boolean isNotAnOrganizationSureFired;
	
	private boolean isNotAName;
	
	private boolean isTitle;

	public Token(String content){
		this.content = content;
		isEndOfSentence = false;
		isTemporalExpression = false;
		isPersonName = false;
		isOrganizationName = false;
		isLocationName = false;
		isNotAPersonSureFired = false;
		isNotAnOrganizationSureFired = false;
		isNotAName = false;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isEndOfSentence() {
		return isEndOfSentence;
	}

	public void setEndOfSentence(boolean isEndOfSentence) {
		this.isEndOfSentence = isEndOfSentence;
	}
	
	public boolean isTemporalExpression() {
		return isTemporalExpression;
	}

	public void setTemporalExpression(boolean isTemporalExpression) {
		this.isTemporalExpression = isTemporalExpression;
	}

	public boolean isPersonName() {
		return isPersonName;
	}

	public void setPersonName(boolean isPersonName) {
		this.isPersonName = isPersonName;
	}

	public boolean isOrganizationName() {
		return isOrganizationName;
	}

	public void setOrganizationName(boolean isOrganizationName) {
		this.isOrganizationName = isOrganizationName;
	}

	public boolean isNotAPersonSureFired() {
		return isNotAPersonSureFired;
	}

	public void setNotAPersonSureFired(boolean isNotAPersonSureFired) {
		this.isNotAPersonSureFired = isNotAPersonSureFired;
	}

	public boolean isNotAnOrganizationSureFired() {
		return isNotAnOrganizationSureFired;
	}

	public void setNotAnOrganizationSureFired(boolean isNotAnOrganizationSureFired) {
		this.isNotAnOrganizationSureFired = isNotAnOrganizationSureFired;
	}

	public boolean isNotAName() {
		return isNotAName;
	}

	public void setNotAName(boolean isNotAName) {
		this.isNotAName = isNotAName;
	}
	
	public boolean isLocationName() {
		return isLocationName;
	}

	public void setLocationName(boolean isLocationName) {
		this.isLocationName = isLocationName;
	}
	
	public boolean isTitle() {
		return isTitle;
	}

	public void setTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}

	public String toString(){
		return content;
	}
	
}
