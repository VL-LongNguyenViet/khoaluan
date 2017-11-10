package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 */

public class Similarity {
	
	private Sentence firstSentence;
	private Sentence secondSentence;
	private double similarityRate;
	
	Similarity(){
		firstSentence = new Sentence();
		secondSentence = new Sentence();
		similarityRate = 0;
	}
	
	Similarity(Sentence _firstSentence, Sentence _secondSentence){
		firstSentence = _firstSentence;
		secondSentence = _secondSentence;
		similarityRate = calculateSimilarityRate();
	}
	
	// Calculate simialarity rate of 2 sentences
	double calculateSimilarityRate() {
		int numberOfWordsFirstSentence = firstSentence.getWordsInSentence().size();
		int numberOfWordsSecondSentence = secondSentence.getWordsInSentence().size();
		int numberOfWordsMax = numberOfWordsFirstSentence < numberOfWordsSecondSentence ? numberOfWordsFirstSentence : numberOfWordsSecondSentence;
		double numerator = 0.0;
		double firstSentenceDenominator = 0.0;
		double secondSentenceDenominator = 0.0;
		double denominator = 0.0;
		for (int pos = 0; pos < numberOfWordsMax; pos++) {
			numerator += firstSentence.getWordsInSentence().get(pos).getWordImportanceInEachSentence().get(firstSentence.getSentencePos())
					* secondSentence.getWordsInSentence().get(pos).getWordImportanceInEachSentence().get(secondSentence.getSentencePos());
			firstSentenceDenominator += Math.pow(firstSentence.getWordsInSentence().get(pos).getWordImportanceInEachSentence().get(firstSentence.getSentencePos()), 2);
			secondSentenceDenominator += Math.pow(secondSentence.getWordsInSentence().get(pos).getWordImportanceInEachSentence().get(secondSentence.getSentencePos()), 2);
		}
		denominator = Math.sqrt(firstSentenceDenominator * secondSentenceDenominator);
		return numerator / denominator;
	}
	
	void setFirstSentence (Sentence _firstSentence) {
		firstSentence = _firstSentence;
	}
	
	void setSecondSentence (Sentence _secondSentence) {
		secondSentence = _secondSentence;
	}
	
	void setSimilarityRate (double _similarityRate) {
		similarityRate = _similarityRate;
	}
	
	Sentence getFirstSentence () {
		return firstSentence;
	}
	
	Sentence getSecondSentence() {
		return secondSentence;
	}
	
	double getSimilarityRate() {
		return similarityRate;
	}
}
